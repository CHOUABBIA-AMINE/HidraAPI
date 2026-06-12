/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NominationScheduleLine
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
