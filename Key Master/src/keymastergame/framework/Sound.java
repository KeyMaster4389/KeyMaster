package keymastergame.framework;

import java.applet.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	public static AudioClip DIE;
	public static AudioClip WIN;
	public static AudioClip GAMEOVER;
	public static AudioClip TILE_REMOVE;
	public static AudioClip TILE_REAPPEAR;
	public static AudioClip MENU;
	public static AudioClip VICTORY;
	
	private static AudioInputStream audioIn; 
	public static Clip MUSIC; 

	public static void setUpMusic(String path) {		 
		 		try {			 
		 			audioIn = AudioSystem.getAudioInputStream(new URL(path)); 
		 			MUSIC = AudioSystem.getClip(); 
		 			MUSIC.open(audioIn); 
		 			 
				} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) { 
					e.printStackTrace(); 
				} 
			} 
		 	 
		 	public static void closeMusic() { 
		 		try { 
		 			audioIn.close(); 
		 		} catch (IOException e) { 
		 			// TODO Auto-generated catch block 
		 			e.printStackTrace(); 
		 		} 
			} 

	
}
