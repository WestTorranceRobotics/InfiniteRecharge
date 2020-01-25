/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands;

import java.util.Set;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.Robot;
import frc5124.robot2020.RobotContainer;
import frc5124.robot2020.subsystems.DriveTrain;

public class JoystickTankDrive implements Command {

    private final DoubleSupplier leftHand;
    private final DoubleSupplier rightHand;
    private final DriveTrain driveTrain;
    private RobotContainer robotContainer;

    public JoystickTankDrive(GenericHID twoHanded, DriveTrain driveTrain) {
        leftHand = () -> twoHanded.getY(Hand.kLeft);
        rightHand = () -> twoHanded.getY(Hand.kRight);
        this.driveTrain = driveTrain;
    }

    public JoystickTankDrive(GenericHID leftHand, GenericHID rightHand, DriveTrain driveTrain) {
        this.leftHand = leftHand::getY;
        this.rightHand = rightHand::getY;
        this.driveTrain = driveTrain;
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return Set.of(driveTrain);
    }

    @Override
    public void execute() {
        driveTrain.tankDrive(-leftHand.getAsDouble(), -rightHand.getAsDouble());
        
    }
}
