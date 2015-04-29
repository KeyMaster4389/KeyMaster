package keymastergame.objects.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import keymastergame.StartingClass;
import keymastergame.Tile;
import keymastergame.framework.Animation;
import keymastergame.framework.Box;
import keymastergame.framework.Resource;
import keymastergame.framework.Sound;
import keymastergame.framework.Vector;
import keymastergame.objects.Player;

public class EnemyB extends EnemyObject {
	// behavior: move under player and jump to destroy blocks underneath palyer

	private double jumpHeight = 100; // how vertically close under player to
										// jump
	private double jumpDist = 2; // how horizontally close to player to jump
	private double jumpVelY = 7;

	private double moveSpeed = 4;

	private int jumpDisable = 30;
	private int breakDisable = 70;
	private int tileDisable = 60;
	private int jumpTimer = 0;

	// animation - left 0, right 1
	private int faceState = 1;

	private Vector imageOffset;// initialize in configureAnimations method

	private Animation left;
	private Animation right;
	private Image currentImage;

	public EnemyB() {
		collision = new Box(new Vector(0, 0), size);
		velocity = new Vector(0, 0);

		configureAnimations();

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}

	public EnemyB(Vector pos) {
		collision = new Box(pos, size);
		velocity = new Vector(0, 0);

		configureAnimations();

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}

	public void updateAnimation() {

		// facePlayer();

		if (velocity.x == 0) {

			if (faceLeft) {

				if (currentImage != Resource.enemyBLeft3) {

					currentImage = Resource.enemyBLeft3;
				}

			} else if (!faceLeft) {

				if (currentImage != Resource.enemyBRight3) {

					currentImage = Resource.enemyBRight3;

				}

			}

		} else if (velocity.x > 0) {

			 faceLeft = false;
			//facePlayer();

			if (faceState != 1) {

				faceState = 1;
				right.resetAnimation();

			}

			currentImage = right.getImage();
			right.update(StartingClass.frameSpeed);

		} else if (velocity.x < 0) {

			faceLeft = true;
			//facePlayer();

			if (faceState != 0) {

				faceState = 0;
				left.resetAnimation();

			}

			currentImage = left.getImage();
			left.update(StartingClass.frameSpeed);

		}
	}

	public void paint(Graphics g) {

		if (StartingClass.debugGraphics) {
			collision.paint(g, Color.RED);

			g.setColor(Color.BLACK);
			g.drawString("B", (int) collision.position.x - 4,
					(int) collision.position.y + 4);
		}else{
			
			double xPos = collision.position.x;
			double yPos = collision.position.y;

			g.drawImage(currentImage, (int) (xPos - imageOffset.x),
					(int) (yPos - imageOffset.y), null);
			
		}
	}

	protected void act() {
		velocity.x = 0;

		if (jumpTimer > 0)
			jumpTimer--;

		if (collisionUp) {
			// check if we're under player
			Player plr = StartingClass.gameState.plr;
			if (plr.collision.position.y <= collision.position.y + 10
					&& plr.collision.position.y + jumpHeight >= collision.position.y) {

				// approximate plr's horizontal position to nearest tile
				double plrPosX = Math.floor(plr.collision.position.x
						/ StartingClass.TILESIZE)
						* StartingClass.TILESIZE + StartingClass.TILESIZE / 2;

				// check if we are directly under player
				if (collision.position.x <= plrPosX + jumpDist
						&& collision.position.x >= plrPosX - jumpDist
						&& jumpTimer == 0) {
					// jump
					velocity.y = -jumpVelY;
					jumpTimer = jumpDisable;

				} else {
					// move towards player
					if (collision.position.x < plrPosX) {
						velocity.x = moveSpeed;
						faceLeft = false;
					} else if (collision.position.x > plrPosX) {
						velocity.x = -moveSpeed;
						faceLeft = true;

					}
				}

			}

		} else {
			if (collisionDown) {
				// destroy block(s) above us
				Tile t = StartingClass.gameState.lvl
						.getTileFromPoint(new Vector(collision.position.x,
								collision.position.y - StartingClass.TILESIZE));
				if (t != null) {
					t.setDisabled(tileDisable);
					// be stunned for 90 frames
					waitTimer = breakDisable;
					Sound.TILE_REMOVE.play();
				}
			}
		}

	}
	
private void configureAnimations() {
		
		imageOffset = new Vector(16, 21);

		left = new Animation();
		right = new Animation();
		
		currentImage = Resource.enemyBLeft3;

		int runtime = 120;
		
		left.addFrame(Resource.enemyBLeft1, runtime);
		left.addFrame(Resource.enemyBLeft2, runtime);
		left.addFrame(Resource.enemyBLeft3, runtime);
		left.addFrame(Resource.enemyBLeft4, runtime);
		
		right.addFrame(Resource.enemyBRight1, runtime);
		right.addFrame(Resource.enemyBRight2, runtime);
		right.addFrame(Resource.enemyBRight3, runtime);
		right.addFrame(Resource.enemyBRight4, runtime);
		
		
	}
	
}
