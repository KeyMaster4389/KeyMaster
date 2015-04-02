package keymastergame.framework;

import java.util.Comparator;

import keymastergame.GameObject;
import keymastergame.Tile;


public class LevelCollision {
		
	public static class LevelCollisionComparator implements Comparator<LevelCollision> {
	    @Override
	    public int compare(LevelCollision o1, LevelCollision o2) {
			double diff = Math.abs(o1.magnitude) - Math.abs(o2.magnitude);
			if (diff > 0) return 1;
			else if (diff < 0) return -1;
			else return 0;
	    }
	}
	
	
	
	//what's colliding
	public GameObject object;
	public Tile tile;
	
	//0 = no intersection, 1 = up, 2 = right, 3 = down, 4 = left
	public int moveOutAxis;
	
	//distance to move
	public double magnitude;
	
	public LevelCollision(GameObject obj, Tile t) {
		object = obj;
		tile = t;
		
		moveOutAxis = 0;
		magnitude = 999999999;	//absurdly high number
		
		if (object.collision.intersects(tile.collision)) {
			//find shortest path out of object
			
			//try up
			if (tile.openTop) {
				//where object is in relation to tile
				double dist = tile.collision.position.y - obj.collision.position.y;
				//where we want object to move to in relation to tile
				double ideal = tile.collision.size.y/2 + object.collision.size.y/2;
				//how far to move object to ideal distance
				double diff = dist - ideal;
						
				if (Math.abs(diff) < Math.abs(magnitude)) {
					moveOutAxis = 1;
					magnitude = diff;
				}
			}
			
			//try right
			if (tile.openLeft) {
				double dist = tile.collision.position.x - obj.collision.position.x;
				double ideal = tile.collision.size.x/2 + object.collision.size.x/2;
				double diff = dist - ideal;
				

				if (Math.abs(diff) < Math.abs(magnitude)) {
					moveOutAxis = 2;
					magnitude = diff;
				}
			}
			
			//try down

			if (tile.openBottom) {
				double dist = obj.collision.position.y - tile.collision.position.y;
				double ideal = tile.collision.size.y/2 + object.collision.size.y/2;
				double diff = dist - ideal;
						
				if (Math.abs(diff) < Math.abs(magnitude)) {
					moveOutAxis = 3;
					magnitude = -diff;
				}
			}
			
			
			//try left
			if (tile.openRight) {

				double dist = obj.collision.position.x - tile.collision.position.x;
				double ideal = tile.collision.size.x/2 + object.collision.size.x/2;
				double diff = dist - ideal;
				
				if (Math.abs(diff) < Math.abs(magnitude)) {
					moveOutAxis = 4;
					magnitude = -diff;
				}
			}
		}
	}
	
	public void resolveCollision() {
		if (object.collision.intersects(tile.collision)) {
			switch (moveOutAxis) {
			case 0: break;
			
			case 1: object.collision.position.y += magnitude; object.collisionUp = true; if (object.velocity.y > 0) object.velocity.y = 0; break;
			case 2: object.collision.position.x += magnitude; object.collisionRight = true; if (object.velocity.x > 0)object.velocity.x = 0; break;
			case 3: object.collision.position.y += magnitude; object.collisionDown = true; if (object.velocity.y < 0)object.velocity.y = 0; break;
			case 4: object.collision.position.x += magnitude; object.collisionLeft = true;if (object.velocity.y < 0) object.velocity.x = 0; break;
			
			}
		}
	}

}
