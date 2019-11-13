package com.nzv.astro.platesolver.service.impl;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.nzv.astro.ephemeris.coordinate.impl.EquatorialCoordinates;
import com.nzv.astro.platesolver.domain.CatalogObject;
import com.nzv.astro.platesolver.domain.catalog.DeepSkyObject;
import com.nzv.astro.platesolver.domain.catalog.Star;
import com.nzv.astro.platesolver.domain.catalog.TychoStar;
import com.nzv.astro.platesolver.service.CatalogService;

public class MockedCatalogServiceImpl implements CatalogService {

	private static Map<String, Star> STAR_BY_NAME = new HashMap<>();
	private static Map<Integer, Star> STAR_BY_HR_NUMBER = new HashMap<>();
	private static Map<Integer, Star> STAR_BY_HD_NUMBER = new HashMap<>();
	private static Map<Integer, Star> STAR_BY_SAO_NUMBER = new HashMap<>();
	private static Map<Integer, Star> STAR_BY_FK5_NUMBER = new HashMap<>();
	private static Map<String, DeepSkyObject> OBJECT_BY_NAME = new HashMap<>();
	private static Map<Integer, DeepSkyObject> OBJECT_BY_ID = new HashMap<>();
	private static Map<String, TychoStar> TYCHO_STAR_BY_COMPOSITE_ID = new HashMap<>();
	private static Map<Integer, TychoStar> TYCHO_STAR_BY_HIPPARCOS_NUMBER = new HashMap<>();
	private static List<CatalogObject> MOCKED_LIST_CATALOG_OBJECT = new ArrayList<>();

	static {
		initializeReferenceData();
	}

	private static void initializeReferenceData() {
	}

	@Override
	public Optional<Star> findStarByHrNumber(Integer hrNumber) {
		return ofNullable(STAR_BY_HR_NUMBER.get(hrNumber));
	}

	@Override
	public Optional<Star> findStarByName(String name) {
		return ofNullable(STAR_BY_NAME.get(name));
	}

	@Override
	public Optional<Star> findStarByHdNumber(Integer hdNumber) {
		return ofNullable(STAR_BY_HD_NUMBER.get(hdNumber));
	}

	@Override
	public Optional<Star> findStarBySaoNumber(Integer saoNumber) {
		return ofNullable(STAR_BY_SAO_NUMBER.get(saoNumber));
	}

	@Override
	public Optional<Star> findStarByFk5Number(Integer fk5Number) {
		return ofNullable(STAR_BY_FK5_NUMBER.get(fk5Number));
	}

	@Override
	public Optional<DeepSkyObject> findObjectByName(String name) {
		return ofNullable(OBJECT_BY_NAME.get(name));
	}

	@Override
	public Optional<DeepSkyObject> findObjectById(Integer id) {
		return ofNullable(OBJECT_BY_ID.get(id));
	}

	@Override
	public Optional<TychoStar> findStarByTychoIdentifier(int tyc1, int tyc2, int tyc3) {
		return ofNullable(TYCHO_STAR_BY_COMPOSITE_ID.get(format("%d%d%d", tyc1, tyc2, tyc3)));
	}

	@Override
	public Optional<TychoStar> findStarByHipparcosNumber(Integer hipNumber) {
		return ofNullable(TYCHO_STAR_BY_HIPPARCOS_NUMBER.get(hipNumber));
	}

	@Override
	public List<CatalogObject> findBrightestStarsInArea(EquatorialCoordinates upperWesternAreaCorner,
			EquatorialCoordinates lowerEasternAreaCorner) {
		return MOCKED_LIST_CATALOG_OBJECT;
	}
}