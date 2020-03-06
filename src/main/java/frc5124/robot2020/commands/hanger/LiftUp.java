package frc5124.robot2020.commands.hanger;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.subsystems.Hanger;
import frc5124.robot2020.subsystems.LED;
import jdk.jfr.Timespan;

public class LiftUp extends CommandBase {

    private final Hanger m_hanger;
    private LED led;
    private double lastSwitch = 0;
    private Timer colorTimer = new Timer();
    private double delaySeconds = RobotMap.LEDMap.delaySeconds;
    private boolean isViolet = false;
    private boolean isHotPink = false;

    

    public LiftUp(Hanger subsystem, LED led) {
        m_hanger = subsystem;
        this.led = led;
        addRequirements(m_hanger);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_hanger.liftUp();
        colorTimer.reset();
        colorTimer.start();
        isViolet = false;
        isHotPink = true;
        lastSwitch = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        if ((m_hanger.getPosition() >= RobotMap.HangerMap.upperLimit)) {
            m_hanger.setNoPower();
          } 

          if ((colorTimer.get() - lastSwitch) >= delaySeconds && isHotPink) {
            led.setLED(LED.Color.violet);
            isHotPink = false;
            isViolet = true;
            lastSwitch = colorTimer.get();
          } else if ((colorTimer.get() - lastSwitch) >= delaySeconds && isViolet) {
            led.setLED(LED.Color.hotPink);
            isHotPink = true;
            isViolet = true;
            lastSwitch = colorTimer.get();
          }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false; 
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_hanger.setNoPower();
        colorTimer.stop();
        led.setLED(led.defaultColor);
    }
}