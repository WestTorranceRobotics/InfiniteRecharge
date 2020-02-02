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
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

import edu.wpi.first.wpilibj2.command.Command;
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
  private Intake intake;
  private Loader loader;
  private Shooter shooter;
  private Turret turret;

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
    OI.panelControllerDeployer.whenPressed(new PanelControllerToggleDeployed(panelController));
    OI.positionControl.whenPressed(new PositionControl(panelController));
    OI.rotationControl.whenPressed(new RotationControl(panelController));
  }

  private void configureDefaultCommands(){
    driveTrain.setDefaultCommand(new JoystickTankDrive(OI.driver, driveTrain));
  }

  private void configureShuffleboard() {
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

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new AutonomousCommand();
  }
}