package visitor;

import provided.music.APhraseVisitor;
import provided.music.Chord;
import provided.music.IPhrase;
import provided.music.IPhraseVisitorCmd;
import provided.music.NESeqList;
import provided.music.Note;
import provided.music.Triplet;
import provided.player.SequencePlayer2;
import provided.util.ABCUtil;
import provided.util.KeySignature;
import provided.music.Header;

/**
 * The PlayMusic visitor extends the APhraseVisitor
 * It implements the algorithm to play music for different hosts.
 *
 * @author Yuchang Shen
 * @author Chen Zeng
 * @version 1.0
 *
 */
public class PlayMusicVisitor extends APhraseVisitor {
	/**
	 * Key signatures ("K" header) 
	 */
	private KeySignature keySign;

	/**
	 * Constructor
	 */
	public PlayMusicVisitor() {

		/**
		 * Ignored headers
		 */
		String headers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < headers.length(); i++) {
			addCmd("" + headers.charAt(i), new IPhraseVisitorCmd() {
				@Override
				public Object apply(String id, IPhrase host, Object... params) {
					return params[1];
				}
			});
		}

		/**
		 * Default note ("L" header)
		 */
		addCmd("L", new IPhraseVisitorCmd() {
			@Override
			public Object apply(String id, IPhrase host, Object... params) {
				SequencePlayer2 sp2 = (SequencePlayer2) params[0];
				double fraction = ABCUtil.Singleton.parseFraction(((Header) host).getValue());
				fraction = sp2.getTicksPerDefaultNote() * fraction * 4;
				sp2.setTicksPerDefaultNote((int) fraction);
				return params[1];
			}
		});

		/**
		 * Tempo ("Q" header)
		 */
		addCmd("Q", new IPhraseVisitorCmd() {
			@Override
			public Object apply(String id, IPhrase host, Object... params) {
				SequencePlayer2 sp2 = (SequencePlayer2) params[0];
				double tempo = ABCUtil.Singleton.parseTempo(((Header) host).getValue(), 1.0);
				sp2.setTempo((int) tempo);
				return params[1];
			}
		});

		/**
		 * Key signatures ("K" header) 
		 */
		addCmd("K", new IPhraseVisitorCmd() {
			@Override
			public Object apply(String id, IPhrase host, Object... params) {
				keySign = new KeySignature(((Header) host).getValue());
				return params[1];
			}
		});

		/**
		 * Non-empty sequence list 
		 */
		addCmd("NESeqList", new IPhraseVisitorCmd() {
			@Override
			public Object apply(String id, IPhrase host, Object... params) {
				NESeqList neSeqlist = (NESeqList) host;
				SequencePlayer2 sp2 = (SequencePlayer2) params[0];
				int next = (int) neSeqlist.getFirst().execute(PlayMusicVisitor.this, params);
				next = (int) neSeqlist.getRest().execute(PlayMusicVisitor.this, sp2, next);
				return next;
			}
		});

		/**
		 * Empty sequence list 
		 */
		addCmd("MTSeqList", new IPhraseVisitorCmd() {
			@Override
			public Object apply(String id, IPhrase host, Object... params) {
				return params[1];
			}
		});

		/**
		 * Chords 
		 */
		addCmd("Chord", new IPhraseVisitorCmd() {
			@Override
			public Object apply(String id, IPhrase host, Object... params) {
				Note[] notes = ((Chord) host).getNotes();
				int next = (int) params[1];
				for (int i = 0; i < notes.length; i++) {
					Note note = notes[i];
					next = (int) note.execute(PlayMusicVisitor.this, params);
				}
				return next;
			}
		});

		/**
		 * Triplets 
		 */
		addCmd("Triplet", new IPhraseVisitorCmd() {
			@Override
			public Object apply(String id, IPhrase host, Object... params) {
				Note[] notes = ((Triplet) host).getNotes();
				int nextTick = (int) params[1];
				for (int i = 0; i < notes.length; i++) {
					Note note = notes[i];
					note.setDuration(((float) note.getDuration()) * 2 / 3);
					nextTick = (int) note.execute(PlayMusicVisitor.this, params);
				}
				return nextTick;
			}
		});

		/**
		 * Individual notes
		 */
		addCmd("Note", new IPhraseVisitorCmd() {
			@Override
			public Object apply(String id, IPhrase host, Object... params) {
				SequencePlayer2 sp2 = (SequencePlayer2) params[0];
				Note newNote = keySign.adjust(((Note) host));
				return sp2.addNote(newNote, (int) params[1]);
			}
		});

		/**
		 * Unknown host
		 * APhraseVisitor has thrown them
		 */
	}
}
