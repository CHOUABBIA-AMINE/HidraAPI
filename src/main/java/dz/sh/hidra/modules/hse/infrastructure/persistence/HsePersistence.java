/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HsePersistence
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence
 *
 * @Description : HSE database table constants.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence;

/**
 * HSE database table constants.
 */
public final class HsePersistence {

    public static final String HSE_CASE_TABLE = "hidra_hse_case";
    public static final String HSE_CASE_STATUS_HISTORY_TABLE = "hidra_hse_case_status_history";
    public static final String HSE_CASE_EVIDENCE_LINK_TABLE = "hidra_hse_case_evidence_link";
    public static final String HSE_IMPACT_ASSESSMENT_TABLE = "hidra_hse_impact_assessment";
    public static final String HSE_CORRECTIVE_PREVENTIVE_ACTION_TABLE = "hidra_hse_capa";
    public static final String HSE_CLOSURE_TABLE = "hidra_hse_closure";
    public static final String HAZARD_REPORT_TABLE = "hidra_hse_hazard_report";
    public static final String NEAR_MISS_REPORT_TABLE = "hidra_hse_near_miss_report";
    public static final String SAFETY_OBSERVATION_TABLE = "hidra_hse_safety_observation";
    public static final String ENVIRONMENTAL_EVENT_TABLE = "hidra_hse_environmental_event";
    public static final String PERMIT_TO_WORK_TABLE = "hidra_hse_permit_to_work";
    public static final String HSE_INSPECTION_TABLE = "hidra_hse_inspection";
    public static final String EMERGENCY_DRILL_TABLE = "hidra_hse_emergency_drill";
    public static final String COMPLIANCE_OBLIGATION_TABLE = "hidra_hse_compliance_obligation";
    public static final String COMPLIANCE_ASSESSMENT_TABLE = "hidra_hse_compliance_assessment";
    public static final String HSE_CATALOG_ENTRY_TABLE = "hidra_hse_catalog_entry";
    public static final String HSE_CATALOG_TRANSLATION_TABLE = "hidra_hse_catalog_translation";

    private HsePersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
