/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;
import frc5124.robot2020.RobotMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class Turret implements Subsystem {

  private CANSparkMax turretMotor;
  //private VictorSP turretMotor;
  
  public Turret() {
    turretMotor = new CANSparkMax(RobotMap.turretCanId, MotorType.kBrushless);
  //  turretMotor = new VictorSP(1);
  }

  @Override
  public void periodic() {
  }

  public void turnTurretPos(){
    turretMotor.set(0.3);
  }

  public void turnTurretNeg(){
    turretMotor.set(-.3);
  }

  public void stop(){
    turretMotor.set(0.0);
  }
  
  // Add logic and sensor to tell if the turret has turned to it max rotation to avoid tangling cables
  public boolean isAtTurnLimit() {
    return false;
  }
}
