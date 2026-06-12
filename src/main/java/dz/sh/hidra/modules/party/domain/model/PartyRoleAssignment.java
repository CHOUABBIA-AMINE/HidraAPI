/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRoleAssignment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Assignment of one or more roles to a party.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Assignment of one or more roles to a party.
     *
         * @param id id
     * @param partyId partyId
     * @param roleId roleId
     * @param validFrom validFrom
     * @param validTo validTo
     * @param status status
     * @param qualificationRequired qualificationRequired
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyRoleAssignment(
            String id,
        String partyId,
        String roleId,
        Instant validFrom,
        Instant validTo,
        PartyRoleAssignmentStatus status,
        boolean qualificationRequired,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyRoleAssignment {
        id = normalize(id);
        partyId = normalize(partyId);
        roleId = normalize(roleId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
