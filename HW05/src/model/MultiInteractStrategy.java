package model;

import util.IDispatcher;

/**
 * This method handle the interact between two balls.
 * 
 * @author Ye Wang
 * @author Chen Zeng
 * @version 1.0
 * */
public class MultiInteractStrategy implements IInteractStrategy {

	/**
	 * The first interact strategy
	 */
	private IInteractStrategy strategy1;

	/**
	 * The second interact strategy
	 */
	private IInteractStrategy strategy2;

	/**
	 * The constructor that initiates the class
	 * @param interact1 The first interact strategy
	 * @param interact2 The second interact strategy
	 */
	public MultiInteractStrategy(IInteractStrategy interact1, IInteractStrategy interact2) {
		this.strategy1 = interact1;
		this.strategy2 = interact2;
	}

	/**
	 * The interact method for the multi interact strategy
	 * Implements the interact methods for both interact strategies
	 * @param context Ball instance.
	 * @param target The target ball.
	 * @param disp The dispatcher.
	 */
	@Override
	public void interact(Ball context, Ball target, IDispatcher<IBallCmd> disp) {
		this.strategy1.interact(context, target, disp);
		this.strategy2.interact(context, target, disp);
	}
}
