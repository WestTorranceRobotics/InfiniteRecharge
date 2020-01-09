
package frc5124.robot2020.subsystems;
// import frc5124.robot2020.Constants;
import frc5124.robot2020.Constants.RobotMap;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.PWMVictorSPX;

/**
 * 
 */
public class Intake implements Subsystem {

private PWMVictorSPX armSpeedController;
private PIDController deployArmPIDController;
private PWMVictorSPX rollerSpeedController;

    public Intake() {

        armSpeedController = new PWMVictorSPX(RobotMap.CanId.intakeArm);
        armSpeedController.setInverted(false);

        // need to add a REV through-bore encoder for the PID controller

        deployArmPIDController = new PIDController(1.0, 0.0, 0.0, 0.0);
        deployArmPIDController.setTolerance(0.2);
                
        rollerSpeedController = new PWMVictorSPX(RobotMap.CanId.intakeRoller);
        rollerSpeedController.setInverted(false);

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
        deployArmPIDController.setSetpoint(1.0);
    }

    public void retract() {
        deployArmPIDController.setSetpoint(0.0);
    }
}

