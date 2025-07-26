package com.medilabo.ms_riskassessment.service;

import com.medilabo.ms_riskassessment.beans.NoteBean;
import com.medilabo.ms_riskassessment.beans.PatientBean;
import com.medilabo.ms_riskassessment.domain.RiskLevel;
import com.medilabo.ms_riskassessment.domain.RiskTrigger;
import com.medilabo.ms_riskassessment.exception.PatientNotFoundException;
import com.medilabo.ms_riskassessment.proxies.MsNoteProxy;
import com.medilabo.ms_riskassessment.proxies.MsPatientProxy;
import com.medilabo.ms_riskassessment.service.impl.RiskService;
import com.medilabo.ms_riskassessment.utils.Utils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test class for the RiskService class.
 */
@ExtendWith(MockitoExtension.class)
public class RiskServiceTest {

    private RiskService riskService;

    @Mock
    private MsPatientProxy msPatientProxy;
    @Mock
    private MsNoteProxy msNoteProxy;

    private static MockedStatic<Utils> utils;
    private static MockedStatic<RiskTrigger> riskTrigger;

    /**
     * Sets up before all tests.
     */
    @BeforeAll
    public static void setUpBeforeAll() {
        utils = mockStatic(Utils.class);
        riskTrigger = mockStatic(RiskTrigger.class);
        riskTrigger.when(RiskTrigger::getLabels).thenReturn(List.of(
                "Hémoglobine A1C",
                "Microalbumine",
                "Taille",
                "Poids",
                "Fumeur",
                "Fumeuse",
                "Anormal",
                "Cholestérol",
                "Vertiges",
                "Rechute",
                "Réaction",
                "Anticorps"
        ));

    }

    /**
     * Sets up before each test.
     */
    @BeforeEach
    public void setUpPerTest() {
        riskService = new RiskService(msPatientProxy, msNoteProxy);
    }

    /**
     * Cleans up after all tests.
     */
    @AfterAll
    public static void tearDownAfterAll() {
        riskTrigger.close();
        utils.close();
    }

    /**
     * Test method: getAll
     * Given: a request to get all risk assessments
     * When: getAll is called
     * Then: Return a map of risk assessments
     */
    @Test
    public void given_whenGetAll_thenReturnRiskAssessments() {
        // When
        Map<String, String> result = riskService.getAll();

        // Then
        assertNotNull(result);
        assertEquals(result.size(), riskService.getAll().size());
    }

    /**
     * Test method: calculateRiskOfPatientId
     * Given: an invalid patient ID
     * When: calculateRiskOfPatientId is called
     * Then: Throw PatientNotFoundException
     */
    @Test
    public void givenInvalidPatientId_whenCalculateRiskOfPatientId_thenThrowPatientNotFoundException() {
        // Given
        int invalidPatientId = -1;
        doThrow(new PatientNotFoundException(invalidPatientId)).when(msPatientProxy).getByPatientId(invalidPatientId);

        // When & Then
        assertThrows(PatientNotFoundException.class, () -> riskService.calculateRiskOfPatientId(invalidPatientId));
    }

    /**
     * Test method: calculateRiskOfPatientId
     * Given: a patient with a birthdate in the future
     * When: calculateRiskOfPatientId is called
     * Then: Throw IllegalArgumentException
     */
    @Test
    public void givenBirthDateInFuture_whenCalculateRiskOfPatientId_thenIllegalArgumentException() {
        // Given
        int patientId = 1;
        LocalDate futureBirthDate = LocalDate.now().plusYears(1);
        when(msPatientProxy.getByPatientId(patientId)).thenReturn(new PatientBean(futureBirthDate.toString(), "M"));
        when(msNoteProxy.getByPatientId(patientId)).thenReturn(List.of());

        utils.when(() -> Utils.calculateAge(futureBirthDate)).thenThrow(new IllegalArgumentException("Birth date is after today"));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> riskService.calculateRiskOfPatientId(patientId));

    }

    /**
     * Provides test cases for the calculateRiskOfPatientId method.
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> calculateRiskOfPatientIdProvider() {
        return Stream.of(
                Arguments.of(
                        "Male below 30 with 1 risk trigger",
                        new PatientBean("1900-01-01", "M"),
                        20,
                        List.of(new NoteBean("Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé")),
                        RiskLevel.NONE
                ),
                Arguments.of(
                        "Female over 30 with 1 risk trigger",
                        new PatientBean("1900-01-01", "F"),
                        40,
                        List.of(new NoteBean("Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé")),
                        RiskLevel.NONE
                ),
                Arguments.of(
                        "Male over 30 with 5 risk triggers",
                        new PatientBean("1900-01-01", "M"),
                        31,
                        List.of(
                                new NoteBean("Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement"),
                                new NoteBean("Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale"),
                                new NoteBean("taille, poids")
                        ),
                        RiskLevel.BORDERLINE
                ),
                Arguments.of(
                        "Female over 30 with 7 risk triggers",
                        new PatientBean("1900-01-01", "F"),
                        31,
                        List.of(
                                new NoteBean("Le patient déclare qu'il fume depuis peu"),
                                new NoteBean("Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé"),
                                new NoteBean("Le patient rechute anormal suite à une réaction aux médicaments anticorps")
                        ),
                        RiskLevel.IN_DANGER
                ),
                Arguments.of(
                        "Male over 30 with 8 risk triggers",
                        new PatientBean("1900-01-01", "M"),
                        31,
                        List.of(
                                new NoteBean("Le patient déclare qu'il fume depuis peu"),
                                new NoteBean("Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé"),
                                new NoteBean("Le patient rechute anormal suite à une réaction aux médicaments anticorps microalbumine")
                        ),
                        RiskLevel.EARLY_ONSET
                ),
                Arguments.of(
                        "Male aged of 30 with 2 risk triggers",
                        new PatientBean("1900-01-01", "M"),
                        30,
                        List.of(
                                new NoteBean("anormal, microalbumine")
                        ),
                        RiskLevel.NA
                ),
                Arguments.of(
                        "Male aged of 30 with 4 risk triggers",
                        new PatientBean("1900-01-01", "M"),
                        30,
                        List.of(
                                new NoteBean("anormal, microalbumine, poids, taille")
                        ),
                        RiskLevel.IN_DANGER
                ),
                Arguments.of(
                        "Male aged of 30 with 5 risk triggers",
                        new PatientBean("1900-01-01", "M"),
                        30,
                        List.of(
                                new NoteBean("Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments"),
                                new NoteBean("Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"),
                                new NoteBean("Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé"),
                                new NoteBean("Taille, Poids, Cholestérol, Vertige et Réaction")
                        ),
                        RiskLevel.EARLY_ONSET
                ),
                Arguments.of(
                        "Female aged of 30 with 3 risk triggers",
                        new PatientBean("1900-01-01", "F"),
                        30,
                        List.of(
                                new NoteBean("anormal, microalbumine, poids")
                        ),
                        RiskLevel.NA
                ),
                Arguments.of(
                        "Female aged of 30 with 6 risk triggers",
                        new PatientBean("1900-01-01", "F"),
                        30,
                        List.of(
                                new NoteBean("anormal, microalbumine, poids, taille, cholestérol, vertiges")
                        ),
                        RiskLevel.IN_DANGER
                ),
                Arguments.of(
                        "Female aged of 30 with 7 risk triggers",
                        new PatientBean("1900-01-01", "F"),
                        30,
                        List.of(
                                new NoteBean("Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments"),
                                new NoteBean("Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"),
                                new NoteBean("Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé"),
                                new NoteBean("Taille, Poids, Cholestérol, Vertige et Réaction")
                        ),
                        RiskLevel.EARLY_ONSET
                )
        );
    }

    @ParameterizedTest(name = "{index} - {0} : {4}")
    @MethodSource("calculateRiskOfPatientIdProvider")
    public void givenParameters_whenCalculateRiskOfPatientId_thenReturnRiskLevel(String testName,
                                                                                 PatientBean patient,
                                                                                 int age, List<NoteBean> notes,
                                                                                 RiskLevel riskLevelExpected) {
        // Given
        when(msPatientProxy.getByPatientId(anyInt())).thenReturn(patient);
        when(msNoteProxy.getByPatientId(anyInt())).thenReturn(notes);
        when(Utils.calculateAge(any(LocalDate.class))).thenReturn(age);

        // When
        RiskLevel riskLevelActual = riskService.calculateRiskOfPatientId(anyInt());

        // Then
        assertEquals(riskLevelExpected, riskLevelActual);
    }


}
