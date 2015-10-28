package com.nzv.astro.platesolver.service;

import java.util.List;

import com.nzv.astro.ephemeris.coordinate.impl.EquatorialCoordinates;
import com.nzv.astro.platesolver.domain.CatalogObject;
import com.nzv.astro.platesolver.domain.catalog.DeepSkyObject;
import com.nzv.astro.platesolver.domain.catalog.Star;
import com.nzv.astro.platesolver.domain.catalog.TychoStar;

public interface CatalogService {

	public Star findStarByHrNumber(Integer hrNumber);

	public Star findStarByName(String name);

	public Star findStarByHdNumber(Integer hdNumber);

	public Star findStarBySaoNumber(Integer saoNumber);

	public Star findStarByFk5Number(Integer fk5Number);

	public DeepSkyObject findObjectByName(String name);
	
	public DeepSkyObject findObjectById(Integer id);
	
	public TychoStar findStarByTychoIdentifier(int tyc1, int tyc2, int tyc3);
	
	public TychoStar findStarByHipparcosNumber(Integer hipNumber);
	
	public List<CatalogObject> findBrightestStarsInArea(EquatorialCoordinates upperWesternAreaCorner,
			EquatorialCoordinates lowerEasternAreaCorner);
}
