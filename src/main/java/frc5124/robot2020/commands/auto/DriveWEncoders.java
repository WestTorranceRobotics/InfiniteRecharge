/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.DriveTrain;

public class DriveWEncoders extends CommandBase {
  private double inchesToMove;
  DriveTrain driveTrain;
  /**
   * Creates a new DriveForTime.
   */
  public DriveWEncoders(DriveTrain subsystem, double inchesToMove) {
    driveTrain = subsystem;
    addRequirements(subsystem);
  }

  double countsPerInch = 1082;
  double targetCounts = (inchesToMove*countsPerInch);

  public void driveStraight(){
    if (driveTrain.getLeftEncoderVal() < targetCounts){
      driveTrain.tankDrive(.45, .45);
    }
  }

  @Override
  public void execute() {
    driveStraight();  
  }

  @Override
  public void end(boolean interrupted) {
    
  }
}
