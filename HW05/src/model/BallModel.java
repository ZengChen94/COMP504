package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.Timer;

import model.strategy.SwitcherStrategy;
import util.IDispatcher;
import util.Randomizer;
import util.SetDispatcherSequential;

/**
 * The Ball model class
 * @author Ye Wang
 * @author Chen Zeng
 * @version 1.0
 *
 */
public class BallModel {
	/**
	 * The model to view adapter
	 * link: https://canvas.rice.edu/courses/6768/modules/items/82366
	 * The NULL_OBJECT insures that the adapter is always valid
	 */
	private IModel2ViewAdapter _model2ViewApt = IModel2ViewAdapter.NULL_OBJECT;

	private IDispatcher<IBallCmd> myDispatcher = new SetDispatcherSequential<IBallCmd>(); // or any other implementation

	//	/**
	//	 * The Dispatcher of this BallModel. Add and remove balls.
	//	 */
	//	private Dispatcher myDispatcher = new Dispatcher();

	/**
	 * BallModel Constructor 
	 * @param model2ViewApt is the adapter from model to view
	 */

	public BallModel(IModel2ViewAdapter model2ViewApt) {
		this._model2ViewApt = model2ViewApt;
	}

	/**
	 * The update time interval
	 */
	private int _timeSlice = 50;

	/**
	 * The timer
	 * The timer "ticks" by calling this method every _timeslice milliseconds
	 */
	private Timer _Timer = new Timer(_timeSlice, (e) -> _model2ViewApt.repaint());

	/**
	 * The timer that controls when the balls are periodically updated.
	 */

	/**
	 * Start the timer
	 */
	public void start() {
		_Timer.start();

	}

	/**
	 * New strategy switcher objecj
	 */
	private SwitcherStrategy<IBallCmd> switcher = new SwitcherStrategy<IBallCmd>();

	/**
	 * The following function in the loadBall(String) reference to
	 * "https://www.clear.rice.edu/comp310/JavaResources/dynamic_class_load.html", 
	 * 
	 * The method creates an instance of an ABall, given a fully qualified class name (package.classname) of
	 * a subclass of ABall.
	 * The method assumes that there is only one constructor for the supplied class that has the same *number* of
	 * input parameters as specified in the args array and that it conforms to a specific
	 * signature, i.e. specific order and types of input parameters in the args array.
	 * @param className A string that is the fully qualified class name of the desired object
	 * @return An IUpdateStrategy of the supplied classname. 
	 */
	@SuppressWarnings("unchecked")
	public IUpdateStrategy<IBallCmd> loadStrategy(String className) {
		try {
			//			className = "model." + className;
			Object[] args = new Object[] {};
			java.lang.reflect.Constructor<?> cs[] = Class.forName(className).getConstructors(); // get all the constructors
			java.lang.reflect.Constructor<?> c = null;
			for (int i = 0; i < cs.length; i++) { // find the first constructor with the right number of input parameters
				if (args.length == (cs[i]).getParameterCount()) {
					c = cs[i];
					break;
				}
			}
			return (IUpdateStrategy<IBallCmd>) c.newInstance(args);
		} catch (Exception ex) {
			System.err.println("Class " + className + " failed to load. \nException = \n" + ex);
			ex.printStackTrace(); // print the stack trace to help in debugging
			//			return _errorStrategyFac.make();
			return (IUpdateStrategy<IBallCmd>) _errorStrategyFac.make();
		}
	}

	/**
	 * This method add an ABall instance which loaded by the loadBall 
	 * Send it to the dispatcher
	 * @param ballname is the type of the ball to be created.
	 * @param paint is the type of the paint object to be created 
	 */
	public void loadBall(IUpdateStrategy<IBallCmd> ballname, IPaintStrategy paint) {
		int Radius = Randomizer.Singleton.randomInt(10, 40);
		Color color = Randomizer.Singleton.randomColor();
		Component canvas = _model2ViewApt.getCanvas();
		Point loc = Randomizer.Singleton.randomLoc(canvas.getSize());
		Point vel = Randomizer.Singleton.randomVel(new Rectangle(5, 5, 30, 30));
		Ball ball = new Ball(loc, Radius, vel, color, canvas, ballname, paint);
		ballname.init(ball);
		myDispatcher.addObserver(ball);
	}

	/**
	 * Clear all the balls on pnl
	 */
	public void clear() {
		myDispatcher.deleteObservers();
	}

	/**
	 * This method will update the sprites's painted locations by painting all the sprites
	 * onto the given Graphics object.
	 * @param g The Graphics object from the view's paintComponent() call.
	 */
	public void update(Graphics g) {
		myDispatcher.dispatch(new IBallCmd() {
			public void apply(Ball context, IDispatcher<IBallCmd> disp) {
				context.movingball();
				context.updateState(disp);
				context.getPaint().paint((Graphics) g, context);
			}
		});
	}

	/**
	 * Returns an IStrategyFac that can instantiate the strategy specified by
	 * classname. Returns a factory for a beeping error strategy if classname is null. 
	 * The toString() of the returned factory is the classname.
	 * 
	 * @param classname  Shortened name of desired strategy
	 * @return A factory to make that strategy
	 */
	public IStrategyFac<IBallCmd> makeStrategyFac(final String classname) {
		if (null == classname)
			return _errorStrategyFac;
		return new IStrategyFac<IBallCmd>() {
			/**
			 * Instantiate a strategy corresponding to the given class name.
			 * @return An IUpdateStrategy instance
			 */
			public IUpdateStrategy<IBallCmd> make() {
				return loadStrategy(fixName(classname));
			}

			public String toString() {
				return classname;
			}
		};
	}

	/**
	 * Returns an IStrategyFac that can instantiate a MultiStrategy with the two
	 * strategies made by the two given IStrategyFac objects. Returns null if
	 * either supplied factory is null. The toString() of the returned factory
	 * is the toString()'s of the two given factories, concatenated with "-". 
	 * If either factory is null, then a factory for a beeping error strategy is returned.
	 * 
	 * @param stratFac1 An IStrategyFac for a strategy
	 * @param stratFac2 An IStrategyFac for a strategy
	 * @return An IStrategyFac for the composition of the two strategies
	 */
	public IStrategyFac<IBallCmd> combineStrategyFacs(final IStrategyFac<IBallCmd> stratFac1,
			final IStrategyFac<IBallCmd> stratFac2) {
		if (null == stratFac1 || null == stratFac2)
			return _errorStrategyFac;
		return new IStrategyFac<IBallCmd>() {
			/**
			 * Instantiate a new MultiStrategy with the strategies from the given strategy factories
			 * @return A MultiStrategy instance
			 */
			public IUpdateStrategy<IBallCmd> make() {
				return new MultiStrategy<IBallCmd>(stratFac1.make(), stratFac2.make());
			}

			/**
			 * Return a string that is the toString()'s of the given strategy factories concatenated with a "-"
			 */
			public String toString() {
				return stratFac1.toString() + "-" + stratFac2.toString();
			}
		};
	}

	/**
	 * Private method is used to save the user's typing by converting the abbreviated strategy class name
	 * into it's fully qualified form
	 * @param classname The abbreviated strategy class name.
	 * @return The fully qualified form of strategy name.
	 */
	private String fixName(String classname) {
		return "model.strategy." + classname + "Strategy";
	}

	/**
	 * A factory for a beeping error strategy that beeps the speaker every 25 updates.
	 * Either use the _errorStrategyFac variable directly if you need a factory that makes an error strategy,
	 * or call _errorStrategyFac.make() to create an instance of a beeping error strategy.
	 */
	private IStrategyFac<IBallCmd> _errorStrategyFac = new IStrategyFac<IBallCmd>() {
		@Override
		/**
		 * Make the beeping error strategy
		 * @return  An instance of a beeping error strategy
		 */
		public IUpdateStrategy<IBallCmd> make() {
			return new IUpdateStrategy<IBallCmd>() {
				private int count = 0; // update counter

				/**
				 * Beep the speaker every 25 updates
				 */
				@Override
				public void updateState(Ball context, IDispatcher<IBallCmd> dispacher) {
					if (25 < count++) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						count = 0;
					}
				}

				@Override
				public void init(Ball context) {
					// TODO Auto-generated method stub

				}
			};
		}
	};

	/**
	 * This method return the current switch of this ball
	 * @return switcher is the updated state of this ball
	 */
	public SwitcherStrategy<IBallCmd> getSwStrategy() {
		return switcher;
	}

	/**
	 * Sets the new strategy for the ball
	 * @param Nstrategy Strategy for switcher
	 */
	public void setSwitcherStrategy(IUpdateStrategy<IBallCmd> Nstrategy) {
		switcher.setNew(Nstrategy);
	}

	/**
	 * The following function in the loadBall(String) reference to
	 * "https://www.clear.rice.edu/comp310/JavaResources/dynamic_class_load.html", 
	 * The following method returns an instance of IPaintStrategy, given the fully qualified paint class name of IPaintStrategy
	 * @param classname A string that is the fully qualified class name of the desired object
	 * @return An IPaintStrategy of the supplied classname. 
	 */
	public IPaintStrategy loadPaint(String classname) {
		try {
			java.lang.reflect.Constructor<?> cs = Class.forName(classname).getConstructor(); // get the default constructor
			return (IPaintStrategy) cs.newInstance(); // Call the constructor
		} catch (Exception ex) {
			System.err.println("Paint Strategy " + classname + " failed to load. \nException = \n" + ex);
			ex.printStackTrace(); // print the stack trace to help in debugging.
			return null;
		}

	}

	/**
	 * Returns an IPaintStrategyFac that can instantiate the strategy specified by
	 * classname. Returns a factory for a beeping error strategy if classname is null. 
	 * The toString() of the returned factory is the classname.
	 * 
	 * @param classname  Shortened name of desired strategy
	 * @return A factory to make that paint strategy
	 */
	public IPaintStrategyFac makePaint(final String classname) {
		if (classname == null)
			return _errorPaintStrategyFac;
		return new IPaintStrategyFac() {

			/**
			 * Instantiate a paint strategy corresponding to the give paint name
			 * @return An IPaintStrategy instance
			 */
			public IPaintStrategy make() {
				return loadPaint(fixNamePaint(classname));
			}

			public String toString() {
				return classname;
			}
		};
	}

	/**
	 * A factory for paint NULL_OBJECT error strategy.
	 */
	private IPaintStrategyFac _errorPaintStrategyFac = new IPaintStrategyFac() {
		public IPaintStrategy make() {
			return IPaintStrategy.NULL_OBJECT;
		}
	};

	/**
	 * This method return abbreviated paint strategy classname
	 * into it's fully qualified form
	 * @param classname The abbreviated strategy class name.
	 * @return The fully qualified form of strategy name.
	 */
	private String fixNamePaint(String classname) {
		return "model.paint.strategy." + classname + "PaintStrategy";
	}

}
