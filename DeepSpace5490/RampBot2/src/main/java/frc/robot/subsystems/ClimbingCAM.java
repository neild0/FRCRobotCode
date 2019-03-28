package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.SpeedController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// this is for the Talon SRX version
import edu.wpi.first.wpilibj.Talon;
import com.revrobotics.*;

import frc.robot.RobotMap;
import frc.robot.commands.DriveRobot;

//import frc.robot.Point3D;

import frc.robot.HermiteSpline;



import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.AnalogInput;


//import com.analog.adis16448.frc.ADIS16448_IMU;
import frc.robot.subsystems.TransferTable;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.SpeedController;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Talon;

import com.revrobotics.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * 
 */
public class ClimbingCAM extends Subsystem {
	
    // Climb CAM objects
    WPI_TalonSRX motorCAM= new WPI_TalonSRX(RobotMap.mtrClimb);    

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
    	SmartDashboard.putData("Winch motor", motorCAM);
       	SmartDashboard.putNumber("Transfer Speed", motorCAM.get());
    	
    	// limit switches
    	//SmartDashboard.putBoolean("Transfer forward?", this.isTransferForward());
    	//SmartDashboard.putBoolean("Transfer back?", this.isTransferBack());
	}
    
	/**
	 * Set the winch motor to move in the un-spooled direction. Percent ranges from 0 to 1.
	 */
	public void reverse() {
		motorCAM.set(motorSpeed * -1 * motor_wind_direction);
	}

	/**
	 * Set the winch motor to move in the spooled direction. Percent ranges from 0 to 1.
	 */
	public void forward() {
		motorCAM.set(motorSpeed * motor_wind_direction);
	}

	/**
	 * Stops the winch motor from moving.
	 */
	public void stop() {
		motorCAM.set(0);
	}
}

