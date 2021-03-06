package model.paint.strategy;

import java.awt.Graphics;
import java.awt.Graphics2D;

import model.CBall;
import model.IPaintStrategy;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.Shape;

/**
 * Paint strategy that paints a filled circle with the Ball's radius. 
 * This functionality is duplicated by the EllipsePaintStrategy. 
 * The class demonstrates a direct implementation of IPaintStrategy.
 * @author Yue Pan
 * @author Chen Zeng
 * @version 1.0
 */
public class BallPaintStrategy implements IPaintStrategy {

	/**
	 * Unit sized circle used as a prototype
	 */
	private Ellipse2D.Double ball;

	/**
	 * default constructor
	 */
	public BallPaintStrategy() {
		ball = new Ellipse2D.Double(-1, -1, 2, 2);
	}

	/**
	 * @param at An affineTransform instance
	 * @param x x-coordinate of the center
	 * @param y y-coordinate of the center
	 * @param width width of the ball
	 * @param height height of the ball
	 */
	public BallPaintStrategy(AffineTransform at, double x, double y, double width, double height) {

		ball.setFrame(x - width / 2, y - width / 2, width, height);
	}

	/* (non-Javadoc)
	 * @see model.IPaintStrategy#init(model.CBall)
	 */
	@Override
	public void init(CBall context) {

	}

	/* (non-Javadoc)
	 * @see model.IPaintStrategy#paint(java.awt.Graphics, model.CBall)
	 */
	@Override
	public void paint(Graphics g, CBall host) {
		g.setColor(host.getColor());
		int x = (int) host.getLocation().getX();
		int y = (int) host.getLocation().getY();
		int r = host.getRadius();
		g.fillOval(x - r, y - r, 2 * r, 2 * r);
	}

	/**
	 * the paintXfrm of ball, could be invoked by other strategies
	 * @param g
	 * @param host
	 * @param at
	 */
	public void paintXfrm(Graphics g, CBall host, AffineTransform at) {
		int x = (int) host.getLocation().getX();
		int y = (int) host.getLocation().getY();
		int r = host.getRadius();
		Shape shape = new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
		((Graphics2D) g).fill(at.createTransformedShape(shape));
	}
}
