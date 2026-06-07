/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyAssetReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Neutral reference to an upstream topology asset.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Neutral snapshot reference to a topology asset.
 *
 * <p>Boundary rule:
 * Telemetry must not import topology domain or infrastructure classes. This value object stores only
 * the stable identifiers needed to bind a telemetry point to a topology asset.
 *
 * @param assetTypeCode topology asset type code
 * @param assetId topology asset identifier
 * @param assetCode topology asset business code
 * @param assetNameSnapshot optional topology asset name snapshot
 */
public record TopologyAssetReference(
        TelemetryCode assetTypeCode,
        String assetId,
        TelemetryCode assetCode,
        String assetNameSnapshot) implements ValueObject {

    public TopologyAssetReference {
        assetTypeCode = Objects.requireNonNull(assetTypeCode, "TopologyAssetReference assetTypeCode must not be null.");
        assetId = requireText(assetId, "TopologyAssetReference assetId");
        assetCode = Objects.requireNonNull(assetCode, "TopologyAssetReference assetCode must not be null.");
        assetNameSnapshot = normalizeOptional(assetNameSnapshot);
    }

    public static TopologyAssetReference of(String assetTypeCode, String assetId, String assetCode, String assetNameSnapshot) {
        return new TopologyAssetReference(TelemetryCode.of(assetTypeCode), assetId, TelemetryCode.of(assetCode), assetNameSnapshot);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(fieldName + " must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 120) {
            throw new InvalidValueObjectException(fieldName + " length must not exceed 120 characters.");
        }

        return normalized;
    }

    private static String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();

        if (normalized.length() > 160) {
            throw new InvalidValueObjectException("TopologyAssetReference assetNameSnapshot length must not exceed 160 characters.");
        }

        return normalized;
    }
}
