package controller;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;

import model.*;
import view.MainAppFrame;
import view.IModelCtrlAdapter;
import view.IModelPaintAdapter;

/**
 * Controller class for managing communications between the view and model
 * 
 * @author cz39
 * @author ker7
 */
public class BallWorldCtrl {

	/**
	 * Model field for the model-to-view adapter to close over
	 */
	private BallWorldModel model;

	/**
	 * view field for the view-to-model adapter to close over
	 */
	private MainAppFrame<IStrategyFac> view;

	/**
	 * Controller constructor builds the system
	 */
	public BallWorldCtrl() {

		/**
		 * Sets the model field
		 * Takes advantage of closure to access the view field from inside the model-to-view adapter
		 */
		model = new BallWorldModel(new IViewAdapter() {

			@Override
			public void update() {
				view.update();

			}

			@Override
			public Component getCanvas() {
				return view.getCanvas();
			}
		});

		/**
		 * Sets the view field
		 * Takes advantage of closure to access the model field from inside the view-to-model adapter
		 */
		view = new MainAppFrame<IStrategyFac>(new IModelPaintAdapter() {
			public void paintBalls(Graphics g) {
				model.update(g);

			}
		}, new IModelCtrlAdapter<IStrategyFac>() {
			public void clearBalls() {
				model.clear_all_balls();
			}

			public void loadBall(IStrategyFac selectedItem) {
				if (null != selectedItem)
					model.dispatch_balls(selectedItem.make());
			}

			@Override
			public IStrategyFac combineStrategies(IStrategyFac item1, IStrategyFac item2) {
				return model.combineStrategyFacs(item1, item2);
			}

			@Override
			public void makeSwitcherBall() {
				model.dispatch_balls(model.getSwitcherStrategy());
			}

			@Override
			public void switchStrategy(IStrategyFac item) {
				model.switchSwitcherStrategy(item.make());

			}

			@Override
			public IStrategyFac addStrategy(String classname) {
				return model.makeStrategyFac(classname);
			}

		});
	}

	/**
	 * Start the model and the view
	 * This will only occur after the model and view have already been instantiated
	 */
	public void start() {
		model.start(); // Makes sense to start the model first most times
		view.start();
	}

	/**
	 * Launch the controller to setup and start the model and view
	 * 
	 * @param args Arguments given by the system or command line.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BallWorldCtrl controller = new BallWorldCtrl(); // instantiate the system
					controller.start(); // start the system
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
