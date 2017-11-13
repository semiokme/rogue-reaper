package com.gamecodeschool.brickbreakertutorial;
import android.graphics.RectF;
// paddle type that is a shield
// should be widrr and curved so bounces oddly
// reflect enemy attacks later

public class Shield extends Paddle
{
	
	Shield(int screenX, int screenY)
	{
		super(screenX,screenY);
		{
			// bigger
			length = 250;
			height = 40;
			//TODO need to drawarc using a starting angle and
			//a rectf.  should use circle collision
			//once it collides, tanget of radius to colliaion point
			//xan be reflection axis  
			
			// start at center
			x = screenX / 2;
			y = screenY - 20;

			rect = new RectF(x, y ,x+length, y+height);

			// how fast is the paddle in pixels per second?
			paddleSpeed = 350;

		}
	}
}
