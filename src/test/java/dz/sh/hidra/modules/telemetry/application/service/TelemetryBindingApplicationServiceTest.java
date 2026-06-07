/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryBindingApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Application service tests for telemetry point binding use cases.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.application.command.BindTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.command.CloseTelemetryPointBindingCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPageDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPointBindingDto;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryPointBindingsQuery;
import dz.sh.hidra.modules.telemetry.application.support.TelemetryApplicationServiceTestSupport;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryBindingPolicy;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryBindingDomainService;
import dz.sh.hidra.modules.telemetry.domain.support.TelemetryDomainTestFixtures;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointBindingId;

/**
 * Application service tests for telemetry point binding use cases.
 */
class TelemetryBindingApplicationServiceTest {

    @Test
    void shouldBindCloseAndListTelemetryPointBindings() {
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryPointRepository pointRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryPointRepository();
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryPointBindingRepository bindingRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryPointBindingRepository();
        pointRepository.add(TelemetryDomainTestFixtures.activeNumericPoint());

        TelemetryBindingApplicationService service = new TelemetryBindingApplicationService(
                pointRepository,
                bindingRepository,
                new TelemetryApplicationServiceTestSupport.AllowingTelemetryTopologyAssetLookup(),
                new TelemetryBindingDomainService(new TelemetryBindingPolicy()));

        TelemetryPointBindingDto bound = service.bindTelemetryPoint(new BindTelemetryPointCommand(
                TelemetryDomainTestFixtures.activeNumericPoint().id(),
                TelemetryDomainTestFixtures.topologyAssetReference(),
                TelemetryDomainTestFixtures.bindingRole(),
                TelemetryDomainTestFixtures.CREATED_AT));
        TelemetryPointBindingDto closed = service.closeTelemetryPointBinding(new CloseTelemetryPointBindingCommand(
                TelemetryPointBindingId.of(bound.id()),
                TelemetryDomainTestFixtures.UPDATED_AT));
        TelemetryPageDto<TelemetryPointBindingDto> page = service.listTelemetryPointBindings(new ListTelemetryPointBindingsQuery(
                TelemetryDomainTestFixtures.activeNumericPoint().id(),
                null,
                null,
                null,
                false,
                PageRequest.of(0, 20)));

        assertEquals(Boolean.TRUE, bound.active());
        assertEquals(Boolean.FALSE, closed.active());
        assertEquals(1, page.items().size());
    }

    @Test
    void shouldRejectBindingWhenTopologyAssetDoesNotExist() {
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryPointRepository pointRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryPointRepository();
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryPointBindingRepository bindingRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryPointBindingRepository();
        pointRepository.add(TelemetryDomainTestFixtures.activeNumericPoint());

        TelemetryBindingApplicationService service = new TelemetryBindingApplicationService(
                pointRepository,
                bindingRepository,
                new TelemetryApplicationServiceTestSupport.RejectingTelemetryTopologyAssetLookup(),
                new TelemetryBindingDomainService(new TelemetryBindingPolicy()));

        assertThrows(BusinessRuleViolationException.class, () -> service.bindTelemetryPoint(new BindTelemetryPointCommand(
                TelemetryDomainTestFixtures.activeNumericPoint().id(),
                TelemetryDomainTestFixtures.topologyAssetReference(),
                TelemetryDomainTestFixtures.bindingRole(),
                TelemetryDomainTestFixtures.CREATED_AT)));
    }
}
