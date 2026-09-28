/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExpectedFlowState
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.model
 *
 * @Description : Expected operating state at a topology asset/time interval.
 *
 */
package dz.sh.hidra.modules.planning.domain.model;

import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Expected operating state at a topology asset/time interval.
     *
         * @param id id
     * @param revisionId revisionId
     * @param scenarioId scenarioId
     * @param planTargetId planTargetId
     * @param topologyAssetType topologyAssetType
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCode topologyAssetCode
     * @param expectedAt expectedAt
     * @param expectedFlowRate expectedFlowRate
     * @param flowRateUnitId flowRateUnitId
     * @param expectedPressureIn expectedPressureIn
     * @param expectedPressureOut expectedPressureOut
     * @param pressureUnitId pressureUnitId
     * @param expectedTemperature expectedTemperature
     * @param temperatureUnitId temperatureUnitId
     * @param expectedVolume expectedVolume
     * @param volumeUnitId volumeUnitId
     * @param expectedOperatingMode expectedOperatingMode
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdAt createdAt
     */
    public record ExpectedFlowState(
            String id,
        String revisionId,
        String scenarioId,
        String planTargetId,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        Instant expectedAt,
        BigDecimal expectedFlowRate,
        String flowRateUnitId,
        BigDecimal expectedPressureIn,
        BigDecimal expectedPressureOut,
        String pressureUnitId,
        BigDecimal expectedTemperature,
        String temperatureUnitId,
        BigDecimal expectedVolume,
        String volumeUnitId,
        String expectedOperatingMode,
        Instant validFrom,
        Instant validTo,
        Instant createdAt
    ) {

        public ExpectedFlowState {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPlanningValueException("ExpectedFlowState id must not be blank.");
        }
        // HRA-051 required: revisionId
        if (revisionId == null || revisionId.isBlank()) {
            throw new InvalidPlanningValueException("ExpectedFlowState revision id must not be blank.");
        }
        // HRA-051 required: topologyAssetId
        if (topologyAssetId == null || topologyAssetId.isBlank()) {
            throw new InvalidPlanningValueException("ExpectedFlowState topology asset id must not be blank.");
        }
        // HRA-051 required: topologyAssetCode
        if (topologyAssetCode == null || topologyAssetCode.isBlank()) {
            throw new InvalidPlanningValueException("ExpectedFlowState topology asset code must not be blank.");
        }
        // HRA-051 required: expectedAt
        if (expectedAt == null) {
            throw new InvalidPlanningValueException("ExpectedFlowState expected at must not be null.");
        }
        // HRA-051 required: validFrom
        if (validFrom == null) {
            throw new InvalidPlanningValueException("ExpectedFlowState valid from must not be null.");
        }
        // HRA-051 required: validTo
        if (validTo == null) {
            throw new InvalidPlanningValueException("ExpectedFlowState valid to must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidPlanningValueException("ExpectedFlowState valid to must not be before valid from.");
        }

        id = normalize(id);
        revisionId = normalize(revisionId);
        scenarioId = normalize(scenarioId);
        planTargetId = normalize(planTargetId);
        topologyAssetType = normalize(topologyAssetType);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCode = normalize(topologyAssetCode);
        flowRateUnitId = normalize(flowRateUnitId);
        pressureUnitId = normalize(pressureUnitId);
        temperatureUnitId = normalize(temperatureUnitId);
        volumeUnitId = normalize(volumeUnitId);
        expectedOperatingMode = normalize(expectedOperatingMode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
