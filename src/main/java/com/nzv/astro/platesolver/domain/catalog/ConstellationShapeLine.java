package com.nzv.astro.platesolver.domain.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "constellation_shape_line")
public class ConstellationShapeLine {
	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "constellation")
	private Constellation constellation;

	@Column(name="start_right_ascension")
	private double startRightAscension;
	
	@Column(name="start_declinaison")
	private double startDeclinaison;
	
	@Column(name="end_right_ascension")
	private double endRightAscension;
	
	@Column(name="end_declinaison")
	private double endDeclinaison;

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

	public double getStartRightAscension() {
		return startRightAscension;
	}

	public void setStartRightAscension(double startRightAscension) {
		this.startRightAscension = startRightAscension;
	}

	public double getStartDeclinaison() {
		return startDeclinaison;
	}

	public void setStartDeclinaison(double startDeclinaison) {
		this.startDeclinaison = startDeclinaison;
	}

	public double getEndRightAscension() {
		return endRightAscension;
	}

	public void setEndRightAscension(double endRightAscension) {
		this.endRightAscension = endRightAscension;
	}

	public double getEndDeclinaison() {
		return endDeclinaison;
	}

	public void setEndDeclinaison(double endDeclinaison) {
		this.endDeclinaison = endDeclinaison;
	}
}
