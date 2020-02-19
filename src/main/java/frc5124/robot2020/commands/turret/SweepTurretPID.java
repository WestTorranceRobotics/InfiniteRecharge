/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;

public class SweepTurretPID extends CommandBase {
  private Turret turret;
  private double currentDegrees;
  private boolean clockwise;
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
    if(!turret.limitReached()) {
    currentDegrees = turret.getDegrees();
    if (clockwise) {
      turret.setTurretDegrees(currentDegrees + 1);
    } else if (!clockwise){
      turret.setTurretDegrees(currentDegrees - 1);
    }
  }

    if (!turret.limitReached()) {
    if (turret.getDegrees() > 170) {
      turret.limitReached(true);
      turret.setTurretDegrees(-165);
    } else if (turret.getDegrees() < -170) {
      turret.limitReached(true);
      turret.setTurretDegrees(165);
    }

    
    if(turret.limitReached()){
    if ((turret.getDegrees() > 160 && turret.getDegrees() < 166)|| (turret.getDegrees() < -160 && turret.getDegrees() > -166)) {
      turret.limitReached(false);
    }
    }

  }
    
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