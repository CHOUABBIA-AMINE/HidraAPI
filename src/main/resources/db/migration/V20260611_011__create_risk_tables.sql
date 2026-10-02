-- HIDRA risk module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity
-- Module: risk

CREATE TABLE IF NOT EXISTS hidra_residual_risk_assessment (
    id varchar(80) PRIMARY KEY,
    risk_assessment_id varchar(80) NOT NULL,
    treatment_plan_id varchar(80),
    reassessment_date timestamp with time zone NOT NULL,
    residual_likelihood_id varchar(80) NOT NULL,
    residual_consequence_id varchar(80) NOT NULL,
    residual_score numeric(18,6) NOT NULL,
    residual_rating_id varchar(80) NOT NULL,
    residual_confidence_level_id varchar(80),
    residual_justification text,
    assessed_by_actor_id varchar(80) NOT NULL,
    approved_by_actor_id varchar(80),
    approved_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_residual_risk_assessment_risk_assessment_id ON hidra_residual_risk_assessment (risk_assessment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_residual_risk_assessment_treatment_plan_id ON hidra_residual_risk_assessment (treatment_plan_id);
CREATE INDEX IF NOT EXISTS ix_hidra_residual_risk_assessment_residual_likelihood_id ON hidra_residual_risk_assessment (residual_likelihood_id);
CREATE INDEX IF NOT EXISTS ix_hidra_residual_risk_assessment_residual_consequence_id ON hidra_residual_risk_assessment (residual_consequence_id);
CREATE INDEX IF NOT EXISTS ix_hidra_residual_risk_assessment_residual_rating_id ON hidra_residual_risk_assessment (residual_rating_id);
CREATE INDEX IF NOT EXISTS ix_hidra_residual_risk_assessment_residual_confidence_level_ ON hidra_residual_risk_assessment (residual_confidence_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_residual_risk_assessment_assessed_by_actor_id ON hidra_residual_risk_assessment (assessed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_residual_risk_assessment_approved_by_actor_id ON hidra_residual_risk_assessment (approved_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_residual_risk_assessment_created_at ON hidra_residual_risk_assessment (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_residual_risk_assessment_updated_at ON hidra_residual_risk_assessment (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_acceptance (
    id varchar(80) PRIMARY KEY,
    risk_assessment_id varchar(80) NOT NULL,
    acceptance_number varchar(80) NOT NULL,
    accepted_rating_id varchar(80) NOT NULL,
    accepted_score numeric(18,6),
    acceptance_reason_id varchar(80) NOT NULL,
    acceptance_justification text NOT NULL,
    accepted_by_actor_id varchar(80) NOT NULL,
    accepted_by_display_name_snapshot varchar(255),
    accepted_by_organization_unit_id varchar(80),
    accepted_by_organization_unit_name_snapshot varchar(500),
    accepted_at timestamp with time zone NOT NULL,
    valid_until timestamp with time zone,
    review_required boolean NOT NULL,
    status varchar(40) NOT NULL,
    workflow_reference_id varchar(80),
    audit_reference_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_acceptance_risk_assessment_id ON hidra_risk_acceptance (risk_assessment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_acceptance_accepted_rating_id ON hidra_risk_acceptance (accepted_rating_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_acceptance_acceptance_reason_id ON hidra_risk_acceptance (acceptance_reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_acceptance_accepted_by_actor_id ON hidra_risk_acceptance (accepted_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_acceptance_accepted_by_organization_unit_id ON hidra_risk_acceptance (accepted_by_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_acceptance_status ON hidra_risk_acceptance (status);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_acceptance_workflow_reference_id ON hidra_risk_acceptance (workflow_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_acceptance_audit_reference_id ON hidra_risk_acceptance (audit_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_acceptance_created_at ON hidra_risk_acceptance (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_acceptance_updated_at ON hidra_risk_acceptance (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_aggregation_snapshot (
    id varchar(80) PRIMARY KEY,
    scope_type varchar(160) NOT NULL,
    scope_id varchar(80) NOT NULL,
    scope_code_snapshot varchar(160),
    scope_label_snapshot varchar(500),
    snapshot_date timestamp with time zone NOT NULL,
    risk_matrix_id varchar(80),
    total_risk_count integer NOT NULL,
    critical_risk_count integer NOT NULL,
    high_risk_count integer NOT NULL,
    medium_risk_count integer NOT NULL,
    low_risk_count integer NOT NULL,
    average_risk_score numeric(18,6),
    maximum_risk_score numeric(18,6),
    open_treatment_count integer NOT NULL,
    overdue_treatment_count integer NOT NULL,
    accepted_risk_count integer NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_aggregation_snapshot_scope_id ON hidra_risk_aggregation_snapshot (scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_aggregation_snapshot_risk_matrix_id ON hidra_risk_aggregation_snapshot (risk_matrix_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_aggregation_snapshot_created_at ON hidra_risk_aggregation_snapshot (created_at);

CREATE TABLE IF NOT EXISTS hidra_risk_assessment (
    id varchar(80) PRIMARY KEY,
    risk_register_id varchar(80) NOT NULL,
    assessment_number varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    description text,
    assessment_type_id varchar(80) NOT NULL,
    methodology_id varchar(80) NOT NULL,
    scope_id varchar(80),
    risk_scenario_id varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    assessment_date timestamp with time zone NOT NULL,
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    assessed_by_actor_id varchar(80),
    assessed_by_display_name_snapshot varchar(255),
    reviewed_by_actor_id varchar(80),
    reviewed_by_display_name_snapshot varchar(255),
    approved_by_actor_id varchar(80),
    approved_by_display_name_snapshot varchar(255),
    approved_at timestamp with time zone,
    inherent_likelihood_id varchar(80),
    inherent_consequence_id varchar(80),
    inherent_score numeric(18,6),
    inherent_rating_id varchar(80),
    residual_likelihood_id varchar(80),
    residual_consequence_id varchar(80),
    residual_score numeric(18,6),
    residual_rating_id varchar(80),
    confidence_level_id varchar(80),
    uncertainty_note text,
    workflow_reference_id varchar(80),
    audit_reference_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_risk_register_id ON hidra_risk_assessment (risk_register_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_assessment_type_id ON hidra_risk_assessment (assessment_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_methodology_id ON hidra_risk_assessment (methodology_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_scope_id ON hidra_risk_assessment (scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_risk_scenario_id ON hidra_risk_assessment (risk_scenario_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_status ON hidra_risk_assessment (status);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_assessed_by_actor_id ON hidra_risk_assessment (assessed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_reviewed_by_actor_id ON hidra_risk_assessment (reviewed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_approved_by_actor_id ON hidra_risk_assessment (approved_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_inherent_likelihood_id ON hidra_risk_assessment (inherent_likelihood_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_inherent_consequence_id ON hidra_risk_assessment (inherent_consequence_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_inherent_rating_id ON hidra_risk_assessment (inherent_rating_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_residual_likelihood_id ON hidra_risk_assessment (residual_likelihood_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_residual_consequence_id ON hidra_risk_assessment (residual_consequence_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_residual_rating_id ON hidra_risk_assessment (residual_rating_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_confidence_level_id ON hidra_risk_assessment (confidence_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_workflow_reference_id ON hidra_risk_assessment (workflow_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_audit_reference_id ON hidra_risk_assessment (audit_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_created_at ON hidra_risk_assessment (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_updated_at ON hidra_risk_assessment (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_assessment_scope (
    id varchar(80) PRIMARY KEY,
    risk_assessment_id varchar(80) NOT NULL,
    scope_type varchar(160) NOT NULL,
    scope_id varchar(80) NOT NULL,
    scope_code_snapshot varchar(160),
    scope_label_snapshot varchar(500),
    topology_snapshot_id varchar(80),
    operational_period_start timestamp with time zone,
    operational_period_end timestamp with time zone,
    included boolean NOT NULL,
    scope_note text,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_scope_risk_assessment_id ON hidra_risk_assessment_scope (risk_assessment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_scope_scope_id ON hidra_risk_assessment_scope (scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_scope_topology_snapshot_id ON hidra_risk_assessment_scope (topology_snapshot_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_assessment_scope_created_at ON hidra_risk_assessment_scope (created_at);

CREATE TABLE IF NOT EXISTS hidra_risk_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_catalog_entry_code ON hidra_risk_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_catalog_entry_active ON hidra_risk_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_catalog_entry_created_at ON hidra_risk_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_catalog_entry_updated_at ON hidra_risk_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_catalog_translation_catalog_entry_id ON hidra_risk_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_catalog_translation_created_at ON hidra_risk_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_catalog_translation_updated_at ON hidra_risk_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_consequence (
    id varchar(80) PRIMARY KEY,
    risk_assessment_id varchar(80) NOT NULL,
    category_id varchar(80) NOT NULL,
    consequence_level_id varchar(80) NOT NULL,
    description text,
    people_impact_level_id varchar(80),
    environment_impact_level_id varchar(80),
    production_impact_level_id varchar(80),
    asset_impact_level_id varchar(80),
    financial_impact_level_id varchar(80),
    reputation_impact_level_id varchar(80),
    compliance_impact_level_id varchar(80),
    estimated_cost numeric(18,6),
    currency_code varchar(3),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_consequence_risk_assessment_id ON hidra_risk_consequence (risk_assessment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_consequence_category_id ON hidra_risk_consequence (category_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_consequence_consequence_level_id ON hidra_risk_consequence (consequence_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_consequence_people_impact_level_id ON hidra_risk_consequence (people_impact_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_consequence_environment_impact_level_id ON hidra_risk_consequence (environment_impact_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_consequence_production_impact_level_id ON hidra_risk_consequence (production_impact_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_consequence_asset_impact_level_id ON hidra_risk_consequence (asset_impact_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_consequence_financial_impact_level_id ON hidra_risk_consequence (financial_impact_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_consequence_reputation_impact_level_id ON hidra_risk_consequence (reputation_impact_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_consequence_compliance_impact_level_id ON hidra_risk_consequence (compliance_impact_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_consequence_created_at ON hidra_risk_consequence (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_consequence_updated_at ON hidra_risk_consequence (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_control (
    id varchar(80) PRIMARY KEY,
    risk_assessment_id varchar(80) NOT NULL,
    control_code varchar(80) NOT NULL,
    control_name varchar(255) NOT NULL,
    control_type_id varchar(80) NOT NULL,
    control_owner_organization_unit_id varchar(80),
    control_owner_name_snapshot varchar(500),
    effectiveness_level_id varchar(80),
    effectiveness_justification text,
    verified boolean NOT NULL,
    verified_by_actor_id varchar(80),
    verified_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_control_risk_assessment_id ON hidra_risk_control (risk_assessment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_control_control_type_id ON hidra_risk_control (control_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_control_control_owner_organization_unit_id ON hidra_risk_control (control_owner_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_control_effectiveness_level_id ON hidra_risk_control (effectiveness_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_control_verified_by_actor_id ON hidra_risk_control (verified_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_control_created_at ON hidra_risk_control (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_control_updated_at ON hidra_risk_control (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_evidence_link (
    id varchar(80) PRIMARY KEY,
    risk_assessment_id varchar(80) NOT NULL,
    evidence_module varchar(80) NOT NULL,
    evidence_type varchar(160) NOT NULL,
    evidence_id varchar(80) NOT NULL,
    evidence_code_snapshot varchar(160),
    evidence_label_snapshot varchar(500),
    evidence_timestamp timestamp with time zone,
    evidence_hash varchar(160),
    evidence_summary text,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_evidence_link_risk_assessment_id ON hidra_risk_evidence_link (risk_assessment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_evidence_link_evidence_id ON hidra_risk_evidence_link (evidence_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_evidence_link_created_at ON hidra_risk_evidence_link (created_at);

CREATE TABLE IF NOT EXISTS hidra_risk_exposure (
    id varchar(80) PRIMARY KEY,
    risk_assessment_id varchar(80) NOT NULL,
    exposure_type_id varchar(80) NOT NULL,
    exposed_object_type varchar(160) NOT NULL,
    exposed_object_id varchar(80) NOT NULL,
    exposed_object_code_snapshot varchar(160),
    exposed_object_label_snapshot varchar(500),
    exposure_start timestamp with time zone,
    exposure_end timestamp with time zone,
    exposure_magnitude numeric(18,6),
    exposure_unit_id varchar(80),
    population_exposure numeric(18,6),
    environmental_sensitivity_id varchar(80),
    production_criticality_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_exposure_risk_assessment_id ON hidra_risk_exposure (risk_assessment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_exposure_exposure_type_id ON hidra_risk_exposure (exposure_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_exposure_exposed_object_id ON hidra_risk_exposure (exposed_object_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_exposure_exposure_unit_id ON hidra_risk_exposure (exposure_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_exposure_environmental_sensitivity_id ON hidra_risk_exposure (environmental_sensitivity_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_exposure_production_criticality_id ON hidra_risk_exposure (production_criticality_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_exposure_created_at ON hidra_risk_exposure (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_exposure_updated_at ON hidra_risk_exposure (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_likelihood (
    id varchar(80) PRIMARY KEY,
    risk_assessment_id varchar(80) NOT NULL,
    likelihood_level_id varchar(80) NOT NULL,
    probability_value numeric(10,6),
    frequency_estimate numeric(18,6),
    frequency_unit_id varchar(80),
    likelihood_basis_id varchar(80),
    confidence_level_id varchar(80),
    evidence_summary text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_likelihood_risk_assessment_id ON hidra_risk_likelihood (risk_assessment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_likelihood_likelihood_level_id ON hidra_risk_likelihood (likelihood_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_likelihood_frequency_unit_id ON hidra_risk_likelihood (frequency_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_likelihood_likelihood_basis_id ON hidra_risk_likelihood (likelihood_basis_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_likelihood_confidence_level_id ON hidra_risk_likelihood (confidence_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_likelihood_created_at ON hidra_risk_likelihood (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_likelihood_updated_at ON hidra_risk_likelihood (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_matrix_cell (
    id varchar(80) PRIMARY KEY,
    risk_matrix_id varchar(80) NOT NULL,
    likelihood_level_id varchar(80) NOT NULL,
    consequence_level_id varchar(80) NOT NULL,
    score_value numeric(18,6) NOT NULL,
    rating_id varchar(80) NOT NULL,
    color_code varchar(40),
    requires_treatment boolean NOT NULL,
    requires_approval boolean NOT NULL,
    requires_executive_acceptance boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_matrix_cell_risk_matrix_id ON hidra_risk_matrix_cell (risk_matrix_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_matrix_cell_likelihood_level_id ON hidra_risk_matrix_cell (likelihood_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_matrix_cell_consequence_level_id ON hidra_risk_matrix_cell (consequence_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_matrix_cell_rating_id ON hidra_risk_matrix_cell (rating_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_matrix_cell_created_at ON hidra_risk_matrix_cell (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_matrix_cell_updated_at ON hidra_risk_matrix_cell (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_matrix (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    description text,
    matrix_type_id varchar(80) NOT NULL,
    version varchar(40) NOT NULL,
    status varchar(40) NOT NULL,
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    created_by_actor_id varchar(80),
    approved_by_actor_id varchar(80),
    approved_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_matrix_code ON hidra_risk_matrix (code);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_matrix_matrix_type_id ON hidra_risk_matrix (matrix_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_matrix_status ON hidra_risk_matrix (status);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_matrix_created_by_actor_id ON hidra_risk_matrix (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_matrix_approved_by_actor_id ON hidra_risk_matrix (approved_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_matrix_created_at ON hidra_risk_matrix (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_matrix_updated_at ON hidra_risk_matrix (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_mitigation_measure (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    description text,
    mitigation_type_id varchar(80) NOT NULL,
    applicable_threat_type_id varchar(80),
    applicable_asset_type_id varchar(80),
    expected_effect_on_likelihood numeric(10,6),
    expected_effect_on_consequence numeric(10,6),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_mitigation_measure_code ON hidra_risk_mitigation_measure (code);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_mitigation_measure_mitigation_type_id ON hidra_risk_mitigation_measure (mitigation_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_mitigation_measure_applicable_threat_type_id ON hidra_risk_mitigation_measure (applicable_threat_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_mitigation_measure_applicable_asset_type_id ON hidra_risk_mitigation_measure (applicable_asset_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_mitigation_measure_active ON hidra_risk_mitigation_measure (active);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_mitigation_measure_created_at ON hidra_risk_mitigation_measure (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_mitigation_measure_updated_at ON hidra_risk_mitigation_measure (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_rating (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    description text,
    severity_order integer NOT NULL,
    requires_treatment boolean NOT NULL,
    requires_approval boolean NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_rating_code ON hidra_risk_rating (code);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_rating_active ON hidra_risk_rating (active);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_rating_created_at ON hidra_risk_rating (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_rating_updated_at ON hidra_risk_rating (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_register (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    description text,
    register_type_id varchar(80) NOT NULL,
    owner_organization_unit_id varchar(80),
    owner_organization_unit_name_snapshot varchar(500),
    scope_type varchar(160) NOT NULL,
    scope_id varchar(80) NOT NULL,
    scope_code_snapshot varchar(160),
    scope_label_snapshot varchar(500),
    status varchar(40) NOT NULL,
    review_frequency_id varchar(80),
    effective_from timestamp with time zone,
    effective_to timestamp with time zone,
    created_by_actor_id varchar(80),
    created_by_display_name_snapshot varchar(255),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_register_code ON hidra_risk_register (code);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_register_register_type_id ON hidra_risk_register (register_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_register_owner_organization_unit_id ON hidra_risk_register (owner_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_register_scope_id ON hidra_risk_register (scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_register_status ON hidra_risk_register (status);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_register_review_frequency_id ON hidra_risk_register (review_frequency_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_register_created_by_actor_id ON hidra_risk_register (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_register_created_at ON hidra_risk_register (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_register_updated_at ON hidra_risk_register (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_review (
    id varchar(80) PRIMARY KEY,
    risk_assessment_id varchar(80) NOT NULL,
    review_type_id varchar(80) NOT NULL,
    review_status varchar(40) NOT NULL,
    review_due_date timestamp with time zone NOT NULL,
    reviewed_at timestamp with time zone,
    reviewed_by_actor_id varchar(80),
    reviewed_by_display_name_snapshot varchar(255),
    review_finding text,
    rating_changed boolean NOT NULL,
    previous_rating_id varchar(80),
    new_rating_id varchar(80),
    next_review_due_date timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_review_risk_assessment_id ON hidra_risk_review (risk_assessment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_review_review_type_id ON hidra_risk_review (review_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_review_reviewed_by_actor_id ON hidra_risk_review (reviewed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_review_previous_rating_id ON hidra_risk_review (previous_rating_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_review_new_rating_id ON hidra_risk_review (new_rating_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_review_created_at ON hidra_risk_review (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_review_updated_at ON hidra_risk_review (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_scenario (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    description text,
    scenario_type_id varchar(80) NOT NULL,
    threat_id varchar(80),
    primary_consequence_category_id varchar(80),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_scenario_code ON hidra_risk_scenario (code);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_scenario_scenario_type_id ON hidra_risk_scenario (scenario_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_scenario_threat_id ON hidra_risk_scenario (threat_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_scenario_primary_consequence_category_id ON hidra_risk_scenario (primary_consequence_category_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_scenario_active ON hidra_risk_scenario (active);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_scenario_created_at ON hidra_risk_scenario (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_scenario_updated_at ON hidra_risk_scenario (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_score (
    id varchar(80) PRIMARY KEY,
    risk_assessment_id varchar(80) NOT NULL,
    score_type varchar(40) NOT NULL,
    risk_matrix_id varchar(80) NOT NULL,
    likelihood_level_id varchar(80) NOT NULL,
    consequence_level_id varchar(80) NOT NULL,
    score_value numeric(18,6) NOT NULL,
    rating_id varchar(80) NOT NULL,
    rating_label_snapshot varchar(160),
    calculated_at timestamp with time zone NOT NULL,
    calculation_method varchar(160),
    explanation text,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_score_risk_assessment_id ON hidra_risk_score (risk_assessment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_score_risk_matrix_id ON hidra_risk_score (risk_matrix_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_score_likelihood_level_id ON hidra_risk_score (likelihood_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_score_consequence_level_id ON hidra_risk_score (consequence_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_score_rating_id ON hidra_risk_score (rating_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_score_created_at ON hidra_risk_score (created_at);

CREATE TABLE IF NOT EXISTS hidra_risk_source (
    id varchar(80) PRIMARY KEY,
    risk_assessment_id varchar(80) NOT NULL,
    source_module varchar(80) NOT NULL,
    source_type varchar(160) NOT NULL,
    source_id varchar(80) NOT NULL,
    source_code_snapshot varchar(160),
    source_label_snapshot varchar(500),
    source_observed_at timestamp with time zone,
    source_severity_snapshot varchar(160),
    source_confidence_snapshot numeric(10,6),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_source_risk_assessment_id ON hidra_risk_source (risk_assessment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_source_source_id ON hidra_risk_source (source_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_source_created_at ON hidra_risk_source (created_at);

CREATE TABLE IF NOT EXISTS hidra_risk_threat (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    description text,
    threat_category_id varchar(80) NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_threat_code ON hidra_risk_threat (code);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_threat_threat_category_id ON hidra_risk_threat (threat_category_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_threat_active ON hidra_risk_threat (active);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_threat_created_at ON hidra_risk_threat (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_threat_updated_at ON hidra_risk_threat (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_treatment_action (
    id varchar(80) PRIMARY KEY,
    risk_treatment_plan_id varchar(80) NOT NULL,
    action_code varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    description text,
    action_type_id varchar(80) NOT NULL,
    owner_actor_id varchar(80),
    owner_display_name_snapshot varchar(255),
    owner_organization_unit_id varchar(80),
    owner_organization_unit_name_snapshot varchar(500),
    target_date timestamp with time zone,
    completed_at timestamp with time zone,
    verification_required boolean NOT NULL,
    verified_by_actor_id varchar(80),
    verified_at timestamp with time zone,
    status varchar(40) NOT NULL,
    linked_work_order_id varchar(80),
    linked_workflow_task_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_action_risk_treatment_plan_id ON hidra_risk_treatment_action (risk_treatment_plan_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_action_action_type_id ON hidra_risk_treatment_action (action_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_action_owner_actor_id ON hidra_risk_treatment_action (owner_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_action_owner_organization_unit_id ON hidra_risk_treatment_action (owner_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_action_verified_by_actor_id ON hidra_risk_treatment_action (verified_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_action_status ON hidra_risk_treatment_action (status);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_action_linked_work_order_id ON hidra_risk_treatment_action (linked_work_order_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_action_linked_workflow_task_id ON hidra_risk_treatment_action (linked_workflow_task_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_action_created_at ON hidra_risk_treatment_action (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_action_updated_at ON hidra_risk_treatment_action (updated_at);

CREATE TABLE IF NOT EXISTS hidra_risk_treatment_plan (
    id varchar(80) PRIMARY KEY,
    risk_assessment_id varchar(80) NOT NULL,
    treatment_strategy_id varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    description text,
    owner_organization_unit_id varchar(80),
    owner_organization_unit_name_snapshot varchar(500),
    owner_actor_id varchar(80),
    owner_display_name_snapshot varchar(255),
    status varchar(40) NOT NULL,
    target_residual_rating_id varchar(80),
    target_completion_date timestamp with time zone,
    workflow_reference_id varchar(80),
    audit_reference_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_plan_risk_assessment_id ON hidra_risk_treatment_plan (risk_assessment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_plan_treatment_strategy_id ON hidra_risk_treatment_plan (treatment_strategy_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_plan_owner_organization_unit_id ON hidra_risk_treatment_plan (owner_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_plan_owner_actor_id ON hidra_risk_treatment_plan (owner_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_plan_status ON hidra_risk_treatment_plan (status);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_plan_target_residual_rating_id ON hidra_risk_treatment_plan (target_residual_rating_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_plan_workflow_reference_id ON hidra_risk_treatment_plan (workflow_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_plan_audit_reference_id ON hidra_risk_treatment_plan (audit_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_plan_created_at ON hidra_risk_treatment_plan (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_risk_treatment_plan_updated_at ON hidra_risk_treatment_plan (updated_at);
