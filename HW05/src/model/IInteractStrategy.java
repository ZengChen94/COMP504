package model;

import util.IDispatcher;

/**
 * Strategy that defines a directed interaction between two balls where the balls are NOT
 * equivalently processed. 
 * 
 * @author swong
 * 
 */
public interface IInteractStrategy {

	/**
	 * Performs a directed interaction between the context ball and the target Ball from the 
	 * perspective of the context Ball.
	 * @param context  The Ball from whose perspective the interaction 
	 * processing takes place.
	 * @param target  The Ball that is the "other ball" in the perspective of this processing.
	 * @param dispatcher  The Dispatcher that is to be used if desired.
	 */
	public void interact(Ball context, Ball target, IDispatcher<IBallCmd> dispatcher);

	/**
	 * Null strategy with no-op behavior.
	 */
	public static final IInteractStrategy NULL_STRATEGY = (context, target, disp) -> {
	};
}
