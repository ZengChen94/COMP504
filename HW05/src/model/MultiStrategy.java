package model;

import util.IDispatcher;

/**
 * This method merge two strategies into one combined strategy.
 * 
 * @author Ye Wang
 * @author Chen Zeng
 * @version 1.0
 * */
public class MultiStrategy<TDispMsg> implements IUpdateStrategy<TDispMsg> {

	private IUpdateStrategy<TDispMsg> stg1, stg2;

	/**
	 * @param stg1 strategy1 to combine
	 * @param stg2 strategy2 to combine
	 */
	public MultiStrategy(IUpdateStrategy<TDispMsg> stg1, IUpdateStrategy<TDispMsg> stg2) {
		this.stg1 = stg1;
		this.stg2 = stg2;
	}

	/**
	 * Update the state of the ball.
	 * @param context Ball instance
	 * @param dispatcher Dispatcher for Ball instance
	 */
	@Override
	public void updateState(Ball context, IDispatcher<TDispMsg> dispatcher) {
		stg1.updateState(context, dispatcher);
		stg2.updateState(context, dispatcher);
	}

	/**
	 * Initial the ball.
	 * @param context Ball instance
	 */
	@Override
	public void init(Ball context) {
		// TODO Auto-generated method stub

	}

}
