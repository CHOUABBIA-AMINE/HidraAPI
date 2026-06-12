/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityPersistence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Defines identity database table constants.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

/**
 * Identity database table constants.
 */
public final class IdentityPersistence {

public static final String USER_TABLE = "hidra_identity_user";
public static final String GROUP_TABLE = "hidra_identity_group";
public static final String ROLE_TABLE = "hidra_identity_role";
public static final String PERMISSION_TABLE = "hidra_identity_permission";
public static final String USER_ROLE_GRANT_TABLE = "hidra_identity_user_role_grant";
public static final String ROLE_PERMISSION_GRANT_TABLE = "hidra_identity_role_permission_grant";
public static final String USER_PERMISSION_GRANT_TABLE = "hidra_identity_user_permission_grant";
public static final String USER_GROUP_MEMBERSHIP_TABLE = "hidra_identity_user_group_membership";
public static final String GROUP_ROLE_GRANT_TABLE = "hidra_identity_group_role_grant";
public static final String AUTHORIZATION_POLICY_TABLE = "hidra_identity_authorization_policy";
public static final String AUTHORIZATION_POLICY_VERSION_TABLE = "hidra_identity_authorization_policy_version";
public static final String AUTHORIZATION_POLICY_RULE_TABLE = "hidra_identity_authorization_policy_rule";
public static final String ATTRIBUTE_DEFINITION_TABLE = "hidra_identity_attribute_definition";
public static final String SUBJECT_SECURITY_ATTRIBUTE_TABLE = "hidra_identity_subject_security_attribute";
public static final String AUTHORIZATION_DELEGATION_GRANT_TABLE = "hidra_identity_authorization_delegation_grant";
public static final String AUTHORIZATION_DECISION_TABLE = "hidra_identity_authorization_decision";
public static final String IDENTITY_PROVIDER_TABLE = "hidra_identity_provider";
public static final String EXTERNAL_IDENTITY_TABLE = "hidra_identity_external_identity";
public static final String EXTERNAL_GROUP_MAPPING_TABLE = "hidra_identity_external_group_mapping";
public static final String EXTERNAL_ROLE_MAPPING_TABLE = "hidra_identity_external_role_mapping";
public static final String EXTERNAL_PERMISSION_MAPPING_TABLE = "hidra_identity_external_permission_mapping";
public static final String IDENTITY_SYNCHRONIZATION_JOB_TABLE = "hidra_identity_synchronization_job";
public static final String IDENTITY_SYNCHRONIZATION_RECORD_TABLE = "hidra_identity_synchronization_record";
public static final String LOGIN_SESSION_TABLE = "hidra_identity_login_session";
public static final String AUTHENTICATION_EVENT_TABLE = "hidra_identity_authentication_event";

    private IdentityPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
