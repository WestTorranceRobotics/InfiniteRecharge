
package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc5124.robot2020.Constants.RobotMap;


/**
 *
 */
public class Loader implements Subsystem {
    // private PWMVictorSPX topBeltSpeedController;
    // private PWMVictorSPX bottomBeltSpeedController;
    // private SpeedControllerGroup beltSpeedControllerGroup;
    private double beltSpeed = 1.0;
    private static final double VOLTS_TO_DIST = 1.0;
    private static final double fieldEmptyDistance = 4.0;

    private CANSparkMax beltSpeedController;
    private AnalogInput ballSensor;

    public Loader() {
        /*topBeltSpeedController = new PWMVictorSPX(RobotMap.CanId.loaderTopBelt);
        topBeltSpeedController.setInverted(false);
        
        bottomBeltSpeedController = new PWMVictorSPX(RobotMap.CanId.loaderBottomBelt);
        bottomBeltSpeedController.setInverted(true);
        */
        beltSpeedController = new CANSparkMax(RobotMap.CanId.loaderTopBelt, MotorType.kBrushless);
        beltSpeedController.setInverted(true);
        
        ballSensor = new AnalogInput(1);
        // beltSpeedControllerGroup = new SpeedControllerGroup(topBeltSpeedController, bottomBeltSpeedController);
    }


    @Override
    public void periodic() {
        // Put code here to be run every loop
        SmartDashboard.putNumber("Ultrasonic Sensor Voltage", getVoltage());
        SmartDashboard.putNumber("Ultrasonic Sensor Distance", getDistance());
        
        SmartDashboard.putBoolean("Ultrasonic Sensor sees ball", seeBall());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void ballsIn() {
        beltSpeedController.set(beltSpeed);
    }

    public void ballsOut() {
        beltSpeedController.set(-beltSpeed);
    }

    public void stop() {
        beltSpeedController.set(0);
    }


    public double getVoltage() {
        return ballSensor.getVoltage();
    }
  
    public double getDistance() {
        return getVoltage() * VOLTS_TO_DIST;
    }

    public boolean seeBall() {
        return (getDistance() < fieldEmptyDistance);
    }
}

