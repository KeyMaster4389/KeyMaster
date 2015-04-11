package keymastergame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.MalformedURLException;
import java.net.URL;

import keymastergame.framework.Box;
import keymastergame.framework.Resource;
import keymastergame.framework.Sound;
import keymastergame.framework.Vector;

@SuppressWarnings("serial")
public class StartingClass extends Applet implements Runnable, KeyListener {

	public static final boolean debugGraphics = false;
	
	public static final int STATE_GAMEPLAY = 0;
	public static final int STATE_MAINMENU = 1;
	public static final int STATE_GAMEOVERSCREEN = 2;
	public static final int STATE_VICTORYSCREEN = 3;

	public static int state;
	
	public static int frameSpeed = 17;
	
	
	//states;
	public static Game gameState;
	public static MainMenu menu;
	//public static Screen gameOverScreen;
	//public static Screen victoryScreen;
	
	// Image variables for double buffering
	private Image image;
	private Graphics second;
	
	//public static URL base;

	
	public static final int WINDOWWIDTH = 960;
	public static final int WINDOWHEIGHT = 640;
	
	public static final int LEVELWIDTH = 30;
	public static final int LEVELHEIGHT = 20;
	
	public static final int TILESIZE = 32;
	
	public static final Box levelBoundary = new Box(new Vector(StartingClass.WINDOWWIDTH/2, StartingClass.WINDOWHEIGHT/2),
			new Vector(StartingClass.WINDOWWIDTH, StartingClass.WINDOWHEIGHT));

	@Override
	public void init() {
		
		Resource.loadResources();
		
		//initial sounds
		try {
			Sound.DIE = Applet.newAudioClip(new URL(getCodeBase() + "/data/sounds/die.wav"));
			Sound.WIN = Applet.newAudioClip(new URL(getCodeBase() + "/data/sounds/win.wav"));
			Sound.MENU = Applet.newAudioClip(new URL(getCodeBase() + "/data/sounds/menu.wav"));
			Sound.TILE_REMOVE = Applet.newAudioClip(new URL(getCodeBase() + "/data/sounds/tile_remove.wav"));
			Sound.TILE_REAPPEAR = Applet.newAudioClip(new URL(getCodeBase() + "/data/sounds/tile_reappear.wav"));
			Sound.GAMEOVER = Applet.newAudioClip(new URL(getCodeBase() + "/data/sounds/gameover.wav"));
			Sound.MUSIC = Applet.newAudioClip(new URL(getCodeBase() + "/data/sounds/music.wav"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		setSize(WINDOWWIDTH, WINDOWHEIGHT);
		setBackground(Color.WHITE);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Key Master");
		
		//try {
		//	base = getDocumentBase();
		//} catch (Exception e) {
			
		//}
		
		changeState(STATE_MAINMENU);

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
			
			switch (state) {
				case STATE_GAMEPLAY: if (gameState != null) gameState.update();
				break;
				case STATE_MAINMENU: if (menu != null) menu.update();
				break;
			
			
			}
			
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

		switch (state) {
			case STATE_GAMEPLAY: if (gameState != null) gameState.paint(g);
			break;
			case STATE_MAINMENU: if (menu != null) menu.paint(g);
			break;
		
		
		
		}
	}// end of paint method

	@Override
	public void keyPressed(KeyEvent e) {
		

		switch (state) {
			case STATE_GAMEPLAY: if (gameState != null) gameState.readInput(e.getKeyCode(), true);
			break;
			case STATE_MAINMENU: if (menu != null) menu.readInput(e.getKeyCode(), true);
			break;
		
		
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (state) {
		case STATE_GAMEPLAY: if (gameState != null) gameState.readInput(e.getKeyCode(), false);
		break;
		case STATE_MAINMENU: if (menu != null) menu.readInput(e.getKeyCode(), false);
		break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public static void changeState(int targetState) {
		
		gameState = null;
		menu = null;
		//gameOverScreen = null;
		//victoryScreen = null;
		
		switch (targetState) {
		
		case STATE_GAMEPLAY: state = STATE_GAMEPLAY; gameState = new Game();
			break;

		case STATE_MAINMENU: state = STATE_MAINMENU; menu = new MainMenu();
			break;
			
		//do nothing for now
		case STATE_GAMEOVERSCREEN:
			break;
			
		case STATE_VICTORYSCREEN:
			break;
		
		}
		
	}
}
