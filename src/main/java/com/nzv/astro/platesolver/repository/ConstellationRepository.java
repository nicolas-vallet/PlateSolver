package com.nzv.astro.platesolver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nzv.astro.platesolver.domain.catalog.Constellation;

@Repository
public interface ConstellationRepository extends JpaRepository<Constellation, String>{
	
	public Constellation findByCode(String constellationCode);
	
	@Query("select c from Constellation c left join fetch c.boundaryPoints where c.code = ?1 ")
	public Constellation findByCodeFetchingBoundary(String constellationCode);
	
	@Query("select distinct c from Constellation c left join fetch c.boundaryPoints order by c.code asc")
	public List<Constellation> findAllFetchingBoundaries();

}
