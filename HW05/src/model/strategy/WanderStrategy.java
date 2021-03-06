package model.strategy;

import java.awt.Point;

import model.Ball;
import model.IUpdateStrategy;
import util.IDispatcher;
import util.Randomizer;

/**
 * The WanderBall strategy 
 * @author Ye Wang
 * @author Chen Zeng
 * @version 1.0
 */
public class WanderStrategy<TDispMsg> implements IUpdateStrategy<TDispMsg> {
	/**
	 * StraightStrategy does nothing special update.
	 */
	@Override
	public void updateState(Ball context, IDispatcher<TDispMsg> dispatcher) {

		Point vel = context.getVel();
		vel.x = Randomizer.Singleton.randomInt(-15, 15);
		vel.y = Randomizer.Singleton.randomInt(-15, 15);

	}

	@Override
	public void init(Ball context) {
		// TODO Auto-generated method stub

	}
}
