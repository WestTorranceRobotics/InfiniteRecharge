/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Shooter;
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.subsystems.Loader;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ShooterAndLoaderRev extends WaitCommand {
  private Shooter m_shooter;
  private Loader m_loader;
  /**
   * Creates a new ShooterAndLoaderRev.
   */
  public ShooterAndLoaderRev(Shooter shooter, Loader loader, double time) {
    super(time);
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
    if (m_shooter.getVelocity() > RobotMap.ShooterMap.lineShootRPM-50) {
      m_loader.runBelt();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stopShooter();
    m_loader.stopBelt();
  }

  // // Returns true when the command should end.
  // @Override
  // public boolean isFinished() {
  //   return false;
  // }
}
