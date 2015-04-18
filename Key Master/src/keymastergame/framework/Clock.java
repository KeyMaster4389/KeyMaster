package keymastergame.framework;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Calendar;

import keymastergame.StartingClass;


public class Clock {
	
	private int Duration; // Time for doing the level in seconds.
	 
	private int previous = -1;
	private int sec;
	private int totalSeconds = 0; // Total of seconds elapsed.
	private boolean timesUp = false;
	char position2, position1, position0;
	
	public Image timeImage0, timeImage1, timeImage2;
		
	String  totalSecondsChar;
	
	public Clock(){
		Duration = 60;
	}
	
	public Clock(int levelTime){
		Duration = levelTime;
	}
	
	public void update() {
			
		Calendar calendar = Calendar.getInstance();
		sec = calendar.get(Calendar.SECOND);
		
		if(sec != previous){
			totalSeconds = totalSeconds + 1;
			previous = sec;
			
			Duration = Duration - 1;
			
			totalSecondsChar = Integer.toString(Duration); // Converts the totalSeconds to String, and then analyze each position of the number.
				
			if(totalSecondsChar.length() >= 1 && timesUp == false){
				if(totalSecondsChar.length() == 1){
					position0 = totalSecondsChar.charAt(0);
				}
				if(totalSecondsChar.length() == 2){
					position0 = totalSecondsChar.charAt(1);
				}
				if(totalSecondsChar.length() == 3){
					position0 = totalSecondsChar.charAt(2);
				}
				//offset such that char '0' to equal subscript 0
				timeImage0 = Resource.number[position0 - '0'];
			}
				
			if(totalSecondsChar.length() >= 2 && timesUp == false){
				if(totalSecondsChar.length() == 2){
					position1 = totalSecondsChar.charAt(0);
				}
				if(totalSecondsChar.length() == 3){
					position1 = totalSecondsChar.charAt(1);
				}
				timeImage1 = Resource.number[position1 - '0'];
			}else{
				timeImage1 = Resource.number[0];
			}
			
			if(totalSecondsChar.length() == 3 && timesUp == false){	
					position2 = totalSecondsChar.charAt(0);
					timeImage2 = Resource.number[position2 - '0'];
			}else{
				timeImage2 = Resource.number[0];
			}
			
			if(Duration == 0){
				timesUp = true;
					timeImage2 = Resource.number[0];
					timeImage1 = Resource.number[0];
					timeImage0 = Resource.number[0];
			}
		
		}
				
	}
	
	public boolean getTime() {
		return timesUp;
	}
	
	public Image getTimeImage0() {
        return timeImage0;
    }
	
	public Image getTimeImage1() {
        return timeImage1;
    }
	
	public Image getTimeImage2() {
        return timeImage2;
    }
	
	public void setDuration(int levelTime){
		Duration = levelTime;
	}
	
	public void paint(Graphics g) {
		Vector offset = new Vector(5,3);
		g.drawImage(Resource.time, (int)(StartingClass.WINDOWWIDTH - 164 + offset.x), (int)offset.y - 3, null);
		g.drawImage(timeImage2, (int)(StartingClass.WINDOWWIDTH - 96 + offset.x), (int)offset.y, null);
		g.drawImage(timeImage1, (int)(StartingClass.WINDOWWIDTH - 73 + offset.x), (int)offset.y, null);
		g.drawImage(timeImage0, (int)(StartingClass.WINDOWWIDTH - 50 + offset.x), (int)offset.y, null);
	}
}
