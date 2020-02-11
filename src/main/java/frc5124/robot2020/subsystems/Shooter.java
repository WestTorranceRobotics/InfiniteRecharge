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

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter implements Subsystem {
  private CANSparkMax shootMotorFollower = new CANSparkMax(RobotMap.ShooterMap.shootFollowerCanID, MotorType.kBrushless);
  private CANSparkMax shootMotorLeader = new CANSparkMax(RobotMap.ShooterMap.shootLeaderCanID, MotorType.kBrushless);
  private CANPIDController shootPID; 
  private double currentVelocity;
  private boolean solenoidBoolean = true;
  private Solenoid shootSolenoid = new Solenoid(0, 0);
  private boolean atSpeed;
 
  public Shooter() {
    shootMotorFollower.follow(shootMotorLeader, true);
    shootPID = shootMotorLeader.getPIDController();
    shootPID.setD(RobotMap.ShooterMap.Kd);
    shootPID.setP(RobotMap.ShooterMap.Kp);
    shootPID.setFF(RobotMap.ShooterMap.Kf);
    shootPID.setReference(0, ControlType.kVelocity);
    shootPID.setOutputRange(-600, 600);
    closeHole();
  }

  /**
   * @param targetRPM desired RPM of shooter
   */
  public void startShooter () {
    shootPID.setReference(RobotMap.ShooterMap.lineRefRPM, ControlType.kVelocity);
    }

    public void stopShooter () {
     shootPID.setReference(0, ControlType.kVelocity);
     closeHole();
    }
  

/**
 * Units of ft/s
 */
  public double getVelocity() {
    return (shootMotorLeader.getEncoder().getVelocity() * RobotMap.ShooterMap.reduction); 
   }
  
  /**
   * 
   */
  public void directPower (double power) {
    shootMotorLeader.set(power);
  }



  public void openHole(){
    shootSolenoid.set(true);
  }


  public boolean atSpeed() {
    return atSpeed;
  }

  public void atSpeed(boolean atSpeed) {
    this.atSpeed= atSpeed;

  }


  public void closeHole(){
    shootSolenoid.set(false);
  }

  public boolean holeIsOpen(){
    return true;
  }

  public boolean holeIsClosed(){
    return false;
  }


  public boolean testOpenOrClose(){
    if (holeIsOpen()){
      return true;
    }
    else{
      return false;
    }
  }

  @Override
  public void periodic() {
  }
}