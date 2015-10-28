package com.nzv.astro.platesolver.domain.catalog;

public enum EnumDsoType {
	ASTER("Asterism"),
    BRTNB("Bright Nebula"),
    CL_NB("Cluster with Nebulosity"),
    DRKNB("Dark Nebula"),
    GALCL("Galaxy cluster"),
    GALXY("Galaxy"),
    GLOCL("Globular Cluster"),
    GX_DN("Diffuse Nebula in a Galaxy"),
    GX_GC("Globular Cluster in a Galaxy"),
    G_C_N("Cluster with Nebulosity in a Galaxy"),
    LMCCN("Cluster with Nebulosity in the LMC"),
    LMCDN("Diffuse Nebula in the LMC"),
    LMCGC("Globular Cluster in the LMC"),
    LMCOC("Open cluster in the LMC"),
    NONEX("Nonexistent"),
    OPNCL("Open Cluster"),
    PLNNB("Planetary Nebula"),
    SMCCN("Cluster with Nebulosity in the SMC"),
    SMCDN("Diffuse Nebula in the SMC"),
    SMCGC("Globular Cluster in the SMC"),
    SMCOC("Open cluster in the SMC"),
    SNREM("Supernova Remnant"),
    QUASR("Quasar"),
    MSTAR("# Stars (#=1, 2, 3, 4, 5, etc.)");
	
	private String comment;
	
	private EnumDsoType(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}
}
