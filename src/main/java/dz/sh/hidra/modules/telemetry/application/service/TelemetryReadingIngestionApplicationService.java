/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingIngestionApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Coordinates original-transaction authorization and PostgreSQL raw-reading replay.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;


import dz.sh.hidra.modules.telemetry.application.command.IngestTelemetryReadingCommand;
import dz.sh.hidra.modules.telemetry.application.port.in.IngestTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingIngestionReferencePort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingReplayPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingScopeAuthorizationPort;
import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryIngestionPolicy;
import dz.sh.hidra.modules.telemetry.domain.value.ReadingState;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TelemetryReadingIngestionApplicationService implements IngestTelemetryReadingUseCase {
    private final TelemetryReadingIngestionReferencePort references;
    private final TelemetryReadingScopeAuthorizationPort authorization;
    private final TelemetryReadingReplayPort replay;
    private final Duration maxPast;
    private final Duration maxFuture;

    public TelemetryReadingIngestionApplicationService(
            TelemetryReadingIngestionReferencePort references,
            TelemetryReadingScopeAuthorizationPort authorization,
            TelemetryReadingReplayPort replay,
            @Value("${hidra.telemetry.ingestion.max-past-age-seconds:86400}") long maxPastSeconds,
            @Value("${hidra.telemetry.ingestion.max-future-skew-seconds:120}") long maxFutureSeconds) {
        this.references = Objects.requireNonNull(references);
        this.authorization = Objects.requireNonNull(authorization);
        this.replay = Objects.requireNonNull(replay);
        if (maxPastSeconds <= 0 || maxFutureSeconds <= 0)
            throw new IllegalArgumentException("Telemetry ingestion UTC bounds must be positive.");
        maxPast = Duration.ofSeconds(maxPastSeconds);
        maxFuture = Duration.ofSeconds(maxFutureSeconds);
    }

    @Override
    @Transactional
    public Outcome ingest(IngestTelemetryReadingCommand cmd) {
        if (cmd == null || cmd.pointId() == null || cmd.pointId().isBlank()
                || cmd.sourceId() == null || cmd.sourceId().isBlank()
                || cmd.sourceSequenceNumber() == null || cmd.sourceSequenceNumber().isBlank()
                || cmd.sourceSequenceNumber().length() > 160 || cmd.qualityCodeId() == null
                || cmd.qualityCodeId().isBlank()) {
            throw new InvalidTelemetryValueException("Required telemetry source, point, sequence or quality missing.");
        }
        // PostgreSQL timestamptz retains microseconds; first and replay receipts must agree.
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        var scope = references.current(cmd.pointId(), cmd.sourceId(), now);
        authorization.require(scope, TelemetryReadingScopeAuthorizationPort.Operation.INGEST);
        String shape = TelemetryIngestionPolicy.shape(cmd.numericValue(), cmd.textValue(), cmd.booleanValue());
        references.validateValue(scope, cmd.qualityCodeId(), cmd.unitId(), shape);
        TelemetryIngestionPolicy.validateTime(cmd.sourceTimestamp(), now, maxPast, maxFuture);
        var reading = new TelemetryReading(UUID.randomUUID().toString(), scope.pointId(),
                cmd.numericValue(), cmd.textValue(), cmd.booleanValue(), cmd.qualityCodeId(),
                cmd.sourceTimestamp(), now, ReadingState.RECEIVED, null, null, null,
                cmd.sourceSequenceNumber().trim(), scope.mappingId(), null, now);
        String digest = TelemetryIngestionPolicy.digest(scope.sourceId(), scope.unitId(), reading);
        reading = new TelemetryReading(reading.id(), reading.pointId(), reading.numericValue(),
                reading.textValue(), reading.booleanValue(), reading.qualityCodeId(),
                reading.sourceTimestamp(), reading.receivedAt(), reading.state(), null, null, null,
                reading.sourceSequenceNumber(), reading.externalTagMappingId(), digest, reading.createdAt());
        var result = replay.appendOrReplay(scope.sourceId(), scope.unitId(), reading);
        return new Outcome(result.reading(), scope.sourceId(), result.effectiveUnitId(), result.replayed());
    }
}
