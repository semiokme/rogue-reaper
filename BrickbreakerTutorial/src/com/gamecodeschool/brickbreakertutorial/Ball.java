package com.gamecodeschool.brickbreakertutorial;

import java.util.Random;

import android.graphics.RectF;

public class Ball {
	RectF rect;
	RectF prev;
	float xVelocity;
	float yVelocity;
	float ballWidth = 10;
	float ballHeight = 10;
	int attack = 1;
	
	public Ball(int screenX, int screenY){
		
		//Star the ball moving upwards @ 100px/s
		xVelocity = 200;
		yVelocity = -400;
		
		//place the ball in the center of the screen at bottom
		// Make it 10px by 10px square
		
		rect = new RectF();
	}
	
	public RectF getRect(){
		return rect;
	}
	
	public void update(long fps){
		prev = new RectF(rect);
		rect.left = rect.left + (xVelocity / fps);
		rect.top = rect.top + (yVelocity / fps);
		rect.right = rect.left + ballWidth;
		rect.bottom = rect.top - ballHeight;
	}
	
	public void reverseYVelocity(){
		yVelocity = -yVelocity;
	}
	
	public void reverseXVelocity(){
		xVelocity = -xVelocity;
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
		rect.left = x / 2;
		rect.top = y- 20;
		rect.right = x / 2 + ballWidth;
		rect.bottom = y - 20 - ballHeight;
	}
	
	public int getAttack(){
		return attack;
	}
	public RectF getPrev(){
		return prev;
	}
	
	// did the ball hit the rivht sidr of the brick
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
