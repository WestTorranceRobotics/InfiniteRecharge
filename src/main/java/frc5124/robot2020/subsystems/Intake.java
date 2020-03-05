package frc5124.robot2020.subsystems;
import frc5124.robot2020.RobotMap;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;


public class Intake implements Subsystem {

  private Solenoid armSolenoid;         //for pivot of the arm
  private CANSparkMax intakeMotor;          //motor
  private boolean deployed;    
  private ShuffleboardTab display;

  public Intake() {
      armSolenoid = new Solenoid(RobotMap.modNumSolenoid, RobotMap.IntakeMap.intakeSolenoid);         // mod num & channel num         
      intakeMotor = new CANSparkMax(RobotMap.IntakeMap.rollerCanId, MotorType.kBrushless);         //establish can id and controller type
      intakeMotor.restoreFactoryDefaults();         //resets things like follwers and such.
      intakeMotor.setInverted(false);
      deployed = false;         // pivot is up
      // Shuffleboard.update();
      // if (RobotMap.debugEnabled) {
      //   debuggingTab = Shuffleboard.getTab("Intake Debug");
      //   debuggingTab.addNumber("Intake Motor Current", intakeMotor::getOutputCurrent)
      //   .withPosition(0, 0).withSize(3, 2).withWidget(BuiltInWidgets.kGraph);
      // }
  }

  @Override
  public void periodic() {
    // Put code here to be run every loop
  }

  public void setDeployed(boolean deployed) {
    armSolenoid.set(deployed);
  
    this.deployed = deployed;
  }

  public boolean isDeployed() {
    return deployed;
  }

  public void setIntakePower(double power){
    intakeMotor.set(power);
    // if (power != 0) {
      // SmartDashboard.putBoolean("IntakeRunning", true);
    // } else {
      // SmartDashboard.putBoolean("IntakeRunning", false);
    // }
  }

  public void flushOut(){
    armSolenoid.set(true);
    intakeMotor.set(RobotMap.IntakeMap.flushOutSpeed);
  }

  public double getOutput() {
    return intakeMotor.getAppliedOutput();
  }
}