/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
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