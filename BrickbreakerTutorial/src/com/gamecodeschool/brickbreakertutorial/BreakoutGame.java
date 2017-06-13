/*
 * version 0.06
 * v 0.06 - paddle offscreen bug fixed
 * v 0.05 - github setup
 * v 0.04 - added hp, color change to bricks
 * v 0.03 - created drag controls vs. tap controls
 * v 0.02 - bricks visibility randomized 
 * v 0.01 - runs, basics
 */
package com.gamecodeschool.brickbreakertutorial;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BreakoutGame extends Activity {
	
	// gameView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    
	BreakoutView breakoutView;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Init gameView and set it as the view
		breakoutView = new BreakoutView(this);
		setContentView(breakoutView);
		
	}
	
	// Here is our implementation of GameView
    // It is an inner class.
    // Note how the final closing curly brace }
    // is inside SimpleGameEngine
 
    // Notice we implement runnable so we have
    // A thread and can override the run method.
	class BreakoutView extends SurfaceView implements Runnable {
		
		// this is our thread
		Thread gameThread = null;
		
		 // This is new. We need a SurfaceHolder
        // When we use Paint and Canvas in a thread
        // We will see it in action in the draw method soon.
		SurfaceHolder ourHolder;
		
		// sets if the game is running or not
		//*// volatile mean read/write main mem, not CPU cache.  guarantees state to other threads.  
		volatile boolean playing;
		
		// Paused to start
		boolean paused = true;
		
		// canvas and paint objects
		Canvas canvas;
		Paint paint;
		
		// track framerate
		long fps;
		
		// to calc fps
		private long timeThisFrame;
		
		// screen size in pixels
		int screenX;
		int screenY;
		
		// previous paddle motion X
		float prevX;
		
		// the paddle
		Paddle paddle;
		
		// A ball
		Ball ball;
		
		// Up to 200 bricks
		Brick[] bricks = new Brick[200];
		int numBricks = 0;
		
		// For sound FX
		SoundPool soundPool;
		int beep1ID = -1;
		int beep2ID = -1;
		int beep3ID = -1;
		int loseLifeID = -1;
		int explodeID = -1;
		
		// the score
		int score = 0;
		
		// exp
		int expPoints = 0;
		
		// lives
		int lives = 3;
		
		// on init of gameView, this special constructor runs
		public BreakoutView(Context context) {
			// ask surfaceview to setup the object
			super(context);
			
			// init holder and paint objects
			ourHolder = getHolder();
			paint = new Paint();
			
			// Get a display object to access screen details
			//Display display = getWindowManager().getDefaultDisplay();
			// Load resolution into a Point object
			// Changed the piece here to be compatible with API 8
			
			//Point size = new Point();
			//display.getSize(size);
			DisplayMetrics display = this.getResources().getDisplayMetrics();
	       
	        screenX = display.widthPixels;
	        screenY = display.heightPixels;
			//screenX = size.x;
			//screenY = size.y;
			
	        // create paddle
			paddle = new Paddle(screenX, screenY);
			
			// create ball
			ball = new Ball(screenX, screenY);
			
			//Load sounds
			// This SoundPool is deprecated but don't worry
			
			soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
			
			try{
				// Create objects of the 2 requires classes
				AssetManager assetManager = context.getAssets();
				AssetFileDescriptor descriptor;
				
				// Load our fx in memory ready for use
				descriptor = assetManager.openFd("beep1.ogg");
				beep1ID = soundPool.load(descriptor, 0);
				
				descriptor = assetManager.openFd("beep2.ogg");
				beep2ID = soundPool.load(descriptor, 0);
				
				descriptor = assetManager.openFd("beep3.ogg");
				beep3ID = soundPool.load(descriptor, 0);
				
				descriptor = assetManager.openFd("loseLife.ogg");
				loseLifeID = soundPool.load(descriptor, 0);
				
				descriptor = assetManager.openFd("explode.ogg");
				explodeID = soundPool.load(descriptor, 0);
				
			}catch(IOException e){
				//print error to console
				Log.e("error", "failed to load sound files");
			}
			
			// start
			createBricksAndRestart();
		}
		
		public void createBricksAndRestart(){
			
			// put the ball at the start
			ball.reset(screenX, screenY);
			
			int brickWidth = screenX / 8;
			int brickHeight = screenY / 10;
			
			// bag of bricks
			numBricks = 0;
			Random generator = new Random();
			
			// added a chance for each brick to start invisible
			for(int column = 0; column < 8; column++) {
				for(int row = 0; row < 3; row++){
					bricks[numBricks] = new Brick(row,column,brickWidth,brickHeight);
					if(generator.nextInt(2) == 0)
						bricks[numBricks].setInvisible();
					numBricks++;
				}
			}
			
			// reset score and lives
		if(lives == 0) {
			score = 0;
			lives = 3;
		}
		
		}
		
		@Override
		public void run() {
			while(playing) {
				
				// capture current time in ms in startFrameTime
				long startFrameTime = System.currentTimeMillis();
				
				//Update the frame
				if(!paused) {
					update();
				}
				
				//Draw the frame
				draw();
				
				// Calculate the fps this frame
                // We can then use the result to
                // time animations and more.
				timeThisFrame = System.currentTimeMillis() - startFrameTime;
				if(timeThisFrame >= 1) {
					fps = 1000 / timeThisFrame;
			}
		}
	}
		
	// Everything that needs to be updated goes in here
    // Movement, collision detection etc.
    public void update() {
    	
    	// move the paddle if required
    	paddle.update(fps, screenX);
    	
    	//move ball
    	ball.update(fps);
    	
    	// check collisions
    	//ball v brick
    	for(int i = 0; i < numBricks; i++){
    		if(bricks[i].getVisibility()){
    			if(RectF.intersects(bricks[i].getRect(), ball.getRect())){
    				//bricks[i].setInvisible(); removed in favor of damage
    				bricks[i].lowerHitPoints(ball.getAttack());
    				if(!bricks[i].getVisibility())
    				{
    					soundPool.play(explodeID, 1, 1, 0, 0, 1);
    					score = score+10;
						expPoints = expPoints + bricks[i].getExp();
    				}
    				ball.reverseYVelocity();
    				//ball.clearObstacleY(bricks[i].getRect().top - 12);
    				
    				
    			}
    		}
    	}
    	
    	//ball v paddle
    	if(RectF.intersects(paddle.getRect(), ball.getRect())){
    		ball.setRandomXVelocity();
    		ball.reverseYVelocity();
    		ball.clearObstacleY(paddle.getRect().top - 2);
    		soundPool.play(beep1ID, 1, 1, 0, 0, 1);
    	}
    	
    	//ball vs. bottom
    	if(ball.getRect().bottom > screenY){
    		ball.reverseYVelocity();
    		ball.clearObstacleY(screenY-2);
    		
    		//lose a life
    		lives--;
    		soundPool.play(loseLifeID, 1, 1, 0, 0, 1);
    		
    		if(lives == 0){
    			paused = true;
    			createBricksAndRestart();
    		}
    	}
    	
    	//ball vs top (clear is 12 because it works on ball.bottom, so it's 2+ballheight)
    	if(ball.getRect().top < 0){
    		ball.reverseYVelocity();
    		ball.clearObstacleY(12);
    		soundPool.play(beep2ID, 1, 1, 0, 0, 1);
    	}
    	
    	//ball vs left
    	if(ball.getRect().left < 0){
    		ball.reverseXVelocity();
    		ball.clearObstacleX(2);
    		soundPool.play(beep3ID, 1, 1, 0, 0, 1);
    	}
    	
    	//ball vs right
    	if(ball.getRect().right > screenX - 10){
    		ball.reverseXVelocity();
    		ball.clearObstacleX(screenX - 22); //works on left of ball
    		soundPool.play(beep3ID, 1, 1, 0, 0, 1);
    	}
    	
    	// Pause if cleared screen
    	if(score == numBricks * 10){
    		paused = true;
    		createBricksAndRestart();
    	}


    }
    
    //Draw the new scene
    public void draw() {
    	
    	// validate drawing surface
    	if(ourHolder.getSurface().isValid()) {
    		
    		// lock the canvas ready to draw
    		canvas = ourHolder.lockCanvas();
    		
    		// draw the background color
    		canvas.drawColor(Color.argb(255,26,128,182));
    		
    		// Choose the brush color for drawing
    		paint.setColor(Color.argb(255, 255, 255, 255));
    		
    		// Draw the paddle
    		canvas.drawRect(paddle.getRect(), paint);
            
    		// Draw the ball
    		canvas.drawRect(ball.getRect(), paint);

    		// change the brush color to brick
    		// paint.setColor(Color.argb(255, 249, 129, 0));
            // Draw the bricks
    		for(int i = 0; i < numBricks; i++){
    			if(bricks[i].getVisibility()) {
    				// change paint color to the color of the current brick, then draw it
    				paint.setColor(bricks[i].getColor());
    				canvas.drawRect(bricks[i].getRect(), paint);
    			}
    		}
            // Draw the HUD
    		// change brush color
    		paint.setColor(Color.argb(255, 255, 255, 255));
    		
    		//Draw the score
    		paint.setTextSize(40);
    		canvas.drawText("Score: " + score + "   Lives: " + lives, 10, 50, paint);
    		canvas.drawText("Experience: " + expPoints, 10, 90, paint);
    		// Has the player cleared the screen?
    		if(score == numBricks * 10){
    			paint.setTextSize(90);
    			canvas.drawText("YOU HAVE WON!", 10, screenY/2, paint);
    		}
    		
    		// Has the player lost?
    		if(lives <= 0){
    			paint.setTextSize(90);
    			canvas.drawText("YOU HAVE LOST!", 10, screenY/2, paint);
    		}
    		
            // Draw everything to the screen
    		ourHolder.unlockCanvasAndPost(canvas);
    	}
    }
    
    // If SimpleGameEngine Activity is paused/stopped
    // shutdown our thread.
    public void pause() {
    	playing = false;
    	try{
    		gameThread.join();
    	} catch (InterruptedException e) {
    		Log.e("Error:", "joining thread");
    	}
	}
    
    // If simpleGameEngine Activitys is started
    // start our thread
    public void resume() {
    	playing = true;
    	gameThread = new Thread(this);
    	gameThread.start();
    }
    
    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    /*@Override
     public boolean onTouchEvent(MotionEvent motionEvent) {
    	switch (motionEvent.getAction() & motionEvent.ACTION_MASK) {
    	// Player has touched the screen
    	case MotionEvent.ACTION_DOWN:
    		
    		paused = false;
    		
    		if(motionEvent.getX() > screenX / 2) {
    			paddle.setMovementState(paddle.RIGHT);
    		}
    		else{
    			paddle.setMovementState(paddle.LEFT);
    		}
    		
    		break;	
    	
    	// Player has removed finger from screen
    	case MotionEvent.ACTION_UP:
    		paddle.setMovementState(paddle.STOPPED);
    		break;
    	}
    	return true;
    }
    */ //end old control set
    
    // trying to implement touch and drag control
    // this works but the sensitivity needs fine tuning
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
    	switch (motionEvent.getAction() & motionEvent.ACTION_MASK) {
    	
    	case MotionEvent.ACTION_DOWN:
    		paused = false;
    		prevX = motionEvent.getX();
    		break;
    		
    	case MotionEvent.ACTION_MOVE:
    		if(motionEvent.getX() > prevX)
    		{
    			paddle.setMovementState(paddle.RIGHT);
    			prevX = motionEvent.getX();
    		}
    		if(motionEvent.getX() < prevX)
    		{
    			paddle.setMovementState(paddle.LEFT);
    			prevX = motionEvent.getX();
    		}
    		break;
    		
    	case MotionEvent.ACTION_UP:
    		paddle.setMovementState(paddle.STOPPED);
    		prevX = 0;
    		break;
    	}
    	return true;
    }
    } //end BreakoutView inner class
	
	// this method executes when the player starts the game
	@Override
	protected void onResume() {
		super.onResume();
		// tell the gameView resume method to execute
		breakoutView.resume();
	}
	
	// this method executes when the player quits the game
	@Override
	protected void onPause() {
		super.onPause();
		// tell the gameView pause method to execute
		breakoutView.pause();
	}
	
} //end BreakoutGame class
