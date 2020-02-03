package frc5124.robot2020;


import java.util.function.IntToDoubleFunction;
import com.revrobotics.ColorSensorV3.RawColor;
import com.revrobotics.SparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid;


public class RobotMap {
    public static final int pcmCanId = 0;
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

        public static final RawColor yellowReading = new RawColor(35000, 60000, 11000, 400); // yellow
        public static final RawColor redReading = new RawColor(22000, 12000, 4400, 250); // red
        public static final RawColor greenReading = new RawColor(6000, 22500, 9000, 250); // green
        public static final RawColor blueReading = new RawColor(6500, 26000, 28000, 440); // blue

        public static final double positionControlPower = 0.1;
        public static final IntToDoubleFunction rotationControlDistanceToPowerFunction =
            (i) -> i > 8 ? 1 : i / 8;
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

    public static class Intake {
        public static double motorPower = 0.5;
    }

    public static class Hanger {
        public static double hangerMotor = 0.5;   
        public static double hangerHalt = 0;
        public static int limitChannelID = 1;             //DIO port    
    }
}

