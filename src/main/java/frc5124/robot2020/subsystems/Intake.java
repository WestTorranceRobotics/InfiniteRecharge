package frc5124.robot2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;

public class Intake implements Subsystem {
    public DoubleSolenoid intakePivot;
    public CANSparkMax intakeMotor; 
  
  public Intake() {

    intakePivot = new DoubleSolenoid(0, 1);
    intakeMotor = new CANSparkMax(1, MotorType.kBrushless);

  }

  @Override
  public void periodic() {
  }
  public void intake(){
    intakeMotor.set(RobotMap.Intake.motorPower);
  }

  public void outtake(){
    intakeMotor.set(-RobotMap.Intake.motorPower);
  }

  public void motorNoPower(){
    intakeMotor.set(0);
  }

  public void liftUp(){
    intakePivot.set(Value.kForward);          // kForward Value makes it stay up. 
  }

  public void liftDown(){
    intakePivot.set(Value.kReverse);          // kReverse Value will make the arm come out.  
  }
}