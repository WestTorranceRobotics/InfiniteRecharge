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
import edu.wpi.first.wpilibj2.command.button.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc5124.robot2020.commands.utility.debugFeed;

import frc5124.robot2020.commands.*;
import frc5124.robot2020.commands.auto.runpos.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc5124.robot2020.commands.driveTrain.*;
import frc5124.robot2020.commands.auto.*;
import frc5124.robot2020.commands.hanger.*;
import frc5124.robot2020.commands.intake.*;
import frc5124.robot2020.commands.loader.*;
import frc5124.robot2020.commands.shooter.*;
import frc5124.robot2020.commands.turret.*;
import frc5124.robot2020.commands.driveTrain.*;
import frc5124.robot2020.commands.panelcontrol.*;
import frc5124.robot2020.subsystems.*;
import frc5124.robot2020.commands.utility.debugFeed;

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
  
  /**
   * send debug information to shuffleboard for given subsystems
   */
  private boolean debugEnabled = true;
  private boolean debugGetTurret = true;
  private boolean debugGetCamera = true;
  private boolean debugGetDriveTrain = true;
  private boolean debugGetHanger = true;
  private boolean debugGetIntake = true;
  private boolean debugGetLoader = true;
  private boolean debugGetPanelController = true;
  private boolean debugGetShooter = true;
  private boolean debugGetLimelight = true;
  private int[] debugGet = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
 

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
    operatorBack.whileHeld(new SetIntakePower(intake, -.6));
    operatorStart.whileHeld(new ReverseBeltWithIntake(loader, intake));
    operatorX.toggleWhenPressed(new LoaderAndIntakeGroup(intake, loader));
    operatorA.whenPressed(new ToggleIntakePivot(intake));
    //operatorB.whileHeld(new ShooterAndLoader(shooter, loader));
    operatorY.whileHeld(new RunLoader(loader));
    // operatorUp.whileHeld(new LiftUp(hanger));
    // operatorDown.whileHeld(new LiftDown(hanger));   
    operatorRight.whileHeld(new SweepTurretPID(turret, true));
    operatorLeft.whileHeld(new SweepTurretPID(turret, true));
    operatorLB.toggleWhenPressed(new ShootFromTrench(shooter, loader));
    operatorRB.toggleWhenPressed(new ShootFromLine(shooter, loader));
  }

  private void configureDefaultCommands(){
    driveTrain.setDefaultCommand(new JoystickTankDrive(driverLeft, driverRight, driveTrain));
  }


  private void configureShuffleboard() {
    display = Shuffleboard.getTab("Driving Display");
    new debugFeed(debugInit(debugEnabled), shooter, turret, panelController, driveTrain, intake, hanger, camera, loader);

 
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
    return new DriveForTime(driveTrain, 5);
  }

  public int[] debugInit(boolean debug) {
    if(debug) {
    if (debugGetTurret) {debugGet[0] = 1;}
    if (debugGetShooter) {debugGet[1] = 1;}
    if (debugGetPanelController) {debugGet[2] = 1;}
    if (debugGetLoader) {debugGet[3] = 1;}
    if (debugGetIntake) {debugGet[4] = 1;}
    if (debugGetHanger) {debugGet[5] = 1;}
    if (debugGetDriveTrain) {debugGet[6] = 1;}
    if (debugGetCamera) {debugGet[7] = 1;}
    if (debugGetLimelight) {debugGet[8] = 1;}
    }
    return debugGet;
  }
}