package com.compomics.pridesearchparameterextractor.extraction;

import com.compomics.pride_asa_pipeline.core.data.extractor.IParametersRefinery;
import com.compomics.util.experiment.identification.identification_parameters.SearchParameters;
import com.compomics.util.experiment.identification.ptm.PtmScore;
import com.compomics.util.preferences.IdMatchValidationPreferences;
import com.compomics.util.preferences.IdentificationParameters;
import com.compomics.util.preferences.PTMScoringPreferences;
import org.apache.log4j.Logger;
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

    @Override
    public IdentificationParameters refineIdentificationParameters(IdentificationParameters identificationParameters) {

        PTMScoringPreferences ptmScoringParams = identificationParameters.getPtmScoringPreferences();

        /**
         * Define the probability scores for the Phosphorylation
         */
        ptmScoringParams.setProbabilitsticScoreCalculation(true);
        ptmScoringParams.setSelectedProbabilisticScore(PtmScore.PhosphoRS);
        identificationParameters.setPtmScoringPreferences(ptmScoringParams);

        /**
         * Define the FDR threshold parameters.
         */

        IdMatchValidationPreferences qualityParams = identificationParameters.getIdValidationPreferences();
        qualityParams.setDefaultPsmFDR(0.1);
        qualityParams.setDefaultPeptideFDR(0.1);
        qualityParams.setDefaultProteinFDR(0.01);
        identificationParameters.setIdValidationPreferences(qualityParams);

        LOGGER.info("Final Parameters after refinements -- " + identificationParameters.toString());

        return identificationParameters;
    }


}
