/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Application service tests for telemetry source use cases.
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
import dz.sh.hidra.modules.telemetry.application.command.ActivateTelemetrySourceCommand;
import dz.sh.hidra.modules.telemetry.application.command.RegisterTelemetrySourceCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPageDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetrySourceDto;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetrySourceByIdQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetrySourcesQuery;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceStatus;

/**
 * Application service tests for telemetry source use cases.
 */
class TelemetrySourceApplicationServiceTest {

    @Test
    void shouldRegisterActivateGetAndListTelemetrySources() {
        TelemetryApplicationServiceTestSupport.InMemoryTelemetrySourceRepository repository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetrySourceRepository();

        TelemetrySourceApplicationService service = new TelemetrySourceApplicationService(
                repository,
                new TelemetryRegistrationDomainService(new TelemetrySourcePolicy(), new TelemetryPointPolicy()));

        TelemetrySourceDto registered = service.registerTelemetrySource(new RegisterTelemetrySourceCommand(
                TelemetryDomainTestFixtures.code("SCADA-NEW"),
                TelemetryDomainTestFixtures.localizedName(),
                TelemetryDomainTestFixtures.sourceType(),
                TelemetryDomainTestFixtures.protocol(),
                null,
                null));

        TelemetrySourceDto activated = service.activateTelemetrySource(
                new ActivateTelemetrySourceCommand(TelemetrySourceId.of(registered.id())));
        TelemetrySourceDto found = service.getTelemetrySource(
                new GetTelemetrySourceByIdQuery(TelemetrySourceId.of(registered.id())));
        TelemetryPageDto<TelemetrySourceDto> page = service.listTelemetrySources(new ListTelemetrySourcesQuery(
                "SCADA",
                null,
                null,
                TelemetrySourceStatus.ACTIVE,
                PageRequest.of(0, 20)));

        assertEquals("PLANNED", registered.status());
        assertEquals("ACTIVE", activated.status());
        assertEquals(registered.id(), found.id());
        assertEquals(1, page.items().size());
    }

    @Test
    void shouldRejectDuplicateTelemetrySourceCode() {
        TelemetryApplicationServiceTestSupport.InMemoryTelemetrySourceRepository repository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetrySourceRepository();
        repository.add(TelemetryDomainTestFixtures.activeSource());

        TelemetrySourceApplicationService service = new TelemetrySourceApplicationService(
                repository,
                new TelemetryRegistrationDomainService(new TelemetrySourcePolicy(), new TelemetryPointPolicy()));

        assertThrows(BusinessRuleViolationException.class, () -> service.registerTelemetrySource(new RegisterTelemetrySourceCommand(
                TelemetryDomainTestFixtures.activeSource().code(),
                TelemetryDomainTestFixtures.localizedName(),
                TelemetryDomainTestFixtures.sourceType(),
                TelemetryDomainTestFixtures.protocol(),
                null,
                null)));
    }
}
