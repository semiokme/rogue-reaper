/*
 * used for any game object that will hit any other
 * far far far from complete
 * uses PVector for 2D Vectors
 */

package com.dapperkobold.roguereaper;

public class Collidible {

	private PVector location;
	private PVector velocity;
	
	// vector constructor
	public Collidible(PVector l, PVector v) {
		location = l;
		velocity = v;
	}
	
	// copy constructor - deep copies
	// NOTE: returned PVectors are already new copies of the existing vectors
	public Collidible(Collidible c) {
		location = c.getLocation();
		velocity = c.getVelocity();
	}
	
	// location getter
	public PVector getLocation() {
		return new PVector(location);
	}
	
	// velocity getter
	public PVector getVelocity() {
		return new PVector(velocity);
	}
	
}
