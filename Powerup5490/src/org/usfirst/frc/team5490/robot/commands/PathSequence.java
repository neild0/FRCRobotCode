package org.usfirst.frc.team5490.robot.commands;

import org.usfirst.frc.team5490.robot.PathRecord;
import org.usfirst.frc.team5490.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *  This is a base class - do not call directly
 *  derived classes need to define the path array
 */
public class PathSequence extends Command {
	
	
	private int state;
	private int index;
	private double timer;
	private double tickrate = 0.02;
	
	private double mspeed;
	
	private boolean done = false;
	
	
	protected PathRecord[] 	path;


    public PathSequence() {
    	requires(Robot.m_Chassis);
    	

    }

    // Called just before this Command runs the first time
    protected void initialize() {    	
    	timer = 0.0;
    	state = 0;
    	index = 0;
    	done = false;
    	mspeed = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
		case 0:
			
			mspeed = .9 * mspeed + 0.1 * path[index].speed;
			
			Robot.m_Chassis.Drive(path[index].X, path[index].Y, path[index].Z, mspeed);
			if (timer > path[index].duration) {
				timer = 0;
				index++;
				if (index >= path.length) state++;
			}			
			break;
		case 1:
			done = true;
			break;
		default:
			state =  0;
			break;
		
    	}
    	timer += tickrate;    	
    	SmartDashboard.putNumber("Path timer ", timer);
    	SmartDashboard.putNumber("Path state", state);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.m_Chassis.StopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
