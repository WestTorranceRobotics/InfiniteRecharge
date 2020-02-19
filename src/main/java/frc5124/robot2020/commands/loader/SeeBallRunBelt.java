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
  double thicc = 0;
  double no = 0;
  boolean sawball = false;
  private Loader m_Loader;

  public SeeBallRunBelt(Loader subsystem) {
    m_Loader = subsystem;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_Loader);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_Loader.seeBall()) {
      if (sawball == false) {
        m_Loader.runBelt();
        sawball = true;
      }
    }
    if (sawball) {
      if (no < m_Loader.returnRotations()) {
        thicc++;
        no++;
      }
      if (thicc >= 26) {
        m_Loader.stopBelt();
        thicc = 0;
        sawball = false;
      }
    }
    // 1000 is just a placeholder, after we test for optimal time we'll replace it
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
