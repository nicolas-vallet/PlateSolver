package com.nzv.astro.platesolver.domain;

import java.util.Comparator;

public class OpmAComparator implements Comparator<Triangle> {
	
	@Override
	public int compare(Triangle t1, Triangle t2) {
		int result = -Double.compare(t1.getLongestShortestSidesRatio(),
				t2.getLongestShortestSidesRatio());
		if (result == 0) {
			return -Double.compare(t1.getDotProduct(),
					t2.getDotProduct());
		} else {
			return result;
		}
	}

}
