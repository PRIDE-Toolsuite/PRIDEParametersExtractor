package com.compomics.pridesearchparameterextractor.extraction.impl;

import com.compomics.pride_asa_pipeline.core.data.extractor.FileParameterExtractor;
import com.compomics.pride_asa_pipeline.core.exceptions.ParameterExtractionException;
import com.compomics.pridesearchparameterextractor.extraction.PrideParameterExtractor;
import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * @author Kenneth Verheggen
 */
public class PrideXMLParameterExtractor implements PrideParameterExtractor {

    private static final Logger LOGGER = Logger.getLogger(PrideXMLParameterExtractor.class);

    /**
     * The input prideXML file
     */
    private final File inputFile;
    /**
     * The folder to store the resulting file(s)
     */
    private final File outputFolder;
    /**
     * Boolean indicating if the mgf file should be exported as well
     */
    private boolean saveMGF = false;

    /**
     * Constructor for a parameter extractor
     *
     * @param inputFile The input prideXML file
     * @param outputFolder The folder to store the resulting file(s)
     * @param saveMGF Boolean indicating if the mgf file should be exported as
     * well
     * @throws ParameterExtractionException if the parameters could not be
     * extracted correctly
     */
    public PrideXMLParameterExtractor(File inputFile, File outputFolder, boolean saveMGF) throws ParameterExtractionException {
        this.inputFile = inputFile;
        this.outputFolder = outputFolder;
        this.saveMGF = saveMGF;
    }

    /**
     * Constructor for a parameter extractor
     *
     * @param inputFile The input prideXML file
     * @param outputFolder The folder to store the resulting file(s)
     * @throws ParameterExtractionException if the parameters could not be
     * extracted correctly
     */
    public PrideXMLParameterExtractor(File inputFile, File outputFolder) throws ParameterExtractionException {
        this.inputFile = inputFile;
        this.outputFolder = outputFolder;

    }

    /**
     * Analyzes the provided input and generates the parameter file at the provided outputfolder
     *
     * @return a boolean indicating the success of the extraction
     */
    @Override
    public boolean analyze() {
        boolean succeeded = false;
        try {
            FileParameterExtractor extractor = new FileParameterExtractor(outputFolder);
            extractor.analyzePrideXML(inputFile, inputFile.getName(), saveMGF,false);
            succeeded = true;
        } catch (Exception ex) {
            LOGGER.error(ex);
        } finally {
            return succeeded;
        }
    }

}
