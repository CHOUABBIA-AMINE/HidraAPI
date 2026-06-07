/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySource
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Core telemetry acquisition source domain aggregate.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.AggregateRoot;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryEndpointUri;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryExternalReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryLocalizedName;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryProtocolReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceTypeReference;

/**
 * Core telemetry acquisition source aggregate.
 *
 * <p>Business role:
 * Represents an upstream acquisition source such as SCADA, historian, PLC gateway, IoT gateway, or
 * manual import source.
 *
 * <p>Architecture role:
 * Pure telemetry domain aggregate. It owns no infrastructure connection behavior and does not import
 * external module implementation classes.
 */
public final class TelemetrySource implements AggregateRoot<TelemetrySourceId> {

    private final TelemetrySourceId id;
    private final TelemetryCode code;
    private final TelemetryLocalizedName name;
    private final TelemetrySourceTypeReference sourceType;
    private final TelemetryProtocolReference protocol;
    private final TelemetryEndpointUri endpointUri;
    private final TelemetryExternalReference externalReference;
    private final TelemetrySourceStatus status;
    private final Instant createdAt;
    private final Instant updatedAt;

    private TelemetrySource(
            TelemetrySourceId id,
            TelemetryCode code,
            TelemetryLocalizedName name,
            TelemetrySourceTypeReference sourceType,
            TelemetryProtocolReference protocol,
            TelemetryEndpointUri endpointUri,
            TelemetryExternalReference externalReference,
            TelemetrySourceStatus status,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Telemetry source id must not be null.");
        this.code = Objects.requireNonNull(code, "Telemetry source code must not be null.");
        this.name = Objects.requireNonNull(name, "Telemetry source name must not be null.");
        this.sourceType = Objects.requireNonNull(sourceType, "Telemetry source type must not be null.");
        this.protocol = Objects.requireNonNull(protocol, "Telemetry source protocol must not be null.");
        this.endpointUri = endpointUri;
        this.externalReference = externalReference;
        this.status = Objects.requireNonNull(status, "Telemetry source status must not be null.");
        this.createdAt = Objects.requireNonNull(createdAt, "Telemetry source createdAt must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Telemetry source updatedAt must not be null.");

        if (updatedAt.isBefore(createdAt)) {
            throw new BusinessRuleViolationException("Telemetry source updatedAt must not be before createdAt.");
        }
    }

    public static TelemetrySource create(
            TelemetryCode code,
            TelemetryLocalizedName name,
            TelemetrySourceTypeReference sourceType,
            TelemetryProtocolReference protocol,
            TelemetryEndpointUri endpointUri,
            TelemetryExternalReference externalReference) {

        Instant now = Instant.now();
        return new TelemetrySource(
                TelemetrySourceId.newId(),
                code,
                name,
                sourceType,
                protocol,
                endpointUri,
                externalReference,
                TelemetrySourceStatus.PLANNED,
                now,
                now);
    }

    public static TelemetrySource restore(
            TelemetrySourceId id,
            TelemetryCode code,
            TelemetryLocalizedName name,
            TelemetrySourceTypeReference sourceType,
            TelemetryProtocolReference protocol,
            TelemetryEndpointUri endpointUri,
            TelemetryExternalReference externalReference,
            TelemetrySourceStatus status,
            Instant createdAt,
            Instant updatedAt) {

        return new TelemetrySource(
                id,
                code,
                name,
                sourceType,
                protocol,
                endpointUri,
                externalReference,
                status,
                createdAt,
                updatedAt);
    }

    @Override
    public TelemetrySourceId id() {
        return id;
    }

    public TelemetryCode code() {
        return code;
    }

    public TelemetryLocalizedName name() {
        return name;
    }

    public TelemetrySourceTypeReference sourceType() {
        return sourceType;
    }

    public TelemetryProtocolReference protocol() {
        return protocol;
    }

    public TelemetryEndpointUri endpointUri() {
        return endpointUri;
    }

    public TelemetryExternalReference externalReference() {
        return externalReference;
    }

    public TelemetrySourceStatus status() {
        return status;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public boolean active() {
        return TelemetrySourceStatus.ACTIVE.equals(status);
    }

    public TelemetrySource activate() {
        if (TelemetrySourceStatus.RETIRED.equals(status)) {
            throw new BusinessRuleViolationException("Retired telemetry source cannot be activated.");
        }
        return withStatus(TelemetrySourceStatus.ACTIVE);
    }

    public TelemetrySource deactivate() {
        if (TelemetrySourceStatus.RETIRED.equals(status)) {
            throw new BusinessRuleViolationException("Retired telemetry source cannot be deactivated.");
        }
        return withStatus(TelemetrySourceStatus.INACTIVE);
    }

    public TelemetrySource retire() {
        return withStatus(TelemetrySourceStatus.RETIRED);
    }

    private TelemetrySource withStatus(TelemetrySourceStatus newStatus) {
        return new TelemetrySource(
                id,
                code,
                name,
                sourceType,
                protocol,
                endpointUri,
                externalReference,
                newStatus,
                createdAt,
                Instant.now());
    }
}
