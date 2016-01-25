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
	public static final int STATE_SCREEN_WIN = 2;
	public static final int STATE_SCREEN_LOSE = 3;

	public static int state;

	public static int frameSpeed = 17;

	// states;
	public static Game gameState;
	public static MainMenu menu;
	public static Screen currentScreen;
	public static GameOver gameOverScreen;
	public static Victory victoryScreen;

	// Image variables for double buffering
	private Image image;
	private Graphics second;

	public static final int WINDOWWIDTH = 960;
	public static final int WINDOWHEIGHT = 640;

	public static final int LEVELWIDTH = 30;
	public static final int LEVELHEIGHT = 20;

	public static final int TILESIZE = 32;

	public static final Box levelBoundary = new Box(new Vector(
			StartingClass.WINDOWWIDTH / 2, StartingClass.WINDOWHEIGHT / 2),
			new Vector(StartingClass.WINDOWWIDTH, StartingClass.WINDOWHEIGHT));

	@Override
	public void init() {

		Resource.loadResources();

		// initial sounds
		try {
			Sound.DIE = Applet.newAudioClip(new URL(getCodeBase()
					+ "/data/sounds/die.wav"));
			Sound.WIN = Applet.newAudioClip(new URL(getCodeBase()
					+ "/data/sounds/win.wav"));
			Sound.TILE_REMOVE = Applet.newAudioClip(new URL(getCodeBase()
					+ "/data/sounds/tile_remove.wav"));
			Sound.TILE_REAPPEAR = Applet.newAudioClip(new URL(getCodeBase()
					+ "/data/sounds/tile_reappear.wav"));
			Sound.GAMEOVER = Applet.newAudioClip(new URL(getCodeBase()
					+ "/data/sounds/gameover.wav"));
			Sound.MENU = Applet.newAudioClip(new URL(getCodeBase()
					+ "/data/sounds/menu.wav"));
			Sound.VICTORY = Applet.newAudioClip(new URL(getCodeBase()
					+ "/data/sounds/victory1.wav"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sound.setUpMusic(getCodeBase() + "/data/sounds/music.wav");

		setSize(WINDOWWIDTH, WINDOWHEIGHT);
		setBackground(Color.WHITE);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Key Master");
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
		Sound.closeMusic();
	}

	@Override
	public void run() {

		// game loop
		while (true) {
			switch (state) {
			case STATE_GAMEPLAY:
				if (gameState != null)
					gameState.update();
				break;
			case STATE_MAINMENU:
				if (menu != null)
					menu.update();
				break;
			case STATE_SCREEN_WIN:
				if (victoryScreen != null)
					victoryScreen.update();
				break;
			case STATE_SCREEN_LOSE:
				if (gameOverScreen != null)
					gameOverScreen.update();
				break;
			}

			repaint();
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
		case STATE_GAMEPLAY:
			if (gameState != null)
				gameState.paint(g);
			break;
		case STATE_MAINMENU:
			if (menu != null)
				menu.paint(g);
			break;
		case STATE_SCREEN_WIN:
			if (victoryScreen != null)
				victoryScreen.paint(g);
			break;
		case STATE_SCREEN_LOSE:
			if (gameOverScreen != null)
				gameOverScreen.paint(g);
			break;

		}
	}// end of paint method

	@Override
	public void keyPressed(KeyEvent e) {

		switch (state) {
		case STATE_GAMEPLAY:
			if (gameState != null)
				gameState.readInput(e.getKeyCode(), true);
			break;
		case STATE_MAINMENU:
			if (menu != null)
				menu.readInput(e.getKeyCode(), true);
			break;
		case STATE_SCREEN_WIN:
			if (victoryScreen != null)
				victoryScreen.readInput(e.getKeyCode(), true);
			break;
		case STATE_SCREEN_LOSE:
			if (gameOverScreen != null)
				gameOverScreen.readInput(e.getKeyCode(), true);
			break;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (state) {
		case STATE_GAMEPLAY:
			if (gameState != null)
				gameState.readInput(e.getKeyCode(), false);
			break;
		case STATE_MAINMENU:
			if (menu != null)
				menu.readInput(e.getKeyCode(), false);
			break;
		case STATE_SCREEN_WIN:
			if (victoryScreen != null)
				victoryScreen.readInput(e.getKeyCode(), false);
			break;
		case STATE_SCREEN_LOSE:
			if (gameOverScreen != null)
				gameOverScreen.readInput(e.getKeyCode(), false);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public static void changeState(int targetState) {

		gameState = null;
		menu = null;
		currentScreen = null;
		victoryScreen = null;
		gameOverScreen = null;

		switch (targetState) {

		case STATE_GAMEPLAY:
			state = STATE_GAMEPLAY;
			gameState = new Game();
			break;

		case STATE_MAINMENU:
			state = STATE_MAINMENU;
			menu = new MainMenu();
			break;

		case STATE_SCREEN_WIN:
			state = STATE_SCREEN_WIN;
			// currentScreen = new Screen(Screen.YOU_WIN, STATE_MAINMENU);
			victoryScreen = new Victory();
			break;

		case STATE_SCREEN_LOSE:
			state = STATE_SCREEN_LOSE;
			// currentScreen = new Screen(Screen.YOU_LOSE, STATE_MAINMENU);
			gameOverScreen = new GameOver();
			break;
		}

	}
}
