package com.nzv.astro.platesolver.domain;


public interface AbstractSpatialElement {
	
	public EnumCoordinateSystem getCoordinatesSystem();
	public double getX();
	public double getY();
	public String getLabel();
	
}
