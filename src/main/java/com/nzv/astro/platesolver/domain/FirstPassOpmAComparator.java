package com.nzv.astro.platesolver.domain;

import java.util.Comparator;

public class FirstPassOpmAComparator implements Comparator<Triangle> {

	@Override
	public int compare(Triangle o1, Triangle o2) {
		return Double.compare(o1.getScore(), o2.getScore());
	}

}
