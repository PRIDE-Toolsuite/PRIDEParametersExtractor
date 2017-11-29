package com.compomics.pridesearchparameterextractor.extraction.impl;

import com.compomics.pride_asa_pipeline.model.MGFExtractionException;
import com.compomics.pride_asa_pipeline.model.ParameterExtractionException;
import com.compomics.pridesearchparameterextractor.extraction.PRIDERefinery;
import com.compomics.pridesearchparameterextractor.extraction.PrideParameterExtractor;
import com.compomics.pridesearchparameterextractor.extraction.impl.xtandem_parser.*;
import com.compomics.pridesearchparameterextractor.utilities.ExtractorConstants;
import com.compomics.util.experiment.biology.PTM;
import com.compomics.util.experiment.identification.identification_parameters.PtmSettings;
import com.compomics.util.experiment.identification.identification_parameters.SearchParameters;
import com.compomics.util.experiment.massspectrometry.Charge;
import com.compomics.util.preferences.DigestionPreferences;
import com.compomics.util.preferences.IdentificationParameters;
import org.apache.log4j.Logger;
import uk.ac.ebi.pride.utilities.util.Tuple;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 * <p>
 * This class extracts from PeptideAtlas the corresponding GPMDB parameters.
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 27/11/2017.
 */
public class XTandemParametersExtractor implements PrideParameterExtractor{

    private final File outpuFile;
    private final File configFile;

    private static final Logger LOGGER = Logger.getLogger(XTandemParametersExtractor.class);


    public XTandemParametersExtractor(File configFile, File outpuFile) {
        this.configFile  = configFile;
        this.outpuFile   = outpuFile;
    }

    @Override
    public boolean analyze() throws ParameterExtractionException, ExecutionException, TimeoutException, MGFExtractionException, InterruptedException {
        XTandemReader parameterReader = XTandemReader.getInstance();
        try {
            parameterReader.readParameters(configFile);
            init(parameterReader.getParameters());

        } catch (IOException e) {
           throw new ParameterExtractionException(e);
        }
        return true;
    }

    private void init(XTandemParameterModel xParameters) throws ParameterExtractionException, IOException {

        SearchParameters searchParam = new SearchParameters();
        IdentificationParameters parameters = new IdentificationParameters(searchParam);

        // Definition charge state for searching.
        searchParam.setMinChargeSearched(new Charge(Charge.PLUS, Math.max(ExtractorConstants.MIN_CHARGE,1)));
        searchParam.setMaxChargeSearched(new Charge(Charge.PLUS, Math.min(ExtractorConstants.MAX_CHARGE,5)));

        //Definition of the Tolerance Threshold
        XParameter precursorUnits = xParameters.getValueByKey(XTandemReader.XTADENM_PRECURSOR_UNIT);
        if(precursorUnits == null || precursorUnits.getValue() == null)
            throw new ParameterExtractionException("The Precursor units are not specified in the file, please review the parameters file");

        if(precursorUnits.getValue().equalsIgnoreCase(XTandemReader.ERROR_UNITS_VALUE_PPM))
            searchParam.setPrecursorAccuracyType(SearchParameters.MassAccuracyType.PPM);
        else if(precursorUnits.getValue().equalsIgnoreCase(XTandemReader.ERROR_UNITS_VALUE_DA)) // I don't now id this is really tru DA or da
            searchParam.setPrecursorAccuracyType(SearchParameters.MassAccuracyType.DA);
        else
            throw new ParameterExtractionException("The Precursor error Unit is not recognized -- Current Value -- " + precursorUnits.getValue());

        // Getting the precursor Error Tolerance.
        XParameter precursorAccuracy = xParameters.getValueByKey(XTandemReader.XTANDEM_PRECURSOR_ERROR);
        if(precursorAccuracy == null || precursorAccuracy.getValue() == null)
            throw new ParameterExtractionException("The Precursor Accuracy are not specified in the file, please review the parameters file");

        try{
            Double precursorValue = Double.parseDouble(precursorAccuracy.getValue());
            searchParam.setPrecursorAccuracy(precursorValue);
        }catch (NumberFormatException e){
            throw new ParameterExtractionException("The Precursor Accuracy is not a Double Value and can't be parsed -- Current Value -- " + precursorAccuracy.getValue());
        }

        //Getting fragment tolerance
        XParameter fragmentUnits = xParameters.getValueByKey(XTandemReader.XTANDEM_FRAGMENT_UNIT);
        if(fragmentUnits == null || fragmentUnits.getValue() == null || fragmentUnits.getValue().equalsIgnoreCase(XTandemReader.ERROR_UNITS_VALUE_DA)){
            LOGGER.info("The Fragment Units where inferer and will be setup to DA, Please review this file");
            searchParam.setFragmentAccuracyType(SearchParameters.MassAccuracyType.DA);
        }else{
            searchParam.setFragmentAccuracyType(SearchParameters.MassAccuracyType.PPM);
        }

        //Setting the Fragment accuracy Value
        XParameter fragmentAccuracy = xParameters.getValueByKey(XTandemReader.XTANDEM_FRAGMENT_ERROR);
        if(fragmentAccuracy == null || fragmentAccuracy.getValue() == null)
            throw new ParameterExtractionException("The Precursor Accuracy are not specified in the file, please review the parameters file");

        try{
            Double fragmentValue = Double.parseDouble(fragmentAccuracy.getValue());
            searchParam.setFragmentIonAccuracy(fragmentValue);
        }catch (NumberFormatException e){
            throw new ParameterExtractionException("The Fragment Accuracy is not a Double Value and can't be parsed -- Current Value -- " + fragmentAccuracy.getValue());
        }


        //Enzyme number of misscleavage
        XParameter enzyme = xParameters.getValueByKey(XTandemReader.ENZYME);
        if(enzyme == null || enzyme.getValue() == null)
            throw new ParameterExtractionException("The Enzyme is not specified in the file, please review the parameters file");

        com.compomics.util.experiment.biology.Enzyme proteinEnzyme = XTandemEnzyme.getEnzymeByKey(enzyme.getValue());
        if(proteinEnzyme == null)
            throw new ParameterExtractionException("The Enzyme is not specified in the file, please review the parameters file");

        DigestionPreferences preferencesDigest =  new DigestionPreferences();
        preferencesDigest.addEnzyme(proteinEnzyme);
        preferencesDigest.setnMissedCleavages(proteinEnzyme.getName(), ExtractorConstants.NUMBER_MISSCLEAVAGES);
        preferencesDigest.setCleavagePreference(DigestionPreferences.CleavagePreference.enzyme);
        searchParam.setDigestionPreferences(preferencesDigest);

        //Variable Modifications needs to be parse
        XParameter variableModString = xParameters.getValueByKey(XTandemReader.VARIABLE_MODIFICATION);
        List<Tuple<PTM, String>> variableMods = XTandemModification.getListOfPTMsByKey(variableModString.getValue());
        LOGGER.info("The final number of variable modifications is -- " + variableMods.size());


        //Variable Modifications needs to be parse
        XParameter fixedModString = xParameters.getValueByKey(XTandemReader.FIXED_MODIFICATIONS);
        List<Tuple<PTM, String>> fixedMods = XTandemModification.getListOfPTMsByKey(fixedModString.getValue());
        LOGGER.info("The final number of fixed modifications is -- " + variableMods.size());

        PtmSettings settingPTM = new PtmSettings();
        for(Tuple<PTM, String> ptm: variableMods)
            settingPTM.addVariableModification(ptm.getKey());

        for(Tuple<PTM, String> ptm: fixedMods)
            settingPTM.addFixedModification(ptm.getKey());

        searchParam.setPtmSettings(settingPTM);
        parameters.setParametersFromSearch(searchParam);

        parameters = new PRIDERefinery().refineIdentificationParameters(parameters);
        IdentificationParameters.saveIdentificationParameters(parameters, outpuFile);
    }
}
