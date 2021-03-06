package strategy;

import model.CBall;
import model.IUpdateStrategy;
import util.Dispatcher;

/**
 * The BreathingBall strategy 
 * @author Yue Pan
 * @author Chen Zeng
 * @version 1.0
 */
public class BreathingStrategy implements IUpdateStrategy {

	/**
	 * The scale for changing rad.
	 */
	private double scale = 1;
	private double angle = 0;

	/* (non-Javadoc)
	 * @see model.IUpdateStrategy#updateState(model.CBall, util.Dispatcher)
	 */
	@SuppressWarnings("javadoc")
	@Override
	public void updateState(CBall ball, Dispatcher dispatcher) {
		scale = 1 + Math.sin(angle);
		angle += 0.5;
		ball.setRadius((int) (Math.round(10 * scale)));
	}

}
