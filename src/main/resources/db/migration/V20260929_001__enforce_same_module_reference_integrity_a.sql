-- HIDRA repository-wide same-module reference integrity (batch A)
-- Roadmap: HRA-111
-- Generated from HRA-110 approved same-module ownership, reconciled on live main.
-- PostgreSQL NOT VALID + VALIDATE is intentional: new writes fail closed immediately and
-- existing rows are verified before Flyway can commit the migration.
-- Cross-module, historical, external, typed/polymorphic identifiers are deliberately excluded.

-- identity: 27 HRA-111 same-module foreign keys
-- hidra_identity_authorization_decision.user_id -> hidra_identity_user.id
ALTER TABLE hidra_identity_authorization_decision
    ADD CONSTRAINT fk_hra111_identity_001
    FOREIGN KEY (user_id) REFERENCES hidra_identity_user (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_authorization_decision VALIDATE CONSTRAINT fk_hra111_identity_001;

-- hidra_identity_authorization_delegation_grant.delegate_user_id -> hidra_identity_user.id
ALTER TABLE hidra_identity_authorization_delegation_grant
    ADD CONSTRAINT fk_hra111_identity_002
    FOREIGN KEY (delegate_user_id) REFERENCES hidra_identity_user (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_authorization_delegation_grant VALIDATE CONSTRAINT fk_hra111_identity_002;

-- hidra_identity_authorization_delegation_grant.delegator_user_id -> hidra_identity_user.id
ALTER TABLE hidra_identity_authorization_delegation_grant
    ADD CONSTRAINT fk_hra111_identity_003
    FOREIGN KEY (delegator_user_id) REFERENCES hidra_identity_user (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_authorization_delegation_grant VALIDATE CONSTRAINT fk_hra111_identity_003;

-- hidra_identity_authorization_policy_rule.policy_version_id -> hidra_identity_authorization_policy_version.id
ALTER TABLE hidra_identity_authorization_policy_rule
    ADD CONSTRAINT fk_hra111_identity_004
    FOREIGN KEY (policy_version_id) REFERENCES hidra_identity_authorization_policy_version (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_authorization_policy_rule VALIDATE CONSTRAINT fk_hra111_identity_004;

-- hidra_identity_authorization_policy_version.policy_id -> hidra_identity_authorization_policy.id
ALTER TABLE hidra_identity_authorization_policy_version
    ADD CONSTRAINT fk_hra111_identity_005
    FOREIGN KEY (policy_id) REFERENCES hidra_identity_authorization_policy (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_authorization_policy_version VALIDATE CONSTRAINT fk_hra111_identity_005;

-- hidra_identity_external_group_mapping.group_id -> hidra_identity_group.id
ALTER TABLE hidra_identity_external_group_mapping
    ADD CONSTRAINT fk_hra111_identity_006
    FOREIGN KEY (group_id) REFERENCES hidra_identity_group (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_external_group_mapping VALIDATE CONSTRAINT fk_hra111_identity_006;

-- hidra_identity_external_group_mapping.identity_provider_id -> hidra_identity_provider.id
ALTER TABLE hidra_identity_external_group_mapping
    ADD CONSTRAINT fk_hra111_identity_007
    FOREIGN KEY (identity_provider_id) REFERENCES hidra_identity_provider (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_external_group_mapping VALIDATE CONSTRAINT fk_hra111_identity_007;

-- hidra_identity_external_identity.identity_provider_id -> hidra_identity_provider.id
ALTER TABLE hidra_identity_external_identity
    ADD CONSTRAINT fk_hra111_identity_008
    FOREIGN KEY (identity_provider_id) REFERENCES hidra_identity_provider (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_external_identity VALIDATE CONSTRAINT fk_hra111_identity_008;

-- hidra_identity_external_identity.user_id -> hidra_identity_user.id
ALTER TABLE hidra_identity_external_identity
    ADD CONSTRAINT fk_hra111_identity_009
    FOREIGN KEY (user_id) REFERENCES hidra_identity_user (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_external_identity VALIDATE CONSTRAINT fk_hra111_identity_009;

-- hidra_identity_external_permission_mapping.identity_provider_id -> hidra_identity_provider.id
ALTER TABLE hidra_identity_external_permission_mapping
    ADD CONSTRAINT fk_hra111_identity_010
    FOREIGN KEY (identity_provider_id) REFERENCES hidra_identity_provider (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_external_permission_mapping VALIDATE CONSTRAINT fk_hra111_identity_010;

-- hidra_identity_external_permission_mapping.permission_id -> hidra_identity_permission.id
ALTER TABLE hidra_identity_external_permission_mapping
    ADD CONSTRAINT fk_hra111_identity_011
    FOREIGN KEY (permission_id) REFERENCES hidra_identity_permission (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_external_permission_mapping VALIDATE CONSTRAINT fk_hra111_identity_011;

-- hidra_identity_external_role_mapping.identity_provider_id -> hidra_identity_provider.id
ALTER TABLE hidra_identity_external_role_mapping
    ADD CONSTRAINT fk_hra111_identity_012
    FOREIGN KEY (identity_provider_id) REFERENCES hidra_identity_provider (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_external_role_mapping VALIDATE CONSTRAINT fk_hra111_identity_012;

-- hidra_identity_external_role_mapping.role_id -> hidra_identity_role.id
ALTER TABLE hidra_identity_external_role_mapping
    ADD CONSTRAINT fk_hra111_identity_013
    FOREIGN KEY (role_id) REFERENCES hidra_identity_role (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_external_role_mapping VALIDATE CONSTRAINT fk_hra111_identity_013;

-- hidra_identity_group_role_grant.group_id -> hidra_identity_group.id
ALTER TABLE hidra_identity_group_role_grant
    ADD CONSTRAINT fk_hra111_identity_014
    FOREIGN KEY (group_id) REFERENCES hidra_identity_group (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_group_role_grant VALIDATE CONSTRAINT fk_hra111_identity_014;

-- hidra_identity_group_role_grant.role_id -> hidra_identity_role.id
ALTER TABLE hidra_identity_group_role_grant
    ADD CONSTRAINT fk_hra111_identity_015
    FOREIGN KEY (role_id) REFERENCES hidra_identity_role (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_group_role_grant VALIDATE CONSTRAINT fk_hra111_identity_015;

-- hidra_identity_login_session.user_id -> hidra_identity_user.id
ALTER TABLE hidra_identity_login_session
    ADD CONSTRAINT fk_hra111_identity_016
    FOREIGN KEY (user_id) REFERENCES hidra_identity_user (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_login_session VALIDATE CONSTRAINT fk_hra111_identity_016;

-- hidra_identity_role_permission_grant.permission_id -> hidra_identity_permission.id
ALTER TABLE hidra_identity_role_permission_grant
    ADD CONSTRAINT fk_hra111_identity_017
    FOREIGN KEY (permission_id) REFERENCES hidra_identity_permission (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_role_permission_grant VALIDATE CONSTRAINT fk_hra111_identity_017;

-- hidra_identity_role_permission_grant.role_id -> hidra_identity_role.id
ALTER TABLE hidra_identity_role_permission_grant
    ADD CONSTRAINT fk_hra111_identity_018
    FOREIGN KEY (role_id) REFERENCES hidra_identity_role (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_role_permission_grant VALIDATE CONSTRAINT fk_hra111_identity_018;

-- hidra_identity_subject_security_attribute.attribute_definition_id -> hidra_identity_attribute_definition.id
ALTER TABLE hidra_identity_subject_security_attribute
    ADD CONSTRAINT fk_hra111_identity_019
    FOREIGN KEY (attribute_definition_id) REFERENCES hidra_identity_attribute_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_subject_security_attribute VALIDATE CONSTRAINT fk_hra111_identity_019;

-- hidra_identity_synchronization_job.identity_provider_id -> hidra_identity_provider.id
ALTER TABLE hidra_identity_synchronization_job
    ADD CONSTRAINT fk_hra111_identity_020
    FOREIGN KEY (identity_provider_id) REFERENCES hidra_identity_provider (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_synchronization_job VALIDATE CONSTRAINT fk_hra111_identity_020;

-- hidra_identity_synchronization_record.job_id -> hidra_identity_synchronization_job.id
ALTER TABLE hidra_identity_synchronization_record
    ADD CONSTRAINT fk_hra111_identity_021
    FOREIGN KEY (job_id) REFERENCES hidra_identity_synchronization_job (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_synchronization_record VALIDATE CONSTRAINT fk_hra111_identity_021;

-- hidra_identity_user_group_membership.group_id -> hidra_identity_group.id
ALTER TABLE hidra_identity_user_group_membership
    ADD CONSTRAINT fk_hra111_identity_022
    FOREIGN KEY (group_id) REFERENCES hidra_identity_group (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_user_group_membership VALIDATE CONSTRAINT fk_hra111_identity_022;

-- hidra_identity_user_group_membership.user_id -> hidra_identity_user.id
ALTER TABLE hidra_identity_user_group_membership
    ADD CONSTRAINT fk_hra111_identity_023
    FOREIGN KEY (user_id) REFERENCES hidra_identity_user (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_user_group_membership VALIDATE CONSTRAINT fk_hra111_identity_023;

-- hidra_identity_user_permission_grant.permission_id -> hidra_identity_permission.id
ALTER TABLE hidra_identity_user_permission_grant
    ADD CONSTRAINT fk_hra111_identity_024
    FOREIGN KEY (permission_id) REFERENCES hidra_identity_permission (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_user_permission_grant VALIDATE CONSTRAINT fk_hra111_identity_024;

-- hidra_identity_user_permission_grant.user_id -> hidra_identity_user.id
ALTER TABLE hidra_identity_user_permission_grant
    ADD CONSTRAINT fk_hra111_identity_025
    FOREIGN KEY (user_id) REFERENCES hidra_identity_user (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_user_permission_grant VALIDATE CONSTRAINT fk_hra111_identity_025;

-- hidra_identity_user_role_grant.role_id -> hidra_identity_role.id
ALTER TABLE hidra_identity_user_role_grant
    ADD CONSTRAINT fk_hra111_identity_026
    FOREIGN KEY (role_id) REFERENCES hidra_identity_role (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_user_role_grant VALIDATE CONSTRAINT fk_hra111_identity_026;

-- hidra_identity_user_role_grant.user_id -> hidra_identity_user.id
ALTER TABLE hidra_identity_user_role_grant
    ADD CONSTRAINT fk_hra111_identity_027
    FOREIGN KEY (user_id) REFERENCES hidra_identity_user (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_identity_user_role_grant VALIDATE CONSTRAINT fk_hra111_identity_027;

-- party: 29 HRA-111 same-module foreign keys
-- hidra_party_address.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_address
    ADD CONSTRAINT fk_hra111_party_001
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_address VALIDATE CONSTRAINT fk_hra111_party_001;

-- hidra_party_bank_reference.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_bank_reference
    ADD CONSTRAINT fk_hra111_party_002
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_bank_reference VALIDATE CONSTRAINT fk_hra111_party_002;

-- hidra_party_catalog_translation.catalog_entry_id -> hidra_party_catalog_entry.id
ALTER TABLE hidra_party_catalog_translation
    ADD CONSTRAINT fk_hra111_party_003
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_party_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_catalog_translation VALIDATE CONSTRAINT fk_hra111_party_003;

-- hidra_party_certification.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_certification
    ADD CONSTRAINT fk_hra111_party_004
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_certification VALIDATE CONSTRAINT fk_hra111_party_004;

-- hidra_party_compliance_status.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_compliance_status
    ADD CONSTRAINT fk_hra111_party_005
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_compliance_status VALIDATE CONSTRAINT fk_hra111_party_005;

-- hidra_party_contact_person.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_contact_person
    ADD CONSTRAINT fk_hra111_party_006
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_contact_person VALIDATE CONSTRAINT fk_hra111_party_006;

-- hidra_party_contact_point.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_contact_point
    ADD CONSTRAINT fk_hra111_party_007
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_contact_point VALIDATE CONSTRAINT fk_hra111_party_007;

-- hidra_party_contractor_qualification.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_contractor_qualification
    ADD CONSTRAINT fk_hra111_party_008
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_contractor_qualification VALIDATE CONSTRAINT fk_hra111_party_008;

-- hidra_party_document_reference.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_document_reference
    ADD CONSTRAINT fk_hra111_party_009
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_document_reference VALIDATE CONSTRAINT fk_hra111_party_009;

-- hidra_party_external_reference.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_external_reference
    ADD CONSTRAINT fk_hra111_party_010
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_external_reference VALIDATE CONSTRAINT fk_hra111_party_010;

-- hidra_party_legal_profile.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_legal_profile
    ADD CONSTRAINT fk_hra111_party_011
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_legal_profile VALIDATE CONSTRAINT fk_hra111_party_011;

-- hidra_party_manufacturer_profile.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_manufacturer_profile
    ADD CONSTRAINT fk_hra111_party_012
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_manufacturer_profile VALIDATE CONSTRAINT fk_hra111_party_012;

-- hidra_party_operator_profile.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_operator_profile
    ADD CONSTRAINT fk_hra111_party_013
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_operator_profile VALIDATE CONSTRAINT fk_hra111_party_013;

-- hidra_party_owner_profile.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_owner_profile
    ADD CONSTRAINT fk_hra111_party_014
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_owner_profile VALIDATE CONSTRAINT fk_hra111_party_014;

-- hidra_party_ownership_link.owner_party_id -> hidra_party_party.id
ALTER TABLE hidra_party_ownership_link
    ADD CONSTRAINT fk_hra111_party_015
    FOREIGN KEY (owner_party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_ownership_link VALIDATE CONSTRAINT fk_hra111_party_015;

-- hidra_party_party.party_type_id -> hidra_party_type.id
ALTER TABLE hidra_party_party
    ADD CONSTRAINT fk_hra111_party_016
    FOREIGN KEY (party_type_id) REFERENCES hidra_party_type (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_party VALIDATE CONSTRAINT fk_hra111_party_016;

-- hidra_party_qualification.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_qualification
    ADD CONSTRAINT fk_hra111_party_017
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_qualification VALIDATE CONSTRAINT fk_hra111_party_017;

-- hidra_party_registration.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_registration
    ADD CONSTRAINT fk_hra111_party_018
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_registration VALIDATE CONSTRAINT fk_hra111_party_018;

-- hidra_party_relationship.source_party_id -> hidra_party_party.id
ALTER TABLE hidra_party_relationship
    ADD CONSTRAINT fk_hra111_party_019
    FOREIGN KEY (source_party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_relationship VALIDATE CONSTRAINT fk_hra111_party_019;

-- hidra_party_relationship.target_party_id -> hidra_party_party.id
ALTER TABLE hidra_party_relationship
    ADD CONSTRAINT fk_hra111_party_020
    FOREIGN KEY (target_party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_relationship VALIDATE CONSTRAINT fk_hra111_party_020;

-- hidra_party_risk_snapshot.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_risk_snapshot
    ADD CONSTRAINT fk_hra111_party_021
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_risk_snapshot VALIDATE CONSTRAINT fk_hra111_party_021;

-- hidra_party_role_assignment.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_role_assignment
    ADD CONSTRAINT fk_hra111_party_022
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_role_assignment VALIDATE CONSTRAINT fk_hra111_party_022;

-- hidra_party_role_assignment.role_id -> hidra_party_role.id
ALTER TABLE hidra_party_role_assignment
    ADD CONSTRAINT fk_hra111_party_023
    FOREIGN KEY (role_id) REFERENCES hidra_party_role (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_role_assignment VALIDATE CONSTRAINT fk_hra111_party_023;

-- hidra_party_role_translation.party_role_id -> hidra_party_role.id
ALTER TABLE hidra_party_role_translation
    ADD CONSTRAINT fk_hra111_party_024
    FOREIGN KEY (party_role_id) REFERENCES hidra_party_role (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_role_translation VALIDATE CONSTRAINT fk_hra111_party_024;

-- hidra_party_status_history.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_status_history
    ADD CONSTRAINT fk_hra111_party_025
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_status_history VALIDATE CONSTRAINT fk_hra111_party_025;

-- hidra_party_supplier_qualification.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_supplier_qualification
    ADD CONSTRAINT fk_hra111_party_026
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_supplier_qualification VALIDATE CONSTRAINT fk_hra111_party_026;

-- hidra_party_tax_identifier.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_tax_identifier
    ADD CONSTRAINT fk_hra111_party_027
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_tax_identifier VALIDATE CONSTRAINT fk_hra111_party_027;

-- hidra_party_type_translation.party_type_id -> hidra_party_type.id
ALTER TABLE hidra_party_type_translation
    ADD CONSTRAINT fk_hra111_party_028
    FOREIGN KEY (party_type_id) REFERENCES hidra_party_type (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_type_translation VALIDATE CONSTRAINT fk_hra111_party_028;

-- hidra_party_vendor_qualification.party_id -> hidra_party_party.id
ALTER TABLE hidra_party_vendor_qualification
    ADD CONSTRAINT fk_hra111_party_029
    FOREIGN KEY (party_id) REFERENCES hidra_party_party (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_party_vendor_qualification VALIDATE CONSTRAINT fk_hra111_party_029;

-- topology: 20 HRA-111 same-module foreign keys
-- hidra_topology_connection.from_node_id -> hidra_topology_node.id
ALTER TABLE hidra_topology_connection
    ADD CONSTRAINT fk_hra111_topology_001
    FOREIGN KEY (from_node_id) REFERENCES hidra_topology_node (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_connection VALIDATE CONSTRAINT fk_hra111_topology_001;

-- hidra_topology_connection.to_node_id -> hidra_topology_node.id
ALTER TABLE hidra_topology_connection
    ADD CONSTRAINT fk_hra111_topology_002
    FOREIGN KEY (to_node_id) REFERENCES hidra_topology_node (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_connection VALIDATE CONSTRAINT fk_hra111_topology_002;

-- hidra_topology_equipment_attribute_definition.equipment_type_version_id -> hidra_topology_equipment_type_version.id
ALTER TABLE hidra_topology_equipment_attribute_definition
    ADD CONSTRAINT fk_hra111_topology_003
    FOREIGN KEY (equipment_type_version_id) REFERENCES hidra_topology_equipment_type_version (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_equipment_attribute_definition VALIDATE CONSTRAINT fk_hra111_topology_003;

-- hidra_topology_equipment_attribute_value.attribute_definition_id -> hidra_topology_equipment_attribute_definition.id
ALTER TABLE hidra_topology_equipment_attribute_value
    ADD CONSTRAINT fk_hra111_topology_004
    FOREIGN KEY (attribute_definition_id) REFERENCES hidra_topology_equipment_attribute_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_equipment_attribute_value VALIDATE CONSTRAINT fk_hra111_topology_004;

-- hidra_topology_equipment_attribute_value.equipment_id -> hidra_topology_equipment.id
ALTER TABLE hidra_topology_equipment_attribute_value
    ADD CONSTRAINT fk_hra111_topology_005
    FOREIGN KEY (equipment_id) REFERENCES hidra_topology_equipment (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_equipment_attribute_value VALIDATE CONSTRAINT fk_hra111_topology_005;

-- hidra_topology_equipment_type_version.equipment_type_id -> hidra_topology_equipment_type.id
ALTER TABLE hidra_topology_equipment_type_version
    ADD CONSTRAINT fk_hra111_topology_006
    FOREIGN KEY (equipment_type_id) REFERENCES hidra_topology_equipment_type (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_equipment_type_version VALIDATE CONSTRAINT fk_hra111_topology_006;

-- hidra_topology_equipment.equipment_type_id -> hidra_topology_equipment_type.id
ALTER TABLE hidra_topology_equipment
    ADD CONSTRAINT fk_hra111_topology_007
    FOREIGN KEY (equipment_type_id) REFERENCES hidra_topology_equipment_type (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_equipment VALIDATE CONSTRAINT fk_hra111_topology_007;

-- hidra_topology_facility_attribute_definition.facility_type_version_id -> hidra_topology_facility_type_version.id
ALTER TABLE hidra_topology_facility_attribute_definition
    ADD CONSTRAINT fk_hra111_topology_008
    FOREIGN KEY (facility_type_version_id) REFERENCES hidra_topology_facility_type_version (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_facility_attribute_definition VALIDATE CONSTRAINT fk_hra111_topology_008;

-- hidra_topology_facility_attribute_value.attribute_definition_id -> hidra_topology_facility_attribute_definition.id
ALTER TABLE hidra_topology_facility_attribute_value
    ADD CONSTRAINT fk_hra111_topology_009
    FOREIGN KEY (attribute_definition_id) REFERENCES hidra_topology_facility_attribute_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_facility_attribute_value VALIDATE CONSTRAINT fk_hra111_topology_009;

-- hidra_topology_facility_attribute_value.facility_id -> hidra_topology_facility.id
ALTER TABLE hidra_topology_facility_attribute_value
    ADD CONSTRAINT fk_hra111_topology_010
    FOREIGN KEY (facility_id) REFERENCES hidra_topology_facility (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_facility_attribute_value VALIDATE CONSTRAINT fk_hra111_topology_010;

-- hidra_topology_facility_node_binding.facility_id -> hidra_topology_facility.id
ALTER TABLE hidra_topology_facility_node_binding
    ADD CONSTRAINT fk_hra111_topology_011
    FOREIGN KEY (facility_id) REFERENCES hidra_topology_facility (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_facility_node_binding VALIDATE CONSTRAINT fk_hra111_topology_011;

-- hidra_topology_facility_node_binding.node_id -> hidra_topology_node.id
ALTER TABLE hidra_topology_facility_node_binding
    ADD CONSTRAINT fk_hra111_topology_012
    FOREIGN KEY (node_id) REFERENCES hidra_topology_node (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_facility_node_binding VALIDATE CONSTRAINT fk_hra111_topology_012;

-- hidra_topology_facility_type_version.facility_type_id -> hidra_topology_facility_type.id
ALTER TABLE hidra_topology_facility_type_version
    ADD CONSTRAINT fk_hra111_topology_013
    FOREIGN KEY (facility_type_id) REFERENCES hidra_topology_facility_type (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_facility_type_version VALIDATE CONSTRAINT fk_hra111_topology_013;

-- hidra_topology_facility.facility_type_id -> hidra_topology_facility_type.id
ALTER TABLE hidra_topology_facility
    ADD CONSTRAINT fk_hra111_topology_014
    FOREIGN KEY (facility_type_id) REFERENCES hidra_topology_facility_type (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_facility VALIDATE CONSTRAINT fk_hra111_topology_014;

-- hidra_topology_pipeline_segment.from_node_id -> hidra_topology_node.id
ALTER TABLE hidra_topology_pipeline_segment
    ADD CONSTRAINT fk_hra111_topology_015
    FOREIGN KEY (from_node_id) REFERENCES hidra_topology_node (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_pipeline_segment VALIDATE CONSTRAINT fk_hra111_topology_015;

-- hidra_topology_pipeline_segment.pipeline_id -> hidra_topology_pipeline.id
ALTER TABLE hidra_topology_pipeline_segment
    ADD CONSTRAINT fk_hra111_topology_016
    FOREIGN KEY (pipeline_id) REFERENCES hidra_topology_pipeline (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_pipeline_segment VALIDATE CONSTRAINT fk_hra111_topology_016;

-- hidra_topology_pipeline_segment.to_node_id -> hidra_topology_node.id
ALTER TABLE hidra_topology_pipeline_segment
    ADD CONSTRAINT fk_hra111_topology_017
    FOREIGN KEY (to_node_id) REFERENCES hidra_topology_node (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_pipeline_segment VALIDATE CONSTRAINT fk_hra111_topology_017;

-- hidra_topology_pipeline_system_facility.facility_id -> hidra_topology_facility.id
ALTER TABLE hidra_topology_pipeline_system_facility
    ADD CONSTRAINT fk_hra111_topology_018
    FOREIGN KEY (facility_id) REFERENCES hidra_topology_facility (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_pipeline_system_facility VALIDATE CONSTRAINT fk_hra111_topology_018;

-- hidra_topology_pipeline_system_facility.pipeline_system_id -> hidra_topology_pipeline_system.id
ALTER TABLE hidra_topology_pipeline_system_facility
    ADD CONSTRAINT fk_hra111_topology_019
    FOREIGN KEY (pipeline_system_id) REFERENCES hidra_topology_pipeline_system (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_pipeline_system_facility VALIDATE CONSTRAINT fk_hra111_topology_019;

-- hidra_topology_pipeline.pipeline_system_id -> hidra_topology_pipeline_system.id
ALTER TABLE hidra_topology_pipeline
    ADD CONSTRAINT fk_hra111_topology_020
    FOREIGN KEY (pipeline_system_id) REFERENCES hidra_topology_pipeline_system (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_topology_pipeline VALIDATE CONSTRAINT fk_hra111_topology_020;

-- telemetry: 28 HRA-111 same-module foreign keys
-- hidra_telemetry_device.device_type_id -> hidra_telemetry_type_catalog.id
ALTER TABLE hidra_telemetry_device
    ADD CONSTRAINT fk_hra111_telemetry_001
    FOREIGN KEY (device_type_id) REFERENCES hidra_telemetry_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_device VALIDATE CONSTRAINT fk_hra111_telemetry_001;

-- hidra_telemetry_device.source_id -> hidra_telemetry_source.id
ALTER TABLE hidra_telemetry_device
    ADD CONSTRAINT fk_hra111_telemetry_002
    FOREIGN KEY (source_id) REFERENCES hidra_telemetry_source (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_device VALIDATE CONSTRAINT fk_hra111_telemetry_002;

-- hidra_telemetry_external_tag_mapping.point_id -> hidra_telemetry_point.id
ALTER TABLE hidra_telemetry_external_tag_mapping
    ADD CONSTRAINT fk_hra111_telemetry_003
    FOREIGN KEY (point_id) REFERENCES hidra_telemetry_point (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_external_tag_mapping VALIDATE CONSTRAINT fk_hra111_telemetry_003;

-- hidra_telemetry_external_tag_mapping.source_id -> hidra_telemetry_source.id
ALTER TABLE hidra_telemetry_external_tag_mapping
    ADD CONSTRAINT fk_hra111_telemetry_004
    FOREIGN KEY (source_id) REFERENCES hidra_telemetry_source (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_external_tag_mapping VALIDATE CONSTRAINT fk_hra111_telemetry_004;

-- hidra_telemetry_ingestion_batch.source_id -> hidra_telemetry_source.id
ALTER TABLE hidra_telemetry_ingestion_batch
    ADD CONSTRAINT fk_hra111_telemetry_005
    FOREIGN KEY (source_id) REFERENCES hidra_telemetry_source (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_ingestion_batch VALIDATE CONSTRAINT fk_hra111_telemetry_005;

-- hidra_telemetry_point_binding.binding_role_id -> hidra_telemetry_type_catalog.id
ALTER TABLE hidra_telemetry_point_binding
    ADD CONSTRAINT fk_hra111_telemetry_006
    FOREIGN KEY (binding_role_id) REFERENCES hidra_telemetry_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_point_binding VALIDATE CONSTRAINT fk_hra111_telemetry_006;

-- hidra_telemetry_point_binding.point_id -> hidra_telemetry_point.id
ALTER TABLE hidra_telemetry_point_binding
    ADD CONSTRAINT fk_hra111_telemetry_007
    FOREIGN KEY (point_id) REFERENCES hidra_telemetry_point (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_point_binding VALIDATE CONSTRAINT fk_hra111_telemetry_007;

-- hidra_telemetry_point_state_snapshot.point_id -> hidra_telemetry_point.id
ALTER TABLE hidra_telemetry_point_state_snapshot
    ADD CONSTRAINT fk_hra111_telemetry_008
    FOREIGN KEY (point_id) REFERENCES hidra_telemetry_point (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_point_state_snapshot VALIDATE CONSTRAINT fk_hra111_telemetry_008;

-- hidra_telemetry_point.device_id -> hidra_telemetry_device.id
ALTER TABLE hidra_telemetry_point
    ADD CONSTRAINT fk_hra111_telemetry_009
    FOREIGN KEY (device_id) REFERENCES hidra_telemetry_device (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_point VALIDATE CONSTRAINT fk_hra111_telemetry_009;

-- hidra_telemetry_point.point_type_id -> hidra_telemetry_type_catalog.id
ALTER TABLE hidra_telemetry_point
    ADD CONSTRAINT fk_hra111_telemetry_010
    FOREIGN KEY (point_type_id) REFERENCES hidra_telemetry_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_point VALIDATE CONSTRAINT fk_hra111_telemetry_010;

-- hidra_telemetry_point.signal_type_id -> hidra_telemetry_type_catalog.id
ALTER TABLE hidra_telemetry_point
    ADD CONSTRAINT fk_hra111_telemetry_011
    FOREIGN KEY (signal_type_id) REFERENCES hidra_telemetry_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_point VALIDATE CONSTRAINT fk_hra111_telemetry_011;

-- hidra_telemetry_quality_assessment.input_quality_code_id -> hidra_telemetry_type_catalog.id
ALTER TABLE hidra_telemetry_quality_assessment
    ADD CONSTRAINT fk_hra111_telemetry_012
    FOREIGN KEY (input_quality_code_id) REFERENCES hidra_telemetry_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_quality_assessment VALIDATE CONSTRAINT fk_hra111_telemetry_012;

-- hidra_telemetry_quality_assessment.point_id -> hidra_telemetry_point.id
ALTER TABLE hidra_telemetry_quality_assessment
    ADD CONSTRAINT fk_hra111_telemetry_013
    FOREIGN KEY (point_id) REFERENCES hidra_telemetry_point (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_quality_assessment VALIDATE CONSTRAINT fk_hra111_telemetry_013;

-- hidra_telemetry_quality_assessment.reading_id -> hidra_telemetry_reading.id
ALTER TABLE hidra_telemetry_quality_assessment
    ADD CONSTRAINT fk_hra111_telemetry_014
    FOREIGN KEY (reading_id) REFERENCES hidra_telemetry_reading (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_quality_assessment VALIDATE CONSTRAINT fk_hra111_telemetry_014;

-- hidra_telemetry_quality_assessment.resolved_quality_code_id -> hidra_telemetry_type_catalog.id
ALTER TABLE hidra_telemetry_quality_assessment
    ADD CONSTRAINT fk_hra111_telemetry_015
    FOREIGN KEY (resolved_quality_code_id) REFERENCES hidra_telemetry_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_quality_assessment VALIDATE CONSTRAINT fk_hra111_telemetry_015;

-- hidra_telemetry_quarantine_record.source_id -> hidra_telemetry_source.id
ALTER TABLE hidra_telemetry_quarantine_record
    ADD CONSTRAINT fk_hra111_telemetry_016
    FOREIGN KEY (source_id) REFERENCES hidra_telemetry_source (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_quarantine_record VALIDATE CONSTRAINT fk_hra111_telemetry_016;

-- hidra_telemetry_reading.point_id -> hidra_telemetry_point.id
ALTER TABLE hidra_telemetry_reading
    ADD CONSTRAINT fk_hra111_telemetry_017
    FOREIGN KEY (point_id) REFERENCES hidra_telemetry_point (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_reading VALIDATE CONSTRAINT fk_hra111_telemetry_017;

-- hidra_telemetry_reading.quality_code_id -> hidra_telemetry_type_catalog.id
ALTER TABLE hidra_telemetry_reading
    ADD CONSTRAINT fk_hra111_telemetry_018
    FOREIGN KEY (quality_code_id) REFERENCES hidra_telemetry_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_reading VALIDATE CONSTRAINT fk_hra111_telemetry_018;

-- hidra_telemetry_source_endpoint.protocol_id -> hidra_telemetry_type_catalog.id
ALTER TABLE hidra_telemetry_source_endpoint
    ADD CONSTRAINT fk_hra111_telemetry_019
    FOREIGN KEY (protocol_id) REFERENCES hidra_telemetry_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_source_endpoint VALIDATE CONSTRAINT fk_hra111_telemetry_019;

-- hidra_telemetry_source_endpoint.source_id -> hidra_telemetry_source.id
ALTER TABLE hidra_telemetry_source_endpoint
    ADD CONSTRAINT fk_hra111_telemetry_020
    FOREIGN KEY (source_id) REFERENCES hidra_telemetry_source (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_source_endpoint VALIDATE CONSTRAINT fk_hra111_telemetry_020;

-- hidra_telemetry_source.protocol_id -> hidra_telemetry_type_catalog.id
ALTER TABLE hidra_telemetry_source
    ADD CONSTRAINT fk_hra111_telemetry_021
    FOREIGN KEY (protocol_id) REFERENCES hidra_telemetry_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_source VALIDATE CONSTRAINT fk_hra111_telemetry_021;

-- hidra_telemetry_source.source_type_id -> hidra_telemetry_type_catalog.id
ALTER TABLE hidra_telemetry_source
    ADD CONSTRAINT fk_hra111_telemetry_022
    FOREIGN KEY (source_type_id) REFERENCES hidra_telemetry_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_source VALIDATE CONSTRAINT fk_hra111_telemetry_022;

-- hidra_telemetry_trusted_reading.point_id -> hidra_telemetry_point.id
ALTER TABLE hidra_telemetry_trusted_reading
    ADD CONSTRAINT fk_hra111_telemetry_023
    FOREIGN KEY (point_id) REFERENCES hidra_telemetry_point (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_trusted_reading VALIDATE CONSTRAINT fk_hra111_telemetry_023;

-- hidra_telemetry_trusted_reading.quality_assessment_id -> hidra_telemetry_quality_assessment.id
ALTER TABLE hidra_telemetry_trusted_reading
    ADD CONSTRAINT fk_hra111_telemetry_024
    FOREIGN KEY (quality_assessment_id) REFERENCES hidra_telemetry_quality_assessment (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_trusted_reading VALIDATE CONSTRAINT fk_hra111_telemetry_024;

-- hidra_telemetry_trusted_reading.quality_code_id -> hidra_telemetry_type_catalog.id
ALTER TABLE hidra_telemetry_trusted_reading
    ADD CONSTRAINT fk_hra111_telemetry_025
    FOREIGN KEY (quality_code_id) REFERENCES hidra_telemetry_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_trusted_reading VALIDATE CONSTRAINT fk_hra111_telemetry_025;

-- hidra_telemetry_trusted_reading.reading_id -> hidra_telemetry_reading.id
ALTER TABLE hidra_telemetry_trusted_reading
    ADD CONSTRAINT fk_hra111_telemetry_026
    FOREIGN KEY (reading_id) REFERENCES hidra_telemetry_reading (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_trusted_reading VALIDATE CONSTRAINT fk_hra111_telemetry_026;

-- hidra_telemetry_type_translation.type_id -> hidra_telemetry_type_catalog.id
ALTER TABLE hidra_telemetry_type_translation
    ADD CONSTRAINT fk_hra111_telemetry_027
    FOREIGN KEY (type_id) REFERENCES hidra_telemetry_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_type_translation VALIDATE CONSTRAINT fk_hra111_telemetry_027;

-- hidra_telemetry_validation_rule.rule_type_id -> hidra_telemetry_type_catalog.id
ALTER TABLE hidra_telemetry_validation_rule
    ADD CONSTRAINT fk_hra111_telemetry_028
    FOREIGN KEY (rule_type_id) REFERENCES hidra_telemetry_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_telemetry_validation_rule VALIDATE CONSTRAINT fk_hra111_telemetry_028;

-- planning: 25 HRA-111 same-module foreign keys
-- hidra_planning_actual_review_snapshot.plan_target_id -> hidra_planning_plan_target.id
ALTER TABLE hidra_planning_actual_review_snapshot
    ADD CONSTRAINT fk_hra111_planning_001
    FOREIGN KEY (plan_target_id) REFERENCES hidra_planning_plan_target (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_actual_review_snapshot VALIDATE CONSTRAINT fk_hra111_planning_001;

-- hidra_planning_catalog_translation.catalog_entry_id -> hidra_planning_catalog_entry.id
ALTER TABLE hidra_planning_catalog_translation
    ADD CONSTRAINT fk_hra111_planning_002
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_planning_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_catalog_translation VALIDATE CONSTRAINT fk_hra111_planning_002;

-- hidra_planning_expected_flow_state.revision_id -> hidra_planning_plan_revision.id
ALTER TABLE hidra_planning_expected_flow_state
    ADD CONSTRAINT fk_hra111_planning_003
    FOREIGN KEY (revision_id) REFERENCES hidra_planning_plan_revision (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_expected_flow_state VALIDATE CONSTRAINT fk_hra111_planning_003;

-- hidra_planning_forecast_point.forecast_series_id -> hidra_planning_forecast_series.id
ALTER TABLE hidra_planning_forecast_point
    ADD CONSTRAINT fk_hra111_planning_004
    FOREIGN KEY (forecast_series_id) REFERENCES hidra_planning_forecast_series (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_forecast_point VALIDATE CONSTRAINT fk_hra111_planning_004;

-- hidra_planning_forecast_point.unit_id -> hidra_planning_catalog_entry.id
ALTER TABLE hidra_planning_forecast_point
    ADD CONSTRAINT fk_hra111_planning_005
    FOREIGN KEY (unit_id) REFERENCES hidra_planning_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_forecast_point VALIDATE CONSTRAINT fk_hra111_planning_005;

-- hidra_planning_forecast_series.forecast_type_id -> hidra_planning_catalog_entry.id
ALTER TABLE hidra_planning_forecast_series
    ADD CONSTRAINT fk_hra111_planning_006
    FOREIGN KEY (forecast_type_id) REFERENCES hidra_planning_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_forecast_series VALIDATE CONSTRAINT fk_hra111_planning_006;

-- hidra_planning_forecast_series.period_id -> hidra_planning_period.id
ALTER TABLE hidra_planning_forecast_series
    ADD CONSTRAINT fk_hra111_planning_007
    FOREIGN KEY (period_id) REFERENCES hidra_planning_period (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_forecast_series VALIDATE CONSTRAINT fk_hra111_planning_007;

-- hidra_planning_nomination_schedule_line.nomination_id -> hidra_planning_nomination.id
ALTER TABLE hidra_planning_nomination_schedule_line
    ADD CONSTRAINT fk_hra111_planning_008
    FOREIGN KEY (nomination_id) REFERENCES hidra_planning_nomination (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_nomination_schedule_line VALIDATE CONSTRAINT fk_hra111_planning_008;

-- hidra_planning_nomination.nomination_type_id -> hidra_planning_catalog_entry.id
ALTER TABLE hidra_planning_nomination
    ADD CONSTRAINT fk_hra111_planning_009
    FOREIGN KEY (nomination_type_id) REFERENCES hidra_planning_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_nomination VALIDATE CONSTRAINT fk_hra111_planning_009;

-- hidra_planning_nomination.product_type_id -> hidra_planning_catalog_entry.id
ALTER TABLE hidra_planning_nomination
    ADD CONSTRAINT fk_hra111_planning_010
    FOREIGN KEY (product_type_id) REFERENCES hidra_planning_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_nomination VALIDATE CONSTRAINT fk_hra111_planning_010;

-- hidra_planning_nomination.quantity_unit_id -> hidra_planning_catalog_entry.id
ALTER TABLE hidra_planning_nomination
    ADD CONSTRAINT fk_hra111_planning_011
    FOREIGN KEY (quantity_unit_id) REFERENCES hidra_planning_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_nomination VALIDATE CONSTRAINT fk_hra111_planning_011;

-- hidra_planning_nomination.revision_id -> hidra_planning_plan_revision.id
ALTER TABLE hidra_planning_nomination
    ADD CONSTRAINT fk_hra111_planning_012
    FOREIGN KEY (revision_id) REFERENCES hidra_planning_plan_revision (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_nomination VALIDATE CONSTRAINT fk_hra111_planning_012;

-- hidra_planning_operation_window.revision_id -> hidra_planning_plan_revision.id
ALTER TABLE hidra_planning_operation_window
    ADD CONSTRAINT fk_hra111_planning_013
    FOREIGN KEY (revision_id) REFERENCES hidra_planning_plan_revision (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_operation_window VALIDATE CONSTRAINT fk_hra111_planning_013;

-- hidra_planning_operation_window.window_type_id -> hidra_planning_catalog_entry.id
ALTER TABLE hidra_planning_operation_window
    ADD CONSTRAINT fk_hra111_planning_014
    FOREIGN KEY (window_type_id) REFERENCES hidra_planning_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_operation_window VALIDATE CONSTRAINT fk_hra111_planning_014;

-- hidra_planning_operational_plan.period_id -> hidra_planning_period.id
ALTER TABLE hidra_planning_operational_plan
    ADD CONSTRAINT fk_hra111_planning_015
    FOREIGN KEY (period_id) REFERENCES hidra_planning_period (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_operational_plan VALIDATE CONSTRAINT fk_hra111_planning_015;

-- hidra_planning_operational_plan.plan_type_id -> hidra_planning_catalog_entry.id
ALTER TABLE hidra_planning_operational_plan
    ADD CONSTRAINT fk_hra111_planning_016
    FOREIGN KEY (plan_type_id) REFERENCES hidra_planning_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_operational_plan VALIDATE CONSTRAINT fk_hra111_planning_016;

-- hidra_planning_period.period_type_id -> hidra_planning_catalog_entry.id
ALTER TABLE hidra_planning_period
    ADD CONSTRAINT fk_hra111_planning_017
    FOREIGN KEY (period_type_id) REFERENCES hidra_planning_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_period VALIDATE CONSTRAINT fk_hra111_planning_017;

-- hidra_planning_plan_approval_reference.revision_id -> hidra_planning_plan_revision.id
ALTER TABLE hidra_planning_plan_approval_reference
    ADD CONSTRAINT fk_hra111_planning_018
    FOREIGN KEY (revision_id) REFERENCES hidra_planning_plan_revision (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_plan_approval_reference VALIDATE CONSTRAINT fk_hra111_planning_018;

-- hidra_planning_plan_constraint.constraint_type_id -> hidra_planning_catalog_entry.id
ALTER TABLE hidra_planning_plan_constraint
    ADD CONSTRAINT fk_hra111_planning_019
    FOREIGN KEY (constraint_type_id) REFERENCES hidra_planning_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_plan_constraint VALIDATE CONSTRAINT fk_hra111_planning_019;

-- hidra_planning_plan_constraint.revision_id -> hidra_planning_plan_revision.id
ALTER TABLE hidra_planning_plan_constraint
    ADD CONSTRAINT fk_hra111_planning_020
    FOREIGN KEY (revision_id) REFERENCES hidra_planning_plan_revision (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_plan_constraint VALIDATE CONSTRAINT fk_hra111_planning_020;

-- hidra_planning_plan_revision.plan_id -> hidra_planning_operational_plan.id
ALTER TABLE hidra_planning_plan_revision
    ADD CONSTRAINT fk_hra111_planning_021
    FOREIGN KEY (plan_id) REFERENCES hidra_planning_operational_plan (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_plan_revision VALIDATE CONSTRAINT fk_hra111_planning_021;

-- hidra_planning_plan_scenario.revision_id -> hidra_planning_plan_revision.id
ALTER TABLE hidra_planning_plan_scenario
    ADD CONSTRAINT fk_hra111_planning_022
    FOREIGN KEY (revision_id) REFERENCES hidra_planning_plan_revision (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_plan_scenario VALIDATE CONSTRAINT fk_hra111_planning_022;

-- hidra_planning_plan_scenario.scenario_type_id -> hidra_planning_catalog_entry.id
ALTER TABLE hidra_planning_plan_scenario
    ADD CONSTRAINT fk_hra111_planning_023
    FOREIGN KEY (scenario_type_id) REFERENCES hidra_planning_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_plan_scenario VALIDATE CONSTRAINT fk_hra111_planning_023;

-- hidra_planning_plan_target.revision_id -> hidra_planning_plan_revision.id
ALTER TABLE hidra_planning_plan_target
    ADD CONSTRAINT fk_hra111_planning_024
    FOREIGN KEY (revision_id) REFERENCES hidra_planning_plan_revision (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_plan_target VALIDATE CONSTRAINT fk_hra111_planning_024;

-- hidra_planning_plan_target.target_type_id -> hidra_planning_catalog_entry.id
ALTER TABLE hidra_planning_plan_target
    ADD CONSTRAINT fk_hra111_planning_025
    FOREIGN KEY (target_type_id) REFERENCES hidra_planning_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_planning_plan_target VALIDATE CONSTRAINT fk_hra111_planning_025;

-- monitoring: 5 HRA-111 same-module foreign keys
-- hidra_monitoring_alert_candidate.candidate_type_id -> hidra_monitoring_catalog_entry.id
ALTER TABLE hidra_monitoring_alert_candidate
    ADD CONSTRAINT fk_hra111_monitoring_001
    FOREIGN KEY (candidate_type_id) REFERENCES hidra_monitoring_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_monitoring_alert_candidate VALIDATE CONSTRAINT fk_hra111_monitoring_001;

-- hidra_monitoring_catalog_translation.catalog_entry_id -> hidra_monitoring_catalog_entry.id
ALTER TABLE hidra_monitoring_catalog_translation
    ADD CONSTRAINT fk_hra111_monitoring_002
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_monitoring_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_monitoring_catalog_translation VALIDATE CONSTRAINT fk_hra111_monitoring_002;

-- hidra_monitoring_operational_state_snapshot.operational_state_id -> hidra_monitoring_operational_state.id
ALTER TABLE hidra_monitoring_operational_state_snapshot
    ADD CONSTRAINT fk_hra111_monitoring_003
    FOREIGN KEY (operational_state_id) REFERENCES hidra_monitoring_operational_state (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_monitoring_operational_state_snapshot VALIDATE CONSTRAINT fk_hra111_monitoring_003;

-- hidra_monitoring_risk_signal.risk_type_id -> hidra_monitoring_catalog_entry.id
ALTER TABLE hidra_monitoring_risk_signal
    ADD CONSTRAINT fk_hra111_monitoring_004
    FOREIGN KEY (risk_type_id) REFERENCES hidra_monitoring_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_monitoring_risk_signal VALIDATE CONSTRAINT fk_hra111_monitoring_004;

-- hidra_monitoring_threshold.rule_id -> hidra_monitoring_rule.id
ALTER TABLE hidra_monitoring_threshold
    ADD CONSTRAINT fk_hra111_monitoring_005
    FOREIGN KEY (rule_id) REFERENCES hidra_monitoring_rule (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_monitoring_threshold VALIDATE CONSTRAINT fk_hra111_monitoring_005;

-- alarm: 14 HRA-111 same-module foreign keys
-- hidra_alarm_acknowledgement.alarm_id -> hidra_alarm.id
ALTER TABLE hidra_alarm_acknowledgement
    ADD CONSTRAINT fk_hra111_alarm_001
    FOREIGN KEY (alarm_id) REFERENCES hidra_alarm (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_alarm_acknowledgement VALIDATE CONSTRAINT fk_hra111_alarm_001;

-- hidra_alarm_catalog_translation.catalog_entry_id -> hidra_alarm_catalog_entry.id
ALTER TABLE hidra_alarm_catalog_translation
    ADD CONSTRAINT fk_hra111_alarm_002
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_alarm_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_alarm_catalog_translation VALIDATE CONSTRAINT fk_hra111_alarm_002;

-- hidra_alarm_closure.alarm_id -> hidra_alarm.id
ALTER TABLE hidra_alarm_closure
    ADD CONSTRAINT fk_hra111_alarm_003
    FOREIGN KEY (alarm_id) REFERENCES hidra_alarm (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_alarm_closure VALIDATE CONSTRAINT fk_hra111_alarm_003;

-- hidra_alarm_comment.alarm_id -> hidra_alarm.id
ALTER TABLE hidra_alarm_comment
    ADD CONSTRAINT fk_hra111_alarm_004
    FOREIGN KEY (alarm_id) REFERENCES hidra_alarm (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_alarm_comment VALIDATE CONSTRAINT fk_hra111_alarm_004;

-- hidra_alarm_escalation.alarm_id -> hidra_alarm.id
ALTER TABLE hidra_alarm_escalation
    ADD CONSTRAINT fk_hra111_alarm_005
    FOREIGN KEY (alarm_id) REFERENCES hidra_alarm (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_alarm_escalation VALIDATE CONSTRAINT fk_hra111_alarm_005;

-- hidra_alarm_evidence_link.alarm_id -> hidra_alarm.id
ALTER TABLE hidra_alarm_evidence_link
    ADD CONSTRAINT fk_hra111_alarm_006
    FOREIGN KEY (alarm_id) REFERENCES hidra_alarm (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_alarm_evidence_link VALIDATE CONSTRAINT fk_hra111_alarm_006;

-- hidra_alarm_lifecycle_event.alarm_id -> hidra_alarm.id
ALTER TABLE hidra_alarm_lifecycle_event
    ADD CONSTRAINT fk_hra111_alarm_007
    FOREIGN KEY (alarm_id) REFERENCES hidra_alarm (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_alarm_lifecycle_event VALIDATE CONSTRAINT fk_hra111_alarm_007;

-- hidra_alarm_rule_binding.alarm_type_id -> hidra_alarm_catalog_entry.id
ALTER TABLE hidra_alarm_rule_binding
    ADD CONSTRAINT fk_hra111_alarm_008
    FOREIGN KEY (alarm_type_id) REFERENCES hidra_alarm_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_alarm_rule_binding VALIDATE CONSTRAINT fk_hra111_alarm_008;

-- hidra_alarm_rule_binding.default_severity_id -> hidra_alarm_catalog_entry.id
ALTER TABLE hidra_alarm_rule_binding
    ADD CONSTRAINT fk_hra111_alarm_009
    FOREIGN KEY (default_severity_id) REFERENCES hidra_alarm_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_alarm_rule_binding VALIDATE CONSTRAINT fk_hra111_alarm_009;

-- hidra_alarm_shelving.alarm_id -> hidra_alarm.id
ALTER TABLE hidra_alarm_shelving
    ADD CONSTRAINT fk_hra111_alarm_010
    FOREIGN KEY (alarm_id) REFERENCES hidra_alarm (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_alarm_shelving VALIDATE CONSTRAINT fk_hra111_alarm_010;

-- hidra_alarm_shelving.shelving_reason_id -> hidra_alarm_catalog_entry.id
ALTER TABLE hidra_alarm_shelving
    ADD CONSTRAINT fk_hra111_alarm_011
    FOREIGN KEY (shelving_reason_id) REFERENCES hidra_alarm_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_alarm_shelving VALIDATE CONSTRAINT fk_hra111_alarm_011;

-- hidra_alarm_suppression.suppression_reason_id -> hidra_alarm_catalog_entry.id
ALTER TABLE hidra_alarm_suppression
    ADD CONSTRAINT fk_hra111_alarm_012
    FOREIGN KEY (suppression_reason_id) REFERENCES hidra_alarm_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_alarm_suppression VALIDATE CONSTRAINT fk_hra111_alarm_012;

-- hidra_alarm.alarm_type_id -> hidra_alarm_catalog_entry.id
ALTER TABLE hidra_alarm
    ADD CONSTRAINT fk_hra111_alarm_013
    FOREIGN KEY (alarm_type_id) REFERENCES hidra_alarm_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_alarm VALIDATE CONSTRAINT fk_hra111_alarm_013;

-- hidra_alarm.severity_id -> hidra_alarm_catalog_entry.id
ALTER TABLE hidra_alarm
    ADD CONSTRAINT fk_hra111_alarm_014
    FOREIGN KEY (severity_id) REFERENCES hidra_alarm_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_alarm VALIDATE CONSTRAINT fk_hra111_alarm_014;

-- leakdetection: 15 HRA-111 same-module foreign keys
-- hidra_leak_detection_candidate.profile_id -> hidra_leak_detection_profile.id
ALTER TABLE hidra_leak_detection_candidate
    ADD CONSTRAINT fk_hra111_leakdetection_001
    FOREIGN KEY (profile_id) REFERENCES hidra_leak_detection_profile (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_leak_detection_candidate VALIDATE CONSTRAINT fk_hra111_leakdetection_001;

-- hidra_leak_detection_case_status_history.case_id -> hidra_leak_detection_case.id
ALTER TABLE hidra_leak_detection_case_status_history
    ADD CONSTRAINT fk_hra111_leakdetection_002
    FOREIGN KEY (case_id) REFERENCES hidra_leak_detection_case (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_leak_detection_case_status_history VALIDATE CONSTRAINT fk_hra111_leakdetection_002;

-- hidra_leak_detection_case.primary_candidate_id -> hidra_leak_detection_candidate.id
ALTER TABLE hidra_leak_detection_case
    ADD CONSTRAINT fk_hra111_leakdetection_003
    FOREIGN KEY (primary_candidate_id) REFERENCES hidra_leak_detection_candidate (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_leak_detection_case VALIDATE CONSTRAINT fk_hra111_leakdetection_003;

-- hidra_leak_detection_escalation_reference.case_id -> hidra_leak_detection_case.id
ALTER TABLE hidra_leak_detection_escalation_reference
    ADD CONSTRAINT fk_hra111_leakdetection_004
    FOREIGN KEY (case_id) REFERENCES hidra_leak_detection_case (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_leak_detection_escalation_reference VALIDATE CONSTRAINT fk_hra111_leakdetection_004;

-- hidra_leak_detection_evidence_link.candidate_id -> hidra_leak_detection_candidate.id
ALTER TABLE hidra_leak_detection_evidence_link
    ADD CONSTRAINT fk_hra111_leakdetection_005
    FOREIGN KEY (candidate_id) REFERENCES hidra_leak_detection_candidate (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_leak_detection_evidence_link VALIDATE CONSTRAINT fk_hra111_leakdetection_005;

-- hidra_leak_detection_localization_estimate.candidate_id -> hidra_leak_detection_candidate.id
ALTER TABLE hidra_leak_detection_localization_estimate
    ADD CONSTRAINT fk_hra111_leakdetection_006
    FOREIGN KEY (candidate_id) REFERENCES hidra_leak_detection_candidate (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_leak_detection_localization_estimate VALIDATE CONSTRAINT fk_hra111_leakdetection_006;

-- hidra_leak_detection_localization_estimate.method_id -> hidra_leak_detection_method.id
ALTER TABLE hidra_leak_detection_localization_estimate
    ADD CONSTRAINT fk_hra111_leakdetection_007
    FOREIGN KEY (method_id) REFERENCES hidra_leak_detection_method (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_leak_detection_localization_estimate VALIDATE CONSTRAINT fk_hra111_leakdetection_007;

-- hidra_leak_detection_method_translation.method_id -> hidra_leak_detection_method.id
ALTER TABLE hidra_leak_detection_method_translation
    ADD CONSTRAINT fk_hra111_leakdetection_008
    FOREIGN KEY (method_id) REFERENCES hidra_leak_detection_method (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_leak_detection_method_translation VALIDATE CONSTRAINT fk_hra111_leakdetection_008;

-- hidra_leak_detection_profile.method_id -> hidra_leak_detection_method.id
ALTER TABLE hidra_leak_detection_profile
    ADD CONSTRAINT fk_hra111_leakdetection_009
    FOREIGN KEY (method_id) REFERENCES hidra_leak_detection_method (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_leak_detection_profile VALIDATE CONSTRAINT fk_hra111_leakdetection_009;

-- hidra_leak_detection_rule.method_id -> hidra_leak_detection_method.id
ALTER TABLE hidra_leak_detection_rule
    ADD CONSTRAINT fk_hra111_leakdetection_010
    FOREIGN KEY (method_id) REFERENCES hidra_leak_detection_method (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_leak_detection_rule VALIDATE CONSTRAINT fk_hra111_leakdetection_010;

-- hidra_leak_detection_rule.profile_id -> hidra_leak_detection_profile.id
ALTER TABLE hidra_leak_detection_rule
    ADD CONSTRAINT fk_hra111_leakdetection_011
    FOREIGN KEY (profile_id) REFERENCES hidra_leak_detection_profile (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_leak_detection_rule VALIDATE CONSTRAINT fk_hra111_leakdetection_011;

-- hidra_leak_detection_run.method_id -> hidra_leak_detection_method.id
ALTER TABLE hidra_leak_detection_run
    ADD CONSTRAINT fk_hra111_leakdetection_012
    FOREIGN KEY (method_id) REFERENCES hidra_leak_detection_method (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_leak_detection_run VALIDATE CONSTRAINT fk_hra111_leakdetection_012;

-- hidra_leak_detection_run.profile_id -> hidra_leak_detection_profile.id
ALTER TABLE hidra_leak_detection_run
    ADD CONSTRAINT fk_hra111_leakdetection_013
    FOREIGN KEY (profile_id) REFERENCES hidra_leak_detection_profile (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_leak_detection_run VALIDATE CONSTRAINT fk_hra111_leakdetection_013;

-- hidra_leak_detection_severity_assessment.candidate_id -> hidra_leak_detection_candidate.id
ALTER TABLE hidra_leak_detection_severity_assessment
    ADD CONSTRAINT fk_hra111_leakdetection_014
    FOREIGN KEY (candidate_id) REFERENCES hidra_leak_detection_candidate (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_leak_detection_severity_assessment VALIDATE CONSTRAINT fk_hra111_leakdetection_014;

-- hidra_leak_detection_verification_action.candidate_id -> hidra_leak_detection_candidate.id
ALTER TABLE hidra_leak_detection_verification_action
    ADD CONSTRAINT fk_hra111_leakdetection_015
    FOREIGN KEY (candidate_id) REFERENCES hidra_leak_detection_candidate (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_leak_detection_verification_action VALIDATE CONSTRAINT fk_hra111_leakdetection_015;

-- incident: 24 HRA-111 same-module foreign keys
-- hidra_incident_assignment.assignment_type_id -> hidra_incident_catalog_entry.id
ALTER TABLE hidra_incident_assignment
    ADD CONSTRAINT fk_hra111_incident_001
    FOREIGN KEY (assignment_type_id) REFERENCES hidra_incident_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_assignment VALIDATE CONSTRAINT fk_hra111_incident_001;

-- hidra_incident_assignment.incident_id -> hidra_incident.id
ALTER TABLE hidra_incident_assignment
    ADD CONSTRAINT fk_hra111_incident_002
    FOREIGN KEY (incident_id) REFERENCES hidra_incident (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_assignment VALIDATE CONSTRAINT fk_hra111_incident_002;

-- hidra_incident_attachment_reference.incident_id -> hidra_incident.id
ALTER TABLE hidra_incident_attachment_reference
    ADD CONSTRAINT fk_hra111_incident_003
    FOREIGN KEY (incident_id) REFERENCES hidra_incident (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_attachment_reference VALIDATE CONSTRAINT fk_hra111_incident_003;

-- hidra_incident_catalog_translation.catalog_entry_id -> hidra_incident_catalog_entry.id
ALTER TABLE hidra_incident_catalog_translation
    ADD CONSTRAINT fk_hra111_incident_004
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_incident_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_catalog_translation VALIDATE CONSTRAINT fk_hra111_incident_004;

-- hidra_incident_closure.incident_id -> hidra_incident.id
ALTER TABLE hidra_incident_closure
    ADD CONSTRAINT fk_hra111_incident_005
    FOREIGN KEY (incident_id) REFERENCES hidra_incident (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_closure VALIDATE CONSTRAINT fk_hra111_incident_005;

-- hidra_incident_escalation.incident_id -> hidra_incident.id
ALTER TABLE hidra_incident_escalation
    ADD CONSTRAINT fk_hra111_incident_006
    FOREIGN KEY (incident_id) REFERENCES hidra_incident (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_escalation VALIDATE CONSTRAINT fk_hra111_incident_006;

-- hidra_incident_escalation.reason_id -> hidra_incident_catalog_entry.id
ALTER TABLE hidra_incident_escalation
    ADD CONSTRAINT fk_hra111_incident_007
    FOREIGN KEY (reason_id) REFERENCES hidra_incident_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_escalation VALIDATE CONSTRAINT fk_hra111_incident_007;

-- hidra_incident_evidence_link.incident_id -> hidra_incident.id
ALTER TABLE hidra_incident_evidence_link
    ADD CONSTRAINT fk_hra111_incident_008
    FOREIGN KEY (incident_id) REFERENCES hidra_incident (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_evidence_link VALIDATE CONSTRAINT fk_hra111_incident_008;

-- hidra_incident_impact_assessment.impact_level_id -> hidra_incident_catalog_entry.id
ALTER TABLE hidra_incident_impact_assessment
    ADD CONSTRAINT fk_hra111_incident_009
    FOREIGN KEY (impact_level_id) REFERENCES hidra_incident_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_impact_assessment VALIDATE CONSTRAINT fk_hra111_incident_009;

-- hidra_incident_impact_assessment.impact_type_id -> hidra_incident_catalog_entry.id
ALTER TABLE hidra_incident_impact_assessment
    ADD CONSTRAINT fk_hra111_incident_010
    FOREIGN KEY (impact_type_id) REFERENCES hidra_incident_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_impact_assessment VALIDATE CONSTRAINT fk_hra111_incident_010;

-- hidra_incident_impact_assessment.incident_id -> hidra_incident.id
ALTER TABLE hidra_incident_impact_assessment
    ADD CONSTRAINT fk_hra111_incident_011
    FOREIGN KEY (incident_id) REFERENCES hidra_incident (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_impact_assessment VALIDATE CONSTRAINT fk_hra111_incident_011;

-- hidra_incident_related_incident.incident_id -> hidra_incident.id
ALTER TABLE hidra_incident_related_incident
    ADD CONSTRAINT fk_hra111_incident_012
    FOREIGN KEY (incident_id) REFERENCES hidra_incident (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_related_incident VALIDATE CONSTRAINT fk_hra111_incident_012;

-- hidra_incident_related_incident.related_incident_id -> hidra_incident_catalog_entry.id
ALTER TABLE hidra_incident_related_incident
    ADD CONSTRAINT fk_hra111_incident_013
    FOREIGN KEY (related_incident_id) REFERENCES hidra_incident_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_related_incident VALIDATE CONSTRAINT fk_hra111_incident_013;

-- hidra_incident_related_incident.relationship_type_id -> hidra_incident_catalog_entry.id
ALTER TABLE hidra_incident_related_incident
    ADD CONSTRAINT fk_hra111_incident_014
    FOREIGN KEY (relationship_type_id) REFERENCES hidra_incident_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_related_incident VALIDATE CONSTRAINT fk_hra111_incident_014;

-- hidra_incident_resolution.incident_id -> hidra_incident.id
ALTER TABLE hidra_incident_resolution
    ADD CONSTRAINT fk_hra111_incident_015
    FOREIGN KEY (incident_id) REFERENCES hidra_incident (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_resolution VALIDATE CONSTRAINT fk_hra111_incident_015;

-- hidra_incident_resolution.resolution_type_id -> hidra_incident_catalog_entry.id
ALTER TABLE hidra_incident_resolution
    ADD CONSTRAINT fk_hra111_incident_016
    FOREIGN KEY (resolution_type_id) REFERENCES hidra_incident_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_resolution VALIDATE CONSTRAINT fk_hra111_incident_016;

-- hidra_incident_response_action.action_type_id -> hidra_incident_catalog_entry.id
ALTER TABLE hidra_incident_response_action
    ADD CONSTRAINT fk_hra111_incident_017
    FOREIGN KEY (action_type_id) REFERENCES hidra_incident_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_response_action VALIDATE CONSTRAINT fk_hra111_incident_017;

-- hidra_incident_response_action.incident_id -> hidra_incident.id
ALTER TABLE hidra_incident_response_action
    ADD CONSTRAINT fk_hra111_incident_018
    FOREIGN KEY (incident_id) REFERENCES hidra_incident (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_response_action VALIDATE CONSTRAINT fk_hra111_incident_018;

-- hidra_incident_root_cause_analysis.incident_id -> hidra_incident.id
ALTER TABLE hidra_incident_root_cause_analysis
    ADD CONSTRAINT fk_hra111_incident_019
    FOREIGN KEY (incident_id) REFERENCES hidra_incident (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_root_cause_analysis VALIDATE CONSTRAINT fk_hra111_incident_019;

-- hidra_incident_root_cause_analysis.root_cause_category_id -> hidra_incident_catalog_entry.id
ALTER TABLE hidra_incident_root_cause_analysis
    ADD CONSTRAINT fk_hra111_incident_020
    FOREIGN KEY (root_cause_category_id) REFERENCES hidra_incident_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_root_cause_analysis VALIDATE CONSTRAINT fk_hra111_incident_020;

-- hidra_incident_timeline_entry.entry_type_id -> hidra_incident_catalog_entry.id
ALTER TABLE hidra_incident_timeline_entry
    ADD CONSTRAINT fk_hra111_incident_021
    FOREIGN KEY (entry_type_id) REFERENCES hidra_incident_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_timeline_entry VALIDATE CONSTRAINT fk_hra111_incident_021;

-- hidra_incident_timeline_entry.incident_id -> hidra_incident.id
ALTER TABLE hidra_incident_timeline_entry
    ADD CONSTRAINT fk_hra111_incident_022
    FOREIGN KEY (incident_id) REFERENCES hidra_incident (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident_timeline_entry VALIDATE CONSTRAINT fk_hra111_incident_022;

-- hidra_incident.classification_id -> hidra_incident_catalog_entry.id
ALTER TABLE hidra_incident
    ADD CONSTRAINT fk_hra111_incident_023
    FOREIGN KEY (classification_id) REFERENCES hidra_incident_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident VALIDATE CONSTRAINT fk_hra111_incident_023;

-- hidra_incident.severity_id -> hidra_incident_catalog_entry.id
ALTER TABLE hidra_incident
    ADD CONSTRAINT fk_hra111_incident_024
    FOREIGN KEY (severity_id) REFERENCES hidra_incident_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_incident VALIDATE CONSTRAINT fk_hra111_incident_024;

-- risk: 44 HRA-111 same-module foreign keys
-- hidra_residual_risk_assessment.residual_consequence_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_residual_risk_assessment
    ADD CONSTRAINT fk_hra111_risk_001
    FOREIGN KEY (residual_consequence_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_residual_risk_assessment VALIDATE CONSTRAINT fk_hra111_risk_001;

-- hidra_residual_risk_assessment.residual_likelihood_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_residual_risk_assessment
    ADD CONSTRAINT fk_hra111_risk_002
    FOREIGN KEY (residual_likelihood_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_residual_risk_assessment VALIDATE CONSTRAINT fk_hra111_risk_002;

-- hidra_residual_risk_assessment.residual_rating_id -> hidra_risk_rating.id
ALTER TABLE hidra_residual_risk_assessment
    ADD CONSTRAINT fk_hra111_risk_003
    FOREIGN KEY (residual_rating_id) REFERENCES hidra_risk_rating (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_residual_risk_assessment VALIDATE CONSTRAINT fk_hra111_risk_003;

-- hidra_residual_risk_assessment.risk_assessment_id -> hidra_risk_assessment.id
ALTER TABLE hidra_residual_risk_assessment
    ADD CONSTRAINT fk_hra111_risk_004
    FOREIGN KEY (risk_assessment_id) REFERENCES hidra_risk_assessment (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_residual_risk_assessment VALIDATE CONSTRAINT fk_hra111_risk_004;

-- hidra_risk_acceptance.acceptance_reason_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_acceptance
    ADD CONSTRAINT fk_hra111_risk_005
    FOREIGN KEY (acceptance_reason_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_acceptance VALIDATE CONSTRAINT fk_hra111_risk_005;

-- hidra_risk_acceptance.accepted_rating_id -> hidra_risk_rating.id
ALTER TABLE hidra_risk_acceptance
    ADD CONSTRAINT fk_hra111_risk_006
    FOREIGN KEY (accepted_rating_id) REFERENCES hidra_risk_rating (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_acceptance VALIDATE CONSTRAINT fk_hra111_risk_006;

-- hidra_risk_acceptance.risk_assessment_id -> hidra_risk_assessment.id
ALTER TABLE hidra_risk_acceptance
    ADD CONSTRAINT fk_hra111_risk_007
    FOREIGN KEY (risk_assessment_id) REFERENCES hidra_risk_assessment (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_acceptance VALIDATE CONSTRAINT fk_hra111_risk_007;

-- hidra_risk_assessment_scope.risk_assessment_id -> hidra_risk_assessment.id
ALTER TABLE hidra_risk_assessment_scope
    ADD CONSTRAINT fk_hra111_risk_008
    FOREIGN KEY (risk_assessment_id) REFERENCES hidra_risk_assessment (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_assessment_scope VALIDATE CONSTRAINT fk_hra111_risk_008;

-- hidra_risk_assessment.assessment_type_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_assessment
    ADD CONSTRAINT fk_hra111_risk_009
    FOREIGN KEY (assessment_type_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_assessment VALIDATE CONSTRAINT fk_hra111_risk_009;

-- hidra_risk_assessment.methodology_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_assessment
    ADD CONSTRAINT fk_hra111_risk_010
    FOREIGN KEY (methodology_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_assessment VALIDATE CONSTRAINT fk_hra111_risk_010;

-- hidra_risk_assessment.risk_register_id -> hidra_risk_register.id
ALTER TABLE hidra_risk_assessment
    ADD CONSTRAINT fk_hra111_risk_011
    FOREIGN KEY (risk_register_id) REFERENCES hidra_risk_register (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_assessment VALIDATE CONSTRAINT fk_hra111_risk_011;

-- hidra_risk_assessment.risk_scenario_id -> hidra_risk_scenario.id
ALTER TABLE hidra_risk_assessment
    ADD CONSTRAINT fk_hra111_risk_012
    FOREIGN KEY (risk_scenario_id) REFERENCES hidra_risk_scenario (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_assessment VALIDATE CONSTRAINT fk_hra111_risk_012;

-- hidra_risk_catalog_translation.catalog_entry_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_catalog_translation
    ADD CONSTRAINT fk_hra111_risk_013
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_catalog_translation VALIDATE CONSTRAINT fk_hra111_risk_013;

-- hidra_risk_consequence.category_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_consequence
    ADD CONSTRAINT fk_hra111_risk_014
    FOREIGN KEY (category_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_consequence VALIDATE CONSTRAINT fk_hra111_risk_014;

-- hidra_risk_consequence.consequence_level_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_consequence
    ADD CONSTRAINT fk_hra111_risk_015
    FOREIGN KEY (consequence_level_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_consequence VALIDATE CONSTRAINT fk_hra111_risk_015;

-- hidra_risk_consequence.risk_assessment_id -> hidra_risk_assessment.id
ALTER TABLE hidra_risk_consequence
    ADD CONSTRAINT fk_hra111_risk_016
    FOREIGN KEY (risk_assessment_id) REFERENCES hidra_risk_assessment (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_consequence VALIDATE CONSTRAINT fk_hra111_risk_016;

-- hidra_risk_control.control_type_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_control
    ADD CONSTRAINT fk_hra111_risk_017
    FOREIGN KEY (control_type_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_control VALIDATE CONSTRAINT fk_hra111_risk_017;

-- hidra_risk_control.risk_assessment_id -> hidra_risk_assessment.id
ALTER TABLE hidra_risk_control
    ADD CONSTRAINT fk_hra111_risk_018
    FOREIGN KEY (risk_assessment_id) REFERENCES hidra_risk_assessment (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_control VALIDATE CONSTRAINT fk_hra111_risk_018;

-- hidra_risk_evidence_link.risk_assessment_id -> hidra_risk_assessment.id
ALTER TABLE hidra_risk_evidence_link
    ADD CONSTRAINT fk_hra111_risk_019
    FOREIGN KEY (risk_assessment_id) REFERENCES hidra_risk_assessment (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_evidence_link VALIDATE CONSTRAINT fk_hra111_risk_019;

-- hidra_risk_exposure.exposure_type_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_exposure
    ADD CONSTRAINT fk_hra111_risk_020
    FOREIGN KEY (exposure_type_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_exposure VALIDATE CONSTRAINT fk_hra111_risk_020;

-- hidra_risk_exposure.risk_assessment_id -> hidra_risk_assessment.id
ALTER TABLE hidra_risk_exposure
    ADD CONSTRAINT fk_hra111_risk_021
    FOREIGN KEY (risk_assessment_id) REFERENCES hidra_risk_assessment (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_exposure VALIDATE CONSTRAINT fk_hra111_risk_021;

-- hidra_risk_likelihood.likelihood_level_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_likelihood
    ADD CONSTRAINT fk_hra111_risk_022
    FOREIGN KEY (likelihood_level_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_likelihood VALIDATE CONSTRAINT fk_hra111_risk_022;

-- hidra_risk_likelihood.risk_assessment_id -> hidra_risk_assessment.id
ALTER TABLE hidra_risk_likelihood
    ADD CONSTRAINT fk_hra111_risk_023
    FOREIGN KEY (risk_assessment_id) REFERENCES hidra_risk_assessment (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_likelihood VALIDATE CONSTRAINT fk_hra111_risk_023;

-- hidra_risk_matrix_cell.consequence_level_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_matrix_cell
    ADD CONSTRAINT fk_hra111_risk_024
    FOREIGN KEY (consequence_level_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_matrix_cell VALIDATE CONSTRAINT fk_hra111_risk_024;

-- hidra_risk_matrix_cell.likelihood_level_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_matrix_cell
    ADD CONSTRAINT fk_hra111_risk_025
    FOREIGN KEY (likelihood_level_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_matrix_cell VALIDATE CONSTRAINT fk_hra111_risk_025;

-- hidra_risk_matrix_cell.rating_id -> hidra_risk_rating.id
ALTER TABLE hidra_risk_matrix_cell
    ADD CONSTRAINT fk_hra111_risk_026
    FOREIGN KEY (rating_id) REFERENCES hidra_risk_rating (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_matrix_cell VALIDATE CONSTRAINT fk_hra111_risk_026;

-- hidra_risk_matrix_cell.risk_matrix_id -> hidra_risk_matrix.id
ALTER TABLE hidra_risk_matrix_cell
    ADD CONSTRAINT fk_hra111_risk_027
    FOREIGN KEY (risk_matrix_id) REFERENCES hidra_risk_matrix (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_matrix_cell VALIDATE CONSTRAINT fk_hra111_risk_027;

-- hidra_risk_matrix.matrix_type_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_matrix
    ADD CONSTRAINT fk_hra111_risk_028
    FOREIGN KEY (matrix_type_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_matrix VALIDATE CONSTRAINT fk_hra111_risk_028;

-- hidra_risk_mitigation_measure.mitigation_type_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_mitigation_measure
    ADD CONSTRAINT fk_hra111_risk_029
    FOREIGN KEY (mitigation_type_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_mitigation_measure VALIDATE CONSTRAINT fk_hra111_risk_029;

-- hidra_risk_register.register_type_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_register
    ADD CONSTRAINT fk_hra111_risk_030
    FOREIGN KEY (register_type_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_register VALIDATE CONSTRAINT fk_hra111_risk_030;

-- hidra_risk_review.review_type_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_review
    ADD CONSTRAINT fk_hra111_risk_031
    FOREIGN KEY (review_type_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_review VALIDATE CONSTRAINT fk_hra111_risk_031;

-- hidra_risk_review.risk_assessment_id -> hidra_risk_assessment.id
ALTER TABLE hidra_risk_review
    ADD CONSTRAINT fk_hra111_risk_032
    FOREIGN KEY (risk_assessment_id) REFERENCES hidra_risk_assessment (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_review VALIDATE CONSTRAINT fk_hra111_risk_032;

-- hidra_risk_scenario.scenario_type_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_scenario
    ADD CONSTRAINT fk_hra111_risk_033
    FOREIGN KEY (scenario_type_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_scenario VALIDATE CONSTRAINT fk_hra111_risk_033;

-- hidra_risk_score.consequence_level_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_score
    ADD CONSTRAINT fk_hra111_risk_034
    FOREIGN KEY (consequence_level_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_score VALIDATE CONSTRAINT fk_hra111_risk_034;

-- hidra_risk_score.likelihood_level_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_score
    ADD CONSTRAINT fk_hra111_risk_035
    FOREIGN KEY (likelihood_level_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_score VALIDATE CONSTRAINT fk_hra111_risk_035;

-- hidra_risk_score.rating_id -> hidra_risk_rating.id
ALTER TABLE hidra_risk_score
    ADD CONSTRAINT fk_hra111_risk_036
    FOREIGN KEY (rating_id) REFERENCES hidra_risk_rating (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_score VALIDATE CONSTRAINT fk_hra111_risk_036;

-- hidra_risk_score.risk_assessment_id -> hidra_risk_assessment.id
ALTER TABLE hidra_risk_score
    ADD CONSTRAINT fk_hra111_risk_037
    FOREIGN KEY (risk_assessment_id) REFERENCES hidra_risk_assessment (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_score VALIDATE CONSTRAINT fk_hra111_risk_037;

-- hidra_risk_score.risk_matrix_id -> hidra_risk_matrix.id
ALTER TABLE hidra_risk_score
    ADD CONSTRAINT fk_hra111_risk_038
    FOREIGN KEY (risk_matrix_id) REFERENCES hidra_risk_matrix (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_score VALIDATE CONSTRAINT fk_hra111_risk_038;

-- hidra_risk_source.risk_assessment_id -> hidra_risk_assessment.id
ALTER TABLE hidra_risk_source
    ADD CONSTRAINT fk_hra111_risk_039
    FOREIGN KEY (risk_assessment_id) REFERENCES hidra_risk_assessment (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_source VALIDATE CONSTRAINT fk_hra111_risk_039;

-- hidra_risk_threat.threat_category_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_threat
    ADD CONSTRAINT fk_hra111_risk_040
    FOREIGN KEY (threat_category_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_threat VALIDATE CONSTRAINT fk_hra111_risk_040;

-- hidra_risk_treatment_action.action_type_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_treatment_action
    ADD CONSTRAINT fk_hra111_risk_041
    FOREIGN KEY (action_type_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_treatment_action VALIDATE CONSTRAINT fk_hra111_risk_041;

-- hidra_risk_treatment_action.risk_treatment_plan_id -> hidra_risk_treatment_plan.id
ALTER TABLE hidra_risk_treatment_action
    ADD CONSTRAINT fk_hra111_risk_042
    FOREIGN KEY (risk_treatment_plan_id) REFERENCES hidra_risk_treatment_plan (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_treatment_action VALIDATE CONSTRAINT fk_hra111_risk_042;

-- hidra_risk_treatment_plan.risk_assessment_id -> hidra_risk_assessment.id
ALTER TABLE hidra_risk_treatment_plan
    ADD CONSTRAINT fk_hra111_risk_043
    FOREIGN KEY (risk_assessment_id) REFERENCES hidra_risk_assessment (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_treatment_plan VALIDATE CONSTRAINT fk_hra111_risk_043;

-- hidra_risk_treatment_plan.treatment_strategy_id -> hidra_risk_catalog_entry.id
ALTER TABLE hidra_risk_treatment_plan
    ADD CONSTRAINT fk_hra111_risk_044
    FOREIGN KEY (treatment_strategy_id) REFERENCES hidra_risk_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_risk_treatment_plan VALIDATE CONSTRAINT fk_hra111_risk_044;

-- hse: 17 HRA-111 same-module foreign keys
-- hidra_hse_capa.action_type_id -> hidra_hse_catalog_entry.id
ALTER TABLE hidra_hse_capa
    ADD CONSTRAINT fk_hra111_hse_001
    FOREIGN KEY (action_type_id) REFERENCES hidra_hse_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_capa VALIDATE CONSTRAINT fk_hra111_hse_001;

-- hidra_hse_capa.hse_case_id -> hidra_hse_case.id
ALTER TABLE hidra_hse_capa
    ADD CONSTRAINT fk_hra111_hse_002
    FOREIGN KEY (hse_case_id) REFERENCES hidra_hse_case (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_capa VALIDATE CONSTRAINT fk_hra111_hse_002;

-- hidra_hse_case_evidence_link.hse_case_id -> hidra_hse_case.id
ALTER TABLE hidra_hse_case_evidence_link
    ADD CONSTRAINT fk_hra111_hse_003
    FOREIGN KEY (hse_case_id) REFERENCES hidra_hse_case (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_case_evidence_link VALIDATE CONSTRAINT fk_hra111_hse_003;

-- hidra_hse_case_status_history.hse_case_id -> hidra_hse_case.id
ALTER TABLE hidra_hse_case_status_history
    ADD CONSTRAINT fk_hra111_hse_004
    FOREIGN KEY (hse_case_id) REFERENCES hidra_hse_case (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_case_status_history VALIDATE CONSTRAINT fk_hra111_hse_004;

-- hidra_hse_case.case_type_id -> hidra_hse_catalog_entry.id
ALTER TABLE hidra_hse_case
    ADD CONSTRAINT fk_hra111_hse_005
    FOREIGN KEY (case_type_id) REFERENCES hidra_hse_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_case VALIDATE CONSTRAINT fk_hra111_hse_005;

-- hidra_hse_case.severity_id -> hidra_hse_catalog_entry.id
ALTER TABLE hidra_hse_case
    ADD CONSTRAINT fk_hra111_hse_006
    FOREIGN KEY (severity_id) REFERENCES hidra_hse_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_case VALIDATE CONSTRAINT fk_hra111_hse_006;

-- hidra_hse_catalog_translation.catalog_entry_id -> hidra_hse_catalog_entry.id
ALTER TABLE hidra_hse_catalog_translation
    ADD CONSTRAINT fk_hra111_hse_007
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_hse_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_catalog_translation VALIDATE CONSTRAINT fk_hra111_hse_007;

-- hidra_hse_closure.hse_case_id -> hidra_hse_case.id
ALTER TABLE hidra_hse_closure
    ADD CONSTRAINT fk_hra111_hse_008
    FOREIGN KEY (hse_case_id) REFERENCES hidra_hse_case (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_closure VALIDATE CONSTRAINT fk_hra111_hse_008;

-- hidra_hse_compliance_assessment.obligation_id -> hidra_hse_compliance_obligation.id
ALTER TABLE hidra_hse_compliance_assessment
    ADD CONSTRAINT fk_hra111_hse_009
    FOREIGN KEY (obligation_id) REFERENCES hidra_hse_compliance_obligation (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_compliance_assessment VALIDATE CONSTRAINT fk_hra111_hse_009;

-- hidra_hse_compliance_obligation.obligation_type_id -> hidra_hse_catalog_entry.id
ALTER TABLE hidra_hse_compliance_obligation
    ADD CONSTRAINT fk_hra111_hse_010
    FOREIGN KEY (obligation_type_id) REFERENCES hidra_hse_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_compliance_obligation VALIDATE CONSTRAINT fk_hra111_hse_010;

-- hidra_hse_emergency_drill.drill_type_id -> hidra_hse_catalog_entry.id
ALTER TABLE hidra_hse_emergency_drill
    ADD CONSTRAINT fk_hra111_hse_011
    FOREIGN KEY (drill_type_id) REFERENCES hidra_hse_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_emergency_drill VALIDATE CONSTRAINT fk_hra111_hse_011;

-- hidra_hse_hazard_report.hazard_type_id -> hidra_hse_catalog_entry.id
ALTER TABLE hidra_hse_hazard_report
    ADD CONSTRAINT fk_hra111_hse_012
    FOREIGN KEY (hazard_type_id) REFERENCES hidra_hse_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_hazard_report VALIDATE CONSTRAINT fk_hra111_hse_012;

-- hidra_hse_impact_assessment.hse_case_id -> hidra_hse_case.id
ALTER TABLE hidra_hse_impact_assessment
    ADD CONSTRAINT fk_hra111_hse_013
    FOREIGN KEY (hse_case_id) REFERENCES hidra_hse_case (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_impact_assessment VALIDATE CONSTRAINT fk_hra111_hse_013;

-- hidra_hse_impact_assessment.impact_type_id -> hidra_hse_catalog_entry.id
ALTER TABLE hidra_hse_impact_assessment
    ADD CONSTRAINT fk_hra111_hse_014
    FOREIGN KEY (impact_type_id) REFERENCES hidra_hse_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_impact_assessment VALIDATE CONSTRAINT fk_hra111_hse_014;

-- hidra_hse_inspection.inspection_type_id -> hidra_hse_catalog_entry.id
ALTER TABLE hidra_hse_inspection
    ADD CONSTRAINT fk_hra111_hse_015
    FOREIGN KEY (inspection_type_id) REFERENCES hidra_hse_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_inspection VALIDATE CONSTRAINT fk_hra111_hse_015;

-- hidra_hse_near_miss_report.near_miss_type_id -> hidra_hse_catalog_entry.id
ALTER TABLE hidra_hse_near_miss_report
    ADD CONSTRAINT fk_hra111_hse_016
    FOREIGN KEY (near_miss_type_id) REFERENCES hidra_hse_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_near_miss_report VALIDATE CONSTRAINT fk_hra111_hse_016;

-- hidra_hse_permit_to_work.permit_type_id -> hidra_hse_catalog_entry.id
ALTER TABLE hidra_hse_permit_to_work
    ADD CONSTRAINT fk_hra111_hse_017
    FOREIGN KEY (permit_type_id) REFERENCES hidra_hse_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_hse_permit_to_work VALIDATE CONSTRAINT fk_hra111_hse_017;

