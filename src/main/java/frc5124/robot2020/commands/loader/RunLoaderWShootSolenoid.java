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

public class RunLoaderWShootSolenoid extends CommandBase {

  private Loader m_Loader;
  private Shooter m_Shooter;

  public RunLoaderWShootSolenoid(Loader loader, Shooter shooter) {
    m_Loader = loader;
    m_Shooter = shooter;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_Loader);
    addRequirements(m_Shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_Shooter.EntryHoleOpenedOrClose()){
      m_Loader.runBelt();
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