package strategy;

import java.awt.Point;

import model.CBall;
import model.IUpdateStrategy;
import util.Dispatcher;
import util.Randomizer;

/**
 * The SleepingBall strategy 
 * @author Yue Pan
 * @author Chen Zeng
 * @version 1.0
 */
public class SleepingStrategy implements IUpdateStrategy {

	private boolean sleepflag = false;

	private int counter = 0;

	/* (non-Javadoc)
	 * @see model.IUpdateStrategy#updateState(model.CBall, util.Dispatcher)
	 */
	@SuppressWarnings("javadoc")
	@Override
	public void updateState(CBall ball, Dispatcher dispatcher) {

		Point vel = ball.getVel();
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
}
