package com.nzv.astro.platesolver.domain;


public abstract class CatalogObject implements AbstractSpatialElement, AbstractLightElement {

	public abstract Double getVisualMagnitude();

	public abstract double getRightAscension();
	
	public abstract double getDeclinaison();

	public abstract String getIdentifier();

	public abstract String getObjectType();
	
	@Override
	public EnumCoordinateSystem getCoordinatesSystem() {
		return EnumCoordinateSystem.EQUATORIAL;
	}

	@Override
	public double getX() {
		return getRightAscension();
	}

	@Override
	public double getY() {
		return getDeclinaison();
	}

	@Override
	public double getIntensity() {
		// On retourne l'inverse de la magnitude car cette méthode est utilisée par
		// un LightElementComparator qui considére la plus grande valeur d'intensité comme la plus
		// lumineuse.
		return -getVisualMagnitude();
	}

}
