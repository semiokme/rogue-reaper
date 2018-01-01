package com.dapperkobold.roguereaper;

import java.util.Random;


public class Job {

	// member variables
	protected String name = "";
	protected int jobLevel = 0;
	protected int[] abilities = new int[6];
	protected Random rand;
	
	//Default constructor creates a reaper with no powers
	public Job(){
		name = "Reaper";
		jobLevel = 1;
		for(int i = 0; i <6; i++)
			abilities[i] = 0;
		rand = new Random();
	}
	
	public Job(String n, int l, int[] a) {
		name = n;
		jobLevel = l;
		for(int i = 0; i <6; i++) 
			abilities[i] = a[i];
		rand = new Random();
	}
	
	
}
