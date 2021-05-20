// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc5124.robot2020.commands.driveTrain;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.DriveTrain;

public class MoveToRightDistance extends CommandBase {
  private DriveTrain driveTrain;
  private Turret turret;

  double kP = 0.1;
  double kI = 0.1;
  double kD = 0.1;
  double integral = 0.0;
  
  /** Creates a new MoveToRightDistance. */
  public MoveToRightDistance(DriveTrain driveTrain, Turret turret) {
    this.driveTrain = driveTrain;
    this.turret = turret;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double error = turret.getDistanceFromTarget() - targetDistance;
    integral += (error * 0.02);
    double move = kP * error + kI * integral;
    driveTrain.tankDrive(move, move);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
