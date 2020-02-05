/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc5124.robot2020.subsystems.Shooter;

//Class can be moved to be an inline
public class ShootVelocity extends InstantCommand {
  private Shooter shooter;
  private double targetVelocity;
  
  public ShootVelocity(Shooter subsystem, double targetVelocity) {
    shooter = subsystem;
    addRequirements(shooter);
    this.targetVelocity = targetVelocity;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.setTargetVelocity(targetVelocity);
  }
}
