import javax.sound.midi.*;
import java.util.*;

public class Midi {
	private Synthesizer synth;
	private MidiChannel channel;
	private MidiChannel[] channels;
	private Soundbank soundbank;

	public Midi() {
		try {
			this.synth = MidiSystem.getSynthesizer();
			synth.open();
			this.soundbank = this.synth.getDefaultSoundbank();
			synth.loadAllInstruments(soundbank);
			this.channels = synth.getChannels();
			this.channel = channels[0];
		} catch (Exception exception) {
			System.out.println(exception);
		}
	}

	public void setInstrument(int instNum) {
		try {
			channel.programChange(instNum);
		} catch (Exception ex) {
			System.out.println("Error: Invalid Instrument");
		}
		
	}

	public void play(int[] notes, int duration) {
		try {
			System.out.println("Playing...");
			for (int i = 0; i < notes.length; i++) {
				int note = notes[i];
				channel.noteOn(note, 64);
			}
			Thread.sleep(duration);
			for (int i = 0; i < notes.length; i++) {
				int note = notes[i];
				channel.noteOff(note, 64);
			}
		} catch (InterruptedException interruptedException) {}
	}

	// public static void main(String[] args) {
	// 	Midi midi = new Midi();
	// 	int[] notes = new int[]{64, 64, 65, 66, 67};
	// 	midi.playChord(notes, 500);
	// }
}

