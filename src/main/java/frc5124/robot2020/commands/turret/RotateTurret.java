/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RotateTurret extends CommandBase {
  private Turret turret;
  private double power;
  /**
   * Creates a new RotateTurret.
   * @param power Useable if limit not reached. Suggest moving by units (not coded yet)
   */
  public RotateTurret(Turret subsystem, double power) {
    turret = subsystem;
    addRequirements(turret);
    this.power = power;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    turret.directPower(power);
  }

  // Called every time the scheduler runs while the command is scheduled.
  // Need encoder position limits to finish coding
  @Override
  public void execute() {
    SmartDashboard.putNumber("Degrees", turret.getDegrees());
    SmartDashboard.updateValues();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.directPower(0);
    //turret.enableTurretPID();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}