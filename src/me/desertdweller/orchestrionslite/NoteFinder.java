package me.desertdweller.orchestrionslite;

public class NoteFinder {

	private static Note[] noteList = {new Note("F#", 0.5f),new Note("G", 0.529732f), new Note("G#", 0.561231f), 
			new Note("A", 0.594604f), new Note("A#", 0.629961f), new Note("B", 0.667420f), new Note("C", 0.707107f), 
			new Note("C#", 0.749154f), new Note("D", 0.793701f), new Note("D#", 0.840896f), new Note("E", 0.890899f), 
			new Note("F", 0.943874f), new Note("F#", 1f), new Note("G", 1.059463f), new Note("G#", 1.122462f),
			new Note("A", 1.189207f), new Note("A#", 1.259921f), new Note("B", 1.334840f), new Note("C", 1.414214f),
			new Note("C#", 1.498307f), new Note("D", 1.587401f), new Note("D#", 1.681793f), new Note("E", 1.781797f),
			new Note("F", 1.887749f), new Note("F#", 2f)}; 
	
	private static String[] octaveNotes = {"F#","G","G#","A","A#","B","C","C#","D","D#","E","F"}; 
	
	public static String getNoteName(int id) {
		return noteList[id].name;
	}
	
	public static float getNotePitch(int octave, int id) {
		System.out.println((float) Math.pow(2, (float) (id+octave*octaveNotes.length) /(float) (octaveNotes.length)));
		return (float) Math.pow(2, (float) (id+octave*octaveNotes.length) /(float) (octaveNotes.length));
	}
	
	public static int getNoteAmount() {
		return octaveNotes.length;
	}
}
