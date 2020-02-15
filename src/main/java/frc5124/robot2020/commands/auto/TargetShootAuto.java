/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc5124.robot2020.commands.turret.TurretTargetByPID;
import frc5124.robot2020.commands.turret.TurretTargetByPIDPerpetually;
import frc5124.robot2020.subsystems.DriveTrain;
import frc5124.robot2020.subsystems.Shooter;
import frc5124.robot2020.subsystems.Loader;
import frc5124.robot2020.subsystems.Turret;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TargetShootAuto extends SequentialCommandGroup {
  /**
   * Creates a new TargetShootAuto.
   */
  public TargetShootAuto(Shooter shooter, Loader loader, Turret turret, DriveTrain driveTrain) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new shootAim(shooter, loader, turret), new DriveForTime(driveTrain, 1));
    //super(new shootAim(shooter, loader, turret), new DriveWEncoders(driveTrain, 5));
  }
}
