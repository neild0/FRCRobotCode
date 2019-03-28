package org.usfirst.frc.team5490.robot.commands;

import org.usfirst.frc.team5490.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for manually closing the gripper.
 */
public class GripperClose extends Command {

	private static final double close_speed = 0.4;
	
	public GripperClose() {
    	requires(Robot.m_Gripper);
    }
	
    public GripperClose(double rateL, double rateR) {
    	requires(Robot.m_Gripper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.m_Gripper.disable();	// TODO we really don't need this b/c we don't have PID anymore
		//Robot.m_Gripper.enable();
		//Robot.m_Gripper.SetDistanceSetpoint(11, false);	// close to 10 inches
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.m_Gripper.manual_close(close_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
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
