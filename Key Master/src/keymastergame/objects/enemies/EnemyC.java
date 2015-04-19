package keymastergame.objects.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import keymastergame.StartingClass;
import keymastergame.framework.Animation;
import keymastergame.framework.Box;
import keymastergame.framework.Resource;
import keymastergame.framework.Vector;
import keymastergame.objects.Player;

public class EnemyC extends EnemyObject {
	//behavior: shooter

	private int shootSpeed = 90;
	private int shootTimer = 0;
	private double projectileSpeed = 3;

	// animation - left 0, right 1
	private int faceState = 1;

	private Vector imageOffset;// initialize in configureAnimations method

	private Animation left;
	private Animation right;
	private Image currentImage;

	public EnemyC() {
		collision = new Box(new Vector(0, 0), size);
		velocity = new Vector(0, 0);

		configureAnimations();

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;

	}

	public EnemyC(Vector pos) {
		collision = new Box(pos, size);
		velocity = new Vector(0, 0);

		configureAnimations();

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;

		//facePlayer();
	}

	public void updateAnimation() {
		
		facePlayer();

		if (faceLeft) {

			if (faceState != 0) {

				faceState = 0;
				left.resetAnimation();
			}

			currentImage = left.getImage();
			left.update(StartingClass.frameSpeed);

		} else if (!faceLeft) {

			if (faceState != 1) {

				faceState = 1;
				right.resetAnimation();
			}

			currentImage = right.getImage();
			right.update(StartingClass.frameSpeed);

		}

	}

	public void paint(Graphics g) {

		if (StartingClass.debugGraphics) {
			collision.paint(g, Color.RED);
			g.setColor(Color.BLACK);
			g.drawString("C", (int) collision.position.x - 4,
					(int) collision.position.y + 4);

		} else {

			double xPos = collision.position.x;
			double yPos = collision.position.y;

			g.drawImage(currentImage, (int) (xPos - imageOffset.x),
					(int) (yPos - imageOffset.y), null);

		}
	}

	protected void act() {
		velocity.x = 0;

		if (shootTimer > 0)
			shootTimer--;

		if (collisionUp) {

			Player plr = StartingClass.gameState.plr;
			if (plr.collision.position.x > collision.position.x) {
				faceLeft = false;
				//facePlayer();
			} else if (plr.collision.position.x < collision.position.x) {
				faceLeft = true;
				//facePlayer();
			}

			if (shootTimer == 0) {
				// shoot

				Projectile p = new Projectile(new Vector(collision.position));

				if (!faceLeft) {
					p.collision.position.x += 8;
					p.velocity.x = projectileSpeed;
				} else {
					p.collision.position.x -= 8;
					p.velocity.x = -projectileSpeed;
				}

				p.faceLeft = faceLeft;

				StartingClass.gameState.addObj.add(p);

				shootTimer = shootSpeed;

			}

		}

	}

	private void configureAnimations() {

		imageOffset = new Vector(16, 21);

		left = new Animation();
		right = new Animation();

		currentImage = Resource.enemyCLeft1;

		int runtime = 120;

		left.addFrame(Resource.enemyCLeft1, 200);
		left.addFrame(Resource.enemyCLeft2, runtime);
		left.addFrame(Resource.enemyCLeft3, runtime);
		left.addFrame(Resource.enemyCLeft4, runtime);

		right.addFrame(Resource.enemyCRight1, 200);
		right.addFrame(Resource.enemyCRight2, runtime);
		right.addFrame(Resource.enemyCRight3, runtime);
		right.addFrame(Resource.enemyCRight4, runtime);

	}

}
