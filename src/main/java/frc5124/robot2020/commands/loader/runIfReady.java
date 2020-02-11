/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.loader;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Loader;
import frc5124.robot2020.subsystems.Shooter;
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.commands.generalGroups.run_belt_shoot;

public class runIfReady extends CommandBase {
  private Loader m_loader;
  private Shooter m_shooter;
  /**
   * Creates a new runIfReady.
   */
  public runIfReady(Loader subsystem1, Shooter subsystem2) {
    m_Loader = subsystem1;
    m_shooter = subsystem2;
    private boolean finished = false;
    

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_Loader);
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_Loader.runBelt();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_shooter.getVelocity() < RobotMap.ShooterMap.lineRefRPM - 2 || m_shooter.getVelocity() > RobotMap.ShooterMap.lineRefRPM + 2){
      run_belt_shoot(m_loader, m_shooter);
      finished = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Loader.stopBelt();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
