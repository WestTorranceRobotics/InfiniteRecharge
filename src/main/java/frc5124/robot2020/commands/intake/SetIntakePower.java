/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Intake;

public class SetIntakePower extends CommandBase {
  private Intake intake;
  private double power;
  /**
   * Creates a new setIntakePower.
   */
  public SetIntakePower(Intake subsystem, double power) {
    intake = subsystem;
    addRequirements(intake);
    this.power = power;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intake.setIntakePower(power);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Returns true when the command should end.
  @Override
    public boolean isFinished() {
      return false;
    }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.setIntakePower(0);
  }

  
}
