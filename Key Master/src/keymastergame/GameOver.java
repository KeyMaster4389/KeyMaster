package keymastergame;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import keymastergame.framework.Resource;
import keymastergame.framework.Sound;

public class GameOver {
	// Game over screen.

	private boolean keySelect = false;

	public final static int controlSelect = KeyEvent.VK_SPACE;
	
	public GameOver(){
		
	}

	public void update() {
		
		Sound.MUSIC.stop();

		if (keySelect) {
			Sound.MENU.play();
			StartingClass.changeState(StartingClass.STATE_MAINMENU);
		}

	}
	
	public void paint(Graphics g) {
		
		g.drawImage(Resource.blackBackground, 0, 0, null);
		g.drawImage(Resource.menuKey, 320, 25, null);
		g.drawImage(Resource.gameOver, 165, 215, null);
		g.drawImage(Resource.tryAgain, 350, 400, null);
		g.drawImage(Resource.credits, 460, 590, null);
		
	}

	public void readInput(int code, boolean pressed) {
		switch (code) {

		case controlSelect:
			keySelect = pressed;
			break;
		
		
		}
	}

}
