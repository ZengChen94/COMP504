package model.strategy;

import model.Ball;
import model.IUpdateStrategy;
import util.IDispatcher;
import util.Randomizer;

/**
 * The ColorBall strategy 
 * @author Ye Wang
 * @author Chen Zeng
 * @version 1.0
 */
public class ColorStrategy<TDispMsg> implements IUpdateStrategy<TDispMsg> {

	/**
	 * The method is to change the color of the ColorChangingBall
	
	 */
	@Override
	public void updateState(Ball context, IDispatcher<TDispMsg> dispatcher) {

		int i = 10;
		while (true) {
			context.setColor(Randomizer.Singleton.randomColor());
			i--;
			if (i == 0) {
				break;
			}
		}
		i = 10;
	}

	@Override
	public void init(Ball context) {
		// TODO Auto-generated method stub

	}

}
