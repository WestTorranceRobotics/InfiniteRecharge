/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Shooter;

public class RPMbyFF extends CommandBase {

  private Shooter shooter;
  private double rpm;

  /**
   * Creates a new RPMbyFF.
   */
  public RPMbyFF(Shooter shooter, double rpm) {
    this.shooter = shooter;
    this.rpm = rpm;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    iAccum = 0;
  }

  // regression-calculated static friction overcome voltage
  private final double S = 0.147;
  // regression-calculated voltage per rpm of velocity added
  private final double V = 0.0015538;
  // tuned constant to determine coarse target rpm approach rate
  private final double A = 0.01;
  // tuned constants to determine aggression of rpm error compensation
  private final double I = 0.001;
  private final double I_RANGE = 75;

  private double iAccum;

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double value = shooter.getVelocity();
    double error = rpm - value;
    if (Math.abs(error) < I_RANGE) {
      iAccum += I * error;
    }
    shooter.directVolts(S + V * value + A * error + iAccum);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.directPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
