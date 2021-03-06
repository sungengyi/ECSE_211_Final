package ca.mcgill.ecse211.ecse211_project;

import java.util.Map;

import ca.mcgill.ecse211.WiFiClient.wifiTest.wifiTest;
import ca.mcgill.ecse211.color.ColorData;
import ca.mcgill.ecse211.color.Display;
import ca.mcgill.ecse211.localizer.LightLocalizer;
import ca.mcgill.ecse211.localizer.UltrasonicLocalizer;
import ca.mcgill.ecse211.navigator.Navigator;
import ca.mcgill.ecse211.odometer.Odometer;
import ca.mcgill.ecse211.odometer.OdometerExceptions;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;

public class Project {
	// The parameters for driving the robot
		// The island parameters
	
	public static final int LOW_SPEED = 200; // this is the slow speed for precise movement
	public static final int MEDIUM_SPEED = 300; // this is the medium speed for intermediate movement
	public static final int HIGH_SPEED = 400; // this is the fast motor speed for less precious, faster movement (long
												// distance travel)
	public static final double HIGH_PROBE = 11; // the distance traveled when probing the higher branches
	public static final double LOW_PROBE = 11.5; // the distance traveled when probing the lower branches
	public static final int DISTANCE = 30; // distance from the wall used by the ultrasonic sensor during the ultrasonic
											// localization

		public static double Island_LL_x = 0; // x coordinate of the lower left corner of the island
		public static double Island_LL_y = 5; // y coordinate of the lower left corner of the island
		public static double Island_UR_x = 6; // x coordinate of the upper right corner of the island
		public static double Island_UR_y = 8; // y coordinate of the upper right corner of the island

		// The team-specific parameters
		public static int corner = 1; // the starting corner
		public static double LL_x = 2; // x coordinate of the lower left corner of the home section
		public static double LL_y = 0; // y coordinate of the lower left corner of the home section
		public static double UR_x = 8; // x coordinate of the upper right corner of the home section
		public static double UR_y = 3; // y coordinate of the upper right corner of the home section
		public static double TN_LL_x = 2; // x coordinate of the lower left of the tunnel
		public static double TN_LL_y = 3; // y coordinate of the lower left of the tunnel
		public static double TN_UR_x = 3; // x coordinate of the upper right of the tunnel
		public static double TN_UR_y = 5; // y coordinate of the upper right of the tunnel
		public static double IS_LL_X = 0;
		public static double IS_LL_Y = 0;
		public static double IS_UR_X = 0;
		public static double IS_UR_Y = 0;
		public static double SZ_LL_x = 0; 
		public static double SZ_LL_y = 0; 
		public static double SZ_UR_x = 0; 
		public static double SZ_UR_y = 0;
	//TESTED CONSTANTS, DO NOT CHANGE
	public static final double WHEEL_RAD = 2.1; //Radius of wheel
	public static final double TRACK = 16.29;//Width of wheel axis
	public static final double TILE_SIZE = 30.48;
	public static final double OFF_SET = 2.5; // this is the offset from the 2 line-detecting light sensors to the wheel

	private static final TextLCD lcd = LocalEV3.get().getTextLCD();
	private static final Port leftLightPort = LocalEV3.get().getPort("S4");
	private static final Port rightLightPort = LocalEV3.get().getPort("S1");
	private static final Port frontUSPort = LocalEV3.get().getPort("S3");
	private static final Port colorPort = LocalEV3.get().getPort("S2");


	//package-private
	public static final EV3LargeRegulatedMotor leftMotor =
			new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
	public static final EV3LargeRegulatedMotor rightMotor =
			new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));
	public static final EV3LargeRegulatedMotor upMotor =
			new EV3LargeRegulatedMotor(LocalEV3.get().getPort("C"));
	public static final EV3LargeRegulatedMotor ultraMotor =
			new EV3LargeRegulatedMotor(LocalEV3.get().getPort("B"));
	@SuppressWarnings("deprecation")
	public static void main (String [] args) throws OdometerExceptions, InterruptedException{
		
		Map data = wifiTest.WIFI();
	

		int redTeam = ((Long) data.get("RedTeam")).intValue();
		int greenTeam = ((Long) data.get("GreenTeam")).intValue();

		
		lcd.clear();
		if (redTeam == 9) {

			corner = ((Long) data.get("RedCorner")).intValue(); // the starting corner
			LL_x = ((Long) data.get("Red_LL_x")).intValue(); // x coordinate of the lower left corner of the home
																// section
			LL_y = ((Long) data.get("Red_LL_y")).intValue(); // y coordinate of the lower left corner of the home
																// section
			UR_x = ((Long) data.get("Red_UR_x")).intValue(); // x coordinate of the upper right corner of the home
																// section
			UR_y = ((Long) data.get("Red_UR_y")).intValue(); // y coordinate of the upper right corner of the home
																// section
			TN_LL_x = ((Long) data.get("TNR_LL_x")).intValue(); // x coordinate of the lower left of the tunnel
			TN_LL_y = ((Long) data.get("TNR_LL_y")).intValue(); // y coordinate of the lower left of the tunnel
			TN_UR_x = ((Long) data.get("TNR_UR_x")).intValue(); // x coordinate of the upper right of the tunnel
			TN_UR_y = ((Long) data.get("TNR_UR_y")).intValue(); // y coordinate of the upper right of the tunnel
			SZ_LL_x = ((Long) data.get("SZR_LL_x")).intValue(); // x coordinate of the can tree
			SZ_LL_y = ((Long) data.get("SZR_LL_y")).intValue(); // y coordinate of the can tree
			SZ_UR_x = ((Long) data.get("SZR_UR_x")).intValue(); // x coordinate of the can tree
			SZ_UR_y = ((Long) data.get("SZR_UR_y")).intValue(); // y coordinate of the can tree
			

		} else {

			corner = ((Long) data.get("GreenCorner")).intValue(); // the starting corner
			LL_x = ((Long) data.get("Green_LL_x")).intValue(); // x coordinate of the lower left corner of the home
																// section
			LL_y = ((Long) data.get("Green_LL_y")).intValue(); // y coordinate of the lower left corner of the home
																// section
			UR_x = ((Long) data.get("Green_UR_x")).intValue(); // x coordinate of the upper right corner of the home
																// section
			UR_y = ((Long) data.get("Green_UR_y")).intValue(); // y coordinate of the upper right corner of the home
																// section
			TN_LL_x = ((Long) data.get("TNG_LL_x")).intValue(); // x coordinate of the lower left of the tunnel
			TN_LL_y = ((Long) data.get("TNG_LL_y")).intValue(); // y coordinate of the lower left of the tunnel
			TN_UR_x = ((Long) data.get("TNG_UR_x")).intValue(); // x coordinate of the upper right of the tunnel
			TN_UR_y = ((Long) data.get("TNG_UR_y")).intValue(); // y coordinate of the upper right of the tunnel
			SZ_LL_x = ((Long) data.get("SZG_LL_x")).intValue(); // x coordinate of the can tree
			SZ_LL_y = ((Long) data.get("SZG_LL_y")).intValue(); // y coordinate of the can tree
			SZ_UR_x = ((Long) data.get("SZG_UR_x")).intValue(); // x coordinate of the can tree
			SZ_UR_y = ((Long) data.get("SZG_UR_y")).intValue(); // y coordinate of the can tree
			

		}

		Island_LL_x = ((Long) data.get("Island_LL_x")).intValue(); // x coordinate of the lower left corner of the
																	// island
		Island_LL_y = ((Long) data.get("Island_LL_y")).intValue(); // y coordinate of the lower left corner of the
																	// island
		Island_UR_x = ((Long) data.get("Island_UR_x")).intValue(); // x coordinate of the upper right corner of the
																	// island
		Island_UR_y = ((Long) data.get("Island_UR_y")).intValue(); // y coordinate of the upper right corner of the														// island

		ultraMotor.lock(500);
		
		int buttonChoice;
		@SuppressWarnings("resource") // Because we don't bother to close this resource
		SensorModes frontUSSensor = new EV3UltrasonicSensor(frontUSPort); // usSensor is the instance
		SampleProvider frontUSDistance = frontUSSensor.getMode("Distance");// usDistance provides samples from

		@SuppressWarnings("resource")
		SensorModes leftLightSensor = new EV3ColorSensor(leftLightPort);//lightSensor is the instance 
		SampleProvider leftLightIntensity = leftLightSensor.getMode("Red");

		@SuppressWarnings("resource")
		SensorModes rightLightSensor = new EV3ColorSensor(rightLightPort);//lightSensor is the instance 
		SampleProvider  rightLightIntensity = rightLightSensor.getMode("Red");

	
		// Odometer related objects
		Odometer odometer = Odometer.getOdometer(leftMotor, rightMotor, TRACK, WHEEL_RAD); 

		//Instances of localizers
		LightLocalizer lightLocal = new LightLocalizer(leftLightIntensity, rightLightIntensity, leftMotor, rightMotor, TRACK, WHEEL_RAD );
		UltrasonicLocalizer usLocal = new UltrasonicLocalizer (frontUSDistance, odometer, leftMotor, rightMotor, TRACK, WHEEL_RAD );
		Display odometryDisplay = new Display(lcd); // No need to change
		Thread odoThread = new Thread(odometer);
		odoThread.start();
		Thread odoDisplay = new Thread(odometryDisplay);
		odoDisplay.start();

		lcd.clear();

		usLocal.fallingEdge();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		lightLocal.localize();
		Sound.beep();	
		Sound.beep();	
		Sound.beep();	

		@SuppressWarnings("resource")
		SensorModes colorData = new EV3ColorSensor(colorPort);//lightSensor is the instance 
		SampleProvider colorSample = colorData.getMode("RGB");	
		
		ColorData color = new ColorData(colorSample);
		
		Navigator oa = new Navigator(odometer,leftMotor, rightMotor, TRACK,WHEEL_RAD);
		Sound.beep();

		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		oa.TravelToTunnel(lightLocal);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		double left = lightLocal.getLightData(leftLightIntensity);
		double right = lightLocal.getLightData(rightLightIntensity);
		lightLocal.correction(left, right);
		
		oa.TravelToSearchArea();
		Sound.beep();	
		Sound.beep();	
		Sound.beep();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		oa.SearchAndGrabTest(ultraMotor,upMotor,color,frontUSDistance);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		oa.TravelBackToTunnel(lightLocal);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		lightLocal.localize();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		oa.TravelToBase();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		oa.releaseCan(ultraMotor);
		Sound.beep();	
		Sound.beep();	
		Sound.beep();
		Sound.beep();	
		Sound.beep();	

		

	}
	
}