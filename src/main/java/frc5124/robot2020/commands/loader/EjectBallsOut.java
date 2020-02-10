/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.loader;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Loader;
import frc5124.robot2020.subsystems.Intake;
public class EjectBallsOut extends CommandBase {

    private Loader m_Loader;
    private Intake m_Intake;

    public EjectBallsOut(Loader subsystem, Intake subsystem2) {
        m_Loader = subsystem;
        m_Intake = subsystem2;

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
        try {
            m_Loader.ejectBallsOut();
            m_Intake.setIntakePower(-1);
            wait(3500);
            m_Intake.setIntakePower(0);

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Loader.setPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}
