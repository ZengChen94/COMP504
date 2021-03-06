package strategy;

import model.CBall;
import model.IUpdateStrategy;
import util.Dispatcher;

/**
 * This method merge two strategies into one combined strategy.
 * 
 * @author Yue Pan
 * @author Chen Zeng
 * @version 1.0
 * */
public class MultiStrategy implements IUpdateStrategy {

	private IUpdateStrategy stg1, stg2;

	/**
	 * @param stg1 strategy1 to combine
	 * @param stg2 strategy2 to combine
	 */
	public MultiStrategy(IUpdateStrategy stg1, IUpdateStrategy stg2) {
		this.stg1 = stg1;
		this.stg2 = stg2;
	}

	/* (non-Javadoc)
	 * @see model.IUpdateStrategy#updateState(model.CBall, util.Dispatcher)
	 */
	@SuppressWarnings("javadoc")
	@Override
	public void updateState(CBall ball, Dispatcher dispatcher) {
		stg1.updateState(ball, dispatcher);
		stg2.updateState(ball, dispatcher);
	}

}
