/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningPersistence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence
 *
 * @Description : Planning database table constants.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence;

/**
 * Planning database table constants.
 */
public final class PlanningPersistence {

    public static final String PLANNING_PERIOD_TABLE = "hidra_planning_period";
    public static final String OPERATIONAL_PLAN_TABLE = "hidra_planning_operational_plan";
    public static final String PLAN_REVISION_TABLE = "hidra_planning_plan_revision";
    public static final String PLAN_SCENARIO_TABLE = "hidra_planning_plan_scenario";
    public static final String NOMINATION_TABLE = "hidra_planning_nomination";
    public static final String NOMINATION_SCHEDULE_LINE_TABLE = "hidra_planning_nomination_schedule_line";
    public static final String PLAN_TARGET_TABLE = "hidra_planning_plan_target";
    public static final String EXPECTED_FLOW_STATE_TABLE = "hidra_planning_expected_flow_state";
    public static final String PLANNED_OPERATION_WINDOW_TABLE = "hidra_planning_operation_window";
    public static final String PLAN_CONSTRAINT_TABLE = "hidra_planning_plan_constraint";
    public static final String PLAN_APPROVAL_REFERENCE_TABLE = "hidra_planning_plan_approval_reference";
    public static final String PLANNING_CATALOG_ENTRY_TABLE = "hidra_planning_catalog_entry";
    public static final String PLANNING_CATALOG_TRANSLATION_TABLE = "hidra_planning_catalog_translation";
    public static final String FORECAST_SERIES_TABLE = "hidra_planning_forecast_series";
    public static final String FORECAST_POINT_TABLE = "hidra_planning_forecast_point";
    public static final String PLAN_ACTUAL_REVIEW_SNAPSHOT_TABLE = "hidra_planning_actual_review_snapshot";

    private PlanningPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
