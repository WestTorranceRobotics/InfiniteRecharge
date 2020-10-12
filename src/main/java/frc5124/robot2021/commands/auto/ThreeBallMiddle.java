/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2021.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc5124.robot2021.commands.auto.runpos.ShootAim;
import frc5124.robot2021.commands.auto.runpos.Turn180;
import frc5124.robot2021.commands.auto.runpos.TurnTurret;
import frc5124.robot2021.commands.intake.ToggleIntakePivot;
import frc5124.robot2021.commands.turret.TurretFindHome;
import frc5124.robot2021.commands.turret.TurretZeroPosition;
import frc5124.robot2021.subsystems.Turret;
import frc5124.robot2021.subsystems.DriveTrain;
import frc5124.robot2021.subsystems.Loader;
import frc5124.robot2021.subsystems.Shooter;
import frc5124.robot2021.subsystems.Intake;
import frc5124.robot2021.subsystems.LED;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ThreeBallMiddle extends SequentialCommandGroup {
  /**
   * Creates a new ThreeBallAuto.
   */
  public ThreeBallMiddle(Turret turret, Loader loader, Shooter shooter, DriveTrain driveTrain, Intake intake) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      new ToggleIntakePivot(intake),
      new TurretFindHome(turret),
      new TurnTurret(turret, 210),
      new ShootAim(shooter, loader, turret),
      new RunDistanceForward(driveTrain, 30, .5)
    );
  }

}