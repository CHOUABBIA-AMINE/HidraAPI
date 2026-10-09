/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationInputSourceVersion
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Pins immutable source revision identity, validity and declared provenance.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.time.Instant;
import java.util.Locale;

/**
 * Metadata reference only: digest and origin declarations do not verify payload or trust.
 * Validity is start-inclusive and end-exclusive; a null end is open-ended.
 */
public record SimulationInputSourceVersion(
        SourceKind kind,
        String owner,
        String sourceId,
        String revisionId,
        String payloadSha256,
        Instant recordedAt,
        Instant effectiveFrom,
        Instant effectiveUntil,
        Origin origin,
        String evidenceReference
) {
    public SimulationInputSourceVersion {
        if (kind == null || origin == null) {
            throw new InvalidSimulationValueException("Source kind and origin are required.");
        }
        owner = required(owner, "Source owner");
        sourceId = required(sourceId, "Source identity");
        revisionId = required(revisionId, "Revision identity");
        evidenceReference = required(evidenceReference, "Evidence reference");
        payloadSha256 = required(payloadSha256, "Payload SHA-256").toLowerCase(Locale.ROOT);
        if (!payloadSha256.matches("[0-9a-f]{64}")) {
            throw new InvalidSimulationValueException("Payload SHA-256 must contain 64 hexadecimal characters.");
        }
        if (recordedAt == null || effectiveFrom == null) {
            throw new InvalidSimulationValueException("Recorded and effective-from timestamps are required.");
        }
        if (effectiveUntil != null && !effectiveUntil.isAfter(effectiveFrom)) {
            throw new InvalidSimulationValueException("Effective-until must be after effective-from.");
        }
    }

    public boolean effectiveAt(Instant at) {
        if (at == null) {
            throw new InvalidSimulationValueException("Effective-at timestamp is required.");
        }
        return !at.isBefore(effectiveFrom) && (effectiveUntil == null || at.isBefore(effectiveUntil));
    }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new InvalidSimulationValueException(field + " must not be blank.");
        }
        return value.trim();
    }

    public enum SourceKind {
        TOPOLOGY_CONFIGURATION, FLUID_MODEL, EQUIPMENT_PARAMETERS, OPERATING_STATE, BOUNDARY_SCHEDULE
    }

    public enum Origin {
        APPROVED_PARAMETER, TRUSTED_TELEMETRY, ESTIMATED, SYNTHETIC, SCENARIO, FORECAST
    }
}
