package strategy;

import model.CBall;
import model.IUpdateStrategy;
import util.Dispatcher;
import util.Randomizer;

/**
 * The ColorBall strategy 
 * @author Yue Pan
 * @author Chen Zeng
 * @version 1.0
 */
public class ColorStrategy implements IUpdateStrategy {

	/**
	 * The method is to change the color of the ColorChangingBall
	
	 */
	@Override
	public void updateState(CBall ball, Dispatcher dispatcher) {

		int i = 10;
		while (true) {
			ball.setColor(Randomizer.Singleton.randomColor());
			i--;
			if (i == 0) {
				break;
			}
		}
		i = 10;
	}

}
