package com.gamecodeschool.brickbreakertutorial;

import android.graphics.*;
import java.util.*;







public class Monster extends Brick
{
	private int speed;
	private int moveRate;
	
	public Monster(int row, int column, int width, int height){
		super(row,column,width,height);
		
		speed = 150;
		moveRate = 25;
	}
	private int moveDirection(int moveRate){
		Random r = new Random();
		if(r.nextInt(100) < moveRate){
			return r.nextInt(4)+1;
		}
		return 0;
	}
	public void update(long fps){
		float shift = speed / fps;
		
		switch(moveDirection(moveRate)){
			
			case 4:
				moveVert(-shift); //up
				break;
			case 3:
				moveHoriz(-shift);//left
				break;
			case 2:
				moveVert(shift); //down
				break;
			case 1:
				moveHoriz(shift); //right
				break;
			default:
	
		}
		
		
	}
	
	private void moveVert(float shift){
		rect.top = rect.top + shift;
		rect.bottom = rect.bottom + shift;
	}
	
	private void moveHoriz(float shift){
		rect.left = rect.left + shift;
		rect.right = rect.right + shift;
	}
}
