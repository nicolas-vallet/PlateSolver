package com.nzv.astro.platesolver.domain.catalog;

public enum SizeUnit {
	d("Degree"),
	m("Arcminute"),
	s("Arcsecond");

	private String name;
	
	SizeUnit(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
