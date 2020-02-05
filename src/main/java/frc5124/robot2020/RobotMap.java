package frc5124.robot2020;



public class RobotMap {

    public static class DriveTrain {
        public static int rightLeaderCanId = 1;
        public static int rightFollowerCanId = 2;
        public static int leftLeaderCanId = 4;
        public static int leftFollowerCanId = 3;
      
        public static double P = 1;
        public static double I = 0.1;
        public static double D = 0.01;
        public static double F = 0.5;
    }

    public static class PanelController {
        public static final int spinnerCanId = 11;
        public static final int deployerSolenoidChannel = 0;

    }
    public static class Intake {
        public static int intakeCanId = 11;
        public static double motorPower = 0.5;
    }

    public static class Loader{
        public static int topBeltCanId = 6;
        public static int bottomBeltCanId = 7;
    }

    public static class Hanger {
        public static double hangerMotor = 0.5;   
        public static double hangerHalt = 0;
        public static int hangerMotorCanId = 5;
        public static int limitChannelID = 1;             //DIO port    
    }
  
    public static class Shooter {
        public static int shootLeaderCanID = 11; 
        public static int shootFollowerCanID = 99; 
        public static double Kp = 0;
        public static double Ki = 0;
        public static double Kd = 0;
        public static double Kf = .195;
        public static double period = .05;
        public static double conversionConstant = 2 * 3.141592654 * .33333 * .75; 
        public static double maxVelocity = 99; //ft/s
    }
}

