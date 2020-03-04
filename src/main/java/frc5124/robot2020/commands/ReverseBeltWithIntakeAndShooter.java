/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc5124.robot2020.commands.intake.SetIntakePower;
import frc5124.robot2020.commands.loader.ReverseBelt;
import frc5124.robot2020.commands.shooter.ReverseShooter;
import frc5124.robot2020.subsystems.Intake;
import frc5124.robot2020.subsystems.Loader;
import frc5124.robot2020.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ReverseBeltWithIntakeAndShooter extends ParallelCommandGroup {
  /**
   * Creates a new ReverseBeltWithIntake.
   */
  public ReverseBeltWithIntakeAndShooter(Shooter shooter, Loader loader, Intake intake) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
    
    super(new ReverseShooter(shooter), new ReverseBelt(loader), new SetIntakePower(intake, -0.5));
  }
}