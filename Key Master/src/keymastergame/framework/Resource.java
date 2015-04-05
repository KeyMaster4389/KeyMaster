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
	
	//player sprites
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
	
	//menus and screens
	public static Image mainMenuScreen;
	public static Image menuSelector;
	public static Image menuNewGame;
	public static Image menuNewGame_Highlight;
	public static Image menuQuit;
	public static Image menuQuit_Highlight;
	
	
	
	public static boolean loadResources() {
		try {

			//player
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
			
			//everything else
			background = ImageIO.read(new File("data/background.jpg"));
			
			tileSpr = ImageIO.read(new File("data/tile.bmp"));
			ladderSpr = ImageIO.read(new File("data/Ladder.png"));
			
			doorOpenSpr = ImageIO.read(new File("data/door_open.png"));
			doorClosedSpr = ImageIO.read(new File("data/door_closed.png"));
			
			keySpr = ImageIO.read(new File("data/key.png"));

			//menus and screens
			mainMenuScreen = ImageIO.read(new File("data/menu/mainmenu.bmp"));
			menuSelector = ImageIO.read(new File("data/menu/selector.png")); 
			menuQuit = ImageIO.read(new File("data/menu/quit.png")); 
			menuQuit_Highlight = ImageIO.read(new File("data/menu/quit_highlight.png")); 
			menuNewGame = ImageIO.read(new File("data/menu/startnew.png")); 
			menuNewGame_Highlight = ImageIO.read(new File("data/menu/startnew_highlight.png")); 
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
}
