/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Shooter;

public class SetShootVelocity extends CommandBase {
  private Shooter shooter;
  private double targetVelocity;
  
  /**
   * Creates a new setShootVelocity.
   */
  public SetShootVelocity(Shooter subsystem, double targetVelocity) {
    shooter = subsystem;
    addRequirements(shooter);
    this.targetVelocity = targetVelocity;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.setTargetVelocity(targetVelocity);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setTargetVelocity(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}