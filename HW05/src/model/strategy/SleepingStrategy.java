package model.strategy;

import java.awt.Point;

import model.Ball;
import model.IUpdateStrategy;
import util.IDispatcher;
import util.Randomizer;

/**
 * The SleepingBall strategy 
 * @author Ye Wang
 * @author Chen Zeng
 * @version 1.0
 */
public class SleepingStrategy<TDispMsg> implements IUpdateStrategy<TDispMsg> {

	private boolean sleepflag = false;

	private int counter = 0;

	/* (non-Javadoc)
	 * @see model.IUpdateStrategy#updateState(model.CBall, util.Dispatcher)
	 */
	@Override
	public void updateState(Ball context, IDispatcher<TDispMsg> dispatcher) {

		Point vel = context.getVel();
		if (sleepflag) {
			vel.x = Randomizer.Singleton.randomInt(-30, 30);
			vel.y = Randomizer.Singleton.randomInt(-30, 30);
		} else {
			vel.x = 0;
			vel.y = 0;
		}
		counter++;
		if (counter >= 30) {
			sleepflag = !sleepflag;
			counter = 0;
		}

	}

	@Override
	public void init(Ball context) {
		// TODO Auto-generated method stub

	}
}
