/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands;

import java.util.Set;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.Robot;
import frc5124.robot2020.RobotContainer;
import frc5124.robot2020.subsystems.DriveTrain;

public class JoystickTankDrive implements Command {

    private final DriveTrain driveTrain;
    private RobotContainer robotContainer;
    private XboxController xboxController;
    private Joystick leftHand;
    private Joystick rightHand;
    private boolean isXbox;

    public JoystickTankDrive(Joystick leftHand, Joystick rightHand, DriveTrain driveTrain) {
        this.leftHand = leftHand;
        this.rightHand = rightHand;
        this.driveTrain = driveTrain;
        isXbox = false;
    }

    public JoystickTankDrive(XboxController driver, DriveTrain driveTrain) {
        this.xboxController = driver;
        this.driveTrain = driveTrain;
        isXbox = true;
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return Set.of(driveTrain);
    }

    @Override
    public void execute() {

        if(isXbox){
            driveTrain.tankDrive(xboxController.getY(Hand.kLeft),xboxController.getY(Hand.kRight));
        }
        else{
            if (leftHand.getY() > 0.1 || rightHand.getY() > 0.1 || rightHand.getY() < -0.1 || leftHand.getY() < -0.1){
                
                driveTrain.tankDrive(leftHand.getY(), rightHand.getY());
                //left side coast, right side break
            }
        }
    }
}
