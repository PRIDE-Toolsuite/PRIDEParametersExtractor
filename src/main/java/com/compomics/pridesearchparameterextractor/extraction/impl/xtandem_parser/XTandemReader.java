package com.compomics.pridesearchparameterextractor.extraction.impl.xtandem_parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 * <p>
 * This class
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 27/11/2017.
 */
public class XTandemReader {

    private static XTandemReader instance;

    /** The XTANDEM ERROR PARAMETERS  */

    public static final String XTADENM_PRECURSOR_UNIT = "spectrum, parent monoisotopic mass error units";
    public static final String XTANDEM_FRAGMENT_UNIT   = "spectrum, fragment monoisotopic mass error units";
    public static final String XTANDEM_FRAGMENT_ERROR  = "spectrum, fragment monoisotopic mass error";
    public static final String XTANDEM_PRECURSOR_ERROR = "spectrum, parent monoisotopic mass error plus";

    /** VARIABLE AND FIXED MODIFICATIONS */
    public static final String FIXED_MODIFICATIONS  = "residue, modification mass";
    public static final String VARIABLE_MODIFICATION = "residue, potential modification mass";

    /** Enzyme information  */
    public static final String ENZYME = "protein, cleavage site";

    /** POSSIBLE VALUES */
    public static final String ERROR_UNITS_VALUE_PPM = "ppm";
    public static final String ERROR_UNITS_VALUE_DA  = "da";

    private XTandemParameterModel parameters = null;

    private XTandemReader(){}

    /**
     * Get the reader.
     * @return
     */
    public static XTandemReader getInstance(){
        if(instance == null)
            instance = new XTandemReader();
        return instance;
    }

    /**
     * Read parameters file from XTandem.
     * @param parameterFile
     * @throws IOException
     */
    public void readParameters(File parameterFile) throws IOException {
        ObjectMapper xmlMapper = new XmlMapper();
        parameters = xmlMapper.readValue(parameterFile, XTandemParameterModel.class);
    }

    public XTandemParameterModel getParameters() {
        return parameters;
    }

}
