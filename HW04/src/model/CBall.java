package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import util.Dispatcher;

/**
 * The concrete ball class
 * 
 * @author Yue Pan
 * @author Chen Zeng
 * @version 1.0
 * 
 */
public class CBall implements Observer {

	/**
	 * The location of this ball, which is the center of the ball
	 */
	Point loc;

	/**
	 * The radius of the ball
	 */
	int rad;

	/**
	 * The color of the ball
	 */
	Color color;

	/**
	 * The vel of the ball
	 */
	Point vel;

	/**
	 * The canvas
	 */
	Component canvas;

	/**
	 * The new strategy of the ball.
	 */
	private IUpdateStrategy strategy;

	/**
	 * The paint strategy of this ball.
	 */

	private IPaintStrategy ipaint;

	/**
	 * Constructor for this Ball.
	 * @param _iniLoc The location of this Ball. The location represents the center point.
	 * @param _iniRad The radius of this Ball.
	 * @param _iniVel The velocity of this Ball.
	 * @param _iniColor The Color of this Ball.
	 * @param _canvas The Canvas related to this Ball
	 * @param _iUpdateStrategy The update strategy of the Ball.
	 * @param _ipaint The paint strategy of the Ball.
	 */
	public CBall(Point _iniLoc, int _iniRad, Point _iniVel, Color _iniColor, Component _canvas,
			IUpdateStrategy _iUpdateStrategy, IPaintStrategy _ipaint) {
		this.loc = _iniLoc;
		this.rad = _iniRad;
		this.vel = _iniVel;
		this.color = _iniColor;
		this.canvas = _canvas;
		this.strategy = _iUpdateStrategy;
		setPaint(_ipaint);
	}

	/**
	 *The bounce method when the ball reach the edge
	 */
	void bounce() {
		/*
		 * The ball reach the left
		 */
		if (loc.x - rad < 0) {
			vel.x *= -1;
			loc.x = rad * 2 - loc.x;
		}

		if (loc.y - rad < 0) {
			vel.y *= -1;
			loc.y = rad * 2 - loc.y;
		}

		if (loc.x + rad > canvas.getWidth()) {
			vel.x *= -1;
			loc.x = 2 * (canvas.getWidth() - rad) - loc.x;
		}

		if (loc.y + rad > canvas.getHeight()) {
			vel.y *= -1;
			loc.y = 2 * (canvas.getHeight() - rad) - loc.y;
		}
	}

	/**
	 * Update the properties of this ABall every time slice.
	 */
	@Override
	public void update(Observable o, Object cmd) {
		((IBallCmd) cmd).apply(this, (Dispatcher) o);
	}

	/**
	 * Update the state of the ball.   Delegates to the update strategy.
	 * @param dispatcher The Dispatcher that sent the command that is calling this method.
	 */
	public void updateState(Dispatcher dispatcher) {
		strategy.updateState(this, dispatcher); // update this ball's state using the strategy.		
	}

	/**
	 * The movement of the ball
	 */
	void movingball() {
		move();
		bounce();
	}

	/**
	 * Change the loc of the ball
	 */
	void move() {
		loc.x += vel.x;
		loc.y += vel.y;
	}

	/**
	 * Paint this Ball.
	 * @param g The Graphics object to paint.
	 */
	public void paint(Graphics g) {
		ipaint.paint(g, this);
	}

	/**
	 * set the ball color
	 * @param color 
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Set the ball radius
	 * @param radius The radius of this Ball.
	 */
	public void setRadius(int radius) {
		this.rad = radius;
	}

	/**
	 * Get ball velocity which is used for update velocity
	 * @return velocity
	 */
	public Point getVel() {
		return this.vel;
	}

	/**
	 * @return rad
	 */
	public int getRadius() {
		// TODO Auto-generated method stub
		return this.rad;
	}

	/**
	 * @return location
	 */
	public Point getLocation() {
		// TODO Auto-generated method stub
		return this.loc;
	}

	/**
	 * @return color
	 */
	public Color getColor() {
		// TODO Auto-generated method stub
		return this.color;
	}

	/**
	 * @return paint strategy
	 */
	public IPaintStrategy getPaint() {
		return ipaint;
	}

	/**
	 * Set the paint strategy of this Ball.
	 * @param _ipaint The paint strategy of this ball.
	 */
	public void setPaint(IPaintStrategy _ipaint) {
		this.ipaint = _ipaint;
		ipaint.init(this);
	}

	/**
	 * @return canvas to paint
	 */
	public Component getCanvas() {
		// TODO Auto-generated method stub
		return canvas;
	}
}
