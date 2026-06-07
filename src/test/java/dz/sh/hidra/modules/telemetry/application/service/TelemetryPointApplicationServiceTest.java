/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Application service tests for telemetry point use cases.
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
import dz.sh.hidra.modules.telemetry.application.command.ActivateTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.command.RegisterTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.command.SuspendTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPageDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPointDto;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryPointByIdQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryPointsQuery;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointStatus;

/**
 * Application service tests for telemetry point use cases.
 */
class TelemetryPointApplicationServiceTest {

    @Test
    void shouldRegisterActivateSuspendGetAndListTelemetryPoints() {
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryDeviceRepository deviceRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryDeviceRepository();
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryPointRepository pointRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryPointRepository();
        deviceRepository.add(TelemetryDomainTestFixtures.activeDevice());

        TelemetryPointApplicationService service = new TelemetryPointApplicationService(
                deviceRepository,
                pointRepository,
                new TelemetryRegistrationDomainService(new TelemetrySourcePolicy(), new TelemetryPointPolicy()));

        TelemetryPointDto registered = service.registerTelemetryPoint(new RegisterTelemetryPointCommand(
                TelemetryDomainTestFixtures.activeDevice().id(),
                TelemetryDomainTestFixtures.code("PT-NEW"),
                TelemetryDomainTestFixtures.localizedName(),
                TelemetryDomainTestFixtures.pointType(),
                TelemetryDomainTestFixtures.signalType("NUMERIC"),
                TelemetryDomainTestFixtures.unit(),
                TelemetryDomainTestFixtures.aggregation(),
                null,
                null));

        TelemetryPointDto activated = service.activateTelemetryPoint(new ActivateTelemetryPointCommand(
                TelemetryDomainTestFixtures.activeDevice().id(),
                TelemetryPointId.of(registered.id())));
        TelemetryPointDto suspended = service.suspendTelemetryPoint(new SuspendTelemetryPointCommand(
                TelemetryPointId.of(registered.id())));
        TelemetryPointDto found = service.getTelemetryPoint(new GetTelemetryPointByIdQuery(
                TelemetryPointId.of(registered.id())));
        TelemetryPageDto<TelemetryPointDto> page = service.listTelemetryPoints(new ListTelemetryPointsQuery(
                "PT",
                TelemetryDomainTestFixtures.activeDevice().id(),
                null,
                null,
                TelemetryPointStatus.SUSPENDED,
                PageRequest.of(0, 20)));

        assertEquals("PLANNED", registered.status());
        assertEquals("ACTIVE", activated.status());
        assertEquals("SUSPENDED", suspended.status());
        assertEquals(registered.id(), found.id());
        assertEquals(1, page.items().size());
    }

    @Test
    void shouldRejectNumericTelemetryPointWithoutUnit() {
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryDeviceRepository deviceRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryDeviceRepository();
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryPointRepository pointRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryPointRepository();
        deviceRepository.add(TelemetryDomainTestFixtures.activeDevice());

        TelemetryPointApplicationService service = new TelemetryPointApplicationService(
                deviceRepository,
                pointRepository,
                new TelemetryRegistrationDomainService(new TelemetrySourcePolicy(), new TelemetryPointPolicy()));

        assertThrows(BusinessRuleViolationException.class, () -> service.registerTelemetryPoint(new RegisterTelemetryPointCommand(
                TelemetryDomainTestFixtures.activeDevice().id(),
                TelemetryDomainTestFixtures.code("PT-BAD"),
                TelemetryDomainTestFixtures.localizedName(),
                TelemetryDomainTestFixtures.pointType(),
                TelemetryDomainTestFixtures.signalType("NUMERIC"),
                null,
                TelemetryDomainTestFixtures.aggregation(),
                null,
                null)));
    }
}
