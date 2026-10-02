/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditPersistence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence
 *
 * @Description : Audit database table constants.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence;

/**
 * Audit database table constants.
 */
public final class AuditPersistence {

    public static final String AUDIT_EVENT_TABLE = "hidra_audit_event";
    public static final String AUDIT_ACTOR_SNAPSHOT_TABLE = "hidra_audit_actor_snapshot";
    public static final String AUDIT_TARGET_REFERENCE_TABLE = "hidra_audit_target_reference";
    public static final String AUDIT_ACTION_REFERENCE_TABLE = "hidra_audit_action_reference";
    public static final String AUDIT_DECISION_CONTEXT_TABLE = "hidra_audit_decision_context";
    public static final String AUDIT_BEFORE_AFTER_VALUE_TABLE = "hidra_audit_before_after_value";
    public static final String AUDIT_CORRELATION_CONTEXT_TABLE = "hidra_audit_correlation_context";
    public static final String AUDIT_EVIDENCE_LINK_TABLE = "hidra_audit_evidence_link";
    public static final String AUDIT_SEARCH_PROJECTION_TABLE = "hidra_audit_search_projection";
    public static final String AUDIT_INTEGRITY_SEAL_TABLE = "hidra_audit_integrity_seal";
    public static final String AUDIT_RETENTION_POLICY_TABLE = "hidra_audit_retention_policy";
    public static final String AUDIT_EXPORT_REQUEST_TABLE = "hidra_audit_export_request";
    public static final String AUDIT_ACCESS_RECORD_TABLE = "hidra_audit_access_record";
    public static final String AUDIT_CATALOG_ENTRY_TABLE = "hidra_audit_catalog_entry";
    public static final String AUDIT_CATALOG_TRANSLATION_TABLE = "hidra_audit_catalog_translation";

    private AuditPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
