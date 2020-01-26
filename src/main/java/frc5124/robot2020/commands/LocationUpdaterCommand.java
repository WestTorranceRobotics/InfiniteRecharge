/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands;

import java.util.Set;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.subsystems.*;

public class LocationUpdaterCommand implements Command {
  /**
   * Creates a new LocationUpdaterCommand.
   */
  DriveTrain driveTrain;
  NetworkTableEntry xSlider;
  NetworkTableEntry ySlider;
  ComplexWidget rotation;

  public LocationUpdaterCommand(DriveTrain driveTrain, NetworkTableEntry xSlider, NetworkTableEntry ySlider,
      ComplexWidget rotation) {
    this.driveTrain = driveTrain;
    this.xSlider = xSlider;
    this.ySlider = ySlider;
    this.rotation = rotation;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.

  // Called every time the scheduler runs while the command is scheduled.
  public void execute() {
    xSlider.setDouble(driveTrain.getLocation().getTranslation().getY());
    ySlider.setDouble(driveTrain.getLocation().getTranslation().getY());
    

  }

  // Called once the command ends or is interrupted.
  @Override
  public boolean runsWhenDisabled() {
    return true;
  }

  @Override
  public Set<Subsystem> getRequirements() {
    // TODO Auto-generated method stub
    return Set.of();
  }
}
