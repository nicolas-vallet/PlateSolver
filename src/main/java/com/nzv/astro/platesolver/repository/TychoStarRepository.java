package com.nzv.astro.platesolver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nzv.astro.platesolver.domain.catalog.TychoStar;

@Repository
public interface TychoStarRepository extends JpaRepository<TychoStar, String> {

	public Optional<TychoStar> findByTychoIdentifier(String id);

	public Optional<TychoStar> findByHpNumber(Integer hpNumber);

	@Query("SELECT s FROM TychoStar s WHERE s.rightAscension <= ?1 "
			+ "       AND s.declinaison <= ?2 AND s.rightAscension >= ?3 "
			+ "       AND s.declinaison >= ?4 ORDER BY s.vtMag ASC ")
	public List<TychoStar> findByEQCoordinatesArea(double RAmax, double DECmax, double RAmin, double DECmin);
}
