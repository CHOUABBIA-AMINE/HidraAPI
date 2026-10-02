/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityProgram
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Long-term integrity program.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
import dz.sh.hidra.modules.integrity.domain.value.*;
import java.time.Instant;

    /**
     * Long-term integrity program.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param description description
     * @param programTypeId programTypeId
     * @param ownerOrganizationUnitId ownerOrganizationUnitId
     * @param ownerOrganizationUnitNameSnapshot ownerOrganizationUnitNameSnapshot
     * @param status status
     * @param plannedStartAt plannedStartAt
     * @param plannedEndAt plannedEndAt
     * @param actualStartAt actualStartAt
     * @param actualEndAt actualEndAt
     * @param createdByActorId createdByActorId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record IntegrityProgram(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String description,
        String programTypeId,
        String ownerOrganizationUnitId,
        String ownerOrganizationUnitNameSnapshot,
        IntegrityProgramStatus status,
        Instant plannedStartAt,
        Instant plannedEndAt,
        Instant actualStartAt,
        Instant actualEndAt,
        String createdByActorId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public IntegrityProgram {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrityValueException("IntegrityProgram id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidIntegrityValueException("IntegrityProgram code must not be blank.");
        }
        // HRA-051 required: programTypeId
        if (programTypeId == null || programTypeId.isBlank()) {
            throw new InvalidIntegrityValueException("IntegrityProgram program type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIntegrityValueException("IntegrityProgram status must not be null.");
        }

        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        description = normalize(description);
        programTypeId = normalize(programTypeId);
        ownerOrganizationUnitId = normalize(ownerOrganizationUnitId);
        ownerOrganizationUnitNameSnapshot = normalize(ownerOrganizationUnitNameSnapshot);
        createdByActorId = normalize(createdByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
