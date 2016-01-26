
package org.usfirst.frc.team2220.robot;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
    //RobotDrive myRobot;
    Joystick controller;
    
    //Joystick rightstick;
    //final String defaultAuto = "Default";
    //final String customAuto = "My Auto";
    //SendableChooser chooser;
    TwilightTalon motor_FL;
    TwilightTalon motor_FR;
    TwilightTalon motor_BL;
    TwilightTalon motor_BR;
    //TwilightTalon shooter_TR;
    //TwilightTalon shooter_TL;
    //TwilightTalon shooter_B;

    public Robot() {
        //myRobot = new RobotDrive(0, 1);
        //myRobot.setExpiration(0.1);
        controller = new Joystick(0);
        motor_FL = new TwilightTalon(4);
        motor_FR = new TwilightTalon(6);
        motor_BL = new TwilightTalon(7);
        motor_BR = new TwilightTalon(5);
        
        motor_FL.setInverted(true);
        motor_BL.setInverted(true);
        
        motor_BL.changeControlMode(TalonControlMode.Follower);
        motor_BL.set(4);
        motor_BR.changeControlMode(TalonControlMode.Follower);
        motor_BR.set(6);
        //shooter_TR = new TwilightTalon(3);
        //shooter_TL = new TwilightTalon(1);
        //shooter_B = new TwilightTalon(2);
    
    }
    
    public void robotInit() {
        //chooser = new SendableChooser();
        //chooser.addDefault("Default Auto", defaultAuto);
        //chooser.addObject("My Auto", customAuto);
        //SmartDashboard.putData("Auto modes", chooser);
    }

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the if-else structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomous() {
    	
    	//String autoSelected = (String) chooser.getSelected();
//		String autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		//System.out.println("Auto selected: " + autoSelected);
    	
    	//switch(autoSelected) {
    	//case customAuto:
            //myRobot.setSafetyEnabled(false);
            //myRobot.drive(-0.5, 1.0);	// spin at half speed
            //Timer.delay(2.0);		//    for 2 seconds
            //myRobot.drive(0.0, 0.0);	// stop robot
            //break;
    	//case defaultAuto:
    	//default:
            //myRobot.setSafetyEnabled(false);
            //myRobot.drive(-0.5, 0.0);	// drive forwards half speed
            //Timer.delay(2.0);		//    for 2 seconds
            //myRobot.drive(0.0, 0.0);	// stop robot
            //break;
    	//}
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
    	double left_speed;
    	double right_speed;
    	//boolean get()
    	double output_voltage_fl;
    	double output_voltage_fr;
    	double output_voltage_bl;
    	double output_voltage_br;
    	
    	
    	
    	
        //myRobot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
            //myRobot.arcadeDrive(stick); // drive with arcade style (use right stick)
            left_speed = controller.getRawAxis(1);
            right_speed = controller.getRawAxis(5);
            SmartDashboard.putNumber("left_speed", left_speed);
            SmartDashboard.putNumber("right_speed",  right_speed);
            output_voltage_fl = motor_FL.getOutputVoltage();
        	output_voltage_fr = motor_FR.getOutputVoltage();
        	output_voltage_bl = motor_BL.getOutputVoltage();
        	output_voltage_br = motor_BR.getOutputVoltage();
        	SmartDashboard.putNumber("FLOV",output_voltage_fl);
        	SmartDashboard.putNumber("FROV",output_voltage_fr);
        	SmartDashboard.putNumber("BLOV",output_voltage_bl);
        	SmartDashboard.putNumber("BROV",output_voltage_br);
        	motor_FL.print();
        	motor_FR.print();
        	motor_BL.print();
        	motor_BR.print();
            
            
            motor_FL.set(left_speed);
            motor_FR.set(right_speed);
            //motor_BL.set(left_speed);
            //motor_BR.set(right_speed);
            //shooter_TR.set();
            //shooter_TL.set();
            //shooter_B.set();
        	Timer.delay(0.005);		// wait for a motor update time
        }
    }

    /**
     * Runs during test mode
     */
    public void test() {
    }
}



