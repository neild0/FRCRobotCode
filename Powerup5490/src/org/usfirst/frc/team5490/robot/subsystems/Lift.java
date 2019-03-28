package org.usfirst.frc.team5490.robot.subsystems;


import org.usfirst.frc.team5490.robot.Robot;
import org.usfirst.frc.team5490.robot.RobotMap;
import org.usfirst.frc.team5490.robot.commands.LiftDown;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Lift extends PIDSubsystem {

	// Manually tuned as of 2/17/2018
	private static final double kP = 0.0175;
	private static final double kI = 0;
	private static final double tolerance = 0.05;
	
	// Encoder configurations
	private static final double mm_per_turn = 4.88;
	private static final double pulses_per_revolution = 500;
	private static final double motor_up_direction = -1;
	
    // Lift objects

    private WPI_TalonSRX   motorLift = new WPI_TalonSRX(RobotMap.mtrLift);   
    private Encoder m_LiftEncoder = new Encoder(RobotMap.liftEncoderA,RobotMap.liftEncoderB);
     
    // Limit switches
    public DigitalInput m_lsTop = new DigitalInput(RobotMap.ls_liftUp);
	public DigitalInput m_lsBottom = new DigitalInput(RobotMap.ls_liftDown);
	
	private boolean m_clamped = false;
	
	public Lift() {
		super(kP, kI, 0);
		m_LiftEncoder.setDistancePerPulse(mm_per_turn / pulses_per_revolution);   // 4.88 mm per turn of the shaft / pulses per turn
		setAbsoluteTolerance(tolerance); // MM
		
		motorLift.configContinuousCurrentLimit(40, 0);
		motorLift.configPeakCurrentLimit(45, 0);
		motorLift.configPeakCurrentDuration(100, 0);
		motorLift.enableCurrentLimit(false);		// kill current limit
		motorLift.configOpenloopRamp(0, 0);			// we do the lamp here..

		
		m_LiftEncoder.setDistancePerPulse(mm_per_turn / pulses_per_revolution);   // 4.88 mm per turn of the shaft / pulses per turn
		
		setAbsoluteTolerance(0.05); // MM
		

		// Let's name everything on the LiveWindow
		addChild("Lift Motor", motorLift);
		addChild("Lift Encoder", m_LiftEncoder);

	}
	
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new LiftDown());
    	 
    }
    
    public void log() {
    	SmartDashboard.putNumber("Lift Speed", m_LiftEncoder.getRate());
    	SmartDashboard.putNumber("Lift Position", this.getPosition());
    	SmartDashboard.putNumber("Lift Distance", m_LiftEncoder.getDistance());
    	SmartDashboard.putNumber("Lift dpp", m_LiftEncoder.getDistancePerPulse());
    	
    	// Because things that are put in the SmartDashboard through putData() have no 
//    	SmartDashboard.putData("Lift Motor", motorLift);
//    	SmartDashboard.putData("Lift Encoder", m_LiftEncoder);
    	
    	// limit switches
    	// TODO fix it back to putData when nav f*cking fixes the connection for l.s.
    	SmartDashboard.putBoolean("LiftAtTop", this.isAtTop());
    	SmartDashboard.putBoolean("LiftAtBottom", this.isAtBottom());

    	
    	// Let's name everything on the LiveWindow
    	addChild("Lift Motor", motorLift);
    	addChild("Lift Encoder", m_LiftEncoder);
	}
    
    
    public void Reset()
    {
    	m_LiftEncoder.reset();
    }
    
    
    /**
	 * Set the lift motor to move in the down direction. Percent ranges from 0 to 1.
	 */
	public void lower(double percent) {
		motorLift.set(percent * -1 * motor_up_direction);
	}

	/**
	 * Set the lift motor to move in the up direction. Percent ranges from 0 to 1.
	 */
	public void raise(double percent) {
		motorLift.set(percent * motor_up_direction);
	}

	/**
	 * Stops the lift motor from moving.
	 */
	public void stop() {
		motorLift.set(0);
	}
    
	/**
	 * Return true when the which lift triggers the "top" limit switch.
	 */
	public boolean isAtTop() {
		return ! m_lsTop.get();
	}

	/**
	 * Return true when the which lift triggers the "bottom" limit switch.
	 */
	public boolean isAtBottom() {
		return ! m_lsBottom.get();
	}
	
	public boolean isClamped() {
		// 
		return m_clamped;
	}

	
	/**
	 * Use the magnetic encoder as the PID sensor. This method is automatically
	 * called by the subsystem.
	 */
	@Override
	protected double returnPIDInput() {
		return m_LiftEncoder.getDistance();
	}
	
	/**
	 * Use the motor as the PID output. This method is automatically called by
	 * the subsystem.
	 */
	@Override
	protected void usePIDOutput(double power) {
		double output = power*motor_up_direction;		
		// detect and clamp output based on direction
		// TODO for isAtBottom, output < 0 if I'm not mistaken... check later
		m_clamped = (((output > 0) && isAtTop()) || ((output < 0) && isAtBottom()));
		if (m_clamped) output = 0;			
		motorLift.set(output);
	}
}

