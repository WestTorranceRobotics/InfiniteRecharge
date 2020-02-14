/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Loader;
import frc5124.robot2020.subsystems.Shooter;

public class ShooterPIDAndLoader extends CommandBase {
  private Shooter m_shooter;
  private Loader m_loader;
  
  /**
   * Creates a new setShootVelocity.
   */
  public ShooterPIDAndLoader (Shooter shooter, Loader loader) {
    m_shooter = shooter;
    m_loader = loader;

    addRequirements(m_loader);
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
    if (m_shooter.holeOpenedOrClose()){
      m_loader.runBelt();
    }
  }

  // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stopShooter();
    m_loader.stopBelt();
  }
  
}