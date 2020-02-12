
package frc5124.robot2020.subsystems;
// import frc5124.robot2020.Constants;
import frc5124.robot2020.RobotMap;
import edu.wpi.first.wpilibj2.command.Subsystem;
//import edu.wpi.first.wpilibj.Solenoid;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Intake implements Subsystem {

  //private Solenoid armSolenoid;

  private CANSparkMax rollerSpeedController;

  public Intake() {
      
      //armSolenoid = new Solenoid(0, 0);

      rollerSpeedController = new CANSparkMax(RobotMap.IntakeMap.rollerCanId, MotorType.kBrushless);
      rollerSpeedController.setInverted(false);
      rollerSpeedController.set(0);
  }

  @Override
  public void periodic() {
      // Put code here to be run every loop

  }

  public void setIntakePower(double power) {
    rollerSpeedController.set(power);

  }

  // Put methods for controlling this subsystem
  // here. Call these from Commands.



  public void deploy() {
  //        armSolenoid.set(true);
  }

  public void retract() {
  //        armSolenoid.set(false);
  }

}
