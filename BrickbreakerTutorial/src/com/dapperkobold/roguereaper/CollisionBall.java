// new ball class will eventually extend collidibles.  First needs to work with Vectors.  

package com.dapperkobold.roguereaper;

import java.util.Random;

import android.graphics.RectF;

public class CollisionBall {
	RectF rect;
	RectF prev;
	float xVelocity; // dep
	float yVelocity; // dep
	float ballWidth = 10;
	float ballHeight = 10;
	int attack = 1;
	PVector position = new PVector(0,0);
	PVector velocity = new PVector(0,0);
	
	public CollisionBall(int screenX, int screenY){
		
		//Star the ball moving upwards @ 100px/s
		//xVelocity = 200;
		//yVelocity = -400;
		
		
		//place the ball in the center of the screen at bottom
		// Make it 10px by 10px square
		
		rect = new RectF();
	}
	
	public RectF getRect(){
		return rect;
	}
	
	public void update(long fps){
		// takes current rect, moves into prev, and updates current.
		prev = new RectF(rect);
/*		rect.left = rect.left + (xVelocity / fps);
		rect.top = rect.top + (yVelocity / fps);
		rect.right = rect.left + ballWidth;
		rect.bottom = rect.top - ballHeight; */

		// I think this should move the ball based on the velocity vector.  
		rect.offset((velocity.getX()/fps), (velocity.getY()/fps));

	}
	
	public void reverseYVelocity(){
//		yVelocity = -yVelocity;
		velocity.setY(-velocity.getY());
	}
	
	public void reverseXVelocity(){
//		xVelocity = -xVelocity;
		velocity.setX(-velocity.getX());
	}
	
	public void setRandomXVelocity(){
		Random generator = new Random();
		int answer = generator.nextInt(10);
		
		if(answer == 0){
			reverseXVelocity();
		}
		if(answer > 0 && answer <= 2){
			xVelocity =  xVelocity - 40;
		}
		if(answer > 2 && answer <= 7){
			xVelocity++;
		}
		if(answer > 7 && answer <= 9){
			xVelocity = xVelocity +50;
		}
	}
	
	public void clearObstacleY(float y){
		rect.bottom = y;
		rect.top = y-ballHeight;
	}
	
	public void clearObstacleX(float x){
		rect.left = x;
		rect.right = x + ballWidth;
	}
	
	public void reset(int x, int y){
		// rect is legacy, still using for collision
		rect.left = x / 2;
		rect.top = y- 20;
		rect.right = x / 2 + ballWidth;
		rect.bottom = y - 20 - ballHeight;
	//NOTE: COULD RECT.CENTER methods build position vector or vice-versa?
		
		// velocity is hard coded starting velocity
		// position is mid screen, 20 px up.  
		velocity.setX(200);
		velocity.setY(-400);
		position.setX(x/2);
		position.setY(y-20);
		
	}
	
	/* This whole thing doesn't work, need to read more and draw up a better outline plan.  
	public void OnCollision(PVector n) {
		// use the normal of the thing that was hit to determine the new velocity vector of the ball
		velocity = velocity - 2 * PVector.mult(n, velocity);
	}
	/*As far as the actual math is concerned, if you need the normal of two vectors
	 * , calculate a cross product, if you need the normal of one 2D vector, use a negative reciprocal */
	
	
	public int getAttack(){
		return attack;
	}
	public RectF getPrev(){
		return prev;
	}
	
	// did the ball hit the right side of the brick
	public boolean hitRight(RectF brick){
		return (brick.right < prev.left) &&
		(brick.right >= rect.left);
		
	}
	public boolean hitLeft(RectF brick){
		return (brick.left > prev.right) &&
			(brick.left <= rect.right);

	}
	public boolean hitBottom(RectF brick){
		return (brick.bottom < prev.top) &&
			(brick.bottom >= rect.top);

	}
	public boolean hitTop(RectF brick){
		return (brick.top > prev.bottom) &&
			(brick.top <= rect.bottom);

	}

}
