package strategy;

import java.awt.Point;

import model.CBall;
import model.IUpdateStrategy;
import util.Dispatcher;

/**
 * The CurveBall strategy 
 * @author Yue Pan
 * @author Chen Zeng
 * @version 1.0
 */
public class CurveStrategy implements IUpdateStrategy {
	/**
	 * StraightStrategy does nothing special update.
	 */
	@Override
	public void updateState(CBall ball, Dispatcher dispatcher) {
		double theta = Math.PI / 20;
		Point vel = ball.getVel();
		int Orix = vel.x;
		int Oriy = vel.y;
		vel.x = (int) Math.round((Math.cos(theta) * Orix - Math.sin(theta) * Oriy));
		vel.y = (int) Math.round(Math.cos(theta) * Oriy + Math.sin(theta) * Orix);
	}
}
