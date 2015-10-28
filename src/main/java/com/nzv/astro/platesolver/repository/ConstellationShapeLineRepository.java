package com.nzv.astro.platesolver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nzv.astro.platesolver.domain.catalog.ConstellationShapeLine;

@Repository
public interface ConstellationShapeLineRepository extends JpaRepository<ConstellationShapeLine, Integer> {

}
