package keymastergame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;

import keymastergame.framework.Animation;

public class StartingClass extends Applet implements Runnable, KeyListener {

	public static int state;
	//private Intro introState;
	public static Game gameState;

	// for Image variables later
	private Image image;
	private Graphics second;
	
	public static URL base;

	private final int WINDOWWIDTH = 960;
	private final int WINDOWHEIGHT = 640;
	
	public static final int LEVELWIDTH = 30;
	public static final int LEVELHEIGHT = 20;
	
	public static final int TILESIZE = 32;

	@Override
	public void init() {
		
		setSize(WINDOWWIDTH, WINDOWHEIGHT);
		setBackground(Color.WHITE);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Key Master");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		gameState = new Game();
		gameState.loadLevel("data/level1.txt");

		// TODO: Image Setups

		
		

	}// end of init method

	@Override
	public void start() {

		Thread thread = new Thread(this);
		thread.start();

	}// end of start method

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {

		// game loop
		while (true) {
			// here we'll call the update methods of the game
			// objects as well as the animate method.

			long startTime = System.nanoTime();
			gameState.update();
			
			
			repaint();
			

			long endTime = System.nanoTime();

			//System.out.println("Game update time: " + (double)(endTime - startTime)/100000  + " milliseconds");
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}// end game loop

	}// end of run method

	@Override
	public void update(Graphics g) {

		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}// end of update method

	@Override
	public void paint(Graphics g) {

		// here will be calls to g.drawImage for
		// various game objects
		gameState.paint(g);

	}// end of paint method

	@Override
	public void keyPressed(KeyEvent e) {
		gameState.readInput(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		gameState.readInput(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
