package sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundHandler {
	
	/**
	 * "Loading" menu music
	 */
	public static String SONG_ONE = "sounds/loading.wav";
	
	/**
	 * "BeautyPlus" game music
	 */
	public static String SONG_TWO = "sounds/BeautyPlus.wav";
	
	/**
	 * the sound of the ball getting putted
	 */
	public static String PuttingSound = "sounds/BeautyPlus.wav";
	
	public static Clip music=null;
	
	public static String currentSong="";
	
	static FloatControl gainControl;
	
	public static void play(String ref) {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File(
					ref));
			Clip clip = AudioSystem.getClip();

			clip.open(audio);

			clip.start();

		} catch (Exception e) {
			System.out.println("check " + ref + "\n");
			e.printStackTrace();
		}
	}

	public static void play(String ref, float deltaGain) {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File(
					ref));
			Clip clip = AudioSystem.getClip();

			clip.open(audio);

			FloatControl gainControl = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(deltaGain); // Reduce volume by deltaGain

			clip.start();

		} catch (Exception e) {
			System.out.println("check " + ref + "\n");
			e.printStackTrace();
		}
	}

	
	
	public static void play(String ref, float deltaGain, float deltaPitch) {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File(
					ref));
			Clip clip = AudioSystem.getClip();

			clip.open(audio);

			FloatControl gainControl = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(deltaGain); // Reduce volume by deltaGain

			FloatControl pitchControl = (FloatControl) clip
					.getControl(FloatControl.Type.SAMPLE_RATE);
			pitchControl.setValue(deltaPitch);

			clip.start();

		} catch (Exception e) {
			System.out.println("check " + ref + "\n");
			e.printStackTrace();
		}
	}
	
	public static void playMusic(String ref,float deltaGain){
		
		try {
			if(!(music==null))
			music.stop();
			
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File(
					ref));
			
			currentSong=ref;
			
			music=AudioSystem.getClip();
			
			music.open(audio);
			
			gainControl = (FloatControl) music
					.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(deltaGain);
			
			music.loop(9999);
			
			music.start();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setMusicVolume(double gain){
		gainControl.setValue((float) (Math.log(gain) / Math.log(10.0) * 20.0));
	}
	
}