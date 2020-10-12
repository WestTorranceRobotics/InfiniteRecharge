/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.driveTrain;

import java.util.Set;

import edu.wpi.first.networktables.NetworkTableEntry;
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

  public LocationUpdaterCommand(DriveTrain driveTrain, NetworkTableEntry xSlider, NetworkTableEntry ySlider) {
    this.driveTrain = driveTrain;
    this.xSlider = xSlider;
    this.ySlider = ySlider;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    xSlider.setDouble(driveTrain.getLocation().getTranslation().getX());
    ySlider.setDouble(driveTrain.getLocation().getTranslation().getY());

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public Set<Subsystem> getRequirements() {
    // TODO Auto-generated method stub
    return null;
  }
}