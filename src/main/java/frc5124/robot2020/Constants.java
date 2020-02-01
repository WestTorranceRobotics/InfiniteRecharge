package frc5124.robot2020;

public class Constants {

    public static class RobotMap {
        public static class CanId {
            public final static int driveLeftLeader    = 0;
            public final static int driveLeftFollower  = 1;
            public final static int driveRightLeader   = 2;
            public final static int driveRightFollower = 3;

            public final static int intakeArm = 4;
            public final static int intakeRoller = 5;

            public final static int loaderTopBelt = 6;
            public final static int loaderBottomBelt = 7;

            public final static int shooterWheel = 8;
            public final static int shooterTurret = 9;

        }
    }

    public static class RobotStructure {
        public static class DriveTrain {
            public final static double trackWidth = 30;
            public final static double maxMotorSpeed = 100;
        }
    }

    public static class OI {
        public static class Driver {
            public final static int quickTurnButton = 0;
        }
        public static class Operator {

        }
    }

    public enum Button {
        kBumperLeft(5),
        kBumperRight(6),
        kStickLeft(9),
        kStickRight(10),
        kA(1),
        kB(2),
        kX(3),
        kY(4),
        kBack(7),
        kStart(8);

      public final int value;

      Button(int value) {
        this.value = value;
      }
    }

    	/**
	 * Motor neutral dead-band, set to the minimum 0.1%.
	 */
	public final static double kNeutralDeadband = 0.001;
    
    	/**
	 * Set to zero to skip waiting for confirmation.
	 * Set to nonzero to wait and report to DS if action fails.
	 */
    public final static int kTimeoutMs = 30;
    
    public final static class Gains {
        public final double kP;
        public final double kI;
        public final double kD;
        public final double kF;
        public final int kIzone;
        public final double kPeakOutput;
        
        public Gains(double _kP, double _kI, double _kD, double _kF, int _kIzone, double _kPeakOutput){
            kP = _kP;
            kI = _kI;
            kD = _kD;
            kF = _kF;
            kIzone = _kIzone;
            kPeakOutput = _kPeakOutput;
        }
    }

    	/**
	 * PID Gains may have to be adjusted based on the responsiveness of control loop.
     * kF: 1023 represents output value to Talon at 100%, 6800 represents Velocity units at 100% output
     * Not all set of Gains are used in this project and may be removed as desired.
     * 
	 * 	                                    			  kP   kI   kD   kF               Iz    PeakOut */
	public final static Gains kGains_Distanc = new Gains( 0.1, 0.0,  0.0, 0.0,            100,  0.50 );
	public final static Gains kGains_Turning = new Gains( 2.0, 0.0,  4.0, 0.0,            200,  1.00 );
	public final static Gains kGains_Velocit = new Gains( 0.1, 0.0, 20.0, 1023.0/6800.0,  300,  0.50 );
    public final static Gains kGains_MotProf = new Gains( 1.0, 0.0,  0.0, 1023.0/6800.0,  400,  1.00 );
    
}
