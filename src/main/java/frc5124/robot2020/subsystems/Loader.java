/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                  s             */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.util.Color;
import frc5124.robot2020.RobotMap;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// import com.revrobotics.ultraSonicSensor;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Loader implements Subsystem {

  DoubleSolenoid CylinderDoubleSol = new DoubleSolenoid(1, 2);
  I2C.Port i2cPort = I2C.Port.kOnboard;
  // UltraSonicSensor ultraSonicSensor = new UltraSonicSensor(analog1);
  // ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);    
  // ColorMatch m_colorMatcher = new ColorMatch();
  // Color detectedColor = m_colorSensor.getColor();
  // ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
  // Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
  CANSparkMax topBeltMotor = new CANSparkMax(3, MotorType.kBrushless);
  CANSparkMax bottomBeltMotor = new CANSparkMax(4, MotorType.kBrushless);
  XboxController controller = new XboxController(5);
  int pressx = 0;
  
  public Loader() throws InterruptedException {

   //Reads value from the hasball method, if there is one it intakes the ball(need to change how long blueBeltMotor is running.
   if (hasBall()){
    topBeltMotor.set(1);
     bottomBeltMotor.set(1);
     wait(1000);
     topBeltMotor.set(0);
     bottomBeltMotor.set(0);
   }

   //If x is pressed it runs the flipCylinder method.
   if(controller.getXButton()){
      pressx++;
      if(pressx == 3){
        pressx = 0;
      }
      flipCylinder(pressx);
   }
  }

  //This is the flipCylinder method. Based on its input it goes into one of three modes. 
  //Mode one causes the cylinder to go forward, mode 2 causes cylinder to go backward, and mode 3 turns the cylinder off.
  public void flipCylinder(int x) throws InterruptedException {
    if(x == 0){
      CylinderDoubleSol.set(DoubleSolenoid.Value.kForward); 
    }
    else if(x == 1){
      CylinderDoubleSol.set(DoubleSolenoid.Value.kReverse);
    }
    else if(x == 2){
      CylinderDoubleSol.set(DoubleSolenoid.Value.kOff);
    }
  }

  //This is the hasBall function. It assumes that the ultrasonicsensor is placed level with the top belt and is facing down
  public boolean hasBall(){
    return true;
    // return (ultraSonicSensor.getRangeMM() < 164);
  }

  //This was here when I started so I left it that way.
  @Override
  public void periodic() {
    
  }

  // TODO:
    // Connect everything to the correct ports
    // Test if this actually works and edit accordingly if it does not 
    //Fix the wait time when bottomBeltMotor is running
}
