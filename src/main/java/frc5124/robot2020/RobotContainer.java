/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.HashMap;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.GyroBase;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

import frc5124.robot2020.commands.*;

import frc5124.robot2020.commands.auto.ChangeCamera;

import frc5124.robot2020.commands.auto.ShootThreeBalls;
import frc5124.robot2020.commands.auto.RunDistanceForward;
import frc5124.robot2020.commands.auto.SixBallAuto;
import frc5124.robot2020.commands.auto.SixBallAutoNoShoot;
import frc5124.robot2020.commands.auto.ThreeBallAuto;
import frc5124.robot2020.commands.auto.ThreeBallAutoDriveIn;
import frc5124.robot2020.commands.auto.runpos.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc5124.robot2020.commands.driveTrain.*;
import frc5124.robot2020.commands.hanger.LiftDown;
import frc5124.robot2020.commands.hanger.LiftUp;
import frc5124.robot2020.commands.intake.*;
import frc5124.robot2020.commands.loader.*;
import frc5124.robot2020.commands.shooter.*;
import frc5124.robot2020.commands.turret.*;
import frc5124.robot2020.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
  * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */

public class RobotContainer {
  
  private Intake intake;
  private Hanger hanger;
  private DriveTrain driveTrain;
  private Shooter shooter; 
  private Turret turret;
  private Loader loader;

  public static final Joystick driverLeft = new Joystick(0);
  public static final Joystick driverRight = new Joystick(1);
  public XboxController operator = new XboxController(2);
  
  public JoystickButton operatorA = new JoystickButton(operator, 2);
  public JoystickButton operatorB = new JoystickButton(operator, 3);
  public JoystickButton operatorX = new JoystickButton(operator, 1);
  public JoystickButton operatorY = new JoystickButton(operator, 4);
  public JoystickButton operatorLB = new JoystickButton(operator, 5);
  public JoystickButton operatorRB = new JoystickButton(operator, 6);
  public JoystickButton operatorLT = new JoystickButton(operator, 7);
  public JoystickButton operatorRT = new JoystickButton(operator, 8);
  public JoystickButton operatorBack = new JoystickButton(operator, 9);
  public JoystickButton operatorStart = new JoystickButton(operator,10);
  public JoystickButton operatorTest = new JoystickButton(operator, 9);
  public JoystickButton operatorStickLeft = new JoystickButton(operator, 11);
  public JoystickButton operatorStickRight = new JoystickButton(operator, 12);

  public JoystickButton driverRightTrigger = new JoystickButton(driverRight, 1);
  public JoystickButton driverRightThumb  = new JoystickButton(driverRight, 2);

  public POVButton operatorUp = new POVButton(operator, 0);
  public POVButton operatorDown = new POVButton(operator, 180);
  public POVButton operatorRight = new POVButton(operator, 90);
  public POVButton operatorLeft = new POVButton(operator, 270);
  
  private ShuffleboardTab display;
  private Supplier<String> autoNameSupplier;
  private HashMap<String, Command> autonomies = new HashMap<>();

  public RobotContainer() {
    configureSubsystems();
    configureButtonBindings();
    configureShuffleboard();
    configureDefaultCommands();
  }

  private void configureSubsystems() {
    intake = new Intake();
    hanger = new Hanger();
    loader = new Loader();
    driveTrain = new DriveTrain();
    shooter = new Shooter();
    turret = new Turret();
  }

  private void configureButtonBindings(){
    operatorStart.whileHeld(new SetIntakePower(intake, -.6));
    operatorBack.whileHeld(new ReverseBeltAndShooter(shooter, loader));
    operatorX.whileHeld(new LoaderAndIntakeGroup(intake, loader));
    operatorA.whenPressed(new ToggleIntakePivot(intake));
    operatorB.toggleWhenPressed(new TurnToAngle(driveTrain));
    operatorRight.whileHeld(new RotateTurret(turret, false));
    operatorLeft.whileHeld(new RotateTurret(turret, true));
    operatorRB.toggleWhenPressed(new RPMbyFF(shooter, loader, 4400)); //line distance
    operatorLB.toggleWhenPressed(new RPMbyFF(shooter, loader, 4950)); //trench distance
    operatorY.whileHeld(new RunLoader(loader));
    operatorUp.whileHeld(new LiftUp(hanger));

    operatorDown.whileHeld(new LiftDown(hanger));

    driverRightTrigger.whenPressed(new ChangeCamera(
      () -> ChangeCamera.lastSelection == ChangeCamera.INTAKE_CAM ? ChangeCamera.CLIMB_CAM : ChangeCamera.INTAKE_CAM)
    );
    driverRightThumb.whenPressed(new ChangeCamera(ChangeCamera.LIMELIGHT));
  }

  private void configureDefaultCommands(){
    driveTrain.setDefaultCommand(new JoystickTankDrive(driverLeft, driverRight, driveTrain));

    autonomies.put("Trench Primary", new SixBallAuto(turret, loader, shooter, driveTrain, intake));
    autonomies.put("Trench Secondary", new SixBallAutoNoShoot(turret, loader, shooter, driveTrain, intake));
    autonomies.put("Middle Primary", new ThreeBallAuto(turret, loader, shooter, driveTrain, intake));
    autonomies.put("Middle Secondary", new ThreeBallAutoDriveIn(turret, loader, shooter, driveTrain, intake));
    autonomies.put("Opposing Trench Primary", new ThreeBallAuto(turret, loader, shooter, driveTrain, intake));
    autonomies.put("Opposing Trench Secondary", new ThreeBallAutoDriveIn(turret, loader, shooter, driveTrain, intake));
    Command zeroTurret = new TurretZeroAndTurn(turret);
    autonomies.put("Trench Zero Turret", zeroTurret);
    autonomies.put("Middle Zero Turret", zeroTurret);
    autonomies.put("Opposing Trench Zero Turret", zeroTurret);
  }

  private void configureShuffleboard() {
    display = Shuffleboard.getTab("Driving Display");
    Shuffleboard.selectTab(display.getTitle());

    NetworkTableEntry pipeEntry = NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline");

    display.addNumber("Balls Intaked", loader::getBallsIntaked)
    .withPosition(3, 1).withSize(1, 1);
    display.addBoolean("Limelight On?",() -> (int) pipeEntry.getDouble(-1) == 0)
    .withPosition(4, 1).withSize(1, 1).withWidget(BuiltInWidgets.kBooleanBox);
    display.addBoolean("Intake Running?", () -> Math.abs(intake.getOutput()) > 0.0001)
    .withPosition(3, 2).withSize(1, 1).withWidget(BuiltInWidgets.kBooleanBox);
    display.addBoolean("Shooter On?", shooter::active)
    .withPosition(4, 2).withSize(1, 1).withWidget(BuiltInWidgets.kBooleanBox);

    SendableChooser<String> selectionsA = new SendableChooser<>();
    selectionsA.addOption("Trench", "Trench");
    selectionsA.addOption("Middle", "Middle");
    selectionsA.addOption("Opposing Trench", "Opposing Trench");
    display.add("Start Position Selector", selectionsA)
    .withPosition(5, 1).withSize(2, 1).withWidget(BuiltInWidgets.kComboBoxChooser);
    
    SendableChooser<String> selectionsB = new SendableChooser<>();
    selectionsB.addOption("Primary", "Primary");
    selectionsB.addOption("Secondary", "Secondary");
    selectionsB.addOption("Zero Turret", "Zero Turret");
    display.add("Mode Selector", selectionsB)
    .withPosition(5, 2).withSize(2, 1).withWidget(BuiltInWidgets.kComboBoxChooser);

    autoNameSupplier = () -> selectionsA.getSelected() + " " + selectionsB.getSelected();

    LiveWindow.disableAllTelemetry();
    SmartDashboard.delete("limelight_Interface");
    SmartDashboard.delete("limelight_Stream");
    SmartDashboard.delete("limelight_PipelineName");
    SmartDashboard.delete("Heartbeat");
  }

  public static GyroBase shuffleboardGyro(DoubleSupplier d) {
    return new GyroBase(){
      @Override public void close() {}
      @Override public void reset() {}
      @Override public double getRate() {return 0;}
      @Override public double getAngle() {return d.getAsDouble();}
      @Override public void calibrate() {}
    };
  }

  /**
   * Code to run when starting teleop mode.
   */
  public void teleopInit() {
  }

  /**
   * Code to run when starting autonomous mode.
   */
  public void autonomousInit() {
  }

  /**
   * Code to run when disabling the robot.
   */
  public void disabledInit() {
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autonomies.get(autoNameSupplier.get());
  }
}