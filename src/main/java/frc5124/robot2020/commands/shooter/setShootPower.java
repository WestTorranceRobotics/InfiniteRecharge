/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.subsystems.Shooter;

public class setShootPower extends CommandBase {
  private Shooter m_shooter;
  
  /**
   * Creates a new setShootVelocity.
   */
  public setShootPower (Shooter shooter) {
    m_shooter = shooter;
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.directPower(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }
  // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.directPower(0);
  }
  
}