package keymastergame;

import java.awt.Color;
import java.awt.Graphics;

import keymastergame.framework.Box;
import keymastergame.framework.Vector;

public class Key extends GameObject {

	Player following = null;
	
	double maxFollowRange = 40;
	
	int floatTime = 0;
	int floatLoopTime = 120;
	int floatAmplitude = 4;
	
	double floatPosY;
	
	public Key() {
		collision = new Box(new Vector(0,0), new Vector(16,16));
		velocity = new Vector(0,0);
		
		floatPosY = collision.position.y;
		
		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}

	public Key(Vector pos) {
		
		collision = new Box(pos, new Vector(16,16));
		velocity = new Vector(0,0);
		
		floatPosY = collision.position.y;
		
		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}
	
	public void update() {	

		
		//do object logic here
		if (following == null) {
			//do nothing	

			floatTime++;
			
		} else {
			//follow player
			
			Vector diff = new Vector();
			diff.x = collision.position.x;
			diff.y = collision.position.y;
			
			diff.x -= following.collision.position.x;
			diff.y -= following.collision.position.y;
			

			//diff.x = Math.abs(diff.x);
			//diff.y = Math.abs(diff.y);
			
			double dist = Math.sqrt((diff.x * diff.x) + (diff.y * diff.y));

			double ang = Math.abs(Math.atan(diff.y/diff.x));
			
			if (dist > maxFollowRange) {
				double moveDist = dist - maxFollowRange;
				
				
				Vector move = new Vector();
				
				move.x = moveDist * Math.cos(ang);
				if (following.collision.position.x < collision.position.x) move.x *= -1;
									
				move.y = moveDist * Math.sin(ang);
				if (following.collision.position.y < collision.position.y) move.y *= -1;
				
				collision.position.add(move);
			} else {

				floatTime++;
			}
			
		}

		if (floatTime >= floatLoopTime) floatTime = 0;
		
	}
	
	public void paint(Graphics g) {
		double tempY = collision.position.y;
		floatPosY = collision.position.y + (Math.sin(floatTime * (360/floatLoopTime) * (Math.PI/180)) * floatAmplitude);
		collision.position.y = floatPosY;
		
		collision.paint(g, Color.RED);
		
		collision.position.y = tempY;
	}
	
	//use null to stop following
	public void setFollow(Player plr) {
		if (plr == null) {
			following.hasKey = false;
			following = null;
			
			floatTime = 0;
			floatPosY = collision.position.y;
		} else {
			following = plr;
			plr.hasKey = true;
		}
	}
	
	public boolean collisionActive() {
		return false;
	}
}
