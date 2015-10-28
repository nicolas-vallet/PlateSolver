package com.nzv.astro.platesolver.domain;

import java.util.Comparator;

public class SpatialComparator<T extends AbstractSpatialElement> implements Comparator<T> {

	@Override
	public int compare(T o1, T o2) {
		int result = Double.compare(o1.getX(), o2.getX());
		if (result != 0) {
			result = Double.compare(o1.getY(), o2.getY());
		}
		return result;
	}

}
