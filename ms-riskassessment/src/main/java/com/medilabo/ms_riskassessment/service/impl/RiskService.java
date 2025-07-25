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

    public RiskService(MsPatientProxy msPatientProxy, MsNoteProxy msNoteProxy) {
        this.msPatientProxy = msPatientProxy;
        this.msNoteProxy = msNoteProxy;
        log.info("====> RiskService initialized <====");
    }

    /**
     * Constructor for RiskService.
     */
    @Override
    public RiskLevel calculateRiskOfPatientId(int patientId) {

        // Retrieve main datas
        PatientBean patient = msPatientProxy.getByPatientId(patientId);
        List<NoteBean> notes = msNoteProxy.getByPatientId(patientId);
        int age = Utils.calculateAge(LocalDate.parse(patient.getBirthDate()));
        long riskTriggersQty = countRiskTriggers(notes, RiskTrigger.getLabels());


        log.info("===> Calculating Risk of Patient Id {} <====", patientId);
        log.info("===> age {}", age);
        log.info("===> gender {}", patient.getGender());
        log.info("===> notes {}", notes);
        log.info("===> riskTriggersQty {}", riskTriggersQty);
//        log.info("===> RiskTrigger {}", RiskTrigger.HEMOGLOBINE_A1C);
//        log.info("===> RiskTrigger {}", RiskTrigger.HEMOGLOBINE_A1C.getLabel());
//        log.info("===> RiskTrigger {}", RiskTrigger.getLabels());

        // Main risk assessment logic
//        RiskLevel riskLevel = RiskLevel.NA;
//

        // Female <= 30 with riskTriggersQty == 2 or 3 => NONE
        // Male <= 30 with riskTriggersQty <= 2 ==> NONE
        // Male Or Female : riskTriggersQty <= 1 ==> NONE
        RiskLevel riskLevel = RiskLevel.NONE;

        if (riskTriggersQty > 1) {

            if (age > 30) {
                log.info("===> Age > 30 <====");
                if (riskTriggersQty >= 2 && riskTriggersQty <= 5) {
                    log.info("===> riskTriggersQty >= 2 && <=5 <====");
                    riskLevel = RiskLevel.BORDERLINE;
                } else if (riskTriggersQty <= 7) {
                    log.info("===> riskTriggersQty <= 7 <====");
                    riskLevel = RiskLevel.IN_DANGER;
                } else {
                    log.info("===> riskTriggersQty > 7 <====");
                    riskLevel = RiskLevel.EARLY_ONSET;
                }
            } else {
                log.info("===> Age <= 30 <====");
                if (patient.getGender().equals("M")) {
                    if (riskTriggersQty >= 3 && riskTriggersQty < 5) {
                        log.info("===> riskTriggersQty >= 3 && <5 <====");
                        riskLevel = RiskLevel.IN_DANGER;
                    } else if (riskTriggersQty >= 5) {
                        log.info("===> riskTriggersQty >= 5 <====");
                        riskLevel = RiskLevel.EARLY_ONSET;
                    }
                } else if (patient.getGender().equals("F")) {
                    if (riskTriggersQty >= 4 && riskTriggersQty < 7) {
                        log.info("===> riskTriggersQty >= 4 && <=7 <====");
                        riskLevel = RiskLevel.IN_DANGER;
                    } else if (riskTriggersQty >= 7) {
                        log.info("===> riskTriggersQty >= 7 <====");
                        riskLevel = RiskLevel.EARLY_ONSET;
                    }
                }
            }
        }

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

//        return notes.stream()
//                .mapToLong(
//                        note -> riskTriggers.stream()
//                                .filter(riskTrigger -> note.getNote().toLowerCase().contains(riskTrigger.toLowerCase()))
//                                .distinct()
//                                .count()
//
//                )
//                .sum();

        return notes.stream()
                .mapToLong(note -> {
                    List<String> matchingTriggers = riskTriggers.stream()
                            .filter(riskTrigger -> note.getNote().toLowerCase().contains(riskTrigger.toLowerCase()))
                            .distinct()
                            .collect(Collectors.toList());

                    log.info("===> Mots trouvés dans la note '{}': {}", note.getNote(), matchingTriggers);

                    return matchingTriggers.size();
                })
                .sum();

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
