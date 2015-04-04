package keymastergame.framework;

import java.awt.Color;
import java.awt.Graphics;

public class Box {
	public Vector position;
	public Vector size;
	
	public Box() {
		position = new Vector();
		size = new Vector();
	}
	
	public Box(Vector pos, Vector si) {
		position = pos;
		size = si;
	}
	
	public Box(Box b) {
		position = b.position;
		size = b.size;
	}
	
	public void paint(Graphics g, Color c) {
				
		g.setColor(c);
		g.fillRect((int)(position.x - size.x/2), (int)(position.y - size.y/2), (int)size.x, (int)size.y);
	}
	
	//check if point is within box's bounds
	public boolean contains(Vector p) {
		if (p.x < position.x + size.x/2 && p.x > position.x - size.x/2
				&& p.y < position.y + size.y/2 && p.y > position.y - size.y/2) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean contains(Box b) {
		double left = position.x - size.x/2;
		double right = position.x + size.x/2;
		double top = position.y - size.y/2;
		double bottom = position.y + size.y/2;
		
		double bLeft = b.position.x - b.size.x/2;
		double bRight = b.position.x + b.size.x/2;
		double bTop = b.position.y - b.size.y/2;
		double bBottom = b.position.y + b.size.y/2;
		
		if (bLeft >= left && bRight <= right && bTop >= top && bBottom <= bottom)
			return true;
		else
			return false;
		
	}
	
	public boolean containsWithEdge(Vector p) {
		if (p.x <= position.x + size.x/2 && p.x >= position.x - size.x/2
				&& p.y <= position.y + size.y/2 && p.y >= position.y - size.y/2) {
			return true;
		} else {
			return false;
		}
	}
	
	//check if box is intersecting this box
	public boolean intersects(Box b) {
		Box r = new Box();
		
		r.size.x = size.x;
		r.size.y = size.y;
		r.position.x = position.x;
		r.position.y = position.y;
		
		r.size.add(b.size);
		
		return r.contains(b.position);
	}
}
