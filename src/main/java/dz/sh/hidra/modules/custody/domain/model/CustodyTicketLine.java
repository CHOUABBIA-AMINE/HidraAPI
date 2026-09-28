/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTicketLine
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidCustodyValueException("CustodyTicketLine id must not be blank.");
        }
        // HRA-051 required: transferTicketId
        if (transferTicketId == null || transferTicketId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyTicketLine transfer ticket id must not be blank.");
        }
        // HRA-051 required: lineTypeId
        if (lineTypeId == null || lineTypeId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyTicketLine line type id must not be blank.");
        }
        // HRA-051 required: productTypeId
        if (productTypeId == null || productTypeId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyTicketLine product type id must not be blank.");
        }
        // HRA-051 required: quantity
        if (quantity == null) {
            throw new InvalidCustodyValueException("CustodyTicketLine quantity must not be null.");
        }
        // HRA-051 required: quantityUnitId
        if (quantityUnitId == null || quantityUnitId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyTicketLine quantity unit id must not be blank.");
        }

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
