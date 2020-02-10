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
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.subsystems.Turret;

public class returnTurretToStart extends CommandBase {
  private Turret turret;
  private CANPIDController turretPID;
  private boolean isDone = false;

  /**
   * Creates a new returnTurretToStart
   */
  public returnTurretToStart(Turret subsystem) {
    turret = subsystem;
    addRequirements(turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    turretPID = turret.turretPID;
    turretPID.setP(RobotMap.TurretMap.Kp);
    turretPID.setI(0);
    turretPID.setD(0);
    turretPID.setReference(turret.getEncoder(), ControlType.kPosition); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    turretPID.setReference(0, ControlType.kPosition);
    if (turret.getEncoder() >= turret.startPosition){
      isDone = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //sets turret motor and encoder to 0
    turret.rotateTurret(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
