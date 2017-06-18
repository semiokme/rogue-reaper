package com.gamecodeschool.brickbreakertutorial;

import java.util.Random;
import android.graphics.RectF;
import android.graphics.Color;
import android.widget.*;

public class Brick {

	protected RectF rect;
	protected boolean isVisible;
	protected int hitPoints;
	protected int expPoints;
	protected int color;
	
	public Brick(int row, int column, int width, int height){
		isVisible = true;
		
		Random r = new Random();
		
		int padding = 1;
		
		hitPoints = r.nextInt(4);
		expPoints = (1+r.nextInt(4)) * hitPoints;
		setColor();
		//color = Color.YELLOW;

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
	protected void setColor(){
		if(hitPoints == 3)
			color = Color.YELLOW;
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
	
	public int getExp(){
		return expPoints;
	}
}
