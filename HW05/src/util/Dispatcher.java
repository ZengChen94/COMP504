package util;

import java.util.*;

/**
 * A wrapper around Java's java.util.Observable to make it easier to use. 
 * The changed state of the Dispatcher does not need to be separately set.
 * @author Ye Wang
 * @author Chen Zeng
 * @version 1.0
 */
public class Dispatcher extends Observable {
	/**
	 * Immediately notifies all the Observers held.
	 * @param param An input parameter that is passed on to all the Observers.
	 */
	public void notifyAll(Object param) {
		setChanged();
		notifyObservers(param);
	}
}
