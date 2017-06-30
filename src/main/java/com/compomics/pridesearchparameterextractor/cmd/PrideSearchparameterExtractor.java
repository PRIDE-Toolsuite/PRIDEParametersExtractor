package com.compomics.pridesearchparameterextractor.cmd;

import com.compomics.pride_asa_pipeline.core.exceptions.ParameterExtractionException;
import com.compomics.pridesearchparameterextractor.extraction.PrideParameterExtractor;
import com.compomics.pridesearchparameterextractor.extraction.impl.PrideMzIDParameterExtractor;
import com.compomics.pridesearchparameterextractor.extraction.impl.PrideXMLParameterExtractor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.BasicConfigurator;

import org.apache.log4j.Logger;

/**
 *
 * @author Kenneth Verheggen
 */
public class PrideSearchparameterExtractor {

    private static final Logger LOGGER = Logger.getLogger(PrideSearchparameterExtractor.class);

    public static void main(String[] args) {
        //init log4J
     //   BasicConfigurator.configure();
        // create Options object
        Options options = new Options();
        generateOptions(options);
        //parse the command
        CommandLineParser parser = new BasicParser();
        try {
            if (args.length == 0) {
                help(options);
            } else {
                CommandLine cmd = parser.parse(options, args);
                handleCommand(cmd, options);
            }
        } catch (FileNotFoundException | ParseException ex) {
            LOGGER.error(ex);
        }
    }

    private static void generateOptions(Options options) {
        options.addOption("help", false, "Writes a usage message");
        options.addOption("type", true, "The input type (prideXML or mzID");
        options.addOption("in", true, "The input file (prideXML or mzID)");
        options.addOption("peak_files", true, "Comma separated list of peakfiles (only for mzID input)");
        options.addOption("out", true, "The output folder");
        options.addOption("save_mgf", false, "Indicates if mgf files have to be exported");
    }

    private static void handleCommand(CommandLine cmd, Options options) throws FileNotFoundException {

        String type = "";
        File input = null;
        File output = null;
        List<File> peakFiles = null;
        boolean saveMGF = false;

        if (cmd.hasOption("help")) {
            help(options);
        } else //check if the input type is provided...
        {
            if (!cmd.hasOption("type")) {
                LOGGER.error("The type needs to be specified in the command !");
                help(options);
            } else if (cmd.getOptionValue("type").equalsIgnoreCase("mzid") && !cmd.hasOption("peak_files")) {
                throw new IllegalArgumentException("peak_files argument is mandatory in the case of mzid extraction");
            } else {
                type = cmd.getOptionValue("type");
                //Get the input files required
                input = new File(cmd.getOptionValue("in"));
                if (!input.exists()) {
                    throw new FileNotFoundException(input.getAbsolutePath() + " does not exist !");
                } else if (type.equalsIgnoreCase("mzid")) {
                    //if the type is mzID we need to also get the peakfiles...
                    peakFiles = new ArrayList<>();
                    String[] peakFilesArg = cmd.getOptionValue("peak_files").split(",");
                    for (String peakFileArg : peakFilesArg) {
                        File peakFile = new File(peakFileArg.trim());
                        if (!peakFile.exists()) {
                            throw new FileNotFoundException(peakFile.getAbsolutePath() + " does not exist !");
                        } else {
                            peakFiles.add(peakFile);
                        }
                    }
                }
                //Get the output location and options
                output = new File(cmd.getOptionValue("out"));
                if (!output.exists()) {
                    output.mkdirs();
                }
                saveMGF = cmd.hasOption("save_mgf");
            }
        }

        //EXECUTE THE EXTRACTION
        try {
            //get the correct extractor
            PrideParameterExtractor extractor;
            switch (type) {
                case "pridexml":
                    extractor = new PrideXMLParameterExtractor(input, output, saveMGF);
                    break;
                case "mzid":
                    extractor = new PrideMzIDParameterExtractor(input, peakFiles, output, saveMGF);
                    break;
                default:
                    throw new IllegalArgumentException("Type needs to be either pridexml or mzid");
            }

            //run the extractor
            if (extractor.analyze()) {
                LOGGER.info("The analysis was completed succesfully");
            }
        } catch (ParameterExtractionException ex) {
            LOGGER.error(ex);
        }
    }

    private static void help(Options options) {
        // This prints out some help
        HelpFormatter formater = new HelpFormatter();
        formater.printHelp("Main", options);
        System.exit(0);
    }

}
