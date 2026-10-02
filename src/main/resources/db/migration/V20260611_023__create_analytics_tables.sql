-- HIDRA analytics module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity
-- Module: analytics

CREATE TABLE IF NOT EXISTS hidra_analytics_access_policy (
    id varchar(80) PRIMARY KEY,
    analytics_object_type varchar(120) NOT NULL,
    analytics_object_id varchar(120) NOT NULL,
    access_scope_type varchar(80) NOT NULL,
    access_scope_id varchar(120) NOT NULL,
    permission_code varchar(120) NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_access_policy_analytics_object_id ON hidra_analytics_access_policy (analytics_object_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_access_policy_access_scope_id ON hidra_analytics_access_policy (access_scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_access_policy_active ON hidra_analytics_access_policy (active);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_access_policy_created_at ON hidra_analytics_access_policy (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_access_policy_updated_at ON hidra_analytics_access_policy (updated_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_catalog_entry_code ON hidra_analytics_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_catalog_entry_active ON hidra_analytics_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_catalog_entry_created_at ON hidra_analytics_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_catalog_entry_updated_at ON hidra_analytics_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description varchar(500),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_catalog_translation_catalog_entry_id ON hidra_analytics_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_catalog_translation_created_at ON hidra_analytics_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_catalog_translation_updated_at ON hidra_analytics_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_data_source_reference (
    id varchar(80) PRIMARY KEY,
    source_module varchar(80) NOT NULL,
    source_type varchar(120) NOT NULL,
    source_name varchar(160) NOT NULL,
    source_version varchar(80),
    access_mode varchar(40) NOT NULL,
    refresh_mode varchar(40) NOT NULL,
    trusted boolean NOT NULL,
    last_available_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_data_source_reference_created_at ON hidra_analytics_data_source_reference (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_data_source_reference_updated_at ON hidra_analytics_data_source_reference (updated_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_dataset (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    subject_area_id varchar(80) NOT NULL,
    dataset_type varchar(40) NOT NULL,
    refresh_mode varchar(40) NOT NULL,
    lineage_status varchar(40) NOT NULL,
    quality_status varchar(40) NOT NULL,
    schema_version varchar(80),
    created_from varchar(255),
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_dataset_code ON hidra_analytics_dataset (code);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_dataset_subject_area_id ON hidra_analytics_dataset (subject_area_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_dataset_created_at ON hidra_analytics_dataset (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_dataset_updated_at ON hidra_analytics_dataset (updated_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_dataset_lineage (
    id varchar(80) PRIMARY KEY,
    dataset_version_id varchar(80) NOT NULL,
    source_module varchar(80) NOT NULL,
    source_object_type varchar(120) NOT NULL,
    source_object_id varchar(120) NOT NULL,
    source_snapshot_id varchar(120),
    source_version varchar(80),
    source_period_start timestamp with time zone,
    source_period_end timestamp with time zone,
    lineage_role varchar(120) NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_dataset_lineage_dataset_version_id ON hidra_analytics_dataset_lineage (dataset_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_dataset_lineage_source_object_id ON hidra_analytics_dataset_lineage (source_object_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_dataset_lineage_source_snapshot_id ON hidra_analytics_dataset_lineage (source_snapshot_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_dataset_lineage_created_at ON hidra_analytics_dataset_lineage (created_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_dataset_version (
    id varchar(80) PRIMARY KEY,
    dataset_id varchar(80) NOT NULL,
    version_number integer NOT NULL,
    schema_hash varchar(160) NOT NULL,
    data_hash varchar(160) NOT NULL,
    row_count bigint,
    period_start timestamp with time zone,
    period_end timestamp with time zone,
    quality_score numeric(10,6),
    published boolean NOT NULL,
    published_at timestamp with time zone,
    published_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_dataset_version_dataset_id ON hidra_analytics_dataset_version (dataset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_dataset_version_published_by_actor_id ON hidra_analytics_dataset_version (published_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_dataset_version_created_at ON hidra_analytics_dataset_version (created_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_feature_set (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    subject_area_id varchar(80) NOT NULL,
    source_dataset_id varchar(80) NOT NULL,
    feature_schema_version varchar(80) NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_feature_set_code ON hidra_analytics_feature_set (code);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_feature_set_subject_area_id ON hidra_analytics_feature_set (subject_area_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_feature_set_source_dataset_id ON hidra_analytics_feature_set (source_dataset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_feature_set_active ON hidra_analytics_feature_set (active);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_feature_set_created_at ON hidra_analytics_feature_set (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_feature_set_updated_at ON hidra_analytics_feature_set (updated_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_feature_value (
    id varchar(80) PRIMARY KEY,
    feature_set_id varchar(80) NOT NULL,
    dataset_version_id varchar(80) NOT NULL,
    scope_type varchar(80) NOT NULL,
    scope_id varchar(120),
    feature_name varchar(160) NOT NULL,
    feature_value_numeric numeric(18,6),
    feature_value_text varchar(2000),
    feature_value_boolean boolean,
    period_start timestamp with time zone,
    period_end timestamp with time zone,
    calculated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_feature_value_feature_set_id ON hidra_analytics_feature_value (feature_set_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_feature_value_dataset_version_id ON hidra_analytics_feature_value (dataset_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_feature_value_scope_id ON hidra_analytics_feature_value (scope_id);

CREATE TABLE IF NOT EXISTS hidra_analytics_insight_evidence (
    id varchar(80) PRIMARY KEY,
    analytics_insight_id varchar(80) NOT NULL,
    evidence_type varchar(120) NOT NULL,
    source_module varchar(80) NOT NULL,
    source_object_type varchar(120) NOT NULL,
    source_object_id varchar(120) NOT NULL,
    source_label_snapshot varchar(240),
    weight numeric(10,6),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_insight_evidence_analytics_insight_id ON hidra_analytics_insight_evidence (analytics_insight_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_insight_evidence_source_object_id ON hidra_analytics_insight_evidence (source_object_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_insight_evidence_created_at ON hidra_analytics_insight_evidence (created_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_insight (
    id varchar(80) PRIMARY KEY,
    insight_type varchar(120) NOT NULL,
    subject_area_id varchar(80) NOT NULL,
    scope_type varchar(80) NOT NULL,
    scope_id varchar(120),
    title varchar(255) NOT NULL,
    summary varchar(3000) NOT NULL,
    severity_id varchar(80),
    confidence_score numeric(10,6),
    source_projection_snapshot_id varchar(80),
    source_trend_analysis_id varchar(80),
    source_model_run_id varchar(80),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_insight_subject_area_id ON hidra_analytics_insight (subject_area_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_insight_scope_id ON hidra_analytics_insight (scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_insight_severity_id ON hidra_analytics_insight (severity_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_insight_source_projection_snapshot_id ON hidra_analytics_insight (source_projection_snapshot_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_insight_source_trend_analysis_id ON hidra_analytics_insight (source_trend_analysis_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_insight_source_model_run_id ON hidra_analytics_insight (source_model_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_insight_status ON hidra_analytics_insight (status);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_insight_created_at ON hidra_analytics_insight (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_insight_updated_at ON hidra_analytics_insight (updated_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_model (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    model_type varchar(120) NOT NULL,
    subject_area_id varchar(80) NOT NULL,
    owner_module varchar(80),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_code ON hidra_analytics_model (code);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_subject_area_id ON hidra_analytics_model (subject_area_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_status ON hidra_analytics_model (status);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_created_at ON hidra_analytics_model (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_updated_at ON hidra_analytics_model (updated_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_model_run (
    id varchar(80) PRIMARY KEY,
    analytics_model_version_id varchar(80) NOT NULL,
    run_type varchar(40) NOT NULL,
    run_status varchar(40) NOT NULL,
    input_dataset_version_id varchar(80),
    scope_type varchar(80),
    scope_id varchar(120),
    period_start timestamp with time zone,
    period_end timestamp with time zone,
    started_at timestamp with time zone NOT NULL,
    completed_at timestamp with time zone,
    output_dataset_version_id varchar(80),
    error_code varchar(120),
    error_message varchar(2000),
    correlation_id varchar(120),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_run_analytics_model_version_id ON hidra_analytics_model_run (analytics_model_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_run_input_dataset_version_id ON hidra_analytics_model_run (input_dataset_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_run_scope_id ON hidra_analytics_model_run (scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_run_output_dataset_version_id ON hidra_analytics_model_run (output_dataset_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_run_correlation_id ON hidra_analytics_model_run (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_run_created_at ON hidra_analytics_model_run (created_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_model_version (
    id varchar(80) PRIMARY KEY,
    analytics_model_id varchar(80) NOT NULL,
    version_number integer NOT NULL,
    model_artifact_reference varchar(500) NOT NULL,
    training_dataset_version_id varchar(80),
    validation_dataset_version_id varchar(80),
    model_parameters_json jsonb,
    performance_summary_json jsonb,
    status varchar(40) NOT NULL,
    created_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_version_analytics_model_id ON hidra_analytics_model_version (analytics_model_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_version_training_dataset_version_id ON hidra_analytics_model_version (training_dataset_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_version_validation_dataset_version_ ON hidra_analytics_model_version (validation_dataset_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_version_status ON hidra_analytics_model_version (status);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_version_created_by_actor_id ON hidra_analytics_model_version (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_model_version_created_at ON hidra_analytics_model_version (created_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_projection_definition (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    subject_area_id varchar(80) NOT NULL,
    projection_type varchar(120) NOT NULL,
    calculation_policy varchar(1000),
    refresh_policy varchar(500),
    retention_policy varchar(500),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_projection_definition_code ON hidra_analytics_projection_definition (code);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_projection_definition_subject_area_id ON hidra_analytics_projection_definition (subject_area_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_projection_definition_active ON hidra_analytics_projection_definition (active);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_projection_definition_created_at ON hidra_analytics_projection_definition (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_projection_definition_updated_at ON hidra_analytics_projection_definition (updated_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_projection_run (
    id varchar(80) PRIMARY KEY,
    projection_definition_id varchar(80) NOT NULL,
    run_status varchar(40) NOT NULL,
    run_mode varchar(40) NOT NULL,
    period_start timestamp with time zone,
    period_end timestamp with time zone,
    started_at timestamp with time zone NOT NULL,
    completed_at timestamp with time zone,
    source_watermark varchar(255),
    records_read bigint,
    records_written bigint,
    error_code varchar(120),
    error_message varchar(2000),
    correlation_id varchar(120),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_projection_run_projection_definition_id ON hidra_analytics_projection_run (projection_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_projection_run_correlation_id ON hidra_analytics_projection_run (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_projection_run_created_at ON hidra_analytics_projection_run (created_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_projection_snapshot (
    id varchar(80) PRIMARY KEY,
    projection_definition_id varchar(80) NOT NULL,
    projection_run_id varchar(80) NOT NULL,
    snapshot_code varchar(120) NOT NULL,
    period_start timestamp with time zone,
    period_end timestamp with time zone,
    snapshot_status varchar(40) NOT NULL,
    published_at timestamp with time zone,
    published_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_projection_snapshot_projection_definition ON hidra_analytics_projection_snapshot (projection_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_projection_snapshot_projection_run_id ON hidra_analytics_projection_snapshot (projection_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_projection_snapshot_published_by_actor_id ON hidra_analytics_projection_snapshot (published_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_projection_snapshot_created_at ON hidra_analytics_projection_snapshot (created_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_subject_area (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    description varchar(1000),
    owner_module varchar(80),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_subject_area_code ON hidra_analytics_subject_area (code);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_subject_area_active ON hidra_analytics_subject_area (active);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_subject_area_created_at ON hidra_analytics_subject_area (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_subject_area_updated_at ON hidra_analytics_subject_area (updated_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_digital_twin_readiness_assessment (
    id varchar(80) PRIMARY KEY,
    scope_type varchar(80) NOT NULL,
    scope_id varchar(120),
    topology_snapshot_id varchar(120) NOT NULL,
    assessment_period_start timestamp with time zone NOT NULL,
    assessment_period_end timestamp with time zone NOT NULL,
    telemetry_completeness_score numeric(10,6),
    telemetry_quality_score numeric(10,6),
    topology_completeness_score numeric(10,6),
    model_availability_score numeric(10,6),
    lineage_completeness_score numeric(10,6),
    overall_readiness_score numeric(10,6),
    readiness_status varchar(40) NOT NULL,
    assessed_at timestamp with time zone NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_digital_twin_readiness_assessment_scope_i ON hidra_analytics_digital_twin_readiness_assessment (scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_digital_twin_readiness_assessment_topolog ON hidra_analytics_digital_twin_readiness_assessment (topology_snapshot_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_digital_twin_readiness_assessment_created ON hidra_analytics_digital_twin_readiness_assessment (created_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_kpi_band (
    id varchar(80) PRIMARY KEY,
    kpi_definition_id varchar(80) NOT NULL,
    band_code varchar(120) NOT NULL,
    label_ar varchar(160),
    label_fr varchar(160) NOT NULL,
    label_en varchar(160),
    min_value numeric(18,6),
    max_value numeric(18,6),
    severity_id varchar(80) NOT NULL,
    sort_order integer NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_band_kpi_definition_id ON hidra_analytics_kpi_band (kpi_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_band_severity_id ON hidra_analytics_kpi_band (severity_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_band_active ON hidra_analytics_kpi_band (active);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_band_created_at ON hidra_analytics_kpi_band (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_band_updated_at ON hidra_analytics_kpi_band (updated_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_kpi_definition (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    subject_area_id varchar(80) NOT NULL,
    primary_metric_definition_id varchar(80) NOT NULL,
    kpi_category_id varchar(80) NOT NULL,
    display_unit_id varchar(80),
    default_granularity varchar(80) NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_definition_code ON hidra_analytics_kpi_definition (code);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_definition_subject_area_id ON hidra_analytics_kpi_definition (subject_area_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_definition_primary_metric_definition_ ON hidra_analytics_kpi_definition (primary_metric_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_definition_kpi_category_id ON hidra_analytics_kpi_definition (kpi_category_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_definition_display_unit_id ON hidra_analytics_kpi_definition (display_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_definition_active ON hidra_analytics_kpi_definition (active);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_definition_created_at ON hidra_analytics_kpi_definition (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_definition_updated_at ON hidra_analytics_kpi_definition (updated_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_kpi_evaluation (
    id varchar(80) PRIMARY KEY,
    kpi_definition_id varchar(80) NOT NULL,
    metric_value_id varchar(80),
    scope_type varchar(80) NOT NULL,
    scope_id varchar(120),
    period_start timestamp with time zone NOT NULL,
    period_end timestamp with time zone NOT NULL,
    value numeric(18,6) NOT NULL,
    unit_id varchar(80),
    band_id varchar(80),
    trend_direction varchar(40),
    evaluated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_evaluation_kpi_definition_id ON hidra_analytics_kpi_evaluation (kpi_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_evaluation_metric_value_id ON hidra_analytics_kpi_evaluation (metric_value_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_evaluation_scope_id ON hidra_analytics_kpi_evaluation (scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_evaluation_unit_id ON hidra_analytics_kpi_evaluation (unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_kpi_evaluation_band_id ON hidra_analytics_kpi_evaluation (band_id);

CREATE TABLE IF NOT EXISTS hidra_analytics_metric_definition (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    subject_area_id varchar(80) NOT NULL,
    metric_type varchar(120) NOT NULL,
    formula_expression varchar(2000) NOT NULL,
    unit_id varchar(80) NOT NULL,
    aggregation_method varchar(80) NOT NULL,
    period_granularity varchar(80) NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_definition_code ON hidra_analytics_metric_definition (code);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_definition_subject_area_id ON hidra_analytics_metric_definition (subject_area_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_definition_unit_id ON hidra_analytics_metric_definition (unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_definition_active ON hidra_analytics_metric_definition (active);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_definition_created_at ON hidra_analytics_metric_definition (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_definition_updated_at ON hidra_analytics_metric_definition (updated_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_metric_definition_version (
    id varchar(80) PRIMARY KEY,
    metric_definition_id varchar(80) NOT NULL,
    version_number integer NOT NULL,
    formula_expression varchar(2000) NOT NULL,
    calculation_description varchar(1000),
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    created_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_definition_version_metric_definiti ON hidra_analytics_metric_definition_version (metric_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_definition_version_created_by_acto ON hidra_analytics_metric_definition_version (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_definition_version_created_at ON hidra_analytics_metric_definition_version (created_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_metric_evaluation_run (
    id varchar(80) PRIMARY KEY,
    metric_definition_version_id varchar(80) NOT NULL,
    run_status varchar(40) NOT NULL,
    period_start timestamp with time zone NOT NULL,
    period_end timestamp with time zone NOT NULL,
    scope_type varchar(80) NOT NULL,
    scope_id varchar(120),
    started_at timestamp with time zone NOT NULL,
    completed_at timestamp with time zone,
    records_read bigint,
    records_produced bigint,
    error_code varchar(120),
    error_message varchar(2000),
    correlation_id varchar(120),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_evaluation_run_metric_definition_v ON hidra_analytics_metric_evaluation_run (metric_definition_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_evaluation_run_scope_id ON hidra_analytics_metric_evaluation_run (scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_evaluation_run_correlation_id ON hidra_analytics_metric_evaluation_run (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_evaluation_run_created_at ON hidra_analytics_metric_evaluation_run (created_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_metric_value (
    id varchar(80) PRIMARY KEY,
    metric_evaluation_run_id varchar(80) NOT NULL,
    metric_definition_id varchar(80) NOT NULL,
    metric_definition_version_id varchar(80) NOT NULL,
    scope_type varchar(80) NOT NULL,
    scope_id varchar(120),
    period_start timestamp with time zone NOT NULL,
    period_end timestamp with time zone NOT NULL,
    value_numeric numeric(18,6),
    value_text varchar(2000),
    unit_id varchar(80),
    quality_status varchar(40) NOT NULL,
    calculated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_value_metric_evaluation_run_id ON hidra_analytics_metric_value (metric_evaluation_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_value_metric_definition_id ON hidra_analytics_metric_value (metric_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_value_metric_definition_version_id ON hidra_analytics_metric_value (metric_definition_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_value_scope_id ON hidra_analytics_metric_value (scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_metric_value_unit_id ON hidra_analytics_metric_value (unit_id);

CREATE TABLE IF NOT EXISTS hidra_analytics_trend_analysis (
    id varchar(80) PRIMARY KEY,
    subject_area_id varchar(80) NOT NULL,
    trend_type varchar(40) NOT NULL,
    scope_type varchar(80) NOT NULL,
    scope_id varchar(120),
    metric_definition_id varchar(80),
    period_start timestamp with time zone NOT NULL,
    period_end timestamp with time zone NOT NULL,
    trend_direction varchar(40) NOT NULL,
    confidence_score numeric(10,6),
    strength_score numeric(10,6),
    detected_at timestamp with time zone NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_trend_analysis_subject_area_id ON hidra_analytics_trend_analysis (subject_area_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_trend_analysis_scope_id ON hidra_analytics_trend_analysis (scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_trend_analysis_metric_definition_id ON hidra_analytics_trend_analysis (metric_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_trend_analysis_created_at ON hidra_analytics_trend_analysis (created_at);

CREATE TABLE IF NOT EXISTS hidra_analytics_trend_point (
    id varchar(80) PRIMARY KEY,
    trend_analysis_id varchar(80) NOT NULL,
    period_start timestamp with time zone NOT NULL,
    period_end timestamp with time zone NOT NULL,
    value numeric(18,6) NOT NULL,
    unit_id varchar(80),
    source_metric_value_id varchar(80),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_trend_point_trend_analysis_id ON hidra_analytics_trend_point (trend_analysis_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_trend_point_unit_id ON hidra_analytics_trend_point (unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_trend_point_source_metric_value_id ON hidra_analytics_trend_point (source_metric_value_id);
CREATE INDEX IF NOT EXISTS ix_hidra_analytics_trend_point_created_at ON hidra_analytics_trend_point (created_at);
