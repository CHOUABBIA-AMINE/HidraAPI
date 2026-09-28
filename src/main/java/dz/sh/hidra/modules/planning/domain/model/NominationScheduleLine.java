/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NominationScheduleLine
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.model
 *
 * @Description : Time-sliced nomination detail.
 *
 */
package dz.sh.hidra.modules.planning.domain.model;

import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Time-sliced nomination detail.
     *
         * @param id id
     * @param nominationId nominationId
     * @param sequenceNumber sequenceNumber
     * @param lineStart lineStart
     * @param lineEnd lineEnd
     * @param plannedQuantity plannedQuantity
     * @param quantityUnitId quantityUnitId
     * @param plannedRate plannedRate
     * @param rateUnitId rateUnitId
     * @param notes notes
     */
    public record NominationScheduleLine(
            String id,
        String nominationId,
        int sequenceNumber,
        Instant lineStart,
        Instant lineEnd,
        BigDecimal plannedQuantity,
        String quantityUnitId,
        BigDecimal plannedRate,
        String rateUnitId,
        String notes
    ) {

        public NominationScheduleLine {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPlanningValueException("NominationScheduleLine id must not be blank.");
        }
        // HRA-051 required: nominationId
        if (nominationId == null || nominationId.isBlank()) {
            throw new InvalidPlanningValueException("NominationScheduleLine nomination id must not be blank.");
        }
        // HRA-051 required: lineStart
        if (lineStart == null) {
            throw new InvalidPlanningValueException("NominationScheduleLine line start must not be null.");
        }
        // HRA-051 required: lineEnd
        if (lineEnd == null) {
            throw new InvalidPlanningValueException("NominationScheduleLine line end must not be null.");
        }

        id = normalize(id);
        nominationId = normalize(nominationId);
        quantityUnitId = normalize(quantityUnitId);
        rateUnitId = normalize(rateUnitId);
        notes = normalize(notes);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
