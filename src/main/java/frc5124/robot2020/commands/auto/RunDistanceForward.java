/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.DriveTrain;

public class RunDistanceForward extends CommandBase {
  /**
   * Creates a new resetGyro.
   */
  private DriveTrain m_DriveTrain;
  private boolean isDone = false;
  private double targetCounts;
  private double distanceToDrive;
  private double countsPerInch = 1082.0;
  private double startingEncoderVal;
  private double dtPower;
  private double x;
  private double power;

  public 
  RunDistanceForward(DriveTrain driveTrain, double x, double power) {
    m_DriveTrain = driveTrain;
    addRequirements(m_DriveTrain);
    this.x = x;
    this.power = power;
  }

  @Override 
  public void initialize() {
    super.initialize();
    m_DriveTrain.resetEncoders();
    distanceToDrive = x; 
    targetCounts = countsPerInch * distanceToDrive;
    startingEncoderVal = m_DriveTrain.getLeftEncoderVal();
    dtPower = power;
  }

  public void driveStraightToPoint(){  // after it figures out the angle, it should just drive straight
      m_DriveTrain.tankDrive(dtPower, dtPower);
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
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
