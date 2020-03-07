/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.DriveTrain;

public class NewDriveDistance extends CommandBase {

  private double distance;
  private double power;
  private DriveTrain drive;
  private double target;

  private boolean finished;

  /**
   * Creates a new NewDriveDistance.
   */
  public NewDriveDistance(DriveTrain drive, double distanceInches, double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drive = drive;
    this.distance = distanceInches;
    this.power = power;
    addRequirements(drive);
  }

  private double getAvgDistanceLocation() {
    System.out.println(drive.getLeftEncoderVal() + " " + drive.getRightEncoderVal());
    return (drive.getLeftEncoderVal() + drive.getRightEncoderVal()) / 2.0 / drive.TICKS_PER_INCH;
  }

  private boolean targetUnreached() {
    if (distance > 0) {
      return getAvgDistanceLocation() < target;
    }
    if (distance < 0) {
      return getAvgDistanceLocation() < target;
    }
    return false;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    target = getAvgDistanceLocation() + distance;
    finished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (targetUnreached()) {
      drive.tankDrive(power, power);
    } else {
      finished = true;
      drive.tankDrive(0, 0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
