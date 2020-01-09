
package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc5124.robot2020.Constants.RobotMap;


/**
 *
 */
public class Loader implements Subsystem {
    private PWMVictorSPX topBeltSpeedController;
    private PWMVictorSPX bottomBeltSpeedController;
    private SpeedControllerGroup beltSpeedControllerGroup;
    private double beltSpeed = 0.5;
    
    public Loader() {
        topBeltSpeedController = new PWMVictorSPX(RobotMap.CanId.loaderTopBelt);
        topBeltSpeedController.setInverted(false);
        
        bottomBeltSpeedController = new PWMVictorSPX(RobotMap.CanId.loaderBottomBelt);
        bottomBeltSpeedController.setInverted(true);
        
        beltSpeedControllerGroup = new SpeedControllerGroup(topBeltSpeedController, bottomBeltSpeedController);
    }


    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void ballsIn() {
        beltSpeedControllerGroup.set(beltSpeed);
    }

    public void ballsOut() {
        beltSpeedControllerGroup.set(-beltSpeed);
    }

    public void ballsStop() {
        beltSpeedControllerGroup.set(0);
    }
}

