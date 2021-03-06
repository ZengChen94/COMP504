package model.strategy;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import model.MultiInteractStrategy;
import util.IDispatcher;

/**
 * The KillStrategy, every time it interact, the target ball will disappear 
 * @author Ye Wang
 * @author Chen Zeng
 * @version 1.0
 */

// Or, using lambda expressions:
public class KillStrategy<TDispMsg> implements IUpdateStrategy<TDispMsg> {

	public void init(Ball context) {
		context.setInteractStrategy(new MultiInteractStrategy(context.getInteractStrategy(),
				(contextBall, targetBall, disp) -> disp.deleteObserver(targetBall)));
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
					if (distance <= (context.getRadius() + target.getRadius()))
						dispatcher.deleteObserver(target);
				}
			}
		});
	}
}
