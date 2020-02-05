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

/**
 * 
 * WARNING 
 * Untuned
 * 
 * 
 * 
 */
public class Shooter implements Subsystem {
  private CANSparkMax shootMotorFollower = new CANSparkMax(RobotMap.ShooterMap.shootFollowerCanID, MotorType.kBrushless);
  private CANSparkMax shootMotorLeader = new CANSparkMax(RobotMap.ShooterMap.shootLeaderCanID, MotorType.kBrushless);
  private CANPIDController shootController; 
  private double currentVelocity;
 

  
  public Shooter() {
    shootMotorFollower.follow(shootMotorLeader);
    shootController = shootMotorLeader.getPIDController();
    shootController.setD(RobotMap.ShooterMap.Kd);
    shootController.setFF(RobotMap.ShooterMap.Kf);
    shootController.setP(RobotMap.ShooterMap.Kp);
    shootController.setI(RobotMap.ShooterMap.Ki);
    shootController.setOutputRange(-RobotMap.ShooterMap.maxVelocity, RobotMap.ShooterMap.maxVelocity);
  }

  /**
   * @param targetVelocity desired ft/s [advise 30 or 0]
   */
  public void setTargetVelocity(double targetVelocity) {
    targetVelocity = (targetVelocity / RobotMap.ShooterMap.conversionConstant);
    if (shootMotorLeader.getAppliedOutput() == 0 && targetVelocity == 0) {
      
    } else {
        shootController.setReference(targetVelocity, ControlType.kVelocity);
      }
  }

/**
 * Units of ft/s
 */
  public double getVelocity() {
    return ((shootMotorLeader.getEncoder().getVelocity())  * RobotMap.ShooterMap.conversionConstant); 
   }
  
  /**
   * @deprecated
   */
  public void directPower (double power) {
    shootMotorLeader.set(power);
  }

  public boolean atSpeed () {
    return true;
  }

  
  @Override
  public void periodic() {
  }

}

