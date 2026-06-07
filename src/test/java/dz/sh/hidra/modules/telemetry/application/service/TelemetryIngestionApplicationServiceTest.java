/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryIngestionApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Application service tests for telemetry ingestion batch use cases.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.application.command.CompleteTelemetryIngestionBatchCommand;
import dz.sh.hidra.modules.telemetry.application.command.MarkTelemetryIngestionBatchProcessingCommand;
import dz.sh.hidra.modules.telemetry.application.command.StartTelemetryIngestionBatchCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryIngestionBatchDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPageDto;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryIngestionBatchByIdQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryIngestionBatchesQuery;
import dz.sh.hidra.modules.telemetry.application.support.TelemetryApplicationServiceTestSupport;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryIngestionPolicy;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryIngestionDomainService;
import dz.sh.hidra.modules.telemetry.domain.support.TelemetryDomainTestFixtures;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCorrelationId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchStatus;

/**
 * Application service tests for telemetry ingestion batch use cases.
 */
class TelemetryIngestionApplicationServiceTest {

    @Test
    void shouldStartProcessCompleteGetAndListTelemetryIngestionBatches() {
        TelemetryApplicationServiceTestSupport.InMemoryTelemetrySourceRepository sourceRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetrySourceRepository();
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryIngestionBatchRepository batchRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryIngestionBatchRepository();
        sourceRepository.add(TelemetryDomainTestFixtures.activeSource());

        TelemetryIngestionApplicationService service = new TelemetryIngestionApplicationService(
                sourceRepository,
                batchRepository,
                new TelemetryIngestionDomainService(new TelemetryIngestionPolicy()));

        TelemetryIngestionBatchDto started = service.startTelemetryIngestionBatch(new StartTelemetryIngestionBatchCommand(
                TelemetryDomainTestFixtures.activeSource().id(),
                TelemetryCorrelationId.of("corr-001")));
        TelemetryIngestionBatchDto processing = service.markTelemetryIngestionBatchProcessing(
                new MarkTelemetryIngestionBatchProcessingCommand(TelemetryIngestionBatchId.of(started.id())));
        TelemetryIngestionBatchDto completed = service.completeTelemetryIngestionBatch(new CompleteTelemetryIngestionBatchCommand(
                TelemetryIngestionBatchId.of(started.id()),
                10,
                10,
                0,
                0,
                0));
        TelemetryIngestionBatchDto found = service.getTelemetryIngestionBatch(
                new GetTelemetryIngestionBatchByIdQuery(TelemetryIngestionBatchId.of(started.id())));
        TelemetryPageDto<TelemetryIngestionBatchDto> page = service.listTelemetryIngestionBatches(new ListTelemetryIngestionBatchesQuery(
                TelemetryDomainTestFixtures.activeSource().id(),
                TelemetryIngestionBatchStatus.COMPLETED,
                null,
                null,
                PageRequest.of(0, 20)));

        assertEquals("RECEIVED", started.status());
        assertEquals("PROCESSING", processing.status());
        assertEquals("COMPLETED", completed.status());
        assertEquals(started.id(), found.id());
        assertEquals(1, page.items().size());
    }

    @Test
    void shouldRejectBatchStartForInactiveSource() {
        TelemetryApplicationServiceTestSupport.InMemoryTelemetrySourceRepository sourceRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetrySourceRepository();
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryIngestionBatchRepository batchRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryIngestionBatchRepository();
        sourceRepository.add(TelemetryDomainTestFixtures.source(dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceStatus.INACTIVE));

        TelemetryIngestionApplicationService service = new TelemetryIngestionApplicationService(
                sourceRepository,
                batchRepository,
                new TelemetryIngestionDomainService(new TelemetryIngestionPolicy()));

        assertThrows(BusinessRuleViolationException.class, () -> service.startTelemetryIngestionBatch(new StartTelemetryIngestionBatchCommand(
                TelemetryDomainTestFixtures.activeSource().id(),
                TelemetryCorrelationId.of("corr-001"))));
    }
}
