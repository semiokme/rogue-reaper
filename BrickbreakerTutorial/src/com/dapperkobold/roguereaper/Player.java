package com.dapperkobold.roguereaper;


//holds player data
public class Player {

	public JobReaper pJob;
	public int maxHealth;
	public int curHealth;
	public int testAbilArray[] = {1,1,1,1,1,1};
	
	//Constructor
	public Player() {
		pJob = new JobReaper("Reaper", 1, testAbilArray);
		maxHealth = 100;
		curHealth = maxHealth;
	}
	
	public int getCurHealth() {
		return curHealth;
	}
	
	public void dmgPlayer(int dmg) {
		curHealth -= dmg;
		if(curHealth <= 0)
			curHealth = 0;
	}
}
