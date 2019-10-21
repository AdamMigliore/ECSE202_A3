
import java.awt.Color;

import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

/**
 * @author adamd
 * Assignment 2 for McGill Fall19 ECSE202 class
 */
public class bSim extends GraphicsProgram {

	private static final int WIDTH = 1200; // Width of window
	private static final int HEIGHT = 600; // Height of window
	private static final int OFFSET = 200; // offset for plane
	private static final double SCALE = HEIGHT / 100; // pixels per meter
	private static final int NUMBALLS = 100; // # balls to simulate
	private static final double MINSIZE = 1.0; // Minumum ball radius (meters)
	private static final double MAXSIZE = 10.0; // Maximum ball radius (meters)
	private static final double EMIN = 0.1; // Minimum loss coefficient
	private static final double EMAX = 0.6; // Maximum loss coefficient
	private static final double VoMIN = 40.0; // Minimum velocity (meters/sec)
	private static final double VoMAX = 50.0; // Maximum velocity (meters/sec)
	private static final double ThetaMIN = 80.0; // Minimum launch angle (degrees)
	private static final double ThetaMAX = 100.0; // Maximum launch angle (degrees)
	private static final int GP_HEIGHT = 3; // Maximum launch angle (degrees)
	private RandomGenerator rgen = new RandomGenerator(); //randomGenerator 

	/**
	 * the main loop of the simulation which will generate the random parameters for the number of balls in the simulation
	 */
	public void run() {
		
		rgen.setSeed((long) 0.12345);//required from prof. to match simulation parameters
		
		setupDisplay();
		
		for (int i = 0; i < NUMBALLS; i++) {

			double bSize = (int) rgen.nextDouble(MINSIZE, MAXSIZE); //randomly generate ball size element of [MINSIZE,MAXSIZE]
			Color bColor = rgen.nextColor();//randomly generate color
			double bLoss = rgen.nextDouble(EMIN, EMAX);//randomly generate energy loss element of [EMIN,EMAX]
			double Vo = rgen.nextDouble(VoMIN, VoMAX); //randomly generate initial velocity element of [VoMIN,VoMAX]
			double theta = rgen.nextDouble(ThetaMIN, ThetaMAX); //randomly generate theta element of [ThetaMIN,ThetaMAX]

			aBall ball = new aBall(gUtil.pixelsToMeter(SCALE, WIDTH / 2), bSize, Vo, theta, bSize, bColor, bLoss, SCALE,
					WIDTH, HEIGHT);//Generate a new aBall object with it's initial parameters
			add(ball.getBall());//add the ball to the simulation
			ball.start();//start the balls thread

		}
	}

	/**
	 * Set up the simulation display: create and add the plane to the simulation and resize the window
	 */
	private void setupDisplay() {
		this.resize(WIDTH, HEIGHT + OFFSET);

		GRect plane = new GRect(0, HEIGHT, WIDTH, GP_HEIGHT);
		plane.setFilled(true);
		plane.setColor(Color.BLACK);
		add(plane);
	}
}
