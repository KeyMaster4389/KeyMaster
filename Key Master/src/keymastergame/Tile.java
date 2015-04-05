package keymastergame;

import java.awt.Color;
import java.awt.Graphics;

import keymastergame.framework.Box;
import keymastergame.framework.Resource;
import keymastergame.framework.Vector;
import keymastergame.objects.GameObject;
import keymastergame.objects.Player;

public class Tile {

	
	public Box collision;
	public int gridX;
	public int gridY;
	private int disableTimer;
	
	public boolean openTop;
	public boolean openRight;
	public boolean openBottom;
	public boolean openLeft;
	
	//when to start flickering
	private int flickerTime = 30;
	
	//flag telling level to recompile collision
	//flag will get set back to false by level
	public boolean changedState;
	
	public Tile(Vector pos) {
		disableTimer = 0;
		
		gridX = (int)pos.x;
		gridY = (int)pos.y;
		
		
		collision = new Box(
				new Vector(pos.x * StartingClass.TILESIZE + StartingClass.TILESIZE/2,pos.y * StartingClass.TILESIZE + StartingClass.TILESIZE/2), 
				new Vector(StartingClass.TILESIZE, StartingClass.TILESIZE));
		
		openTop = true;
		openRight = true;
		openBottom = true;
		openLeft = true;
		
		changedState = false;
	}

	public void update() {
		if (isDisabled()) {
			disableTimer--;
			if (!isDisabled()) {
				changedState = true;
				
				//do thing here for intersecting gameobjects
				Player p = StartingClass.gameState.plr;
				if (p.collision.intersects(collision)) {
					p.die();
					//throw player out of screen, to make them "disappear"
					p.collision.position.y = 1000;
				}
				for (GameObject obj : StartingClass.gameState.objects) {
					
					if(obj.collision.intersects(collision)) {
						
						obj.toRemove = true;
						
					}
					
				}
				
				
			}
		}
	}
	
	public void paint(Graphics g) {
		if (!isDisabled()) {
			
			drawTile(g);
			
			g.setColor(Color.BLACK);
			if (openTop) {
				int x1 = (int)(collision.position.x - collision.size.x/2);
				int y1 = (int)(collision.position.y - collision.size.y/2);
				int x2 = (int)(collision.position.x + collision.size.x/2 - 1);
				int y2 = (int)(collision.position.y - collision.size.y/2);
				
				g.drawLine(x1, y1, x2, y2);
			}
			if (openRight) {

				int x1 = (int)(collision.position.x + collision.size.x/2 - 1);
				int y1 = (int)(collision.position.y - collision.size.y/2);
				int x2 = (int)(collision.position.x + collision.size.x/2 - 1);
				int y2 = (int)(collision.position.y + collision.size.y/2 - 1);
				
				g.drawLine(x1, y1, x2, y2);
			}
			if (openBottom) {

				int x1 = (int)(collision.position.x - collision.size.x/2);
				int y1 = (int)(collision.position.y + collision.size.y/2 - 1);
				int x2 = (int)(collision.position.x + collision.size.x/2 - 1);
				int y2 = (int)(collision.position.y + collision.size.y/2 - 1);
				
				g.drawLine(x1, y1, x2, y2);
			}
			if (openLeft) {

				int x1 = (int)(collision.position.x - collision.size.x/2);
				int y1 = (int)(collision.position.y - collision.size.y/2);
				int x2 = (int)(collision.position.x - collision.size.x/2);
				int y2 = (int)(collision.position.y + collision.size.y/2 - 1);
				
				g.drawLine(x1, y1, x2, y2);
			}
			
		} else if (disableTimer < flickerTime && disableTimer % 3 == 0) {
			drawTile(g);
		}
	}
	
	private void drawTile(Graphics g) {
		if (StartingClass.debugGraphics) {
		
			if ((gridX + gridY) % 2 == 0)
				collision.paint(g, Color.GREEN);
			else
				collision.paint(g, new Color(0,200,0));
			
		} else {
			int xPos = (int)(collision.position.x - StartingClass.TILESIZE/2);
			int yPos = (int)(collision.position.y - StartingClass.TILESIZE/2);
			
			g.drawImage(Resource.tileSpr, xPos, yPos, null);
			
		}
	}
	
	public void setDisabled(int frames) {
		if (!isDisabled()) {
			disableTimer = frames;
			changedState = true;
		}
	}
	
	public boolean isDisabled() {
		return disableTimer > 0;
	}
	
}
