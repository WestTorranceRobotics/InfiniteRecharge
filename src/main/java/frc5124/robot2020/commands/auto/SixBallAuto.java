/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc5124.robot2020.commands.auto.runpos.ShootAim;
import frc5124.robot2020.commands.auto.runpos.Turn180;
import frc5124.robot2020.commands.intake.ToggleIntakePivot;
import frc5124.robot2020.commands.turret.TurretFindHome;
import frc5124.robot2020.commands.turret.TurretZeroPosition;
import frc5124.robot2020.subsystems.Turret;
import frc5124.robot2020.subsystems.DriveTrain;
import frc5124.robot2020.subsystems.Loader;
import frc5124.robot2020.subsystems.Shooter;
import frc5124.robot2020.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class SixBallAuto extends SequentialCommandGroup {
  /**
   * Creates a new SixBallAuto.
   */
  public SixBallAuto(Turret turret, Loader loader, Shooter shooter, DriveTrain driveTrain, Intake intake) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      new InstantCommand(() -> intake.setDeployed(true), intake),
      new TurretFindHome(turret),
      new Turn180(turret),
      new ShootAim(shooter, loader, turret),
      new DriveAndIntake(165, .55, driveTrain, intake, loader),
      new RunDistanceReverse(driveTrain, 77),
      new ShootAimTrench(shooter, loader, turret)
    );
  }

}