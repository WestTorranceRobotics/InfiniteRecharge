package frc5124.robot2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;

public class Intake implements Subsystem {
    private Solenoid intakePivot;
    private CANSparkMax intakeMotor; 
  
  public Intake() {
   // intakePivot = new Solenoid(0, 1);
    intakeMotor = new CANSparkMax(11, MotorType.kBrushless);
  }

  @Override
  public void periodic() {
  }
  public void intake(){

    intakeMotor.set(1);
  }

  public void outtake(){
    intakeMotor.set(-1);
  }

  public void motorNoPower(){
    intakeMotor.set(0);
  }

  public void liftUp(){
    intakePivot.set(true);          // kForward Value makes it stay up. 
  }

  public void liftDown(){
    intakePivot.set(false);          // kReverse Value will make the arm come out.  
  }
}