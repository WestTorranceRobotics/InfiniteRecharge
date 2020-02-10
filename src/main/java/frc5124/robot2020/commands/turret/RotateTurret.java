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

public class RotateTurret extends CommandBase {
  private Turret turret;
  private boolean isDone = false;
  /**
   * Creates a new RotateTurret.
   * @param power Useable if limit not reached. Suggest moving by units (not coded yet)
   */
  public RotateTurret(Turret subsystem) {
    turret = subsystem;
    addRequirements(turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    turret.getMotor().getEncoder().setPosition(50);
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  // Need encoder position limits to finish coding
  @Override
  public void execute() {
  
    if(turret.getMagnetSensor().get()){
    
      turret.setPower(0.3);
    
    }

    else if(!turret.getMagnetSensor().get()){
      turret.setPower(0);
    }
    else if(turret.getDegrees() < 1 && turret.getDegrees() > -1){

    }

    SmartDashboard.getBoolean("ON?", turret.getMagnetSensor().get());
    SmartDashboard.getNumber("Degrees", turret.getDegrees());
    SmartDashboard.updateValues();

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
