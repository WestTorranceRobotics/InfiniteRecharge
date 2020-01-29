/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;

import com.revrobotics.CANSparkMax;
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
  private final double processTime = .02; //in units of seconds
  private final double maxVelocity = 99; //in units of ft/s
  private final double conversionConstant = 2 * 3.141592654 * 0.33333; //radius = .3333 ft
  private double kOut = 0;
  private double currentVelocity, error, integral = 0; 
  private double kI, kP = 1; 
  //private TalonSRX jeff = new TalonSRX(3);
  private CANSparkMax shootMotor = new CANSparkMax(RobotMap.Shooter.shootMotorID, MotorType.kBrushless);

  
  public Shooter() {

  }


  /**
   * To be called in a execution loop
   *  
   * @param velocity in units of ft/s
   */
  public void setVelocity (double targetVelocity) {
  getVelocity();
  kPI(targetVelocity);
  setPower(kOut); //kOut is the kPI output
  }

  /**
   * @deprecated
   */
  public void directPower (double power) {
  setPower(power);
  }

  /**
   * lightweight PI loop
   */
  private void kPI(double targetVelocity) {
    targetVelocity = (targetVelocity > maxVelocity) ? maxVelocity : 0;
    error = targetVelocity - currentVelocity;
    integral += error * processTime;
    kOut = kP*error + kI*integral;
  }

/**
 * Units of ft/s
 */
  private void getVelocity() {
    this.currentVelocity = (((shootMotor.getEncoder().getVelocity()) * 42) / .75) * conversionConstant; // ((1 rpm * 42 ticks) / .75 [ gear reduction]) * conversionConstant
  }
  
  private void setPower (double power) {
    shootMotor.set(power);
  }

  @Override
  public void periodic() {}
}
