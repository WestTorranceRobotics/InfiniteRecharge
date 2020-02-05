/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto.runPos;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;


public class RunDistance extends CommandBase {
private Pose2d currentPos;
private double targetTheta;
private DriveTrain driveTrain;
private double transX;
private double transY;
private double targetDistance;
private ParallelCommandGroup runPos = new ParallelCommandGroup();
private double currentDistance;
private PIDController distanceController = new PIDController(0, 0, 0);
private PIDController angleController = new PIDController(0,0,0); 



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
  public RunDistance(DriveTrain subsystem, double transX, double transY) {
    driveTrain = subsystem;
    addRequirements(driveTrain);
    this.transX = transX;
    this.transY = transY;
      // Use addRequirements() here to declare subsystem dependencies.
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}
  

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
        currentPos =  driveTrain.getLocation();
        targetTheta = Math.atan((transX/transY));
        double turn = angleController.calculate(driveTrain.getGryoDegree(), targetTheta);

        if(driveTrain.getGryoDegree() <= targetTheta){
          driveTrain.arcadeDrive(0,turn);
        }
        else{
          targetDistance = Math.sqrt((transX*transX)+(transY*transY));
          double currentX = (currentPos.getTranslation().getX() + transX);
          double currentY = (currentPos.getTranslation().getY() + transY); 
        
          currentDistance =  Math.sqrt((currentX * currentX)+(currentY * currentY));

          distanceController.calculate(currentDistance, targetDistance);
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
}

