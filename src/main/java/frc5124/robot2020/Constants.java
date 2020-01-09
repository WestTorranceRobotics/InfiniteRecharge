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
}
