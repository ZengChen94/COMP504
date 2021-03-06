package view;

import java.awt.Graphics;

/**
 * Interface that goes from the model to the view that enables the model to talk to the view
 * @author Ye Wang
 * @author Chen Zeng
 * @version 1.0
 * 
 */
public interface IModelUpdateAdapter {

	/**
	 * This method to paint all the balls on canvas
	 * @param g The graphics object g
	 */
	public void paint(Graphics g);

	/**
	 * No-op singleton implementation of IView2ModelAdapter 
	 * See the web page on the Null Object Design Pattern at link http://cnx.org/content/m17227/latest/
	 */
	public static final IModelUpdateAdapter NULL_OBJECT = new IModelUpdateAdapter() {
		public void paint(Graphics g) {
		}
	};

}
