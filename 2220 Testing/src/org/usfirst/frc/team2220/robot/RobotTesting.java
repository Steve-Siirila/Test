
package org.usfirst.frc.team2220.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Utility;

public class RobotTesting extends SampleRobot {
    Joystick stick;
    CANTalon motor_rot_fr;
    AnalogInput ai_rot_fr;
    Encoder enc_dist_fr;
    Timer timer;
    PIDController rotation_control;
    PowerDistributionPanel pdp;
    PWM pwm_test;
    
    /**
     * Constructor - functionally the same as robotInit()
     */
    public RobotTesting() {
    	System.out.printf("Start Constructor\n");
    	
        System.out.printf("End Constructor\n");
    }
    
    /**
     * robotInit - Runs during robot initialization/reboot
     */
    public void robotInit() {
    	System.out.printf("Start Robot Initialization\n");
    	motor_rot_fr = new CANTalon(7);
    	ai_rot_fr = new AnalogInput(2);
    	enc_dist_fr = new Encoder(0, 1);
        stick = new Joystick(0);
    	timer = new Timer();
    	rotation_control = new PIDController(0.1, 0.001, 0.0, ai_rot_fr, motor_rot_fr);
    	pdp = new PowerDistributionPanel();
    	pwm_test = new PWM(0);
    	System.out.printf("End Robot Initialization\n");
    }

    /**
     * Runs during autonomous mode
     */
    public void autonomous() {
    	double speed = 0.40;
    	double output_current;
    	
    	System.out.printf("Start Autonomous mode\n");
    	
    	while (isAutonomous() && isEnabled()) {
    		motor_rot_fr.set(speed);
    		output_current = motor_rot_fr.getOutputCurrent();
    		System.out.printf("output_current=%f\n", output_current);
    		Timer.delay(2.0);
    		motor_rot_fr.set(0);
    		output_current = motor_rot_fr.getOutputCurrent();
    		System.out.printf("output_current=%f\n", output_current);
    		Timer.delay(1.0);
    		speed = -speed;
    	}
    	
    	System.out.printf("End Autonomous mode\n");
    }

    /**
     * operatorControl - Runs during Tele-Operated mode
     */
    public void operatorControl() {
    	double stick_left_y;
    	double rot_target;
    	
    	System.out.printf("Start Tele-Operated mode\n");
    	rotation_control.enable();		// start calculating PIDOutput values
    	
        while (isOperatorControl() && isEnabled()) {
        	stick_left_y = (stick.getRawAxis(1));
        	rot_target = (stick_left_y + 1.0) * 2.5;
        	if (rot_target < 0.001) {
        		rot_target = 0.001;
        	} else if (rot_target > 4.9) {
        		rot_target = 4.9;
        	}
        	rotation_control.setSetpoint(rot_target);
        	SmartDashboard.putNumber("rot_target", rot_target);
        	SmartDashboard.putNumber("ai_rot_fr", ai_rot_fr.getAverageVoltage());
            Timer.delay(0.005);			// wait for new joystick values
        }
        
        rotation_control.disable();
        System.out.printf("End Tele-Operated mode\n");
    }

    /**
     * test - Runs during Test mode
     */
    public void test() {
    	double rot_fr;
    	int dist_fr;
    	double output_current_fr, output_voltage_fr;
    	
    	System.out.printf("Start Test mode\n");
    	
    	while (isTest() && isEnabled()) {
    		Timer.delay(1.0);
    		if (Utility.getUserButton()) {		// user button pressed?
    	    	double pdp_current, pdp_voltage, pdp_temp;
    	    	double rio_current, rio_voltage;

    	    	// Display PDP and RoboRIO status information
    	    	pdp_current = pdp.getTotalCurrent();
        		pdp_voltage = pdp.getVoltage();
        		pdp_temp = pdp.getTemperature();
       	    	rio_current = ControllerPower.getInputCurrent();
        		rio_voltage = ControllerPower.getInputVoltage();
        		System.out.printf("pdp_current=%f, pdp_voltage=%f, pdp_temp=%f, rio_current=%f, rio_voltage=%f\n",
						pdp_current, pdp_voltage, pdp_temp, rio_current, rio_voltage);
    			continue;
    		}
    		// display front right module status
    		rot_fr = ai_rot_fr.getAverageVoltage();
    		dist_fr = enc_dist_fr.getRaw();
    		output_current_fr = motor_rot_fr.getOutputCurrent();
    		output_voltage_fr = motor_rot_fr.getOutputVoltage();
    		System.out.printf("rot_fr=%f, dist_fr=%d, output_current_fr=%f, output_voltage_fr=%f\n",
    							rot_fr, dist_fr, output_current_fr, output_voltage_fr);
    	}
    	
    	System.out.printf("End Test mode\n");
    }
    
    /**
     * disabled - runs during Disabled mode
     */
    public void disabled() {
    	double sec_limit = 15.0;			// maximum time to stay in this function
    	double sec_disabled = 0;			// number of seconds we have been disabled
    	
    	System.out.printf("Start Disabled mode\n");
    	timer.reset();
    	timer.start();
    	
    	while (isDisabled() && sec_disabled <= sec_limit) {
    		sec_disabled += timer.get();
    		Timer.delay(0.5);
    	}
    	
    	System.out.printf("End Disabled mode (disabled for %f seconds)\n", sec_disabled);
    }
}
