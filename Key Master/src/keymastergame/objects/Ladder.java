package keymastergame.objects;

import java.awt.Color;
import java.awt.Graphics;

import keymastergame.StartingClass;
import keymastergame.framework.Box;
import keymastergame.framework.Resource;
import keymastergame.framework.Vector;

public class Ladder {

	public static final Vector size = new Vector(16, 32);
	
	public Box collision;
	
	public Ladder(Vector pos) {
		
		int offsetY = (int)((StartingClass.TILESIZE - Player.size.y)/2 - (StartingClass.TILESIZE/2));
		System.out.println(offsetY);
		
		collision = new Box(new Vector(pos.x, pos.y + offsetY), size);
	}
	
	public void paint(Graphics g) {
		
		if (StartingClass.debugGraphics) {
		collision.paint(g, new Color(0, 0, 255, 150));
		
		} else {
			int xPos = (int)(collision.position.x - StartingClass.TILESIZE/2);
			int yPos = (int)(collision.position.y - 5);
		
			g.drawImage(Resource.ladderSpr, xPos, yPos, null);
		}
		
	}
	
	
}
