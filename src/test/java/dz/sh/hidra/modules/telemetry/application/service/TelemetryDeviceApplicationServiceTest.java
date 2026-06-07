/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryDeviceApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Application service tests for telemetry device use cases.
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
import dz.sh.hidra.modules.telemetry.application.command.ActivateTelemetryDeviceCommand;
import dz.sh.hidra.modules.telemetry.application.command.RegisterTelemetryDeviceCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryDeviceDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPageDto;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryDeviceByIdQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryDevicesQuery;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceStatus;

/**
 * Application service tests for telemetry device use cases.
 */
class TelemetryDeviceApplicationServiceTest {

    @Test
    void shouldRegisterActivateGetAndListTelemetryDevices() {
        TelemetryApplicationServiceTestSupport.InMemoryTelemetrySourceRepository sourceRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetrySourceRepository();
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryDeviceRepository deviceRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryDeviceRepository();
        sourceRepository.add(TelemetryDomainTestFixtures.activeSource());

        TelemetryDeviceApplicationService service = new TelemetryDeviceApplicationService(
                sourceRepository,
                deviceRepository,
                new TelemetryRegistrationDomainService(new TelemetrySourcePolicy(), new TelemetryPointPolicy()));

        TelemetryDeviceDto registered = service.registerTelemetryDevice(new RegisterTelemetryDeviceCommand(
                TelemetryDomainTestFixtures.activeSource().id(),
                TelemetryDomainTestFixtures.code("RTU-NEW"),
                TelemetryDomainTestFixtures.localizedName(),
                TelemetryDomainTestFixtures.deviceType(),
                null));

        TelemetryDeviceDto activated = service.activateTelemetryDevice(new ActivateTelemetryDeviceCommand(
                TelemetryDomainTestFixtures.activeSource().id(),
                TelemetryDeviceId.of(registered.id())));
        TelemetryDeviceDto found = service.getTelemetryDevice(new GetTelemetryDeviceByIdQuery(
                TelemetryDeviceId.of(registered.id())));
        TelemetryPageDto<TelemetryDeviceDto> page = service.listTelemetryDevices(new ListTelemetryDevicesQuery(
                "RTU",
                TelemetryDomainTestFixtures.activeSource().id(),
                null,
                TelemetryDeviceStatus.ACTIVE,
                PageRequest.of(0, 20)));

        assertEquals("PLANNED", registered.status());
        assertEquals("ACTIVE", activated.status());
        assertEquals(registered.id(), found.id());
        assertEquals(1, page.items().size());
    }

    @Test
    void shouldRejectDuplicateTelemetryDeviceCode() {
        TelemetryApplicationServiceTestSupport.InMemoryTelemetrySourceRepository sourceRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetrySourceRepository();
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryDeviceRepository deviceRepository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryDeviceRepository();
        sourceRepository.add(TelemetryDomainTestFixtures.activeSource());
        deviceRepository.add(TelemetryDomainTestFixtures.activeDevice());

        TelemetryDeviceApplicationService service = new TelemetryDeviceApplicationService(
                sourceRepository,
                deviceRepository,
                new TelemetryRegistrationDomainService(new TelemetrySourcePolicy(), new TelemetryPointPolicy()));

        assertThrows(BusinessRuleViolationException.class, () -> service.registerTelemetryDevice(new RegisterTelemetryDeviceCommand(
                TelemetryDomainTestFixtures.activeSource().id(),
                TelemetryDomainTestFixtures.activeDevice().code(),
                TelemetryDomainTestFixtures.localizedName(),
                TelemetryDomainTestFixtures.deviceType(),
                null)));
    }
}
