package frc5124.robot2020;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.XboxController;
import java.util.ArrayList;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {
    Orchestra _orchestra;
    XboxController operator = new XboxController(0);

    TalonFX [] _fxes =  { new TalonFX(1), new TalonFX(2) };
    int _lastPOV = 0;

    @Override
    public void robotInit() {
        ArrayList<TalonFX> _instruments = new ArrayList<TalonFX>();
        for (int i = 0; i < _fxes.length; ++i) {
            _instruments.add(_fxes[i]);
        }
        _orchestra = new Orchestra(_instruments);
    }
    
    @Override
    public void teleopInit() {
        _orchestra.loadMusic("StarWars.chrp");
    }

    @Override
    public void teleopPeriodic() {
        int currentPOV = operator.getPOV();
        /* has a button been pressed? */
        if (_lastPOV != currentPOV) {
            _lastPOV = currentPOV;

            switch (currentPOV) {
                case 90: /* toggle play and paused */
                    if (_orchestra.isPlaying()) {
                        _orchestra.pause();
                        System.out.println("Song paused");
                    }  else {
                        _orchestra.play();
                        System.out.println("Playing song...");
                    }
                    break;
                    
                case 180: /* toggle play and stop */
                    if (_orchestra.isPlaying()) {
                        _orchestra.stop();
                        System.out.println("Song stopped.");
                    }  else {
                        _orchestra.play();
                        System.out.println("Playing song...");
                    }
                    break;
            }
        }

    }
}
