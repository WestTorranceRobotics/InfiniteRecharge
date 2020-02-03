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
import edu.wpi.first.networktables.NetworkTableEntry;
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
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.POVButton;

import frc5124.robot2020.commands.*;
import frc5124.robot2020.commands.panelcontrol.*;
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

  private DriveTrain driveTrain;
  private Hanger hanger;
  public Intake intake;
  private Loader loader;
  private Shooter shooter; 
  private Turret turret;

  
  public static final Joystick driver = new Joystick(0);
  public static final Joystick operator = new Joystick(1);

  public static final JoystickButton panelControllerDeployer = new JoystickButton(driver, XboxController.Button.kA.value);
  public static final JoystickButton rotationControl = new JoystickButton(driver, XboxController.Button.kB.value);
  public static final JoystickButton positionControl = new JoystickButton(driver, XboxController.Button.kX.value);

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
    panelController = new PanelController();
    driveTrain = new DriveTrain();
    hanger = new Hanger();
    intake = new Intake();
    loader = new Loader();
    shooter = new Shooter();
    turret = new Turret();
  }

  private void configureButtonBindings(){

    panelControllerDeployer.whenPressed(new PanelControllerToggleDeployed(panelController));
    positionControl.whenPressed(new PositionControl(panelController));
    rotationControl.whenPressed(new RotationControl(panelController));
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
    
    ShuffleboardTab display = Shuffleboard.getTab("Driving Display");
    Shuffleboard.selectTab(display.getTitle());

    ShuffleboardLayout colorReader = display.getLayout("Control Panel Color", BuiltInLayouts.kList)
      .withPosition(0, 2).withSize(3, 2);
    SimpleWidget colorWidget = colorReader.add("Control Panel Color", true)
      .withWidget(BuiltInWidgets.kBooleanBox);
    NetworkTableEntry colorNumbersEntry = colorReader.add("Raw Color Values", "Red: 0, Green: 0, Blue: 0").getEntry();
    NetworkTableEntry colorAnswer = colorReader.add("Answer", "Nothing").getEntry();
    Consumer<OutputColor> colorDisplayer = (incolor) -> {
      RawColor color = incolor.value;
      float max = Math.max(Math.max(color.red, color.green), color.blue);
      Color normalized = new Color(color.red / max, color.green / max, color.blue / max);
      colorWidget.withProperties(Map.of("Color when true", 0xFF + 256 * normalized.getRGB()));
      colorNumbersEntry.setString(
        "Red: " + color.red + ", Green: " + color.green +
        ", Blue: " + color.blue + ", IR: " + color.ir
      );
      colorAnswer.setString(incolor.choice == null ? "NOTHING" : incolor.choice.name());
    };
    new ColorDisplayer(panelController, colorDisplayer).schedule();
  }

  private void configureDefaultCommands(){
    operatorRB.whileHeld(new IntakeBall(intake));
    operatorLB.whileHeld(new OuttakeBall(intake));
    operatorA.whileHeld(new IntakePivotDown(intake));
    operatorY.whileHeld(new IntakePivotUp(intake));
    operatorUp.whileHeld(new LiftUp(hanger));
    operatorDown.whileHeld(new LiftDown(hanger));
    operatorRight.whileHeld(new TurretTurn(turret));
    driveTrain.setDefaultCommand(new JoystickTankDrive(driverLeft, driverRight, driveTrain));
    shooter.setDefaultCommand(new ShootHold(shooter));
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
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new AutonomousCommand(driveTrain);
  }
}