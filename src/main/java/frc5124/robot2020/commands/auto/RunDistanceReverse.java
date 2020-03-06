/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.DriveTrain;

public class RunDistanceReverse extends CommandBase {
  /**
   * Creates a new resetGyro.
   */
  private DriveTrain m_DriveTrain;
  private boolean isDone = false;
  private double targetCounts;
  private double distanceToDrive;
  private double countsPerInch = 1082.0;
  private double startingEncoderVal; 

  public RunDistanceReverse(DriveTrain driveTrain, double x) {
    m_DriveTrain = driveTrain;
    addRequirements(m_DriveTrain);
    distanceToDrive = x; 
    targetCounts = countsPerInch * distanceToDrive;
    // SmartDashboard.putNumber("ended", 0);
  }

  @Override
  public void initialize() {
    startingEncoderVal = m_DriveTrain.getLeftEncoderVal();
    isDone = false;
  }

  public void driveStraightToPoint(){ 
    m_DriveTrain.tankDrive(-.75, -.75);
    if (Math.abs(m_DriveTrain.getLeftEncoderVal() - startingEncoderVal) >= Math.abs(targetCounts)){
      m_DriveTrain.tankDrive(0, 0);
      isDone = true;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveStraightToPoint();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // SmartDashboard.putNumber("ended", 1);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
