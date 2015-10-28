package com.nzv.astro.platesolver.run;

import static com.nzv.astro.ephemeris.Sexagesimal.SexagesimalType.DEGREES;
import static com.nzv.astro.ephemeris.Sexagesimal.SexagesimalType.HOURS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nzv.astro.ephemeris.Sexagesimal;
import com.nzv.astro.platesolver.domain.catalog.TychoStar;
import com.nzv.astro.platesolver.service.CatalogService;

public class Sanbox {

	public static void main(String[] args) {
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("application-context.xml",
				"jpaContext.xml");
		CatalogService catalogService = appCtx.getBean(CatalogService.class);
		List<TychoStar> stars = new ArrayList<TychoStar>();
		List<Integer> ids = Arrays.asList(416, 429, 51, 769, 384, 1, 511, 383, 717, 766, 565, 223,
				56, 604, 628, 1121, 1087, 868, 1437);
		for (Integer hipId : ids) {
			stars.add(catalogService.findStarByHipparcosNumber(hipId));
		}

		for (TychoStar star : stars) {
			Sexagesimal ra = new Sexagesimal(star.getRightAscension());
			Sexagesimal dec = new Sexagesimal(star.getDeclinaison());
			System.out
					.println("-----------------------------------------------------------------------");
			System.out.println("HIP" + star.getHpNumber() + " / TYC=" + star.getTychoIdentifier());
			System.out.println("RA=" + ra.toString(HOURS));
			System.out.println("DEC=" + dec.toString(DEGREES));
		}
	}
}
