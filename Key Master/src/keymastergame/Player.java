package keymastergame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import keymastergame.framework.Box;
import keymastergame.framework.Vector;

public class Player extends GameObject {

	/* variables inherited from GameObject, for reference
	 
	public Box collision;
	public Vector velocity;
	protected double gravAcc;
		
	public boolean collisionUp;
	public boolean collisionRight;
	public boolean collisionDown;
	public boolean collisionLeft;
	
	*/
	
	boolean hasKey = false;
	
	protected double gravAcc = 0.5;
	
	private boolean onLadder = false;
	
	private static double horizontalSpeed = 2.5;
	private static double ladderSpeed = 2.5; 
	
	//tracks state of controls, true = pressed, false = not pressed
	private boolean keyLeft = false;
	private boolean keyRight = false;
	private boolean keyUp = false;
	private boolean keyDown = false;
	private boolean keyAbility = false;
	
	private boolean doAbility = false;
	
	//const of controls
	public final static int controlLeft = KeyEvent.VK_LEFT;
	public final static int controlRight = KeyEvent.VK_RIGHT;
	public final static int controlUp = KeyEvent.VK_UP;
	public final static int controlDown = KeyEvent.VK_DOWN;
	public final static int controlAbility = KeyEvent.VK_SPACE;
	
	//tile disapear lasts 120 frames
	private int abilityDuration = 120;
	
	//countdown variable until player can use ability again
	private int abilityCooldown = 0;
	
	
	public Player() {
	}

	public Player(Vector position) {
		super(position);
	}

	public void update() {
		velocity.x = 0;
		
		
		//decrement cooldown
		if (abilityCooldown > 0) {
			abilityCooldown--;
			if (abilityCooldown == 0) {
				System.out.println("Ability available!");
			}
		}
		
		if (!onLadder) {
			//not attached to a ladder
			
			
			if (collisionUp) {
				//on the ground
				
				if (doAbility && abilityCooldown == 0) {
					//do ability
					performAbility();
				}

				if ((keyUp || keyDown) && !(keyLeft || keyRight)) {
					//try to attach to ladder
					
					Ladder ladder = StartingClass.gameState.lvl.getLadderFromPoint(collision.position);
					if (ladder != null) {
						collision.position.x = ladder.collision.position.x;
						onLadder = true;
					}
				}
			}
			
			
			//movement
			if (!onLadder) {
				if (keyLeft && !keyRight) {
					velocity.x = -horizontalSpeed;
				} else if (keyRight && !keyLeft) {
					velocity.x = horizontalSpeed;
				}
				
				//apply gravity when off ladder
				velocity.y += gravAcc;
			}
			
		} else {
			
			
			if (canMountLadder(collision.position)) {
				//on ladder
				velocity.y = 0;
				//if (faceLeft) faceLeft = false;
	
				if (keyUp && !keyDown) {
					if (canMountLadder(new Vector(collision.position.x, collision.position.y - ladderSpeed))) {
						velocity.y = -ladderSpeed;
					} else {
						//reached top of ladder
						Ladder ladder = StartingClass.gameState.lvl.getLadderFromPoint(collision.position);
						collision.position.y = ladder.collision.position.y - ladder.collision.size.y/2;
						
						if (canDismountLadder()) onLadder = false;
						
					}
					
				} else if (keyDown && !keyUp) {
					
					if (canMountLadder(new Vector(collision.position.x, collision.position.y + ladderSpeed))) {
						velocity.y = ladderSpeed;
					} else {
						//reached bottom of ladder
						Ladder ladder = StartingClass.gameState.lvl.getLadderFromPoint(collision.position);
						collision.position.y = ladder.collision.position.y + ladder.collision.size.y/2;
						
						if (canDismountLadder()) onLadder = false;
					}
				}
	
				//left or right dismounts ladder
				if (!(keyUp || keyDown) && (keyLeft || keyRight) && canDismountLadder()) {
					onLadder = false;
				}
			}else {
				onLadder = false;
				velocity.y = 0;
			}
		}
		
		
		if (velocity.x < 0) {
			faceLeft = true;
		} else if (velocity.x > 0) {
			faceLeft = false;
		}
		
		collision.position.add(velocity);
	}
	
	public void paint(Graphics g) {
		collision.paint(g, Color.GREEN);
	}
	
	public void input(int code, boolean pressed) {
		//do something here
		switch (code) {
		
		case controlLeft: keyLeft = pressed;
			break;
		case controlRight: keyRight = pressed;
			break;
		case controlUp: keyUp = pressed;
			break;
		case controlDown: keyDown = pressed;
			break;
			//only do ability when pressed, not held
		case controlAbility: doAbility = (keyAbility != pressed) && pressed; keyAbility = pressed;
			break;
		}
		
	}
	
	//performs ability
	public void performAbility() {
		System.out.println("Ability activated!");

		Vector checkPoint = new Vector(collision.position);
		
		Vector removePoint = new Vector(collision.position);
		if (!faceLeft) {
			removePoint.x += StartingClass.TILESIZE;
			checkPoint.x += StartingClass.TILESIZE;
		} else {
			removePoint.x -= StartingClass.TILESIZE;
			checkPoint.x -= StartingClass.TILESIZE;
		}
		removePoint.y += StartingClass.TILESIZE;
		

		Tile t1 = StartingClass.gameState.lvl.getTileFromPoint(checkPoint);
		Tile t2 = StartingClass.gameState.lvl.getTileFromPoint(removePoint);
		
		if (t1 == null && t2 != null) {
			t2.setDisabled(abilityDuration);
			
			abilityCooldown = abilityDuration;
		
		} else {
			System.out.println("Ability failed!");
		}
	}
	
	public boolean canMountLadder(Vector pos) {
		//check if the player is intersecting a ladder's collision box
		Ladder ladder = StartingClass.gameState.lvl.getLadderFromPoint(pos);
		
		return ladder != null;
	}
	public boolean canDismountLadder() {
		
		//check if player is currently colliding with any tiles
		for (Tile t : StartingClass.gameState.lvl.tiles) {
			if (!t.isDisabled() && t.collision.intersects(collision)) {
				return false;
			}
		}
		return true;
	}

	public boolean collisionActive() {
		//don't collide with tiles if on a ladder
		return !onLadder;
	}
}
