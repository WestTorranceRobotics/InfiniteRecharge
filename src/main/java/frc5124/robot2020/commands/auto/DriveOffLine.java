/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.DriveTrain;

public class DriveOffLine extends CommandBase {
  /**
   * Creates a new resetGyro.
   */
  private DriveTrain m_DriveTrain;
  private boolean yes;
  double targetCounts;
  double countsPerInch = 1082;

  public DriveOffLine(DriveTrain driveTrain, double distanceToMove) {
    m_DriveTrain = driveTrain;
    targetCounts  = (countsPerInch * distanceToMove);
    addRequirements(m_DriveTrain);
    yes = false;
  }

  public void driveDistance(){
    if (m_DriveTrain.leftEncoder() > targetCounts){
      m_DriveTrain.directPower(0);
    }
  }

  public void addToSmartDashBoard(){
    SmartDashboard.putNumber("Encoders", m_DriveTrain.leftEncoder());
    SmartDashboard.putNumber("TargetCounts", targetCounts);
    SmartDashboard.updateValues();
  }

  public void setPower(){
    m_DriveTrain.setLeftPower(0.5);
  }
  public void setNoPower(){
    m_DriveTrain.directPower(0);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_DriveTrain.resetEncoder();
    //driveDistance();
    setPower();
  }
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveDistance();
    //setPower();
    addToSmartDashBoard();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    setNoPower();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return yes;
  }
}
