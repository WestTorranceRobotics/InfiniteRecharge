package frc5124.robot2020;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class OI {
    
    public static final XboxController driver = new XboxController(0);
    public static final Joystick operator = new Joystick(1);

    public static final JoystickButton isPressedButton = new JoystickButton(driver, XboxController.Button.kA.value);

}