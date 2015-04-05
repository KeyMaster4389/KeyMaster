package keymastergame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import keymastergame.framework.Box;
import keymastergame.framework.Resource;
import keymastergame.framework.Vector;

@SuppressWarnings("serial")
public class StartingClass extends Applet implements Runnable, KeyListener {

	public static final boolean debugGraphics = false;
	
	public static int state;
	public static final int STATE_GAMEPLAY = 0;
	public static final int STATE_MAINMENU = 1;
	public static final int STATE_GAMEOVERSCREEN = 2;
	public static final int STATE_VICTORYSCREEN = 3;
	
	
	public static int frameSpeed = 17;
	
	
	//private Intro introState;
	public static Game gameState;
	
	// Image variables for double buffering
	private Image image;
	private Graphics second;
	
	public static URL base;

	
	private static final int WINDOWWIDTH = 960;
	private static final int WINDOWHEIGHT = 640;
	
	public static final int LEVELWIDTH = 30;
	public static final int LEVELHEIGHT = 20;
	
	public static final int TILESIZE = 32;
	
	public static final Box levelBoundary = new Box(new Vector(StartingClass.WINDOWWIDTH/2, StartingClass.WINDOWHEIGHT/2),
			new Vector(StartingClass.WINDOWWIDTH, StartingClass.WINDOWHEIGHT));;

	@Override
	public void init() {
		
		Resource.loadResources();
		
		setSize(WINDOWWIDTH, WINDOWHEIGHT);
		setBackground(Color.WHITE);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Key Master");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			
		}
		
		gameState = new Game();

		Thread thread = new Thread(this);
		thread.start();
		

	}// end of init method

	@Override
	public void start() {


	}// end of start method

	@Override
	public void stop() {
		
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void run() {

		// game loop
		while (true) {
			// here we'll call the update methods of the game
			// objects as well as the animate method.

			//long startTime = System.nanoTime();
			
			gameState.update();
			
			
			repaint();
			
			//long endTime = System.nanoTime();
			//System.out.println("Game update time: " + (double)(endTime - startTime)/100000  + " milliseconds");
			
			try {
				Thread.sleep(frameSpeed);
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

	}

}
