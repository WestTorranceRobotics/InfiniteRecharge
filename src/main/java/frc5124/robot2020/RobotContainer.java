/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
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
  public Intake intake;
  private Loader loader;
  private Shooter shooter;
  private Turret turret;

  public XboxController operator = new XboxController(2);
  public Joystick driver = new Joystick(0);
  public JoystickButton operatorX = new JoystickButton(operator, 3);
  public JoystickButton operatorB = new JoystickButton(operator, 2);
  public JoystickButton operatorA = new JoystickButton(operator, 1);
  public JoystickButton operatorY = new JoystickButton(operator, 4);

  public JoystickButton operatorRB = new JoystickButton(operator, 6);
  public JoystickButton operatorLB = new JoystickButton(operator, 5);

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
    controlPanel = new ControlPanel();
    driveTrain = new DriveTrain();
    hanger = new Hanger();
    intake = new Intake();
    loader = new Loader();
    shooter = new Shooter();
    turret = new Turret();
  }

  private void configureButtonBindings(){
      operatorX.whileHeld(new IntakeBall(intake));
      operatorB.whileHeld(new OuttakeBall(intake));
      operatorA.whileHeld(new IntakePivotDown(intake));
      operatorY.whileHeld(new IntakePivotUp(intake));
      operatorLB.whileHeld(new LiftUp(hanger));
      operatorRB.whileHeld(new LiftDown(hanger));
  }

  private void configureDefaultCommands(){
    //driveTrain.setDefaultCommand(new JoystickTankDrive(OI.driver, driveTrain));
  }

  private void configureShuffleboard() {
    ShuffleboardTab display = Shuffleboard.getTab("Driving Display");
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