package com.nzv.astro.platesolver.domain.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "constellation_boundary_point")
public class ConstellationBoundaryPoint {
public ConstellationBoundaryPoint() {}
	
	protected ConstellationBoundaryPoint(double ra, double dec) {
		this.rightAscensionAsDecimalDegrees = ra;
		this.declinaisonAsDecimalDegrees = dec;
	}

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "constellation")
	private Constellation constellation;

	@Column(name = "right_ascension")
	private Double rightAscensionAsDecimalDegrees;

	@Column(name = "declinaison")
	private Double declinaisonAsDecimalDegrees;

	@Column(name = "point_type", columnDefinition = "enum")
	@Enumerated(EnumType.STRING)
	private ConstellationBoundaryPointType type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Constellation getConstellation() {
		return constellation;
	}

	public void setConstellation(Constellation constellation) {
		this.constellation = constellation;
	}

	public Double getRightAscensionAsDecimalDegrees() {
		return rightAscensionAsDecimalDegrees;
	}

	public void setRightAscensionAsDecimalDegrees(
			Double rightAscensionAsDecimalDegrees) {
		this.rightAscensionAsDecimalDegrees = rightAscensionAsDecimalDegrees;
	}

	public Double getDeclinaisonAsDecimalDegrees() {
		return declinaisonAsDecimalDegrees;
	}

	public void setDeclinaisonAsDecimalDegrees(
			Double declinaisonAsDecimalDegrees) {
		this.declinaisonAsDecimalDegrees = declinaisonAsDecimalDegrees;
	}

	public ConstellationBoundaryPointType getType() {
		return type;
	}

	public void setType(ConstellationBoundaryPointType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ConstellationBoundaryPoint [constellation=" + constellation
				+ ", RA=" + rightAscensionAsDecimalDegrees + ", DEC="
				+ declinaisonAsDecimalDegrees + "]";
	}
}
