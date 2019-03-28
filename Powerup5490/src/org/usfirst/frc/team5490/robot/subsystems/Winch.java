package org.usfirst.frc.team5490.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

import org.usfirst.frc.team5490.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


/**
 * 
 */
public class Winch extends Subsystem {
	
    // Winch objects
    WPI_TalonSRX motorWinch= new WPI_TalonSRX(RobotMap.mtrWinch);    

    // TODO determine wind direction for winch
    private static final double motor_wind_direction = -1;
    private static final double motorSpeed = 0.1;

    
    // Limit switches
    private DigitalInput m_lvertical = new DigitalInput(RobotMap.ls_winchVertical);
	private DigitalInput m_lstored = new DigitalInput(RobotMap.ls_winchStored);


    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    	
    	
    	// Define current limiting
    	motorWinch.configContinuousCurrentLimit(10, 0);
    	motorWinch.configPeakCurrentLimit(15, 0);
    	motorWinch.configPeakCurrentDuration(100, 0);
    	motorWinch.enableCurrentLimit(true);    	
    	motorWinch.configOpenloopRamp(2, 0);
    	

    }
    
    public void log() {
    	// winch motor info
//    	SmartDashboard.putData("Winch motor", motorWinch);
       	SmartDashboard.putNumber("Winch Speed", motorWinch.get());
    	
    	// limit switches
    	SmartDashboard.putBoolean("Lift vertical?", this.isLiftVertical());
    	SmartDashboard.putBoolean("Lift stored?", this.isLiftStored());
	}
    
	/**
	 * Set the winch motor to move in the un-spooled direction. Percent ranges from 0 to 1.
	 */
	public void unwind() {
		motorWinch.set(motorSpeed * -1 * motor_wind_direction);
	}

	/**
	 * Set the winch motor to move in the spooled direction. Percent ranges from 0 to 1.
	 */
	public void wind() {
		motorWinch.set(motorSpeed * motor_wind_direction);
	}

	/**
	 * Stops the winch motor from moving.
	 */
	public void stop() {
		motorWinch.set(0);
	}

	/**
	 * Return true when the which lift triggers the "vertical" limit switch.
	 * active low -- note - contact pulls switch low  
	 */
	public boolean isLiftVertical() {
		return ! m_lvertical.get();
	}
		

	/**
	 * Return true when the which lift triggers the "stored" limit switch.
	 * active low -- note - contact pulls switch low
	 */
	public boolean isLiftStored() {
		return ! m_lstored.get();
	}
}

