package keymastergame;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import keymastergame.framework.Resource;
import keymastergame.framework.Sound;

public class Screen {

	public static final int YOU_LOSE = 0;
	public static final int YOU_WIN = 1;
	
	
	private Image display;	
	private int nextState;
	
	private final int minDisplayDuration = 60;
	private int duration = 0;
	private int fadeInTime = 15;
	
	public Screen(int screen, int state) {
		if (screen == YOU_WIN) {
			display = Resource.screenWin;

			Sound.WIN.play();
			
		} else if (screen == YOU_LOSE) {
			display = Resource.screenLose;

			Sound.GAMEOVER.play();
			
		}
		
		nextState = state;
	}
	
	public void update() {
				

		duration++;
	}
	
	public void paint(Graphics g) {
		
		g.drawImage(display, 0, 0, null);
		
		int xPos = (StartingClass.WINDOWWIDTH/2) - (Resource.screenPressSpace.getWidth(null)/2);
		int yPos = 500;
		
		if (duration >= minDisplayDuration) {
			//draw press space to continue
			g.drawImage(Resource.screenPressSpace, xPos, yPos, null);
			
		} else if (duration >= minDisplayDuration - fadeInTime) {
			//fade in
			
			float percent = (float)(duration - (minDisplayDuration - fadeInTime)) / (float)fadeInTime;
			
			System.out.println("alpha = " + percent);
			
		    Graphics2D g2d = (Graphics2D) g;
		    Composite cTemp = g2d.getComposite();
		    
		    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, percent));
		    g2d.drawImage(Resource.screenPressSpace, xPos, yPos, null);
			
		    g2d.setComposite(cTemp);
		}
		
	}
	
	
	public void readInput(int code, boolean pressed) {
		if (code == KeyEvent.VK_SPACE && pressed && duration >= minDisplayDuration) {
			StartingClass.changeState(nextState);
		}
	}
	
}
