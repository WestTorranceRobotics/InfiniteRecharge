/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.loader;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.RobotMap.LoaderMap;
import frc5124.robot2020.subsystems.Loader;

public class TempLoad extends CommandBase {

  private Loader m_Loader;
  private double power;

  public TempLoad(frc5124.robot2020.subsystems.Loader loader, double power) {
    this.power = power;
    m_Loader = loader;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_Loader);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_Loader.setPower(power);
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Loader.setPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
