package org.usfirst.frc.team2220.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TwilightTalon extends CANTalon{
	double MAXTEMP;
	double MAXCURRENT;
	final int Port;
	
	
	public TwilightTalon(int port) {
		super(port);
		MAXCURRENT = 27.0;	//Normal Load Value
		MAXTEMP = 5.0;	//Could not find temp value, currently placeholder
		Port = port;
	}
	
	void setMaxCurrent(double newCurrent) {
		MAXCURRENT = newCurrent;
	}
	
	void setMaxTemp(double newTemp) {
		MAXTEMP = newTemp;
	}
	
	boolean test() {
		boolean test = true;
		if (isOverMaxCurrent()) { test = false; }
		if (isOverMaxTemp()) {test = false; }
		return test;
	}
	
	boolean isOverMaxCurrent() {
		double CurrCurrent = this.getOutputCurrent(); //Returns Amperes
		if (CurrCurrent > MAXCURRENT) {
			return true;
		} else {
			return false;
		}
	}
	
	boolean isOverMaxTemp() {
		double CurrTemp = this.getTemperature(); //Returns Celsius
		if (CurrTemp > MAXTEMP) {
			return true;
		} else {
			return false;
		}
	}
	
	void stop() {
		this.disableControl();
	}
	
	void printWarning () {	//Checks to see if thresholds are passed
		if (isOverMaxCurrent()) {
			SmartDashboard.putBoolean("isOverMaxCurrent",isOverMaxCurrent()); //insert method that SmartDashboard.putNumber()s to dashboard
		}
		if (isOverMaxTemp()) {
			SmartDashboard.putBoolean("isOverMaxTemp",isOverMaxTemp()); //insert method that SmartDashboard.putNumber()s to dashboard
		}
	}
	
	void print() {	//Status report
		double CurrTemp = this.getTemperature(); //Returns Celsius
		double CurrCurrent = this.getOutputCurrent(); //Returns Amperes
	    int id = this.getDeviceID();
	    
		SmartDashboard.putNumber(id + " Current Current:",CurrCurrent);
		SmartDashboard.putNumber(id + " Current Temperature:",CurrTemp);
	}
}
