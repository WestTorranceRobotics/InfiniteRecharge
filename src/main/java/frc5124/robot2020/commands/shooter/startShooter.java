/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Shooter;
import frc5124.robot2020.RobotMap;  

public class startShooter extends CommandBase {
  private Shooter m_shooter;
  private boolean finished = false;
  
  /**
   * Creates a new setShootVelocity.
   */
  public startShooter (Shooter subsystem) {
    this.m_shooter = subsystem;
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.startShooter();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_shooter.getVelocity() == RobotMap.ShooterMap.lineRefRPM - 2 || m_shooter.getVelocity() == RobotMap.ShooterMap.lineRefRPM + 2) {
      m_shooter.openHole();
      m_shooter.atSpeed(true);
      finished = true;
    } 
  }
  // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return finished;
    }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }
  
}
