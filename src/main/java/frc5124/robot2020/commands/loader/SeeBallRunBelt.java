/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.loader;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Loader;

public class SeeBallRunBelt extends CommandBase {
  private double timedelay = 0;
  private double num = 0;
  private Loader m_Loader;
  private boolean finished;

  public SeeBallRunBelt(Loader subsystem) {
    m_Loader = subsystem;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_Loader);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    finished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_Loader.seeBall()) {
      m_Loader.runBelt();
      if (num < m_Loader.returnRotations()) {
        timedelay++;
        num++;
      }
      if (timedelay >= 26) {
        m_Loader.stopBelt();
        timedelay = 0;
        finished = true;
      }
    }
    // 26 is just a placeholder, after we test for optimal time we'll replace it
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Loader.stopBelt();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }

}
