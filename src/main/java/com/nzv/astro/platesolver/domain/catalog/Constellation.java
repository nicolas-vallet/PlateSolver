package com.nzv.astro.platesolver.domain.catalog;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.nzv.astro.ephemeris.coordinate.impl.EquatorialCoordinates;

@Entity
@Table(name = "constellation")
public class Constellation {

	@Id
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "center_ra")
	private Double centerRightAscension;

	@Column(name = "center_dec")
	private Double centerDeclinaison;

	@Column(name = "area")
	private Double areaInSquareDegrees;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "constellation")
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy
	private List<ConstellationBoundaryPoint> boundaryPoints;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "constellation")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<ConstellationShapeLine> shapeLines;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCenterRightAscension() {
		return centerRightAscension;
	}

	public void setCenterRightAscension(Double centerRightAscension) {
		this.centerRightAscension = centerRightAscension;
	}

	public Double getCenterDeclinaison() {
		return centerDeclinaison;
	}

	public void setCenterDeclinaison(Double centerDeclinaison) {
		this.centerDeclinaison = centerDeclinaison;
	}

	public EquatorialCoordinates getCenterEqCoordinates() {
		return new EquatorialCoordinates(centerRightAscension, centerDeclinaison);
	}

	public Double getAreaInSquareDegrees() {
		return areaInSquareDegrees;
	}

	public void setAreaInSquareDegrees(Double areaInSquareDegrees) {
		this.areaInSquareDegrees = areaInSquareDegrees;
	}

	public List<ConstellationBoundaryPoint> getBoundaryPoints() {
		return boundaryPoints;
	}

	public void setBoundaryPoints(List<ConstellationBoundaryPoint> boundaryPoints) {
		this.boundaryPoints = boundaryPoints;
	}

	public Set<ConstellationShapeLine> getShapeLines() {
		return shapeLines;
	}

	public void setShapeLines(Set<ConstellationShapeLine> shapeLines) {
		this.shapeLines = shapeLines;
	}

	@Override
	public String toString() {
		return "Constellation [code=" + code + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Constellation other = (Constellation) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	public EquatorialCoordinates getUpperWesternMapLimit(double... marginInDegree) {
		BoundingBoxCoordinates boundaryBoxCoordinate = new BoundingBoxCoordinates(
				this.boundaryPoints, isSpreadOnBothSidesOfRightAscensionOriginAxis());
		return new EquatorialCoordinates(boundaryBoxCoordinate.getMaxRA(),
				boundaryBoxCoordinate.getMaxDEC());
	}

	public EquatorialCoordinates getLowerEasternMapLimit(double... marginInDegree) {
		BoundingBoxCoordinates boundaryBoxCoordinate = new BoundingBoxCoordinates(
				this.boundaryPoints, isSpreadOnBothSidesOfRightAscensionOriginAxis());
		return new EquatorialCoordinates(boundaryBoxCoordinate.getMinRA(),
				boundaryBoxCoordinate.getMinDEC());
	}

	public boolean isSpreadOnBothSidesOfRightAscensionOriginAxis() {
		boolean raValuesInFirstQuadrant = false;
		boolean raValuesInSecondQuadrant = false;
		boolean raValuesInThirdQuadrant = false;
		boolean raValuesInForthQuadrant = false;

		for (ConstellationBoundaryPoint point : boundaryPoints) {
			if (!raValuesInFirstQuadrant
					&& cos(toRadians(point.getRightAscensionAsDecimalDegrees())) >= 0
					&& sin(toRadians(point.getRightAscensionAsDecimalDegrees())) >= 0) {
				raValuesInFirstQuadrant = true;
			}
			if (!raValuesInSecondQuadrant
					&& cos(toRadians(point.getRightAscensionAsDecimalDegrees())) < 0
					&& sin(toRadians(point.getRightAscensionAsDecimalDegrees())) >= 0) {
				raValuesInSecondQuadrant = true;
			}
			if (!raValuesInThirdQuadrant
					&& cos(toRadians(point.getRightAscensionAsDecimalDegrees())) < 0
					&& sin(toRadians(point.getRightAscensionAsDecimalDegrees())) < 0) {
				raValuesInThirdQuadrant = true;
			}
			if (!raValuesInForthQuadrant
					&& cos(toRadians(point.getRightAscensionAsDecimalDegrees())) >= 0
					&& sin(toRadians(point.getRightAscensionAsDecimalDegrees())) < 0) {
				raValuesInForthQuadrant = true;
			}

			// FIXME
			if (raValuesInFirstQuadrant && raValuesInForthQuadrant) {
				break;
			}
		}
		return (raValuesInFirstQuadrant && raValuesInForthQuadrant);
	}

	private class BoundingBoxCoordinates {
		private Double minRA = null;
		private Double maxRA = null;
		private Double minDEC = null;
		private Double maxDEC = null;

		public BoundingBoxCoordinates(List<ConstellationBoundaryPoint> boundaryPoints,
				boolean spreadOnBothSidesOfRightAscensionOrigin) {

			if (spreadOnBothSidesOfRightAscensionOrigin) {
				minRA = maxRA = 0d;
			}
			for (ConstellationBoundaryPoint point : boundaryPoints) {
				if (!spreadOnBothSidesOfRightAscensionOrigin) {
					if (minRA == null || point.getRightAscensionAsDecimalDegrees() < minRA) {
						minRA = point.getRightAscensionAsDecimalDegrees();
					}
					if (maxRA == null || point.getRightAscensionAsDecimalDegrees() > maxRA) {
						maxRA = point.getRightAscensionAsDecimalDegrees();
					}
				} else {
					if (sin(toRadians(point.getRightAscensionAsDecimalDegrees())) >= 0) {
						// RA between 0° and 180°
						if (cos(toRadians(point.getRightAscensionAsDecimalDegrees())) < cos(toRadians(maxRA))) {
							maxRA = point.getRightAscensionAsDecimalDegrees();
						}
					} else {
						// RA beetween 180° and 360°
						if (cos(toRadians(point.getRightAscensionAsDecimalDegrees())) < cos(toRadians(minRA))) {
							minRA = point.getRightAscensionAsDecimalDegrees();
						}
					}
				}
				if (minDEC == null || point.getDeclinaisonAsDecimalDegrees() < minDEC) {
					minDEC = point.getDeclinaisonAsDecimalDegrees();
				}
				if (maxDEC == null || point.getDeclinaisonAsDecimalDegrees() > maxDEC) {
					maxDEC = point.getDeclinaisonAsDecimalDegrees();
				}
			}
		}

		public Double getMinRA() {
			return minRA;
		}

		public Double getMaxRA() {
			return maxRA;
		}

		public Double getMinDEC() {
			return minDEC;
		}

		public Double getMaxDEC() {
			return maxDEC;
		}
	}

}
