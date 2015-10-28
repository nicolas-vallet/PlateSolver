package com.nzv.astro.platesolver.domain.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.nzv.astro.platesolver.domain.CatalogObject;

@Entity
@Table(name = "tycho")
public class TychoStar extends CatalogObject {

	@Id
	@Column(name = "tycho_identifier")
	private String tychoIdentifier;

	@Column(name = "tyc1")
	private String tyc1;

	@Column(name = "tyc2")
	private String tyc2;

	@Column(name = "tyc3")
	private String tyc3;

	@Column(name = "hipparcos_number", nullable = true)
	private Integer hpNumber;

	@Column(name = "right_ascension")
	private Double rightAscension;

	@Column(name = "declinaison")
	private Double declinaison;

	@Column(name = "bt_mag")
	private Double btMag;

	@Column(name = "vt_mag")
	private Double vtMag;

	@Override
	public Double getVisualMagnitude() {
		return this.vtMag;
	}

	@Override
	public double getRightAscension() {
		return this.rightAscension;
	}

	@Override
	public double getDeclinaison() {
		return this.declinaison;
	}

	@Override
	public String getIdentifier() {
		if (this.hpNumber != null) {
			return "HIP"+this.hpNumber;
		} else {
			return "TYC"+this.tychoIdentifier;
		}
	}
	
	@Override
	public String getLabel() {
		return getIdentifier();
	}

	public String getTychoIdentifier() {
		return tychoIdentifier;
	}

	public void setTychoIdentifier(String tychoIdentifier) {
		this.tychoIdentifier = tychoIdentifier;
	}

	public String getTyc1() {
		return tyc1;
	}

	public void setTyc1(String tyc1) {
		this.tyc1 = tyc1;
	}

	public String getTyc2() {
		return tyc2;
	}

	public void setTyc2(String tyc2) {
		this.tyc2 = tyc2;
	}

	public String getTyc3() {
		return tyc3;
	}

	public void setTyc3(String tyc3) {
		this.tyc3 = tyc3;
	}

	public Integer getHpNumber() {
		return hpNumber;
	}

	public void setHpNumber(Integer hpNumber) {
		this.hpNumber = hpNumber;
	}

	public Double getBtMag() {
		return btMag;
	}

	public void setBtMag(Double btMag) {
		this.btMag = btMag;
	}

	public Double getVtMag() {
		return vtMag;
	}

	public void setVtMag(Double vtMag) {
		this.vtMag = vtMag;
	}

	public void setRightAscension(Double rightAscension) {
		this.rightAscension = rightAscension;
	}

	public void setDeclinaison(Double declinaison) {
		this.declinaison = declinaison;
	}

	@Override
	public String getObjectType() {
		return "star of Tycho-2 catalog";
	}
}
