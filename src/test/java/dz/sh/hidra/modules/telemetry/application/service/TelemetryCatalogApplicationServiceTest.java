/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCatalogApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Application service tests for telemetry catalog use cases.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryCatalogDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPageDto;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryCatalogTypeQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryCatalogTypesQuery;
import dz.sh.hidra.modules.telemetry.application.query.ResolveTelemetryCatalogTypeQuery;
import dz.sh.hidra.modules.telemetry.application.support.TelemetryApplicationServiceTestSupport;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryCatalogPolicy;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryCatalogDomainService;
import dz.sh.hidra.modules.telemetry.domain.support.TelemetryDomainTestFixtures;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;

/**
 * Application service tests for telemetry catalog use cases.
 */
class TelemetryCatalogApplicationServiceTest {

    @Test
    void shouldGetListAndResolveLocalizedTelemetryCatalogTypes() {
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryCatalogRepository repository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryCatalogRepository();
        repository.add(TelemetryDomainTestFixtures.catalogWithMandatoryTranslations(true));

        TelemetryCatalogApplicationService service = new TelemetryCatalogApplicationService(
                repository,
                new TelemetryCatalogDomainService(new TelemetryCatalogPolicy()));

        TelemetryCatalogDto byId = service.getTelemetryCatalogType(new GetTelemetryCatalogTypeQuery(
                TelemetryDomainTestFixtures.catalogWithMandatoryTranslations(true).id(),
                "en"));
        TelemetryCatalogDto resolved = service.resolveTelemetryCatalogType(new ResolveTelemetryCatalogTypeQuery(
                "POINT_TYPE",
                TelemetryCode.of("PRESSURE"),
                true,
                "fr"));
        TelemetryPageDto<TelemetryCatalogDto> page = service.listTelemetryCatalogTypes(new ListTelemetryCatalogTypesQuery(
                "POINT_TYPE",
                "ar",
                true,
                PageRequest.of(0, 20)));

        assertEquals("Pressure", byId.resolvedName());
        assertEquals("Pression", resolved.resolvedName());
        assertEquals(1, page.items().size());
        assertEquals("ضغط", page.items().get(0).resolvedName());
    }

    @Test
    void shouldRejectInactiveCatalogWhenResolveRequiresActive() {
        TelemetryApplicationServiceTestSupport.InMemoryTelemetryCatalogRepository repository =
                new TelemetryApplicationServiceTestSupport.InMemoryTelemetryCatalogRepository();
        repository.add(TelemetryDomainTestFixtures.catalogWithMandatoryTranslations(false));

        TelemetryCatalogApplicationService service = new TelemetryCatalogApplicationService(
                repository,
                new TelemetryCatalogDomainService(new TelemetryCatalogPolicy()));

        assertThrows(BusinessRuleViolationException.class, () -> service.resolveTelemetryCatalogType(
                new ResolveTelemetryCatalogTypeQuery("POINT_TYPE", TelemetryCode.of("PRESSURE"), true, "fr")));
    }
}
