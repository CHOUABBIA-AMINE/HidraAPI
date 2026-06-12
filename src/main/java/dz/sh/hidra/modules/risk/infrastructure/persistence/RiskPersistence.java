/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskPersistence
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence
 *
 * @Description : Risk database table constants.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence;

/**
 * Risk database table constants.
 */
public final class RiskPersistence {

    public static final String RISK_REGISTER_TABLE = "hidra_risk_register";
    public static final String RISK_ASSESSMENT_TABLE = "hidra_risk_assessment";
    public static final String RISK_ASSESSMENT_SCOPE_TABLE = "hidra_risk_assessment_scope";
    public static final String RISK_SOURCE_TABLE = "hidra_risk_source";
    public static final String RISK_SCENARIO_TABLE = "hidra_risk_scenario";
    public static final String RISK_THREAT_TABLE = "hidra_risk_threat";
    public static final String RISK_CONSEQUENCE_TABLE = "hidra_risk_consequence";
    public static final String RISK_LIKELIHOOD_TABLE = "hidra_risk_likelihood";
    public static final String RISK_EXPOSURE_TABLE = "hidra_risk_exposure";
    public static final String RISK_MATRIX_TABLE = "hidra_risk_matrix";
    public static final String RISK_MATRIX_CELL_TABLE = "hidra_risk_matrix_cell";
    public static final String RISK_SCORE_TABLE = "hidra_risk_score";
    public static final String RISK_RATING_TABLE = "hidra_risk_rating";
    public static final String RISK_CONTROL_TABLE = "hidra_risk_control";
    public static final String RISK_TREATMENT_PLAN_TABLE = "hidra_risk_treatment_plan";
    public static final String RISK_TREATMENT_ACTION_TABLE = "hidra_risk_treatment_action";
    public static final String RISK_MITIGATION_MEASURE_TABLE = "hidra_risk_mitigation_measure";
    public static final String RISK_ACCEPTANCE_TABLE = "hidra_risk_acceptance";
    public static final String RESIDUAL_RISK_ASSESSMENT_TABLE = "hidra_residual_risk_assessment";
    public static final String RISK_REVIEW_TABLE = "hidra_risk_review";
    public static final String RISK_EVIDENCE_LINK_TABLE = "hidra_risk_evidence_link";
    public static final String RISK_AGGREGATION_SNAPSHOT_TABLE = "hidra_risk_aggregation_snapshot";
    public static final String RISK_CATALOG_ENTRY_TABLE = "hidra_risk_catalog_entry";
    public static final String RISK_CATALOG_TRANSLATION_TABLE = "hidra_risk_catalog_translation";

    private RiskPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
