/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;
import frc5124.robot2020.RobotMap;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Turret implements Subsystem {
  public boolean limitReached = false;
  private CANSparkMax turretMotor;
  private CANPIDController turretPID;
  
  public Turret() {
    turretMotor = new CANSparkMax(RobotMap.TurretMap.turretCanID, MotorType.kBrushless);
    turretPID = turretMotor.getPIDController();
    // turretPID.setD(RobotMap.TurretMap.Kd);
    // turretPID.setP(RobotMap.TurretMap.Kp);
    // turretPID.setReference(5, ControlType.kPosition);

    // SmartDashboard.putNumber("P", RobotMap.TurretMap.Kp);
    // SmartDashboard.putNumber("I", 0);
    // SmartDashboard.putNumber("D", RobotMap.TurretMap.Kd);
    // SmartDashboard.putNumber("F", 0);
    // SmartDashboard.putNumber("Ref", 0 );
    
  }

  @Override
  public void periodic() {
    SmartDashboard.updateValues();
  }


  // public void updateCoeffs() {
  //   turretPID.setD(SmartDashboard.getNumber("D", RobotMap.TurretMap.Kd));
  //   turretPID.setI(SmartDashboard.getNumber("I", 0 ));
  //   turretPID.setFF(SmartDashboard.getNumber("F", 0));
  //   turretPID.setP(SmartDashboard.getNumber("P", RobotMap.TurretMap.Kp));
  //   turretPID.setReference(SmartDashboard.getNumber("Ref", 0), ControlType.kPosition);
    


  // }

  public void rotateTurret(double power) {
    if (limitReached && turretMotor.getAppliedOutput() == 0) {
      
    } else if (limitReached) {
      turretMotor.set(0);
      
    } else if (!limitReached && turretMotor.getAppliedOutput() != power) {
      turretMotor.set(power);
      
    }
    else {
      
    }
  }

  public int getEncoder(){
    return turretMotor.getEncoder().getCountsPerRevolution();
  }

  private boolean limitReached() {
    return true;
    
  }

}
