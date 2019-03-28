package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.SpeedController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// this is for the Talon SRX version
//import edu.wpi.first.wpilibj.Talon;
import com.revrobotics.*;

import frc.robot.RobotMap;
import frc.robot.commands.DriveRobot;

//import frc.robot.Point3D;

import frc.robot.HermiteSpline;



import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.AnalogInput;


import com.analog.adis16448.frc.ADIS16448_IMU;
import frc.robot.subsystems.TransferTable;





/**
 *
 */
public class Chassis extends Subsystem {
	
	// lower limit for speed setting  
	private static final double minimum_drive = 0.1;
	
	// Main Movement Drive 
	
	/*
	WPI_TalonSRX motorFrontLeft= new WPI_TalonSRX(RobotMap.mtrFrontLeft);
	WPI_TalonSRX motorRearLeft= new WPI_TalonSRX(RobotMap.mtrRearLeft);
	WPI_TalonSRX motorFrontRight= new WPI_TalonSRX(RobotMap.mtrFrontRight);
	WPI_TalonSRX motorRearRight= new WPI_TalonSRX(RobotMap.mtrRearRight);
	*/
	


	CANSparkMax motorFrontLeft= new CANSparkMax(RobotMap.mtrFrontLeft, CANSparkMaxLowLevel.MotorType.kBrushless);
	CANSparkMax motorRearLeft= new CANSparkMax(RobotMap.mtrRearLeft, CANSparkMaxLowLevel.MotorType.kBrushless);
	CANSparkMax motorFrontRight= new CANSparkMax(RobotMap.mtrFrontRight, CANSparkMaxLowLevel.MotorType.kBrushless);
	CANSparkMax motorRearRight= new CANSparkMax(RobotMap.mtrRearRight, CANSparkMaxLowLevel.MotorType.kBrushless);
	

    MecanumDrive m_robotDrive = new MecanumDrive(motorFrontLeft,motorRearLeft,motorFrontRight,motorRearRight);
    
  
	public TransferTable m_transfer = new TransferTable();
    
    
    private static HermiteSpline JoystickCurve;
    
    //private static HermiteSpline Hcurve;
    
	ADIS16448_IMU imu = new ADIS16448_IMU();
	
	AnalogInput  LeftDistance = new AnalogInput(RobotMap.us_LeftDist);
	AnalogInput  RightDistance = new AnalogInput(RobotMap.us_RightDist);

    
    
    public int segment;
    public double percent;
    public double tick; 
    
    public double speed;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	// see DriveTrain example
    public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		
		    /* factory default values */
		/*motorFrontLeft.configFactoryDefault();
		motorRearLeft.configFactoryDefault();
		motorFrontRight.configFactoryDefault();
		motorRearRight.configFactoryDefault();*/
    	
    	DefineJoystickResponse();
		
		//int bits;
		LeftDistance.setOversampleBits(4);
		//bits = LeftDistance.getOversampleBits();
		LeftDistance.setAverageBits(2);
		//bits = LeftDistance.getAverageBits();

		RightDistance.setOversampleBits(4);
		//bits = LeftDistance.getOversampleBits();
		RightDistance.setAverageBits(2);
		//bits = LeftDistance.getAverageBits();

		// (2/14/2018) Inversion proved to be necessary to get the robot moving in the right direction 
    	//motorFrontLeft.setInverted(true);
    	//motorRearRight.setInverted(true);
    			
		// Let's name the sensors on the LiveWindow
		
		//addChild("Drive", m_robotDrive);
		//addChild("Winch", m_Winch);
		

		// ToDo determine when the light should come on/off
		//addChild("Lightmast", m_lightmast);

		
		//When no other command is running let the operator drive around using the joystick		 
		setDefaultCommand(new DriveRobot());
		SmartDashboard.putNumber("Path Segment: ", segment);
		SmartDashboard.putNumber("Path percent: ", percent);
		SmartDashboard.putNumber("Path timer: ", tick);
		 
		

		
		SmartDashboard.putNumber("Gyro-X", imu.getAngleX());
		SmartDashboard.putNumber("Gyro-Y", imu.getAngleY());
		SmartDashboard.putNumber("Gyro-Z", imu.getAngleZ());
		
		SmartDashboard.putNumber("Accel-X", imu.getAccelX());
		SmartDashboard.putNumber("Accel-Y", imu.getAccelY());
		SmartDashboard.putNumber("Accel-Z", imu.getAccelZ());
		
		SmartDashboard.putNumber("Pitch", imu.getPitch());
		SmartDashboard.putNumber("Roll", imu.getRoll());
		SmartDashboard.putNumber("Yaw", imu.getYaw());
		
		SmartDashboard.putNumber("Pressure: ", imu.getBarometricPressure());
		SmartDashboard.putNumber("Temperature: ", imu.getTemperature());
		

		SmartDashboard.putNumber("Left Distance: ",  LeftDistance.getValue());
		SmartDashboard.putNumber("Right Distance: ",  RightDistance.getValue());
		
		

    }
    
    private void DefineJoystickResponse()
    {
    	JoystickCurve = new HermiteSpline();
    	JoystickCurve.Size(2);
		
				// Define point and tangent for point 1 (starting)
    	JoystickCurve.P(0).x = 0;
    	JoystickCurve.P(0).y = 0;
    	JoystickCurve.P(0).z = 0;		// angle
		
    	JoystickCurve.T(0).x = 50;
    	JoystickCurve.T(0).y = 0;
    	JoystickCurve.T(0).z = 0;		// angle
		
    	JoystickCurve.P(1).x = 1;
    	JoystickCurve.P(1).y = 1;
    	JoystickCurve.P(1).z = 1;

    	JoystickCurve.T(1).x = -15;
    	JoystickCurve.T(1).y = 0;
    	JoystickCurve.T(0).z = 0;		// angle
    }
    
   
    public void log() 
    {
    	
    	// put class variables we want to see on dashboard or capture here
    	m_transfer.log();
    	
    	
    	SmartDashboard.putNumber("FL", -1 * motorFrontLeft.get());
    	SmartDashboard.putNumber("FR", motorFrontRight.get());
    	SmartDashboard.putNumber("RL", -1 * motorRearLeft.get());
    	SmartDashboard.putNumber("RR", motorRearRight.get());

		SmartDashboard.putNumber("Path Segment: ", segment);
		SmartDashboard.putNumber("Path percent: ", percent);
		SmartDashboard.putNumber("Path timer: ", tick);

		
		SmartDashboard.putNumber("Gyro-X", imu.getAngleX());
		SmartDashboard.putNumber("Gyro-Y", imu.getAngleY());
		SmartDashboard.putNumber("Gyro-Z", imu.getAngleZ());
		
		SmartDashboard.putNumber("Accel-X", imu.getAccelX());
		SmartDashboard.putNumber("Accel-Y", imu.getAccelY());
		SmartDashboard.putNumber("Accel-Z", imu.getAccelZ());
		
		SmartDashboard.putNumber("Pitch", imu.getPitch());
		SmartDashboard.putNumber("Roll", imu.getRoll());
		SmartDashboard.putNumber("Yaw", imu.getYaw());
		
		SmartDashboard.putNumber("Angle-X", imu.getAngleX());
		SmartDashboard.putNumber("Angle-Y", imu.getAngleY());
		SmartDashboard.putNumber("Angle-Z", imu.getAngleZ());
		
		SmartDashboard.putNumber("Pressure: ", imu.getBarometricPressure());
		SmartDashboard.putNumber("Temperature: ", imu.getTemperature());
		
		SmartDashboard.putNumber("Speed: ", speed);
	}

	
	public void Drive(Joystick driveStick)
	{
		double speedrange = 1 - minimum_drive;
		speed = (-speedrange*driveStick.getThrottle()+1)/2;
		speed += minimum_drive;

		m_robotDrive.setDeadband(0.2);
		
		// mechanum orientation is
		//  X forward/reverse
		//	Y right/left
		//  Z twist		
		// normal people assume
		//  Y forward/reverse
		//	X right/left
		//  Z twist
		//  So we swap orientations here to avoid brain cramps..
		//
		
		/*
		double Xreq = -1 * driveStick.getX();
		double Yreq = driveStick.getY();
		double Zreq = driveStick.getX();
		
		// 
		// make a non-linear response to the joystick that smoothes out
		// initial response - this keeps twist from doing weird things
		// use same response for each axis...
		// the curve works for 0..1 so remove polarity for calc 
		Point3D xscale = JoystickCurve.Calc(1, Math.abs(Xreq));
		Point3D yscale = JoystickCurve.Calc(1, Math.abs(Yreq));
		Point3D zscale = JoystickCurve.Calc(1, Math.abs(Zreq));
		
		double Xout = speed * xscale.y;
		double Yout = speed * yscale.y;
		double Zout = speed * zscale.y;
		
		// restore polarity
		if (Xreq < 0) Xout *= -1;
		if (Yreq < 0) Yout *= -1;
		if (Zreq < 0) Zout *= -1;
		
		
		m_robotDrive.driveCartesian(Xout, Yout, Zout, 0);
		*/
		
		m_robotDrive.driveCartesian(-1 * speed*driveStick.getX(),
									speed*driveStick.getY(),
									-0.25*driveStick.getZ(), 0);
									
		
		//
		
	}
	
	// Let an external function drive the chassis
	// note X = forward, Y right 
	// 
	public void Drive(double X, double Y, double Z, double speed)
	{
		// mechanum orientation is
		//  X forward/reverse
		//	Y right/left
		//  Z twist		
		// normal people assume
		//  Y forward/reverse
		//	X right/left
		//  Z twist
		//  So we swap orientations here to avoid brain cramps..
		//
		if (X > 1.0) X = 1.0;
		if (Y > 1.0) Y = 1.0;
		if (Z > 1.0) Z = 1.0;
		if (speed > 1.0) speed = 1.0;
		
		//m_robotDrive.driveCartesian( -1 * X * speed, -1 * Y * speed, Z * speed, imu.getAngleY());
		m_robotDrive.driveCartesian( -1 * X * speed, -1 * Y * speed, Z * speed);
	}
	
	public void StopMotors()
	{		
		m_robotDrive.driveCartesian(0,0,0,0);		
	}
	
	public void moveForward()
	{
		m_robotDrive.driveCartesian(0,1,0,0);
	}
	
	public void moveBackward()
	{
		m_robotDrive.driveCartesian(0,-1,0,0);
	}
}

