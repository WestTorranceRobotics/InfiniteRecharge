/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Shooter;
import edu.wpi.first.wpilibj.controller.PIDController;
import frc5124.robot2020.RobotMap;

public class holdShooterVelocity extends CommandBase {
  Shooter shooter;
  double targetVelocity = 0;
  double kOut = 0;
  double currentVelocity = 0;
  private PIDController shootControl = new PIDController(RobotMap.Shooter.Kp, RobotMap.Shooter.Ki, RobotMap.Shooter.Kd, RobotMap.Shooter.period);
  /**
   * Creates a new holdShooterVelocity.
   */
  public holdShooterVelocity(Shooter subsystem, double targetVelocity) {
    shooter = subsystem;
    addRequirements(shooter);
    this.targetVelocity = targetVelocity;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  //   currentVelocity = shooter.getVelocity();
  //   kOut = shootControl.calculate(currentVelocity, targetVelocity);
  //   if (currentVelocity == 0 ) {
  //     shooter.setPower(0);
  //     return;}
  //   kOut = kOut + RobotMap.ShooterMap.Kf ; 
  //  shooter.setPower(kOut);
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
