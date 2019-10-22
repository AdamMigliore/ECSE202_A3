
import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

/**
 * @author adamd
 * Assignment 3 for McGill Fall19 ECSE202 class
 */
public class bSim extends GraphicsProgram {

	private static final int WIDTH = 1200; // Width of window
	private static final int HEIGHT = 600; // Height of window
	private static final int OFFSET = 200; // offset for plane
	private static final double SCALE = HEIGHT / 100; // pixels per meter
	private static final int NUMBALLS = 10; // # balls to simulate
	private static final double MINSIZE = 1.0; // Minumum ball radius (meters)
	private static final double MAXSIZE = 7.0; // Maximum ball radius (meters)
	private static final double EMIN = 0.2; // Minimum loss coefficient
	private static final double EMAX = 0.6; // Maximum loss coefficient
	private static final double VoMIN = 40.0; // Minimum velocity (meters/sec)
	private static final double VoMAX = 50.0; // Maximum velocity (meters/sec)
	private static final double ThetaMIN = 80.0; // Minimum launch angle (degrees)
	private static final double ThetaMAX = 100.0; // Maximum launch angle (degrees)
	private static final int GP_HEIGHT = 3; // Maximum launch angle (degrees)
	private RandomGenerator rgen = new RandomGenerator(); //randomGenerator 
	private bTree myTree = new bTree();
	private GLabel message;
	/**
	 * the main loop of the simulation which will generate the random parameters for the number of balls in the simulation
	 */
	public void run() {
		
		rgen.setSeed((long) 424242);//required from prof. to match simulation parameters
		
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
			myTree.addNode(ball);
			ball.start();//start the balls thread

		}
		
		while (myTree.isRunning()) {//not exactly
			myTree.checkRunning();
		}
		
		message = new GLabel("CR to continue", WIDTH-100,HEIGHT-100);
		message.setColor(Color.RED);
		add(message);
		addMouseListeners();
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
	
	public void mousePressed(MouseEvent e) {
		myTree.stackBalls(this);
		message.setLabel("All Stacked");
	}
	
	public double getScale() {
		return SCALE;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
}
