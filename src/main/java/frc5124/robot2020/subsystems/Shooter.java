/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
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
  private final double maxVelocity = 0; //in units of ft/s
  private double kOut = 0;
  private double currentVelocity, error, integral = 0; 
  private double kI, kP = 1; 
  //private TalonSRX jeff = new TalonSRX(3);
  private CANSparkMax shootMotor = new CANSparkMax(RobotMap.Shooter.shootMotorID, MotorType.kBrushless);

  
  public Shooter() {

  }


  /**
   * To be called in a loop
   * 
   * in units of ft/s
   * @param velocity
   */
  public void setVelocity (double targetVelocity) {
  getVelocity();
  kPI(targetVelocity);
  setPower(kOut); //kOut is the kPI output
  }

  public void directPower (double power) {
  setPower(power);
  //jeff.set(ControlMode.PercentOutput, 1);
  }

  /**
   * lightweight PI loop
   */
  private void kPI(double targetVelocity) {
    currentVelocity = (targetVelocity > maxVelocity) ? maxVelocity : 0;
    error = targetVelocity - currentVelocity;
    integral += error*processTime;
    kOut = kP*error + kI*error;
  }

  private void getVelocity() {
    this.currentVelocity = shootMotor.getEncoder().getVelocity();
  }
  
  private void setPower (double power) {
    shootMotor.set(power);
  }

  @Override
  public void periodic() {}
}
