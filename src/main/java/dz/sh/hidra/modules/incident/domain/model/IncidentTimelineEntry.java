/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentTimelineEntry
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.model
 *
 * @Description : Append-only incident history entry.
 *
 */
package dz.sh.hidra.modules.incident.domain.model;

import dz.sh.hidra.modules.incident.domain.value.*;
import java.time.Instant;

    /**
     * Append-only incident history entry.
     *
         * @param id id
     * @param incidentId incidentId
     * @param entryTypeId entryTypeId
     * @param statusBefore statusBefore
     * @param statusAfter statusAfter
     * @param title title
     * @param description description
     * @param actorId actorId
     * @param actorNameSnapshot actorNameSnapshot
     * @param organizationUnitId organizationUnitId
     * @param organizationUnitNameSnapshot organizationUnitNameSnapshot
     * @param occurredAt occurredAt
     * @param recordedAt recordedAt
     * @param correlationId correlationId
     */
    public record IncidentTimelineEntry(
            String id,
        String incidentId,
        String entryTypeId,
        IncidentStatus statusBefore,
        IncidentStatus statusAfter,
        String title,
        String description,
        String actorId,
        String actorNameSnapshot,
        String organizationUnitId,
        String organizationUnitNameSnapshot,
        Instant occurredAt,
        Instant recordedAt,
        String correlationId
    ) {

        public IncidentTimelineEntry {
        id = normalize(id);
        incidentId = normalize(incidentId);
        entryTypeId = normalize(entryTypeId);
        title = normalize(title);
        description = normalize(description);
        actorId = normalize(actorId);
        actorNameSnapshot = normalize(actorNameSnapshot);
        organizationUnitId = normalize(organizationUnitId);
        organizationUnitNameSnapshot = normalize(organizationUnitNameSnapshot);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
