package com.gamecodeschool.brickbreakertutorial;

import android.graphics.RectF;
import android.graphics.Color;

public class Brick {

	private RectF rect;
	private boolean isVisible;
	private int hitPoints;
	private int color;
	
	public Brick(int row, int column, int width, int height){
		isVisible = true;
		
		int padding = 1;
		
		hitPoints = 3;
		color = Color.YELLOW;

		rect = new RectF(column * width + padding,
				row * height + padding,
				column * width + width - padding,
				row * height + height - padding);
	}
	

	
	public RectF getRect(){
		return this.rect;
	}
	
	public void setInvisible(){
		isVisible = false;
	}
	
	public boolean getVisibility(){
		return isVisible;
	}
	
	public int getHitPoints(){
		return hitPoints;
	}
	
	public int getColor(){
		return color;
	}
	
	// changes color based on hitPoints value
	private void setColor(){
		if(hitPoints == 2)
			color = Color.BLACK;
		if(hitPoints == 1)
			color = Color.RED;
		if(hitPoints <= 0)
			setInvisible();
	}
	
	// reduces HP and changes color
	public void lowerHitPoints(int damage){
		hitPoints = hitPoints-damage;
		setColor();
	}
	
}
