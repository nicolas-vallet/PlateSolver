package com.nzv.astro.platesolver.domain.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.nzv.astro.platesolver.domain.CatalogObject;

@Entity
@Table(name = "star")
public class Star extends CatalogObject {

	@Id
	@Column(name = "hr_number")
	private Integer hrNumber;

	@Column(name = "name")
	private String name;

	@Column(name = "durchmusterung_identification")
	private String durchmusterungIdentification;

	@Column(name = "hd_number")
	private Integer hdNumber;

	@Column(name = "sao_number")
	private Integer saoNumber;

	@Column(name = "fk5_number")
	private Integer fk5Number;

	@Column(name = "ir_source", columnDefinition = "tinyint(1)")
	private boolean irSource;

	@Column(name = "ads_number")
	private String adsNumber;

	@Column(name = "ads_number_component")
	private String adsNumberComponent;

	@Column(name = "variable_id")
	private String variableStarIdentification;

	@Column(name = "RAh1900")
	private Integer rightAscension1900Hour;

	@Column(name = "RAm1900")
	private Integer rightAscention1900Minute;

	@Column(name = "RAs1900")
	private Double rightAscension1900Second;

	@Column(name = "DEsignus1900", columnDefinition = "char")
	private String declinaison1900Signus;

	@Column(name = "DEd1900")
	private Integer declinaison1900Degree;

	@Column(name = "DEm1900")
	private Integer declinaison1900Minute;

	@Column(name = "DEs1900")
	private Double declinaison1900Second;

	@Column(name = "RAh")
	private Integer rightAscensionHour;

	@Column(name = "RAm")
	private Integer rightAscensionMinute;

	@Column(name = "RAs")
	private Double rightAscensionSecond;
	
	@Formula("RAh * 15 + (RAm * 15 / 60) + (RAs * 15 / 3600) ")
	private Double rightAscension;
	
	@Formula("CAST(CONCAT(DEsignus, (DEd + (DEm / 60) + (DEs / 3600))) AS DECIMAL(10, 7))")
	private Double declinaison;

	@Column(name = "DEsignus", columnDefinition = "char")
	private String declinaisonSignus;

	@Column(name = "DEd")
	private Integer declinaisonDegree;

	@Column(name = "DEm")
	private Integer declinaisonMinute;

	@Column(name = "DEs")
	private Double declinaisonSecond;

	@Column(name = "Glon")
	private Double galacticLongitude;

	@Column(name = "Glat")
	private Double galacticLatitude;

	@Column(name = "Vmag")
	private Double visualMagnitude;

	@Column(name = "B_Vmag")
	private Double bvMag;

	@Column(name = "B_Vmag_uncertain", columnDefinition = "tinyint(1)")
	private boolean uncertainBvMag;

	@Column(name = "U_Bmag")
	private Double ubMag;

	@Column(name = "U_Bmag_uncertain", columnDefinition = "tinyint(1)")
	private boolean uncertainUbMag;

	@Column(name = "R_Imag")
	private Double riMag;

	@Column(name = "spectral_type")
	private String spectralType;

	@Column(name = "propermotion_RA")
	private Double properMotionRa;

	@Column(name = "propermotion_DE")
	private Double properMotionDec;

	@Column(name = "parallax")
	private Double parallax;

	@Column(name = "radialvelocity")
	private Integer radialVelocity;

	@Column(name = "Dmag")
	private Double companionMagnitudeDifference;

	@Column(name = "separation")
	private Double companionSeparation;

	@Column(name = "multiple_ID")
	private String companionIdentification;

	@Column(name = "multiple_count")
	private Integer companionCount;

	public Integer getHrNumber() {
		return hrNumber;
	}

	public void setHrNumber(Integer hrNumber) {
		this.hrNumber = hrNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDurchmusterungIdentification() {
		return durchmusterungIdentification;
	}

	public void setDurchmusterungIdentification(
			String durchmusterungIdentification) {
		this.durchmusterungIdentification = durchmusterungIdentification;
	}

	public Integer getHdNumber() {
		return hdNumber;
	}

	public void setHdNumber(Integer hdNumber) {
		this.hdNumber = hdNumber;
	}

	public Integer getSaoNumber() {
		return saoNumber;
	}

	public void setSaoNumber(Integer saoNumber) {
		this.saoNumber = saoNumber;
	}

	public Integer getFk5Number() {
		return fk5Number;
	}

	public void setFk5Number(Integer fk5Number) {
		this.fk5Number = fk5Number;
	}

	public boolean isIrSource() {
		return irSource;
	}

	public void setIrSource(boolean irSource) {
		this.irSource = irSource;
	}

	public String getAdsNumber() {
		return adsNumber;
	}

	public void setAdsNumber(String adsNumber) {
		this.adsNumber = adsNumber;
	}

	public String getAdsNumberComponent() {
		return adsNumberComponent;
	}

	public void setAdsNumberComponent(String adsNumberComponent) {
		this.adsNumberComponent = adsNumberComponent;
	}

	public String getVariableStarIdentification() {
		return variableStarIdentification;
	}

	public void setVariableStarIdentification(String variableStarIdentification) {
		this.variableStarIdentification = variableStarIdentification;
	}

	public Integer getRightAscension1900Hour() {
		return rightAscension1900Hour;
	}

	public void setRightAscension1900Hour(Integer rightAscension1900Hour) {
		this.rightAscension1900Hour = rightAscension1900Hour;
	}

	public Integer getRightAscention1900Minute() {
		return rightAscention1900Minute;
	}

	public void setRightAscention1900Minute(Integer rightAscention1900Minute) {
		this.rightAscention1900Minute = rightAscention1900Minute;
	}

	public Double getRightAscension1900Second() {
		return rightAscension1900Second;
	}

	public void setRightAscension1900Second(Double rightAscension1900Second) {
		this.rightAscension1900Second = rightAscension1900Second;
	}

	public String getDeclinaison1900Signus() {
		return declinaison1900Signus;
	}

	public void setDeclinaison1900Signus(String declinaison1900Signus) {
		this.declinaison1900Signus = declinaison1900Signus;
	}

	public Integer getDeclinaison1900Degree() {
		return declinaison1900Degree;
	}

	public void setDeclinaison1900Degree(Integer declinaison1900Degree) {
		this.declinaison1900Degree = declinaison1900Degree;
	}

	public Integer getDeclinaison1900Minute() {
		return declinaison1900Minute;
	}

	public void setDeclinaison1900Minute(Integer declinaison1900Minute) {
		this.declinaison1900Minute = declinaison1900Minute;
	}

	public Double getDeclinaison1900Second() {
		return declinaison1900Second;
	}

	public void setDeclinaison1900Second(Double declinaison1900Second) {
		this.declinaison1900Second = declinaison1900Second;
	}

	public Integer getRightAscensionHour() {
		return rightAscensionHour;
	}

	public void setRightAscensionHour(Integer rightAscensionHour) {
		this.rightAscensionHour = rightAscensionHour;
	}

	public Integer getRightAscensionMinute() {
		return rightAscensionMinute;
	}

	public void setRightAscensionMinute(Integer rightAscensionMinute) {
		this.rightAscensionMinute = rightAscensionMinute;
	}

	public Double getRightAscensionSecond() {
		return rightAscensionSecond;
	}

	public void setRightAscensionSecond(Double rightAscensionSecond) {
		this.rightAscensionSecond = rightAscensionSecond;
	}

	public String getDeclinaisonSignus() {
		return declinaisonSignus;
	}

	public void setDeclinaisonSignus(String declinaisonSignus) {
		this.declinaisonSignus = declinaisonSignus;
	}

	public Integer getDeclinaisonDegree() {
		return declinaisonDegree;
	}

	public void setDeclinaisonDegree(Integer declinaisonDegree) {
		this.declinaisonDegree = declinaisonDegree;
	}

	public Integer getDeclinaisonMinute() {
		return declinaisonMinute;
	}

	public void setDeclinaisonMinute(Integer declinaisonMinute) {
		this.declinaisonMinute = declinaisonMinute;
	}

	public Double getDeclinaisonSecond() {
		return declinaisonSecond;
	}

	public void setDeclinaisonSecond(Double declinaisonSecond) {
		this.declinaisonSecond = declinaisonSecond;
	}

	public Double getGalacticLongitude() {
		return galacticLongitude;
	}

	public void setGalacticLongitude(Double galacticLongitude) {
		this.galacticLongitude = galacticLongitude;
	}

	public Double getGalacticLatitude() {
		return galacticLatitude;
	}

	public void setGalacticLatitude(Double galacticLatitude) {
		this.galacticLatitude = galacticLatitude;
	}

	public Double getVisualMagnitude() {
		return visualMagnitude;
	}

	public void setVisualMagnitude(Double visualMagnitude) {
		this.visualMagnitude = visualMagnitude;
	}

	public Double getBvMag() {
		return bvMag;
	}

	public void setBvMag(Double bvMag) {
		this.bvMag = bvMag;
	}

	public boolean isUncertainBvMag() {
		return uncertainBvMag;
	}

	public void setUncertainBvMag(boolean uncertainBvMag) {
		this.uncertainBvMag = uncertainBvMag;
	}

	public Double getUbMag() {
		return ubMag;
	}

	public void setUbMag(Double ubMag) {
		this.ubMag = ubMag;
	}

	public boolean isUncertainUbMag() {
		return uncertainUbMag;
	}

	public void setUncertainUbMag(boolean uncertainUbMag) {
		this.uncertainUbMag = uncertainUbMag;
	}

	public Double getRiMag() {
		return riMag;
	}

	public void setRiMag(Double riMag) {
		this.riMag = riMag;
	}

	public String getSpectralType() {
		return spectralType;
	}

	public void setSpectralType(String spectralType) {
		this.spectralType = spectralType;
	}

	public Double getProperMotionRa() {
		return properMotionRa;
	}

	public void setProperMotionRa(Double properMotionRa) {
		this.properMotionRa = properMotionRa;
	}

	public Double getProperMotionDec() {
		return properMotionDec;
	}

	public void setProperMotionDec(Double properMotionDec) {
		this.properMotionDec = properMotionDec;
	}


	public Double getParallax() {
		return parallax;
	}

	public void setParallax(Double parallax) {
		this.parallax = parallax;
	}

	public Integer getRadialVelocity() {
		return radialVelocity;
	}

	public void setRadialVelocity(Integer radialVelocity) {
		this.radialVelocity = radialVelocity;
	}

	public Double getCompanionMagnitudeDifference() {
		return companionMagnitudeDifference;
	}

	public void setCompanionMagnitudeDifference(
			Double companionMagnitudeDifference) {
		this.companionMagnitudeDifference = companionMagnitudeDifference;
	}

	public Double getCompanionSeparation() {
		return companionSeparation;
	}

	public void setCompanionSeparation(Double companionSeparation) {
		this.companionSeparation = companionSeparation;
	}

	public String getCompanionIdentification() {
		return companionIdentification;
	}

	public void setCompanionIdentification(String companionIdentification) {
		this.companionIdentification = companionIdentification;
	}

	public Integer getCompanionCount() {
		return companionCount;
	}

	public void setCompanionCount(Integer companionCount) {
		this.companionCount = companionCount;
	}

	@Override
	public String toString() {
		return "BrightStar [HR=" + hrNumber + ", Name=" + name + ", HD="
				+ hdNumber + ", SAO=" + saoNumber + ", RA="
				+ rightAscensionHour + "h " + rightAscensionMinute + "m "
				+ rightAscensionSecond + "s " + ", DEC=" + declinaisonSignus
				+ declinaisonDegree + "° " + declinaisonMinute + "m "
				+ declinaisonSecond + "s " + ", Vmag=" + visualMagnitude
				+ ", BVmag=" + bvMag + ", UBmag=" + ubMag + "]";
	}

	/*
	 * Convenient method to get the RA as a decimal value of degrees.
	 */
	@Override
	public double getRightAscension() {
		return rightAscension;
	}

	/*
	 * Convenient method to get the DEC as a decimal value of degrees.
	 */
	@Override
	public double getDeclinaison() {
		return declinaison;
	}

	@Override
	public String getIdentifier() {
		String result = "HR" + getHrNumber();
		if (getName() != null && !getName().isEmpty()) {
			result += " ("+getName()+")";
		}
		return result;
	}
	
	@Override
	public String getLabel() {
		return getIdentifier();
	}

	@Override
	public String getObjectType() {
		String result = getSpectralType() != null ? getSpectralType() + " "
				: "";
		result += " star";
		return result;
	}
}
