/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands;

import java.util.Set;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.subsystems.Turret;

public class TargetTracker implements Command {

    private final Turret turret;
    private final NetworkTableEntry errorSupplier;
    private final PIDController turretPid;

    public TargetTracker(Turret turret) {
        this.turret = turret;
        this.errorSupplier = NetworkTableInstance.getDefault()
            .getTable(RobotMap.Turret.networkTableName)
            .getEntry(RobotMap.Turret.horizontalTargetEntry);
        turretPid = new PIDController(RobotMap.Turret.P, RobotMap.Turret.I, RobotMap.Turret.D);
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return Set.of(turret);
    }
}
