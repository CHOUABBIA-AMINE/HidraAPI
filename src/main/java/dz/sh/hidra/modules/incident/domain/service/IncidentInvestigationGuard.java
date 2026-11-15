/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentInvestigationGuard
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.service
 * @Description : Pure incident investigation, independent review and closure state machine.
 */
package dz.sh.hidra.modules.incident.domain.service;

import java.util.Set;

/** Only persisted, attributable human actions advance an Incident investigation. */
public final class IncidentInvestigationGuard {
    public void require(String parent, String state, String operation, String investigator,
                        String actor, boolean evidence, boolean actions) {
        if (parent == null || state == null || actor == null || actor.isBlank())
            throw new IllegalStateException("Missing incident investigation state or actor.");
        if (Set.of("CLOSED","MERGED","CANCELLED","DRAFT").contains(parent))
            throw new IllegalStateException("Terminal or draft Incident cannot be investigated.");
        switch(operation) {
            case "ASSIGN" -> {
                if (!"NEW".equals(state) || !Set.of("OPEN","TRIAGED").contains(parent))
                    throw new IllegalStateException("Incident assignment must start from an open case.");
            }
            case "EVIDENCE","ACTION" -> {
                if (!Set.of("ASSIGNED","INVESTIGATING").contains(state)
                        || !Set.of("ASSIGNED","IN_PROGRESS").contains(parent)
                        || !actor.equals(investigator))
                    throw new IllegalStateException("Only the active Incident investigator can record work.");
            }
            case "SUBMIT" -> {
                if (!"INVESTIGATING".equals(state) || !"IN_PROGRESS".equals(parent)
                        || !actor.equals(investigator) || !evidence || !actions)
                    throw new IllegalStateException("Incident resolution requires investigator evidence and action.");
            }
            case "REVIEW" -> {
                if (!"SUBMITTED".equals(state) || !"IN_PROGRESS".equals(parent)
                        || actor.equals(investigator))
                    throw new IllegalStateException("Independent Incident reviewer and submitted resolution required.");
            }
            case "CLOSE" -> {
                if (!"APPROVED".equals(state) || !"RESOLVED".equals(parent)
                        || actor.equals(investigator))
                    throw new IllegalStateException("Only reviewed, resolved Incident may close independently.");
            }
            default -> throw new IllegalArgumentException("Unsupported Incident investigation transition.");
        }
    }
}
