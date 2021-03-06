package model;

import visitor.PlayMusicVisitor;
import visitor.ToStringVisitor;
import provided.abcParser.ABCParser;
import provided.music.IPhrase;
import provided.music.NESeqList;
import provided.player.ISequencePlayerStatus;
import provided.player.SequencePlayer2;
import provided.player.SequencePlayer2.IPlayable;
import provided.util.ABCInstrument;
import provided.util.ABCUtil;

/**
 * 
 * Model for the app 
 * @author Yuchang Shen
 * @author Chen Zeng
 * @version 1.0
 *
 */
public class Model {
	/**
	 * The adapter to the view
	 */
	private IViewAdapter _iViewAdapter;

	/**
	 * The parse of the music
	 */
	private ABCParser parser;

	/**
	 * The player of the music
	 */
	private SequencePlayer2 sp2;

	/**
	 * The playable of the music
	 */
	private IPlayable playable;

	/**
	 * The phrase of the music
	 */
	private IPhrase phrase;

	/**
	 * The name of the music
	 */
	private String fullPathName;

	/**
	 * Constructor
	 * @param iViewAdapter The adapter to the view
	 */
	public Model(IViewAdapter iViewAdapter) {
		// TODO Auto-generated constructor stub
		this._iViewAdapter = iViewAdapter;
		sp2 = new SequencePlayer2(16, 0);
	}

	/**
	 * initialize the instrument choices
	 */
	public void start() {
		ABCInstrument[] instruments = ABCUtil.Singleton.getInstruments();
		for (ABCInstrument instrument : instruments) {
			_iViewAdapter.addInstrument(instrument);
		}
	}

	/**
	 * This method load music
	 * @param musicName The name of the music
	 * @return contents The contents of the music
	 */
	public String loadMusic(String musicName) {
		fullPathName = "/songs/" + musicName + ".abc";
		parser = new ABCParser(fullPathName);
		return ABCUtil.Singleton.getFileContents(fullPathName);
	}

	/**
	 * This method parse music
	 * @return phrase The phrase of the music
	 */
	public String parseMusic() {
		NESeqList.setToStringAlgo(new ToStringVisitor());
		phrase = parser.parse();
		return phrase.toString();
	}

	/**
	 * This method play music
	 * reference: https://www.clear.rice.edu/comp504/f17/labs/lab07/
	 * @param instrument The type of instrument
	 */
	public void playMusic(ABCInstrument instrument) {
		sp2.init(16, instrument.getValue());
		sp2.setTicksPerDefaultNote(16); //default
		sp2.setTempo(120); //default
		parser = new ABCParser(fullPathName);
		parser.parse().execute(new PlayMusicVisitor(), sp2, 0);
		playable = sp2.makePlayable(ISequencePlayerStatus.NULL);
		playable.start();
	}

	/**
	 * This method stop music
	 */
	public void stopMusic() {
		playable.stop();
	}
}
