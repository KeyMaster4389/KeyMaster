package keymastergame;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import keymastergame.framework.LevelCollision;
import keymastergame.framework.Vector;

public class Game {

	Level lvl;
	ArrayList<GameObject> objects;
	Player plr;
	//Door door;
	//Key key;
	
	//private int playerLives = 3;
	
	public Game() {
		objects = new ArrayList<GameObject>();
		lvl = new Level();
		plr = new Player(new Vector(0, 0));
	}
	
	public void update() {
		lvl.update();
		
		plr.update();
		if (plr.collisionActive()) doCollision(plr);
		
		for (GameObject e : objects) {
			e.update();
			if (e.collisionActive())
				doCollision(e);
		}
		
	}
	
	public void paint(Graphics g) {
		lvl.paint(g);

		for (GameObject e : objects) {
			e.paint(g);
		}
		
		
		//paint player last?
		plr.paint(g);
		
		
	}
	
	
	public void readInput(int code, boolean pressed) {
		//do player controls here i guess
		plr.input(code, pressed);
	}
	
	
	public void doCollision(GameObject obj) {
		
		boolean collision = false;
		
		obj.collisionUp = false;
		obj.collisionRight = false;
		obj.collisionDown = false;
		obj.collisionLeft = false;
		
		ArrayList<LevelCollision> collisionArr = new ArrayList<LevelCollision>();
		
		//get all tile collisions with this object in this frame
		for (Tile t : lvl.tiles) {
			
			if (!t.isDisabled() && obj.collision.intersects(t.collision)) {
				LevelCollision col = new LevelCollision(obj,t); 
				collisionArr.add(col);
				
				if (!collision) collision = true;
			}
		}
		
			
		if(collision) {
			//sort from lowest magnitude to highest
			Collections.sort(collisionArr, new LevelCollision.LevelCollisionComparator());
			
			//resolve each in order
			//System.out.println("COLLISIONS");
			for (LevelCollision e : collisionArr) {
				//System.out.println(e.moveOutAxis + " " + e.magnitude);
				e.resolveCollision();
			}
		}
	}
	
	
	public boolean loadLevel(String path) {
		lvl.clear();
		objects.clear();
		
		try {
			System.out.println("Reading level file at: " + path);
			Scanner scan = new Scanner(new FileReader(path));
			
			Vector pos = new Vector();
			
			while(scan.hasNext()) {
				String s = scan.nextLine();

				for (int i = 0; i < s.length(); i++, pos.x++) {
					addToGame(s.charAt(i), pos);
					
				}
				pos.y++;
				pos.x = 0;
			}
			
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
				
		lvl.generateLevelCollision();
		
		return true;
	}
	
	private void addToGame(char c, Vector pos) {
		
		//convert grid position to actual position in pixels
		Vector realPos = new Vector(pos.x * StartingClass.TILESIZE + StartingClass.TILESIZE/2, pos.y * StartingClass.TILESIZE + StartingClass.TILESIZE/2);
		
		switch (c) {
		case '0': 
			break; //add empty space tile(?)
		
		case '1': lvl.tiles.add(new Tile(pos)); 
			break; //add solid space tile
		
		case '2': lvl.ladders.add(new Ladder(realPos));
			break; //add ladder
		
		case '3': lvl.tiles.add(new Tile(pos)); lvl.ladders.add(new Ladder(realPos));
			break; //add ladder top (should place both a ladder and a tile)
		
		case 'P': plr.collision.position = realPos;
			break; //set player starting position
		
		case 'D': 
			break; //set door position
		
		case 'K': 
			break; //set key position
		
		case 'A': 
			break; //add enemy type A
		
		case 'B': 
			break; //add enemy type B
		
		case 'C': 
			break; //add enemy type C
		case 'O': objects.add(new GameObject(realPos));
			break; // add basic object
		}
	}
}
