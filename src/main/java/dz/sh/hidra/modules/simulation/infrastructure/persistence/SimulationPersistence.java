/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationPersistence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence
 *
 * @Description : Simulation database table constants.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence;

/**
 * Simulation database table constants.
 */
public final class SimulationPersistence {

    public static final String SIMULATION_MODEL_TABLE = "hidra_simulation_model";
    public static final String SIMULATION_MODEL_VERSION_TABLE = "hidra_simulation_model_version";
    public static final String SIMULATION_SCENARIO_TABLE = "hidra_simulation_scenario";
    public static final String SIMULATION_SCENARIO_ASSUMPTION_TABLE = "hidra_simulation_scenario_assumption";
    public static final String SIMULATION_INPUT_SNAPSHOT_TABLE = "hidra_simulation_input_snapshot";
    public static final String SIMULATION_INPUT_DATASET_TABLE = "hidra_simulation_input_dataset";
    public static final String SIMULATION_CONSTRAINT_TABLE = "hidra_simulation_constraint";
    public static final String SIMULATION_OBJECTIVE_TABLE = "hidra_simulation_objective";
    public static final String SIMULATION_RUN_TABLE = "hidra_simulation_run";
    public static final String SIMULATION_RUN_STEP_TABLE = "hidra_simulation_run_step";
    public static final String SIMULATION_SOLVER_TRACE_TABLE = "hidra_simulation_solver_trace";
    public static final String SIMULATION_RESULT_SUMMARY_TABLE = "hidra_simulation_result_summary";
    public static final String SIMULATION_RESULT_VALUE_TABLE = "hidra_simulation_result_value";
    public static final String SIMULATION_RESULT_SERIES_REFERENCE_TABLE = "hidra_simulation_result_series_reference";
    public static final String SIMULATION_CONSTRAINT_EVALUATION_TABLE = "hidra_simulation_constraint_evaluation";
    public static final String SIMULATION_OPTIMIZATION_CANDIDATE_TABLE = "hidra_simulation_optimization_candidate";
    public static final String SIMULATION_CANDIDATE_CHANGE_TABLE = "hidra_simulation_candidate_change";
    public static final String SIMULATION_CANDIDATE_OPERATING_CONDITION_TABLE = "hidra_simulation_candidate_operating_condition";
    public static final String SIMULATION_CANDIDATE_SCORE_TABLE = "hidra_simulation_candidate_score";
    public static final String SIMULATION_RECOMMENDATION_TABLE = "hidra_simulation_recommendation";
    public static final String SIMULATION_VALIDATION_FINDING_TABLE = "hidra_simulation_validation_finding";
    public static final String SIMULATION_SENSITIVITY_ANALYSIS_TABLE = "hidra_simulation_sensitivity_analysis";
    public static final String SIMULATION_EVIDENCE_LINK_TABLE = "hidra_simulation_evidence_link";
    public static final String SIMULATION_CATALOG_ENTRY_TABLE = "hidra_simulation_catalog_entry";
    public static final String SIMULATION_CATALOG_TRANSLATION_TABLE = "hidra_simulation_catalog_translation";

    private SimulationPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
