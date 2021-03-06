package strategy;

import model.CBall;
import model.IUpdateStrategy;
import util.Dispatcher;

/**
 * The method switch the ball strategy 
 * 
 * @author Yue Pan
 * @author Chen Zeng
 * @version 1.0
 * 
 */
public class SwitcherStrategy implements IUpdateStrategy {

	private IUpdateStrategy strategy;

	/**
	 * constructor of switcherStrategy
	 */
	public SwitcherStrategy() {
		strategy = new StraightStrategy();
	}

	/* (non-Javadoc)
	 * @see model.IUpdateStrategy#updateState(model.CBall, util.Dispatcher)
	 */
	@SuppressWarnings("javadoc")
	@Override
	public void updateState(CBall _ball, Dispatcher dispatcher) {

		strategy.updateState(_ball, dispatcher);
	}

	/**
	 * @param Nstrategy return new strategy
	 */
	public void setNew(IUpdateStrategy Nstrategy) {
		strategy = Nstrategy;
	}

}
