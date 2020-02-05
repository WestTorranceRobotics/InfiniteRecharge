/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc5124.robot2020.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class setShootVelocity extends InstantCommand {
  private Shooter shooter;
  private double targetVelocity;
  public setShootVelocity(Shooter subsystem, double targetVelocity) {
    shooter = subsystem;
    addRequirements(shooter);
    this.targetVelocity = targetVelocity;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }
}
