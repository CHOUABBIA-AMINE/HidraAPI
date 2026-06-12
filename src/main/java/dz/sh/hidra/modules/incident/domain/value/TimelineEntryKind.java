/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TimelineEntryKind
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.value
 *
 * @Description : Defines TimelineEntryKind values.
 *
 */
package dz.sh.hidra.modules.incident.domain.value;

/**
 * Defines TimelineEntryKind values.
 */
public enum TimelineEntryKind {
    CREATED, CLASSIFIED, ASSIGNED, ACTION_RECORDED, ESCALATED, CONTAINED, RESOLVED, CLOSED, REOPENED, COMMENTED, EVIDENCE_LINKED, CANCELLED
}
