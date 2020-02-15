/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.subsystems.*;

public class ShootFiveBalls extends CommandBase {
  
  private final Shooter shooter;
  private final Loader  loader;
  private final Turret  turret;

  private final NetworkTableEntry horizontalAngle;
  private final NetworkTableEntry verticalAngle;

  private double loaderPos0;
  private boolean finished;

  public ShootFiveBalls(Shooter shooter, Loader loader, Turret turret) {
    this.shooter = shooter;
    this.loader  = loader;
    this.turret  = turret;
    horizontalAngle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx");
    verticalAngle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty");
    addRequirements(shooter, loader, turret);
  }

  @Override
  public void initialize() {
    loaderPos0 = loader.getEncoder();
    loader.stopBelt();
    turret.enableTurretPID();
    shooter.enablePID();
    finished = false;
  }

  @Override
  public void execute() {
    double xErr = horizontalAngle.getDouble(0);
    double target = turret.getDegrees() - xErr;
    turret.setTurretDegrees(target);

    double rpm = getRpmTarget();
    shooter.startShooter(rpm);

    if (Math.abs(shooter.getVelocity() - rpm) < rpm / 100 && Math.abs(xErr) < 1) {
      loader.runBelt();
      if ((loader.getEncoder() - loaderPos0) / RobotMap.LoaderMap.motorRotationsPerBall > 5) {
        finished = true;
      }
    }
    // no "else turn off", because it is assumed that once the loader is on,
    // it can run continously and the flywheel will keep up
  }

  private double getRpmTarget() {
    // return RobotMap.ShooterMap.lineShootRPM;
    double ty = verticalAngle.getDouble(0) + RobotMap.TurretMap.limelightAngle;
    double dv = RobotMap.TurretMap.targetHeight - RobotMap.TurretMap.limelightHeight;
    double dst = dv/Math.tan(ty);
    int len = RobotMap.ShooterMap.regressionCoefficients.length;
    // regression type: polynomial divided by a monomial, with monomial degree one less than that of the polynomial
    // note that this isnt a very good regression, and the number of terms needed may be relatively large for the amount of data
    double sum = 0;
    double pow = 1;
    double divide = 1;
    for (int i = 0; i < len; i++) {
      sum += pow * RobotMap.ShooterMap.regressionCoefficients[len - i - 1];
      if (i == len - 2) {
        divide = pow;
      }
      pow *= dst;
    }
    return sum / divide;
  }

  @Override
  public void end(boolean interrupted) {
    loader.stopBelt();
    shooter.stopShooter();
    turret.disableTurretPID();
  }

  @Override
  public boolean isFinished() {
    return finished;
  }
}
