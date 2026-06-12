-- HIDRA party module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity
-- Module: party

CREATE TABLE IF NOT EXISTS hidra_party_contractor_qualification (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    contractor_category_code varchar(120) NOT NULL,
    qualification_status varchar(80) NOT NULL,
    approved_from timestamp with time zone,
    approved_to timestamp with time zone,
    approval_reference_id varchar(120),
    risk_level_snapshot varchar(80),
    last_review_date timestamp with time zone,
    next_review_date timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_contractor_qualification_party_id ON hidra_party_contractor_qualification (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_contractor_qualification_approval_reference_i ON hidra_party_contractor_qualification (approval_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_contractor_qualification_created_at ON hidra_party_contractor_qualification (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_contractor_qualification_updated_at ON hidra_party_contractor_qualification (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_manufacturer_profile (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    capability_type varchar(80) NOT NULL,
    brand_name varchar(160),
    manufacturer_code varchar(120),
    qualification_status varchar(80) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_manufacturer_profile_party_id ON hidra_party_manufacturer_profile (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_manufacturer_profile_created_at ON hidra_party_manufacturer_profile (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_manufacturer_profile_updated_at ON hidra_party_manufacturer_profile (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_operator_profile (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    capability_type varchar(80) NOT NULL,
    operator_code varchar(120),
    qualification_status varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_operator_profile_party_id ON hidra_party_operator_profile (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_operator_profile_status ON hidra_party_operator_profile (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_operator_profile_created_at ON hidra_party_operator_profile (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_operator_profile_updated_at ON hidra_party_operator_profile (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_owner_profile (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    owner_profile_type varchar(80) NOT NULL,
    ownership_context_code varchar(120),
    risk_level_snapshot varchar(80),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_owner_profile_party_id ON hidra_party_owner_profile (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_owner_profile_status ON hidra_party_owner_profile (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_owner_profile_created_at ON hidra_party_owner_profile (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_owner_profile_updated_at ON hidra_party_owner_profile (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_address (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    address_type varchar(80) NOT NULL,
    country_code varchar(3) NOT NULL,
    state_or_region varchar(160),
    city varchar(160),
    postal_code varchar(40),
    address_line1 varchar(255),
    address_line2 varchar(255),
    primary_address boolean NOT NULL,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_address_party_id ON hidra_party_address (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_address_status ON hidra_party_address (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_address_created_at ON hidra_party_address (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_address_updated_at ON hidra_party_address (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_bank_reference (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    bank_name varchar(255),
    account_reference_masked varchar(160),
    iban_masked varchar(160),
    swift_code varchar(80),
    country_code varchar(3),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_bank_reference_party_id ON hidra_party_bank_reference (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_bank_reference_status ON hidra_party_bank_reference (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_bank_reference_created_at ON hidra_party_bank_reference (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_bank_reference_updated_at ON hidra_party_bank_reference (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_code varchar(120) NOT NULL,
    entry_code varchar(120) NOT NULL,
    parent_entry_id varchar(80),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_catalog_entry_parent_entry_id ON hidra_party_catalog_entry (parent_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_catalog_entry_status ON hidra_party_catalog_entry (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_catalog_entry_created_at ON hidra_party_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_catalog_entry_updated_at ON hidra_party_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    language_code varchar(10) NOT NULL,
    label varchar(255) NOT NULL,
    description text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_catalog_translation_catalog_entry_id ON hidra_party_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_catalog_translation_created_at ON hidra_party_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_catalog_translation_updated_at ON hidra_party_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_certification (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    certification_code varchar(120) NOT NULL,
    certification_body_party_id varchar(80),
    certificate_number varchar(160),
    issued_at timestamp with time zone,
    expires_at timestamp with time zone,
    status varchar(40) NOT NULL,
    document_reference_id varchar(120),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_certification_party_id ON hidra_party_certification (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_certification_certification_body_party_id ON hidra_party_certification (certification_body_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_certification_status ON hidra_party_certification (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_certification_document_reference_id ON hidra_party_certification (document_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_certification_created_at ON hidra_party_certification (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_certification_updated_at ON hidra_party_certification (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_compliance_status (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    compliance_status varchar(80) NOT NULL,
    screening_source varchar(160),
    screening_reference varchar(160),
    checked_at timestamp with time zone NOT NULL,
    valid_until timestamp with time zone,
    notes text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_compliance_status_party_id ON hidra_party_compliance_status (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_compliance_status_created_at ON hidra_party_compliance_status (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_compliance_status_updated_at ON hidra_party_compliance_status (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_contact_person (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    full_name varchar(255) NOT NULL,
    job_title varchar(255),
    email_address varchar(254),
    phone_number varchar(80),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_contact_person_party_id ON hidra_party_contact_person (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_contact_person_status ON hidra_party_contact_person (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_contact_person_created_at ON hidra_party_contact_person (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_contact_person_updated_at ON hidra_party_contact_person (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_contact_point (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    contact_point_type varchar(80) NOT NULL,
    label varchar(255),
    value varchar(255) NOT NULL,
    primary_contact boolean NOT NULL,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_contact_point_party_id ON hidra_party_contact_point (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_contact_point_status ON hidra_party_contact_point (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_contact_point_created_at ON hidra_party_contact_point (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_contact_point_updated_at ON hidra_party_contact_point (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_document_reference (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    document_reference_type varchar(80) NOT NULL,
    document_id varchar(120) NOT NULL,
    document_code_snapshot varchar(120),
    document_title_snapshot varchar(255),
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_document_reference_party_id ON hidra_party_document_reference (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_document_reference_document_id ON hidra_party_document_reference (document_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_document_reference_status ON hidra_party_document_reference (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_document_reference_created_at ON hidra_party_document_reference (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_document_reference_updated_at ON hidra_party_document_reference (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_external_reference (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    external_system_type varchar(80) NOT NULL,
    external_system_code varchar(120) NOT NULL,
    external_reference varchar(255) NOT NULL,
    external_label_snapshot varchar(255),
    status varchar(40) NOT NULL,
    last_synchronized_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_external_reference_party_id ON hidra_party_external_reference (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_external_reference_status ON hidra_party_external_reference (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_external_reference_created_at ON hidra_party_external_reference (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_external_reference_updated_at ON hidra_party_external_reference (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_party (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    party_type_id varchar(80) NOT NULL,
    legal_name varchar(255) NOT NULL,
    trade_name varchar(255),
    short_name varchar(160),
    country_code varchar(3) NOT NULL,
    jurisdiction_code varchar(120),
    status varchar(40) NOT NULL,
    primary_role_code_snapshot varchar(120),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_party_code ON hidra_party_party (code);
CREATE INDEX IF NOT EXISTS ix_hidra_party_party_party_type_id ON hidra_party_party (party_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_party_status ON hidra_party_party (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_party_created_at ON hidra_party_party (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_party_updated_at ON hidra_party_party (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_legal_profile (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    legal_name varchar(255) NOT NULL,
    trade_name varchar(255),
    legal_form_code varchar(120),
    registration_summary text,
    jurisdiction_code varchar(120),
    country_code varchar(3) NOT NULL,
    effective_from timestamp with time zone,
    effective_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_legal_profile_party_id ON hidra_party_legal_profile (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_legal_profile_created_at ON hidra_party_legal_profile (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_legal_profile_updated_at ON hidra_party_legal_profile (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_ownership_link (
    id varchar(80) PRIMARY KEY,
    owner_party_id varchar(80) NOT NULL,
    target_type varchar(80) NOT NULL,
    target_id varchar(120) NOT NULL,
    target_code_snapshot varchar(120),
    target_name_snapshot varchar(255),
    ownership_percentage numeric(10,4),
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_ownership_link_owner_party_id ON hidra_party_ownership_link (owner_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_ownership_link_target_id ON hidra_party_ownership_link (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_ownership_link_status ON hidra_party_ownership_link (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_ownership_link_created_at ON hidra_party_ownership_link (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_ownership_link_updated_at ON hidra_party_ownership_link (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_qualification (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    qualification_type_code varchar(120) NOT NULL,
    qualification_status varchar(80) NOT NULL,
    approved_from timestamp with time zone,
    approved_to timestamp with time zone,
    approval_reference_id varchar(120),
    risk_level_snapshot varchar(80),
    last_review_date timestamp with time zone,
    next_review_date timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_qualification_party_id ON hidra_party_qualification (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_qualification_approval_reference_id ON hidra_party_qualification (approval_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_qualification_created_at ON hidra_party_qualification (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_qualification_updated_at ON hidra_party_qualification (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_registration (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    registration_type varchar(80) NOT NULL,
    registration_number varchar(160) NOT NULL,
    issuing_authority varchar(255),
    country_code varchar(3),
    issued_at timestamp with time zone,
    expires_at timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_registration_party_id ON hidra_party_registration (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_registration_status ON hidra_party_registration (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_registration_created_at ON hidra_party_registration (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_registration_updated_at ON hidra_party_registration (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_relationship (
    id varchar(80) PRIMARY KEY,
    source_party_id varchar(80) NOT NULL,
    target_party_id varchar(80) NOT NULL,
    relationship_type varchar(80) NOT NULL,
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_relationship_source_party_id ON hidra_party_relationship (source_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_relationship_target_party_id ON hidra_party_relationship (target_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_relationship_status ON hidra_party_relationship (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_relationship_created_at ON hidra_party_relationship (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_relationship_updated_at ON hidra_party_relationship (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_risk_snapshot (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    risk_level varchar(80) NOT NULL,
    risk_source_module varchar(80),
    risk_source_reference_id varchar(120),
    risk_reason text,
    assessed_at timestamp with time zone NOT NULL,
    valid_until timestamp with time zone,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_risk_snapshot_party_id ON hidra_party_risk_snapshot (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_risk_snapshot_risk_source_reference_id ON hidra_party_risk_snapshot (risk_source_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_risk_snapshot_created_at ON hidra_party_risk_snapshot (created_at);

CREATE TABLE IF NOT EXISTS hidra_party_role_assignment (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    role_id varchar(80) NOT NULL,
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone,
    status varchar(40) NOT NULL,
    qualification_required boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_role_assignment_party_id ON hidra_party_role_assignment (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_role_assignment_role_id ON hidra_party_role_assignment (role_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_role_assignment_status ON hidra_party_role_assignment (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_role_assignment_created_at ON hidra_party_role_assignment (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_role_assignment_updated_at ON hidra_party_role_assignment (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_role (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    description text,
    qualification_required_by_default boolean NOT NULL,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_role_code ON hidra_party_role (code);
CREATE INDEX IF NOT EXISTS ix_hidra_party_role_status ON hidra_party_role (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_role_created_at ON hidra_party_role (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_role_updated_at ON hidra_party_role (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_role_translation (
    id varchar(80) PRIMARY KEY,
    party_role_id varchar(80) NOT NULL,
    language_code varchar(10) NOT NULL,
    label varchar(255) NOT NULL,
    description text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_role_translation_party_role_id ON hidra_party_role_translation (party_role_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_role_translation_created_at ON hidra_party_role_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_role_translation_updated_at ON hidra_party_role_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_status_history (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    old_status varchar(40),
    new_status varchar(40) NOT NULL,
    reason varchar(80) NOT NULL,
    reason_message text,
    changed_by_actor_id varchar(120),
    changed_at timestamp with time zone NOT NULL,
    correlation_id varchar(120)
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_status_history_party_id ON hidra_party_status_history (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_status_history_changed_by_actor_id ON hidra_party_status_history (changed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_status_history_correlation_id ON hidra_party_status_history (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_party_tax_identifier (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    tax_identifier_type varchar(80) NOT NULL,
    identifier_value varchar(160) NOT NULL,
    country_code varchar(3),
    primary_identifier boolean NOT NULL,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_tax_identifier_party_id ON hidra_party_tax_identifier (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_tax_identifier_status ON hidra_party_tax_identifier (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_tax_identifier_created_at ON hidra_party_tax_identifier (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_tax_identifier_updated_at ON hidra_party_tax_identifier (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_type (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    regulatory_class_code varchar(120),
    description text,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_type_code ON hidra_party_type (code);
CREATE INDEX IF NOT EXISTS ix_hidra_party_type_status ON hidra_party_type (status);
CREATE INDEX IF NOT EXISTS ix_hidra_party_type_created_at ON hidra_party_type (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_type_updated_at ON hidra_party_type (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_type_translation (
    id varchar(80) PRIMARY KEY,
    party_type_id varchar(80) NOT NULL,
    language_code varchar(10) NOT NULL,
    label varchar(255) NOT NULL,
    description text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_type_translation_party_type_id ON hidra_party_type_translation (party_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_type_translation_created_at ON hidra_party_type_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_type_translation_updated_at ON hidra_party_type_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_supplier_qualification (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    supplier_category_code varchar(120) NOT NULL,
    qualification_status varchar(80) NOT NULL,
    approved_from timestamp with time zone,
    approved_to timestamp with time zone,
    approval_reference_id varchar(120),
    risk_level_snapshot varchar(80),
    last_review_date timestamp with time zone,
    next_review_date timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_supplier_qualification_party_id ON hidra_party_supplier_qualification (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_supplier_qualification_approval_reference_id ON hidra_party_supplier_qualification (approval_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_supplier_qualification_created_at ON hidra_party_supplier_qualification (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_supplier_qualification_updated_at ON hidra_party_supplier_qualification (updated_at);

CREATE TABLE IF NOT EXISTS hidra_party_vendor_qualification (
    id varchar(80) PRIMARY KEY,
    party_id varchar(80) NOT NULL,
    vendor_category_code varchar(120) NOT NULL,
    qualification_status varchar(80) NOT NULL,
    approved_from timestamp with time zone,
    approved_to timestamp with time zone,
    approval_reference_id varchar(120),
    risk_level_snapshot varchar(80),
    last_review_date timestamp with time zone,
    next_review_date timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_party_vendor_qualification_party_id ON hidra_party_vendor_qualification (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_vendor_qualification_approval_reference_id ON hidra_party_vendor_qualification (approval_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_party_vendor_qualification_created_at ON hidra_party_vendor_qualification (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_party_vendor_qualification_updated_at ON hidra_party_vendor_qualification (updated_at);
