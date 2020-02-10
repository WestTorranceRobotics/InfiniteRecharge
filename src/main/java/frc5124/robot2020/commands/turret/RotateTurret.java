/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;
public class RotateTurret extends CommandBase {
  private Turret turret;
  private double power;
  private boolean isDone = false;
  public RotateTurret(Turret subsystem, double power) {
    turret = subsystem;
    addRequirements(turret);
    this.power = power;
  }

  @Override
  public void initialize() {
  
  }

  @Override
  public void execute() {
    turret.rotateTurret(power);
  }

  @Override
  public void end(boolean interrupted) {
    turret.rotateTurret(0);
  }

  @Override
  public boolean isFinished() {
    return isDone;
  }
}
