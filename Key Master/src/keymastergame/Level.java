package keymastergame;

import java.awt.Graphics;
import java.util.ArrayList;

import keymastergame.framework.Vector;

public class Level {
	public ArrayList<Tile> tiles;
	public ArrayList<Ladder> ladders;
	
	public Level() {
		// TODO Auto-generated constructor stub
		tiles = new ArrayList<Tile>();
		ladders = new ArrayList<Ladder>();
	}

	public void update() {
		boolean redoCollision = false;
		
		//im just kinda winging this, it probably doesn't work
		for (Tile t : tiles) {
			t.update();
			if (t.changedState) {
				//update collision of adjecent tiles
				
				redoCollision = true;
				t.changedState = false;
				
			}
		}
		
		if (redoCollision) {
			generateLevelCollision();
		}
	}
	
	public void paint(Graphics g) {
		for (Tile t : tiles) {
			t.paint(g);
		}
		for (Ladder l : ladders) {
			l.paint(g);
		}
	}
	
	public void generateLevelCollision() {
		long startTime = System.nanoTime();

		//start comparing tiles
		//if two tiles are adjecent, change their open flags to false;
		//call this after loading a level or when tile status has been changed
		
		for (Tile t : tiles) {
			if (!t.isDisabled()) {
				checkCollision(t);
			}
		}
		
		long endTime = System.nanoTime();

		System.out.println("Level collision compile: " + (endTime - startTime)/1000000  + " milliseconds");
	}
	
	public void checkCollision(Tile check) {
		//set appropriate collision flags for tiles
		
		//System.out.println("check tile: "+ check.gridX + ", "+check.gridY);
		check.openTop = true;
		check.openRight = true;
		check.openBottom = true;
		check.openLeft = true;
	
		

		int count = 0;
		for (Tile checkee : tiles) {
			if (count > 4) break;
			
			if (check != checkee && !checkee.isDisabled()) {
				if (checkee.gridX == check.gridX) {
					if (checkee.gridY == check.gridY - 1) {
						check.openTop = false;
						count++;
						continue;
					} else if (checkee.gridY == check.gridY + 1) {
						check.openBottom = false;
						count++;
						continue;
					}
					
				} else if (checkee.gridY == check.gridY) {

					if (checkee.gridX == check.gridX - 1) {
						check.openLeft = false;
						count++;
						continue;
					} else if (checkee.gridX == check.gridX + 1) {
						check.openRight = false;
						count++;
						continue;
					}
				}
			}
			
		}
	}
	
	
	public void clear() {
		tiles.clear();
		ladders.clear();
		
	}
	
	public Ladder getLadderFromPoint(Vector point) {
		
		for (Ladder l : ladders) {
			if (l.collision.containsWithEdge(point)) {
				return l;
			}
		}
		return null;
		
	}
	
	public Tile getTileFromPoint(Vector point) {

		for (Tile t : tiles) {
			if (!t.isDisabled() && t.collision.contains(point)) {
				return t;
			}
		}
		return null;
		
	}
	
}
