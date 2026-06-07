/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingAndIngestionDomainTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Domain tests for telemetry reading and ingestion batch lifecycle models.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.domain.support.TelemetryDomainTestFixtures;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCorrelationId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingState;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingValue;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTimestamp;

/**
 * Domain tests for telemetry reading and ingestion batch lifecycle models.
 */
class TelemetryReadingAndIngestionDomainTest {

    @Test
    void shouldMoveReadingThroughProcessingStates() {
        TelemetryReading received = TelemetryDomainTestFixtures.receivedReading(
                TelemetryDomainTestFixtures.activeNumericPoint().id());

        assertEquals(TelemetryReadingState.RECEIVED, received.state());
        assertEquals(TelemetryReadingState.ACCEPTED, received.accept().state());
        assertEquals(TelemetryReadingState.DUPLICATE, received.markDuplicate().state());
        assertEquals(TelemetryReadingState.QUARANTINED, received.quarantine(" suspicious value ").state());

        TelemetryReading rejected = received.reject(" invalid quality ");
        assertEquals(TelemetryReadingState.REJECTED, rejected.state());
        assertEquals("invalid quality", rejected.rejectionReason());
    }

    @Test
    void shouldRejectInvalidReadingTimelineAndMissingRejectionReason() {
        TelemetryReading received = TelemetryDomainTestFixtures.receivedReading(
                TelemetryDomainTestFixtures.activeNumericPoint().id());

        assertThrows(BusinessRuleViolationException.class, () -> received.reject(" "));

        assertThrows(BusinessRuleViolationException.class, () -> TelemetryReading.restore(
                TelemetryReadingId.of("reading-invalid"),
                received.pointId(),
                TelemetryReadingValue.numeric(BigDecimal.ONE),
                received.qualityCode(),
                TelemetryTimestamp.of(Instant.parse("2026-01-01T00:02:00Z")),
                TelemetryTimestamp.of(Instant.parse("2026-01-01T00:01:00Z")),
                TelemetryReadingState.RECEIVED,
                TelemetryIngestionBatchId.of("batch-001"),
                TelemetryCorrelationId.of("corr-001"),
                null));
    }

    @Test
    void shouldMoveIngestionBatchThroughLifecycle() {
        TelemetryIngestionBatch started = TelemetryIngestionBatch.start(
                TelemetryDomainTestFixtures.activeSource().id(),
                TelemetryCorrelationId.of("corr-001"));

        TelemetryIngestionBatch processing = started.markProcessing();
        TelemetryIngestionBatch completed = processing.complete(10, 10, 0, 0, 0);
        TelemetryIngestionBatch completedWithErrors = processing.complete(10, 8, 1, 1, 0);

        assertEquals(TelemetryIngestionBatchStatus.RECEIVED, started.status());
        assertEquals(TelemetryIngestionBatchStatus.PROCESSING, processing.status());
        assertEquals(TelemetryIngestionBatchStatus.COMPLETED, completed.status());
        assertEquals(TelemetryIngestionBatchStatus.COMPLETED_WITH_ERRORS, completedWithErrors.status());
    }

    @Test
    void shouldRequireFailureReasonForFailedBatch() {
        TelemetryIngestionBatch processing = TelemetryDomainTestFixtures.batch(TelemetryIngestionBatchStatus.PROCESSING);

        assertEquals(TelemetryIngestionBatchStatus.FAILED, processing.fail(" source timeout ").status());
        assertThrows(BusinessRuleViolationException.class, () -> processing.fail(" "));
    }
}
