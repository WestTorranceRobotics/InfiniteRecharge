/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;

import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;

public class HomeTurret extends CommandBase {
  private Turret turret;
  private double initDeg;
  private boolean finished;

  /**
   * Creates a new returnTurretToStart.
   */
  public HomeTurret(Turret subsystem) {
    turret = subsystem;
    addRequirements(turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initDeg = turret.getDegrees();
    finished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // if (turret.isHome() || Math.abs(turret.getDegrees() - initDeg) > 30) {
    //   turret.directPower(0);
    //   finished = true;
    //   turret.setLimits();
    // } else {
    //   turret.directPower(0.05);
    // }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.getMotor().set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}