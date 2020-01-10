/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
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
  // The robot's subsystems
  private final DriveTrain m_driveTrain = new DriveTrain();
  private final Intake m_intake = new Intake();
  // private final Loader m_loader = new Loader();
  private final Shooter m_shooter = new Shooter();
  private final Turret m_turretIntake = new Turret();
  // private final Hanger m_hanger = new Hanger();
  // private final ControlPanelInterface m_controlPanelInterface = new ControlPanelInterface();

  private final XboxController m_driver = new XboxController(0);
  private final Joystick m_operator =  new Joystick(1);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Configure the button bindings
    configureButtonBindings();

    // Configure Shuffleboard
    configureShuffleboard();

    // Configure default commands
    // Set the default drive command to tank drive
    m_driveTrain.setDefaultCommand(new DrivewithJoysticksCommand(m_driver, m_driveTrain));

            // SmartDashboard Buttons
            /*
            SmartDashboard.putData("Deploy Intake Command", new DeployIntakeCommand());
            SmartDashboard.putData("Run Intake Command", new RunIntakeCommand());
            SmartDashboard.putData("Retract Intake Command", new RetractIntakeCommand());
            SmartDashboard.putData("Run Out Intake Command", new RunOutIntakeCommand());
            SmartDashboard.putData("Shoot PowerCell Command", new ShootPowerCellCommand());
            SmartDashboard.putData("Shoot Multiple PowerCells Command Group", new ShootMultiplePowerCellsCommandGroup());
            SmartDashboard.putData("Run Loader Timed Command", new RunLoaderTimedCommand());
            */

            
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    new JoystickButton(m_operator, 1).whenPressed(new DeployIntakeCommand(m_intake));
    new JoystickButton(m_operator, 2).whenPressed(new RetractIntakeCommand(m_intake));
    new JoystickButton(m_operator, 3).whileHeld(new RunIntakeCommand(m_intake));
    new JoystickButton(m_operator, 4).whileHeld(new RunOutIntakeCommand(m_intake));

    new JoystickButton(m_driver, 1).whileHeld(new ShootPowerCellCommand(m_shooter));

    // Drive at full speed when the Start button is held
    new JoystickButton(m_driver, Button.kStart.value)
        .whileHeld(() -> m_driveTrain.tankDrive(1.0, 1.0), m_driveTrain)
        .whenReleased(() -> m_driveTrain.tankDrive(0, 0), m_driveTrain);


  }

  /**
   * Use this method to define your Shuffleboard command mappings.
   */
  private void configureShuffleboard() {
      /*
    // Show the driveTrain subsystem
    SmartDashboard.putData(m_driveTrain);

    // Commands to run from Shuffleboard
    SmartDashboard.putData("Drive5Sec", new Drive(0.6, 0.6, driveTrain).withTimeout(5) );
    SmartDashboard.putData("Drive2Sec", new DriveByTime(2.0, driveTrain) );
    SmartDashboard.putData("Stop", new Stop(driveTrain));
    */
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