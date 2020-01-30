package frc5124.robot2020;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class OI {
    
    public static final Joystick driverLeft = new Joystick(0);
    public static final Joystick driverRight = new Joystick(1);
    public static final XboxController operator = new XboxController(2);

    public static final JoystickButton isPressedButton = new JoystickButton(operator, XboxController.Button.kA.value);

}