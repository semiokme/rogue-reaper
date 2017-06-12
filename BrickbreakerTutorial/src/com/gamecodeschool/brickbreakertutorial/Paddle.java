package com.gamecodeschool.brickbreakertutorial;
import android.graphics.RectF;

public class Paddle {
	//RectF is an object that holds 4 coordinates
	private RectF rect;
	
	// length and height
	private float length;
	private float height;
	
	// far left is x, top is y
	private float x;
	private float y;
	
	// holds pixels/sec speed
	private float paddleSpeed;
	
	// which ways can the paddle move?
	public final int STOPPED = 0;
	public final int LEFT = 1;
	public final int RIGHT = 2;
	
	// is it moving and which way?
	private int paddleMoving = STOPPED;
	
	// constructor, takes width and height as args
	public Paddle(int screenX, int screenY) {
		// 130 pixels by 20 pixels
		length = 130;
		height = 20;
		
		// start at center
		x = screenX / 2;
		y = screenY - 20;
		
		rect = new RectF(x, y ,x+length, y+height);
		
		// how fast is the paddle in pixels per second?
		paddleSpeed = 350;
		
	}
	
	// getter to make RectF avail 
	public RectF getRect() {
		return rect;
	}
	
	// change movement state
	public void setMovementState(int state) {
		paddleMoving = state;
	}
	
	// called from update in BreakoutView
	// determines if the paddle needs to move and changes coords in rect
	public void update(long fps) {
		if(paddleMoving == LEFT) {
			x = x - paddleSpeed / fps;
		}
		
		if(paddleMoving == RIGHT) {
			x = x + paddleSpeed / fps;
		}
		
		rect.left = x;
		rect.right = x+length;
	}
	
} //end Paddle class
