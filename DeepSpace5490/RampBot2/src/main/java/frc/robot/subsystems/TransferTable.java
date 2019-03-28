package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Talon;
import com.ctre.phoenix.motorcontrol.can.*;


/**
 * 
 */
public class TransferTable extends Subsystem {
	
    // Winch objects
    WPI_TalonSRX motorTransfer= new WPI_TalonSRX(RobotMap.mtrTransfer);    

    // TODO determine direction for transfer
    private static final double motor_wind_direction = -1;
    private static final double motorSpeed = 0.1;

    
    // Limit switches
    private DigitalInput m_lforward = new DigitalInput(RobotMap.ls_transferForward);
	private DigitalInput m_lback = new DigitalInput(RobotMap.ls_transferBack);


    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    	
    	
		// Define current limiting
		/*

    	motorTransfer.configContinuousCurrentLimit(10, 0);
    	motorTransfer.configPeakCurrentLimit(15, 0);
    	motorTransfer.configPeakCurrentDuration(100, 0);
    	motorTransfer.enableCurrentLimit(true);    	
		motorTransfer.configOpenloopRamp(2, 0);
		*/
    	

    }
    
    public void log() {
    	// winch motor info
//    	SmartDashboard.putData("Winch motor", motorWinch);
       	SmartDashboard.putNumber("Transfer Speed", motorTransfer.get());
    	
    	// limit switches
    	SmartDashboard.putBoolean("Transfer forward?", this.isTransferForward());
    	SmartDashboard.putBoolean("Transfer back?", this.isTransferBack());
	}
    
	/**
	 * Set the winch motor to move in the un-spooled direction. Percent ranges from 0 to 1.
	 */
	public void reverse() {
		motorTransfer.set(-motorSpeed * motor_wind_direction);
	}

	/**
	 * Set the winch motor to move in the spooled direction. Percent ranges from 0 to 1.
	 */
	public void forward() {
		motorTransfer.set(motorSpeed * motor_wind_direction);
	}

	/**
	 * Stops the winch motor from moving.
	 */
	public void stop() {
		motorTransfer.set(0);
	}

	/**
	 * Return true when the which lift triggers the "vertical" limit switch.
	 * active low -- note - contact pulls switch low  
	 */
	public boolean isTransferForward() {
		return ! m_lforward.get();
	}
		

	/**
	 * Return true when the which lift triggers the "stored" limit switch.
	 * active low -- note - contact pulls switch low
	 */
	public boolean isTransferBack() {
		return ! m_lback.get();
	}
}

