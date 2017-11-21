package com.compomics.pridesearchparameterextractor.extraction;

import com.compomics.pride_asa_pipeline.model.MGFExtractionException;
import com.compomics.pride_asa_pipeline.model.ParameterExtractionException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Kenneth Verheggen
 */
public interface PrideParameterExtractor {

    /**
     * Analyzes the provided input and generates the parameter file at the
     * provided outputfolder
     *
     * @return a boolean indicating the success of the extraction
     */
    public boolean analyze() throws ParameterExtractionException, ExecutionException, TimeoutException, MGFExtractionException, InterruptedException;
}
