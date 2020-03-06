/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.subsystems.Shooter;


public class ShootTuner extends CommandBase {
  private Shooter shooter;
  private double rpm = 0;

  /**
   * Creates a new setShootVelocity.
   */
  public ShootTuner (Shooter shooter) {
    this.shooter = shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    rpm=SmartDashboard.getNumber("TargetRPM", 0);

    double error = shooter.getVelocity() - rpm;
    double comp = -1.2e-5 * Math.pow(error,3);
    if (Math.abs(comp) > 0.2) {
      comp = Math.signum(comp) * 2;
    }
    shooter.directVolts(0.147 + 0.0015538 * rpm );
  }
  // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopShooter();
  }
  
}