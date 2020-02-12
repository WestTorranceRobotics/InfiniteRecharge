/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;

import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.DriveTrain;
import frc5124.robot2020.subsystems.Turret;

public class returnTurretToStart extends CommandBase {
  private Turret m_turret;
  private CANPIDController turretPID;
  private boolean isDone = false;
  private DigitalInput x;

  /**
   * Creates a new returnTurretToStart.
   */
  public returnTurretToStart(Turret subsystem) {
    m_turret = subsystem;
    addRequirements(m_turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    // if(turret.getMagnetSensor().get()){
    //   turret.setPower(0.3);
    // }
    // else{
    // }

    // SmartDashboard.putBoolean("IS DONE", turret.getMagnetSensor().get());

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //sets turret motor and encoder to 0
    m_turret.getMotor().set(0);
   // turret.getEncoder().setPosition(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
