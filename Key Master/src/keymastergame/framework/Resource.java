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
	
	
	public static boolean loadResources() {
		try {
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
