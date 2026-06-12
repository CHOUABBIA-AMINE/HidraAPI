/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityPersistence
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence
 *
 * @Description : Integrity database table constants.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence;

/**
 * Integrity database table constants.
 */
public final class IntegrityPersistence {

    public static final String INTEGRITY_PROGRAM_TABLE = "hidra_integrity_program";
    public static final String INTEGRITY_ASSESSMENT_TABLE = "hidra_integrity_assessment";
    public static final String INTEGRITY_ASSESSMENT_SCOPE_TABLE = "hidra_integrity_assessment_scope";
    public static final String INSPECTION_CAMPAIGN_TABLE = "hidra_integrity_inspection_campaign";
    public static final String INSPECTION_RUN_TABLE = "hidra_integrity_inspection_run";
    public static final String INSPECTION_FINDING_TABLE = "hidra_integrity_inspection_finding";
    public static final String PIPELINE_DEFECT_TABLE = "hidra_integrity_pipeline_defect";
    public static final String DEFECT_MEASUREMENT_TABLE = "hidra_integrity_defect_measurement";
    public static final String WALL_THICKNESS_MEASUREMENT_TABLE = "hidra_integrity_wall_thickness_measurement";
    public static final String CORROSION_FEATURE_TABLE = "hidra_integrity_corrosion_feature";
    public static final String COATING_CONDITION_OBSERVATION_TABLE = "hidra_integrity_coating_condition_observation";
    public static final String CATHODIC_PROTECTION_SURVEY_TABLE = "hidra_integrity_cathodic_protection_survey";
    public static final String CATHODIC_PROTECTION_MEASUREMENT_TABLE = "hidra_integrity_cathodic_protection_measurement";
    public static final String INTEGRITY_THREAT_TABLE = "hidra_integrity_threat";
    public static final String DEFECT_ASSESSMENT_TABLE = "hidra_integrity_defect_assessment";
    public static final String REMAINING_LIFE_ESTIMATE_TABLE = "hidra_integrity_remaining_life_estimate";
    public static final String INTEGRITY_RECOMMENDATION_TABLE = "hidra_integrity_recommendation";
    public static final String INTEGRITY_CASE_TABLE = "hidra_integrity_case";
    public static final String INTEGRITY_CASE_STATUS_HISTORY_TABLE = "hidra_integrity_case_status_history";
    public static final String INTEGRITY_EVIDENCE_LINK_TABLE = "hidra_integrity_evidence_link";
    public static final String INTEGRITY_CATALOG_ENTRY_TABLE = "hidra_integrity_catalog_entry";
    public static final String INTEGRITY_CATALOG_TRANSLATION_TABLE = "hidra_integrity_catalog_translation";

    private IntegrityPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
