/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;

public class TargetTracker extends CommandBase {

    private final Turret turret;
    private final CANPIDController turretPid;
    private final NetworkTable limelight;

    public TargetTracker(Turret turret) {
        limelight = NetworkTableInstance.getDefault().getTable("limelight");

        this.turret = turret;
        turretPid = turret.getController();
    }

    @Override
    public void initialize() {
        turretPid.setReference(0, ControlType.kDutyCycle);
    }

    @Override
    public void execute() {
        turretPid.setReference(limelight.getEntry("tx").getDouble(0) + turret.getEncoder(), ControlType.kSmartMotion);
    }

    @Override
    public void end(boolean interrupted) {
        turretPid.setReference(0, ControlType.kDutyCycle);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
