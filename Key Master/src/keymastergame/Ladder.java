package keymastergame;

import java.awt.Color;
import java.awt.Graphics;

import keymastergame.framework.Box;
import keymastergame.framework.Vector;

public class Ladder {
	public Box collision;
	
	public Ladder(Vector pos) {
		collision = new Box(new Vector(pos.x, pos.y - StartingClass.TILESIZE/2),
							new Vector(StartingClass.TILESIZE/2, StartingClass.TILESIZE));
	}
	
	public void paint(Graphics g) {
		collision.paint(g, new Color(0, 0, 255, 150));
	}
	
	//no need for update
}
