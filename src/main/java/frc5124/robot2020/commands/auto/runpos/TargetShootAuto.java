/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto.runpos;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc5124.robot2020.subsystems.DriveTrain;
import frc5124.robot2020.subsystems.Shooter;
import frc5124.robot2020.subsystems.Loader;
import frc5124.robot2020.subsystems.Turret;
import frc5124.robot2020.commands.auto.runpos.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TargetShootAuto extends SequentialCommandGroup {
  /**
   * Creates a new TargetShootAuto.
   */
  public TargetShootAuto(Shooter shooter, Loader loader, Turret turret, DriveTrain driveTrain) {
    super(new Turn180(turret), new ShootAim(shooter, loader, turret), new DriveForTime(driveTrain, 2));
  }
}