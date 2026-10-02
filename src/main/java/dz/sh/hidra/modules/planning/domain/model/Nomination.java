/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Nomination
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.model
 *
 * @Description : Requested hydrocarbon movement quantity.
 *
 */
package dz.sh.hidra.modules.planning.domain.model;

import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Requested hydrocarbon movement quantity.
     *
         * @param id id
     * @param revisionId revisionId
     * @param scenarioId scenarioId
     * @param code code
     * @param nominationTypeId nominationTypeId
     * @param productTypeId productTypeId
     * @param quantity quantity
     * @param quantityUnitId quantityUnitId
     * @param rate rate
     * @param rateUnitId rateUnitId
     * @param sourceAssetType sourceAssetType
     * @param sourceAssetId sourceAssetId
     * @param sourceAssetCode sourceAssetCode
     * @param destinationAssetType destinationAssetType
     * @param destinationAssetId destinationAssetId
     * @param destinationAssetCode destinationAssetCode
     * @param shipperPartyId shipperPartyId
     * @param shipperPartyCodeSnapshot shipperPartyCodeSnapshot
     * @param counterpartyId counterpartyId
     * @param contractReferenceId contractReferenceId
     * @param priority priority
     * @param status status
     * @param periodStart periodStart
     * @param periodEnd periodEnd
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record Nomination(
            String id,
        String revisionId,
        String scenarioId,
        String code,
        String nominationTypeId,
        String productTypeId,
        BigDecimal quantity,
        String quantityUnitId,
        BigDecimal rate,
        String rateUnitId,
        String sourceAssetType,
        String sourceAssetId,
        String sourceAssetCode,
        String destinationAssetType,
        String destinationAssetId,
        String destinationAssetCode,
        String shipperPartyId,
        String shipperPartyCodeSnapshot,
        String counterpartyId,
        String contractReferenceId,
        Integer priority,
        NominationStatus status,
        Instant periodStart,
        Instant periodEnd,
        Instant createdAt,
        Instant updatedAt
    ) {

        public Nomination {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPlanningValueException("Nomination id must not be blank.");
        }
        // HRA-051 required: revisionId
        if (revisionId == null || revisionId.isBlank()) {
            throw new InvalidPlanningValueException("Nomination revision id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidPlanningValueException("Nomination code must not be blank.");
        }
        // HRA-051 required: nominationTypeId
        if (nominationTypeId == null || nominationTypeId.isBlank()) {
            throw new InvalidPlanningValueException("Nomination nomination type id must not be blank.");
        }
        // HRA-051 required: productTypeId
        if (productTypeId == null || productTypeId.isBlank()) {
            throw new InvalidPlanningValueException("Nomination product type id must not be blank.");
        }
        // HRA-051 required: quantity
        if (quantity == null) {
            throw new InvalidPlanningValueException("Nomination quantity must not be null.");
        }
        // HRA-051 required: quantityUnitId
        if (quantityUnitId == null || quantityUnitId.isBlank()) {
            throw new InvalidPlanningValueException("Nomination quantity unit id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPlanningValueException("Nomination status must not be null.");
        }
        // HRA-051 required: periodStart
        if (periodStart == null) {
            throw new InvalidPlanningValueException("Nomination period start must not be null.");
        }
        // HRA-051 required: periodEnd
        if (periodEnd == null) {
            throw new InvalidPlanningValueException("Nomination period end must not be null.");
        }
        // HRA-051 order: periodStart <= periodEnd
        if (periodStart != null && periodEnd != null && periodEnd.isBefore(periodStart)) {
            throw new InvalidPlanningValueException("Nomination period end must not be before period start.");
        }

        id = normalize(id);
        revisionId = normalize(revisionId);
        scenarioId = normalize(scenarioId);
        code = normalize(code);
        nominationTypeId = normalize(nominationTypeId);
        productTypeId = normalize(productTypeId);
        quantityUnitId = normalize(quantityUnitId);
        rateUnitId = normalize(rateUnitId);
        sourceAssetType = normalize(sourceAssetType);
        sourceAssetId = normalize(sourceAssetId);
        sourceAssetCode = normalize(sourceAssetCode);
        destinationAssetType = normalize(destinationAssetType);
        destinationAssetId = normalize(destinationAssetId);
        destinationAssetCode = normalize(destinationAssetCode);
        shipperPartyId = normalize(shipperPartyId);
        shipperPartyCodeSnapshot = normalize(shipperPartyCodeSnapshot);
        counterpartyId = normalize(counterpartyId);
        contractReferenceId = normalize(contractReferenceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
