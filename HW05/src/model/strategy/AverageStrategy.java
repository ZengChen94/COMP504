package model.strategy;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import util.IDispatcher;

/**
 * The AverageStrategy, every time it interact, their mass of all be average of their mass sum 
 * @author Ye Wang
 * @author Chen Zeng
 * @version 1.0
 */

// Or, using lambda expressions:
public class AverageStrategy<TDispMsg> implements IUpdateStrategy<TDispMsg> {

	public void init(Ball context) {
		//no-op
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateState(Ball context, IDispatcher<TDispMsg> dispatcher) {
		// No-op
		dispatcher.dispatch((TDispMsg) new IBallCmd() {
			@Override
			public void apply(Ball tarfet, IDispatcher<IBallCmd> dispatcher) {
				if (context != tarfet) {
					double distance = context.getLocation().distance(tarfet.getLocation());
					if (distance <= (context.getRadius() + tarfet.getRadius())) {
						double contextMass = context.getRadius() * context.getRadius();
						double otherMass = tarfet.getRadius() * tarfet.getRadius();
						double averageMass = (contextMass + otherMass) / 2;
						context.setRadius((int) Math.sqrt(averageMass));
						tarfet.setRadius((int) Math.sqrt(averageMass));
					}
				}
			}
		});
	}
}
