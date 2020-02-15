package frc5124.robot2020.subsystems;
import frc5124.robot2020.RobotMap;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;


public class Intake implements Subsystem {

  private Solenoid armSolenoid;         //for pivot of the arm
  private CANSparkMax rollerSpeedController;          //motor
  private boolean deployed;         //need to create the toggle for the pivot 

  public Intake() {
      armSolenoid = new Solenoid(RobotMap.modNumSolenoid, RobotMap.IntakeMap.intakeSolenoid);         // mod num & channel num         
      rollerSpeedController = new CANSparkMax(RobotMap.IntakeMap.rollerCanId, MotorType.kBrushless);         //establish can id and controller type
      rollerSpeedController.restoreFactoryDefaults();         //resets things like follwers and such.
      rollerSpeedController.setInverted(false);
      deployed = false;         // pivot is up 
      rollerSpeedController.setIdleMode(IdleMode.kBrake);

  }

  @Override
  public void periodic() {
    // Put code here to be run every loop
  }

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

  public void flushOut(){
    armSolenoid.set(true);
    rollerSpeedController.set(-1);
  }
} 