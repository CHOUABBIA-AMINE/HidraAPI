-- HIDRA documents module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity
-- Module: documents

CREATE TABLE IF NOT EXISTS hidra_documents_access_grant (
    id varchar(80) PRIMARY KEY,
    document_id varchar(80) NOT NULL,
    document_version_id varchar(80),
    principal_type varchar(40) NOT NULL,
    principal_id varchar(120) NOT NULL,
    principal_label_snapshot varchar(160),
    access_level varchar(40) NOT NULL,
    granted_by_actor_id varchar(80) NOT NULL,
    granted_at timestamp with time zone NOT NULL,
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    revoked_at timestamp with time zone,
    active boolean NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_access_grant_document_id ON hidra_documents_access_grant (document_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_access_grant_document_version_id ON hidra_documents_access_grant (document_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_access_grant_principal_id ON hidra_documents_access_grant (principal_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_access_grant_granted_by_actor_id ON hidra_documents_access_grant (granted_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_access_grant_active ON hidra_documents_access_grant (active);

CREATE TABLE IF NOT EXISTS hidra_documents_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_catalog_entry_code ON hidra_documents_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_catalog_entry_active ON hidra_documents_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_catalog_entry_created_at ON hidra_documents_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_catalog_entry_updated_at ON hidra_documents_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_documents_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description varchar(500),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_catalog_translation_catalog_entry_id ON hidra_documents_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_catalog_translation_created_at ON hidra_documents_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_catalog_translation_updated_at ON hidra_documents_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_documents_external_reference (
    id varchar(80) PRIMARY KEY,
    document_id varchar(80) NOT NULL,
    document_version_id varchar(80),
    external_system_id varchar(80) NOT NULL,
    external_object_type varchar(80) NOT NULL,
    external_object_id varchar(160) NOT NULL,
    external_object_code varchar(160),
    external_url_reference varchar(1000),
    sync_status varchar(40) NOT NULL,
    last_synced_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_external_reference_document_id ON hidra_documents_external_reference (document_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_external_reference_document_version_id ON hidra_documents_external_reference (document_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_external_reference_external_system_id ON hidra_documents_external_reference (external_system_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_external_reference_external_object_id ON hidra_documents_external_reference (external_object_id);

CREATE TABLE IF NOT EXISTS hidra_documents_extraction_record (
    id varchar(80) PRIMARY KEY,
    document_version_id varchar(80) NOT NULL,
    extraction_type varchar(40) NOT NULL,
    extraction_status varchar(40) NOT NULL,
    extracted_text_ref varchar(500),
    extracted_metadata_json jsonb,
    confidence_score numeric(10,6),
    language_detected varchar(10),
    started_at timestamp with time zone,
    completed_at timestamp with time zone,
    failure_reason varchar(1000)
);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_extraction_record_document_version_id ON hidra_documents_extraction_record (document_version_id);

CREATE TABLE IF NOT EXISTS hidra_documents_document (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    title_ar varchar(240),
    title_fr varchar(240) NOT NULL,
    title_en varchar(240),
    document_type_id varchar(80) NOT NULL,
    document_category_id varchar(80),
    classification_id varchar(80) NOT NULL,
    confidentiality_level integer NOT NULL,
    status varchar(40) NOT NULL,
    current_version_id varchar(80),
    owner_module varchar(80),
    owner_target_type_code varchar(80),
    owner_target_id varchar(120),
    owner_target_code_snapshot varchar(120),
    owner_target_label_snapshot varchar(240),
    created_by_actor_id varchar(80) NOT NULL,
    created_by_display_name_snapshot varchar(160) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    archived_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_document_code ON hidra_documents_document (code);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_document_document_type_id ON hidra_documents_document (document_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_document_document_category_id ON hidra_documents_document (document_category_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_document_classification_id ON hidra_documents_document (classification_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_document_status ON hidra_documents_document (status);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_document_current_version_id ON hidra_documents_document (current_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_document_owner_target_id ON hidra_documents_document (owner_target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_document_created_by_actor_id ON hidra_documents_document (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_document_created_at ON hidra_documents_document (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_document_updated_at ON hidra_documents_document (updated_at);

CREATE TABLE IF NOT EXISTS hidra_documents_retention_record (
    id varchar(80) PRIMARY KEY,
    document_id varchar(80) NOT NULL,
    retention_policy_id varchar(80) NOT NULL,
    retention_class_id varchar(80) NOT NULL,
    retain_until date,
    legal_hold boolean NOT NULL,
    legal_hold_reason varchar(500),
    archived_at timestamp with time zone,
    archive_storage_object_id varchar(80),
    disposal_allowed_from date,
    disposed_at timestamp with time zone,
    disposed_by_actor_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_retention_record_document_id ON hidra_documents_retention_record (document_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_retention_record_retention_policy_id ON hidra_documents_retention_record (retention_policy_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_retention_record_retention_class_id ON hidra_documents_retention_record (retention_class_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_retention_record_archive_storage_object_i ON hidra_documents_retention_record (archive_storage_object_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_retention_record_disposed_by_actor_id ON hidra_documents_retention_record (disposed_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_documents_review_reference (
    id varchar(80) PRIMARY KEY,
    document_id varchar(80) NOT NULL,
    document_version_id varchar(80),
    workflow_instance_id varchar(80) NOT NULL,
    review_type_id varchar(80) NOT NULL,
    review_status varchar(40) NOT NULL,
    requested_by_actor_id varchar(80) NOT NULL,
    requested_at timestamp with time zone NOT NULL,
    completed_at timestamp with time zone,
    decision_reason_id varchar(80),
    decision_comment varchar(2000)
);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_review_reference_document_id ON hidra_documents_review_reference (document_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_review_reference_document_version_id ON hidra_documents_review_reference (document_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_review_reference_workflow_instance_id ON hidra_documents_review_reference (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_review_reference_review_type_id ON hidra_documents_review_reference (review_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_review_reference_requested_by_actor_id ON hidra_documents_review_reference (requested_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_review_reference_decision_reason_id ON hidra_documents_review_reference (decision_reason_id);

CREATE TABLE IF NOT EXISTS hidra_documents_storage_object (
    id varchar(80) PRIMARY KEY,
    storage_provider_id varchar(80) NOT NULL,
    bucket_or_container varchar(160),
    object_key varchar(500) NOT NULL,
    object_uri varchar(1000),
    encrypted boolean NOT NULL,
    encryption_key_reference varchar(160),
    content_length_bytes bigint NOT NULL,
    content_type varchar(120) NOT NULL,
    checksum_algorithm varchar(40) NOT NULL,
    checksum_value varchar(160) NOT NULL,
    storage_status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    verified_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_storage_object_storage_provider_id ON hidra_documents_storage_object (storage_provider_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_storage_object_created_at ON hidra_documents_storage_object (created_at);

CREATE TABLE IF NOT EXISTS hidra_documents_target_link (
    id varchar(80) PRIMARY KEY,
    document_id varchar(80) NOT NULL,
    document_version_id varchar(80),
    target_module varchar(80) NOT NULL,
    target_type_code varchar(80) NOT NULL,
    target_id varchar(120) NOT NULL,
    target_code_snapshot varchar(120),
    target_label_snapshot varchar(240),
    link_role_id varchar(80) NOT NULL,
    primary_link boolean NOT NULL,
    linked_by_actor_id varchar(80) NOT NULL,
    linked_at timestamp with time zone NOT NULL,
    unlinked_at timestamp with time zone,
    active boolean NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_target_link_document_id ON hidra_documents_target_link (document_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_target_link_document_version_id ON hidra_documents_target_link (document_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_target_link_target_id ON hidra_documents_target_link (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_target_link_link_role_id ON hidra_documents_target_link (link_role_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_target_link_linked_by_actor_id ON hidra_documents_target_link (linked_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_target_link_active ON hidra_documents_target_link (active);

CREATE TABLE IF NOT EXISTS hidra_documents_document_version (
    id varchar(80) PRIMARY KEY,
    document_id varchar(80) NOT NULL,
    version_number integer NOT NULL,
    version_label varchar(80),
    title_ar varchar(240),
    title_fr varchar(240),
    title_en varchar(240),
    description varchar(1000),
    storage_object_id varchar(80) NOT NULL,
    mime_type varchar(120) NOT NULL,
    original_filename varchar(255) NOT NULL,
    file_extension varchar(20),
    file_size_bytes bigint NOT NULL,
    checksum_algorithm varchar(40) NOT NULL,
    checksum_value varchar(160) NOT NULL,
    language_code varchar(10),
    document_date date,
    effective_from date,
    effective_to date,
    version_status varchar(40) NOT NULL,
    uploaded_by_actor_id varchar(80) NOT NULL,
    uploaded_by_display_name_snapshot varchar(160) NOT NULL,
    uploaded_at timestamp with time zone NOT NULL,
    approved_by_workflow_instance_id varchar(80),
    approved_at timestamp with time zone,
    superseded_by_version_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_document_version_document_id ON hidra_documents_document_version (document_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_document_version_storage_object_id ON hidra_documents_document_version (storage_object_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_document_version_uploaded_by_actor_id ON hidra_documents_document_version (uploaded_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_document_version_approved_by_workflow_ins ON hidra_documents_document_version (approved_by_workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_documents_document_version_superseded_by_version_id ON hidra_documents_document_version (superseded_by_version_id);
