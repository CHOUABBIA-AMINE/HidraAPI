/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Application service tests for telemetry reading use cases.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.application.support.TelemetryApplicationServiceTestSupport;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryBindingPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryCatalogPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryIngestionPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryPointPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryReadingPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetrySourcePolicy;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryBindingDomainService;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryCatalogDomainService;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryIngestionDomainService;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryReadingDomainService;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryRegistrationDomainService;
import dz.sh.hidra.modules.telemetry.domain.support.TelemetryDomainTestFixtures;
import dz.sh.hidra.modules.telemetry.application.command.AcceptTelemetryReadingCommand;
import dz.sh.hidra.modules.telemetry.application.command.ReceiveTelemetryReadingCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPageDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryReadingDto;
import dz.sh.hidra.modules.telemetry.application.query.GetLatestTelemetryReadingQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryReadingsQuery;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCorrelationId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingState;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingValue;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTimestamp;

/**
 * Application service tests for telemetry reading use cases.
 */
class TelemetryReadingApplicationServiceTest {

    @Test
    void shouldReceiveAcceptGetLatestAndListTelemetryReadings() {
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryPointRepository pointRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryPointRepository();
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryReadingRepository readingRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryReadingRepository();
        pointRepository.add(TelemetryDomainTestFixtures.activeNumericPoint());

        TelemetryReadingApplicationService service = new TelemetryReadingApplicationService(
                pointRepository,
                readingRepository,
                new TelemetryReadingDomainService(new TelemetryPointPolicy(), new TelemetryReadingPolicy()));

        TelemetryReadingDto received = service.receiveTelemetryReading(new ReceiveTelemetryReadingCommand(
                TelemetryDomainTestFixtures.activeNumericPoint().id(),
                TelemetryReadingValue.numeric(BigDecimal.valueOf(42.25)),
                TelemetryDomainTestFixtures.quality(),
                TelemetryTimestamp.of(TelemetryDomainTestFixtures.SOURCE_TIMESTAMP),
                null,
                TelemetryCorrelationId.of("corr-001")));
        TelemetryReadingDto accepted = service.acceptTelemetryReading(new AcceptTelemetryReadingCommand(
                TelemetryReadingId.of(received.id())));
        TelemetryReadingDto latest = service.getLatestTelemetryReading(new GetLatestTelemetryReadingQuery(
                TelemetryDomainTestFixtures.activeNumericPoint().id()));
        TelemetryPageDto<TelemetryReadingDto> page = service.listTelemetryReadings(new ListTelemetryReadingsQuery(
                TelemetryDomainTestFixtures.activeNumericPoint().id(),
                null,
                TelemetryReadingState.ACCEPTED,
                null,
                null,
                null,
                PageRequest.of(0, 20)));

        assertEquals("RECEIVED", received.state());
        assertEquals("ACCEPTED", accepted.state());
        assertEquals(received.id(), latest.id());
        assertEquals(1, page.items().size());
    }

    @Test
    void shouldMarkSecondReadingWithSamePointAndTimestampAsDuplicate() {
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryPointRepository pointRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryPointRepository();
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryReadingRepository readingRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryReadingRepository();
        pointRepository.add(TelemetryDomainTestFixtures.activeNumericPoint());

        TelemetryReadingApplicationService service = new TelemetryReadingApplicationService(
                pointRepository,
                readingRepository,
                new TelemetryReadingDomainService(new TelemetryPointPolicy(), new TelemetryReadingPolicy()));

        service.receiveTelemetryReading(new ReceiveTelemetryReadingCommand(
                TelemetryDomainTestFixtures.activeNumericPoint().id(),
                TelemetryReadingValue.numeric(BigDecimal.ONE),
                TelemetryDomainTestFixtures.quality(),
                TelemetryTimestamp.of(TelemetryDomainTestFixtures.SOURCE_TIMESTAMP),
                null,
                null));
        TelemetryReadingDto duplicate = service.receiveTelemetryReading(new ReceiveTelemetryReadingCommand(
                TelemetryDomainTestFixtures.activeNumericPoint().id(),
                TelemetryReadingValue.numeric(BigDecimal.TEN),
                TelemetryDomainTestFixtures.quality(),
                TelemetryTimestamp.of(TelemetryDomainTestFixtures.SOURCE_TIMESTAMP),
                null,
                null));

        assertEquals("DUPLICATE", duplicate.state());
    }
}
