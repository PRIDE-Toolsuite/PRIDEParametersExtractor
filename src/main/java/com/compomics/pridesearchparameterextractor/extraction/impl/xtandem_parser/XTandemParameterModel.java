package com.compomics.pridesearchparameterextractor.extraction.impl.xtandem_parser;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

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

@JacksonXmlRootElement(localName = "bioml")
public class XTandemParameterModel {

    @JacksonXmlProperty(localName = "note")
    @JacksonXmlElementWrapper(useWrapping = false)
    List<XParameter> listParameters;

    public List<XParameter> getListParameters() {
        return listParameters;
    }

    public void setListParameters(List<XParameter> listParameters) {
        this.listParameters = listParameters;
    }

    /**
     * This function returns the xParameter for an specific Key
     * @param key name of the parameter
     * @return XParameter
     */
    public XParameter getValueByKey(String key){
        final XParameter[] returnValue = {null};
        if(listParameters != null && listParameters.size() > 0){
            listParameters.forEach( xParameter -> {
                if(xParameter.getLabel().equalsIgnoreCase(key))
                    returnValue[0] =  xParameter;
            });
        }
        return returnValue[0];
    }
}
