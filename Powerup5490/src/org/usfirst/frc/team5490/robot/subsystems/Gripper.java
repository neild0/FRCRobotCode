package org.usfirst.frc.team5490.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
//import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5490.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;



/**
 *
 * The gripper subsystem is a simple system with two "snowblower" motors for opening and closing the gripper bars.
 * It is assumed that 0 is the "open" orientation.
 * 
 * The two motors are slaved together and controlled by a single encoder for sensing position
 * To support setup and calibration a calibrate mode allows individual positioning and resetting of the zero position
 * zero should be set to be with bars at 90 degrees from vertical centerline, i.e. maximum with of gripper bars.
 * 
 * Target positions can be specified as TopDistance (i.e. rotated into Cartesion zone I) or BottomDistance (cartesion zone IV)
 *    
 * 
 */
public class Gripper extends PIDSubsystem {


	private static final double motor_offset = 4.5;	// inches -- distance from griper center to center of motor hubs
	//private static final double short_arm_length = 5.9;	// inches -- arm length - hub center to center of grip bar
	private static final double arm_length = 10.9;	// inches -- arm length - hub center to center of grip bar
	private static final double grip_pad_radius = 1.0;	// inches -- radius of grip bar padding (including bar itself)
	
	private static final double large_gear_teeth = 90;
	private static final double small_gear_teeth = 15;
	private static final double deg_per_turn = 2 * Math.PI;
	private static final double pulses_per_revolution = 500;
	
	private static final double PowerCubeWidth = 12;	// inches

	private static final double kP = 0.7;
	private static final double kI = 0.02;

	
	private WPI_TalonSRX mLGripper = new WPI_TalonSRX(RobotMap.mtrLGripper);
	private WPI_TalonSRX mRGripper = new WPI_TalonSRX(RobotMap.mtrRGripper);
	
        
    private Encoder LGripperEncoder = new Encoder(RobotMap.gripperEncoderA,RobotMap.gripperEncoderB);
        
    private double  mBox_Angle;	
	

	public Gripper() {
		super(kP, kI, 0);
		
		setAbsoluteTolerance(0.008); // 1/5 deg in radians	should be close enough
		
    	// Define current limiting
		mLGripper.configContinuousCurrentLimit(5, 0);
		mLGripper.configPeakCurrentLimit(10, 0);
		mLGripper.configPeakCurrentDuration(200, 0);
		mLGripper.enableCurrentLimit(true);		
		mLGripper.configOpenloopRamp(0.05, 0);
		
		mRGripper.configContinuousCurrentLimit(5, 0);
		mRGripper.configPeakCurrentLimit(10, 0);
		mRGripper.configPeakCurrentDuration(200, 0);
		mRGripper.enableCurrentLimit(true);
	    mRGripper.configOpenloopRamp(0.05, 0);
	    mRGripper.setInverted(true);
		
		
		LGripperEncoder.setDistancePerPulse(deg_per_turn / ((large_gear_teeth / small_gear_teeth) * pulses_per_revolution));   // degrees per pulse


		// Let's name everything on the LiveWindow
		addChild("LMotor", mLGripper);
		addChild("RMotor", mRGripper);
		
		addChild("Gripper Encoder", LGripperEncoder);
		
		
		mBox_Angle = CalcGripperPos(PowerCubeWidth);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new GripperOpen());
    }
    
    public void log() {
    	// TODO take these out completely if we confirm that PID is gone
//    	double angle = LGripperEncoder.getDistance();
//    	SmartDashboard.putNumber("Gripper Speed", LGripperEncoder.getRate());
//    	SmartDashboard.putNumber("Gripper Angle", angle);
//    	SmartDashboard.putNumber("Grip Distance", CalcGripperPos(angle));
    	
//    	SmartDashboard.putData("Gripper Encoder (L)", LGripperEncoder);
    	
	}
    
    // enable independent control of both motors 
	public void enable_calibrate() {		
		disable();
	}
	
	// resume slaved motor control 
	public void disable_calibrate() {		
		manual_stop();
	}

	/// reset the position to zero 
	public void Zero() {
		LGripperEncoder.reset();
	}
	
	public void manual_Lplus() {		
		mLGripper.set(0.1);		
	}
	
	public void manual_Lminus() {		
		mLGripper.set(-0.1);		
	}
	public void manual_Rplus() {		
		mRGripper.set(0.1);		
	}
	
	public void manual_Rminus() {		
		mRGripper.set(-0.1);		
	}

	/**
	 * Set the claw motor to move in the open direction. Percent ranges from 0 to 1.
	 */
	public void manual_open(double speed) {
		disable();
		mLGripper.set(-1 * speed);
		mRGripper.set(-1 * speed);

	}
	

	/**
	 * Set the claw motor to move in the close direction. Percent ranges from 0 to 1.
	 */

	public void manual_close(double speed) {
		disable();
		mLGripper.set(speed);
		mRGripper.set(speed);
	}

	/**
	 * Stops the claw motor from moving.
	 */

	public void manual_stop() {
		disable();
		mLGripper.set(0);
		mRGripper.set(0);
	}
	
	// Determine spacing between gripper bars using the current angle and physical characteristics,
	private double CalcGripperPos(double angle_radians)
	{	
			
		double distance = 2 * (motor_offset + (Math.cos(angle_radians) * (arm_length - grip_pad_radius)));
		return distance;
	}
	
	// Determine the angle required to achieve the passed distance between grippers
	private double CalcPosToGripperAngle(double distance)
	{
		double desired_arm = ((distance / 2) - motor_offset) / (arm_length - grip_pad_radius);		
		double angle_radians = Math.acos(desired_arm);	
		return angle_radians;
	}

	
	// allow setpoint to be specified in terms of grip distance
	public void SetDistanceSetpoint(double grip_distance_inch, boolean top_quadrant)
	{		
		double angle =CalcPosToGripperAngle(grip_distance_inch);
		if (! top_quadrant) angle *= -1;
		setSetpoint(angle);
	}
	

	/**
	 * Return true when the robot is grabbing an object hard enough to trigger
	 * the limit switch.
	 */
	public boolean isGrabbing() {		
		return (Math.abs(LGripperEncoder.getDistance()) <= mBox_Angle);
	}

	/**
	 * Return true when the grippers are fully open.
	 */
	public boolean isOpen() {
		return (Math.abs(LGripperEncoder.getDistance()) > mBox_Angle);		
	}
	
	
	public double RelativeTarget(double delta) {		 
		 //return CalcGripperPos() + delta;
		return LGripperEncoder.getDistance() + delta;
	}
	
	/**
	 * get the encoder data (returns radians)
	 */
	@Override
	protected double returnPIDInput() {		
		//
		return LGripperEncoder.getDistance();
	}
	
	/**
	 * Use the motor as the PID output. This method is automatically called by
	 * the subsystem.
	 */
	@Override
	protected void usePIDOutput(double power) {		
		mLGripper.set(power);
		mRGripper.set(power);
	}	
}

