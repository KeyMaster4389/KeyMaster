package keymastergame;

import java.awt.Color;
import java.awt.Graphics;

import keymastergame.framework.Box;
import keymastergame.framework.Vector;

public class Key extends GameObject {

	Player following = null;
	Door targetDoor = null;
	
	double maxFollowRange = 40;
	
	double lockOntoDoorRange = 50;
	
	int floatTime = 0;
	int floatLoopTime = 120;
	int floatAmplitude = 4;
	
	double maxSpeed = 4;
	
	double floatPosY;
	
	boolean active = true;
	
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

		if (active) {
		
			if (targetDoor == null) {
				//do object logic here
				if (following == null) {
					//do nothing	
		
					
				} else {
					//follow thing
					
					goTowardsTarget(maxFollowRange, following);
				}
			} else {
				if (collision.position.getDistanceTo(targetDoor.collision.position) > 2) {
				
					goTowardsTarget(0, targetDoor);
				
				} else {
					
					active = false;
					targetDoor.isOpen = true;
					
				}
				
			}
			
			floatTime++;
			if (floatTime >= floatLoopTime) floatTime = 0;
			
		}
		
	}
	
	public void paint(Graphics g) {
		
		if (active) {
		
			double tempY = collision.position.y;
			floatPosY = collision.position.y + (Math.sin(floatTime * (360/floatLoopTime) * (Math.PI/180)) * floatAmplitude);
			collision.position.y = floatPosY;
			
			collision.paint(g, Color.RED);
			
			collision.position.y = tempY;
		}
		
	}
	
	public void goTowardsTarget(double range, GameObject obj) {

		Vector diff = new Vector();
		diff.x = collision.position.x;
		diff.y = collision.position.y;
		
		diff.x -= obj.collision.position.x;
		diff.y -= obj.collision.position.y;
		
		double dist = diff.getMagnitude();

		double ang = Math.abs(Math.atan(diff.y/diff.x));
		
		if (dist > range) {
			double moveDist = dist - range;
			
			
			Vector move = new Vector();
			
			move.x = moveDist * Math.cos(ang);
			if (obj.collision.position.x < collision.position.x) move.x *= -1;
								
			move.y = moveDist * Math.sin(ang);
			if (obj.collision.position.y < collision.position.y) move.y *= -1;
			
			double mDist = move.getMagnitude();
			
			if (mDist > maxSpeed) {
				move.x = move.x / mDist * maxSpeed;
				move.y = move.y / mDist * maxSpeed;
			}
			
			
			collision.position.add(move);
		}
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
