package com.compomics.pridesearchparameterextractor.extraction.impl;

import com.compomics.pride_asa_pipeline.core.data.extractor.FileParameterExtractor;
import com.compomics.pride_asa_pipeline.core.data.extractor.IParametersRefinery;
import com.compomics.pride_asa_pipeline.model.ParameterExtractionException;
import com.compomics.pridesearchparameterextractor.extraction.PrideParameterExtractor;
import java.io.File;
import java.util.List;

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
     * Save the Identification report to a File.
     */
    private Boolean saveIDs = false;
    /**
     * The folder to store the resulting file(s)
     */
    private File outputFolder = null;

    /**
     * The file yo output the files
     */
    private String outputFile = null;
    /**
     * Boolean indicating if the mgf file should be exported as well
     */
    private boolean saveMGF = false;

    //If the assay Number is provided it should be use instead of the file name
    private String assayNumber = null;

    //Refinery to inject paramters from outside.

    private IParametersRefinery parameterRefinery = null;


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
    public PrideXMLParameterExtractor(File inputFile, File outputFolder, boolean saveMGF, IParametersRefinery refinery) throws ParameterExtractionException {
        this.inputFile = inputFile;
        this.outputFolder = outputFolder;
        this.saveMGF = saveMGF;
        this.parameterRefinery = refinery;
    }

    /**
     * Export into File rather that output Folder
     * @param inputFile input File PRIDE to predict the parameters
     * @param assayNumber assay Number used into the analysis
     * @param fileName file name to export the parameters.
     * @param saveMGF Save the mgf Files.
     * @param saveIds save the Ids.
     */
    public PrideXMLParameterExtractor(File inputFile, String assayNumber, String fileName, Boolean saveMGF, Boolean saveIds, IParametersRefinery refinery) {
        this.inputFile = inputFile;
        this.outputFile = fileName;
        this.saveMGF = saveMGF.booleanValue();
        this.saveIDs = saveIds;
        this.assayNumber = assayNumber;
        this.parameterRefinery = refinery;
    }

    /**
     * Constructor for a parameter extractor
     *
     * @param inputFile The input prideXML file
     * @param outputFolder The folder to store the resulting file(s)
     * @throws ParameterExtractionException if the parameters could not be
     * extracted correctly
     */
    public PrideXMLParameterExtractor(File inputFile, File outputFolder, IParametersRefinery refinery) throws ParameterExtractionException {
        this.inputFile = inputFile;
        this.outputFolder = outputFolder;
        this.parameterRefinery = refinery;

    }

    /**
     * Analyzes the provided input and generates the parameter file at the provided outputfolder
     *
     * @return a boolean indicating the success of the extraction
     */
    @Override
    public boolean analyze() {
        boolean succeeded = false;
        FileParameterExtractor extractor = null;
        try {
            if(inputFile != null)
                extractor = new FileParameterExtractor(outputFile, saveMGF, saveIDs, parameterRefinery);
            else if (outputFolder != null)
                extractor = new FileParameterExtractor(outputFolder, saveMGF, saveIDs, parameterRefinery);
            else {
                LOGGER.error("OutputFolder or FileName not defined -- File not converted");
                return false;
            }
            if(assayNumber == null)
                assayNumber = inputFile.getName();

            extractor.analyzePrideXML(inputFile, assayNumber);
            succeeded = true;
        } catch (Exception ex) {
            LOGGER.error(ex);
        } finally {
            return succeeded;
        }
    }

}
