package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TransferToForward extends Command {

    public TransferToForward() {
    	requires(Robot.m_Chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.m_Chassis.m_transfer.forward();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.m_Chassis.m_transfer.isTransferForward();        
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.m_Chassis.m_transfer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
