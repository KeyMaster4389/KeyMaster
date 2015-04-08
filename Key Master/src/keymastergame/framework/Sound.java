package keymastergame.framework;

import java.applet.*;

public class Sound {

	public static final AudioClip DIE = Applet.newAudioClip(Sound.class.getResource
			("die.wav"));
	
	public static final AudioClip GAMEOVER = Applet.newAudioClip(Sound.class.getResource
			("gameover.wav"));
	
	public static final AudioClip TILE = Applet.newAudioClip(Sound.class.getResource
			("tilesound.wav"));
	
	public static final AudioClip MUSIC = Applet.newAudioClip(Sound.class.getResource
			("music.wav"));
	
}
