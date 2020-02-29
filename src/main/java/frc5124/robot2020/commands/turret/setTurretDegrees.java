/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;

public class setTurretDegrees extends CommandBase {
  private Turret turret;
  private double degrees;
  /**
   * Creates a new setTurretDegrees.
   */
  public setTurretDegrees(Turret subsystem, double degrees) {
    turret = subsystem;
    this.degrees = degrees;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    turret.setTurretDegrees(degrees);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
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
}