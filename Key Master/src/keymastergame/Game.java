package keymastergame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import keymastergame.framework.*;
import keymastergame.objects.*;
import keymastergame.objects.enemies.*;

public class Game {

	public Clock gameClock;
	public Level lvl;
	public static ArrayList<GameObject> objects;
	public ArrayList<GameObject> removeObj;
	public ArrayList<GameObject> addObj;
	public Player plr;
	public Door door;
	public Key key;

	public boolean levelComplete;
	public int currentLevel; // 1, 2, 3

	private int playerLives;

	// wait this many frames before loading/reloading a level
	public static final int reloadLevelDelayLose = 120;
	public static final int reloadLevelDelayWin = 60;
	private int reloadLevelTimer;

	// Icon for message dialogs
	Image keyImage = null;
	ImageIcon keyIcon = null;

	public Game() {
		objects = new ArrayList<GameObject>();
		removeObj = new ArrayList<GameObject>(); 
		addObj = new ArrayList<GameObject>();
		
		gameClock = new Clock();
		
		lvl = new Level();
		plr = new Player();
		key = new Key();
		door = new Door();

		levelComplete = false;
		currentLevel = 1;

		playerLives = 3;

		loadLevel();

		
		try {
			keyImage = ImageIO.read(getClass().getResource("/data/key.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		keyIcon = new ImageIcon(keyImage);
	}

	public void update() {
		
		
		if (!plr.hasWon && !plr.isDead) {
			lvl.update();
			gameClock.update();
			plr.update();
			if (plr.collisionActive())
				doCollision(plr);
						
			removeObj.clear();
			
			for (GameObject e : objects) {
				e.update();
				if (e.collisionActive())
					doCollision(e);
				
				if (e instanceof EnemyObject && plr.collision.intersects(e.collision)) {
					plr.die();
					if (e instanceof Projectile) {
						e.toRemove = true;
					}
				}
				
				if (!e.collision.intersects(StartingClass.levelBoundary)) {
					System.out.println("object destroyed!");
					e.toRemove = true;
				}
				
				if (e.toRemove) {
					removeObj.add(e);
				}
			}
			if (!removeObj.isEmpty()) {
				for (int i = removeObj.size() - 1; i >= 0; i--) {
					for (GameObject obj : objects) {
						if (obj.equals(removeObj.get(i))) {
							objects.remove(obj);
							break;
						}
					}
				}
			}

			objects.addAll(addObj);
			addObj.clear();
			

			door.update();
			key.update();

			if (plr.collision.intersects(key.collision)) {
				key.setFollow(plr);
				
			}

			plr.updateAnimation();

			if (gameClock.getTime()) {
				plr.die();
			}
			if (plr.isDead) {
				reloadLevelTimer = reloadLevelDelayLose;
				plr.die();
			} else if (door.isOpen && door.collision.contains(plr.collision)
					&& plr.collisionUp) {
				reloadLevelTimer = reloadLevelDelayWin;
				plr.collision.position.x = door.collision.position.x;
				plr.win();
			}

		} else {

			reloadLevelTimer--;

			if (plr.hasWon) {
				plr.winUpdate();

			} else if (plr.isDead) {
				plr.dieUpdate();
				doCollision(plr);

			}

			if (reloadLevelTimer <= 0) {
				if (plr.hasWon) {

					levelComplete = true;
					currentLevel++;
					if (currentLevel == 4) {

						// you win!
						// just do this for now
						System.out.println("YOU'RE WINNER");
						
						//win message and ask to play again
						int result = JOptionPane.showConfirmDialog(null,
										"      \t      \t     Congratulations!!\nYou have defeated the evil Key Master!!\n     \t    \tWould you like to play again?",
										"Congratulations! You win!",
										JOptionPane.YES_NO_OPTION, 2, keyIcon);
						

						if (result == JOptionPane.YES_OPTION)
							currentLevel = 1;
						else
							StartingClass.changeState(StartingClass.STATE_MAINMENU);
					}
					loadLevel();

				} else if (plr.isDead) {
					System.out.println("YOU DIED");

					playerLives--;
					
					//message to player about lives remaining
					JOptionPane.showMessageDialog(null,
							"You have died.\nREMAINING LIVES: " + playerLives,
							"REMAINING LIVES: " + playerLives, 2, keyIcon);

					System.out.println("REMAINING LIVES: " + playerLives);

					if (playerLives == 0) {
						// game over
						// just do this for now
						System.out.println("GAME OVER :(");
						Sound.GAMEOVER.play();

						int result = JOptionPane.showConfirmDialog(null,
										"         \t         Oh no!!\nYou have been defeated by\n     \t  the evil Key Master!!\nWould you like to play again?",
										"Game Over", JOptionPane.YES_NO_OPTION,
										2, keyIcon);

						if (result == JOptionPane.YES_OPTION) {
							currentLevel = 1;
							playerLives = 3;
						} else {
							StartingClass.changeState(StartingClass.STATE_MAINMENU);
							return;
						}
					}
					loadLevel();

				}
			}
			return;
		}
	}

	public void paint(Graphics g) {
		
		lvl.paint(g);

		door.paint(g);

		for (GameObject e : objects) {
			e.paint(g);
		}

		// paint player last?
		plr.paint(g);

		key.paint(g);
		
		//paint HUD
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, StartingClass.WINDOWWIDTH , 32);
		//paint clock images
		gameClock.paint(g);
		
		//paint level number
		g.drawImage(Resource.level, StartingClass.WINDOWWIDTH/2 - 60, 0, null);
		g.drawImage(Resource.number[currentLevel], StartingClass.WINDOWWIDTH/2 + 30, 3, null);
		
		
		//paint life count
		if (playerLives < 0) playerLives = 0;
		else if (playerLives > 9) playerLives = 9;

		g.drawImage(Resource.lives, 16, 0, null);
		g.drawImage(Resource.number[playerLives], 96, 3, null);
	}

	public void readInput(int code, boolean pressed) {
		// do player controls here i guess
		plr.input(code, pressed);
	}

	public void doCollision(GameObject obj) {

		boolean collision = false;

		obj.collisionUp = false;
		obj.collisionRight = false;
		obj.collisionDown = false;
		obj.collisionLeft = false;

		ArrayList<LevelCollision> collisionArr = new ArrayList<LevelCollision>();

		// get all tile collisions with this object in this frame
		for (Tile t : lvl.tiles) {

			if (!t.isDisabled() && obj.collision.intersects(t.collision)) {
				LevelCollision col = new LevelCollision(obj, t);
				collisionArr.add(col);

				if (!collision)
					collision = true;
			}
		}

		if (collision) {
			// sort from lowest magnitude to highest
			Collections.sort(collisionArr,
					new LevelCollision.LevelCollisionComparator());

			// resolve each in order
			// System.out.println("COLLISIONS");
			for (LevelCollision e : collisionArr) {
				// System.out.println(e.moveOutAxis + " " + e.magnitude);
				e.resolveCollision();
			}
		}
	}

	public boolean loadLevel() {
		String path = "data/level1.txt";
		if (currentLevel == 2) {
			path = "data/level2.txt";
		} else if (currentLevel == 3) {
			path = "data/level3.txt";
		}

		lvl.clear();
		objects.clear();
		lvl = new Level();
		plr = new Player();
		key = new Key();
		door = new Door();
		gameClock = new Clock();
		levelComplete = false;

		try {
			System.out.println("Reading level file at: " + path);
			Scanner scan = new Scanner(new FileReader(path));

			Vector pos = new Vector();

			while (scan.hasNext()) {
				String s = scan.nextLine();

				for (int i = 0; i < s.length(); i++, pos.x++) {
					addToGame(s.charAt(i), pos);

				}
				pos.y++;
				pos.x = 0;
			}

			scan.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			return false;
		}

		lvl.generateLevelCollision();
		//restart music from beginning
		Sound.MUSIC.setFramePosition(0);
		Sound.MUSIC.loop(Clip.LOOP_CONTINUOUSLY);

		return true;
	}

	
	private void addToGame(char c, Vector pos) {

		// convert grid position to actual position in pixels
		Vector realPos = new Vector(pos.x * StartingClass.TILESIZE
				+ StartingClass.TILESIZE / 2, pos.y * StartingClass.TILESIZE
				+ StartingClass.TILESIZE / 2);

		switch (c) {
		case '0':
			break; // add empty space tile(?)

		case '1':
			lvl.tiles.add(new Tile(pos));
			break; // add solid space tile

		case '2':
			lvl.ladders.add(new Ladder(realPos));
			break; // add ladder

		case '3':
			lvl.tiles.add(new Tile(pos));
			lvl.ladders.add(new Ladder(realPos));
			break; // add ladder top (should place both a ladder and a tile)

		case 'P':
			plr = new Player(realPos);
			break; // set player starting position

		case 'D':
			door = new Door(realPos);
			break; // set door position

		case 'K':
			key = new Key(realPos);
			break; // set key position

		case 'A': 
			objects.add(new EnemyA(realPos));
			break; // add enemy type A

		case 'B':
			objects.add(new EnemyB(realPos));
			break; // add enemy type B

		case 'C':
			objects.add(new EnemyC(realPos));
			break; // add enemy type C
			
		case 'G':
			objects.add(new GameObject(realPos));
			break; // add basic object
		case 'E':
			//objects.add(new EnemyObject(realPos));
			break; // add basic object
		}
	}
}
