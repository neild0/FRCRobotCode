/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5490.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import sun.misc.Timer;

import org.usfirst.frc.team5490.robot.commands.DriveRobot;
import org.usfirst.frc.team5490.robot.commands.GripperOpen;
import org.usfirst.frc.team5490.robot.commands.LiftDown;
import org.usfirst.frc.team5490.robot.commands.WinchToOperate;
import org.usfirst.frc.team5490.robot.subsystems.Chassis;
import org.usfirst.frc.team5490.robot.subsystems.Gripper;
import org.usfirst.frc.team5490.robot.subsystems.Lift;


/*import org.usfirst.frc.team5490.robot.subsystems.Winch;
import org.usfirst.frc.team5490.robot.subsystems.Gripper;

import org.usfirst.frc.team5490.robot.commands.Autonomous;
import org.usfirst.frc.team5490.robot.commands.DriveRobot;
import org.usfirst.frc.team5490.robot.commands.GripperOpen;
import org.usfirst.frc.team5490.robot.commands.LiftDown;
import org.usfirst.frc.team5490.robot.commands.WinchToOperate;

*/


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	
	// create our assemblies..
	public static Chassis m_Chassis;
	public static Lift m_Lift;
	public static Gripper m_Gripper;
	public static OI m_oi;
	
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	
	
	@Override
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	
	
	public void robotInit() {		
		UsbCamera jevoisCam = CameraServer.getInstance().startAutomaticCapture();
		jevoisCam.setVideoMode(PixelFormat.kYUYV,320,254,60);
		
		m_Chassis = new Chassis();
		
		m_Lift = new Lift();
		m_Gripper = new Gripper();
		m_oi = new OI();
		
		m_autonomousCommand = new DriveRobot();
		
		
		// instantiate the command used for the autonomous period
		// add version of auto operation here..
		m_chooser.addDefault("Basic Start", new WinchToOperate());
		m_chooser.addObject("Gripper Open", new GripperOpen());
		m_chooser.addObject("Lift Down", new LiftDown());
		
		
		
		// this lets the dashboard choose which on to run at start
		//SmartDashboard.putData("Auto mode", m_chooser);
		
		//SmartDashboard.putString("Camera", cam.readString());
		
		
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		log();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
	
		
		m_autonomousCommand = m_chooser.getSelected();
		
		
		 
		String autoSelected = SmartDashboard.getString("Auto Selector", "Default"); 
		switch(autoSelected) { 
			case "My Auto": 
				//autonomousCommand = new MyAutoCommand(); 
				break; 
			case "Default Auto": 
			default:
				//autonomousCommand = new ExampleCommand(); 
				break; 
		}
		
		 

		// schedule the autonomous command (example)
		
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		log();
		
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		// this allows activated commands to run..
		Scheduler.getInstance().run();
		log();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		log();
	}
	
	/**
	 * The log method puts interesting information to the SmartDashboard.
	 */
	private void log() {
		m_Chassis.log();
		m_Lift.log();
		m_Gripper.log();
	}
}
