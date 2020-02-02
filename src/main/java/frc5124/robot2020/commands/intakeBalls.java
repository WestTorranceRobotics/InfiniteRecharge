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

public class intakeBalls extends CommandBase {
  /**
   * Creates a new intakeBalls.
   */
  private Intake intake;
  private boolean run;

  public intakeBalls(Intake intake, boolean run) {
    this.intake = intake;
    this.run = run;
    
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (run == true){
      intake.intake();
    }
    if (run == false){
      intake.motorNoPower();
      isFinished();
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public Set<Subsystem> getRequirements() {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
