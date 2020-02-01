// test
package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

public class Turret extends PIDSubsystem {

    private int kTurretMotorPort = 3;
    public static final int[] kEncoderPorts = new int[]{0, 1};

    private double toleranceRPS = 0.2;
    private double kEncoderDistancePerPulse = 4096.0/4.0/360.0;
    private double kTurretRPS = 1.0;

    private final PWMVictorSPX m_turret_motor = new PWMVictorSPX(kTurretMotorPort);
    private final Encoder m_turret_encoder = new Encoder(0, 1, false);

    private double kSVolts = 0.05;
    private double kVVoltSecondsPerRotation = 12.0;
    private final SimpleMotorFeedforward m_shooterFeedforward =
        new SimpleMotorFeedforward(kSVolts, kVVoltSecondsPerRotation);

    public Turret(double targetAngle) {
        super(new PIDController(1.0, 0.0, 0.0));
        getController().setTolerance(toleranceRPS);
        m_turret_encoder.setDistancePerPulse(kEncoderDistancePerPulse);
        setSetpoint(targetAngle);
    }

    @Override
    public void useOutput(double output, double setpoint) {
        m_turret_motor.setVoltage(output + m_shooterFeedforward.calculate(setpoint));
    }

    @Override
    public double getMeasurement() {
        return m_turret_encoder.getDistance();
    }

    public boolean atSetpoint() {
        return m_controller.atSetpoint();
    }

    public void resetEncoder() {
        m_turret_encoder.reset();
    }
}
