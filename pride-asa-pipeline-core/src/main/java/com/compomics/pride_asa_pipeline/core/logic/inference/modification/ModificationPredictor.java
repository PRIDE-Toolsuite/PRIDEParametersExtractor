package com.compomics.pride_asa_pipeline.core.logic.inference.modification;

import com.compomics.pride_asa_pipeline.core.util.report.impl.TotalReportGenerator;
import com.compomics.pride_asa_pipeline.model.ModificationHolder;
import com.compomics.pride_asa_pipeline.model.modification.impl.UtilitiesPTMAdapter;
import com.compomics.pride_asa_pipeline.model.modification.source.PRIDEModificationFactory;
import com.compomics.pride_asa_pipeline.core.repository.ModificationRepository;
import com.compomics.pride_asa_pipeline.core.repository.impl.combo.FileExperimentModificationRepository;
import com.compomics.pride_asa_pipeline.core.repository.impl.combo.FileExperimentModificationRepository;
import com.compomics.pride_asa_pipeline.core.repository.impl.file.FileModificationRepository;
import com.compomics.pride_asa_pipeline.model.Modification;
import com.compomics.pride_asa_pipeline.model.ParameterExtractionException;

import com.compomics.util.experiment.biology.PTM;
import com.compomics.util.experiment.biology.PTMFactory;
import com.compomics.util.experiment.identification.identification_parameters.PtmSettings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author Kenneth Verheggen
 */
public class ModificationPredictor {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = Logger.getLogger(ModificationPredictor.class);
    /**
     * The consideration threshold for modifications. At least this value must
     * carry the modification for it to be considered in the extraction (default
     * = 5%)
     */
    private double considerationThreshold = 0.01;
    /**
     * The threshold for fixed modifications
     *
     * @ToDo everything should be variable?
     */
    private final double fixedThreshold = 0.90;
    /**
     * The rates in which modifications occur on the provided identifications
     */
    private Map<Modification, Double> modificationRates = new HashMap<>();
    /**
     * The ptm settings (former modification profile)
     */
    protected PtmSettings ptmSettings;
    /**
     * A list of UNIMOD quant related terms
     */
    private final List<Integer> quantAccessions = Arrays.asList(new Integer[]{258, 259, 267, 367, 687, 365, 866, 730, 532, 730, 533, 731, 739, 738, 737, 984, 985, 1341, 1342});
    /**
     * The assay identifier
     */
    private String assay;
    /**
     * The complete modification holder
     */
    private final ModificationHolder modificationHolder;
    /**
     * The mod repository
     */
    private ModificationRepository repository;

    /**
     * A predictor for the modification profile
     *
     * @param assay the assay
     * @param modificationHolder the modification holder
     */
    public ModificationPredictor(String assay, ModificationHolder modificationHolder) {
        this.assay = assay;
        this.modificationHolder = modificationHolder;
        repository = new FileModificationRepository();
        inferModifications(false);
    }

    /**
     * A predictor for the modification profile
     *
     * @param assay the assay
     * @param modificationHolder the modification holder
     * @param repository an initialized modification repository
     */
    public ModificationPredictor(String assay, ModificationHolder modificationHolder, ModificationRepository repository) {
        this.assay = assay;
        this.modificationHolder = modificationHolder;
        this.repository = repository;
        inferModifications(false);
    }

    /**
     * A predictor for the modification profile
     *
     * @param assay the assay
     * @param modificationHolder the modification holder
     */
    public ModificationPredictor(String assay, ModificationHolder modificationHolder, boolean allowOnlyPRIDEAnnotated) {
        this.assay = assay;
        this.modificationHolder = modificationHolder;
        repository = new FileModificationRepository();
        inferModifications(allowOnlyPRIDEAnnotated);
    }

    /**
     * A predictor for the modification profile
     *
     * @param assay the assay
     * @param modificationHolder the modification holder
     * @param repository an initialized modification repository
     */
    public ModificationPredictor(String assay, ModificationHolder modificationHolder, ModificationRepository repository, boolean allowOnlyPRIDEAnnotated) {
        this.assay = assay;
        this.modificationHolder = modificationHolder;
        this.repository = repository;
        inferModifications(allowOnlyPRIDEAnnotated);
    }

    private void inferModifications(boolean allowOnlyPRIDEAnnotated) {
        LOGGER.info("Inferring modifications...");
        HashMap<String, Boolean> asapMods = new HashMap<>();

        UtilitiesPTMAdapter adapter = new UtilitiesPTMAdapter();
        ptmSettings = new PtmSettings();
        //make sure the annotated mods are in there as well
        List<Modification> modificationsByExperimentId = FileExperimentModificationRepository.getInstance().getModificationsByExperimentId(assay);
        TotalReportGenerator.setExperimentMods(modificationsByExperimentId);
        for (Modification annotatedMod : modificationsByExperimentId) {
            asapMods.put(annotatedMod.getName(), false);
        }

        if (!allowOnlyPRIDEAnnotated) {

            if (modificationHolder != null) {
                for (Modification aMod : modificationHolder.getAllModifications()) {
                    asapMods.put(aMod.getName(), false);
                }
            }
            //and add any new ones
            HashSet<Double> encounteredMasses = new HashSet<>();
            for (Map.Entry<String, Boolean> aMod : asapMods.entrySet()) {
                try {
                    ArrayList<String> unknownPTM = new ArrayList<>();
                    String convertPridePtm = PTMFactory.getInstance().convertPridePtm(aMod.getKey(), ptmSettings, unknownPTM, false);
                    if (!unknownPTM.isEmpty()) {
                        try {
                            LOGGER.debug(aMod.getKey() + " is not a standard modification. Converting to utilities object");
                            PTM aUtilitiesMod = (PTM) PRIDEModificationFactory.getInstance().getModification(adapter, aMod.getKey());
                            if (!aUtilitiesMod.getName().toLowerCase().contains("unknown")) {
                                if (!encounteredMasses.contains(aUtilitiesMod.getRoundedMass())) {
                                    encounteredMasses.add(aUtilitiesMod.getRoundedMass());
                                    if (aMod.getValue()) {
                                        ptmSettings.addFixedModification(aUtilitiesMod);
                                    } else {
                                        ptmSettings.addVariableModification(aUtilitiesMod);
                                    }

                                } else {
                                    LOGGER.warn("Duplicate mass, " + aMod.getKey() + " will be ignored");
                                }

                            } else {
                                LOGGER.debug(aMod.getKey() + " was found in the default PTMs");
                            }
                        } catch (ParameterExtractionException e) {
                            LOGGER.error("Skipping" + aMod.getKey() + " : reason : " + e);
                        }
                    }

                } catch (Exception e) {
                    LOGGER.error(e);
                    LOGGER.error("Modification could not be passed : " + aMod.getKey());
                }
            }

        }
        TotalReportGenerator.setPtmSettings(ptmSettings);

        TotalReportGenerator.setPtmSettingsMethod(
                "Fragment mass error analysis");
    }

    public double getConsiderationThreshold() {
        return considerationThreshold;
    }

    public double getFixedThreshold() {
        return fixedThreshold;
    }

    public Map<Modification, Double> getModificationRates() {
        return modificationRates;
    }

    public PtmSettings getPtmSettings() {
        return ptmSettings;
    }

}
