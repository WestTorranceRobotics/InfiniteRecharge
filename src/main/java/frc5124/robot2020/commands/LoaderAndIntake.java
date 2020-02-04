/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.subsystems.Intake;
import frc5124.robot2020.subsystems.Loader;

public class LoaderAndIntake extends CommandBase {
  /**
   * Creates a new LoaderAndIntake.
   */

   private Loader loader;
   private Intake intake;
   int counter = 0;
   boolean isDone;

  public LoaderAndIntake(Loader loader, Intake intake) {
    this.loader = loader;
    this.intake = intake;
    addRequirements(loader, intake);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isDone = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (loader.hasBall()){
      loader.moveOneSpot();
      counter++;
    }
    if(counter == 5){
      isDone = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}