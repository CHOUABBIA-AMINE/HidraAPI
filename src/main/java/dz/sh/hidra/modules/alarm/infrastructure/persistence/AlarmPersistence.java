/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmPersistence
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence
 *
 * @Description : Alarm database table constants.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence;

/**
 * Alarm database table constants.
 */
public final class AlarmPersistence {

    public static final String ALARM_TABLE = "hidra_alarm";
    public static final String ALARM_LIFECYCLE_EVENT_TABLE = "hidra_alarm_lifecycle_event";
    public static final String ALARM_ACKNOWLEDGEMENT_TABLE = "hidra_alarm_acknowledgement";
    public static final String ALARM_SHELVING_TABLE = "hidra_alarm_shelving";
    public static final String ALARM_SUPPRESSION_TABLE = "hidra_alarm_suppression";
    public static final String ALARM_ESCALATION_TABLE = "hidra_alarm_escalation";
    public static final String ALARM_CLOSURE_TABLE = "hidra_alarm_closure";
    public static final String ALARM_EVIDENCE_LINK_TABLE = "hidra_alarm_evidence_link";
    public static final String ALARM_COMMENT_TABLE = "hidra_alarm_comment";
    public static final String ALARM_RULE_BINDING_TABLE = "hidra_alarm_rule_binding";
    public static final String ALARM_CATALOG_ENTRY_TABLE = "hidra_alarm_catalog_entry";
    public static final String ALARM_CATALOG_TRANSLATION_TABLE = "hidra_alarm_catalog_translation";

    private AlarmPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
