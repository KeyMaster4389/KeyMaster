package keymastergame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import keymastergame.framework.Vector;
import keymastergame.objects.Ladder;

public class Level {
	public ArrayList<Tile> tiles;
	public ArrayList<Ladder> ladders;
	private Color bgColor;
	
	public Level() {
		bgColor = new Color(10, 55, 89);
		tiles = new ArrayList<Tile>();
		ladders = new ArrayList<Ladder>();
	}

	public void update() {
		boolean redoCollision = false;
		
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
		
		g.setColor(bgColor);
		g.fillRect(0, 0, (int)StartingClass.WINDOWWIDTH, (int)StartingClass.WINDOWHEIGHT);
		
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
			if (!t.isDisabled() && t.collision.containsWithEdge(point)) {
				return t;
			}
		}
		return null;
		
	}
	
}
