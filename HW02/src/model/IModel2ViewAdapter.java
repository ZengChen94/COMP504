package model;

import java.awt.Component;

/**
 * Interface that goes from the model to the view that enables the model to talk to the view
 * 
 * @author Chen Zeng (cz39)
 * @author Rong Hu (rh41)
 * @version 20170908
 * @since 20170908
 */
public interface IModel2ViewAdapter {
	/**
	 * The method that gets the canvas
	 */
	public Component getCanvas();

	/**
	 * The method that tells the view to update
	 */
	public void repaint();

	/**
	 * No-op "null" adapter
	 * See the web page on the Null Object Design Pattern at http://cnx.org/content/m17227/latest/
	 */
	public static final IModel2ViewAdapter NULL_OBJECT = new IModel2ViewAdapter() {
		public Component getCanvas() {
			return null;
		}

		public void repaint() {
		}
	};
}
