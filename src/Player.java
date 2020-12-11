import java.util.*;
import javax.sound.midi.*;

public class Player {

    // Fields
    private Midi midi;

    public Player() {
        // Initializations
        this.midi = new Midi();
    }

    // Takes user input and passes to play and instruments methods.
    public void initialize() {
        Scanner console = new Scanner(System.in);
        System.out.println("Select Instrument Number (Type 'list' for list of instruments): ");
        String input = console.nextLine();
        instruments(input);

        // Once instrument is chosen
        System.out.println("Input Song: ");
        input = console.nextLine();
        while (!input.equals("exit")) {
            play(input);
            System.out.println("Input Song: ");
            input = console.nextLine();
        }
    }
    
    // Displays list of possible MIDI instruments and handles selection of midi instrument.
    public void instruments(String input) {
        Scanner console = new Scanner(System.in); 
        if (input.equals("list")) {
            System.out.println("0: Piano 1        1: Piano 2        2: Piano 3        3: Honky-tonk     4: E.Piano 1");
            System.out.println("5: E.Piano 2      6: Harpsichord    7: Clav.          8: Celesta        9: Glockenspiel");
            System.out.println("10: Music Box     11: Vibraphone    12: Marimba       13: Xylophone     14: Tubular-bell");
            System.out.println("15: Santur        16: Organ 1       17: Organ 2       18: Organ 3       19: Church Org.1");
            System.out.println("20: Reed Organ    21: Accordion Fr  22: Harmonica     23: Bandoneon     24: Nylon-str.Gt");
            System.out.println("25: Steel-str.Gt  26: Jazz Gt.      27: Clean Gt.     28: Muted Gt.     29: Overdrive Gt");
            System.out.println("30: DistortionGt  31: Gt.Harmonics  32: Acoustic Bs.  33: Fingered Bs.  34: Picked Bs.");
            System.out.println("35: Fretless Bs.  36: Slap Bass 1   37: Slap Bass 2   38: Synth Bass 1  39: Synth Bass 2");
            System.out.println("40: Violin        41: Viola         42: Cello         43: Contrabass    44: Tremolo Str");
            System.out.println("45: PizzicatoStr  46: Harp          47: Timpani       48: Strings       49: Slow Strings");
            System.out.println("50: Syn.Strings1  51: Syn.Strings2  52: Choir Aahs    53: Voice Oohs    54: SynVox");
            System.out.println("55: OrchestraHit  56: Trumpet       57: Trombone      58: Tuba          59: MutedTrumpet");
            System.out.println("60: French Horns  61: Brass 1       62: Synth Brass1  63: Synth Brass2  64: Soprano Sax");
            System.out.println("65: Alto Sax      66: Tenor Sax     67: Baritone Sax  68: Oboe          69: English Horn");
            System.out.println("70: Bassoon       71: Clarinet      72: Piccolo       73: Flute         74: Recorder");
            System.out.println("75: Pan Flute     76: Bottle Blow   77: Shakuhachi    78: Whistle       79: Ocarina");
            System.out.println("80: Square Wave   81: Saw Wave      82: Syn.Calliope  83: Chiffer Lead  84: Charang");
            System.out.println("85: Solo Vox      86: 5th Saw Wave  87: Bass & Lead   88: Fantasia      89: Warm Pad");
            System.out.println("90: Polysynth     91: Space Voice   92: Bowed Glass   93: Metal Pad     94: Halo Pad");
            System.out.println("95: Sweep Pad     96: Ice Rain      97: Soundtrack    98: Crystal       99: Atmosphere");
            System.out.println("100: Brightness   101: Goblin       102: Echo Drops   103: Star Theme   104: Sitar");
            System.out.println("105: Banjo        106: Shamisen     107: Koto         108: Kalimba      109: Bagpipe");
            System.out.println("110: Fiddle       111: Shanai       112: Tinkle Bell  113: Agogo        114: Steel Drums");
            System.out.println("115: Woodblock    116: Taiko        117: Melo. Tom 1  118: Synth Drum   119: Reverse Cym.");
            System.out.println("120: Gt.FretNoise 121: Breath Noise 122: Seashore     123: Bird         124: Telephone 1");
            System.out.println("125: Helicopter   126: Applause     127: Gun Shot");

    	    System.out.println("");
    	    System.out.println("Select an Instrument:");
            input = console.nextLine();
        } 
 	
        int instrument = Integer.parseInt(input);
        while (instrument < 0 || instrument > 127) {
            System.out.println("Invalid Instrument. Please type a number between 0 and 127.");
            instrument = Integer.parseInt(console.nextLine());
        } 
        midi.setInstrument(instrument);
    }

    // Interprets input (notes, durations, chords, accidentals and octaves) and plays the notes.  
    public void play(String input) {
        List<String[]> allInput = new ArrayList<String[]>();
        List<Integer> durations = new ArrayList<Integer>();
        String[] inputSplitter = input.split(" ");

        // Adding all the notes and durations to ArrayLists
        for (int i = 0; i < inputSplitter.length; i++) {
            String slice = inputSplitter[i];
            String notes = slice.substring(0, slice.indexOf("_"));
            durations.add(Integer.parseInt(slice.substring(slice.indexOf("_") + 1)));
            String[] notesSplit = notes.split("/");
            allInput.add(notesSplit); 
        }

        for (int i = 0; i < allInput.size(); i++) {

            int[] chordNotes = new int[allInput.get(i).length];
            for (int j = 0; j < chordNotes.length; j++) {
                chordNotes[j] = notePicker(allInput.get(i)[j]);
            }
            midi.play(chordNotes, durations.get(i));
        }
    }
    
    // Helper method for play to decide the MIDI note number.
    public int notePicker(String input) {
        int note = 0;
        String letter = input.substring(0, 1);

        if (letter.equals("A")) {
			note = 69;
		} else if (letter.equals("B")) {
			note = 71;
		} else if (letter.equals("C")) {
			note = 60;
		} else if (letter.equals("D")) {
			note = 62;
		} else if (letter.equals("E")) {
			note = 64;
		} else if (letter.equals("F")) {
			note = 65;
		} else if (letter.equals("G")) {
			note = 67;
		}
        // Sharps
        if (input.indexOf("#") != -1) {
			note += 1;
		}
        // Flats
		if (input.indexOf("b") != -1) {
			note -= 1;
		}
        // Octaves
		if (input.indexOf("@") != -1) {
			note += 12*(Integer.parseInt(input.substring(input.indexOf("@") + 1)));
		}

        return note;
    } 

    public static void main(String[] args) {
        Player player = new Player();
        player.initialize();
    }
}