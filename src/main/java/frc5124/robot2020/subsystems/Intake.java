package frc5124.robot2020.subsystems;
import frc5124.robot2020.RobotMap;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Intake implements Subsystem {

  private Solenoid armSolenoid;
  private CANSparkMax rollerSpeedController;
  private boolean deployed;

  public Intake() {
      armSolenoid = new Solenoid(RobotMap.pcmCanId, RobotMap.Intake.armSolenoidId);
    
      rollerSpeedController = new CANSparkMax(RobotMap.Intake.rollerCanId, MotorType.kBrushless);
      rollerSpeedController.setInverted(false);
      rollerSpeedController.set(0);

      deployed = false;
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
