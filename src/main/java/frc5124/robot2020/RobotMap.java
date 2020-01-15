package frc5124.robot2020;

import com.revrobotics.SparkMax;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class RobotMap { 
    public static DoubleSolenoid intakeDeploy;
    public static SparkMax ballIntake;
    
    public static class Intake {
        public static DoubleSolenoid intakeDeploy = new DoubleSolenoid(1,2);                 // for double solenoid (forward channel, reverse channel)
        public static int ballIntake = 3;                                                    // for spark max
    }
}
