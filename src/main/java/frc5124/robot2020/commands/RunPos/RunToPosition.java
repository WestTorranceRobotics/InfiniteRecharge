/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.RunPos;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.DriveTrain;

import javax.swing.GroupLayout.ParallelGroup;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;


public class RunToPosition extends CommandBase {
private Pose2d currentPos;
private double targetTheta;
private DriveTrain driveTrain;
private double transX;
private double transY;
private double targetDistance;
private ParallelCommandGroup runPos = new ParallelCommandGroup();


  /**
   * Creates a new RunToPosition.
   */
   /**
   * 
   * Creates a new RunToPosition.
   * @param transX translation in X axis
   * @param transY translation in Y axis
   * 
   */
  public RunToPosition(double transX, double transY) {
    this.transX = transX;
    this.transY = transY;
    driveTrain = new DriveTrain();
    addRequirements(driveTrain);


    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
        currentPos =  driveTrain.getLocation();
        targetTheta = Math.atan((transX/transY));
        targetDistance = Math.sqrt((transX*transX)+(transY*transY));
        double currentX = (currentPos.getTranslation().getX() + transX);
        double currentY = (currentPos.getTranslation().getY() + transY);
        
        
        
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
}
