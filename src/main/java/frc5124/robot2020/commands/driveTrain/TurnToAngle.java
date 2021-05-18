// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc5124.robot2020.commands.driveTrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.DriveTrain;

public class TurnToAngle extends CommandBase {
  private DriveTrain subsystem;
  float Kp = -0.1f;
  float min_command = 0.15f;
  
  /** Creates a new TurnToAngle. */
  public TurnToAngle(DriveTrain subsystem) {
    this.subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    NetworkTableInstance.getDefault().getTable("rpi").getEntry("aimbot").setDouble(1);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0.0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      double tx = 
      NetworkTableInstance.getDefault().getTable("limelight")
      .getEntry("tx").getDouble(0);
      // float heading_error = tx;
      float steering_adjust = 0.0f;
      if (tx > 1.0) {
        steering_adjust = (tx / 20) * -min_command;
      }
      else if (tx < 1.0) {
        steering_adjust = (tx / 20) *  min_command;
      }
      subsystem.tankDrive(steering_adjust, -steering_adjust);
  // SmartDashboard.putNumber("Deg", subsystem.getDegrees());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    NetworkTableInstance.getDefault().getTable("rpi").getEntry("aimbot").setDouble(0);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1.0);
    //subsystem.setTurretDegrees(0);
   // NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setDouble(1.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
