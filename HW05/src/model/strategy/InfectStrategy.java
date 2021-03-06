package model.strategy;

import java.awt.Color;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import util.IDispatcher;

/**
 * The InfectStrategy, every time it interact, the target ball's color will be the same as context 
 * @author Ye Wang
 * @author Chen Zeng
 * @version 1.0
 */

// Or, using lambda expressions:
public class InfectStrategy<TDispMsg> implements IUpdateStrategy<TDispMsg> {

	public void init(Ball context) {
		// no-op
		context.setColor(Color.BLACK);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateState(Ball context, IDispatcher<TDispMsg> dispatcher) {
		// No-op
		dispatcher.dispatch((TDispMsg) new IBallCmd() {
			@Override
			public void apply(Ball target, IDispatcher<IBallCmd> dispatcher) {
				if (context != target) {
					double distance = context.getLocation().distance(target.getLocation());
					if (distance <= (context.getRadius() + target.getRadius())) {
						target.setColor(context.getColor());
					}
				}
			}
		});
	}
}
