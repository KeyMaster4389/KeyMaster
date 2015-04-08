package keymastergame;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import keymastergame.framework.Resource;
import keymastergame.framework.Sound;

public class MainMenu {
	//main menu of the game, from here player can start new game or quit game
	//boot player to here if they beat the game or get a game over

	public int selection = 0;
	
	private final int SELECT_NEWGAME = 0;
	private final int SELECT_QUIT = 1;
	private final int SELECTION_COUNT = 2;

	private boolean keyUp = false;
	private boolean keyDown = false;
	private boolean keySelect = false;
	
	public final static int controlUp = KeyEvent.VK_UP;
	public final static int controlDown = KeyEvent.VK_DOWN;
	public final static int controlSelect = KeyEvent.VK_SPACE;
	
	public MainMenu() {
		
		
	}
	
	public void update() {
		if (keySelect) {
			if (selection == SELECT_NEWGAME) {
				Sound.MENU.play();
				StartingClass.changeState(StartingClass.STATE_GAMEPLAY);
			}
			if (selection == SELECT_QUIT) {
				Sound.MENU.play();
				System.exit(0);
			}
		} else if (keyUp && !keyDown) {
			keyUp = false;
			keyDown = false;
			if (selection > 0) {
				selection--;
			}
			
		} else if (keyDown && !keyUp) {
			keyUp = false;
			keyDown = false;
			if (selection < SELECTION_COUNT - 1) {
				selection++;
			}
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(Resource.mainMenuScreen, 0, 0, null);
		
		int optionsX = 300;
		int optionsY = 250;
		
		//draw menu options
		if (selection == SELECT_NEWGAME) {
			g.drawImage(Resource.menuNewGame_Highlight, optionsX, optionsY, null);	
		} else {
			g.drawImage(Resource.menuNewGame, optionsX, optionsY, null);	
		}
		optionsY += 50;

		if (selection == SELECT_QUIT) {
			g.drawImage(Resource.menuQuit_Highlight, optionsX, optionsY, null);	
		} else {
			g.drawImage(Resource.menuQuit, optionsX, optionsY, null);	
		}
		
		//draw pointer
		int y = 250 + selection * 50;
		g.drawImage(Resource.menuSelector, 140, y, null);		
	}
	
	public void readInput(int code, boolean pressed) {
		switch (code) {
		case controlUp: keyUp = pressed;
			break;
		case controlDown: keyDown = pressed;
			break;
		case controlSelect: keySelect = pressed;
			break;
		}
	}
}
