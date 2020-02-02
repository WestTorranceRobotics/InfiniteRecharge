package frc5124.robot2020;

import com.revrobotics.SparkMax;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class RobotMap {
    public static class DriveTrain {
        public static int leftLeaderCanId = 1;
        public static int leftFollowerCanId = 2;
        public static int rightLeaderCanId = 3;
        public static int rightFollowerCanId = 4;

        public static double P = 1;
        public static double I = 0.1;
        public static double D = 0.01;
        public static double F = 0.5;
    }

    public static class Intake {
        public static double motorPower = 0.5;
    }

    public static class Turret {
        public static int spinnerCanId;
        public static String networkTableName = "limelight";
        public static String horizontalTargetEntry = "tx";

        public static double P = 0.01;
        public static double I = 0.001;
        public static double D = 0.0001;

        public static double percentSpeedLimit = 0.2;
    }

    public static class Hanger {
        public static double hangerMotor = 0.5;   
        public static double hangerHalt = 0;
        public static int limitChannelID = 1;             //DIO port    
    }
}