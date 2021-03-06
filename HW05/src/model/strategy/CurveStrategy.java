package model.strategy;

import java.awt.Point;

import model.Ball;
import model.IUpdateStrategy;
import util.IDispatcher;

/**
 * The CurveBall strategy 
 * @author Ye Wang
 * @author Chen Zeng
 * @version 1.0
 */
public class CurveStrategy<TDispMsg> implements IUpdateStrategy<TDispMsg> {
	/**
	 * StraightStrategy does nothing special update.
	 */
	@Override
	public void updateState(Ball context, IDispatcher<TDispMsg> dispatcher) {
		double theta = Math.PI / 20;
		Point vel = context.getVel();
		int Orix = vel.x;
		int Oriy = vel.y;
		vel.x = (int) Math.round((Math.cos(theta) * Orix - Math.sin(theta) * Oriy));
		vel.y = (int) Math.round(Math.cos(theta) * Oriy + Math.sin(theta) * Orix);
	}

	@Override
	public void init(Ball context) {
		// TODO Auto-generated method stub

	}
}
