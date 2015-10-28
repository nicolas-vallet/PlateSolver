package com.nzv.astro.platesolver.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nzv.astro.platesolver.service.AstrometryService;

public class Component implements AbstractSpatialElement, AbstractLightElement {

	public static final double EPSILON = 2d;

	private List<Peak> peaks = new ArrayList<Peak>();
	private Centroid centroid;
	private double intensity = 0d;

	public Component() {
	}

	@Override
	public EnumCoordinateSystem getCoordinatesSystem() {
		return EnumCoordinateSystem.CARTESIAN;
	}

	public Component(List<Peak> peaks) {
		this.peaks = peaks;
		this.centroid = computeCentroid(this.peaks);
	}

	public Component addPeak(Peak peak) {
		this.peaks.add(peak);
		this.intensity += peak.getIntensity();
		this.centroid = computeCentroid(this.peaks);
		return this;
	}

	public List<Peak> getPeaks() {
		return peaks;
	}

	@Override
	public double getX() {
		return centroid.getX();
	}

	@Override
	public double getY() {
		return centroid.getY();
	}

	@Override
	public double getIntensity() {
		return intensity;
	}

	private Centroid computeCentroid(Collection<Peak> peaks) {
		double wholeIntensity = 0d, cX = 0d, cY = 0d;
		double[] result = new double[2];
		for (Peak p : peaks) {
			wholeIntensity += p.getIntensity();
			cX += p.getX() * p.getIntensity();
			cY += p.getY() * p.getIntensity();
		}
		
		double p = Math.pow(10, AstrometryService.CENTROID_COORDINATE_PRECISION);
		result[0] = Math.round((cX / wholeIntensity) * p) / p;
		result[1] = Math.round((cY / wholeIntensity) * p) / p;
		return new Centroid(result[0], result[1]);
		
	}
	public Centroid getCentroid() {
		return this.centroid;
	}

	// public Centroid getCentroid() {
	// return centroid;
	// }
	//
	// public void setCentroid(Centroid centroid) {
	// this.centroid = centroid;
	// }

	@Override
	public String toString() {
		return "Component [" + centroid + ", intensity=" + intensity + ", peaksCount="
				+ peaks.size() + "]";
	}

	@Override
	public String getLabel() {
		return "Component("+centroid.getX()+","+centroid.getY()+")";
	}

	/**
	 * Returns a point which has as coordinates the upper left corner of the box which contains that
	 * component.
	 */
	public Point getUpperLeftBoundBoxCorner() {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;

		for (Peak p : peaks) {
			minX = Math.min((int) p.getX(), minX);
			minY = Math.min((int) p.getY(), minY);
		}
		return new Point(minX, minY);
	}

	public Point getLowerRightBoundBoxCorner() {
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;

		for (Peak p : peaks) {
			maxX = Math.max((int) p.getX(), maxX);
			maxY = Math.max((int) p.getY(), maxY);
		}
		return new Point(maxX, maxY);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((centroid == null) ? 0 : centroid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Component other = (Component) obj;
		if (centroid == null) {
			if (other.centroid != null)
				return false;
		} else if (Triangle.computeLengthBetweenVertices(this.getCentroid(), other.getCentroid()) > EPSILON)
			return false;
		return true;
	}

}
