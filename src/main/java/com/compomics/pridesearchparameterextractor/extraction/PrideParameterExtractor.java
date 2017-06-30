package com.compomics.pridesearchparameterextractor.extraction;

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
    public boolean analyze();
}
