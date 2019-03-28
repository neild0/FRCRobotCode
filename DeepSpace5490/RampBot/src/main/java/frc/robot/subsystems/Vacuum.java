package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;



/**
 * 
 */
public class Vacuum extends Subsystem {
	
    // Climb CAM objects
    Talon motorVac= new Talon(RobotMap.mtrVaccum);    

    // TODO determine direction for transfer
    private static final double motor_wind_direction = -1;
    private static final double motorSpeed = 1;

    
   

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    	
    	
		// Define current limiting
		/*
    	motorCAM.configContinuousCurrentLimit(10, 0);
    	motorCAM.configPeakCurrentLimit(15, 0);
    	motorCAM.configPeakCurrentDuration(100, 0);
    	motorCAM.enableCurrentLimit(true);    	
		motorCAM.configOpenloopRamp(2, 0);
		*/
    	

    }
    
    public void log() {
    	// winch motor info
    	SmartDashboard.putData("Vacuum motor", motorVac);
       	SmartDashboard.putNumber("Transfer Speed", motorVac.get());
    	
    	// limit switches
    	//SmartDashboard.putBoolean("Transfer forward?", this.isTransferForward());
    	//SmartDashboard.putBoolean("Transfer back?", this.isTransferBack());
	}


	/**
	 * Set the vacuum motor to move in the spooled direction. Percent ranges from 0 to 1.
	 */
	public void run() {
		motorVac.set(motorSpeed * motor_wind_direction);
	}

	/**
	 * Stops the vacuum motor from moving.
	 */
	public void stop() {
		motorVac.set(0);
	}
}

