/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Intake;
import frc5124.robot2020.subsystems.Loader;

public class FlushOut extends CommandBase {

  private Loader m_Loader;
  private Intake m_Intake;

  public FlushOut(Loader loader, Intake intake) {
    m_Loader = loader;
    m_Intake = intake;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_Loader);
    addRequirements(m_Intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_Intake.flushOut();
    m_Loader.flushOut();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Loader.stopBelt();
    m_Intake.setIntakePower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}