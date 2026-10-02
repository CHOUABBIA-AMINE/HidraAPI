-- HIDRA hse module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity
-- Module: hse

CREATE TABLE IF NOT EXISTS hidra_hse_compliance_assessment (
    id varchar(80) PRIMARY KEY,
    obligation_id varchar(80) NOT NULL,
    assessment_number varchar(80) NOT NULL,
    compliance_status varchar(40) NOT NULL,
    assessment_summary text,
    assessed_by_actor_id varchar(80),
    assessed_at timestamp with time zone NOT NULL,
    evidence_reference_id varchar(80),
    linked_hse_case_id varchar(80),
    next_assessment_due_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_compliance_assessment_obligation_id ON hidra_hse_compliance_assessment (obligation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_compliance_assessment_assessed_by_actor_id ON hidra_hse_compliance_assessment (assessed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_compliance_assessment_evidence_reference_id ON hidra_hse_compliance_assessment (evidence_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_compliance_assessment_linked_hse_case_id ON hidra_hse_compliance_assessment (linked_hse_case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_compliance_assessment_created_at ON hidra_hse_compliance_assessment (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_compliance_assessment_updated_at ON hidra_hse_compliance_assessment (updated_at);

CREATE TABLE IF NOT EXISTS hidra_hse_compliance_obligation (
    id varchar(80) PRIMARY KEY,
    obligation_number varchar(80) NOT NULL,
    obligation_type_id varchar(80) NOT NULL,
    regulatory_reference varchar(255),
    title varchar(255) NOT NULL,
    description text,
    jurisdiction_id varchar(80),
    responsible_organization_unit_id varchar(80),
    effective_from timestamp with time zone,
    effective_to timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_compliance_obligation_obligation_type_id ON hidra_hse_compliance_obligation (obligation_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_compliance_obligation_jurisdiction_id ON hidra_hse_compliance_obligation (jurisdiction_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_compliance_obligation_responsible_organization_ ON hidra_hse_compliance_obligation (responsible_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_compliance_obligation_status ON hidra_hse_compliance_obligation (status);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_compliance_obligation_created_at ON hidra_hse_compliance_obligation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_compliance_obligation_updated_at ON hidra_hse_compliance_obligation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_hse_emergency_drill (
    id varchar(80) PRIMARY KEY,
    drill_number varchar(80) NOT NULL,
    drill_type_id varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    target_module varchar(80),
    target_type_code varchar(80),
    target_id varchar(80),
    planned_at timestamp with time zone,
    executed_at timestamp with time zone,
    status varchar(40) NOT NULL,
    participants_count integer,
    evaluation_summary text,
    linked_hse_case_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_emergency_drill_drill_type_id ON hidra_hse_emergency_drill (drill_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_emergency_drill_target_id ON hidra_hse_emergency_drill (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_emergency_drill_status ON hidra_hse_emergency_drill (status);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_emergency_drill_linked_hse_case_id ON hidra_hse_emergency_drill (linked_hse_case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_emergency_drill_created_at ON hidra_hse_emergency_drill (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_emergency_drill_updated_at ON hidra_hse_emergency_drill (updated_at);

CREATE TABLE IF NOT EXISTS hidra_hse_environmental_event (
    id varchar(80) PRIMARY KEY,
    event_number varchar(80) NOT NULL,
    event_type varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    description text,
    substance_id varchar(80),
    quantity numeric(18,6),
    quantity_unit_id varchar(80),
    medium_affected_id varchar(80),
    target_module varchar(80),
    target_type_code varchar(80),
    target_id varchar(80),
    severity varchar(40),
    status varchar(40) NOT NULL,
    occurred_at timestamp with time zone NOT NULL,
    linked_hse_case_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_environmental_event_substance_id ON hidra_hse_environmental_event (substance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_environmental_event_quantity_unit_id ON hidra_hse_environmental_event (quantity_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_environmental_event_medium_affected_id ON hidra_hse_environmental_event (medium_affected_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_environmental_event_target_id ON hidra_hse_environmental_event (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_environmental_event_status ON hidra_hse_environmental_event (status);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_environmental_event_linked_hse_case_id ON hidra_hse_environmental_event (linked_hse_case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_environmental_event_created_at ON hidra_hse_environmental_event (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_environmental_event_updated_at ON hidra_hse_environmental_event (updated_at);

CREATE TABLE IF NOT EXISTS hidra_hse_hazard_report (
    id varchar(80) PRIMARY KEY,
    report_number varchar(80) NOT NULL,
    hazard_type_id varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    description text,
    target_module varchar(80),
    target_type_code varchar(80),
    target_id varchar(80),
    target_code_snapshot varchar(160),
    initial_severity varchar(40),
    status varchar(40) NOT NULL,
    reported_by_actor_id varchar(80),
    reported_at timestamp with time zone NOT NULL,
    linked_hse_case_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_hazard_report_hazard_type_id ON hidra_hse_hazard_report (hazard_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_hazard_report_target_id ON hidra_hse_hazard_report (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_hazard_report_status ON hidra_hse_hazard_report (status);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_hazard_report_reported_by_actor_id ON hidra_hse_hazard_report (reported_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_hazard_report_linked_hse_case_id ON hidra_hse_hazard_report (linked_hse_case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_hazard_report_created_at ON hidra_hse_hazard_report (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_hazard_report_updated_at ON hidra_hse_hazard_report (updated_at);

CREATE TABLE IF NOT EXISTS hidra_hse_case_evidence_link (
    id varchar(80) PRIMARY KEY,
    hse_case_id varchar(80) NOT NULL,
    evidence_type varchar(80) NOT NULL,
    evidence_reference_id varchar(80) NOT NULL,
    evidence_code_snapshot varchar(160),
    evidence_label_snapshot varchar(500),
    evidence_summary text,
    evidence_timestamp timestamp with time zone,
    attached_by_actor_id varchar(80),
    attached_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_evidence_link_hse_case_id ON hidra_hse_case_evidence_link (hse_case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_evidence_link_evidence_reference_id ON hidra_hse_case_evidence_link (evidence_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_evidence_link_attached_by_actor_id ON hidra_hse_case_evidence_link (attached_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_hse_case (
    id varchar(80) PRIMARY KEY,
    case_number varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    description text,
    case_type_id varchar(80) NOT NULL,
    severity_id varchar(80) NOT NULL,
    priority_id varchar(80),
    status varchar(40) NOT NULL,
    source_type varchar(80) NOT NULL,
    incident_reference_id varchar(80),
    incident_code_snapshot varchar(160),
    incident_title_snapshot varchar(255),
    target_module varchar(80),
    target_type_code varchar(80),
    target_id varchar(80),
    target_code_snapshot varchar(160),
    target_label_snapshot varchar(500),
    occurred_at timestamp with time zone,
    reported_at timestamp with time zone NOT NULL,
    reported_by_actor_id varchar(80),
    reported_by_display_name_snapshot varchar(255),
    responsible_organization_unit_id varchar(80),
    responsible_organization_unit_name_snapshot varchar(500),
    workflow_instance_id varchar(80),
    audit_reference_id varchar(80),
    controlled_at timestamp with time zone,
    resolved_at timestamp with time zone,
    closed_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_case_type_id ON hidra_hse_case (case_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_severity_id ON hidra_hse_case (severity_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_priority_id ON hidra_hse_case (priority_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_status ON hidra_hse_case (status);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_incident_reference_id ON hidra_hse_case (incident_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_target_id ON hidra_hse_case (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_reported_by_actor_id ON hidra_hse_case (reported_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_responsible_organization_unit_id ON hidra_hse_case (responsible_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_workflow_instance_id ON hidra_hse_case (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_audit_reference_id ON hidra_hse_case (audit_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_created_at ON hidra_hse_case (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_updated_at ON hidra_hse_case (updated_at);

CREATE TABLE IF NOT EXISTS hidra_hse_case_status_history (
    id varchar(80) PRIMARY KEY,
    hse_case_id varchar(80) NOT NULL,
    old_status varchar(40),
    new_status varchar(40) NOT NULL,
    reason_id varchar(80),
    reason_text text,
    changed_by_actor_id varchar(80),
    changed_by_display_name_snapshot varchar(255),
    changed_at timestamp with time zone NOT NULL,
    correlation_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_status_history_hse_case_id ON hidra_hse_case_status_history (hse_case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_status_history_reason_id ON hidra_hse_case_status_history (reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_status_history_changed_by_actor_id ON hidra_hse_case_status_history (changed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_case_status_history_correlation_id ON hidra_hse_case_status_history (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_hse_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_catalog_entry_code ON hidra_hse_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_catalog_entry_active ON hidra_hse_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_catalog_entry_created_at ON hidra_hse_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_catalog_entry_updated_at ON hidra_hse_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_hse_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_catalog_translation_catalog_entry_id ON hidra_hse_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_catalog_translation_created_at ON hidra_hse_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_catalog_translation_updated_at ON hidra_hse_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_hse_closure (
    id varchar(80) PRIMARY KEY,
    hse_case_id varchar(80) NOT NULL,
    closure_summary text NOT NULL,
    impact_assessed boolean NOT NULL,
    capa_completed boolean NOT NULL,
    evidence_reviewed boolean NOT NULL,
    regulatory_reviewed boolean NOT NULL,
    closed_by_actor_id varchar(80) NOT NULL,
    closed_by_display_name_snapshot varchar(255),
    closed_at timestamp with time zone NOT NULL,
    workflow_instance_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_closure_hse_case_id ON hidra_hse_closure (hse_case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_closure_closed_by_actor_id ON hidra_hse_closure (closed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_closure_workflow_instance_id ON hidra_hse_closure (workflow_instance_id);

CREATE TABLE IF NOT EXISTS hidra_hse_capa (
    id varchar(80) PRIMARY KEY,
    hse_case_id varchar(80) NOT NULL,
    action_number varchar(80) NOT NULL,
    action_type_id varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    description text,
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
    workflow_task_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_capa_hse_case_id ON hidra_hse_capa (hse_case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_capa_action_type_id ON hidra_hse_capa (action_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_capa_owner_actor_id ON hidra_hse_capa (owner_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_capa_owner_organization_unit_id ON hidra_hse_capa (owner_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_capa_verified_by_actor_id ON hidra_hse_capa (verified_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_capa_status ON hidra_hse_capa (status);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_capa_linked_work_order_id ON hidra_hse_capa (linked_work_order_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_capa_workflow_task_id ON hidra_hse_capa (workflow_task_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_capa_created_at ON hidra_hse_capa (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_capa_updated_at ON hidra_hse_capa (updated_at);

CREATE TABLE IF NOT EXISTS hidra_hse_impact_assessment (
    id varchar(80) PRIMARY KEY,
    hse_case_id varchar(80) NOT NULL,
    impact_domain varchar(40) NOT NULL,
    impact_type_id varchar(80) NOT NULL,
    severity varchar(40) NOT NULL,
    description text,
    people_affected_count integer,
    injury_count integer,
    spill_volume numeric(18,6),
    spill_volume_unit_id varchar(80),
    estimated_cost numeric(18,6),
    currency_code varchar(3),
    regulatory_reference_id varchar(80),
    assessed_by_actor_id varchar(80),
    assessed_at timestamp with time zone NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_impact_assessment_hse_case_id ON hidra_hse_impact_assessment (hse_case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_impact_assessment_impact_type_id ON hidra_hse_impact_assessment (impact_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_impact_assessment_spill_volume_unit_id ON hidra_hse_impact_assessment (spill_volume_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_impact_assessment_regulatory_reference_id ON hidra_hse_impact_assessment (regulatory_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_impact_assessment_assessed_by_actor_id ON hidra_hse_impact_assessment (assessed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_impact_assessment_created_at ON hidra_hse_impact_assessment (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_impact_assessment_updated_at ON hidra_hse_impact_assessment (updated_at);

CREATE TABLE IF NOT EXISTS hidra_hse_inspection (
    id varchar(80) PRIMARY KEY,
    inspection_number varchar(80) NOT NULL,
    inspection_type_id varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    target_module varchar(80),
    target_type_code varchar(80),
    target_id varchar(80),
    inspector_actor_id varchar(80),
    planned_at timestamp with time zone,
    started_at timestamp with time zone,
    completed_at timestamp with time zone,
    status varchar(40) NOT NULL,
    finding_summary text,
    linked_hse_case_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_inspection_inspection_type_id ON hidra_hse_inspection (inspection_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_inspection_target_id ON hidra_hse_inspection (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_inspection_inspector_actor_id ON hidra_hse_inspection (inspector_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_inspection_status ON hidra_hse_inspection (status);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_inspection_linked_hse_case_id ON hidra_hse_inspection (linked_hse_case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_inspection_created_at ON hidra_hse_inspection (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_inspection_updated_at ON hidra_hse_inspection (updated_at);

CREATE TABLE IF NOT EXISTS hidra_hse_near_miss_report (
    id varchar(80) PRIMARY KEY,
    report_number varchar(80) NOT NULL,
    near_miss_type_id varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    description text,
    potential_consequence_id varchar(80),
    potential_severity varchar(40),
    target_module varchar(80),
    target_type_code varchar(80),
    target_id varchar(80),
    status varchar(40) NOT NULL,
    reported_by_actor_id varchar(80),
    reported_at timestamp with time zone NOT NULL,
    linked_hse_case_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_near_miss_report_near_miss_type_id ON hidra_hse_near_miss_report (near_miss_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_near_miss_report_potential_consequence_id ON hidra_hse_near_miss_report (potential_consequence_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_near_miss_report_target_id ON hidra_hse_near_miss_report (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_near_miss_report_status ON hidra_hse_near_miss_report (status);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_near_miss_report_reported_by_actor_id ON hidra_hse_near_miss_report (reported_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_near_miss_report_linked_hse_case_id ON hidra_hse_near_miss_report (linked_hse_case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_near_miss_report_created_at ON hidra_hse_near_miss_report (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_near_miss_report_updated_at ON hidra_hse_near_miss_report (updated_at);

CREATE TABLE IF NOT EXISTS hidra_hse_permit_to_work (
    id varchar(80) PRIMARY KEY,
    permit_number varchar(80) NOT NULL,
    permit_type_id varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    description text,
    target_module varchar(80),
    target_type_code varchar(80),
    target_id varchar(80),
    requested_by_actor_id varchar(80),
    approved_by_actor_id varchar(80),
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone NOT NULL,
    status varchar(40) NOT NULL,
    workflow_instance_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_permit_to_work_permit_type_id ON hidra_hse_permit_to_work (permit_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_permit_to_work_target_id ON hidra_hse_permit_to_work (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_permit_to_work_requested_by_actor_id ON hidra_hse_permit_to_work (requested_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_permit_to_work_approved_by_actor_id ON hidra_hse_permit_to_work (approved_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_permit_to_work_status ON hidra_hse_permit_to_work (status);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_permit_to_work_workflow_instance_id ON hidra_hse_permit_to_work (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_permit_to_work_created_at ON hidra_hse_permit_to_work (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_permit_to_work_updated_at ON hidra_hse_permit_to_work (updated_at);

CREATE TABLE IF NOT EXISTS hidra_hse_safety_observation (
    id varchar(80) PRIMARY KEY,
    observation_number varchar(80) NOT NULL,
    observation_type varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    description text,
    target_module varchar(80),
    target_type_code varchar(80),
    target_id varchar(80),
    observed_by_actor_id varchar(80),
    observed_at timestamp with time zone NOT NULL,
    status varchar(40) NOT NULL,
    linked_hse_case_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_safety_observation_target_id ON hidra_hse_safety_observation (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_safety_observation_observed_by_actor_id ON hidra_hse_safety_observation (observed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_safety_observation_status ON hidra_hse_safety_observation (status);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_safety_observation_linked_hse_case_id ON hidra_hse_safety_observation (linked_hse_case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_safety_observation_created_at ON hidra_hse_safety_observation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_hse_safety_observation_updated_at ON hidra_hse_safety_observation (updated_at);
