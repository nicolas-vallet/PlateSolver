package com.nzv.astro.platesolver.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nzv.astro.platesolver.domain.catalog.Constellation;
import com.nzv.astro.platesolver.domain.catalog.DeepSkyObject;
import com.nzv.astro.platesolver.domain.catalog.EnumDsoType;

@Repository
public interface DeepSkyObjectRepository extends JpaRepository<DeepSkyObject, Integer> {

	List<DeepSkyObject> findDistinctByConstellationAndMagnitudeLessThanEqualAndTypeInOrderByMagnitudeAsc(
			Constellation constellation, Double limitMagnitude, Collection<EnumDsoType> types);

	List<DeepSkyObject> findDistinctByMagnitudeLessThanEqualAndTypeInOrderByMagnitudeAsc(Double limitMagnitude,
			Collection<EnumDsoType> types);

	Optional<DeepSkyObject> findByName(String name);
}
