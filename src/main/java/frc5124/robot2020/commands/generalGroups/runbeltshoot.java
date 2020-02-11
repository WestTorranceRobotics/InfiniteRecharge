/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.generalGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc5124.robot2020.commands.loader.runIfReady;
import frc5124.robot2020.commands.shooter.SetShootRPM;
import frc5124.robot2020.commands.shooter.startShooter;
import frc5124.robot2020.subsystems.Loader;
import frc5124.robot2020.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class runbeltshoot extends SequentialCommandGroup {
  private Shooter m_shooter;
  private Loader m_loader;
  /**
   * Creates a new run_belt_shoot.
   */
  public runbeltshoot(Loader subsystem1, Shooter subsystem2) {
    m_loader = subsystem1;
    m_shooter = subsystem2;
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    //super(new startShooter(m_shooter), new runIfReady(m_Loader, m_shooter));
  }
}
