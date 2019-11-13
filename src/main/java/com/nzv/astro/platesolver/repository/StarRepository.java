package com.nzv.astro.platesolver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nzv.astro.platesolver.domain.catalog.Star;

@Repository
public interface StarRepository extends JpaRepository<Star, Integer> {

	public Optional<Star> findByName(String name);

	public Optional<Star> findByHrNumber(Integer id);

	public Optional<Star> findByHdNumber(Integer id);

	public Optional<Star> findBySaoNumber(Integer id);

	public Optional<Star> findByFk5Number(Integer id);

	public List<Star> findByVisualMagnitudeLessThanEqualOrderByVisualMagnitudeAsc(Double limitMagnitude);

	@Query("SELECT s FROM Star s WHERE s.rightAscension <= ?1 "
			+ "       AND s.declinaison <= ?2 AND s.rightAscension >= ?3 "
			+ "       AND s.declinaison >= ?4 AND s.visualMagnitude <= ?5 ")
	public List<Star> findByEQCoordinatesAreaAndVisualMagnitudeLessThan(double RAmax, double DECmax, double RAmin,
			double DECmin, Double limitMagnitude);

}
