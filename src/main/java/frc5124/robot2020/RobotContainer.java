/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.List;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj.GyroBase;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.trajectory.constraint.TrajectoryConstraint;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

import frc5124.robot2020.commands.*;

import frc5124.robot2020.commands.auto.ShootThreeBalls;
import frc5124.robot2020.commands.auto.RunDistanceForward;
import frc5124.robot2020.commands.auto.SixBallAuto;
import frc5124.robot2020.commands.auto.SixBallAuto;
import frc5124.robot2020.commands.auto.runpos.*;
import edu.wpi.first.wpilibj2.command.Command;


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
    operatorBack.whileHeld(new ReverseBeltWithIntakeAndShooter(shooter, loader, intake));
    operatorX.whileHeld(new LoaderAndIntakeGroup(intake, loader));
    operatorA.whenPressed(new ToggleIntakePivot(intake));
    operatorB.toggleWhenPressed(new TurretTargetByPIDPerpetually(turret));
    operatorRight.whileHeld(new RotateTurret(turret, false));
    operatorLeft.whileHeld(new RotateTurret(turret, true));
    operatorRB.toggleWhenPressed(new RPMbyFF(shooter, loader, 4400));
    operatorLB.toggleWhenPressed(new RPMbyFF(shooter, loader, 4950));
    operatorY.whileHeld(new RunLoader(loader));
    operatorUp.whileHeld(new LiftUp(hanger) );  
    operatorDown.whileHeld(new LiftDown(hanger) );  

   
  }

  private void configureDefaultCommands(){
    driveTrain.setDefaultCommand(new JoystickTankDrive(driverLeft, driverRight, driveTrain));
    //turret.setDefaultCommand(new TurretFindHomeDefault(turret));
    
  }


  private void configureShuffleboard() {
    display = Shuffleboard.getTab("Driving Display");
    Shuffleboard.selectTab(display.getTitle());

    // display.addBoolean("Intake Running?", intake::isRunning)
    // .withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kBooleanBox);
    display.addBoolean("Limelight On?",
    () -> NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").getDouble(-1) == 0
    ).withPosition(0, 1).withSize(1, 1).withWidget(BuiltInWidgets.kBooleanBox);
    display.addBoolean("Target Acquired?",
    () -> NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1
    ).withPosition(0, 2).withSize(1, 1).withWidget(BuiltInWidgets.kBooleanBox);
    display.addBoolean("Shooter On?", shooter::active)
    .withPosition(0, 3).withSize(1, 1).withWidget(BuiltInWidgets.kBooleanBox);
    display.addNumber("Number of Balls in Loader (Stub)", () -> 0)
    .withPosition(0, 4).withSize(1, 1);

    //new LocationUpdaterCommand(driveTrain, xSlider, ySlider).schedule();
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
    DifferentialDriveVoltageConstraint autoVoltageConstraint = new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(RobotMap.DriveTrainMap.kS, RobotMap.DriveTrainMap.kV,RobotMap.DriveTrainMap.kA
  ), driveTrain.getKinematics(), 10);

  TrajectoryConfig config = new TrajectoryConfig(RobotMap.DriveTrainMap.kMaxSpeedInchesPerSecond, RobotMap.DriveTrainMap.kMaxAccelerationInchesPerSecondSquared).setKinematics(driveTrain.getKinematics()).addConstraint(autoVoltageConstraint);

  Trajectory path = TrajectoryGenerator.generateTrajectory(new Pose2d(0,0, new Rotation2d(0)), List.of(new Translation2d(0, 10)), new Pose2d(0, 0, new Rotation2d(0)), config);

//  return new Pathing(path, driveTrain).andThen(() -> driveTrain.tankDrive(0, 0));
    return new SixBallAuto(turret, loader, shooter, driveTrain, intake);
  }
}