/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret.turretGroups;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;

public class setTurretDegrees extends CommandBase {
  private Turret m_turret;
  private double degrees;
  /**
   * Creates a new setTurretDegrees.
   */
  public setTurretDegrees(Turret subsystem, double degrees) {
    m_turret = subsystem;
    addRequirements(m_turret);
    this.degrees = degrees;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_turret.setTurretDegrees(degrees);
    // SmartDashboard.putNumber("setP", 0);
    // SmartDashboard.putBoolean("update", false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  // if (SmartDashboard.getBoolean("update", true)){
  //   m_turret.setTurretDegrees(SmartDashboard.getNumber("setPoint", 0));
  //   //m_turret.updateCoeffs();
  // }
  //     SmartDashboard.putNumber("Deg", m_turret.getDegrees());
  //   SmartDashboard.putBoolean("update", false);
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
