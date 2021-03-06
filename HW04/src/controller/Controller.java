package controller;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;

import model.BallModel;
import model.IModel2ViewAdapter;
import model.IPaintStrategyFac;
import model.IStrategyFac;
import view.BallGUI;
import view.IModelControlAdapter;
import view.IModelUpdateAdapter;

/**
 * 
 * MVC Controller for the app
 * Contain the main() method. 
 * @author Yue Pan
 * @author Chen Zeng
 * @version 1.0
 *
 */
public class Controller {

	/**
	 * The View of this Controller
	 */
	private BallGUI<IStrategyFac, IPaintStrategyFac> _view;

	/**
	 * The Model of this Controller.
	 * link: https://www.clear.rice.edu/comp310/JavaResources/HowTo/easyMVC.html
	 * https://www.clear.rice.edu/comp504/f17/lectures/lec10/
	 */
	private BallModel _model;

	/**
	 * The Controller constructor 
	 */
	public Controller() {
		_model = new BallModel(new IModel2ViewAdapter() {
			@Override
			public Component getCanvas() {
				return _view.getCanvas();
			}

			@Override
			public void repaint() {
				_view.update();
			}
		});

		/*
		 * Initial the view
		 */
		_view = new BallGUI<IStrategyFac, IPaintStrategyFac>(
				new IModelControlAdapter<IStrategyFac, IPaintStrategyFac>() {

					/**
					 * Method to load ball and paint in a given strategy.
					 */
					@Override
					public void loadBall(String strategy, String paint) {
						_model.loadBall(_model.loadStrategy(strategy), _model.loadPaint(paint));
					}

					/**
					 * Method to clear all balls from the canvas.
					 */
					@Override
					public void clear() {
						_model.clear();
					}

					@Override
					/**
					 * https://www.clear.rice.edu/comp504/f17/lectures/lec10/
					 * Returns an IStrategyFac that can instantiate the strategy specified
					 * by classname. Returns null if classname is null. The toString() of
					 * the returned factory is the classname.
					 * @param classname  Shortened name of desired strategy 
					 * @return A factory to make that strategy
					 */
					public IStrategyFac addStrategy(String classname) {
						return _model.makeStrategyFac(classname);
					}

					@Override
					/**
					 * Add a ball to the system with a strategy as given by the given IStrategyFac
					 * @param selectedItem The fully qualified name of the desired strategy.
					 */
					public void makeBall(IStrategyFac selectedItem, IPaintStrategyFac selectedPaint) {

						if (null != selectedItem && null != selectedPaint)
							_model.loadBall(selectedItem.make(), selectedPaint.make());
					}

					@Override
					/**
					 * Returns an IStrategyFac that can instantiate a MultiStrategy with the
					 * two strategies made by the two given IStrategyFac objects. Returns
					 * null if either supplied factory is null. The toString() of the
					 * returned factory is the toString()'s of the two given factories,
					 * concatenated with "-".             * 
					 * @param selectedItem1 An IStrategyFac for a strategy
					 * @param selectedItem2 An IStrategyFac for a strategy
					 * @return An IStrategyFac for the composition of the two strategies
					 */
					public IStrategyFac combineStrategies(IStrategyFac selectedItem1, IStrategyFac selectedItem2) {
						return _model.combineStrategyFacs(selectedItem1, selectedItem2);
					}

					@Override
					/**
					 * This method switch the ball strategy.
					 * @param selectedItem
					 */
					public void switchStrategy(IStrategyFac selectedItem) {
						if (null != selectedItem)
							_model.setSwitcherStrategy(selectedItem.make());
					}

					/**
					 * This method make a switch ball 
					 */
					@Override
					public void makeSwitcher(IPaintStrategyFac selectedPaint) {

						_model.loadBall(_model.getSwStrategy(), selectedPaint.make());
					}

					@Override
					public IPaintStrategyFac addPaint(String classname) {
						// TODO Auto-generated method stub
						return _model.makePaint(classname);
					}

				}, new IModelUpdateAdapter() {
					@Override
					/**
					 * Pass the update request to the model.
					 * @param g The Graphics object the balls use to draw themselves.
					 */
					public void paint(Graphics g) {
						_model.update(g);
					}

				});
	}

	/**
	 * Start method of this Controller.
	 */
	public void start() {
		_model.start();
		_view.start();
	}

	/**
	 * Controller Main function
	 * @param args Arguments by default
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Controller controller = new Controller();
					controller.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
