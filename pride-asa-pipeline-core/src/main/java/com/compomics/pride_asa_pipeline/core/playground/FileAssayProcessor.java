package com.compomics.pride_asa_pipeline.core.playground;

import com.compomics.pride_asa_pipeline.core.data.extractor.FileParameterExtractor;
import com.compomics.util.experiment.identification.identification_parameters.SearchParameters;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Kenneth Verheggen
 */
public class FileAssayProcessor {

    public static void main(String[] args) throws Exception {
        File outputFolder = new File("C:\\Users\\compomics\\Desktop\\TEST_ASAP\\output");
        if (outputFolder.exists()) {
            outputFolder.delete();
        }
        outputFolder.mkdirs();
        File inputFile = new File("C:\\Users\\compomics\\Desktop\\Projecten\\PRIDE_TEST\\TCGA-AG-A008-01A-23_W_VU_20130127_A0218_9G_R_FR05.mzid");
        File peakFile = new File("C:\\Users\\compomics\\Desktop\\Projecten\\PRIDE_TEST\\TCGA-AG-A008-01A-23_W_VU_20130127_A0218_9G_R_FR05.mzML");
        ArrayList<File> peakFiles = new ArrayList<>();
        peakFiles.add(peakFile);
        SearchParameters parameters = new FileParameterExtractor(outputFolder, false, true, null).analyzeMzID(inputFile, peakFiles,"peptideshaker",false);
        System.out.println(parameters);
    }
}
