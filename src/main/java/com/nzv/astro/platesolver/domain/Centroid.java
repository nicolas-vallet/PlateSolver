package com.nzv.astro.platesolver.domain;

public class Centroid implements AbstractSpatialElement {

	private double x;
	private double y;

	public Centroid(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public EnumCoordinateSystem getCoordinatesSystem() {
		return EnumCoordinateSystem.CARTESIAN;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Centroid [x=" + x + ", y=" + y + "]";
	}

	@Override
	public String getLabel() {
		return "Centroid("+x+","+y+")";
	}
}
