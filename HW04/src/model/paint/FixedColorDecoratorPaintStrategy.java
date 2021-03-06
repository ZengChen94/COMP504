package model.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import model.CBall;

/**
 * Decorator paint strategy that decorates another 
 * IPaintStrategy and causes the painted color to always be a specified, fixed color.
 * @author Yue Pan
 * @author Chen Zeng
 * @version 1.0
 */
public class FixedColorDecoratorPaintStrategy extends ADecoratorPaintStrategy {
	/**
	 * The painted color
	 */
	private Color color;

	/**
	 * Constructor that takes the fixed color and the decoree strategy
	 * @param color The fixed color to use
	 * @param decoree The decoree strategy whose color is overridden
	 */
	public FixedColorDecoratorPaintStrategy(Color color, APaintStrategy decoree) {
		super(decoree);
		this.color = color;
	}

	@SuppressWarnings("javadoc")
	public void paintXfrm(Graphics g, CBall host, AffineTransform at) {
		g.setColor(color);
		super.paintXfrm(g, host, at);
	}
}
