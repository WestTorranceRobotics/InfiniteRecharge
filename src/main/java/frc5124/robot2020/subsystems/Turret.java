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
  public boolean limitReached = false;
  private CANSparkMax turretMotor;
  //private VictorSP turretMotor;
  
  public Turret() {
    turretMotor = new CANSparkMax(RobotMap.TurretMap.turretCanID, MotorType.kBrushless);
   //turretMotor = new VictorSP(1);
  }

  public void rotateTurret(double power) {
    if (limitReached && turretMotor.getAppliedOutput() == 0) {
      
    } else if (limitReached) {
      turretMotor.set(0);
      
    } else if (!limitReached && turretMotor.getAppliedOutput() != power) {
      turretMotor.set(power);
      
    }

  }

  public int getEncoder(){
    return turretMotor.getEncoder().getCountsPerRevolution();
  }

  private boolean limitReached() {
    return true;
  }
  
}