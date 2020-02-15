/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Intake;

public class SetIntakePower extends CommandBase {
  private Intake intake;
  private double power;
  private TalonFX talon = new TalonFX(5);
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
    talon.set(ControlMode.PercentOutput, .5);
   // intake.setIntakePower(power);
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
