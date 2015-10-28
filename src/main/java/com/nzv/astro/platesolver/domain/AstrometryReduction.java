package com.nzv.astro.platesolver.domain;

import java.awt.Image;

public class AstrometryReduction {

	/**
	 * The input image
	 */
	private Image sourceImage;

	/**
	 * The Equatorial coordinates of center of input image.
	 * <p>
	 * The first element contains the right ascension and the second element contains the
	 * declinaison.
	 * <p>
	 * Both angles are expressed in decimal degrees.
	 */
	private double[] centerCoordinates;

	/**
	 * The spatial resolution of the image
	 * <p>
	 * It's expressed as arcsec/pixel.
	 * <p>
	 * For a given DSLR or CCD and an optical element, It'is approximatly equals to 206 *
	 * pixelsize(in um) / focal length(in mm)
	 */
	private double samplerate;

	public Image getSourceImage() {
		return sourceImage;
	}

	public double[] getCenterCoordinates() {
		return centerCoordinates;
	}

	public double getSamplerate() {
		return samplerate;
	}

}
