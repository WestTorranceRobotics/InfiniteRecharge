/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;

public class Shooter implements Subsystem {
  private CANSparkMax shootMotorFollower = new CANSparkMax(RobotMap.ShooterMap.shootFollowerCanID, MotorType.kBrushless);
  private CANSparkMax shootMotorLeader = new CANSparkMax(RobotMap.ShooterMap.shootLeaderCanID, MotorType.kBrushless);
  private CANPIDController shootPID; 
  private boolean atSpeed;
  private Solenoid shootSolenoid = new Solenoid(RobotMap.modNumSolenoid, RobotMap.ShooterMap.shootSolenoid);
  private int ballsShot;
  private boolean passedBallCurrent = false;
 
  public Shooter() {
    shootMotorFollower.restoreFactoryDefaults();
    shootMotorLeader.restoreFactoryDefaults();
    shootMotorFollower.follow(shootMotorLeader, true);
    shootMotorLeader.setSmartCurrentLimit(RobotMap.ShooterMap.smartCurrentLimit);
    shootMotorFollower.setSmartCurrentLimit(RobotMap.ShooterMap.smartCurrentLimit);
    shootPID = shootMotorLeader.getPIDController();
    shootPID.setD(RobotMap.ShooterMap.Kd);
    shootPID.setP(RobotMap.ShooterMap.Kp);
    shootPID.setFF(RobotMap.ShooterMap.Kf);
    shootPID.setReference(0, ControlType.kVelocity);
  }

  public int getBallsShot() {
    return ballsShot;
  }

  public void resetBallCount() {
    ballsShot = 0;
  }

  public void disablePID() {
    shootPID.setD(0);
    shootPID.setP(0);
    shootPID.setFF(0);
  }

  public void enablePID() {
    shootPID.setD(RobotMap.ShooterMap.Kd);
    shootPID.setP(RobotMap.ShooterMap.Kp);
    shootPID.setFF(RobotMap.ShooterMap.Kf);
  }

  /**
   * @param targetRPM desired RPM of shooter
   */
  public void startShooter() {
    enablePID();
    shootPID.setReference(RobotMap.ShooterMap.lineShootRPM, ControlType.kVelocity);
    }

    public void stopShooter () {
      disablePID();
     shootMotorLeader.set(0);
    }

/**
 * Units of ft/s
 */
  public double getVelocity() {
    return (shootMotorLeader.getEncoder().getVelocity()); 
   }
  
   public void atSpeed(boolean atSpeed) {
    this.atSpeed= atSpeed;
  }

  public class atSpeed extends Shooter{
    public boolean atSpeed() {
      return atSpeed;
    }
  }
  
  public boolean holeOpenedOrClose(){
    return shootSolenoid.get();
  }

  public void openHole(){
    shootSolenoid.set(true);
  }

  public void closeHole(){
    shootSolenoid.set(false);
  }

  public void directPower (double power) {
    shootMotorLeader.set(power);
  }

  public boolean atSpeed () {
    return true;
  }

  /**
   * Checks output current to shooter to count balls that have passed
   * 
   * Call in command execute or periodic
   * 
   * @param targetRPM PID RPM reference; will not count a ball shot if a current spike is detected below speed
   */
  public void currentWatch(double targetRPM) {
    if (shootMotorLeader.getOutputCurrent() >= RobotMap.ShooterMap.ballCurrent  && passedBallCurrent == false && getVelocity() >= targetRPM-75 ) {
      passedBallCurrent = true;
      ballsShot = ballsShot + 1;
    } else if (passedBallCurrent == true && shootMotorLeader.getOutputCurrent() < RobotMap.ShooterMap.ballCurrent) {
      passedBallCurrent = false;
    } 
  }
  

  @Override
  public void periodic() {
  }
    
}