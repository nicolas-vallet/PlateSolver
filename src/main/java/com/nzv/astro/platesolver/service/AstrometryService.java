package com.nzv.astro.platesolver.service;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.nzv.astro.platesolver.domain.AstrometryReduction;
import com.nzv.astro.platesolver.domain.CatalogObject;
import com.nzv.astro.platesolver.domain.Centroid;
import com.nzv.astro.platesolver.domain.ColorChannel;
import com.nzv.astro.platesolver.domain.Component;
import com.nzv.astro.platesolver.domain.EnumTriangleType;
import com.nzv.astro.platesolver.domain.Peak;
import com.nzv.astro.platesolver.domain.Triangle;

/**
 * A service which can detect stars in an image.
 * <p>
 * It also provides methods to identify patterns against a stars catalog and it can determine the
 * orientation of the image on the sky.
 */
public interface AstrometryService {

	/**
	 * Minimal intensity level (maximal levevl equals 255) from which we consider a pixel during the
	 * seach of {@link Peak}.
	 */
	public static final int BACKGROUND_THRESHOLD = 200;

	/**
	 * Intensity level (maximal level equals 255) at which a pixel is not considered as part of a
	 * {@link Component}.
	 * <p>
	 * It is used during computation of a {@link Component}'s {@link Centroid}.
	 */
	public static final int CENTROID_THRESHOLD = 60;

	/**
	 * The distance in pixel under which we consider two {@link Peak} as being connected and
	 * relative to the same {@link Component}.
	 */
	public static double MAX_DISTANCE_BEETWEEN_TWO_PEAKS_OF_SAME_COMPONENT = 4;

	/**
	 * Maximal distance in pixel during the computation of a {@link Component}'s {@link Centroid}.
	 */
	public static final int MAX_RANGE_FOR_CENTROID = 5;

	/**
	 * The number of significative decimal desired when we calculate the coordinates of
	 * {@link Component}'s {@link Centroid}.
	 */
	public static final double CENTROID_COORDINATE_PRECISION = 2;

	/**
	 * Extract {@link Component} of an input {@link BufferedImage}, considering the given
	 * {@link ColorChannel}.
	 * <p>
	 * The returned list is sorted by descending light intensity.
	 * 
	 * @param img
	 * @param channel
	 * @return
	 */
	public List<Component> findComponents(BufferedImage img, ColorChannel channel);

	public List<Triangle> buildTrianglesFromComponents(Collection<Component> components);

	public List<Triangle> buildTrianglesFromCatalogObjects(Collection<CatalogObject> objects);

	public AstrometryReduction findOrientation(BufferedImage img, ColorChannel channel);

}
