/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;

public class SweepTurretPID extends CommandBase {
  private Turret turret;
  private double currentDegrees;
  private boolean clockwise;
  private boolean switchAround = false;
  /**
   * Creates a new setTurretDegrees.
   */
  public SweepTurretPID(Turret subsystem, boolean clockwise) {
    turret = subsystem;
    addRequirements(turret);
    this.clockwise = clockwise;
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      currentDegrees = turret.getDegrees();
      if (currentDegrees > 170) {
        switchAround = true;
        turret.rightLimitReached(true);
        turret.setTurretDegrees(-170);
      }
      else if (currentDegrees < -170) {
        switchAround = true;
        turret.leftLimitReached(true);
        turret.setTurretDegrees(170);
      }
      if (switchAround && turret.rightLimitReached()) {
        if (currentDegrees < -160) {
          switchAround = false;
          turret.rightLimitReached(false);
        }
      } else if (switchAround && turret.leftLimitReached()) {
        if (currentDegrees > 160) {
          switchAround = false;
          turret.leftLimitReached(false);
        }
      }

    if(!switchAround) {
      if (clockwise) {
        turret.setTurretDegrees(currentDegrees + 5);
      } else if (!clockwise){
        turret.setTurretDegrees(currentDegrees - 5);
      }
    }
    SmartDashboard.putNumber("IN CLASS DEGREES READ", currentDegrees);
    SmartDashboard.putNumber("degrees", turret.getDegrees());
    SmartDashboard.updateValues();

  
    
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