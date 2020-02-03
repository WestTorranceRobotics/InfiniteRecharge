/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands;

import java.util.Set;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.subsystems.Turret;

public class TargetTracker implements Command {

    private final Turret turret;
    private final PIDController turretPid;

    public TargetTracker(Turret turret) {
        this.turret = turret;
        turretPid = new PIDController(RobotMap.Turret.P, RobotMap.Turret.I, RobotMap.Turret.D);
        turretPid.setIntegratorRange(-RobotMap.Turret.percentSpeedLimit, RobotMap.Turret.percentSpeedLimit);
        turretPid.setTolerance(0.05);
    }

    @Override
    public void initialize() {
        turretPid.reset();
    }

    @Override
    public void execute() {
        turret.setPower(safety(turretPid.calculate(0, turret.getTargetTx())));
    }

    @Override
    public void end(boolean interrupted) {
        turret.setPower(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return Set.of(turret);
    }

    private double safety(double value) {
        if (value > RobotMap.Turret.percentSpeedLimit) {
            value = RobotMap.Turret.percentSpeedLimit;
        }
        if (value < -RobotMap.Turret.percentSpeedLimit) {
            value = -RobotMap.Turret.percentSpeedLimit;
        }
        return value;
    }
}
