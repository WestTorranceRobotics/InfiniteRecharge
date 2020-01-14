/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc5124.robot2020.commands.AutonomousCommand;
import frc5124.robot2020.subsystems.Camera;
import frc5124.robot2020.subsystems.ControlPanel;
import frc5124.robot2020.subsystems.DriveTrain;
import frc5124.robot2020.subsystems.Hanger;
import frc5124.robot2020.subsystems.Intake;
import frc5124.robot2020.subsystems.Loader;
import frc5124.robot2020.subsystems.Shooter;
import frc5124.robot2020.subsystems.Turret;


// import static frc5124.robot2020.Constants.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
  * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  
  public final DriveTrain driveTrain = new DriveTrain();
  
  
  public final static Command autonomousCommand = new AutonomousCommand();
  public final OI oi = new OI();
  public final Camera camera = new Camera();
  public final ControlPanel controlPanel = new ControlPanel();
  public final DriveTrain drivetrain = new DriveTrain();
  public final Hanger hanger = new Hanger();
  public final Intake intake = new Intake();
  public final Loader loader = new Loader();
  public final Shooter shooter = new Shooter();
  public final Turret turret = new Turret();

  private final XboxController driver1 = new XboxController(0);
  private final Joystick driver2 =  new Joystick(1);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureDefaultCommands();
    configureButtonBindings();    
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings(){

  }

  private void configureDefaultCommands(){
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public static Command getAutonomousCommand() {
    return null;
  }
}