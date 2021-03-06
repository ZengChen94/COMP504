package model.strategy;

import model.Ball;
import model.IUpdateStrategy;
import util.IDispatcher;

/**
 * The StraightBall strategy subclass
 * as same as the bouncing ball
 * @author Ye Wang
 * @author Chen Zeng
 * @version 1.0
 * 
 */
public class StraightStrategy<TDispMsg> implements IUpdateStrategy<TDispMsg> {
	@Override
	public void updateState(Ball context, IDispatcher<TDispMsg> dispatcher) {
	}

	@Override
	public void init(Ball context) {
		// TODO Auto-generated method stub

	}
}
