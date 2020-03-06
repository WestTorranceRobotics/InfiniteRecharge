/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc5124.robot2020.commands.auto.ShootThreeBalls;
import frc5124.robot2020.commands.shooter.ShootFromTrench;
import frc5124.robot2020.commands.turret.TurretTargetByPIDPerpetually;
import frc5124.robot2020.subsystems.Turret;
import frc5124.robot2020.subsystems.Loader;
import frc5124.robot2020.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShootAimTrench extends ParallelDeadlineGroup {
  /**
   * Creates a new shootAim.
   */
  public ShootAimTrench(Shooter shooter, Loader loader, Turret turret) {
    // Add your commands in the super() call.  Add the deadline first.
    super(new ShootThreeByFF(shooter, loader, 4950, 3), new TurretTargetByPIDPerpetually(turret));
  }
}