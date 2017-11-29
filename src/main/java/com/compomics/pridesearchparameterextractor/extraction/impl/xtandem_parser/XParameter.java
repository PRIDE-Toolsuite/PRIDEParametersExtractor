package com.compomics.pridesearchparameterextractor.extraction.impl.xtandem_parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class XParameter {

    @JacksonXmlProperty(isAttribute = true)
    String type;

    @JacksonXmlProperty(isAttribute = true)
    String label;

    @JacksonXmlText(value = true)
    String note;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return note;
    }

    public void setValue(String value) {
        this.note = value;
    }
}
