/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.DriveTrain;

public class DriveToPoint extends CommandBase {
  /**
   * Creates a new resetGyro.
   */
  private DriveTrain m_DriveTrain;
  private int x;    //in inches
  private int y;    //in inches

  public DriveToPoint(DriveTrain driveTrain, int x, int y) {
    m_DriveTrain = driveTrain;
    addRequirements(m_DriveTrain);
  }

  double distanceToDrive = Math.sqrt((x*x) + (y*y)); //the hypotenuse (inches)
  double angleToTurn = Math.atan((y)/(x));
  double countsPerInch = 1082;
  double targetCounts = (countsPerInch * distanceToDrive);

  public void turnToAngle(){
    if (angleToTurn < 0) { // if angle is neg (clockwise [think of unit circle] right? idk i think so)
      if (m_DriveTrain.getGyroDegree() <= Math.abs(angleToTurn)) { //if gyro angle has not been reached
        m_DriveTrain.setSeperatePower(1, -1); //left, right
      }
    }
    if (angleToTurn > 0) { // if angle is pos (counterclockwise)
      if (m_DriveTrain.getGyroDegree() <= angleToTurn){
        m_DriveTrain.setSeperatePower(-1, 1);
      }
    }
    m_DriveTrain.directPower(0);
  }

  public void driveStraightToPoint(){ // after it figures out the angle, it should just drive straight
    if (m_DriveTrain.leftEncoder() <= targetCounts){ // if the encoder counts dont match yet then run
      m_DriveTrain.directPower(1);
    }
    /*
    while (m_DriveTrain.leftEncoder()<= targetCounts){
      m_DriveTrain.directPower(1);
    }
    */
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    turnToAngle();
    driveStraightToPoint();    
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
