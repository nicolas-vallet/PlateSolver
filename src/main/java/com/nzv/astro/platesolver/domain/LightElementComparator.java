package com.nzv.astro.platesolver.domain;

import java.util.Comparator;

public class LightElementComparator<T extends AbstractLightElement> implements Comparator<T> {

	@Override
	public int compare(T o1, T o2) {
		return -Double.compare(o1.getIntensity(), o2.getIntensity());
	}

}
