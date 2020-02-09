/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc5124.robot2020.subsystems.*;
import frc5124.robot2020.commands.loader.RunLoaderWShootSolenoid;
import frc5124.robot2020.commands.shooter.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class LoaderAndShooterGroup extends ParallelCommandGroup {
  /**
   * Creates a new LoaderAndShooterGroup.
   */
  private static Loader loader;
  private static Shooter shooter;
  private static double targetVel;

  public LoaderAndShooterGroup() {
    super(new SetShootRPM(shooter, targetVel), new RunLoaderWShootSolenoid(loader, shooter));
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
  }
}
