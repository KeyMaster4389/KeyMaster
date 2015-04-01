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
	
	protected double gravAcc = 0.5;
	
	private boolean onLadder = false;
	
	private static double horizontalSpeed = 2;
	private static double ladderSpeed = 2; 
	
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
		
		//do object logic here
		if (!onLadder) {
			//not attached to a ladder
			
			if (collisionUp) {
				//on the ground
				
				if (doAbility && abilityCooldown == 0) {
					//do ability
					performAbility();
				}
				
				
				
				
			}
			
			
			//movement
			if (keyLeft && !keyRight) {
				velocity.x = -horizontalSpeed;
			} else if (keyRight && !keyLeft) {
				velocity.x = horizontalSpeed;
			}
			
			//apply gravity when off ladder
			velocity.y += gravAcc;
			
		} else {
			velocity.y = 0;
			//on ladder
			if (faceLeft) faceLeft = false;

			if (keyUp && !keyDown) {
				velocity.y = -ladderSpeed;
			} else if (keyDown && !keyUp) {
				velocity.y = ladderSpeed;
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

		
		
		
		abilityCooldown = abilityDuration;
	}
	
	//check if the player is intersecting a ladder's collision box
	public boolean canLadder() {
		
		return false;
	}
	
}
