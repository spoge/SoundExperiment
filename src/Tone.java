/*
 * Static class for making/playing tones, in msec-precision
 * Differs from Sound-class, since Sound.java is in beat-precision
 */

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Tone {
	public static float SAMPLE_RATE = 32000f;	// Can be changed, but be careful

	// Plays a sound/tone, generated from the input-parameters
	public static void sound(double hz, int msecs, double vol) throws LineUnavailableException {
		if (hz <= 0) throw new IllegalArgumentException("Frequency <= 0 hz");
		if (msecs <= 0)	throw new IllegalArgumentException("Duration <= 0 msecs");
		if (vol > 1.0 || vol < 0.0)	throw new IllegalArgumentException("Volume out of range 0.0 - 1.0");

		byte[] buf = new byte[(int) SAMPLE_RATE * msecs / 1000];
		for (int i = 0; i < buf.length; i++) {
			double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
			buf[i] = (byte) (Math.sin(angle) * 127.0 * vol);
		}

		// softens first and last 10ms
		for (int i = 0; i < SAMPLE_RATE / 100.0 && i < buf.length / 2; i++) {
			buf[i] = (byte) (buf[i] * i / (SAMPLE_RATE / 100.0));
			buf[buf.length - 1 - i] = (byte) (buf[buf.length - 1 - i] * i / (SAMPLE_RATE / 100.0));
		}

		AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
		SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
		sdl.open(af);
		sdl.start();
		sdl.write(buf, 0, buf.length);
		sdl.drain();
		sdl.close();
	}

	// Plays single tone/sound from parameters, hz for note height
	public static void tone(int beat, double hz, double note, double volum) {
		try {
			sound(hz, (int) (note * 60000 / beat), volum);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	// Plays single tone/sound from parameters, note for note height (i.e. C#4)
	public static void tone(int beat, String note, double length, double volum) {
		tone(beat, Note.getFrequency(note), length, volum);
	}
	
	// Plays threaded tone/sound from parameters, hz for note height
	public static void threadedTone(final int beat, final double hz, final double length, final double volum) {
		new Thread() {
			public void run() {
				tone(beat, hz, length, volum);
			}
		}.start();
	}

	// Plays threaded tone/sound from parameters, note for note height
	public static void threadedTone(final int beat, final String note, final double length, final double volum) {
		new Thread() {
			public void run() {
				tone(beat, note, length, volum);
			}
		}.start();
	}

	// Used for pauses in the song, in msecs
	public static void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}
	}

	// Used for pauses in the song, in beats
	public static void sleepBeat(int beat, double i) {
		sleep((int) (i * 60000 / beat));
	}
}