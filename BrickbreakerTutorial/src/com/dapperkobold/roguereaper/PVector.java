/*
 * My own Vector class
 * should facilitate collisions
 * as of 11/11/17 incomplete.  
 */

package com.dapperkobold.roguereaper;

import java.lang.Math;

public class PVector {
	private float x;
	private float y;
	
	// direct constructor
	public PVector(float x_, float y_){
		x = x_;
		y = y_;
	}
	
	// copy constructor
	public PVector(PVector v) {
		x = v.getX();
		y = v.getY();
	}
	
	// vector addition - modifies caller
	public void add(PVector v) {
		y += v.getY();
		x += v.getX();
	}
	
	// vector subtraction - modifies caller
	public void sub(PVector v) {
		y -= v.getY();
		x -= v.getX();
	}
	
	// scalar multiplications - modifies caller
	public void mult(float n) {
		x *= n;
		y *= n;
	}
	
	// scalar division - modifies caller
	public void div(float n) {
		x /= n;
		y /= n;
	}
	
	// static add
	public static PVector add(PVector a, PVector b) {
		return new PVector(a.getX() + b.getX(), a.getY() + b.getY());
	}

	// static subtraction (a - b)
	public static PVector sub(PVector a, PVector b) {
		return new PVector(a.getX() - b.getX(), a.getY() - b.getY());
	}
	
	// static multiplication
	public static PVector mult(float n, PVector v) {
		return new PVector(v.getX() * n, v.getY() * n);
	}
	
	// static division 
	public static PVector div(float n, PVector v) {
		return new PVector(v.getX() / n, v.getY() / n);
	}
	
	
	// returns vector magnitude - no modification 
	public float mag() {
		return (float)Math.sqrt(x*x + y*y);
	}
	
	// normalizes calling vector - sets mag = 1.0f
	public void normalize() {
		float m = mag();
		if(m != 0) {
			div(m);
		}
	}
	
	// limits the vector to the passed magnitude
	public void limit(float max) {
		if (mag() > max) {
			normalize();
			mult(max);
		}
	}
	
	
	
	// X getter
	public float getX() {
		return x;
	}
	
	// Y getter
	public float getY() {
		return y;
	}
	
	// X setter
	public void setX(float x_) {
		x = x_;
	}
	
	// Y setter
	public void setY(float y_) {
		y = y_;
	}
	
}
