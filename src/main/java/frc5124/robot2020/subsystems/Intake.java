
package frc5124.robot2020.subsystems;
import frc5124.robot2020.RobotMap;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Intake implements Subsystem {

  private Solenoid armSolenoid;
  private CANSparkMax rollerSpeedController;

  public Intake() {
      
      armSolenoid = new Solenoid(0, 0);

      rollerSpeedController = new CANSparkMax(RobotMap.Intake.intakeRollerCanId, MotorType.kBrushless);
      rollerSpeedController.setInverted(false);
      rollerSpeedController.set(0);
  }

  @Override
  public void periodic() {
      // Put code here to be run every loop

  }

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public void in() {
    rollerSpeedController.set(1.0);
  }

  public void out() {
      rollerSpeedController.set(-1.0);
  }

  public void stop() {
      rollerSpeedController.set(0.0);
  }

  public void deploy() {
      armSolenoid.set(true);
  }

  public void retract() {
      armSolenoid.set(false);
  }

}
