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

public class returnTurretToStart extends CommandBase {
  private Turret turret;
  private CANPIDController turretPID;
  private boolean isDone = false;

  /**
   * Creates a new returnTurretToStart.
   */
  public returnTurretToStart(Turret subsystem) {
    turret = subsystem;
    addRequirements(turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    turretPID = turret.getMotor().getPIDController();
    turretPID.setP(.0004);
    turretPID.setI(0);
    turretPID.setD(0);
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(turret.getEncoderCountsPerRevolution() < 0){
      turret.getMotor().set(0.3);
    }
    if(turret.getEncoderCountsPerRevolution() > 0){
      turret.getMotor().set(-0.3);
    }
    else{
      isDone = true;
    }
    

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //sets turret motor and encoder to 0
    turret.getMotor().set(0);
   // turret.getEncoder().setPosition(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}