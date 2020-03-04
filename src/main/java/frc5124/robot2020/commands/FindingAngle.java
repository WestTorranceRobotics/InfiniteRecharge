/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.DriveTrain;

public class FindingAngle extends CommandBase {
  /**
   * Creates a new resetGyro.
   */
  private DriveTrain m_DriveTrain;
  private double angleToTurn;

  public FindingAngle(DriveTrain driveTrain) {
    m_DriveTrain = driveTrain;
    addRequirements(m_DriveTrain);
  }

  public void updateSmartDashboard(){
    SmartDashboard.putNumber("yaw angle", m_DriveTrain.getYawAngle());
    SmartDashboard.putNumber("pitch angle", m_DriveTrain.getPitchAngle());
    SmartDashboard.putNumber("roll angle", m_DriveTrain.getRollAngle());
    SmartDashboard.updateValues();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      
    updateSmartDashboard();
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
