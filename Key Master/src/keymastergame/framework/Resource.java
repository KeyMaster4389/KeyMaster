package keymastergame.framework;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resource {

	public static Image background;

	public static Image tileSpr;
	public static Image ladderSpr;

	public static Image doorOpenSpr;
	public static Image doorClosedSpr;

	public static Image keySpr;

	// player sprites
	public static Image idle;
	public static Image air;
	public static Image climb1;
	public static Image climb2;
	public static Image run1;
	public static Image run2;
	public static Image run3;
	public static Image run4;
	public static Image run5;
	public static Image run6;
	public static Image die1;
	public static Image die2;
	public static Image die3;
	public static Image die4;
	public static Image die5;
	public static Image die6;
	public static Image die7;
	public static Image die8;
	public static Image die9;
	public static Image winning;
	
	//Enemy C
	public static Image enemyCLeft1;
	public static Image enemyCLeft2;
	public static Image enemyCLeft3;
	public static Image enemyCLeft4;
	public static Image enemyCRight1;
	public static Image enemyCRight2;
	public static Image enemyCRight3;
	public static Image enemyCRight4;
	

	// menus and screens
	public static Image mainMenuScreen;
	public static Image menuSelector;
	public static Image menuNewGame;
	public static Image menuNewGame_Highlight;
	public static Image menuQuit;
	public static Image menuQuit_Highlight;

	public static Image menuKey;

	// game over screen
	public static Image blackBackground;
	public static Image gameOver;
	public static Image tryAgain;
	public static Image credits;

	// victory screen
	public static Image victory1;
	public static Image victory2;
	public static Image congrats;

	// hud stuff
	public static Image number[];
	public static Image time;
	public static Image lives;
	public static Image level;

	public static boolean loadResources() {
		try {

			// player
			idle = ImageIO.read(new File("data/player/idle.png"));
			air = ImageIO.read(new File("data/player/air.png"));
			climb1 = ImageIO.read(new File("data/player/climb1.png"));
			climb2 = ImageIO.read(new File("data/player/climb2.png"));
			run1 = ImageIO.read(new File("data/player/run1.png"));
			run2 = ImageIO.read(new File("data/player/run2.png"));
			run3 = ImageIO.read(new File("data/player/run3.png"));
			run4 = ImageIO.read(new File("data/player/run4.png"));
			run5 = ImageIO.read(new File("data/player/run5.png"));
			run6 = ImageIO.read(new File("data/player/run6.png"));
			die1 = ImageIO.read(new File("data/player/die1.png"));
			die2 = ImageIO.read(new File("data/player/die2.png"));
			die3 = ImageIO.read(new File("data/player/die3.png"));
			die4 = ImageIO.read(new File("data/player/die4.png"));
			die5 = ImageIO.read(new File("data/player/die5.png"));
			die6 = ImageIO.read(new File("data/player/die6.png"));
			die7 = ImageIO.read(new File("data/player/die7.png"));
			die8 = ImageIO.read(new File("data/player/die8.png"));
			die9 = ImageIO.read(new File("data/player/die9.png"));
			winning = ImageIO.read(new File("data/player/winning.png"));
			
			//Enemy A
			enemyCLeft1 = ImageIO.read(new File("data/enemies/e2l1.png"));
			enemyCLeft2 = ImageIO.read(new File("data/enemies/e2l2.png"));
			enemyCLeft3 = ImageIO.read(new File("data/enemies/e2l3.png"));
			enemyCLeft4 = ImageIO.read(new File("data/enemies/e2l4.png"));
			enemyCRight1 = ImageIO.read(new File("data/enemies/e2r1.png"));
			enemyCRight2 = ImageIO.read(new File("data/enemies/e2r2.png"));
			enemyCRight3 = ImageIO.read(new File("data/enemies/e2r3.png"));
			enemyCRight4 = ImageIO.read(new File("data/enemies/e2r4.png"));

			// everything else
			background = ImageIO.read(new File("data/background.jpg"));

			tileSpr = ImageIO.read(new File("data/tile.bmp"));
			ladderSpr = ImageIO.read(new File("data/Ladder.png"));

			doorOpenSpr = ImageIO.read(new File("data/door_open.png"));
			doorClosedSpr = ImageIO.read(new File("data/door_closed.png"));

			keySpr = ImageIO.read(new File("data/key.png"));

			// menus and screens
			mainMenuScreen = ImageIO.read(new File("data/menu/mainmenu.bmp"));
			menuSelector = ImageIO.read(new File("data/menu/selector.png"));
			menuQuit = ImageIO.read(new File("data/menu/quit.png"));
			menuQuit_Highlight = ImageIO.read(new File(
					"data/menu/quit_highlight.png"));
			menuNewGame = ImageIO.read(new File("data/menu/startnew.png"));
			menuNewGame_Highlight = ImageIO.read(new File(
					"data/menu/startnew_highlight.png"));

			menuKey = ImageIO.read(new File("data/menu/keymenu2.png"));

			// game over screen
			blackBackground = ImageIO.read(new File(
					"data/menu/blackbackground.png"));
			gameOver = ImageIO.read(new File("data/menu/gameover.png"));
			tryAgain = ImageIO.read(new File("data/menu/tryagain.png"));
			credits = ImageIO.read(new File("data/menu/credits5.png"));

			// victory screen
			congrats = ImageIO.read(new File("data/menu/congrats.png"));
			victory1 = ImageIO.read(new File("data/menu/victory1.png"));
			victory2 = ImageIO.read(new File("data/menu/victory2.png"));

			// hud stuff
			number = new Image[10];
			number[0] = ImageIO.read(new File("data/0.png"));
			number[1] = ImageIO.read(new File("data/1.png"));
			number[2] = ImageIO.read(new File("data/2.png"));
			number[3] = ImageIO.read(new File("data/3.png"));
			number[4] = ImageIO.read(new File("data/4.png"));
			number[5] = ImageIO.read(new File("data/5.png"));
			number[6] = ImageIO.read(new File("data/6.png"));
			number[7] = ImageIO.read(new File("data/7.png"));
			number[8] = ImageIO.read(new File("data/8.png"));
			number[9] = ImageIO.read(new File("data/9.png"));
			time = ImageIO.read(new File("data/time.png"));

			lives = ImageIO.read(new File("data/lives.png"));
			level = ImageIO.read(new File("data/level.png"));

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
