package keymastergame.objects;

import java.awt.Color;
import java.awt.Graphics;

import keymastergame.StartingClass;
import keymastergame.framework.Box;
import keymastergame.framework.Resource;
import keymastergame.framework.Vector;

public class Key extends GameObject {


	public static final Vector size = new Vector(16, 16);
	
	public Player following = null;
	public Door targetDoor = null;
	
	double maxFollowRange = 30;
	
	double lockOntoDoorRange = 50;
	
	int floatTime = 0;
	int floatLoopTime = 120;
	int floatAmplitude = 4;
	
	double maxSpeed = 4;
	
	double floatPosY;
	
	boolean active = true;
	
	public Key() {
		collision = new Box(new Vector(0,0), size);
		velocity = new Vector(0,0);
		
		floatPosY = collision.position.y;
		
		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}

	public Key(Vector pos) {
		
		collision = new Box(pos, size);
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
				if (following == null) {
					//do nothing
					
				} else {
					//follow player
					goTowardsTarget(maxFollowRange, following);
					

					if (following.collision.intersects(StartingClass.gameState.door.collision)) {
						targetDoor = StartingClass.gameState.door;
					}		
					
					
				}
			} else {
				//go to door
				if (collision.position.getDistanceTo(targetDoor.collision.position) > 2) {

					maxSpeed = 2;
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

			floatPosY = collision.position.y + (Math.sin(floatTime * (360/floatLoopTime) * (Math.PI/180)) * floatAmplitude);
			
			if (StartingClass.debugGraphics) {

				double tempY = collision.position.y;
				collision.position.y = floatPosY;
				
				collision.paint(g, Color.RED);
				
				collision.position.y = tempY;
			
			} else {
				int xPos = (int)(collision.position.x);
				int yPos = (int)(floatPosY);
				
				if (!faceLeft) {
					g.drawImage(Resource.keySpr, xPos - 14, yPos - 7, null);
				} else {
					
					//vvvv how to flip vvvv
					
					g.drawImage(Resource.keySpr, xPos + 14, yPos - 7, -Resource.keySpr.getWidth(null), Resource.keySpr.getHeight(null), null);
				}
				
			}
			
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
			faceLeft = false;
			
			double moveDist = dist - range;
			
			
			Vector move = new Vector();
			
			move.x = moveDist * Math.cos(ang);
			if (obj.collision.position.x < collision.position.x) {
				move.x *= -1;
				faceLeft = true;
			}
								
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
