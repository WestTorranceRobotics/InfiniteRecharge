/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.driveTrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.DriveTrain;
import frc5124.robot2020.commands.auto.ChangeCamera;;

public class JoystickTankDrive extends CommandBase {

    private final DriveTrain m_driveTrain;
    private Joystick leftHand;
    private Joystick rightHand;
    private double leftHandIn;
    private double rightHandIn;

    public JoystickTankDrive(Joystick leftHand, Joystick rightHand, DriveTrain subsystem) {
        this.leftHand = leftHand;
        this.rightHand = rightHand;
        m_driveTrain = subsystem;
        addRequirements(m_driveTrain);
    }

    @Override
    public void initialize() {
        new ChangeCamera(ChangeCamera.INTAKE_CAM).schedule();
    }

    @Override
    public void execute() {

        if (leftHand.getY() > 0.1 || rightHand.getY() > 0.1 || rightHand.getY() < -0.1 || leftHand.getY() < -0.1){
            leftHandIn = Math.pow(-leftHand.getY(), 1);
           
        }
       rightHandIn = Math.pow(-rightHand.getY(), 1);
       m_driveTrain.tankDrive(leftHandIn, rightHandIn);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_driveTrain.tankDrive(0,0);
    }
}