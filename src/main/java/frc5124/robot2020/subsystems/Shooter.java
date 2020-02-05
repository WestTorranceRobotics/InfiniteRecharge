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
    

        SmartDashboard.putNumber("P Gain", 0);
        SmartDashboard.putNumber("I Gain", 0);
        SmartDashboard.putNumber("D Gain", 0);
        SmartDashboard.putNumber("I Zone", 0);
        SmartDashboard.putNumber("Feed Forward", 0);
        SmartDashboard.putNumber("Max Output", 0);
        SmartDashboard.putNumber("Min Output", 0);
        SmartDashboard.putNumber("SetPoint", 0);
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

  public void updatePID(){
    shootController.setD(RobotMap.ShooterMap.Kd);
    shootController.setFF(RobotMap.ShooterMap.Kf);
    shootController.setP(RobotMap.ShooterMap.Kp);
    shootController.setI(RobotMap.ShooterMap.Ki);
    shootController.setOutputRange(-RobotMap.ShooterMap.maxVelocity, RobotMap.ShooterMap.maxVelocity);

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
    SmartDashboard.updateValues();
  }

//   /**
//    * WARNING
//    * Control Loop Untuned
//    * @param targetVelocity in units of ft/s; truncated if exceeding maxVelocity
//    * 
//    */
//   public void setVelocity (double targetVelocity) {
//   }

//   /**
//    * Must be called in periodic
//    */
//   private void holdVelocity (double targetVelocity) {
//   kPI(targetVelocity);
//   setPower(kOut); //kOut is the kPI output
//   }

//   /**
//    * @deprecated
//    */
//   public void directPower (double power) {
//   setPower(power);
//   }


//   public void runWheel(boolean run) {
//     this.run = run;
//   }
 

//   /**
//    * Must be called in execute
//    */
//   public void holdVelocity (double targetVelocity) {
//   kPI(targetVelocity);
//   setPower(kOut); //kOut is the kPI output
//   }

//   /**
//    * @deprecated
//    */
//   public void directPower (double power) {
//     if (run) {
//   setPower(power);
//   } else {
//     setPower(0);
//   }
//   }


//   /**
//    * lightweight PI loop
//    */
//   private void kPI(double targetVelocity) {
//     getVelocity();
//     kOut = shootControl.calculate(currentVelocity, targetVelocity);

//      if (targetVelocity== 0) {
//        return;
//      }
  
//     kOut = kOut + RobotMap.Shooter.Kf ; 
//   }

// /**
//  * Units of ft/s
//  */

//   public void getVelocity() {
//     this.currentVelocity = ((shootMotorLeader.getEncoder().getVelocity() / 60)  * RobotMap.Shooter.conversionConstant); // 1 rpm * .75 (gear reduction) * conversionConstant
    
//    }
  
//   private void setPower (double power) {
//     shootMotorLeader.set(power);
//   }
}

