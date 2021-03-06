package balls;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;

import util.Randomizer;

/**
 * 
 * The WanderAndColorChangingBall is a subclass of ABall.
 * 
 * @author Chen Zeng (cz39)
 * @author Rong Hu (rh41)
 * @version 20170908
 * @since 20170908
 *
 */

public class WanderAndColorChangingBall extends ABall {
	private int cnt = 20;

	/**
	 * Constructor.
	 * @param location The location of the ball.
	 * @param radius The radius of the ball.
	 * @param velocity The velocity of the ball.
	 * @param color The color of the ball.
	 * @param canvas The canvas of GUI.
	 */
	public WanderAndColorChangingBall(Point location, int radius, Point velocity, Color color, Component canvas) {
		super(location, radius, velocity, color, canvas);
	}

	/**
	 * Random change the color and velocity of the ball.
	 */
	@Override
	public void specifyBall() {
		cnt--;
		if (cnt == 0) {
			set_color(Randomizer.Singleton.randomColor());
			set_velocity(Randomizer.Singleton.randomVel(new Rectangle(0, 0, 30, 30)));
			cnt = 20;
		}
	}
}
