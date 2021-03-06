package model;

import util.IDispatcher;

/**
 * Interface that represents commands sent through the dispatcher to process the balls
 * @author Ye Wang
 * @author Chen Zeng
 * @version 1.0
 */
@FunctionalInterface
public abstract interface IBallCmd {
	/**
	 * The method run by the ball's update method which is called when the ball is updated by the dispatcher.
	 * @param context The ball that is calling this method.   The context under which the command is to be run.
	 * @param dispatcher The dispatcher of the ball.
	 */
	public abstract void apply(Ball context, IDispatcher<IBallCmd> dispatcher);
}