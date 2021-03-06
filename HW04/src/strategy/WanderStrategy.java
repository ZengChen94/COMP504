package strategy;

import java.awt.Point;

import model.CBall;
import model.IUpdateStrategy;
import util.Dispatcher;
import util.Randomizer;

/**
 * The WanderBall strategy 
 * @author Yue Pan
 * @author Chen Zeng
 * @version 1.0
 */
public class WanderStrategy implements IUpdateStrategy {
	/**
	 * StraightStrategy does nothing special update.
	 */
	@Override
	public void updateState(CBall ball, Dispatcher dispatcher) {

		Point vel = ball.getVel();
		vel.x = Randomizer.Singleton.randomInt(-15, 15);
		vel.y = Randomizer.Singleton.randomInt(-15, 15);

	}
}
