package com.nzv.astro.platesolver.domain;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

public class Triangle {

	/**
	 * The vertices of the Triangle.
	 */
	private final AbstractSpatialElement A, B, C;

	/**
	 * The length of the edges (respectively BC, AC and BA.
	 */
	// private final double a, b, c;

	/**
	 * The ratio beetween the longest and the shortest edges.
	 */
	private final double longestShortestEdgesRatio;

	/**
	 * The dot product CB . CA TODO : Should be based on unit vector,
	 */
	private final double dotProduct;

	public Triangle(AbstractSpatialElement vertex1, AbstractSpatialElement vertex2,
			AbstractSpatialElement vertex3) {
		double distanceV1V2 = computeLengthBetweenVertices(vertex1, vertex2);
		double distanceV2V3 = computeLengthBetweenVertices(vertex2, vertex3);
		double distanceV1V3 = computeLengthBetweenVertices(vertex1, vertex3);
		if (distanceV1V2 < distanceV2V3) {
			if (distanceV1V2 < distanceV1V3) {
				if (distanceV2V3 < distanceV1V3) {
					A = vertex2;
					B = vertex1;
					C = vertex3;
				} else {
					A = vertex1;
					B = vertex2;
					C = vertex3;
				}
			} else {
				A = vertex1;
				B = vertex3;
				C = vertex2;
			}
		} else {
			if (distanceV1V2 > distanceV1V3) {
				if (distanceV2V3 < distanceV1V3) {
					A = vertex3;
					B = vertex2;
					C = vertex1;
				} else {
					A = vertex3;
					B = vertex1;
					C = vertex2;
				}
			} else {
				A = vertex2;
				B = vertex3;
				C = vertex1;
			}
		}

		// a = computeLengthBetweenVertices(B, C);
		// b = computeLengthBetweenVertices(A, C);
		// c = computeLengthBetweenVertices(A, C);

		longestShortestEdgesRatio = computeLengthBetweenVertices(B, C)
				/ computeLengthBetweenVertices(B, A);

		dotProduct = computeDotProduct(C, B, A);
	}

	public AbstractSpatialElement getA() {
		return A;
	}

	public AbstractSpatialElement getB() {
		return B;
	}

	public AbstractSpatialElement getC() {
		return C;
	}

	public double getDotProduct() {
		return this.dotProduct;
	}

	public double getLongestShortestSidesRatio() {
		return this.longestShortestEdgesRatio;
	}

	public double getScore() {
		return getDotProduct() * getLongestShortestSidesRatio();
	}
	
	public static double computeLengthBetweenVertices(AbstractSpatialElement v1,
			AbstractSpatialElement v2) {
		if (v1.getCoordinatesSystem() != v2.getCoordinatesSystem()) {
			throw new IllegalArgumentException(
					"the two points are not using the same coordinates system");
		}

		switch (v1.getCoordinatesSystem()) {
		case EQUATORIAL:
			return acos(sin(toRadians(v1.getY())) * sin(toRadians(v2.getY()))
					+ cos(toRadians(v1.getY())) * cos(toRadians(v2.getY()))
					* cos(toRadians(v2.getX()) - toRadians(v1.getX())));
		case CARTESIAN:
		default:
			double xComponent = Math.abs(v1.getX() - v2.getX());
			double yComponent = Math.abs(v1.getY() - v2.getY());
			double distance = Math.sqrt(Math.pow(xComponent, 2) + Math.pow(yComponent, 2));
			return Math.abs(distance);
		}
	}

	public double computeDotProduct(AbstractSpatialElement v, AbstractSpatialElement vu,
			AbstractSpatialElement vv) {
		if (v.getCoordinatesSystem() != vu.getCoordinatesSystem()
				|| v.getCoordinatesSystem() != vv.getCoordinatesSystem()
				|| vu.getCoordinatesSystem() != vv.getCoordinatesSystem()) {
			throw new IllegalArgumentException(
					"the provided vertices are not in the same coordinates system");
		}
		double k = computeLengthBetweenVertices(v, vu);
		double l = computeLengthBetweenVertices(vu, vv);
		double cosV = -1;
		switch (C.getCoordinatesSystem()) {
		case EQUATORIAL:
			double m = computeLengthBetweenVertices(v, vv);
			cosV = (cos(l) - cos(m) * cos(k)) / (sin(m) * sin(k));
			break;
		case CARTESIAN:
		default:
			double xa = vu.getX() - v.getX();
			double xb = vv.getX() - v.getX();
			double ya = vu.getY() - v.getY();
			double yb = vv.getY() - v.getY();
			cosV = (pow(xa + xb, 2) + pow(ya + yb, 2) - (pow(xb, 2) + pow(yb, 2)) - (pow(xa, 2) + pow(
					ya, 2))) / (2 * sqrt(pow(xb, 2) + pow(yb, 2)) * sqrt(pow(xa, 2) + pow(ya, 2)));
		}
		return (k / min(k, l)) * (l / min(k, l)) * cosV;
	}

	@Override
	public String toString() {
		return "Triangle [longestShortestEdgesRatio=" + longestShortestEdgesRatio + ", dotProduct="
				+ dotProduct + "] A(" + A.getLabel() + ") / B(" + B.getLabel() + ") / C("
				+ C.getLabel() + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(dotProduct);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longestShortestEdgesRatio);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Triangle other = (Triangle) obj;
		if (Double.doubleToLongBits(dotProduct) != Double.doubleToLongBits(other.dotProduct))
			return false;
		if (Double.doubleToLongBits(longestShortestEdgesRatio) != Double
				.doubleToLongBits(other.longestShortestEdgesRatio))
			return false;
		return true;
	}

}
