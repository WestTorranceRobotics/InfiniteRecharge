/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020;

import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.NetworkTableEntry;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import frc5124.robot2020.commands.RunPos.RunToPosition;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc5124.robot2020.commands.*;
import frc5124.robot2020.subsystems.*;


/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
  * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private Camera camera;
  private DriveTrain driveTrain;
  private Hanger hanger;
  public Intake intake;
  private Loader loader;
  private Shooter shooter; 
  private Turret turret;


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
  
  public ShuffleboardTab display;
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
    driveTrain = new DriveTrain();
    hanger = new Hanger();
    intake = new Intake();
    loader = new Loader();
    //shooter = new Shooter();
    turret = new Turret();
  }

  private void configureButtonBindings(){
  }

  private void configureDefaultCommands(){
    driveTrain.setDefaultCommand(new RunToPosition(driveTrain,1,1));
    // operatorRB.whileHeld(new IntakeBall(intake));
    // operatorLB.whileHeld(new OuttakeBall(intake));
    // operatorA.whileHeld(new IntakePivotDown(intake));
    // operatorY.whileHeld(new IntakePivotUp(intake));
    // operatorUp.whileHeld(new LiftUp(hanger));
    // operatorDown.whileHeld(new LiftDown(hanger));
    // operatorRight.whileHeld(new TurretTurn(turret));
    // driveTrain.setDefaultCommand(new JoystickTankDrive(driverLeft, driverRight, driveTrain));
    // shooter.setDefaultCommand(new ShootHold(shooter));
  }

  private void configureShuffleboard() {
    // display = Shuffleboard.getTab("Driving Display");
    // shuffleboardButtonBooleanEntry = display.add("Button Boolean", false).getEntry();

    // ShuffleboardLayout poseLayout = display.getLayout("Pose", BuiltInLayouts.kGrid).withSize(3, 2).withPosition(1, 0);
    // ShuffleboardLayout xyLayout = poseLayout.getLayout("Location", BuiltInLayouts.kGrid);
    // NetworkTableEntry xSlider = xyLayout.add("Position X Inches", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    // NetworkTableEntry ySlider = xyLayout.add("Position Y Inches", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    // ShuffleboardLayout pIDlLayout = display.getLayout("Controller", BuiltInLayouts.kGrid).withSize(3,3).withPosition(4,0);
    // NetworkTableEntry Motor = pIDlLayout.add("Motor speed", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    // NetworkTableEntry pIDController = pIDlLayout.add("PID Controller", 0).withWidget(BuiltInWidgets.kPIDController).getEntry();
    // poseLayout.add("Rotation", shuffleboardGyro(() -> 90 - driveTrain.getLocation().getRotation().getDegrees()))
    //   .withWidget(BuiltInWidgets.kGyro).withSize(3, 3).withPosition(3, 0);
      
    // display.add("time", shuffleboardGyro(() -> System.currentTimeMillis()/1000)).withWidget(BuiltInWidgets.kGyro).withSize(3,3).withPosition(8,0);
    
    // new LocationUpdaterCommand(driveTrain, xSlider, ySlider).schedule();
  }

  // private GyroBase shuffleboardGyro(DoubleSupplier d) {
  //   // return new GyroBase(){
  //   //   @Override public void close() {}
  //   //   @Override public void reset() {}
  //   //   @Override public double getRate() {return 0;}
  //   //   @Override public double getAngle() {return d.getAsDouble();}
  //   //   @Override public void calibrate() {}
  //   // };
  // }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new AutonomousCommand(driveTrain);
  }
}