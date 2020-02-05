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
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

import frc5124.robot2020.commands.*;
import frc5124.robot2020.commands.RunPos.RunToPosition;
import frc5124.robot2020.commands.RunPos.TurnToAngle;
import frc5124.robot2020.commands.RunPos.resetGyro;
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
    shooter = new Shooter();
    turret = new Turret();
  }

  private void configureButtonBindings(){

    //operatorA.whenActive(new TurnToAngle(driveTrain, 10,10));

  }


  private void configureShuffleboard() {
    ShuffleboardTab display = Shuffleboard.getTab("Driving Display");
    shuffleboardButtonBooleanEntry = display.add("Button Boolean", false).getEntry();
    
    
    // ShuffleboardLayout poseLayout = display.getLayout("Pose", BuiltInLayouts.kGrid).withSize(3, 2).withPosition(1, 0);
    // ShuffleboardLayout xyLayout = poseLayout.getLayout("Location", BuiltInLayouts.kGrid);
    // NetworkTableEntry xSlider = xyLayout.add("Position X Inches", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    // NetworkTableEntry ySlider = xyLayout.add("Position Y Inches", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    // ShuffleboardLayout pIDlLayout = display.getLayout("Controller", BuiltInLayouts.kGrid).withSize(3,3).withPosition(4,0);
    // NetworkTableEntry Motor = pIDlLayout.add("Motor speed", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
      
    // display.add("time", shuffleboardGyro(() -> System.currentTimeMillis()/1000)).withWidget(BuiltInWidgets.kGyro).withSize(3,3).withPosition(8,0);
    
    // new LocationUpdaterCommand(driveTrain, xSlider, ySlider).schedule();
  

    // ShuffleboardLayout colorReader = display.getLayout("Control Panel Color", BuiltInLayouts.kList)
    //   .withPosition(0, 2).withSize(3, 2);
    // SimpleWidget colorWidget = colorReader.add("Control Panel Color", true)
    //   .withWidget(BuiltInWidgets.kBooleanBox);
    // NetworkTableEntry colorNumbersEntry = colorReader.add("Raw Color Values", "Red: 0, Green: 0, Blue: 0").getEntry();
    // NetworkTableEntry colorAnswer = colorReader.add("Answer", "Nothing").getEntry();
    // Consumer<OutputColor> colorDisplayer = (incolor) -> {
    //   RawColor color = incolor.value;
    //   float max = Math.max(Math.max(color.red, color.green), color.blue);
    //   Color normalized = new Color(color.red / max, color.green / max, color.blue / max);
    //   colorWidget.withProperties(Map.of("Color when true", 0xFF + 256 * normalized.getRGB()));
    //   colorNumbersEntry.setString(
    //     "Red: " + color.red + ", Green: " + color.green +
    //     ", Blue: " + color.blue + ", IR: " + color.ir
    //   );
    //   colorAnswer.setString(incolor.choice == null ? "NOTHING" : incolor.choice.name());
    // };
    // new ColorDisplayer(panelController, colorDisplayer).schedule();
  }

  private void configureDefaultCommands(){
    // operatorRB.whileHeld(new IntakeBall(intake));
    // operatorLB.whileHeld(new OuttakeBall(intake));
    // operatorA.whileHeld(new IntakePivotDown(intake));
    // operatorY.whileHeld(new IntakePivotUp(intake));
    // operatorUp.whileHeld(new LiftUp(hanger));
    // operatorDown.whileHeld(new LiftDown(hanger));
    // operatorRight.whileHeld(new TurretTurn(turret));
    // driveTrain.setDefaultCommand(new JoystickTankDrive(driverLeft, driverRight, driveTrain));
     driveTrain.setDefaultCommand(new RunToPosition(driveTrain, 1,1));
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
  // public Command getAutonomousCommand() {
  //   //return new AutonomousCommand(driveTrain);
  // }
}