package com.nzv.astro.platesolver.service.impl;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nzv.astro.ephemeris.coordinate.impl.EquatorialCoordinates;
import com.nzv.astro.platesolver.domain.CatalogObject;
import com.nzv.astro.platesolver.domain.catalog.DeepSkyObject;
import com.nzv.astro.platesolver.domain.catalog.Star;
import com.nzv.astro.platesolver.domain.catalog.TychoStar;
import com.nzv.astro.platesolver.repository.DeepSkyObjectRepository;
import com.nzv.astro.platesolver.repository.StarRepository;
import com.nzv.astro.platesolver.repository.TychoStarRepository;
import com.nzv.astro.platesolver.service.CatalogService;

@Service
@Transactional(readOnly = true)
public class CatalogServiceImpl implements CatalogService {

	@Autowired
	private StarRepository starRepository;

	@Autowired
	private TychoStarRepository tychoRepository;

	@Autowired
	private DeepSkyObjectRepository dsoRepository;

	@Override
	public Star findStarByHrNumber(Integer hrNumber) {
		return starRepository.findByHrNumber(hrNumber);
	}

	@Override
	public TychoStar findStarByHipparcosNumber(Integer hipNumber) {
		return tychoRepository.findByHpNumber(hipNumber);
	}

	@Override
	public TychoStar findStarByTychoIdentifier(int tyc1, int tyc2, int tyc3) {
		return tychoRepository.findByTychoIdentifier(format("%04d", tyc1) + "."
				+ format("%05d", tyc2) + "." + format("%01d", tyc3));
	}

	@Override
	public Star findStarByName(String name) {
		return starRepository.findByName(name);
	}

	@Override
	public Star findStarByHdNumber(Integer hdNumber) {
		return starRepository.findByHdNumber(hdNumber);
	}

	@Override
	public Star findStarBySaoNumber(Integer saoNumber) {
		return starRepository.findBySaoNumber(saoNumber);
	}

	@Override
	public Star findStarByFk5Number(Integer fk5Number) {
		return starRepository.findByFk5Number(fk5Number);
	}

	@Override
	public DeepSkyObject findObjectByName(String name) {
		return dsoRepository.findByName(name);
	}

	@Override
	public DeepSkyObject findObjectById(Integer id) {
		return dsoRepository.findOne(id);
	}

	@Override
	public List<CatalogObject> findBrightestStarsInArea(
			EquatorialCoordinates upperWesternAreaCorner,
			EquatorialCoordinates lowerEasternAreaCorner) {
		List<CatalogObject> result = new ArrayList<CatalogObject>();

		// Searching for brightest stars in this area...
		result.addAll(tychoRepository.findByEQCoordinatesArea(
				upperWesternAreaCorner.getRightAscension(),
				upperWesternAreaCorner.getDeclinaison(),
				lowerEasternAreaCorner.getRightAscension(), lowerEasternAreaCorner.getDeclinaison()));
		return result;
	}

}
