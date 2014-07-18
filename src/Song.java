import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
/*
 * Contains array of sounds, combining the song
 */
public class Song {
	public String title = "";
	public int bpm = 60;		// Beats per minute

	private boolean ignoreLine = false;

	public ArrayList<Sound> sounds;

	public Song() {
		sounds = new ArrayList<>();
	}

	public Song(int bpm) {
		sounds = new ArrayList<>();
		this.bpm = bpm;
	}

	// New sound/note to the song
	public void addSound(Sound s) {
		sounds.add(s);
	}

	// Pauses or stops in the song (pause = sound with Hz = 0)
	public void addPause(double length) {
		sounds.add(new Sound(bpm, 0, length, 0.0));
	}

	// Plays sound one-by-one, making simple melody (pauses not needed)
	public void playSingle() {
		for (Sound s : sounds) s.playSound(bpm);
	}

	// Plays each note threaded, making chords (pauses are needed)
	public void play() {
		for (Sound s : sounds) s.playSoundThreaded();
		System.out.println(title + " finished playing!");
	}

	// Open song.file
	public void openFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		if (chooser.showOpenDialog(null) == 0) {
			try {
				File f = chooser.getSelectedFile();
				getFile(f.getPath());
			} catch (IOException e) { e.printStackTrace(); }
		}
	}

	// Reads song.file
	private void getFile(String filename) throws IOException {
		try (BufferedReader text = new BufferedReader(new FileReader(filename))) {
			System.out.println("Loaded: " + filename);
			String line = text.readLine();
			while (line != null) {
				if (line != null) handleInputString(line);
				line = text.readLine();
			}
			System.out.println(title + " is playing...");
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}

	}

	// Parses each line of song.file, to pauses and sounds
	private void handleInputString(String s) {
		if (s.contains("/*")) ignoreLine = true;
		if (s.contains("*/")) ignoreLine = false;
		if (ignoreLine || s.startsWith("//") || s.trim().equals("") || s.contains("/*") || s.contains("*/")) return;

		if (s.startsWith("title")) {
			String[] t = s.split("=");
			title = t[1].trim();
			return;
		}
		if (s.startsWith("beat") || s.startsWith("bpm")) {
			String[] t = s.split("=");
			bpm = Integer.parseInt(t[1].trim());
			return;
		}

		String[] text = s.split(", |,");
		if (text.length == 1) addPause(Double.parseDouble(text[0].trim()));
		if (text.length == 2) addSound(new Sound(bpm, Note.getFrequency(text[0]), Double.parseDouble(text[1]), 1.0));
		if (text.length == 3) addSound(new Sound(bpm, Note.getFrequency(text[0]), Double.parseDouble(text[1]), Double.parseDouble(text[2])));
	}
}