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

public class ShootThreeBallsTrench extends CommandBase {
  private Shooter m_shooter;
  private Loader m_loader;
  private boolean isDone = false;
  private double targetVelocity = RobotMap.ShooterMap.trenchShootRPM;
  /**
   * Creates a new ShooterAndLoaderRev.
   */
  public ShootThreeBallsTrench(Shooter shooter, Loader loader) {
    m_shooter = shooter;
    m_loader = loader;

    addRequirements(m_loader);
    addRequirements(m_shooter);
  }

 @Override
  public void initialize() {
    m_shooter.startShooter(RobotMap.ShooterMap.trenchShootRPM);
    m_shooter.resetBallsShot();
  }
   
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    super.execute();
    if (Math.abs(targetVelocity) - Math.abs(m_shooter.getVelocity()) <= 20) {
      m_loader.runBelt();
      m_shooter.atSpeed(true);
    } 
    if(m_shooter.atSpeed()) {
    m_shooter.currentWatch(targetVelocity);
  }

    if (m_shooter.getBallsShot() == 3) {
      isDone = true;
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    m_shooter.stopShooter();
    m_loader.stopBelt();
   
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}