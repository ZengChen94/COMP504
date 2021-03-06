package balls;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

/**
 * 
 * The ABall is the abstract class of specific kind of balls.
 * 
 * @author Chen Zeng (cz39)
 * @author Rong Hu (rh41)
 * @version 20170908
 * @since 20170908
 *
 */

public abstract class ABall implements Observer {
	/**
	 * Location of the ball.
	 */
	protected Point location;

	/**
	 * Radius of the ball.
	 */
	protected int radius;

	/**
	 * Velocity of the ball.
	 */
	protected Point velocity;

	/**
	 * Color of the ball.
	 */
	protected Color color;

	/**
	 * Canvas of the GUI.
	 */
	protected Component canvas;

	/**
	 * Initial the ABall.
	 * @param location Location of the ball.
	 * @param radius Radius of the ball.
	 * @param velocity Velocity of the ball.
	 * @param Color of the ball.
	 * @param Canvas of the GUI.
	 */
	public ABall(Point location, int radius, Point velocity, Color color, Component canvas) {
		set_location(location);
		set_radius(radius);
		set_velocity(velocity);
		set_color(color);
		set_canvas(canvas);
	}

	/**
	 * Set the location of the ball
	 * @param location Location of the ball.
	 */
	public void set_location(Point location) {
		this.location = location;
	}

	/**
	 * Set the radius of the ball
	 * @param radius Radius of the ball.
	 */
	public void set_radius(int radius) {
		this.radius = radius;
	}

	/**
	 * Set the velocity of the ball
	 * @param velocity Velocity of the ball.
	 */
	public void set_velocity(Point velocity) {
		this.velocity = velocity;
	}

	/**
	 * Set the color of the ball
	 * @param color Color of the ball.
	 */
	public void set_color(Color color) {
		this.color = color;
	}

	/**
	 * Set the canvas of the GUI
	 * @param canvas Canvas of the GUI.
	 */
	public void set_canvas(Component canvas) {
		this.canvas = canvas;
	}

	/**
	 * Get the location of the ball
	 * @return location Location of the ball.
	 */
	public Point get_location() {
		return this.location;
	}

	/**
	 * Get the radius of the ball
	 * @return radius Radius of the ball.
	 */
	public int get_radius() {
		return this.radius;
	}

	/**
	 * Get the velocity of the ball
	 * @return velocity Velocity of the ball.
	 */
	public Point get_velocity() {
		return this.velocity;
	}

	/**
	 * Get the color of the ball
	 * @return color Color of the ball.
	 */
	public Color get_color() {
		return this.color;
	}

	/**
	 * Get the canvas of the GUI
	 * @return canvas Canvas of the GUI.
	 */
	public Component get_canvas() {
		return this.canvas;
	}

	/**
	 * Update the state of the ball.
	 */
	public void updatingState() {
		specifyBall();
		moving();
		bouncing();
	}

	/**
	 * Used for behavior of subclass of ABall.
	 */
	public abstract void specifyBall();

	/**
	 * Paint the canvas.
	 * @param g Graphics g
	 */
	public void paint(Graphics g) {
		g.setColor(get_color());
		g.fillOval(location.x - radius, location.y - radius, radius * 2, radius * 2);
	}

	/**
	 * Move the ball.
	 */
	public void moving() {
		location.x += velocity.x;
		location.y += velocity.y;
	}

	/**
	 * Bounce the ball.
	 */
	public void bouncing() {
		while (this.location.x - this.radius < 0 || this.location.x + this.radius > this.canvas.getWidth() || this.location.y - this.radius < 0 || this.location.y + this.radius > this.canvas.getHeight()) {
			//hit left
			if (this.location.x - this.radius < 0) {
				this.get_velocity().x = -this.get_velocity().x;
				/*The position of the ball (perpendicular to the wall) where it would just barely contact the wall, 
				is the average of (i.e. halfway between) the current perpendicular position and the position of where the ball should be, 
				had it actually bounced off the wall. */
				this.location.x = 2 * this.radius - this.location.x;
			}
			//hit right
			if (this.location.x + this.radius > this.canvas.getWidth()) {
				this.get_velocity().x = -this.get_velocity().x;
				this.location.x = 2 * (this.canvas.getWidth() - this.radius) - this.location.x;
			}
			//hit top
			if (this.location.y - this.radius < 0) {
				this.get_velocity().y = -this.get_velocity().y;
				this.location.y = 2 * this.radius - this.location.y;
			}
			//hit bottom
			if (this.location.y + this.radius > this.canvas.getHeight()) {
				this.get_velocity().y = -this.get_velocity().y;
				this.location.y = 2 * (this.canvas.getHeight() - this.radius) - this.location.y;
			}
		}
	}


	/**
	 * Override the method of Observer.
	 * @param arg Graphics.
	 */
	@Override
	public void update(Observable o, Object arg) {
		updatingState();
		paint((Graphics) arg);
	}
}
