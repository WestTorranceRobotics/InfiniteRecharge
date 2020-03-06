/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.commands.intake.SetIntakePower;
import frc5124.robot2020.commands.loader.SeeBallRunBelt;
import frc5124.robot2020.subsystems.DriveTrain;
import frc5124.robot2020.subsystems.Intake;
import frc5124.robot2020.subsystems.Loader;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DriveAndIntake extends ParallelDeadlineGroup {
  /**
   * Creates a new DriveAndIntake.
   */
  public DriveAndIntake(double inchesDriven, double power, DriveTrain driveTrain, Intake intake, Loader loader) {
    // Add your commands in the super() call.  Add the deadline first.
    super(
        new RunDistanceForward(driveTrain, inchesDriven, power),
        new SetIntakePower(intake, 1),
        new SeeBallRunBelt(loader)
    );
  }
}
