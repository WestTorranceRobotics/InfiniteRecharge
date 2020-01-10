
package frc5124.robot2020.subsystems;
// import frc5124.robot2020.Constants;
import frc5124.robot2020.Constants.RobotMap;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;

/*
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
*/

/**
 * 
 */
public class Intake implements Subsystem {

private WPI_TalonSRX armSpeedController;
private Solenoid armSolenoid;
private WPI_VictorSPX rollerSpeedController;
private final int encoderTicksPerRevolution = 4096; // at motor output shaft; consider gear ratio

    public Intake() {

        armSpeedController = new WPI_TalonSRX(RobotMap.CanId.intakeArm);
        armSpeedController.setInverted(false);
        armSpeedController.set(0);
        
        armSolenoid = new Solenoid(0, 0);

        rollerSpeedController = new WPI_VictorSPX(RobotMap.CanId.intakeRoller);
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
        armSpeedController.set(ControlMode.Position, angleToEncTicks(90));
        armSolenoid.set(true);
    }

    public void retract() {
        armSpeedController.set(ControlMode.Position, angleToEncTicks(-90));
        armSolenoid.set(false);
    }

    public boolean atSetpoint() {
        return (armSpeedController.get() <= 0.1);
    }
    
   // Convert angle in degrees to encoder ticks
     public double angleToEncTicks (double angle) {
        return ((angle * encoderTicksPerRevolution) / 360);
    }
}

