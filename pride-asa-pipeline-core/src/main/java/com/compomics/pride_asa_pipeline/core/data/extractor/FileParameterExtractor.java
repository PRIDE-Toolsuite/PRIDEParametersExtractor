package com.compomics.pride_asa_pipeline.core.data.extractor;

import com.compomics.pride_asa_pipeline.model.MGFExtractionException;
import com.compomics.pride_asa_pipeline.core.repository.impl.combo.FileExperimentModificationRepository;
import com.compomics.pride_asa_pipeline.core.repository.impl.file.FileSpectrumRepository;
import com.compomics.pride_asa_pipeline.core.spring.ApplicationContextProvider;
import com.compomics.pride_asa_pipeline.model.AnalyzerData;
import com.compomics.pride_asa_pipeline.model.ParameterExtractionException;
import com.compomics.util.experiment.identification.identification_parameters.SearchParameters;
import com.compomics.util.gui.waiting.waitinghandlers.WaitingHandlerCLIImpl;
import com.compomics.util.io.compression.ZipUtils;
import com.compomics.util.preferences.IdentificationParameters;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.apache.log4j.Logger;
import org.geneontology.oboedit.dataadapter.GOBOParseException;
import org.xmlpull.v1.XmlPullParserException;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.mzxml_parser.MzXMLParsingException;

/**
 * This class is used to process an assay from an input file
 *
 * @author Kenneth Verheggen
 */
public class FileParameterExtractor {

    private String outputFile;
    /**
     * The output folder for the extraction
     */
    private File outputFolder;

    private Boolean processMGF = false;

    private Boolean processIDs = true;

    private IParametersRefinery parametersRefinery = null;
    
    /**
     * The Logger instance
     */
    private static final Logger LOGGER = Logger.getLogger(FileParameterExtractor.class);

    private FileExperimentModificationRepository experimentRepository;
    private FileSpectrumRepository spectrumRepository;
    private FileExperimentModificationRepository modificationRepository;
    private AnalyzerData analyzerData;

    public FileParameterExtractor(File outputFolder, Boolean processMGF, Boolean processIDs, IParametersRefinery refinery) throws IOException {
        this.outputFolder = outputFolder;
        this.processMGF = processMGF;
        this.analyzerData = AnalyzerData.getAnalyzerDataByAnalyzerType("");
        this.processIDs = processIDs;
        this.parametersRefinery = refinery;
        init();
    }

    public FileParameterExtractor(File outputFolder, AnalyzerData analyzerData, IParametersRefinery refinery) throws IOException {
        this.outputFolder = outputFolder;
        this.analyzerData = analyzerData;
        this.parametersRefinery = refinery;
        init();
    }

    public FileParameterExtractor(String outputFile, Boolean processMGF, Boolean processIDs, IParametersRefinery refinery) {
        this.outputFile = outputFile;
        this.processMGF = processMGF;
        this.processIDs = processIDs;
        this.parametersRefinery = refinery;
        this.analyzerData = AnalyzerData.getAnalyzerDataByAnalyzerType("");
        init();
    }

    private void init() {
        //load into the spring setup
        ApplicationContextProvider.getInstance().setDefaultApplicationContext();

        spectrumRepository = ApplicationContextProvider.getInstance().getBean("spectrumRepository");

        //add a logger specific to this file
    }

    private SearchParameters inferParameters(String assay,boolean allowOnlyPRIDEAnnotated) throws ParameterExtractionException, IOException {

        LOGGER.info("Attempting to infer searchparameters");
        ParameterExtractor extractor = new ParameterExtractor(assay, analyzerData, modificationRepository,allowOnlyPRIDEAnnotated);
        //extractor.setExperimentRepository(experimentRepository);
        IdentificationParameters parameters = extractor.getParameters();

        //Refine the parameters in case something is wrong in the file.
        if(parametersRefinery != null)
            parameters = parametersRefinery.refineIdentificationParameters(parameters);

        if(outputFile != null){
            IdentificationParameters.saveIdentificationParameters(parameters, new File(outputFile));
            //Todo: We have decided here to do nto print the output resume when a file is specified
            //extractor.printReports(outputFile + ".report.out");
        } else{
            extractor.printReports(outputFolder);
            IdentificationParameters.saveIdentificationParameters(parameters, new File(outputFolder, ".par"));
        }
        return parameters.getSearchParameters();
    }

    private void processSpectra() throws IOException, MGFExtractionException {
        LOGGER.debug("Getting related spectrum files from the cache");
        File mgf = spectrumRepository.writeToMGF(outputFolder, false);
        //zip the MGF file
        File zip = new File(mgf.getAbsolutePath() + ".zip");
        ZipUtils.zip(mgf, zip, new WaitingHandlerCLIImpl(), mgf.length());
        mgf.delete();
    }

    public SearchParameters analyzePrideXML(File inputFile, String assay,boolean allowOnlyPRIDEAnnotated) throws IOException, MGFExtractionException, MzXMLParsingException, JMzReaderException, XmlPullParserException, ClassNotFoundException, GOBOParseException, Exception {
        LOGGER.debug("Setting up experiment repository for assay " + assay);
        experimentRepository = FileExperimentModificationRepository.getInstance();
        experimentRepository.setExperimentIdentifier(assay);
        modificationRepository = experimentRepository;
        //load the file into the repository
        experimentRepository.addPrideXMLFile(assay, inputFile);
        spectrumRepository.setExperimentIdentifier(assay);
        modificationRepository.setExperimentIdentifier(assay);
        if(processMGF)
            processSpectra();
        return inferParameters(assay,allowOnlyPRIDEAnnotated);
    }

    public SearchParameters analyzeMzID(File inputFile, List<File> peakFiles, String assay,boolean allowOnlyPRIDEAnnotated) throws MGFExtractionException, ParameterExtractionException, IOException, TimeoutException, InterruptedException, ExecutionException {
        LOGGER.debug("Setting up experiment repository for assay " + assay);
        experimentRepository = FileExperimentModificationRepository.getInstance();
        experimentRepository.setExperimentIdentifier(assay);
        modificationRepository = experimentRepository;
        if(peakFiles.isEmpty()){
            throw new ParameterExtractionException("There are no compatible spectrum files for this assay !!!!");
        }
        experimentRepository.addMzID(assay, inputFile, peakFiles);
        spectrumRepository.setExperimentIdentifier(assay);
        modificationRepository.setExperimentIdentifier(assay);
        if(processMGF)
            processSpectra();
        return inferParameters(assay, allowOnlyPRIDEAnnotated);
    }

}
