/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;

public class returnTurretToStart extends CommandBase {
  private Turret turret;
  private CANPIDController turretPID;
  private boolean isDone = false;
  public returnTurretToStart(Turret subsystem) {
    turret = subsystem;
    addRequirements(turret);
  }

  @Override
  public void initialize() {
    turretPID = turret.turretPID;
    turretPID.setReference(turret.getEncoder(), ControlType.kPosition); 
  }

  @Override
  public void execute() {
    turretPID.setReference(0, ControlType.kPosition);
    if (turret.getEncoder() >= turret.startPosition){
      isDone = true;
    }
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
