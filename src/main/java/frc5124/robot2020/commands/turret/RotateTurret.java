/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;

public class RotateTurret extends CommandBase {
  private Turret turret;
  private double power;
  /**
   * Creates a new RotateTurret.
   * @param power Useable if limit not reached. Suggest moving by units (not coded yet)
   */
  public RotateTurret(Turret subsystem, double power) {
    turret = subsystem;
    addRequirements(turret);
    this.power = power;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    turret.rotateTurret(power);
  }

  // Called every time the scheduler runs while the command is scheduled.
  // Need encoder position limits to finish coding
  @Override
  public void execute() {
  
    // if (1==1 && !turret.limitReached) { //1==1 placeholder
    //   turret.limitReached = true;
    // } 
    // else if (!(1==1) && turret.limitReached) { //1==1 placeholder
    //   turret.limitReached = false;
    // }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.rotateTurret(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
