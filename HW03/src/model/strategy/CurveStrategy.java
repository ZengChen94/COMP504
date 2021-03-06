package model.strategy;

import java.awt.Point;

import model.Ball;
import util.Randomizer;

/**
 * Strategy for Curve balls (like spin ball but isn't a sinusodal continues curve)
 * 
 * @author cz39
 * @author ker7
 */
public class CurveStrategy implements IUpdateStrategy {
	double theta = Randomizer.singleton.randomDouble(-Math.PI / 20, Math.PI / 20);

	/**
	 * Change the velocity of the ball according to angle theta.
	 */
	@Override
	public void updateState(Ball ball) {
		Point velocity_old = ball.getVelocity();
		Point velocity_new = new Point();
		velocity_new.x = (int) Math.round(velocity_old.x * Math.cos(theta) - velocity_old.y * Math.sin(theta));
		velocity_new.y = (int) Math.round(velocity_old.y * Math.cos(theta) + velocity_old.x * Math.sin(theta));
		ball.setVelocity(velocity_new);
	}
}
