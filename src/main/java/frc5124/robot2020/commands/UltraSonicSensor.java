/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands;

import java.util.Set;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.subsystems.Loader;


public class UltraSonicSensor implements Command {
  /**
   * Creates a new UltraSonicSensor.
   */
  private Loader loader;
  private NetworkTableEntry ultraSonic;
  private NetworkTableEntry distance;

  public UltraSonicSensor(Loader loader,NetworkTableEntry ultraSonic,NetworkTableEntry distance ) {
    this.loader = loader;
    this.ultraSonic = ultraSonic;
    this.distance = distance;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.

  // Called every time the scheduler runs while the command is scheduled.
  public void execute() {
      ultraSonic.setBoolean(loader.getDistance() < 10);
      distance.setDouble(loader.getDistance());

      
  }
  public Set<Subsystem> getRequirements(){
    return Set.of();
  }

  @Override 
  public boolean runsWhenDisabled(){
    return true;
  }
}
