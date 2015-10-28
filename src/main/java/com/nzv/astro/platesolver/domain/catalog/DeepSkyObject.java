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

import org.hibernate.annotations.Formula;

import com.nzv.astro.platesolver.domain.CatalogObject;

@Entity
@Table(name = "deep_sky_object")
public class DeepSkyObject extends CatalogObject {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "other_name")
	private String otherName;

	@Column(name = "object_type")
	@Enumerated(EnumType.STRING)
	private EnumDsoType type;

	@ManyToOne
	@JoinColumn(name="constellation")
	private Constellation constellation;

	@Column(name = "RAh")
	private Integer rightAscensionHour;

	@Column(name = "RAm")
	private Double rightAscensionMinute;
	
	@Formula("(RAh * 15) + (RAm * 15 / 60)")
	private Double rightAscension;

	@Column(name = "DEd")
	private Integer declinaisonDegree;

	@Column(name = "DEm")
	private Double declinaisonMinute;
	
	@Formula("IF(DEd<0 , (DEd - (DEm / 60)), (DEd + (DEm / 60)))")
	private Double declinaison;

	@Column(name = "magnitude")
	private Double magnitude;
	
	@Column(name = "surface_brigthness")
	private Double surfaceBrightness;

	@Column(name = "u2k_chart")
	private Integer u2kChartNumber;

	@Column(name = "ti_chart")
	private Integer tiChartNumber;

	@Column(name = "size_max")
	private Double maxSize;

	@Column(name = "size_max_unit", columnDefinition="enum")
	@Enumerated(EnumType.STRING)
	private SizeUnit maxSizeUnit;

	@Column(name = "size_min")
	private Double minSize;

	@Column(name = "size_min_unit", columnDefinition="enum")
	@Enumerated(EnumType.STRING)
	private SizeUnit minSizeUnit;

	@Column(name = "position_angle")
	private Double positionAngle;

	@Column(name = "class")
	private String classtype;

	@Column(name = "stars_count")
	private Integer starsCount;

	@Column(name = "brightest_star_mag")
	private Double brightestStarMagnitude;

	@Column(name = "in_bestngc_catalog", columnDefinition = "tinyint(1)")
	private boolean inBestNgcCatalog;
	
	@Column(name = "in_caldwell_catalog", columnDefinition = "tinyint(1)")
	private boolean inCaldwellCalatalog;
	
	@Column(name = "in_herschel_catalog", columnDefinition = "tinyint(1)")
	private boolean inHerschelCatalog;
	
	@Column(name = "in_messier_catalog", columnDefinition = "tinyint(1)")
	private boolean inMessierCatalog;

	@Column(name = "ngc_desc")
	private String ngcDescription;

	@Column(name = "notes")
	private String notes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public EnumDsoType getType() {
		return type;
	}

	public void setType(EnumDsoType type) {
		this.type = type;
	}

	public Constellation getConstellation() {
		return constellation;
	}

	public void setConstellation(Constellation constellation) {
		this.constellation = constellation;
	}

	public Integer getRightAscensionHour() {
		return rightAscensionHour;
	}

	public void setRightAscensionHour(Integer rightAscensionHour) {
		this.rightAscensionHour = rightAscensionHour;
	}

	public Double getRightAscensionMinute() {
		return rightAscensionMinute;
	}

	public void setRightAscensionMinute(Double rightAscensionMinute) {
		this.rightAscensionMinute = rightAscensionMinute;
	}

	public Integer getDeclinaisonDegree() {
		return declinaisonDegree;
	}

	public void setDeclinaisonDegree(Integer declinaisonDegree) {
		this.declinaisonDegree = declinaisonDegree;
	}

	public Double getDeclinaisonMinute() {
		return declinaisonMinute;
	}

	public void setDeclinaisonMinute(Double declinaisonMinute) {
		this.declinaisonMinute = declinaisonMinute;
	}

	public Double getMagnitude() {
		return magnitude;
	}

	public double getRightAscension() {
		return rightAscension;
	}

	public double getDeclinaison() {
		return declinaison;
	}

	public void setMagnitude(Double magnitude) {
		this.magnitude = magnitude;
	}

	public Double getSurfaceBrightness() {
		return surfaceBrightness;
	}

	public void setSurfaceBrightness(Double surfaceBrightness) {
		this.surfaceBrightness = surfaceBrightness;
	}

	public Integer getU2kChartNumber() {
		return u2kChartNumber;
	}

	public void setU2kChartNumber(Integer u2kChartNumber) {
		this.u2kChartNumber = u2kChartNumber;
	}

	public Integer getTiChartNumber() {
		return tiChartNumber;
	}

	public void setTiChartNumber(Integer tiChartNumber) {
		this.tiChartNumber = tiChartNumber;
	}

	public Double getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Double maxSize) {
		this.maxSize = maxSize;
	}

	public SizeUnit getMaxSizeUnit() {
		return maxSizeUnit;
	}

	public void setMaxSizeUnit(SizeUnit maxSizeUnit) {
		this.maxSizeUnit = maxSizeUnit;
	}

	public Double getMinSize() {
		return minSize;
	}

	public void setMinSize(Double minSize) {
		this.minSize = minSize;
	}

	public SizeUnit getMinSizeUnit() {
		return minSizeUnit;
	}

	public void setMinSizeUnit(SizeUnit minSizeUnit) {
		this.minSizeUnit = minSizeUnit;
	}
	
	public String getSizeHumanReadable() {
		StringBuffer sb = new StringBuffer();
		if (minSize != null && maxSize != null) {
			sb.append(""+maxSize+maxSizeUnit);
			sb.append(" x ");
			sb.append(""+minSize+minSizeUnit);
		} else {
			if (maxSize != null) {
				sb.append(""+maxSize+maxSizeUnit);
			}
			if (minSize != null) {
				sb.append(""+minSize+minSizeUnit);
			}
		}
		return sb.toString();
	}

	public Double getPositionAngle() {
		return positionAngle;
	}

	public void setPositionAngle(Double positionAngle) {
		this.positionAngle = positionAngle;
	}

	public String getClasstype() {
		return classtype;
	}

	public void setClasstype(String classtype) {
		this.classtype = classtype;
	}

	public Integer getStarsCount() {
		return starsCount;
	}

	public void setStarsCount(Integer starsCount) {
		this.starsCount = starsCount;
	}

	public Double getBrightestStarMagnitude() {
		return brightestStarMagnitude;
	}

	public void setBrightestStarMagnitude(Double brightestStarMagnitude) {
		this.brightestStarMagnitude = brightestStarMagnitude;
	}

	public boolean isInBestNgcCatalog() {
		return inBestNgcCatalog;
	}

	public void setInBestNgcCatalog(boolean inBestNgcCatalog) {
		this.inBestNgcCatalog = inBestNgcCatalog;
	}

	public boolean isInCaldwellCalatalog() {
		return inCaldwellCalatalog;
	}

	public void setInCaldwellCalatalog(boolean inCaldwellCalatalog) {
		this.inCaldwellCalatalog = inCaldwellCalatalog;
	}

	public boolean isInHerschelCatalog() {
		return inHerschelCatalog;
	}

	public void setInHerschelCatalog(boolean inHerschelCatalog) {
		this.inHerschelCatalog = inHerschelCatalog;
	}

	public boolean isInMessierCatalog() {
		return inMessierCatalog;
	}

	public void setInMessierCatalog(boolean inMessierCatalog) {
		this.inMessierCatalog = inMessierCatalog;
	}

	public String getNgcDescription() {
		return ngcDescription;
	}

	public void setNgcDescription(String ngcDescription) {
		this.ngcDescription = ngcDescription;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public boolean isAsterism() {
		return EnumDsoType.ASTER == type;
	}
	public boolean isGalaxy() {
		return EnumDsoType.GALXY == type || EnumDsoType.GALCL == type;
	}
	public boolean isGlobularCluster() {
		return EnumDsoType.GLOCL == type || EnumDsoType.GX_GC == type
				|| EnumDsoType.LMCGC == type || EnumDsoType.SMCGC == type;
	}
	public boolean isOpenCluster() {
		return EnumDsoType.CL_NB == type || EnumDsoType.G_C_N == type
				|| EnumDsoType.LMCCN == type || EnumDsoType.LMCOC == type
				|| EnumDsoType.OPNCL == type || EnumDsoType.SMCCN == type
				|| EnumDsoType.SMCOC == type;
	}
	public boolean isPlanetaryNebula() {
		return EnumDsoType.PLNNB == type;
	}
	public boolean isNebula() {
		return EnumDsoType.BRTNB == type || EnumDsoType.CL_NB == type
				|| EnumDsoType.DRKNB == type || EnumDsoType.GX_DN == type
				|| EnumDsoType.G_C_N == type || EnumDsoType.LMCCN == type
				|| EnumDsoType.LMCDN == type || EnumDsoType.SMCCN == type
				|| EnumDsoType.SMCDN == type;
	}
	public boolean isSupernovaRemnant() {
		return EnumDsoType.SNREM == type;
	}
	public boolean isQuasar() {
		return EnumDsoType.QUASR == type;
	}

	@Override
	public String toString() {
		return "DeepSkyObject [name=" + name + ", type=" + type
				+ ", classtype=" + classtype + " in constellation=" + constellation 
				+ ", magnitude=" + magnitude + "]";
	}
	
	@Override
	public String getLabel() {
		return this.name;
	}

	@Override
	public Double getVisualMagnitude() {
		return getMagnitude();
	}

	@Override
	public String getIdentifier() {
		String result = getName();
		if (getOtherName() != null) {
			result += " ("+getOtherName()+")";
		}
		return result;
	}

	@Override
	public String getObjectType() {
		return getType().getComment()+ " "+getClasstype();
	}
}
