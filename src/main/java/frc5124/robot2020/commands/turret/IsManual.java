/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;

public class IsManual extends CommandBase {
  private Turret subsystem;
  /**
   * Creates a new TurretTargetByPID.
   */
  public IsManual (Turret subsystem) {
  this.subsystem = subsystem;
  }

  public void initialize() {
    subsystem.isManual(true);
  }

  @Override
  public void execute() {
    // TODO Auto-generated method stub
   // subsystem.isManual(true);
  }

  @Override
  public void end(boolean interrupted) {
    subsystem.isManual(false);
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}