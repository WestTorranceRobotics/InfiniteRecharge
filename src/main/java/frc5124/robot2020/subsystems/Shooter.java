package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter implements Subsystem {
  private CANSparkMax shootMotorFollower = new CANSparkMax(RobotMap.ShooterMap.shootFollowerCanID, MotorType.kBrushless);
  private CANSparkMax shootMotorLeader = new CANSparkMax(RobotMap.ShooterMap.shootLeaderCanID, MotorType.kBrushless);
  private CANPIDController shootPID; 
  private double currentVelocity;
  private Solenoid shootSolenoid = new Solenoid(RobotMap.modNumSolenoid, RobotMap.ShooterMap.shootSolenoidNum);
 
  public Shooter() {
    shootMotorFollower.follow(shootMotorLeader, true);
    shootPID = shootMotorLeader.getPIDController();
    shootPID.setD(RobotMap.ShooterMap.Kd);
    shootPID.setP(RobotMap.ShooterMap.Kp);
    shootPID.setFF(RobotMap.ShooterMap.Kf);
    shootPID.setReference(0, ControlType.kVelocity);
    shootPID.setOutputRange(-600, 600);
    
  }

  /**
   * @param targetRPM desired RPM of shooter
   */
  public void startShooter() {
    shootPID.setReference(RobotMap.ShooterMap.lineRefRPM, ControlType.kVelocity);
    }

    public void stopShooter () {
      shootPID.setReference(0, ControlType.kVelocity);
    }
  

/**
 * Units of ft/s
 */
  public double getVelocity() {
    return (shootMotorLeader.getEncoder().getVelocity()); 
   }
  
  /**
   * 
   */
  public void directPower (double power) {
    shootMotorLeader.set(power);
  }

  public boolean atSpeed () {
    return true;
  }

  public boolean EntryHoleOpenedOrClose(){
    if (shootSolenoid.get()){
      return true;
    }
    else{
      return false;
    }
  }

  public void openEntryHole(){
    shootSolenoid.set(true);
  }

  public void closeEntryHole(){
    shootSolenoid.set(false);
  }

  @Override
  public void periodic() {
  }
}