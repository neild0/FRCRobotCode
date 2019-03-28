/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Ultrasonic;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;

//import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import sun.misc.Timer;
import frc.robot.ball;
import frc.robot.commands.DriveRobot;
//import frc.robot.commands.GripperOpen;
//import frc.robot.commands.LiftDown;
import frc.robot.commands.TransferToForward;
//import frc.robot.commands.WinchToOperate;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.ClimbingCAM;
//import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Vacuum;
import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.imgproc.Imgproc;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;

import frc.robot.commands.HatchClose;

/*import frc.robot.subsystems.Winch;
import frc.robot.subsystems.Gripper;

import frc.robot.commands.Autonomous;
import frc.robot.commands.DriveRobot;
import frc.robot.commands.GripperOpen;
import frc.robot.commands.LiftDown;
import frc.robot.commands.WinchToOperate;

*/


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	
	//private static final boolean True = false;


	// Camera
	private VisionThread visionThread;
	private VisionThread visionThread2;
	//Ultrasonic ultra = new Ultrasonic(21,21);
	// create our assemblies..
	public static Chassis m_Chassis;
	public static Lift m_Lift;
	public static ClimbingCAM m_CAM;
	public static Vacuum m_VAC;
	public static OI m_oi;
	
	Command m_autonomousCommand;
	//SendableChooser<Command> m_chooser = new SendableChooser<>();
	
	
	@Override
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	
	
	public void robotInit() {		
		//UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		//camera.setVideoMode(PixelFormat.kYUYV,320,254,60);
		//ultra.setAutomaticMode(true);
		m_Chassis = new Chassis();
		
		m_Lift = new Lift();
		m_CAM = new ClimbingCAM();		
		m_VAC = new Vacuum();



		m_oi = new OI();
		
		m_autonomousCommand = new DriveRobot();
		
		
		// instantiate the command used for the autonomous period
		// add version of auto operation here..
		//m_chooser.setDefaultOption("Basic Start", new TransferToForward());
		//m_chooser.addObject("Gripper Open", new GripperOpen());
		//m_chooser.addObject("Lift Down", new LiftDown());
		
		
		
		// this lets the dashboard choose which on to run at start
		//SmartDashboard.putData("Auto mode", m_chooser);
		
		//SmartDashboard.putString("Camera", camera.readString());
		//visionThread.start();
		new Thread(() -> {
			UsbCamera CameraFront = CameraServer.getInstance().startAutomaticCapture();
			CameraFront.setVideoMode(PixelFormat.kYUYV, 320, 240, 30);
		
		}).start();

		new Thread(() -> {
			UsbCamera CameraBack = CameraServer.getInstance().startAutomaticCapture();
			CameraBack.setVideoMode(PixelFormat.kYUYV, 320, 240, 30);
		
		}).start();
		
	}
	/*public void ultrasonicSample() {
    	double range = ultra.getRangeInches(); // reads the range on the ultrasonic sensor
    }*/
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
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		new HatchClose();
		/*
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
		*/
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		/*
		Scheduler.getInstance().run();
		log();
		*/
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
		new HatchClose();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		// this allows activated commands to run..
		//System.out.println("999 cm");
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
		m_CAM.log();
		m_VAC.log();
	}
}
