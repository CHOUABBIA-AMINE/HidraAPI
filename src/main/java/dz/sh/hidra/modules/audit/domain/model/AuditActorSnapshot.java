/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditActorSnapshot
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.model
 *
 * @Description : Normalized actor snapshot.
 *
 */
package dz.sh.hidra.modules.audit.domain.model;

import dz.sh.hidra.modules.audit.domain.value.*;
import java.time.Instant;

    /**
     * Normalized actor snapshot.
     *
         * @param id id
     * @param auditEventId auditEventId
     * @param actorId actorId
     * @param actorType actorType
     * @param usernameSnapshot usernameSnapshot
     * @param displayNameSnapshot displayNameSnapshot
     * @param emailMasked emailMasked
     * @param roleCodeSnapshot roleCodeSnapshot
     * @param employeeId employeeId
     * @param employeeNumberSnapshot employeeNumberSnapshot
     * @param organizationUnitId organizationUnitId
     * @param organizationUnitCodeSnapshot organizationUnitCodeSnapshot
     * @param organizationUnitNameSnapshot organizationUnitNameSnapshot
     * @param positionCodeSnapshot positionCodeSnapshot
     * @param capturedAt capturedAt
     */
    public record AuditActorSnapshot(
            String id,
        String auditEventId,
        String actorId,
        AuditActorType actorType,
        String usernameSnapshot,
        String displayNameSnapshot,
        String emailMasked,
        String roleCodeSnapshot,
        String employeeId,
        String employeeNumberSnapshot,
        String organizationUnitId,
        String organizationUnitCodeSnapshot,
        String organizationUnitNameSnapshot,
        String positionCodeSnapshot,
        Instant capturedAt
    ) {

        public AuditActorSnapshot {
        id = normalize(id);
        auditEventId = normalize(auditEventId);
        actorId = normalize(actorId);
        usernameSnapshot = normalize(usernameSnapshot);
        displayNameSnapshot = normalize(displayNameSnapshot);
        emailMasked = normalize(emailMasked);
        roleCodeSnapshot = normalize(roleCodeSnapshot);
        employeeId = normalize(employeeId);
        employeeNumberSnapshot = normalize(employeeNumberSnapshot);
        organizationUnitId = normalize(organizationUnitId);
        organizationUnitCodeSnapshot = normalize(organizationUnitCodeSnapshot);
        organizationUnitNameSnapshot = normalize(organizationUnitNameSnapshot);
        positionCodeSnapshot = normalize(positionCodeSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
