package com.compomics.pridesearchparameterextractor.extraction;

import com.compomics.pride_asa_pipeline.core.data.extractor.IParametersRefinery;
import com.compomics.util.experiment.biology.PTMFactory;
import com.compomics.util.experiment.identification.identification_parameters.PtmSettings;
import com.compomics.util.experiment.identification.identification_parameters.SearchParameters;
import com.compomics.util.experiment.identification.ptm.PtmScore;
import com.compomics.util.preferences.IdMatchValidationPreferences;
import com.compomics.util.preferences.IdentificationParameters;
import com.compomics.util.preferences.PTMScoringPreferences;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Map;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 * <p>
 * This class refine some of the parameters that the pipeline know the can be incorrect.
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 29/11/2017.
 */
public class PRIDERefinery implements IParametersRefinery{

    private static final Logger LOGGER = Logger.getLogger(PRIDERefinery.class);

    private static final PTMFactory ptmFactory = PTMFactory.getInstance();

    private String[] canonicalMods = new String[]{"Phosphorylation of T", "Phosphorylation of S", "Phosphorylation of Y"};

    @Override
    public IdentificationParameters refineIdentificationParameters(IdentificationParameters identificationParameters) {

        /**
         * Refining modifications, Some times the modifications, specially phosphorylation is not captured in the righ
         * way.
         */

        SearchParameters searchParameters = identificationParameters.getSearchParameters();
        PtmSettings searchPTMs = searchParameters.getPtmSettings();
        searchPTMs = correctCanonicalPhosphorylation(searchPTMs);
        searchParameters.setPtmSettings(searchPTMs);
        identificationParameters.setSearchParameters(searchParameters);

        /**
         * Define the probability scores for the Phosphorylation
         */
        PTMScoringPreferences ptmScoringParams = identificationParameters.getPtmScoringPreferences();

        ptmScoringParams.setProbabilitsticScoreCalculation(true);
        ptmScoringParams.setSelectedProbabilisticScore(PtmScore.PhosphoRS);
        identificationParameters.setPtmScoringPreferences(ptmScoringParams);

        /**
         * Define the FDR threshold parameters.
         */

        IdMatchValidationPreferences qualityParams = identificationParameters.getIdValidationPreferences();
        qualityParams.setDefaultPsmFDR(0.2);
        qualityParams.setDefaultPeptideFDR(0.2);
        qualityParams.setDefaultProteinFDR(0.1);
        identificationParameters.setIdValidationPreferences(qualityParams);



        LOGGER.info("Final Parameters after refinements -- " + identificationParameters.toString());

        return identificationParameters;
    }

    /**
     * This fucntion corrects if the parameters has modifications in phosphorylation,
     * it should look for all of them (e.g S, T , Y)
     * @param searchPTMs
     * @return
     */
    private PtmSettings correctCanonicalPhosphorylation(PtmSettings searchPTMs) {

        boolean canonicalPhosphoPresent = false;

        ArrayList<String> modifications = searchPTMs.getAllModifications();
        for(String mod: modifications){
            for(String canonical: canonicalMods){
                if(canonical.equalsIgnoreCase(mod)){
                    canonicalPhosphoPresent = true;
                    break;
                }
            }
        }
        if(canonicalPhosphoPresent){
            for(String mod: canonicalMods)
                if(!searchPTMs.contains(mod))
                    searchPTMs.addVariableModification(ptmFactory.getPTM(mod));
        }

        return searchPTMs;
    }


}
