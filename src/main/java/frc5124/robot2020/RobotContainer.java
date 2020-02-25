/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020;

import edu.wpi.first.networktables.NetworkTableEntry;

import java.util.List;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj.GyroBase;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

import frc5124.robot2020.commands.*;
import frc5124.robot2020.commands.auto.runpos.DriveForTime;
import frc5124.robot2020.commands.auto.runpos.ShooterAndLoaderRev;
import frc5124.robot2020.commands.auto.runpos.TargetShootAuto;
import frc5124.robot2020.commands.auto.runpos.Turn180;
import frc5124.robot2020.commands.auto.runpos.turretZero;
import edu.wpi.first.wpilibj2.command.Command;

import frc5124.robot2020.commands.driveTrain.*;
import frc5124.robot2020.commands.intake.*;
import frc5124.robot2020.commands.loader.RunLoader;
import frc5124.robot2020.commands.shooter.*;
import frc5124.robot2020.commands.turret.*;
import frc5124.robot2020.subsystems.*;

import frc5124.robot2020.subsystems.PanelController.OutputColor;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
  * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */

public class RobotContainer {

  private Camera camera;
  private PanelController panelController;
  private Intake intake;
  private Hanger hanger;
  private DriveTrain driveTrain;
  private Shooter shooter; 
  private Turret turret;
  private Loader loader;

  public static final Joystick driverLeft = new Joystick(0);
  public static final Joystick driverRight = new Joystick(1);
  public XboxController operator = new XboxController(2);
  
  public JoystickButton operatorA = new JoystickButton(operator, XboxController.Button.kA.value);
  public JoystickButton operatorB = new JoystickButton(operator, XboxController.Button.kB.value);
  public JoystickButton operatorX = new JoystickButton(operator, XboxController.Button.kX.value);
  public JoystickButton operatorY = new JoystickButton(operator, XboxController.Button.kY.value);
  public JoystickButton operatorLB = new JoystickButton(operator, XboxController.Button.kBumperLeft.value);
  public JoystickButton operatorRB = new JoystickButton(operator, XboxController.Button.kBumperRight.value);
  public JoystickButton operatorBack = new JoystickButton(operator, XboxController.Button.kBack.value);
  public JoystickButton operatorStart = new JoystickButton(operator,XboxController.Button.kStart.value);
  public JoystickButton operatorTest = new JoystickButton(operator, XboxController.Button.kBack.value);
  public JoystickButton operatorStickLeft = new JoystickButton(operator, XboxController.Button.kStickLeft.value);
  public JoystickButton operatorStickRight = new JoystickButton(operator, XboxController.Button.kStickRight.value);

  public POVButton operatorUp = new POVButton(operator, 0);
  public POVButton operatorDown = new POVButton(operator, 180);
  public POVButton operatorRight = new POVButton(operator, 90);
  public POVButton operatorLeft = new POVButton(operator, 270);
  
  private NetworkTableEntry shuffleboardButtonBooleanEntry;
  private ShuffleboardTab display;

  public RobotContainer() {
    configureSubsystems();
    configureButtonBindings();
    configureShuffleboard();
    configureDefaultCommands();
    
  }

  private void configureSubsystems() {
    camera = new Camera();
    panelController = new PanelController();
    intake = new Intake();
    hanger = new Hanger();
    loader = new Loader();
    driveTrain = new DriveTrain();
    shooter = new Shooter();
    turret = new Turret();
  }

  private void configureButtonBindings(){
    operatorStart.whileHeld(new SetIntakePower(intake, -.6));
    operatorBack.whileHeld(new ReverseBeltWithIntake(loader, intake));
    operatorX.whileHeld(new LoaderAndIntakeGroup(intake, loader));
    operatorA.whenPressed(new ToggleIntakePivot(intake));
    operatorB.toggleWhenPressed(new RotateTurret(turret, operatorRight, operatorLeft)).whenInactive(new TurretTargetByPIDPerpetually(turret));
    //operatorDown.whileHeld(new LiftDown(hanger));
    //operatorY.whileHeld(new RunLoader(loader));
    operatorRB.toggleWhenPressed(new ShootFromLine(shooter, loader));
    operatorLB.toggleWhenPressed(new ShootFromTrench(shooter, loader));
    operatorUp.toggleWhenPressed(new toggleLimeLight()); 
    operatorDown.toggleWhenPressed(new ShootFromMidTrench(shooter, loader));   
  }

  private void configureDefaultCommands(){
    driveTrain.setDefaultCommand(new JoystickTankDrive(driverLeft, driverRight, driveTrain));
  }


  private void configureShuffleboard() {
    display = Shuffleboard.getTab("Driving Display");
    shuffleboardButtonBooleanEntry = display.add("Button Boolean", false).getEntry();

    ShuffleboardLayout poseLayout = display.getLayout("Pose", BuiltInLayouts.kGrid).withSize(3, 2).withPosition(1, 0);
    ShuffleboardLayout xyLayout = poseLayout.getLayout("Location", BuiltInLayouts.kGrid);
    NetworkTableEntry xSlider = xyLayout.add("Position X Inches", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    NetworkTableEntry ySlider = xyLayout.add("Position Y Inches", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    poseLayout.add("Rotation", shuffleboardGyro(() -> 90 - driveTrain.getLocation().getRotation().getDegrees()))
      .withWidget(BuiltInWidgets.kGyro).withSize(3, 3).withPosition(3, 0);
      
    display.add("time", shuffleboardGyro(() -> System.currentTimeMillis()/1000)).withWidget(BuiltInWidgets.kGyro).withSize(3,3).withPosition(8,0);
    //new LocationUpdaterCommand(driveTrain, xSlider, ySlider).schedule();
  }

  private GyroBase shuffleboardGyro(DoubleSupplier d) {
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
    return new TargetShootAuto(shooter, loader, turret, driveTrain);
    //TargetShootAuto(shooter, loader, turret, driveTrain);
  }
}