package keymastergame;

import java.awt.Color;
import java.awt.Graphics;

import keymastergame.framework.Box;
import keymastergame.framework.Resource;
import keymastergame.framework.Vector;

public class Ladder {
	public Box collision;
	
	public Ladder(Vector pos) {
		collision = new Box(new Vector(pos.x, pos.y - StartingClass.TILESIZE/2),
							new Vector(StartingClass.TILESIZE/2, StartingClass.TILESIZE));
	}
	
	public void paint(Graphics g) {
		
		if (StartingClass.debugGraphics) {
		collision.paint(g, new Color(0, 0, 255, 150));
		
		} else {
			int xPos = (int)(collision.position.x - StartingClass.TILESIZE/2);
			int yPos = (int)(collision.position.y - 4);
		
			g.drawImage(Resource.ladderSpr, xPos, yPos, null);
		}
		
	}
	
	//no need for update
}
