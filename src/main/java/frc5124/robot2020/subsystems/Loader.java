package frc5124.robot2020.subsystems;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.Subsystem;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.XboxController;
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.commands.UltraSonicSensor;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Loader implements Subsystem {

   // factor to convert sensor values to a distance in inches
   private static final double kValueToInches = 0.125;

  DoubleSolenoid CylinderDoubleSol;
  I2C.Port i2cPort;
  AnalogInput ultraSonicSensor1;
  CANSparkMax topBeltMotor;
  CANSparkMax bottomBeltMotor;
  XboxController controller;
  
  public Loader() {

    CylinderDoubleSol = new DoubleSolenoid(1, 2);
    i2cPort = I2C.Port.kOnboard;
    // ultraSonicSensor1 = new AnalogInput(RobotMap.Loader.ussID1);
    // topBeltMotor = new CANSparkMax(RobotMap.Loader.topBeltCanID, MotorType.kBrushless);
    // bottomBeltMotor = new CANSparkMax(RobotMap.Loader.bottomBeltCanId, MotorType.kBrushless);
    

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
  }

  public void moveOneSpot(){
    
  }

  //This is the flipCylinder method. Based on its input it goes into one of three modes. 
  //Mode 0 causes the cylinder to go forward, mode 1 causes the cylinder to go backward, and mode 2 turns the cylinder off.
  // public void flipCylinder(int x){
  //   if (x == 0){
  //     CylinderDoubleSol.set(DoubleSolenoid.Value.kForward); 
  //   }
  //   else if (x == 1){
  //     CylinderDoubleSol.set(DoubleSolenoid.Value.kReverse);
  //   }
  //   else {
  //     CylinderDoubleSol.set(DoubleSolenoid.Value.kOff);
  //   }
  // }

  //This is the hasBall function. It assumes that the ultrasonicsensor is placed level with the top belt and is facing down
  public boolean hasBall(){
    return (ultraSonicSensor1.getValue() * kValueToInches < 2);
  }


  public double getDistance(){
    return (ultraSonicSensor1.getValue()*kValueToInches);
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
