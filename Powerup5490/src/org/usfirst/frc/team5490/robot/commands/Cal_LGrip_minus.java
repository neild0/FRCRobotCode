package org.usfirst.frc.team5490.robot.commands;

import org.usfirst.frc.team5490.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Cal_LGrip_minus extends Command {

    public Cal_LGrip_minus() {
    	requires(Robot.m_Gripper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.m_Gripper.enable_calibrate();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.m_Gripper.manual_Lminus();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.m_Gripper.Zero();
    	Robot.m_Gripper.disable_calibrate();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
