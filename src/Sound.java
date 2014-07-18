/*
 * Class representing a single note/sound in i.e. a song
 * Different from Tone. Sound is in beat-precision
 */
public class Sound {
	public double hz = 440.0;	// Hz-frequency of sound
	public double length = 1.0;	// Length of sound (1.0 = 1 beat long, 0.5 = half beat, 2.0 double beat etc.)
	public double volume = 1.0;	// Volume (from 0.0 to 1.0)
	public int bpm = 60;		// Beats per minute

	public Sound(int bpm, double hz, double length, double volume) {
		this.bpm = bpm;
		this.hz = hz;
		this.length = length;
		this.volume = volume;
	}

	public Sound(double hz, double length, double volume) {
		this.hz = hz;
		this.length = length;
		this.volume = volume;
	}

	public Sound(String note, double length, double volume) {
		this.hz = Note.getFrequency(note);
		this.length = length;
		this.volume = volume;
	}

	public Sound(double hz, double length) {
		this.hz = hz;
		this.length = length;
	}

	public Sound(String note, double length) {
		this.hz = Note.getFrequency(note);
		this.length = length;
	}

	// Plays simple tone, custom bpm
	public void playSound(int bpm) {
		if (hz == 0) Tone.sleepBeat(bpm, length);
		else Tone.tone(bpm, hz, length, volume);
	}

	// Plays simple tone, predefined bpm
	public void playSound() {
		if (hz == 0) Tone.sleepBeat(bpm, length);
		Tone.tone(bpm, hz, length, volume);
	}

	// Plays threaded tone (needed for chords), custom bpm
	public void playSoundThreaded(int bpm) {
		if (hz == 0) Tone.sleepBeat(bpm, length);
		else Tone.threadedTone(bpm, hz, length, volume);
	}

	// Plays threaded tone (needed for chords), predefined bpm
	public void playSoundThreaded() {
		if (hz == 0) Tone.sleepBeat(bpm, length);
		else Tone.threadedTone(bpm, hz, length, volume);
	}
}