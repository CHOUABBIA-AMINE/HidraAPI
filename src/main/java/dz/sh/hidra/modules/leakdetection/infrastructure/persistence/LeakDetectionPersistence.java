/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionPersistence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence
 *
 * @Description : Leak detection database table constants.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence;

/**
 * Leak detection database table constants.
 */
public final class LeakDetectionPersistence {

    public static final String LEAK_DETECTION_PROFILE_TABLE = "hidra_leak_detection_profile";
    public static final String LEAK_DETECTION_METHOD_CATALOG_TABLE = "hidra_leak_detection_method";
    public static final String LEAK_DETECTION_METHOD_TRANSLATION_TABLE = "hidra_leak_detection_method_translation";
    public static final String LEAK_DETECTION_RULE_TABLE = "hidra_leak_detection_rule";
    public static final String LEAK_DETECTION_RUN_TABLE = "hidra_leak_detection_run";
    public static final String LEAK_CANDIDATE_TABLE = "hidra_leak_detection_candidate";
    public static final String LEAK_EVIDENCE_LINK_TABLE = "hidra_leak_detection_evidence_link";
    public static final String LEAK_LOCALIZATION_ESTIMATE_TABLE = "hidra_leak_detection_localization_estimate";
    public static final String LEAK_SEVERITY_ASSESSMENT_TABLE = "hidra_leak_detection_severity_assessment";
    public static final String LEAK_VERIFICATION_ACTION_TABLE = "hidra_leak_detection_verification_action";
    public static final String LEAK_DETECTION_CASE_TABLE = "hidra_leak_detection_case";
    public static final String LEAK_CASE_STATUS_HISTORY_TABLE = "hidra_leak_detection_case_status_history";
    public static final String LEAK_ESCALATION_REFERENCE_TABLE = "hidra_leak_detection_escalation_reference";
    public static final String LEAK_DISMISSAL_REASON_TABLE = "hidra_leak_detection_dismissal_reason";

    private LeakDetectionPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
