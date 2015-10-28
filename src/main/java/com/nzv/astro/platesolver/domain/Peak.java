package com.nzv.astro.platesolver.domain;

public class Peak implements Comparable<Peak>, AbstractSpatialElement, AbstractLightElement {

	private final ColorChannel channel;
	private final int x;
	private final int y;
	private final double intensity;

	public Peak(ColorChannel channel, int x, int y, double intensity) {
		super();
		this.channel = channel;
		this.x = x;
		this.y = y;
		this.intensity = intensity;
	}

	@Override
	public EnumCoordinateSystem getCoordinatesSystem() {
		return EnumCoordinateSystem.CARTESIAN;
	}

	public ColorChannel getChannel() {
		return channel;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	@Override
	public String getLabel() {
		return "p("+x+","+y+")";
	}

	public double getIntensity() {
		return intensity;
	}

	@Override
	public String toString() {
		return "Component [channel=" + channel + ", x=" + x + ", y=" + y + ", intensity="
				+ intensity + "]";
	}

	/**
	 * Components are sorted by decreasing intensity
	 */
	@Override
	public int compareTo(Peak o) {
		if (this.getIntensity() > o.getIntensity()) {
			return -1;
		} else if (this.getIntensity() < o.getIntensity()) {
			return 1;
		}
		return 0;
	}

}
