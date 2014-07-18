/*
 * This application plays songs, made up of sounds generated from java's own libraries
 * The songs are imported from a file (i.e. beethoven.txt) with syntax (excluding the "-sign):
 * 
 * "																							"
 * 	title = songname
 * 	bpm = 60 			// beats per minute
 * 
 * 	// this is a comment,  */ /*, can also be used as comments over several lines
 * 	// comments can be used for numerating bars
 * 	// be aware, a if comment is used, the whole line will be ignored
 * 
 *
 * 	// 1.
 * 	C4, 0.5, 1.0	// Plays a C4, for 0.5 beats, with volume 1.0
 * 	1.0				// Pause 1 beat
 * 	C#4, 1.0		// Plays a C#4, for 1.0 beats, volume is by default 1.0 if not specified
 * 	1.0
 * 	C4, 1.0			// These 3 next notes create a chord of C4, E4 and G4 (C-major chord)
 * 	E4, 1.0			// The notes does not need to be of the same length
 * 	G4, 1.0			// If used properly can be used to create complex pieces
 * 	1.0
 * 
 * 	// 2.
 * 	Bb4, 1.0, 0.7
 * 	1.0
 * 	A3, 1.0
 * 	// etc...
 * "																							"
 * 
 * Main-class outside of Song.java, for possible use of multiple songs in the future
 * 
 */
public class SoundExperiment{
	// GO!!
	public static void main(String[] args){
		Song song = new Song();
		song.openFile();
		song.play();
	}
}
