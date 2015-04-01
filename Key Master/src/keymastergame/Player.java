package keymastergame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import keymastergame.framework.Vector;

public class Player extends GameObject {


	public Player(Vector position) {
		super(position);
	}

	public void update() {	
		//System.out.println(collision.position.x + " " + collision.position.y);
		
		
		//do object logic here
		
		
		
		velocity.y += gravAcc;
		
		collision.position.add(velocity);
	}
	
	public void paint(Graphics g) {
		collision.paint(g, Color.GREEN);
	}
	
	
}
