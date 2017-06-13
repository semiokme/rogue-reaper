package com.gamecodeschool.brickbreakertutorial;

import java.util.Random;

import android.graphics.RectF;

public class Ball {
	RectF rect;
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
		int answer = generator.nextInt(2);
		
		if(answer == 0){
			reverseXVelocity();
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

}
