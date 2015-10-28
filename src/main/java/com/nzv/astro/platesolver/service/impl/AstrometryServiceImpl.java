package com.nzv.astro.platesolver.service.impl;

import static com.nzv.astro.platesolver.Constants.MAX_SOURCES_AUTHORIZED;
import static java.lang.Math.cos;
import static java.lang.Math.min;
import static java.lang.Math.sin;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import com.nzv.astro.ephemeris.coordinate.impl.EquatorialCoordinates;
import com.nzv.astro.platesolver.domain.AbstractSpatialElement;
import com.nzv.astro.platesolver.domain.AstrometryReduction;
import com.nzv.astro.platesolver.domain.CatalogObject;
import com.nzv.astro.platesolver.domain.ColorChannel;
import com.nzv.astro.platesolver.domain.Component;
import com.nzv.astro.platesolver.domain.FirstPassOpmAComparator;
import com.nzv.astro.platesolver.domain.LightElementComparator;
import com.nzv.astro.platesolver.domain.OpmAComparator;
import com.nzv.astro.platesolver.domain.Peak;
import com.nzv.astro.platesolver.domain.SpatialComparator;
import com.nzv.astro.platesolver.domain.Triangle;
import com.nzv.astro.platesolver.service.AstrometryService;

public class AstrometryServiceImpl implements AstrometryService {

	private static final double FIRST_PASS_THRESHOLD = 0.02d;

	@Override
	public AstrometryReduction findOrientation(BufferedImage img, ColorChannel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Triangle> findFirstPassCandidates(List<Triangle> imageTriangles,
			List<Triangle> referenceTriangles) {

		List<Triangle> result = new ArrayList<Triangle>();

		List<Triangle> I = new ArrayList<Triangle>();
		I.addAll(imageTriangles);
		Collections.sort(I, new FirstPassOpmAComparator());

		List<Triangle> R = new ArrayList<Triangle>(referenceTriangles.size());
		R.addAll(referenceTriangles);
		Collections.sort(R, new FirstPassOpmAComparator());

		return result;
	}

	/**
	 * Given the approximative place center coordinates and a reference catalog entry, returns an
	 * array of double containing the standard coordinates used in PV phase (preliminary
	 * verification), as described in part 2.2.5 of reference article.
	 * <p>
	 * [0] contains coordinate E and [1] contains coordinate N as :
	 * 
	 * <pre>
	 *                       cos (ref.DEC) * sin (ref.RA - placeCenter.RA)
	 * E = ---------------------------------------------------------------------------------------------
	 *      sin(placeCenter.DEC) * sin(ref.DEC) + cos(placeCenter.DEC) * cos(ref.DEC) * cos(ref.RA - a)
	 *     
	 *     
	 *     
	 *      sin(placeCenter.DEC) * cos(ref.DEC) * cos(ref.RA - placeCenter.RA) - cos(placeCenter.DEC) * sin(ref.DEC)
	 * N = ----------------------------------------------------------------------------------------------------------
	 *         sin(placeCenter.DEC) * sin(ref.DEC) + ( cos(placeCenter.DEC) * cos(ref.DEC) * cos(ref.RA - a) )
	 * </pre>
	 * 
	 * @param approximativePlaceCenter
	 * @param ref
	 * @return
	 */
	private double[] computeStandardCoordinates(EquatorialCoordinates approximativePlaceCenter,
			CatalogObject ref) {
		// TODO : Comprendre la signification du parametre "a" de la formule, remplacé par 1 en
		// attendant.
		double e = (cos(ref.getDeclinaison()) * sin(ref.getRightAscension()
				- approximativePlaceCenter.getRightAscension()))
				/ ((sin(approximativePlaceCenter.getDeclinaison()) * sin(ref.getDeclinaison())) + cos(approximativePlaceCenter
						.getDeclinaison())
						* cos(ref.getDeclinaison())
						* cos(ref.getRightAscension() - 1));

		// TODO : Comprendre la signification du parametre "a" de la formule, remplacé par 1 en
		// attendant.
		double n = ((sin(approximativePlaceCenter.getDeclinaison()) * cos(ref.getDeclinaison()) * cos(ref
				.getRightAscension() - approximativePlaceCenter.getRightAscension())) - (cos(approximativePlaceCenter
				.getDeclinaison()) * sin(ref.getDeclinaison())))
				/ ((sin(approximativePlaceCenter.getDeclinaison()) * sin(ref.getDeclinaison())) + (cos(approximativePlaceCenter
						.getDeclinaison()) * cos(ref.getDeclinaison()) * cos(ref
						.getRightAscension() - 1)));

		double[] result = new double[2];
		result[0] = e;
		result[1] = n;
		return result;
	}

	/**
	 * Given the samplerate of image (expressed as degrees per pixel), three {@link Component},
	 * three {@link CatalogObject} potential candidates and standard coordinates as computed by
	 * {@link AstrometryServiceImpl#computeStandardCoordinates(EquatorialCoordinates, CatalogObject)}
	 * , returns the place constants a, b, c, a', b', c', contained in this order in an array of
	 * double.
	 * 
	 * HINT:
	 * 
	 * <pre>
	 * 2x + 3y - 2z = 1
	 * -x + 7y + 6z = -2
	 * 4x - 3y - 5z = 1
	 * </pre>
	 * 
	 * is resolved by
	 *
	 * <pre>
	 * 	RealMatrix coefficients = new Array2DRowRealMatrix(new double[][] { { 2, -3, -2 },
	 * 			{ -1, 7, 6 }, { 4, -3, -5 } }, false);
	 * 	DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
	 * 	
	 * 	RealVector constants = new ArrayRealVector(new double[] { 1, -2, 1 }, false);
	 * 	RealVector solution = solver.solve(constants);
	 * 	double[] result = new double[3];
	 * 	result[0] = solution.getEntry(0);
	 * 	result[1] = solution.getEntry(1);
	 * 	result[2] = solution.getEntry(2);
	 * 	return result;
	 * </pre>
	 */
	private double[] computePlateConstants(double samplerate, Component[] components,
			CatalogObject[] references, double n, double e) {

		double[] result = new double[6];
		return result;
	}

	@Override
	public List<Triangle> buildTrianglesFromCatalogObjects(Collection<CatalogObject> objects) {
		List<Triangle> result = new ArrayList<Triangle>();

		if (objects == null || objects.size() < 3) {
			throw new IllegalArgumentException(
					"Could not shape triangle with less than three sources");
		}

		// We sort the sources by decreasing brightness.
		List<CatalogObject> sortedObjects = new ArrayList<CatalogObject>();
		sortedObjects.addAll(objects);
		Collections.sort(sortedObjects, new LightElementComparator<CatalogObject>());

		// We limit the input list of sources to MAX_SOURCES_AUTHORIZED items...
		Set<Triangle> tmp = new HashSet<>();
		for (AbstractSpatialElement v1 : sortedObjects.subList(0,
				min(sortedObjects.size(), MAX_SOURCES_AUTHORIZED))) {
			for (AbstractSpatialElement v2 : sortedObjects.subList(0,
					min(sortedObjects.size(), MAX_SOURCES_AUTHORIZED))) {
				if (v1.equals(v2)) {
					continue;
				}
				for (AbstractSpatialElement v3 : sortedObjects.subList(0,
						min(sortedObjects.size(), MAX_SOURCES_AUTHORIZED))) {
					if (v3.equals(v1) || v3.equals(v2)) {
						continue;
					}
					tmp.add(new Triangle(v1, v2, v3));
				}
			}
		}

		result.addAll(tmp);
		Collections.sort(result, new OpmAComparator());
		return result;
	}

	@Override
	public List<Triangle> buildTrianglesFromComponents(Collection<Component> components) {
		List<Triangle> result = new ArrayList<Triangle>();

		if (components == null || components.size() < 3) {
			throw new IllegalArgumentException(
					"Could not shape triangle with less than three sources");
		}

		// We sort the sources by decreasing brightness.
		List<Component> sortedComponents = new ArrayList<Component>();
		sortedComponents.addAll(components);
		Collections.sort(sortedComponents, new LightElementComparator<Component>());

		// We limit the input list of sources to MAX_SOURCES_AUTHORIZED items...
		Set<Triangle> tmp = new HashSet<>();
		for (AbstractSpatialElement v1 : sortedComponents.subList(0,
				min(sortedComponents.size(), MAX_SOURCES_AUTHORIZED))) {
			for (AbstractSpatialElement v2 : sortedComponents.subList(0,
					min(sortedComponents.size(), MAX_SOURCES_AUTHORIZED))) {
				if (v1.equals(v2)) {
					continue;
				}
				for (AbstractSpatialElement v3 : sortedComponents.subList(0,
						min(sortedComponents.size(), MAX_SOURCES_AUTHORIZED))) {
					if (v3.equals(v1) || v3.equals(v2)) {
						continue;
					}
					tmp.add(new Triangle(v1, v2, v3));
				}
			}
		}

		result.addAll(tmp);
		Collections.sort(result, new OpmAComparator());
		return result;
	}

	@Override
	public List<Component> findComponents(BufferedImage img, ColorChannel channel) {
		// We start by extracting every relevant peaks of the image...
		List<Peak> peaks = getPeaks(img, channel);

		// Then we group the connected peaks by component
		List<Component> components = groupPeaksByComponents(peaks);

		// We finally sort the list of Component by decreasing luminosity
		Collections.sort(components, new LightElementComparator<Component>());
		return components;
	}

	private List<Component> groupPeaksByComponents(Collection<Peak> peaks) {
		List<Component> result = new ArrayList<Component>();
		for (Peak p : peaks) {
			if (result.isEmpty()) {
				Component newComponent = new Component();
				newComponent.addPeak(p);
				result.add(newComponent);
			} else {
				// TODO : Revoir la logique qui détermine si les peaks sont connectés
				// choix sur la variation d'intensité ?
				Component closestComponent = findClosestComponent(result, p);
				if (isConnected(p, closestComponent)) {
					closestComponent.addPeak(p);
				} else {
					Component newComponent = new Component();
					newComponent.addPeak(p);
					result.add(newComponent);
				}
			}
		}
		return result;
	}

	private Component findClosestComponent(List<Component> candidates, AbstractSpatialElement ref) {
		Component closest = null;
		for (Component c : candidates) {
			if (closest == null) {
				closest = c;
			} else {
				if (Triangle.computeLengthBetweenVertices(c, ref) < Triangle
						.computeLengthBetweenVertices(closest, ref)) {
					closest = c;
				}
			}
		}
		return closest;
	}

	private boolean isConnected(Peak p, Component c) {
		// computeCentroid(c);
		if (p.getX() >= c.getCentroid().getX() - MAX_RANGE_FOR_CENTROID
				&& p.getX() <= c.getCentroid().getX() + MAX_RANGE_FOR_CENTROID
				&& p.getY() >= c.getCentroid().getY() - MAX_RANGE_FOR_CENTROID
				&& p.getY() <= c.getCentroid().getY() + MAX_RANGE_FOR_CENTROID) {
			return true;
		} else {
			return false;
		}
	}

	private List<Peak> getPeaks(BufferedImage img, ColorChannel channel) {
		List<Peak> peaks = new ArrayList<Peak>();
		int x = 0, y = 0, xx = 0, yy = 0;
		double peakmin = 0, peakmax = 255, pixelValue = 0, neighbor = 0;
		boolean bingo = false;
		int width = img.getWidth();
		int height = img.getHeight();

		for (y = 2; y < height; y++) {
			for (x = 2; x < width; x++) {
				pixelValue = getPixelIntensityByChannel(img, x, y, channel);
				if (pixelValue < BACKGROUND_THRESHOLD) {
					continue;
				}
				if (pixelValue >= peakmin && pixelValue < peakmax) {
					bingo = true;
					for (yy = y - 1; yy < y + 1; yy++) {
						for (xx = x - 1; xx < x + 1; xx++) {
							if (bingo) {
								neighbor = getPixelIntensityByChannel(img, xx, yy, channel);
								if (neighbor > pixelValue) {
									bingo = false;
								} else if (neighbor == pixelValue) {
									if (xx != x || yy != y) {
										if ((xx <= x && yy <= y) || (xx > x && yy < y)) {
											bingo = false;
										}
									}
								}
							}
						}
					}
				}
				if (bingo) {
					peaks.add(new Peak(channel, x, y, pixelValue));
				}
			}
		}

		// We sort the peaks found, spatially
		Collections.sort(peaks, new SpatialComparator<Peak>());
		return peaks;
	}

	private int getPixelIntensityByChannel(BufferedImage img, int x, int y, ColorChannel channel) {
		Color color = new Color(img.getRGB(x, y));
		switch (channel) {
		case ALL:
			return (color.getRed() + color.getGreen() + color.getBlue()) / 3;
		case RED:
			return color.getRed();
		case BLUE:
			return color.getBlue();
		case GREEN:
		default:
			return color.getGreen();
		}
	}

}
