/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5490.robot;

import org.usfirst.frc.team5490.robot.commands.WinchToOperate;
import org.usfirst.frc.team5490.robot.commands.WinchToStore;

import org.usfirst.frc.team5490.robot.commands.GripperOpen;
import org.usfirst.frc.team5490.robot.commands.GripperReady;
import org.usfirst.frc.team5490.robot.commands.GripperClose;
import org.usfirst.frc.team5490.robot.commands.GripperRelease;

import org.usfirst.frc.team5490.robot.commands.LiftDown;
import org.usfirst.frc.team5490.robot.commands.LiftSwitch;
import org.usfirst.frc.team5490.robot.commands.TestSequencer;
import org.usfirst.frc.team5490.robot.commands.LiftScale;
import org.usfirst.frc.team5490.robot.commands.LiftSetpoint;
import org.usfirst.frc.team5490.robot.commands.LiftHook;
import org.usfirst.frc.team5490.robot.commands.LiftManualMove;
import org.usfirst.frc.team5490.robot.commands.LiftRobot;   // same as lift down, but may be partial
import org.usfirst.frc.team5490.robot.commands.Cal_LGrip_minus;
import org.usfirst.frc.team5490.robot.commands.Cal_LGrip_plus;
import org.usfirst.frc.team5490.robot.commands.Cal_RGrip_minus;
import org.usfirst.frc.team5490.robot.commands.Cal_RGrip_plus;

//import org.usfirst.frc.team5490.robot.commands.TestSpline;
//import org.usfirst.frc.team5490.robot.commands.DrivePath;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private Joystick m_joystick = new Joystick(0);

	
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	public OI() {
		// Put our operations buttons on the SmartDashboard
		
		SmartDashboard.putData("Winch to Operate", new WinchToOperate());
		SmartDashboard.putData("Winch to Store", new WinchToStore());
		
		SmartDashboard.putData("Gripper Close", new GripperClose());
		SmartDashboard.putData("Gripper Release", new GripperRelease());
		SmartDashboard.putData("Gripper Open", new GripperOpen());
		SmartDashboard.putData("Gripper Ready", new GripperReady());
		
		SmartDashboard.putData("Gripper Cal L+", new Cal_LGrip_plus());
		SmartDashboard.putData("Gripper Cal L-", new Cal_LGrip_minus());
		SmartDashboard.putData("Gripper Cal R+", new Cal_RGrip_plus());
		SmartDashboard.putData("Gripper Cal R-", new Cal_RGrip_minus());
		
		
		SmartDashboard.putData("Lift Down", new LiftDown());
		SmartDashboard.putData("Lift Switch", new LiftSwitch());
		SmartDashboard.putData("Lift Scale", new LiftScale());
		SmartDashboard.putData("Lift Hook", new LiftHook());
		SmartDashboard.putData("Lift Robot", new LiftRobot());
		
		// Gripper calibrate buttons
		/*
		SmartDashboard.putData("Lift Robot", new LiftRobot());
		SmartDashboard.putData("Lift Robot", new LiftRobot());
		SmartDashboard.putData("Lift Robot", new LiftRobot());
		SmartDashboard.putData("Lift Robot", new LiftRobot());
		*/
		
		
		// BUTTON MAPPINGS
		JoystickButton trigger = new JoystickButton(m_joystick, 1);
		JoystickButton thumb = new JoystickButton(m_joystick, 2);
		
		// Top buttons (counterclockwise from top left: 5-3-4-6)
		JoystickButton button5 = new JoystickButton(m_joystick, 5);
		JoystickButton button3 = new JoystickButton(m_joystick, 3);
		JoystickButton liftManualDown= new JoystickButton(m_joystick, 4);
		JoystickButton liftManualUp = new JoystickButton(m_joystick, 6);
		
		// Side buttons (from top left)
		JoystickButton button7 = new JoystickButton(m_joystick, 7);
		JoystickButton button8 = new JoystickButton(m_joystick, 8);
		JoystickButton button9 = new JoystickButton(m_joystick, 9);
		JoystickButton button10 = new JoystickButton(m_joystick, 10);
		JoystickButton button11 = new JoystickButton(m_joystick, 11);
		JoystickButton button12 = new JoystickButton(m_joystick, 12);
		
		
		
		// COMMAND MAPPINGS
		trigger.whileHeld(new GripperClose());
		thumb.whileHeld(new GripperOpen());
		
		// TODO for testing purposes
		button3.toggleWhenPressed(new WinchToStore());
		button5.toggleWhenPressed(new WinchToOperate());
		liftManualDown.whileHeld(new LiftManualMove(-1, 0.5));
		liftManualUp.whileHeld(new LiftManualMove(1, 0.5));
		
		// TODO change mappings depending on the driver's preferences
		button7.whenPressed(new LiftDown());
		button9.whenPressed(new LiftSwitch());
		button10.whenPressed(new LiftScale());
		button11.whenPressed(new LiftHook());
		button12.whileHeld(new LiftRobot());
		


		
//		JoystickButton lu = new JoystickButton(m_joystick, 9);
//		JoystickButton ld = new JoystickButton(m_joystick, 10);
//		JoystickButton gopen = new JoystickButton(m_joystick, 2);
//		JoystickButton gclose = new JoystickButton(m_joystick, 1);
//		JoystickButton wstore = new JoystickButton(m_joystick, 11);
//		JoystickButton woperate = new JoystickButton(m_joystick, 12);
//		JoystickButton forward = new JoystickButton(m_joystick, 5);
//		JoystickButton backward = new JoystickButton(m_joystick, 3);
//		
//		// allow "zero" to be established with both gripper motors
//		JoystickButton grip_calib_Lminus = new JoystickButton(m_joystick, 4);
//		JoystickButton grip_calib_Lplus = new JoystickButton(m_joystick, 6);
//		JoystickButton grip_calib_Rminus = new JoystickButton(m_joystick, 7);
//		JoystickButton grip_calib_Rplus = new JoystickButton(m_joystick, 8);
//		
//		
//		lu.toggleWhenPressed(new LiftSetpoint(800));
//		ld.toggleWhenPressed(new LiftSetpoint(0));
//		
//		gopen.whileHeld(new GripperOpen());
//		gclose.whileHeld(new GripperClose());
//		
//		wstore.toggleWhenPressed(new WinchToStore());
//		woperate.toggleWhenPressed(new WinchToOperate());
//		
//		forward.toggleWhenPressed(new TestSequencer());
//		backward.toggleWhenPressed(new TestSequencer());
//		
//		
//		// These should probably be in the smart dashboard not on joystick
//		grip_calib_Lminus.whileHeld(new Cal_LGrip_minus());
//		grip_calib_Lplus.whileHeld(new Cal_LGrip_plus());
//		grip_calib_Rminus.whileHeld(new Cal_RGrip_minus());
//		grip_calib_Rplus.whileHeld(new Cal_RGrip_plus());


	}
	
	public Joystick getJoystick() {
		return m_joystick;
	}
}
