package keymastergame.framework;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resource {

	public static Image tileSpr;
	public static Image ladderSpr;
	
	public static boolean loadResources() {
		try {
			tileSpr = ImageIO.read(new File("data/tile.bmp"));
			ladderSpr = ImageIO.read(new File("data/Ladder.png"));
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
		
		
		return true;
	}
	
}
