/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import frc5124.robot2020.RobotMap;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Turret implements Subsystem {
  private CANSparkMax turretMotor;
  public CANPIDController turretPID;
  private CANEncoder turretEncoder;
  public double startPosition;
  public boolean limitReached = false;

  public Turret() {
    turretMotor = new CANSparkMax(RobotMap.TurretMap.turretCanID, MotorType.kBrushless);
    turretMotor.setIdleMode(IdleMode.kBrake);
    turretPID = turretMotor.getPIDController();
    turretEncoder = turretMotor.getEncoder();
    turretMotor.restoreFactoryDefaults();
    turretPID.setP(RobotMap.TurretMap.Kp);
    turretPID.setReference(0, ControlType.kVelocity);
    startPosition = getEncoder();
  }



  public double getEncoder() {
    return turretEncoder.getPosition()*RobotMap.TurretMap.turretGearing;
  }

  public void rotateTurret(double power) {
    if (Math.abs(getEncoder()-startPosition) <= RobotMap.TurretMap.turnLimit) {
      if (power != 0) {
        turretMotor.set(power);
      }
      else {
        turretMotor.set(0);
      }
    }  
    
    else if (Math.abs(getEncoder()-startPosition) > RobotMap.TurretMap.turnLimit) {
      if (getEncoder()-startPosition > 0) {
        if (power < 1) {
          turretMotor.set(power);
        }
        else {
          turretMotor.set(0);
        }
        }
      
      else if (getEncoder()-startPosition < 0) {
        if (power > 1) {
          turretMotor.set(power);
        }
        else {
          turretMotor.set(0);
        }
      }
    }
    
    else {
      turretMotor.set(0);
    }
  }

  public int getEncoderCountsPerRevolution(){
    return turretEncoder.getCountsPerRevolution();
  }


  private boolean limitReached() {
    limitReached = true;
    return true;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Current Position", getEncoder());
    SmartDashboard.putNumber("Start position", startPosition);
    SmartDashboard.updateValues();
  }

} 