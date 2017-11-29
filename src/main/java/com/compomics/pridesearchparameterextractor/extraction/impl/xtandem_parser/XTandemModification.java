package com.compomics.pridesearchparameterextractor.extraction.impl.xtandem_parser;

import com.compomics.pride_asa_pipeline.model.ParameterExtractionException;
import com.compomics.pride_asa_pipeline.model.modification.impl.UtilitiesPTMAdapter;
import com.compomics.util.experiment.biology.PTM;
import com.compomics.util.experiment.biology.PTMFactory;
import com.compomics.util.pride.CvTerm;
import uk.ac.ebi.pride.utilities.pridemod.ModReader;
import uk.ac.ebi.pride.utilities.pridemod.model.Specificity;
import uk.ac.ebi.pride.utilities.pridemod.model.UniModPTM;
import uk.ac.ebi.pride.utilities.util.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 * <p>
 * This class transform XTandem Modifications into SearchgUI representation. The input of the
 * class is an String like:
 *       - 57.021464@C
 *
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 28/11/2017.
 */
public class XTandemModification {

    //Get pride modification
    static final ModReader modReader = ModReader.getInstance();

    //Get search gui modifications
    static final UtilitiesPTMAdapter adapter = new UtilitiesPTMAdapter();

    public static List<Tuple<PTM, String>> getListOfPTMsByKey(String key) throws ParameterExtractionException {

        List<Tuple<PTM, String>> resultPTMs = new ArrayList<>();

        //The first step is to break the modification list into single modification Strings.
        String[] modStrings = key.split(",");
        for(String modString: modStrings){
            List<Tuple<PTM,String>> resultPTM = transformStringToPTM(modString);
            if(resultPTM != null)
                resultPTMs.addAll(resultPTM);
            else {
//                resultPTM = transformStringToPTMCompomics(modString);
//                if(resultPTM != null)
//                    resultPTMs.add(resultPTM);
//                else
                    throw new ParameterExtractionException("The current PTM can't be assigned to any PTM in Compomics -- " + modString );
            }
        }
        return resultPTMs;
    }

    private static List<Tuple<PTM,String>> transformStringToPTM(String modString) throws ParameterExtractionException {
        String mass = modString.split("@")[0];
        String site = modString.split("@")[1];

        //Result PTMs from the transformation. It needs to be more than one becase CTerm and NTerm mods are transform in two.
        List<Tuple<PTM, String>> ptms = new ArrayList<>();

        try{
            double massDouble = Double.parseDouble(mass);
            String massSite = site.replaceAll("\\d",""); //This is needed to remove number in the string

            massSite = correctNTermCTermAminoAcid(massSite);

            List<uk.ac.ebi.pride.utilities.pridemod.model.PTM> mods = modReader.getAnchorMassModification(massDouble, massSite);
            if(mods == null && mods.size() == 0)
                throw new ParameterExtractionException("The Extractor hasn't found any modification for the following ModificationString  -- " + modString);

            //Filter only for UnimodModifications
            mods = mods.stream().filter(mod -> (mod instanceof UniModPTM)).collect(Collectors.toList());


            if(mods != null && mods.size() > 1)
                throw new ParameterExtractionException("The Extractor has found more than one modification for the modification String  -- " + modString);

            if(mods.size() == 1 && mods.get(0) != null){
                if(massSite.equalsIgnoreCase(Specificity.Position.NTERM.toString())){
                    Tuple<PTM,String> ptm = createPTMfromPRIDEMod(mods.get(0), Specificity.Position.NTERM.name());
                    if(ptm != null)
                        ptms.add(ptm);
                    //We will not include the Protein Terminal for now.
//                    ptm = createPTMfromPRIDEMod(mods.get(0), Specificity.Position.PNTERM.name());
//                    if(ptm != null)
//                        ptms.add(ptm);
                }else if(massSite.equalsIgnoreCase(Specificity.Position.CTERM.toString())){
                    Tuple<PTM,String> ptm = createPTMfromPRIDEMod(mods.get(0), Specificity.Position.CTERM.name());
                    if(ptm != null)
                        ptms.add(ptm);

//                    ptm = createPTMfromPRIDEMod(mods.get(0), Specificity.Position.PCTERM.name());
//                    if(ptm != null)
//                        ptms.add(ptm);
                }else{
                    Tuple<PTM,String> ptm = createPTMfromPRIDEMod(mods.get(0), massSite);
                    if(ptm != null)
                        ptms.add(ptm);
                }
                return ptms;
            }
        }catch(NumberFormatException e){
            throw new ParameterExtractionException("Exception parsing the mass if the modification -- " + modString, e);
        }

        return null;
    }

    /**
     * Position of CTerm or NTerm position are corrected for PRIDE PTM
     * @param massSite mass site
     * @return The corrected mass.
     */
    private static String correctNTermCTermAminoAcid(String massSite) {
        if(massSite.equalsIgnoreCase("["))
            massSite = Specificity.Position.NTERM.name();
        if(massSite.equalsIgnoreCase("]"))
            massSite = Specificity.Position.CTERM.name();
        return massSite;
    }

    private static Tuple<PTM, String> createPTMfromPRIDEMod(uk.ac.ebi.pride.utilities.pridemod.model.PTM ptm, String massSite) {
        PTMFactory ptmFactory = PTMFactory.getInstance();

        for (String ptmName : ptmFactory.getDefaultModifications()) {

            PTM compomicsPTM = ptmFactory.getPTM(ptmName);
            if (compomicsPTM.getCvTerm() != null && compomicsPTM.getCvTerm().getAccession().equals(ptm.getAccession()) && compomicsPTM.getPattern().asSequence().equalsIgnoreCase(massSite)) {
                return new Tuple<>(compomicsPTM, massSite);
            }
        }

        try {
            PTM compomicsPTM = adapter.convertModification(ptm, massSite);
            return new Tuple<>(compomicsPTM, massSite);
        } catch (ParameterExtractionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
