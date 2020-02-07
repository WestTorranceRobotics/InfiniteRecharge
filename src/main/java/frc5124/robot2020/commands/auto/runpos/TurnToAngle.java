/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto.runpos;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.subsystems.DriveTrain;

import java.util.Set;

import edu.wpi.first.wpilibj.controller.PIDController;

public class TurnToAngle  extends CommandBase {
  /**
   * Creates a new TurnToAngle.
   */
  private DriveTrain m_driveTrain;
  private double m_x;
  private double m_y;
  private double targetAngle;
  private boolean isDone = false;
  private int isClockwise = 0;

  public TurnToAngle(DriveTrain driveTrain, double x, double y) {
    m_driveTrain = driveTrain;
    m_x = x;
    m_y = y;

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    targetAngle = Math.toDegrees(Math.atan2(m_y, m_x));

    m_driveTrain.getGyroScope().reset();

    m_driveTrain.getGyroScope().zeroYaw();

    if (m_x < 0 && m_y < 0) {
      isClockwise = 1;
    }
    if (m_x < 0 && m_y > 0){
      isClockwise = 1;
    }

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

     if(isClockwise == 0){
      if(m_driveTrain.getGryoDegree() < targetAngle){
        m_driveTrain.arcadeDrive(0, 0.3);
    }
      else{
        isDone = true;
      }
    }
    else if (isClockwise == 1){
      if(m_driveTrain.getGryoDegree() > -targetAngle){
        m_driveTrain.arcadeDrive(0, -0.3);
      }
      else{
        isDone = true;
      } 
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    isDone = false;

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }

  @Override
  public Set<Subsystem> getRequirements() {
    // TODO Auto-generated method stub
    return null;
  }
}
