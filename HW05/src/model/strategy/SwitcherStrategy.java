package model.strategy;

import model.Ball;
import model.IUpdateStrategy;
import util.IDispatcher;

/**
 * The method switch the ball strategy 
 * 
 * @author Ye Wang
 * @author Chen Zeng
 * @version 1.0
 * 
 */
public class SwitcherStrategy<TDispMsg> implements IUpdateStrategy<TDispMsg> {

	private IUpdateStrategy<TDispMsg> strategy;

	/**
	 * constructor of switcherStrategy
	 */
	public SwitcherStrategy() {
		strategy = new StraightStrategy<TDispMsg>();
	}

	/* (non-Javadoc)
	 * @see model.IUpdateStrategy#updateState(model.CBall, util.Dispatcher)
	 */
	@Override
	public void updateState(Ball context, IDispatcher<TDispMsg> dispatcher) {

		strategy.updateState(context, dispatcher);
	}

	/**
	 * @param Nstrategy return new strategy
	 */
	public void setNew(IUpdateStrategy<TDispMsg> Nstrategy) {
		strategy = Nstrategy;
	}

	@Override
	public void init(Ball context) {
		// TODO Auto-generated method stub

	}

}
