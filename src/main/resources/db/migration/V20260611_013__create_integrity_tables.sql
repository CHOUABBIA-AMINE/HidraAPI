-- HIDRA integrity module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity
-- Module: integrity

CREATE TABLE IF NOT EXISTS hidra_integrity_cathodic_protection_measurement (
    id varchar(80) PRIMARY KEY,
    survey_id varchar(80) NOT NULL,
    kilometer_point numeric(14,4),
    pipe_to_soil_potential numeric(18,6),
    potential_unit_id varchar(80),
    current_density numeric(18,6),
    current_unit_id varchar(80),
    measurement_method_id varchar(80),
    measured_at timestamp with time zone NOT NULL,
    notes text
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_cathodic_protection_measurement_survey_id ON hidra_integrity_cathodic_protection_measurement (survey_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_cathodic_protection_measurement_potential ON hidra_integrity_cathodic_protection_measurement (potential_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_cathodic_protection_measurement_current_u ON hidra_integrity_cathodic_protection_measurement (current_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_cathodic_protection_measurement_measureme ON hidra_integrity_cathodic_protection_measurement (measurement_method_id);

CREATE TABLE IF NOT EXISTS hidra_integrity_cathodic_protection_survey (
    id varchar(80) PRIMARY KEY,
    survey_number varchar(80) NOT NULL,
    survey_type_id varchar(80) NOT NULL,
    topology_asset_type_code varchar(80) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code_snapshot varchar(160),
    status varchar(40) NOT NULL,
    survey_start_at timestamp with time zone,
    survey_end_at timestamp with time zone,
    performed_by_party_id varchar(80),
    summary text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_cathodic_protection_survey_survey_type_id ON hidra_integrity_cathodic_protection_survey (survey_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_cathodic_protection_survey_topology_asset ON hidra_integrity_cathodic_protection_survey (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_cathodic_protection_survey_status ON hidra_integrity_cathodic_protection_survey (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_cathodic_protection_survey_performed_by_p ON hidra_integrity_cathodic_protection_survey (performed_by_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_cathodic_protection_survey_created_at ON hidra_integrity_cathodic_protection_survey (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_cathodic_protection_survey_updated_at ON hidra_integrity_cathodic_protection_survey (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integrity_coating_condition_observation (
    id varchar(80) PRIMARY KEY,
    inspection_run_id varchar(80),
    topology_asset_type_code varchar(80) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    coating_condition_id varchar(80) NOT NULL,
    kilometer_point numeric(14,4),
    description text,
    severity varchar(40),
    observed_at timestamp with time zone NOT NULL,
    observed_by_actor_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_coating_condition_observation_inspection_ ON hidra_integrity_coating_condition_observation (inspection_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_coating_condition_observation_topology_as ON hidra_integrity_coating_condition_observation (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_coating_condition_observation_coating_con ON hidra_integrity_coating_condition_observation (coating_condition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_coating_condition_observation_observed_by ON hidra_integrity_coating_condition_observation (observed_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_integrity_corrosion_feature (
    id varchar(80) PRIMARY KEY,
    defect_id varchar(80),
    corrosion_type_id varchar(80) NOT NULL,
    topology_asset_type_code varchar(80) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    kilometer_point numeric(14,4),
    length numeric(18,6),
    width numeric(18,6),
    depth numeric(18,6),
    dimension_unit_id varchar(80),
    severity varchar(40),
    observed_at timestamp with time zone NOT NULL,
    notes text
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_corrosion_feature_defect_id ON hidra_integrity_corrosion_feature (defect_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_corrosion_feature_corrosion_type_id ON hidra_integrity_corrosion_feature (corrosion_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_corrosion_feature_topology_asset_id ON hidra_integrity_corrosion_feature (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_corrosion_feature_dimension_unit_id ON hidra_integrity_corrosion_feature (dimension_unit_id);

CREATE TABLE IF NOT EXISTS hidra_integrity_defect_assessment (
    id varchar(80) PRIMARY KEY,
    defect_id varchar(80) NOT NULL,
    assessment_method_id varchar(80) NOT NULL,
    assessment_number varchar(80) NOT NULL,
    assessed_severity varchar(40),
    failure_pressure numeric(18,6),
    pressure_unit_id varchar(80),
    safety_factor numeric(18,6),
    fit_for_service boolean NOT NULL,
    assessment_summary text,
    assessed_by_actor_id varchar(80),
    assessed_at timestamp with time zone NOT NULL,
    approved_by_actor_id varchar(80),
    approved_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_defect_assessment_defect_id ON hidra_integrity_defect_assessment (defect_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_defect_assessment_assessment_method_id ON hidra_integrity_defect_assessment (assessment_method_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_defect_assessment_pressure_unit_id ON hidra_integrity_defect_assessment (pressure_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_defect_assessment_assessed_by_actor_id ON hidra_integrity_defect_assessment (assessed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_defect_assessment_approved_by_actor_id ON hidra_integrity_defect_assessment (approved_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_integrity_defect_measurement (
    id varchar(80) PRIMARY KEY,
    defect_id varchar(80) NOT NULL,
    measurement_type_id varchar(80) NOT NULL,
    measurement_value numeric(18,6) NOT NULL,
    unit_id varchar(80) NOT NULL,
    measurement_method_id varchar(80),
    measured_by_actor_id varchar(80),
    measured_at timestamp with time zone NOT NULL,
    notes text
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_defect_measurement_defect_id ON hidra_integrity_defect_measurement (defect_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_defect_measurement_measurement_type_id ON hidra_integrity_defect_measurement (measurement_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_defect_measurement_unit_id ON hidra_integrity_defect_measurement (unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_defect_measurement_measurement_method_id ON hidra_integrity_defect_measurement (measurement_method_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_defect_measurement_measured_by_actor_id ON hidra_integrity_defect_measurement (measured_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_integrity_inspection_campaign (
    id varchar(80) PRIMARY KEY,
    program_id varchar(80),
    campaign_number varchar(80) NOT NULL,
    name varchar(255) NOT NULL,
    inspection_type_id varchar(80) NOT NULL,
    contractor_party_id varchar(80),
    contractor_name_snapshot varchar(255),
    status varchar(40) NOT NULL,
    planned_start_at timestamp with time zone,
    planned_end_at timestamp with time zone,
    actual_start_at timestamp with time zone,
    actual_end_at timestamp with time zone,
    created_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_campaign_program_id ON hidra_integrity_inspection_campaign (program_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_campaign_inspection_type_id ON hidra_integrity_inspection_campaign (inspection_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_campaign_contractor_party_id ON hidra_integrity_inspection_campaign (contractor_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_campaign_status ON hidra_integrity_inspection_campaign (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_campaign_created_by_actor_id ON hidra_integrity_inspection_campaign (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_campaign_created_at ON hidra_integrity_inspection_campaign (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_campaign_updated_at ON hidra_integrity_inspection_campaign (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integrity_inspection_finding (
    id varchar(80) PRIMARY KEY,
    inspection_run_id varchar(80) NOT NULL,
    finding_number varchar(80) NOT NULL,
    finding_type_id varchar(80) NOT NULL,
    severity varchar(40) NOT NULL,
    description text,
    kilometer_point numeric(14,4),
    topology_asset_type_code varchar(80),
    topology_asset_id varchar(80),
    topology_asset_code_snapshot varchar(160),
    linked_defect_id varchar(80),
    observed_at timestamp with time zone NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_finding_inspection_run_id ON hidra_integrity_inspection_finding (inspection_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_finding_finding_type_id ON hidra_integrity_inspection_finding (finding_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_finding_topology_asset_id ON hidra_integrity_inspection_finding (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_finding_linked_defect_id ON hidra_integrity_inspection_finding (linked_defect_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_finding_created_at ON hidra_integrity_inspection_finding (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_finding_updated_at ON hidra_integrity_inspection_finding (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integrity_inspection_run (
    id varchar(80) PRIMARY KEY,
    campaign_id varchar(80) NOT NULL,
    run_number varchar(80) NOT NULL,
    topology_asset_type_code varchar(80) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code_snapshot varchar(160),
    status varchar(40) NOT NULL,
    started_at timestamp with time zone,
    completed_at timestamp with time zone,
    tool_reference varchar(160),
    operator_actor_id varchar(80),
    summary text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_run_campaign_id ON hidra_integrity_inspection_run (campaign_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_run_topology_asset_id ON hidra_integrity_inspection_run (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_run_status ON hidra_integrity_inspection_run (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_run_operator_actor_id ON hidra_integrity_inspection_run (operator_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_run_created_at ON hidra_integrity_inspection_run (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_inspection_run_updated_at ON hidra_integrity_inspection_run (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integrity_assessment (
    id varchar(80) PRIMARY KEY,
    program_id varchar(80),
    assessment_number varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    description text,
    assessment_type_id varchar(80) NOT NULL,
    methodology_id varchar(80),
    status varchar(40) NOT NULL,
    assessment_date timestamp with time zone NOT NULL,
    assessed_by_actor_id varchar(80),
    reviewed_by_actor_id varchar(80),
    approved_by_actor_id varchar(80),
    approved_at timestamp with time zone,
    workflow_instance_id varchar(80),
    audit_reference_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_assessment_program_id ON hidra_integrity_assessment (program_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_assessment_assessment_type_id ON hidra_integrity_assessment (assessment_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_assessment_methodology_id ON hidra_integrity_assessment (methodology_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_assessment_status ON hidra_integrity_assessment (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_assessment_assessed_by_actor_id ON hidra_integrity_assessment (assessed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_assessment_reviewed_by_actor_id ON hidra_integrity_assessment (reviewed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_assessment_approved_by_actor_id ON hidra_integrity_assessment (approved_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_assessment_workflow_instance_id ON hidra_integrity_assessment (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_assessment_audit_reference_id ON hidra_integrity_assessment (audit_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_assessment_created_at ON hidra_integrity_assessment (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_assessment_updated_at ON hidra_integrity_assessment (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integrity_assessment_scope (
    id varchar(80) PRIMARY KEY,
    assessment_id varchar(80) NOT NULL,
    topology_asset_type_code varchar(80) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code_snapshot varchar(160),
    topology_asset_name_snapshot varchar(500),
    scope_role_id varchar(80),
    topology_snapshot_id varchar(80),
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_assessment_scope_assessment_id ON hidra_integrity_assessment_scope (assessment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_assessment_scope_topology_asset_id ON hidra_integrity_assessment_scope (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_assessment_scope_scope_role_id ON hidra_integrity_assessment_scope (scope_role_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_assessment_scope_topology_snapshot_id ON hidra_integrity_assessment_scope (topology_snapshot_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_assessment_scope_created_at ON hidra_integrity_assessment_scope (created_at);

CREATE TABLE IF NOT EXISTS hidra_integrity_case (
    id varchar(80) PRIMARY KEY,
    case_number varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    description text,
    case_type_id varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    severity_id varchar(80),
    topology_asset_type_code varchar(80) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code_snapshot varchar(160),
    primary_defect_id varchar(80),
    source_incident_id varchar(80),
    source_hse_case_id varchar(80),
    responsible_organization_unit_id varchar(80),
    workflow_instance_id varchar(80),
    opened_at timestamp with time zone NOT NULL,
    closed_at timestamp with time zone,
    opened_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_case_case_type_id ON hidra_integrity_case (case_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_case_status ON hidra_integrity_case (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_case_severity_id ON hidra_integrity_case (severity_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_case_topology_asset_id ON hidra_integrity_case (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_case_primary_defect_id ON hidra_integrity_case (primary_defect_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_case_source_incident_id ON hidra_integrity_case (source_incident_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_case_source_hse_case_id ON hidra_integrity_case (source_hse_case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_case_responsible_organization_unit_id ON hidra_integrity_case (responsible_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_case_workflow_instance_id ON hidra_integrity_case (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_case_opened_by_actor_id ON hidra_integrity_case (opened_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_case_created_at ON hidra_integrity_case (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_case_updated_at ON hidra_integrity_case (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integrity_case_status_history (
    id varchar(80) PRIMARY KEY,
    integrity_case_id varchar(80) NOT NULL,
    old_status varchar(40),
    new_status varchar(40) NOT NULL,
    reason_id varchar(80),
    reason_text text,
    changed_by_actor_id varchar(80),
    changed_at timestamp with time zone NOT NULL,
    correlation_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_case_status_history_integrity_case_id ON hidra_integrity_case_status_history (integrity_case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_case_status_history_reason_id ON hidra_integrity_case_status_history (reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_case_status_history_changed_by_actor_id ON hidra_integrity_case_status_history (changed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_case_status_history_correlation_id ON hidra_integrity_case_status_history (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_integrity_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_catalog_entry_code ON hidra_integrity_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_catalog_entry_active ON hidra_integrity_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_catalog_entry_created_at ON hidra_integrity_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_catalog_entry_updated_at ON hidra_integrity_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integrity_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_catalog_translation_catalog_entry_id ON hidra_integrity_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_catalog_translation_created_at ON hidra_integrity_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_catalog_translation_updated_at ON hidra_integrity_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integrity_evidence_link (
    id varchar(80) PRIMARY KEY,
    target_type varchar(80) NOT NULL,
    target_id varchar(80) NOT NULL,
    evidence_type varchar(80) NOT NULL,
    evidence_reference_id varchar(80) NOT NULL,
    evidence_code_snapshot varchar(160),
    evidence_label_snapshot varchar(500),
    description text,
    evidence_timestamp timestamp with time zone,
    attached_by_actor_id varchar(80),
    attached_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_evidence_link_target_id ON hidra_integrity_evidence_link (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_evidence_link_evidence_reference_id ON hidra_integrity_evidence_link (evidence_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_evidence_link_attached_by_actor_id ON hidra_integrity_evidence_link (attached_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_integrity_program (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    description text,
    program_type_id varchar(80) NOT NULL,
    owner_organization_unit_id varchar(80),
    owner_organization_unit_name_snapshot varchar(500),
    status varchar(40) NOT NULL,
    planned_start_at timestamp with time zone,
    planned_end_at timestamp with time zone,
    actual_start_at timestamp with time zone,
    actual_end_at timestamp with time zone,
    created_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_program_code ON hidra_integrity_program (code);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_program_program_type_id ON hidra_integrity_program (program_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_program_owner_organization_unit_id ON hidra_integrity_program (owner_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_program_status ON hidra_integrity_program (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_program_created_by_actor_id ON hidra_integrity_program (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_program_created_at ON hidra_integrity_program (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_program_updated_at ON hidra_integrity_program (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integrity_recommendation (
    id varchar(80) PRIMARY KEY,
    recommendation_number varchar(80) NOT NULL,
    source_assessment_id varchar(80),
    source_defect_id varchar(80),
    recommendation_type_id varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    description text,
    status varchar(40) NOT NULL,
    priority_id varchar(80),
    target_module varchar(80),
    target_reference_id varchar(80),
    created_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    due_at timestamp with time zone,
    closed_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_recommendation_source_assessment_id ON hidra_integrity_recommendation (source_assessment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_recommendation_source_defect_id ON hidra_integrity_recommendation (source_defect_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_recommendation_recommendation_type_id ON hidra_integrity_recommendation (recommendation_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_recommendation_status ON hidra_integrity_recommendation (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_recommendation_priority_id ON hidra_integrity_recommendation (priority_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_recommendation_target_reference_id ON hidra_integrity_recommendation (target_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_recommendation_created_by_actor_id ON hidra_integrity_recommendation (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_recommendation_created_at ON hidra_integrity_recommendation (created_at);

CREATE TABLE IF NOT EXISTS hidra_integrity_threat (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    threat_type varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    description text,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_threat_code ON hidra_integrity_threat (code);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_threat_active ON hidra_integrity_threat (active);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_threat_created_at ON hidra_integrity_threat (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_threat_updated_at ON hidra_integrity_threat (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integrity_pipeline_defect (
    id varchar(80) PRIMARY KEY,
    defect_number varchar(80) NOT NULL,
    defect_type_id varchar(80) NOT NULL,
    threat_type varchar(80),
    status varchar(40) NOT NULL,
    severity varchar(40),
    topology_asset_type_code varchar(80) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code_snapshot varchar(160),
    kilometer_point numeric(14,4),
    latitude numeric(10,7),
    longitude numeric(10,7),
    description text,
    detected_at timestamp with time zone NOT NULL,
    closed_at timestamp with time zone,
    source_finding_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_pipeline_defect_defect_type_id ON hidra_integrity_pipeline_defect (defect_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_pipeline_defect_status ON hidra_integrity_pipeline_defect (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_pipeline_defect_topology_asset_id ON hidra_integrity_pipeline_defect (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_pipeline_defect_source_finding_id ON hidra_integrity_pipeline_defect (source_finding_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_pipeline_defect_created_at ON hidra_integrity_pipeline_defect (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_pipeline_defect_updated_at ON hidra_integrity_pipeline_defect (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integrity_remaining_life_estimate (
    id varchar(80) PRIMARY KEY,
    defect_id varchar(80),
    assessment_id varchar(80),
    method_id varchar(80) NOT NULL,
    remaining_life_value numeric(18,6) NOT NULL,
    remaining_life_unit_id varchar(80) NOT NULL,
    corrosion_rate numeric(18,6),
    corrosion_rate_unit_id varchar(80),
    estimated_at timestamp with time zone NOT NULL,
    estimated_by_actor_id varchar(80),
    confidence_level_id varchar(80),
    notes text
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_remaining_life_estimate_defect_id ON hidra_integrity_remaining_life_estimate (defect_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_remaining_life_estimate_assessment_id ON hidra_integrity_remaining_life_estimate (assessment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_remaining_life_estimate_method_id ON hidra_integrity_remaining_life_estimate (method_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_remaining_life_estimate_remaining_life_un ON hidra_integrity_remaining_life_estimate (remaining_life_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_remaining_life_estimate_corrosion_rate_un ON hidra_integrity_remaining_life_estimate (corrosion_rate_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_remaining_life_estimate_estimated_by_acto ON hidra_integrity_remaining_life_estimate (estimated_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_remaining_life_estimate_confidence_level_ ON hidra_integrity_remaining_life_estimate (confidence_level_id);

CREATE TABLE IF NOT EXISTS hidra_integrity_wall_thickness_measurement (
    id varchar(80) PRIMARY KEY,
    inspection_run_id varchar(80),
    topology_asset_type_code varchar(80) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code_snapshot varchar(160),
    kilometer_point numeric(14,4),
    nominal_thickness numeric(18,6),
    measured_thickness numeric(18,6) NOT NULL,
    thickness_unit_id varchar(80) NOT NULL,
    metal_loss_percent numeric(10,4),
    measured_at timestamp with time zone NOT NULL,
    measurement_method_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_wall_thickness_measurement_inspection_run ON hidra_integrity_wall_thickness_measurement (inspection_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_wall_thickness_measurement_topology_asset ON hidra_integrity_wall_thickness_measurement (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_wall_thickness_measurement_thickness_unit ON hidra_integrity_wall_thickness_measurement (thickness_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integrity_wall_thickness_measurement_measurement_me ON hidra_integrity_wall_thickness_measurement (measurement_method_id);
