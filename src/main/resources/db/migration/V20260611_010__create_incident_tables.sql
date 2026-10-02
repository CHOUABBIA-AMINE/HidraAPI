-- HIDRA incident module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity
-- Module: incident

CREATE TABLE IF NOT EXISTS hidra_incident_assignment (
    id varchar(80) PRIMARY KEY,
    incident_id varchar(80) NOT NULL,
    assignment_type_id varchar(80) NOT NULL,
    assigned_organization_unit_id varchar(80),
    assigned_organization_unit_name_snapshot varchar(500),
    assigned_actor_id varchar(80),
    assigned_actor_name_snapshot varchar(255),
    assigned_by_actor_id varchar(80) NOT NULL,
    assigned_at timestamp with time zone NOT NULL,
    accepted_at timestamp with time zone,
    released_at timestamp with time zone,
    release_reason text,
    primary_assignment boolean NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_assignment_incident_id ON hidra_incident_assignment (incident_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_assignment_assignment_type_id ON hidra_incident_assignment (assignment_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_assignment_assigned_organization_unit_id ON hidra_incident_assignment (assigned_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_assignment_assigned_actor_id ON hidra_incident_assignment (assigned_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_assignment_assigned_by_actor_id ON hidra_incident_assignment (assigned_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_incident_attachment_reference (
    id varchar(80) PRIMARY KEY,
    incident_id varchar(80) NOT NULL,
    document_reference_id varchar(80) NOT NULL,
    document_type_id varchar(80),
    filename_snapshot varchar(255),
    content_type varchar(160),
    description text,
    uploaded_by_actor_id varchar(80) NOT NULL,
    uploaded_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_attachment_reference_incident_id ON hidra_incident_attachment_reference (incident_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_attachment_reference_document_reference_id ON hidra_incident_attachment_reference (document_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_attachment_reference_document_type_id ON hidra_incident_attachment_reference (document_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_attachment_reference_uploaded_by_actor_id ON hidra_incident_attachment_reference (uploaded_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_incident_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_catalog_entry_code ON hidra_incident_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_catalog_entry_active ON hidra_incident_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_catalog_entry_created_at ON hidra_incident_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_catalog_entry_updated_at ON hidra_incident_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_incident_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_catalog_translation_catalog_entry_id ON hidra_incident_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_catalog_translation_created_at ON hidra_incident_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_catalog_translation_updated_at ON hidra_incident_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_incident_closure (
    id varchar(80) PRIMARY KEY,
    incident_id varchar(80) NOT NULL,
    closure_summary text NOT NULL,
    resolution_verified boolean NOT NULL,
    evidence_reviewed boolean NOT NULL,
    root_cause_reviewed boolean NOT NULL,
    follow_up_actions_created boolean NOT NULL,
    closed_by_actor_id varchar(80) NOT NULL,
    closed_by_actor_name_snapshot varchar(255),
    closed_at timestamp with time zone NOT NULL,
    workflow_instance_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_closure_incident_id ON hidra_incident_closure (incident_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_closure_closed_by_actor_id ON hidra_incident_closure (closed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_closure_workflow_instance_id ON hidra_incident_closure (workflow_instance_id);

CREATE TABLE IF NOT EXISTS hidra_incident_escalation (
    id varchar(80) PRIMARY KEY,
    incident_id varchar(80) NOT NULL,
    from_level integer NOT NULL,
    to_level integer NOT NULL,
    reason_id varchar(80) NOT NULL,
    reason_comment text,
    escalated_to_organization_unit_id varchar(80),
    escalated_to_actor_id varchar(80),
    escalated_by_actor_id varchar(80) NOT NULL,
    escalated_at timestamp with time zone NOT NULL,
    acknowledged_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_escalation_incident_id ON hidra_incident_escalation (incident_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_escalation_reason_id ON hidra_incident_escalation (reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_escalation_escalated_to_organization_unit_ ON hidra_incident_escalation (escalated_to_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_escalation_escalated_to_actor_id ON hidra_incident_escalation (escalated_to_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_escalation_escalated_by_actor_id ON hidra_incident_escalation (escalated_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_incident_evidence_link (
    id varchar(80) PRIMARY KEY,
    incident_id varchar(80) NOT NULL,
    evidence_type varchar(80) NOT NULL,
    evidence_reference_id varchar(80) NOT NULL,
    evidence_reference_code varchar(160),
    evidence_title varchar(255),
    evidence_summary text,
    evidence_timestamp timestamp with time zone,
    attached_by_actor_id varchar(80) NOT NULL,
    attached_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_evidence_link_incident_id ON hidra_incident_evidence_link (incident_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_evidence_link_evidence_reference_id ON hidra_incident_evidence_link (evidence_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_evidence_link_attached_by_actor_id ON hidra_incident_evidence_link (attached_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_incident_impact_assessment (
    id varchar(80) PRIMARY KEY,
    incident_id varchar(80) NOT NULL,
    impact_type_id varchar(80) NOT NULL,
    impact_level_id varchar(80) NOT NULL,
    estimated boolean NOT NULL,
    description text,
    affected_topology_asset_type_code varchar(80),
    affected_topology_asset_id varchar(80),
    affected_organization_unit_id varchar(80),
    estimated_volume_loss numeric(18,6),
    estimated_volume_unit_id varchar(80),
    estimated_duration_minutes integer,
    assessed_by_actor_id varchar(80) NOT NULL,
    assessed_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_impact_assessment_incident_id ON hidra_incident_impact_assessment (incident_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_impact_assessment_impact_type_id ON hidra_incident_impact_assessment (impact_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_impact_assessment_impact_level_id ON hidra_incident_impact_assessment (impact_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_impact_assessment_affected_topology_asset_ ON hidra_incident_impact_assessment (affected_topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_impact_assessment_affected_organization_un ON hidra_incident_impact_assessment (affected_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_impact_assessment_estimated_volume_unit_id ON hidra_incident_impact_assessment (estimated_volume_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_impact_assessment_assessed_by_actor_id ON hidra_incident_impact_assessment (assessed_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_incident (
    id varchar(80) PRIMARY KEY,
    incident_number varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    description text,
    classification_id varchar(80) NOT NULL,
    severity_id varchar(80) NOT NULL,
    priority_id varchar(80),
    status varchar(40) NOT NULL,
    source_type varchar(80) NOT NULL,
    source_reference_id varchar(80),
    source_reference_code varchar(160),
    detected_at timestamp with time zone NOT NULL,
    reported_at timestamp with time zone NOT NULL,
    occurred_at timestamp with time zone,
    topology_asset_type_code varchar(80),
    topology_asset_id varchar(80),
    topology_asset_code varchar(160),
    topology_asset_name_snapshot varchar(500),
    location_description_ar varchar(500),
    location_description_lt varchar(500),
    latitude numeric(10,7),
    longitude numeric(10,7),
    responsible_organization_unit_id varchar(80),
    responsible_organization_unit_code varchar(160),
    responsible_organization_unit_name_snapshot varchar(500),
    responsible_actor_id varchar(80),
    responsible_actor_name_snapshot varchar(255),
    workflow_instance_id varchar(80),
    current_escalation_level integer NOT NULL,
    contained_at timestamp with time zone,
    resolved_at timestamp with time zone,
    closed_at timestamp with time zone,
    cancelled_at timestamp with time zone,
    created_by_actor_id varchar(80) NOT NULL,
    created_by_actor_name_snapshot varchar(255),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_classification_id ON hidra_incident (classification_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_severity_id ON hidra_incident (severity_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_priority_id ON hidra_incident (priority_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_status ON hidra_incident (status);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_source_reference_id ON hidra_incident (source_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_topology_asset_id ON hidra_incident (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_responsible_organization_unit_id ON hidra_incident (responsible_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_responsible_actor_id ON hidra_incident (responsible_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_workflow_instance_id ON hidra_incident (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_created_by_actor_id ON hidra_incident (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_created_at ON hidra_incident (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_updated_at ON hidra_incident (updated_at);

CREATE TABLE IF NOT EXISTS hidra_incident_related_incident (
    id varchar(80) PRIMARY KEY,
    incident_id varchar(80) NOT NULL,
    related_incident_id varchar(80) NOT NULL,
    relationship_type_id varchar(80) NOT NULL,
    comment text,
    created_by_actor_id varchar(80) NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_related_incident_incident_id ON hidra_incident_related_incident (incident_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_related_incident_related_incident_id ON hidra_incident_related_incident (related_incident_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_related_incident_relationship_type_id ON hidra_incident_related_incident (relationship_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_related_incident_created_by_actor_id ON hidra_incident_related_incident (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_related_incident_created_at ON hidra_incident_related_incident (created_at);

CREATE TABLE IF NOT EXISTS hidra_incident_resolution (
    id varchar(80) PRIMARY KEY,
    incident_id varchar(80) NOT NULL,
    resolution_type_id varchar(80) NOT NULL,
    resolution_summary text NOT NULL,
    corrective_action_required boolean NOT NULL,
    preventive_action_required boolean NOT NULL,
    residual_risk_level_id varchar(80),
    resolved_by_actor_id varchar(80) NOT NULL,
    resolved_at timestamp with time zone NOT NULL,
    workflow_instance_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_resolution_incident_id ON hidra_incident_resolution (incident_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_resolution_resolution_type_id ON hidra_incident_resolution (resolution_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_resolution_residual_risk_level_id ON hidra_incident_resolution (residual_risk_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_resolution_resolved_by_actor_id ON hidra_incident_resolution (resolved_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_resolution_workflow_instance_id ON hidra_incident_resolution (workflow_instance_id);

CREATE TABLE IF NOT EXISTS hidra_incident_response_action (
    id varchar(80) PRIMARY KEY,
    incident_id varchar(80) NOT NULL,
    action_type_id varchar(80) NOT NULL,
    action_status varchar(40) NOT NULL,
    description text NOT NULL,
    target_type varchar(80),
    target_reference_id varchar(80),
    target_reference_code varchar(160),
    planned_start_at timestamp with time zone,
    planned_end_at timestamp with time zone,
    started_at timestamp with time zone,
    completed_at timestamp with time zone,
    performed_by_actor_id varchar(80),
    performed_by_actor_name_snapshot varchar(255),
    organization_unit_id varchar(80),
    result_summary text,
    failure_reason text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_response_action_incident_id ON hidra_incident_response_action (incident_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_response_action_action_type_id ON hidra_incident_response_action (action_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_response_action_target_reference_id ON hidra_incident_response_action (target_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_response_action_performed_by_actor_id ON hidra_incident_response_action (performed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_response_action_organization_unit_id ON hidra_incident_response_action (organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_response_action_created_at ON hidra_incident_response_action (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_response_action_updated_at ON hidra_incident_response_action (updated_at);

CREATE TABLE IF NOT EXISTS hidra_incident_root_cause_analysis (
    id varchar(80) PRIMARY KEY,
    incident_id varchar(80) NOT NULL,
    root_cause_category_id varchar(80) NOT NULL,
    root_cause_code_id varchar(80),
    method_id varchar(80),
    summary text NOT NULL,
    analysis_details text,
    contributing_factors text,
    confidence_level_id varchar(80),
    performed_by_actor_id varchar(80) NOT NULL,
    performed_at timestamp with time zone NOT NULL,
    approved_by_actor_id varchar(80),
    approved_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_root_cause_analysis_incident_id ON hidra_incident_root_cause_analysis (incident_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_root_cause_analysis_root_cause_category_id ON hidra_incident_root_cause_analysis (root_cause_category_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_root_cause_analysis_root_cause_code_id ON hidra_incident_root_cause_analysis (root_cause_code_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_root_cause_analysis_method_id ON hidra_incident_root_cause_analysis (method_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_root_cause_analysis_confidence_level_id ON hidra_incident_root_cause_analysis (confidence_level_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_root_cause_analysis_performed_by_actor_id ON hidra_incident_root_cause_analysis (performed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_root_cause_analysis_approved_by_actor_id ON hidra_incident_root_cause_analysis (approved_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_incident_timeline_entry (
    id varchar(80) PRIMARY KEY,
    incident_id varchar(80) NOT NULL,
    entry_type_id varchar(80) NOT NULL,
    status_before varchar(40),
    status_after varchar(40),
    title varchar(255) NOT NULL,
    description text,
    actor_id varchar(80),
    actor_name_snapshot varchar(255),
    organization_unit_id varchar(80),
    organization_unit_name_snapshot varchar(500),
    occurred_at timestamp with time zone NOT NULL,
    recorded_at timestamp with time zone NOT NULL,
    correlation_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_timeline_entry_incident_id ON hidra_incident_timeline_entry (incident_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_timeline_entry_entry_type_id ON hidra_incident_timeline_entry (entry_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_timeline_entry_actor_id ON hidra_incident_timeline_entry (actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_timeline_entry_organization_unit_id ON hidra_incident_timeline_entry (organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_incident_timeline_entry_correlation_id ON hidra_incident_timeline_entry (correlation_id);
