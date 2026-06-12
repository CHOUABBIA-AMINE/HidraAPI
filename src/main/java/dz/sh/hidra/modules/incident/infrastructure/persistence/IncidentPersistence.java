/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentPersistence
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence
 *
 * @Description : Incident database table constants.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence;

/**
 * Incident database table constants.
 */
public final class IncidentPersistence {

    public static final String INCIDENT_TABLE = "hidra_incident";
    public static final String INCIDENT_TIMELINE_ENTRY_TABLE = "hidra_incident_timeline_entry";
    public static final String INCIDENT_EVIDENCE_LINK_TABLE = "hidra_incident_evidence_link";
    public static final String INCIDENT_ASSIGNMENT_TABLE = "hidra_incident_assignment";
    public static final String INCIDENT_RESPONSE_ACTION_TABLE = "hidra_incident_response_action";
    public static final String INCIDENT_IMPACT_ASSESSMENT_TABLE = "hidra_incident_impact_assessment";
    public static final String INCIDENT_ROOT_CAUSE_ANALYSIS_TABLE = "hidra_incident_root_cause_analysis";
    public static final String INCIDENT_RESOLUTION_TABLE = "hidra_incident_resolution";
    public static final String INCIDENT_CLOSURE_TABLE = "hidra_incident_closure";
    public static final String INCIDENT_ESCALATION_TABLE = "hidra_incident_escalation";
    public static final String INCIDENT_RELATED_INCIDENT_TABLE = "hidra_incident_related_incident";
    public static final String INCIDENT_ATTACHMENT_REFERENCE_TABLE = "hidra_incident_attachment_reference";
    public static final String INCIDENT_CATALOG_ENTRY_TABLE = "hidra_incident_catalog_entry";
    public static final String INCIDENT_CATALOG_TRANSLATION_TABLE = "hidra_incident_catalog_translation";

    private IncidentPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
