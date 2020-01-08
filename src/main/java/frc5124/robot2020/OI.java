package frc5124.robot2020;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
    
    public Joystick driver;
    public Joystick operator;

    public OI() {
        operator = new Joystick(1);
        driver = new Joystick(0);
    }

    public Joystick getDriver() {
        return driver;
    }

    public Joystick getOperator() {
        return operator;
    }

}

