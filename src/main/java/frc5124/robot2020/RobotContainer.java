/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020;

import java.util.Map;
import java.util.function.Consumer;
import java.awt.Color;
import com.revrobotics.ColorSensorV3.RawColor;
<<<<<<< HEAD
import edu.wpi.first.networktables.NetworkTableEntry;
import java.util.function.DoubleSupplier;
import edu.wpi.first.networktables.NetworkTableEntry;
=======
import edu.wpi.first.networktables.NetworkTableEntry;
import java.util.function.DoubleSupplier;
//import edu.wpi.first.networktables.NetworkTableEntry;
>>>>>>> c28850f205118727f29ffec768853a1d465f94b4
import edu.wpi.first.wpilibj.GyroBase;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
<<<<<<< HEAD
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

import frc5124.robot2020.commands.*;
import frc5124.robot2020.commands.RunPos.RunToPosition;
import frc5124.robot2020.commands.RunPos.TurnToAngle;
import frc5124.robot2020.commands.RunPos.resetGyro;
=======
import edu.wpi.first.wpilibj2.command.Command;

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
>>>>>>> c28850f205118727f29ffec768853a1d465f94b4
import frc5124.robot2020.subsystems.*;

//import frc5124.robot2020.subsystems.PanelController.OutputColor;




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
  
  public JoystickButton operatorA = new JoystickButton(operator, 1);
  public JoystickButton operatorB = new JoystickButton(operator, 2);
  public JoystickButton operatorX = new JoystickButton(operator, 3);
  public JoystickButton operatorY = new JoystickButton(operator, 4);
  public JoystickButton operatorLB = new JoystickButton(operator, 5);
  public JoystickButton operatorRB = new JoystickButton(operator, 6);

  public POVButton operatorUp = new POVButton(operator, 0);
  public POVButton operatorDown = new POVButton(operator, 180);
  public POVButton operatorRight = new POVButton(operator, 90);

  public final JoystickButton panelControllerDeployer = new JoystickButton(operator, XboxController.Button.kA.value);
  public final JoystickButton rotationControl = new JoystickButton(operator, XboxController.Button.kB.value);
  public final JoystickButton positionControl = new JoystickButton(operator, XboxController.Button.kX.value);
  
  private NetworkTableEntry shuffleboardButtonBooleanEntry;


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureSubsystems();
    configureShuffleboard();
    configureDefaultCommands();
    configureButtonBindings();    
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

  private void configureDefaultCommands(){
    operatorX.whileHeld(new setIntakePower(intake, RobotMap.IntakeMap.motorPower));
    operatorA.whileHeld(new IntakePivotDown(intake));
    operatorY.whileHeld(new IntakePivotUp(intake));
    operatorUp.whileHeld(new LiftUp(hanger));
    operatorDown.whileHeld(new LiftDown(hanger));   
    operatorRB.whileHeld(new RotateTurret(turret, RobotMap.TurretMap.turretSpeed));
    operatorLB.whileHeld(new RotateTurret(turret, -RobotMap.TurretMap.turretSpeed));
    operatorUp.whenPressed(new setShootVelocity(shooter, RobotMap.ShooterMap.shootVelocity));


    driveTrain.setDefaultCommand(new JoystickTankDrive(driverLeft, driverRight, driveTrain));
    
  }


  private void configureShuffleboard() {
    display = Shuffleboard.getTab("Driving Display");
    shuffleboardButtonBooleanEntry = display.add("Button Boolean", false).getEntry();

    ShuffleboardLayout poseLayout = display.getLayout("Pose", BuiltInLayouts.kGrid).withSize(3, 2).withPosition(1, 0);
    ShuffleboardLayout xyLayout = poseLayout.getLayout("Location", BuiltInLayouts.kGrid);
    NetworkTableEntry xSlider = xyLayout.add("Position X Inches", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    NetworkTableEntry ySlider = xyLayout.add("Position Y Inches", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    ShuffleboardLayout pIDlLayout = display.getLayout("Controller", BuiltInLayouts.kGrid).withSize(3,3).withPosition(4,0);
    NetworkTableEntry Motor = pIDlLayout.add("Motor speed", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    NetworkTableEntry pIDController = pIDlLayout.add("PID Controller", 0).withWidget(BuiltInWidgets.kPIDController).getEntry();
    poseLayout.add("Rotation", shuffleboardGyro(() -> 90 - driveTrain.getLocation().getRotation().getDegrees()))
      .withWidget(BuiltInWidgets.kGyro).withSize(3, 3).withPosition(3, 0);
      
    display.add("time", shuffleboardGyro(() -> System.currentTimeMillis()/1000)).withWidget(BuiltInWidgets.kGyro).withSize(3,3).withPosition(8,0);
    
    new LocationUpdaterCommand(driveTrain, xSlider, ySlider).schedule();
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
    return null;
  }
}