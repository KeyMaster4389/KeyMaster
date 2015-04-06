package keymastergame.framework;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Calendar;

import keymastergame.StartingClass;


public class Clock {
	
	private int Duration = 60; // Time for doing the level in seconds.
	 
	private int previous = -1;
	private int sec;
	private int totalSeconds = 0; // Total of seconds elapsed.
	private boolean timesUp = false;
	char position2, position1, position0;
	
	public Image timeImage0, timeImage1, timeImage2;
		
	String  totalSecondsChar;
	
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
				if (position0 == '0') {
					timeImage0 = Resource.zero;
				}else if (position0 == '1') {
					timeImage0 = Resource.one;
				}else if (position0 == '2') {
					timeImage0 = Resource.two;
				}else if (position0 == '3') {
					timeImage0 = Resource.three;
				}else if (position0 == '4') {
					timeImage0 = Resource.four;
				}else if (position0 == '5') {
					timeImage0 = Resource.five;
				}else if (position0 == '6') {
					timeImage0 = Resource.six;
				}else if (position0 == '7') {
					timeImage0 = Resource.seven;
				}else if (position0 == '8') {
					timeImage0 = Resource.eight;
				}else if (position0 == '9') {
					timeImage0 = Resource.nine;
				}
		}
			
		if(totalSecondsChar.length() >= 2 && timesUp == false){
			if(totalSecondsChar.length() == 2){
				position1 = totalSecondsChar.charAt(0);
			}
			if(totalSecondsChar.length() == 3){
				position1 = totalSecondsChar.charAt(1);
			}
				if (position1 == '0') {
					timeImage1 = Resource.zero;
				}else if (position1 == '1') {
					timeImage1 = Resource.one;
				}else if (position1 == '2') {
					timeImage1 = Resource.two;
				}else if (position1 == '3') {
					timeImage1 = Resource.three;
				}else if (position1 == '4') {
					timeImage1 = Resource.four;
				}else if (position1 == '5') {
					timeImage1 = Resource.five;
				}else if (position1 == '6') {
					timeImage1 = Resource.six;
				}else if (position1 == '7') {
					timeImage1 = Resource.seven;
				}else if (position1 == '8') {
					timeImage1 = Resource.eight;
				}else if (position1 == '9') {
					timeImage1 = Resource.nine;
				}
		}else{
			timeImage1 = Resource.zero;
		}
		
		if(totalSecondsChar.length() == 3 && timesUp == false){	
				position2 = totalSecondsChar.charAt(0);
	
				if (position2 == '0') {
					timeImage2 = Resource.zero;
				}else if (position2 == '1') {
					timeImage2 = Resource.one;
				}else if (position2 == '2') {
					timeImage2 = Resource.two;
				}else if (position2 == '3') {
					timeImage2 = Resource.three;
				}else if (position2 == '4') {
					timeImage2 = Resource.four;
				}else if (position2 == '5') {
					timeImage2 = Resource.five;
				}else if (position2 == '6') {
					timeImage2 = Resource.six;
				}else if (position2 == '7') {
					timeImage2 = Resource.seven;
				}else if (position2 == '8') {
					timeImage2 = Resource.eight;
				}else if (position2 == '9') {
					timeImage2 = Resource.nine;
				}
		}else{
			timeImage2 = Resource.zero;
		}
		
		if(Duration == 0){
			timesUp = true;
				timeImage2 = Resource.zero;
				timeImage1 = Resource.zero;
				timeImage0 = Resource.zero;
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
	
	public void paint(Graphics g) {
		Vector offset = new Vector(5,3);
		g.drawImage(Resource.time, (int)(StartingClass.WINDOWWIDTH - 185 + offset.x), (int)offset.y, null);
		g.drawImage(timeImage2, (int)(StartingClass.WINDOWWIDTH - 96 + offset.x), (int)offset.y, null);
		g.drawImage(timeImage1, (int)(StartingClass.WINDOWWIDTH - 73 + offset.x), (int)offset.y, null);
		g.drawImage(timeImage0, (int)(StartingClass.WINDOWWIDTH - 50 + offset.x), (int)offset.y, null);
	}
}
