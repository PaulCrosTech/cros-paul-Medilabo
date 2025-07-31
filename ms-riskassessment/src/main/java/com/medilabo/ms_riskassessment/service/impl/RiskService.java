package com.medilabo.ms_riskassessment.service.impl;

import com.medilabo.ms_riskassessment.beans.NoteBean;
import com.medilabo.ms_riskassessment.beans.PatientBean;
import com.medilabo.ms_riskassessment.domain.RiskLevel;
import com.medilabo.ms_riskassessment.domain.RiskTrigger;
import com.medilabo.ms_riskassessment.proxies.MsNoteProxy;
import com.medilabo.ms_riskassessment.proxies.MsPatientProxy;
import com.medilabo.ms_riskassessment.service.IRiskService;
import com.medilabo.ms_riskassessment.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Service implementation for risk-assessment-related operations.
 */
@Service
@Slf4j
public class RiskService implements IRiskService {

    private final MsPatientProxy msPatientProxy;
    private final MsNoteProxy msNoteProxy;
    private static final int AGE_THRESHOLD = 30;

    /**
     * Constructor for RiskService.
     *
     * @param msPatientProxy the proxy for patient-related operations
     * @param msNoteProxy    the proxy for note-related operations
     */
    public RiskService(MsPatientProxy msPatientProxy, MsNoteProxy msNoteProxy) {
        this.msPatientProxy = msPatientProxy;
        this.msNoteProxy = msNoteProxy;
        log.info("====> RiskService initialized <====");
    }


    /**
     * Calculates the risk level of a patient based on their ID.
     *
     * @param patientId the ID of the patient
     * @return the risk level of the patient as a RiskLevel enum
     * @throws IllegalArgumentException if the patient ID is invalid, or if the birthdate is after today
     */
    @Override
    public RiskLevel calculateRiskOfPatientId(int patientId) throws IllegalArgumentException {

        // Retrieve main datas
        PatientBean patient = msPatientProxy.getByPatientId(patientId);
        List<NoteBean> notes = msNoteProxy.getByPatientId(patientId);
        boolean isAgeOverThreshold = isAgeOverThreshold(LocalDate.parse(patient.getBirthDate()));
        long riskTriggersQty = countRiskTriggers(notes, RiskTrigger.getLabels());

        log.debug("===> Calculating Risk of Patient Id {} <====", patientId);
        log.debug("===> age over {} : {}", AGE_THRESHOLD, isAgeOverThreshold);
        log.debug("===> gender {}", patient.getGender());
        log.debug("===> notes {}", notes);
        log.debug("===> riskTriggersQty {}", riskTriggersQty);

        // Default risk level
        RiskLevel riskLevel = RiskLevel.NA;

        if (riskTriggersQty <= 1) {
            log.debug("===> riskTriggersQty <= 1 <====");
            log.debug("===> Result {}", RiskLevel.NONE);
            return RiskLevel.NONE;
        }

        if (isAgeOverThreshold) {
            log.debug("===> Age > {} <====", AGE_THRESHOLD);
            if (riskTriggersQty >= 2 && riskTriggersQty <= 5) {
                log.debug("===> riskTriggersQty >= 2 && <=5 <====");
                riskLevel = RiskLevel.BORDERLINE;
            } else if (riskTriggersQty <= 7) {
                log.debug("===> riskTriggersQty <= 7 <====");
                riskLevel = RiskLevel.IN_DANGER;
            } else {
                log.debug("===> riskTriggersQty > 7 <====");
                riskLevel = RiskLevel.EARLY_ONSET;
            }
        } else {
            log.debug("===> Age <= {} <====", AGE_THRESHOLD);
            if (patient.getGender().equals("M")) {
                if (riskTriggersQty >= 3 && riskTriggersQty < 5) {
                    log.debug("===> riskTriggersQty >= 3 && <5 <====");
                    riskLevel = RiskLevel.IN_DANGER;
                } else if (riskTriggersQty >= 5) {
                    log.debug("===> riskTriggersQty >= 5 <====");
                    riskLevel = RiskLevel.EARLY_ONSET;
                }
            } else if (patient.getGender().equals("F")) {
                if (riskTriggersQty >= 4 && riskTriggersQty < 7) {
                    log.debug("===> riskTriggersQty >= 4 && <7 <====");
                    riskLevel = RiskLevel.IN_DANGER;
                } else if (riskTriggersQty >= 7) {
                    log.debug("===> riskTriggersQty >= 7 <====");
                    riskLevel = RiskLevel.EARLY_ONSET;
                }
            }
        }

        log.debug("===> Result {}", riskLevel);

        return riskLevel;
    }

    /**
     * Counts the number of risk triggers present in the patient's notes.
     * (Notes count risk triggers uniquely)
     *
     * @param notes        a List of NoteBean objects representing the patient's notes
     * @param riskTriggers a List of risk trigger labels to search for in the notes
     * @return the total count of risk triggers found in the notes
     */
    private long countRiskTriggers(List<NoteBean> notes, List<String> riskTriggers) {

        return notes.stream()
                .mapToLong(note -> {
                    List<String> matchingTriggers = riskTriggers.stream()
                            .filter(riskTrigger -> note.getNote().toLowerCase().contains(riskTrigger.toLowerCase()))
                            .distinct()
                            .collect(Collectors.toList());

                    log.debug("===> Mots trouvés dans la note '{}': {}", note.getNote(), matchingTriggers);

                    return matchingTriggers.size();
                })
                .sum();

    }


    /**
     * Checks if the patient is over a certain age threshold.
     *
     * @param birthDate the birthdate of the patient
     * @return true if the patient is over the specified age, false otherwise
     */
    private boolean isAgeOverThreshold(LocalDate birthDate) throws IllegalArgumentException {
        return Utils.calculateAge(birthDate) > AGE_THRESHOLD;
    }


    /**
     * Retrieves all risk assessments
     *
     * @return a Map (key,valu) of risk assessment levels
     */
    @Override
    public Map<String, String> getAll() {

        return Stream.of(RiskLevel.values())
                .collect(
                        Collectors.toMap(RiskLevel::name, RiskLevel::getLabel)
                );
    }
}
