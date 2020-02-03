/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Turret implements Subsystem {

  private TalonSRX turretMotor;
  //private VictorSP turretMotor;
  
  public Turret() {
    turretMotor = new TalonSRX(1);
  //  turretMotor = new VictorSP(1);
  }

  @Override
  public void periodic() {
  }

  public void turnTurretPos(){
    turretMotor.set(ControlMode.PercentOutput, .3);
  }
  public void turnTurretNeg(){
    turretMotor.set(ControlMode.PercentOutput, -.3);
  }
}
