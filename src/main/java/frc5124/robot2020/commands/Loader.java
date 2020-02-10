/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.Subsystem;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.XboxController;
import frc5124.robot2020.RobotMap;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Loader implements Subsystem {

  DoubleSolenoid CylinderDoubleSol = new DoubleSolenoid(1, 2);
  I2C.Port i2cPort = I2C.Port.kOnboard;
  AnalogInput ultraSonicSensor = new Ultra;
  CANSparkMax topBeltMotor = new CANSparkMax(3, MotorType.kBrushless);
  CANSparkMax bottomBeltMotor = new CANSparkMax(4, MotorType.kBrushless);
  XboxController controller = new XboxController(5);
  int pressx = 0;
  
  public Loader() throws InterruptedException {

   //Reads value from the hasball method, if there is one it intakes the ball(need to change how long blueBeltMotor is running.
  //  if (hasBall()){
  //    runBelt(1000);
  //  }
  //moved to SeeBallRunBeltAndFlipCylin command

  //If x is pressed it runs the flipCylinder method.
  //  if(controller.getXButton()){
  //     pressx++;
  //     if(pressx == 3){
  //       pressx = 0;
  //     }
  //     flipCylinder(pressx);
  //  }
  // }
  //also moved to SeeBallRunBeltAndFlipCylin command

  
  public void runBelt(int milliseconds) throws InterruptedException {
    topBeltMotor.set(1);
    bottomBeltMotor.set(1);
    wait(milliseconds);
    topBeltMotor.set(0);
    bottomBeltMotor.set(0);
  }
  //This is the flipCylinder method. Based on its input it goes into one of three modes. 
  //Mode 0 causes the cylinder to go forward, mode 1 causes the cylinder to go backward, and mode 2 turns the cylinder off.
  public void flipCylinder(int x) throws InterruptedException {
    if (x == 0){
      CylinderDoubleSol.set(DoubleSolenoid.Value.kForward); 
    }
    else if (x == 1){
      CylinderDoubleSol.set(DoubleSolenoid.Value.kReverse);
    }
    else {
      CylinderDoubleSol.set(DoubleSolenoid.Value.kOff);
    }
  }

  //This is the hasBall function. It assumes that the ultrasonicsensor is placed level with the top belt and is facing down
  public boolean hasBall(){
    return (ultraSonicSensor.getRangeMM() < 164);
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
a