package frc5124.robot2020.subsystems;
import frc5124.robot2020.RobotMap;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Intake implements Subsystem {

  private Solenoid armSolenoid;         //for pivot of the arm
  private CANSparkMax rollerSpeedController;          //motor
  private boolean deployed;         //need to create the toggle for the pivot 

  public Intake() {
      armSolenoid = new Solenoid(0, 1);         // mod num & channel num         
      rollerSpeedController = new CANSparkMax(RobotMap.Intake.rollerCanId, MotorType.kBrushless);         //establish can id and controller type
      rollerSpeedController.setInverted(false);
      rollerSpeedController.restoreFactoryDefaults();         //reestablish can ids
      deployed = false;         // pivot is up 
  }

  @Override
  public void periodic() {
    // Put code here to be run every loop
  }

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public void setDeployed(boolean deployed) {
    armSolenoid.set(deployed);
    this.deployed = deployed;
  }

  public boolean isDeployed() {
    return deployed;
  }

  public void setIntakePower(double power){
    rollerSpeedController.set(power);
  }
}