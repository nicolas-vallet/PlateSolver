package com.nzv.astro.platesolver.domain.catalog;

public enum ConstellationBoundaryPointType {
	O("Original"), I("Interpolated");

	private String comment;

	private ConstellationBoundaryPointType(String c) {
		this.comment = c;
	}

	public String getComment() {
		return this.comment;
	}
}
