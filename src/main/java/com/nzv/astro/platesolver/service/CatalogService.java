package com.nzv.astro.platesolver.service;

import java.util.List;
import java.util.Optional;

import com.nzv.astro.ephemeris.coordinate.impl.EquatorialCoordinates;
import com.nzv.astro.platesolver.domain.CatalogObject;
import com.nzv.astro.platesolver.domain.catalog.DeepSkyObject;
import com.nzv.astro.platesolver.domain.catalog.Star;
import com.nzv.astro.platesolver.domain.catalog.TychoStar;

public interface CatalogService {

	public Optional<Star> findStarByHrNumber(Integer hrNumber);

	public Optional<Star> findStarByName(String name);

	public Optional<Star> findStarByHdNumber(Integer hdNumber);

	public Optional<Star> findStarBySaoNumber(Integer saoNumber);

	public Optional<Star> findStarByFk5Number(Integer fk5Number);

	public Optional<DeepSkyObject> findObjectByName(String name);

	public Optional<DeepSkyObject> findObjectById(Integer id);

	public Optional<TychoStar> findStarByTychoIdentifier(int tyc1, int tyc2, int tyc3);

	public Optional<TychoStar> findStarByHipparcosNumber(Integer hipNumber);

	public List<CatalogObject> findBrightestStarsInArea(EquatorialCoordinates upperWesternAreaCorner,
			EquatorialCoordinates lowerEasternAreaCorner);
}
