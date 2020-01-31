package frc5124.robot2020;

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
