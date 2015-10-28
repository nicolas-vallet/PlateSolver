package com.nzv.astro.platesolver.run;

import static com.nzv.astro.ephemeris.Sexagesimal.SexagesimalType.DEGREES;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nzv.astro.ephemeris.Sexagesimal;
import com.nzv.astro.ephemeris.coordinate.impl.EquatorialCoordinates;
import com.nzv.astro.platesolver.domain.CatalogObject;
import com.nzv.astro.platesolver.domain.ColorChannel;
import com.nzv.astro.platesolver.domain.Component;
import com.nzv.astro.platesolver.domain.Triangle;
import com.nzv.astro.platesolver.domain.catalog.DeepSkyObject;
import com.nzv.astro.platesolver.service.AstrometryService;
import com.nzv.astro.platesolver.service.CatalogService;
import com.nzv.astro.platesolver.service.impl.AstrometryServiceImpl;

public class StarDetector {

	private static final String WORKING_IMAGE = "ngc7331_t4.png";

	private static final String TARGET_NAME = "ngc 7331";

	// private static final String TARGET_NAME = "ngc 6853"; // M27

	// expressed in degrees
	private static final double HALF_FIELD_WIDE_IN_DEGREES = 1.5d;

	public static ColorChannel WORKING_CHANNEL = ColorChannel.ALL;

	public static final int MAX_COMPONENTS = 50;

	private static AstrometryService astroService = new AstrometryServiceImpl();

	public static void main(String[] args) throws BeansException, InterruptedException,
			ExecutionException {

		long idx = 0;
		StringBuilder sb = new StringBuilder();

		BufferedImage img = null;
		try {
			System.out.println("Opening image " + WORKING_IMAGE);
			img = ImageIO.read(new File(ClassLoader.getSystemResource(WORKING_IMAGE).toURI()));
			System.out.println("Width=" + img.getWidth());
			System.out.println("Height=" + img.getHeight());
		} catch (URISyntaxException e) {
			System.out.println("An error occured while trying to open image");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("An error occured while trying to open image");
			e.printStackTrace();
		}

		long tStart = System.currentTimeMillis();
		List<Component> components = astroService.findComponents(img, WORKING_CHANNEL);
		long tEnd = System.currentTimeMillis();
		System.out.println("We found " + components.size() + " component"
				+ (components.size() > 1 ? "s" : "") + " in channel " + WORKING_CHANNEL
				+ ", in " + (tEnd - tStart) + " ms.");
		idx = 0;
		System.out.println("Here are the 5 brightest : ");
		for (Component c : components.subList(0, Math.min(components.size(), 5))) {
			System.out.println(c);
		}
		
		tStart = System.currentTimeMillis();
		List<Triangle> opmSortedTriangles = astroService.buildTrianglesFromComponents(components
				.subList(0, Math.min(components.size(), MAX_COMPONENTS)));
		tEnd = System.currentTimeMillis();
		System.out.println("We made " + opmSortedTriangles.size() + " triangle"
				+ (opmSortedTriangles.size() > 1 ? "s" : "") + " based on discovered components, in "+(tEnd-tStart)+" ms.");
		for (Triangle triangle : opmSortedTriangles.subList(0,
				Math.min(opmSortedTriangles.size(), 8))) {
			System.out.println(triangle);
		}

		// On cherche les étoiles candidates dans le catalogue...
		System.out.println("Loading Spring application context...");
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("/application-context.xml",
				"/jpaContext.xml");

		CatalogService catalogService = appCtx.getBean(CatalogService.class);
		System.out.println("...found catalog service for comparison");

		DeepSkyObject target = catalogService.findObjectByName(TARGET_NAME);
		if (target != null) {

			EquatorialCoordinates upperWesternAreaCorner = new EquatorialCoordinates(
					target.getRightAscension() + HALF_FIELD_WIDE_IN_DEGREES,
					target.getDeclinaison() + HALF_FIELD_WIDE_IN_DEGREES);
			EquatorialCoordinates lowerEasternAreaCorner = new EquatorialCoordinates(
					target.getRightAscension() - HALF_FIELD_WIDE_IN_DEGREES,
					target.getDeclinaison() - HALF_FIELD_WIDE_IN_DEGREES);

			sb = new StringBuilder();
			sb.append("HINT : ");
			sb.append("TARGET = " + target.getName() + " at RA="
					+ new Sexagesimal(target.getRightAscension()).toString(DEGREES) + " / DEC="
					+ new Sexagesimal(target.getDeclinaison()).toString(DEGREES));
			System.out.println(sb.toString());
			System.out.println("Searching for catalog objects contained in area :");
			sb = new StringBuilder();
			sb.append("[RA=");
			sb.append(new Sexagesimal(upperWesternAreaCorner.getRightAscension()).toString(DEGREES));
			sb.append("\t\t\t...\t\t");
			sb.append("RA=");
			sb.append(new Sexagesimal(lowerEasternAreaCorner.getRightAscension()).toString(DEGREES)
					+ "]");
			System.out.println(sb.toString());
			sb = new StringBuilder();
			sb.append("[DEC=");
			sb.append(new Sexagesimal(upperWesternAreaCorner.getDeclinaison()).toString(DEGREES));
			sb.append("\t\t...\t\t");
			sb.append("DEC=");
			sb.append(new Sexagesimal(lowerEasternAreaCorner.getDeclinaison()).toString(DEGREES)
					+ "]");
			System.out.println(sb.toString());
			tStart = System.currentTimeMillis();
			List<CatalogObject> stars = catalogService.findBrightestStarsInArea(
					upperWesternAreaCorner, lowerEasternAreaCorner);
			tEnd = System.currentTimeMillis();
			sb = new StringBuilder();
			idx = 0;
			for (CatalogObject co : stars) {
				if (idx > 0) {
					sb.append(", ");
				}
				sb.append(co.getIdentifier());
				idx++;
			}
			System.out.println("We found " + stars.size() + " catalog object"
					+ (stars.size() > 1 ? "s" : "") + " in "+(tEnd-tStart)+" ms.");
			System.out.println(sb.toString());

			tStart = System.currentTimeMillis();
			List<Triangle> catalogTriangles = astroService.buildTrianglesFromCatalogObjects(stars
					.subList(0, Math.min(stars.size(), MAX_COMPONENTS)));
			tEnd = System.currentTimeMillis();
			System.out.println("We made " + catalogTriangles.size() + " triangle"
					+ (catalogTriangles.size() > 1 ? "s" : "")
					+ " with the objects found in the catalog, in "+(tEnd-tStart)+" ms.");

			// We look for the good interval to start comparison...
			int idxGoodCandidate = 0;
			boolean goodCandidateFound = false;
			for (Triangle catalogTriangle : catalogTriangles) {
				if ((int) catalogTriangle.getLongestShortestSidesRatio() == (int) opmSortedTriangles
						.get(0).getLongestShortestSidesRatio()) {
					goodCandidateFound = true;
					break;
				}
				idxGoodCandidate++;
			}
			System.out.println("Check this : ");
			if (!goodCandidateFound) {
				idxGoodCandidate=0;
			}
			
			for (Triangle triangle : catalogTriangles.subList(idxGoodCandidate,
					Math.min(catalogTriangles.size(), (idxGoodCandidate + 8)))) {
				System.out.println(triangle);
			}
			
			// Looking for good candidates from catalogs...

		}

	}
}
