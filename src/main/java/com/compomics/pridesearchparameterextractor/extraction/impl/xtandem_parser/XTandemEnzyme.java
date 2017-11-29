package com.compomics.pridesearchparameterextractor.extraction.impl.xtandem_parser;

import com.compomics.util.experiment.biology.Enzyme;
import com.compomics.util.pride.CvTerm;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 * <p>
 * This class handle all the possible enzymes in XTandem parser. Be ware that the terms are controlled manually, if a new enzyme is found
 * this file needs to be updated manually.
 *
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 28/11/2017.
 */
public enum XTandemEnzyme {

    TRYPSIN("[RK]|{P}", createEnzyme("Trypsin", new Character[]{'R','K'}, new Character[]{'P'}, new CvTerm("PSI-MS", "MS:1001251", "Trypsin", null))),
    TRYPSIN_REVERSE("[KR]|{P}", createEnzyme("Trypsin", new Character[]{'R','K'}, new Character[]{'P'}, new CvTerm("PSI-MS", "MS:1001251", "Trypsin", null))),

    LYS_N("[K]|{P}", createEnzyme("Lys-N", new Character[]{'K'}, new Character[]{'P'}, null)),

    ASP_N("[ED]|{P}", createEnzyme("Asp-N (ambic)", new Character[]{'E', 'D'}, new Character[]{'P'}, new CvTerm("PSI-MS", "MS:1001305", "Asp-N_ambic", null))),
    ASP_N_AMBIC("[DE]|{P}", createEnzyme("Asp-N (ambic)", new Character[]{'E', 'D'}, new Character[]{'P'}, new CvTerm("PSI-MS", "MS:1001305", "Asp-N_ambic", null))),

    ASP_N_REVERSE("[X]|[D]", createEnzyme("Asp-N", new Character[]{'D'}, new Character[]{'\u0000'}, new CvTerm("PSI-MS", "MS:1001304", "Asp-N", null))),
    CHRYMORYPSIN("[FLWY]|{P}", createEnzyme("Chymotrypsin", new Character[]{'F', 'Y', 'W', 'L'}, new Character[]{'P'}, new CvTerm("PSI-MS", "MS:1001306", "Chymotrypsin", null))),

    LYS_N_NOSPECIFIC("[X]|[K]" ,createEnzyme("Lys-N", new Character[]{'K'}, new Character[]{'\u0000'}, null));

    private  Enzyme  emzyme = null;
    private  String  xTandemKey = null;


    XTandemEnzyme(String key, Enzyme enzyme) {
        this.xTandemKey = key;
        this.emzyme = enzyme;

    }

    public static com.compomics.util.experiment.biology.Enzyme createEnzyme(String name, Character[] aminoBeforeList, Character[] restrictAfter, CvTerm cvTerm){
        com.compomics.util.experiment.biology.Enzyme enzyme = new com.compomics.util.experiment.biology.Enzyme(name);
        for(Character beforeString: aminoBeforeList)
            if(beforeString != '\u0000')
                enzyme.addAminoAcidBefore(beforeString);
        for(Character afterString: restrictAfter)
            if(afterString != '\u0000')
                enzyme.addRestrictionAfter(afterString);
        enzyme.setCvTerm(cvTerm);
        return enzyme;
    }

    /**
     * This method returns the Enzyme for the Xtandem representation. The Enzyme follow the representation from
     * compomics platform.
     *
     * @param key Xtandem Representation for enzyme
     * @return compomics representation for enzyme.
     */
    public static Enzyme getEnzymeByKey(String key){
        for(XTandemEnzyme xTandemEnzyme: values())
            if(xTandemEnzyme.xTandemKey.equalsIgnoreCase(key))
                return xTandemEnzyme.emzyme;
        return null;
    }
}
