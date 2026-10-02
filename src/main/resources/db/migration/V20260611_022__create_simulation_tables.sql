-- HIDRA simulation module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity
-- Module: simulation

CREATE TABLE IF NOT EXISTS hidra_simulation_candidate_change (
    id varchar(80) PRIMARY KEY,
    candidate_id varchar(80) NOT NULL,
    change_type_id varchar(80) NOT NULL,
    target_type varchar(80) NOT NULL,
    target_id varchar(120) NOT NULL,
    before_value varchar(1000),
    after_value varchar(1000) NOT NULL,
    unit_code varchar(40),
    requires_topology_change boolean NOT NULL,
    requires_operational_procedure boolean NOT NULL,
    safety_critical boolean NOT NULL,
    explanation varchar(2000),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_candidate_change_candidate_id ON hidra_simulation_candidate_change (candidate_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_candidate_change_change_type_id ON hidra_simulation_candidate_change (change_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_candidate_change_target_id ON hidra_simulation_candidate_change (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_candidate_change_created_at ON hidra_simulation_candidate_change (created_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_candidate_operating_condition (
    id varchar(80) PRIMARY KEY,
    candidate_id varchar(80) NOT NULL,
    target_type varchar(80) NOT NULL,
    target_id varchar(120) NOT NULL,
    metric_code varchar(120) NOT NULL,
    expected_value numeric(18,6) NOT NULL,
    unit_code varchar(40),
    time_offset_seconds bigint,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_candidate_operating_condition_candidate_ ON hidra_simulation_candidate_operating_condition (candidate_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_candidate_operating_condition_target_id ON hidra_simulation_candidate_operating_condition (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_candidate_operating_condition_created_at ON hidra_simulation_candidate_operating_condition (created_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_candidate_score (
    id varchar(80) PRIMARY KEY,
    candidate_id varchar(80) NOT NULL,
    objective_id varchar(80),
    score_code varchar(120) NOT NULL,
    score_value numeric(18,6) NOT NULL,
    weight numeric(18,6),
    rank_contribution numeric(18,6),
    explanation varchar(2000)
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_candidate_score_candidate_id ON hidra_simulation_candidate_score (candidate_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_candidate_score_objective_id ON hidra_simulation_candidate_score (objective_id);

CREATE TABLE IF NOT EXISTS hidra_simulation_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_catalog_entry_code ON hidra_simulation_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_catalog_entry_active ON hidra_simulation_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_catalog_entry_created_at ON hidra_simulation_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_catalog_entry_updated_at ON hidra_simulation_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description varchar(500),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_catalog_translation_catalog_entry_id ON hidra_simulation_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_catalog_translation_created_at ON hidra_simulation_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_catalog_translation_updated_at ON hidra_simulation_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_constraint_evaluation (
    id varchar(80) PRIMARY KEY,
    run_id varchar(80) NOT NULL,
    constraint_id varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    observed_value numeric(18,6),
    limit_value numeric(18,6),
    unit_code varchar(40),
    severity_id varchar(80) NOT NULL,
    explanation varchar(2000),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_constraint_evaluation_run_id ON hidra_simulation_constraint_evaluation (run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_constraint_evaluation_constraint_id ON hidra_simulation_constraint_evaluation (constraint_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_constraint_evaluation_status ON hidra_simulation_constraint_evaluation (status);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_constraint_evaluation_severity_id ON hidra_simulation_constraint_evaluation (severity_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_constraint_evaluation_created_at ON hidra_simulation_constraint_evaluation (created_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_constraint (
    id varchar(80) PRIMARY KEY,
    scenario_id varchar(80) NOT NULL,
    constraint_type_id varchar(80) NOT NULL,
    target_type varchar(80),
    target_id varchar(120),
    expression_text varchar(2000) NOT NULL,
    limit_value numeric(18,6),
    unit_code varchar(40),
    severity_id varchar(80) NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_constraint_scenario_id ON hidra_simulation_constraint (scenario_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_constraint_constraint_type_id ON hidra_simulation_constraint (constraint_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_constraint_target_id ON hidra_simulation_constraint (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_constraint_severity_id ON hidra_simulation_constraint (severity_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_constraint_active ON hidra_simulation_constraint (active);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_constraint_created_at ON hidra_simulation_constraint (created_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_evidence_link (
    id varchar(80) PRIMARY KEY,
    owner_type varchar(40) NOT NULL,
    owner_id varchar(80) NOT NULL,
    evidence_type varchar(40) NOT NULL,
    evidence_reference varchar(255) NOT NULL,
    label varchar(240),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_evidence_link_owner_id ON hidra_simulation_evidence_link (owner_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_evidence_link_created_at ON hidra_simulation_evidence_link (created_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_input_dataset (
    id varchar(80) PRIMARY KEY,
    input_snapshot_id varchar(80) NOT NULL,
    dataset_type_id varchar(80) NOT NULL,
    source_module varchar(80) NOT NULL,
    source_reference varchar(255) NOT NULL,
    record_count bigint,
    checksum varchar(160),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_input_dataset_input_snapshot_id ON hidra_simulation_input_dataset (input_snapshot_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_input_dataset_dataset_type_id ON hidra_simulation_input_dataset (dataset_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_input_dataset_created_at ON hidra_simulation_input_dataset (created_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_input_snapshot (
    id varchar(80) PRIMARY KEY,
    scenario_id varchar(80) NOT NULL,
    topology_snapshot_id varchar(120) NOT NULL,
    telemetry_snapshot_reference varchar(255),
    planning_snapshot_reference varchar(255),
    monitoring_snapshot_reference varchar(255),
    integrity_snapshot_reference varchar(255),
    asset_availability_snapshot_reference varchar(255),
    captured_at timestamp with time zone NOT NULL,
    capture_hash varchar(160) NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_input_snapshot_scenario_id ON hidra_simulation_input_snapshot (scenario_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_input_snapshot_topology_snapshot_id ON hidra_simulation_input_snapshot (topology_snapshot_id);

CREATE TABLE IF NOT EXISTS hidra_simulation_model (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    model_type_id varchar(80) NOT NULL,
    topology_scope_type varchar(80) NOT NULL,
    topology_scope_id varchar(120),
    status varchar(40) NOT NULL,
    description varchar(1000),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_model_code ON hidra_simulation_model (code);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_model_model_type_id ON hidra_simulation_model (model_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_model_topology_scope_id ON hidra_simulation_model (topology_scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_model_status ON hidra_simulation_model (status);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_model_created_at ON hidra_simulation_model (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_model_updated_at ON hidra_simulation_model (updated_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_model_version (
    id varchar(80) PRIMARY KEY,
    model_id varchar(80) NOT NULL,
    version_number integer NOT NULL,
    solver_profile_id varchar(80) NOT NULL,
    model_definition_hash varchar(160) NOT NULL,
    compatible_topology_version varchar(120),
    status varchar(40) NOT NULL,
    activated_at timestamp with time zone,
    retired_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_model_version_model_id ON hidra_simulation_model_version (model_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_model_version_solver_profile_id ON hidra_simulation_model_version (solver_profile_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_model_version_status ON hidra_simulation_model_version (status);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_model_version_created_at ON hidra_simulation_model_version (created_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_objective (
    id varchar(80) PRIMARY KEY,
    scenario_id varchar(80) NOT NULL,
    objective_type_id varchar(80) NOT NULL,
    weight numeric(18,6) NOT NULL,
    priority_order integer NOT NULL,
    target_type varchar(80),
    target_id varchar(120),
    expression_text varchar(2000),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_objective_scenario_id ON hidra_simulation_objective (scenario_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_objective_objective_type_id ON hidra_simulation_objective (objective_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_objective_target_id ON hidra_simulation_objective (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_objective_created_at ON hidra_simulation_objective (created_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_optimization_candidate (
    id varchar(80) PRIMARY KEY,
    run_id varchar(80) NOT NULL,
    candidate_number integer NOT NULL,
    candidate_status varchar(40) NOT NULL,
    feasible boolean NOT NULL,
    objective_score numeric(18,6),
    rank integer,
    summary_text varchar(2000),
    selected_by_actor_id varchar(80),
    selected_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_optimization_candidate_run_id ON hidra_simulation_optimization_candidate (run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_optimization_candidate_selected_by_actor ON hidra_simulation_optimization_candidate (selected_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_optimization_candidate_created_at ON hidra_simulation_optimization_candidate (created_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_recommendation (
    id varchar(80) PRIMARY KEY,
    run_id varchar(80) NOT NULL,
    candidate_id varchar(80),
    recommendation_type_id varchar(80) NOT NULL,
    recommendation_status varchar(40) NOT NULL,
    title varchar(255) NOT NULL,
    description varchar(3000) NOT NULL,
    confidence_level_id varchar(80),
    target_module varchar(80),
    target_proposal_reference varchar(120),
    published_by_actor_id varchar(80),
    published_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_recommendation_run_id ON hidra_simulation_recommendation (run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_recommendation_candidate_id ON hidra_simulation_recommendation (candidate_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_recommendation_recommendation_type_id ON hidra_simulation_recommendation (recommendation_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_recommendation_confidence_level_id ON hidra_simulation_recommendation (confidence_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_recommendation_published_by_actor_id ON hidra_simulation_recommendation (published_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_recommendation_created_at ON hidra_simulation_recommendation (created_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_result_series_reference (
    id varchar(80) PRIMARY KEY,
    run_id varchar(80) NOT NULL,
    series_type_id varchar(80) NOT NULL,
    target_type varchar(80),
    target_id varchar(120),
    storage_location varchar(1000) NOT NULL,
    checksum varchar(160),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_result_series_reference_run_id ON hidra_simulation_result_series_reference (run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_result_series_reference_series_type_id ON hidra_simulation_result_series_reference (series_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_result_series_reference_target_id ON hidra_simulation_result_series_reference (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_result_series_reference_created_at ON hidra_simulation_result_series_reference (created_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_result_summary (
    id varchar(80) PRIMARY KEY,
    run_id varchar(80) NOT NULL,
    feasible boolean NOT NULL,
    objective_score numeric(18,6),
    constraint_violation_count integer NOT NULL,
    warning_count integer NOT NULL,
    result_status_id varchar(80) NOT NULL,
    summary_text varchar(2000),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_result_summary_run_id ON hidra_simulation_result_summary (run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_result_summary_result_status_id ON hidra_simulation_result_summary (result_status_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_result_summary_created_at ON hidra_simulation_result_summary (created_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_result_value (
    id varchar(80) PRIMARY KEY,
    run_id varchar(80) NOT NULL,
    target_type varchar(80) NOT NULL,
    target_id varchar(120) NOT NULL,
    metric_code varchar(120) NOT NULL,
    value numeric(18,6) NOT NULL,
    unit_code varchar(40),
    time_offset_seconds bigint,
    recorded_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_result_value_run_id ON hidra_simulation_result_value (run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_result_value_target_id ON hidra_simulation_result_value (target_id);

CREATE TABLE IF NOT EXISTS hidra_simulation_run (
    id varchar(80) PRIMARY KEY,
    scenario_id varchar(80) NOT NULL,
    model_version_id varchar(80) NOT NULL,
    input_snapshot_id varchar(80) NOT NULL,
    run_type_id varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    requested_by_actor_id varchar(80) NOT NULL,
    requested_by_display_name_snapshot varchar(160) NOT NULL,
    queued_at timestamp with time zone NOT NULL,
    started_at timestamp with time zone,
    completed_at timestamp with time zone,
    duration_millis bigint,
    solver_profile_id varchar(80) NOT NULL,
    correlation_id varchar(120),
    failure_reason varchar(2000),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_run_scenario_id ON hidra_simulation_run (scenario_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_run_model_version_id ON hidra_simulation_run (model_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_run_input_snapshot_id ON hidra_simulation_run (input_snapshot_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_run_run_type_id ON hidra_simulation_run (run_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_run_status ON hidra_simulation_run (status);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_run_requested_by_actor_id ON hidra_simulation_run (requested_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_run_solver_profile_id ON hidra_simulation_run (solver_profile_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_run_correlation_id ON hidra_simulation_run (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_run_created_at ON hidra_simulation_run (created_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_run_step (
    id varchar(80) PRIMARY KEY,
    run_id varchar(80) NOT NULL,
    step_order integer NOT NULL,
    step_code varchar(120) NOT NULL,
    status varchar(40) NOT NULL,
    started_at timestamp with time zone,
    completed_at timestamp with time zone,
    message varchar(2000)
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_run_step_run_id ON hidra_simulation_run_step (run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_run_step_status ON hidra_simulation_run_step (status);

CREATE TABLE IF NOT EXISTS hidra_simulation_scenario_assumption (
    id varchar(80) PRIMARY KEY,
    scenario_id varchar(80) NOT NULL,
    assumption_type_id varchar(80) NOT NULL,
    target_type varchar(80),
    target_id varchar(120),
    parameter_code varchar(120) NOT NULL,
    value_type varchar(40) NOT NULL,
    value_text varchar(2000) NOT NULL,
    unit_code varchar(40),
    confidence_level_id varchar(80),
    source_note varchar(1000),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_scenario_assumption_scenario_id ON hidra_simulation_scenario_assumption (scenario_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_scenario_assumption_assumption_type_id ON hidra_simulation_scenario_assumption (assumption_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_scenario_assumption_target_id ON hidra_simulation_scenario_assumption (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_scenario_assumption_confidence_level_id ON hidra_simulation_scenario_assumption (confidence_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_scenario_assumption_created_at ON hidra_simulation_scenario_assumption (created_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_scenario (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    scenario_type_id varchar(80) NOT NULL,
    model_id varchar(80) NOT NULL,
    model_version_id varchar(80) NOT NULL,
    topology_snapshot_id varchar(120) NOT NULL,
    planning_reference_id varchar(120),
    monitoring_context_id varchar(120),
    status varchar(40) NOT NULL,
    created_by_actor_id varchar(80) NOT NULL,
    created_by_display_name_snapshot varchar(160) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_scenario_code ON hidra_simulation_scenario (code);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_scenario_scenario_type_id ON hidra_simulation_scenario (scenario_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_scenario_model_id ON hidra_simulation_scenario (model_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_scenario_model_version_id ON hidra_simulation_scenario (model_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_scenario_topology_snapshot_id ON hidra_simulation_scenario (topology_snapshot_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_scenario_planning_reference_id ON hidra_simulation_scenario (planning_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_scenario_monitoring_context_id ON hidra_simulation_scenario (monitoring_context_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_scenario_status ON hidra_simulation_scenario (status);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_scenario_created_by_actor_id ON hidra_simulation_scenario (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_scenario_created_at ON hidra_simulation_scenario (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_scenario_updated_at ON hidra_simulation_scenario (updated_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_sensitivity_analysis (
    id varchar(80) PRIMARY KEY,
    scenario_id varchar(80) NOT NULL,
    base_run_id varchar(80) NOT NULL,
    parameter_code varchar(120) NOT NULL,
    parameter_range_text varchar(1000) NOT NULL,
    result_metric_code varchar(120) NOT NULL,
    sensitivity_score numeric(18,6),
    summary_text varchar(2000),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_sensitivity_analysis_scenario_id ON hidra_simulation_sensitivity_analysis (scenario_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_sensitivity_analysis_base_run_id ON hidra_simulation_sensitivity_analysis (base_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_sensitivity_analysis_created_at ON hidra_simulation_sensitivity_analysis (created_at);

CREATE TABLE IF NOT EXISTS hidra_simulation_solver_trace (
    id varchar(80) PRIMARY KEY,
    run_id varchar(80) NOT NULL,
    iteration_number integer,
    trace_level varchar(40) NOT NULL,
    metric_code varchar(120),
    metric_value numeric(18,6),
    message varchar(2000),
    recorded_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_solver_trace_run_id ON hidra_simulation_solver_trace (run_id);

CREATE TABLE IF NOT EXISTS hidra_simulation_validation_finding (
    id varchar(80) PRIMARY KEY,
    scenario_id varchar(80),
    run_id varchar(80),
    finding_type_id varchar(80) NOT NULL,
    severity_id varchar(80) NOT NULL,
    target_type varchar(80),
    target_id varchar(120),
    message varchar(2000) NOT NULL,
    resolved boolean NOT NULL,
    resolved_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_validation_finding_scenario_id ON hidra_simulation_validation_finding (scenario_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_validation_finding_run_id ON hidra_simulation_validation_finding (run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_validation_finding_finding_type_id ON hidra_simulation_validation_finding (finding_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_validation_finding_severity_id ON hidra_simulation_validation_finding (severity_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_validation_finding_target_id ON hidra_simulation_validation_finding (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_simulation_validation_finding_created_at ON hidra_simulation_validation_finding (created_at);
