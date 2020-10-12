/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2021.commands.loader;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc5124.robot2021.subsystems.Loader;

public class ReversePriorToShoot extends WaitCommand {
  /**
   * Creates a new ReversePriorToShoot.
   */
  private Loader loader;

  public ReversePriorToShoot(Loader subsystem, double time) {
    super(time);
    loader = subsystem;
    addRequirements(loader);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    super.initialize();
    loader.reverseBelt();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    loader.stopBelt();
  }
}
