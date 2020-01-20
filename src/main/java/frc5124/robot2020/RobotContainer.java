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
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import frc5124.robot2020.commands.*;
import frc5124.robot2020.subsystems.*;


// import static frc5124.robot2020.Constants.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
  * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  
  private Camera camera;
  private ControlPanel controlPanel;
  private DriveTrain driveTrain;
  private Hanger hanger;
  private Intake intake;
  private Loader loader;
  private Shooter shooter;
  private Turret turret;

  private NetworkTableEntry shuffleboardButtonBooleanEntry;

  public ShuffleboardTab display;

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
    controlPanel = new ControlPanel();
    driveTrain = new DriveTrain();
    hanger = new Hanger();
    intake = new Intake();
    loader = new Loader();
    shooter = new Shooter();
    turret = new Turret();
  }

  private void configureButtonBindings(){
    OI.isPressedButton
      .whenPressed(new SetShuffleBoolean(true, shuffleboardButtonBooleanEntry))
      .whenReleased(new SetShuffleBoolean(false, shuffleboardButtonBooleanEntry));
  }

  private void configureDefaultCommands(){
    driveTrain.setDefaultCommand(new JoystickTankDrive(OI.driver, driveTrain));
  }

  private void configureShuffleboard() {
    display = Shuffleboard.getTab("Driving Display");
    shuffleboardButtonBooleanEntry = display.add("Button Boolean", false).getEntry();

    ShuffleboardLayout poseLayout = display.getLayout("Pose", BuiltInLayouts.kGrid).withSize(3, 2).withPosition(1, 0);
    ShuffleboardLayout xyLayout = poseLayout.getLayout("Location", BuiltInLayouts.kGrid);
    NetworkTableEntry xSlider = xyLayout.add("Position X Inches", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    NetworkTableEntry ySlider = xyLayout.add("Position Y Inches", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    poseLayout.add("Rotation", shuffleboardGyro(() -> System.currentTimeMillis()/100 /*90 - driveTrain.getLocation().getRotation().getDegrees()*/))
      .withWidget(BuiltInWidgets.kGyro).withSize(3, 3).withPosition(3, 0);
    // new LocationUpdaterCommand(driveTrain, xSlider, ySlider).schaedule();
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
    return new AutonomousCommand();
  }
}