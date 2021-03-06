package ca.mcgill.ecse211.ecse211_project;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class Track {
	public static final double WHEEL_RAD = 2.1; //Radius of wheel
	public static final double TRACK = 16.29;//Width of wheel axis
	public static final EV3LargeRegulatedMotor leftMotor =
			new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));
	public static final EV3LargeRegulatedMotor rightMotor =
			new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
	public static void main(String [] args) {
		
		leftMotor.rotate(convertAngle(WHEEL_RAD, TRACK, 360), true);
		rightMotor.rotate(-convertAngle(WHEEL_RAD, TRACK, 360), false);
		
//		leftMotor.rotate(convertDistance(WHEEL_RAD,3*30.48), true);
//		rightMotor.rotate(convertDistance(WHEEL_RAD,3*30.48), false);
		
//		leftMotor.rotate(convertAngle(WHEEL_RAD, TRACK, 360), true);
//		rightMotor.rotate(convertAngle(WHEEL_RAD, TRACK, 360), false);
	}
	
	private static int convertDistance(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}
	/**
	 * This method comes from SquareDriver class. It converts the desired turning
	 * angle to the degrees of rotation of the motor.
	 * 
	 * @param radius
	 * @param width
	 * @param angle
	 * @return
	 */
	private static int convertAngle(double radius, double width, double angle) {
		return convertDistance(radius, Math.PI * width * angle / 360.0);
	}

}
