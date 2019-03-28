package org.usfirst.frc.team5490.robot.commands;

import org.usfirst.frc.team5490.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GripperRelease extends Command {

    public GripperRelease() {
    	requires(Robot.m_Gripper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	///back off 5 degrees    	
    	Robot.m_Gripper.SetDistanceSetpoint(13, true);	// open to 13 inches in quadrant I
		Robot.m_Gripper.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()    
    protected boolean isFinished() {
    	return Robot.m_Gripper.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.m_Gripper.disable();
    	Robot.m_Gripper.manual_stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
