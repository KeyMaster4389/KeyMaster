package keymastergame;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import keymastergame.framework.Resource;
import keymastergame.framework.Sound;

public class Victory {

	private boolean keySelect = false;

	public final static int controlSelect = KeyEvent.VK_SPACE;

	public Victory() {

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
		g.drawImage(Resource.menuKey, 335, 15, null);
		g.drawImage(Resource.congrats, 165, 220, null);
		g.drawImage(Resource.victory1, 300, 365, null);
		g.drawImage(Resource.victory2, 300, 430, null);
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
