package com.compomics.pridesearchparameterextractor.utilities;

/**
 * This code is licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 * <p>
 * This class define a set of constants that would be use on all the search
 * parameters. In the reanalysis pipeline we will use some default parameters
 * that are better that the one we can estimate.
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 28/11/2017.
 */
public class ExtractorConstants {

    public final static boolean ALLOW_ONLY_PRIDE_ANNOTATED = true;

    // Minimun charge state to be search
    public final static Integer MIN_CHARGE = 1;

    // Maximun charge State to be Search.
    public final static Integer MAX_CHARGE = 5;

    public final static Integer NUMBER_MISSCLEAVAGES = 2;
}
