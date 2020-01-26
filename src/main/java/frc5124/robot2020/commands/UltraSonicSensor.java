/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Loader;


public class UltraSonicSensor extends CommandBase {
  /**
   * Creates a new UltraSonicSensor.
   */
  private Loader loader;
  private NetworkTableEntry ultraSonic;

  public UltraSonicSensor(Loader loader,NetworkTableEntry ultraSonic ) {
    this.loader = loader;
    this.ultraSonic = ultraSonic;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      ultraSonic.setDouble(loader.currentDistance);
      if(loader.currentDistance < 10){
        ultraSonic.setBoolean(true);
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
  public boolean runWhenDisabled(){
    return true;
  }
}
