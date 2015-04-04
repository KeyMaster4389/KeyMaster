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
			
			
			background = ImageIO.read(new File("data/background.jpg"));
			
			tileSpr = ImageIO.read(new File("data/tile.bmp"));
			ladderSpr = ImageIO.read(new File("data/Ladder.png"));
			
			doorOpenSpr = ImageIO.read(new File("data/door_open.png"));
			doorClosedSpr = ImageIO.read(new File("data/door_closed.png"));
			
			keySpr = ImageIO.read(new File("data/key.png"));
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
		
		
		return true;
	}
	
}
