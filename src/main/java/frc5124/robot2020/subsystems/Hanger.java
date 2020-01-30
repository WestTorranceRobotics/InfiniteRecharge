package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 */
public class Hanger extends Subsystem {

    private PWMVictorSPX liftSpeedController;
    private Encoder quadratureEncoder;
    // private PIDController liftPIDController;
    private Solenoid brakeSolenoid;

 
    public Hanger() {
        liftSpeedController = new PWMVictorSPX(10);
        addChild("Lift Speed Controller",liftSpeedController);
        liftSpeedController.setInverted(false);
                
        quadratureEncoder = new Encoder(2, 5, false, EncodingType.k4X);
        addChild("Quadrature Encoder",quadratureEncoder);
        quadratureEncoder.setDistancePerPulse(1.0);
       //  quadratureEncoder.setPIDSourceType(PIDSourceType.kRate);
     /*           
        liftPIDController = new PIDController(1.0, 0.0, 0.0, 0.0, quadratureEncoder, liftSpeedController, 0.02);
        addChild("Lift PID Controller",liftPIDController);
        liftPIDController.setContinuous(false);
        liftPIDController.setAbsoluteTolerance(0.2);

        liftPIDController.setOutputRange(-1.0, 1.0);
    */
        brakeSolenoid = new Solenoid(0, 1);
        addChild("Brake Solenoid",brakeSolenoid);
    }

    @Override
    public void initDefaultCommand() {

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}

