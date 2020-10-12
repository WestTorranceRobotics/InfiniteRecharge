/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc5124.robot2020.commands.auto.runpos.Turn180;
import frc5124.robot2020.subsystems.Turret;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TurretZeroAndTurn extends SequentialCommandGroup {
  /**
   * Creates a new TurretZeroAndTurn.
   */
  public TurretZeroAndTurn(Turret turret) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      new TurretFindHome(turret),
      new Turn180(turret)
    
    );
  }
}
