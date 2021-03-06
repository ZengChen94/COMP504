package model.strategy;

import java.awt.Rectangle;

import model.Ball;
import util.Randomizer;

/**
 * Strategy for a ball to mindlessly (randomly) wander around the screen
 * 
 * @author cz39
 * @author ker7
 */
public class WanderStrategy implements IUpdateStrategy {
	private final int total = 20;
	private int cnt = total;

	/**
	 * Random change the velocity of the ball.
	 */
	@Override
	public void updateState(Ball ball) {
		cnt--;
		if (cnt == 0) {
			ball.setVelocity(Randomizer.singleton.randomVel(new Rectangle(0, 0, 30, 30)));
			cnt = total;
		}
	}
}