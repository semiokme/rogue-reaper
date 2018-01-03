package com.dapperkobold.roguereaper;

public class JobReaper extends Job {

	private int twoFor = 0;
	private float hunter = 0.0f;
	private float lastGasp = 0.0f;
	private int ghostly = 0;
	private int curGhost = 0;
	private Brick hollowBrick;
	private Brick ghostBrick;
	private int reaping = 0;
	private int ltd = 0;
	private long ltdTime = 0;
	
	//default constructor
	public JobReaper() {
		super();
	}
	
	//constructor
	public JobReaper(String n, int l, int[] a) {
		super(n,l,a);
		twoFor = abilities[1]*5;
		hunter = abilities[2]*0.05f;
		lastGasp = abilities[3]*2.5f;
		ghostly = abilities[4];
		hollowBrick = new Brick(-1,-1,-1,-1);
		resetGhost();
		reaping = abilities[5];
		ltd = abilities[6];
	}


	//1st abil - compares a random 1-100 value against twoFor
	//Description: 2 For 1 - rank*5% chance to gain 100% more souls when gaining souls
	//Invoked when gaining souls
	public boolean twoForOne() {
		if(rand.nextInt(100)+1 < twoFor)
			return true;
		return false;
	}
	
	//2nd abil - accepts numMonsters as input, returns number of additional monsters to spawn
	//Description: Hunter - causes rank*5% more enemies to spawn
	//Invoked when creating enemies
	public int hunter(int numMonsters) {
			int addMonsters = (int)(Math.ceil(numMonsters * hunter));
		return addMonsters;
	}
	
	//3rd abil - accepts maxHealth, currentHealth as arguements
	//Description: Last Gasp - monsters below rank*2.5% health die in one hit
	//Invoked when damaging monsters before damage is dealt
	public boolean lastGasp(int maxHealth, int curHealth) {
		if( (float)curHealth / maxHealth < lastGasp)
			return true;
		return false;
	}
	
	//4th abil - called when ghostly is activated via a button
	//Description: Ghostly - Ball passes through 1 target/rank when activated.  Ball becomes solid again after, and begins colliding
	//Called when abilities is clicked, needs a same brick case
	//This will cause it to reset to max if activated multiple times, not stack
	public int ghostly() {
		curGhost = ghostly;
		resetGhost();
		return curGhost;
	}
	
	//4th abil helper - called on collision while curGhost > 0
	//Calling this should be to check a collision while ghostly is active
	//It returns the number of bricks the ghost can pass through after this collision
	//It should also handle colliding with the same brick more than once.  
	//NOTE: I might be able to restructure this to only have a single return statement.  
	public int gUpdate(Brick b) {
		if(curGhost > 0) {
			if(ghostBrick != b) {
				ghostBrick = b;
				curGhost--;
			}
		} else {
			curGhost = 0;
		}
		return curGhost;
	}
	
	//4 abil helper - resets ghostBrick to hollowBrick: this is to not create new objects
	private void resetGhost() {
		ghostBrick = hollowBrick;
	}
	
	//5th abil - called when Reaping is activated via a button
	//Description: Reaping - Consumes 50% current health, Deals (Rank * Max Health) Damage to all targets
	//Called when activated, needs current player health, max player health, list of existing targets.
	//returned int is damage dealt to player
	public int reaping(int pHealth, int maxHealth, Brick[] bricks) {

		int dmg = reaping*maxHealth;
		
		for(int i = 0; i < bricks.length; i++) {
			bricks[i].lowerHitPoints(dmg);
		}
		
		return (int)Math.floor(pHealth/2.0);
	}
	
	//6th abil - called when LTD is activated via a button
	//Description: Like The Dead - Reduce ball speed by 100% for rank*5 seconds
	//called when activated, needs current ball speed
	public void likeTheDead(Ball b) {
		b.setXVelocity(b.getXVelocity()/2);
		b.setYVelocity(b.getYVelocity()/2);
		ltdTime = ltd*5000;
	}
	
	//6th abil helper - verifies slow is still active, decrements timer by frame time.  
	//shuts off slow if no longer active.  
	public void ltdUpdate(long frameTime, Ball b) {
		if(ltdTime >= 0) 
				ltdTime -= frameTime;
		else {
			b.setXVelocity(b.getXVelocity()*2);
			b.setYVelocity(b.getYVelocity()*2);
			ltdTime = 0;
		}
	}
	
}
