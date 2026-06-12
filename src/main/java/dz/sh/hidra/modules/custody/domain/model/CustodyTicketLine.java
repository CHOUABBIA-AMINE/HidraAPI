/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTicketLine
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Ticket line.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Ticket line.
     *
         * @param id id
     * @param transferTicketId transferTicketId
     * @param lineNumber lineNumber
     * @param lineTypeId lineTypeId
     * @param productTypeId productTypeId
     * @param quantity quantity
     * @param quantityUnitId quantityUnitId
     * @param qualityValue qualityValue
     * @param qualityUnitId qualityUnitId
     * @param description description
     * @param createdAt createdAt
     */
    public record CustodyTicketLine(
            String id,
        String transferTicketId,
        int lineNumber,
        String lineTypeId,
        String productTypeId,
        BigDecimal quantity,
        String quantityUnitId,
        BigDecimal qualityValue,
        String qualityUnitId,
        String description,
        Instant createdAt
    ) {

        public CustodyTicketLine {
        id = normalize(id);
        transferTicketId = normalize(transferTicketId);
        lineTypeId = normalize(lineTypeId);
        productTypeId = normalize(productTypeId);
        quantityUnitId = normalize(quantityUnitId);
        qualityUnitId = normalize(qualityUnitId);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
