/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryDomainServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.service
 *
 * @Description : Domain tests for telemetry domain services.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryDevice;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryIngestionBatch;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPointBinding;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryTypeTranslation;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryBindingPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryCatalogPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryIngestionPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryPointPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryReadingPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetrySourcePolicy;
import dz.sh.hidra.modules.telemetry.domain.support.TelemetryDomainTestFixtures;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCorrelationId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingState;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingValue;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTimestamp;

/**
 * Domain tests for telemetry domain services.
 */
class TelemetryDomainServiceTest {

    @Test
    void registrationServiceShouldRegisterAndActivateTelemetryAssets() {
        TelemetryRegistrationDomainService service = new TelemetryRegistrationDomainService(
                new TelemetrySourcePolicy(),
                new TelemetryPointPolicy());

        TelemetrySource source = service.registerSource(
                TelemetryDomainTestFixtures.code("SCADA-NEW"),
                TelemetryDomainTestFixtures.localizedName(),
                TelemetryDomainTestFixtures.sourceType(),
                TelemetryDomainTestFixtures.protocol(),
                null,
                null);

        TelemetryDevice device = service.registerDevice(
                TelemetryDomainTestFixtures.activeSource(),
                TelemetryDomainTestFixtures.code("RTU-NEW"),
                TelemetryDomainTestFixtures.localizedName(),
                TelemetryDomainTestFixtures.deviceType(),
                null);

        TelemetryPoint point = service.registerPoint(
                TelemetryDomainTestFixtures.activeDevice(),
                TelemetryDomainTestFixtures.code("PT-NEW"),
                TelemetryDomainTestFixtures.localizedName(),
                TelemetryDomainTestFixtures.pointType(),
                TelemetryDomainTestFixtures.signalType("NUMERIC"),
                TelemetryDomainTestFixtures.unit(),
                TelemetryDomainTestFixtures.aggregation(),
                null,
                null);

        assertEquals(TelemetrySourceStatus.PLANNED, source.status());
        assertEquals(TelemetryDeviceStatus.PLANNED, device.status());
        assertEquals(TelemetryPointStatus.PLANNED, point.status());
        assertEquals(TelemetryDeviceStatus.ACTIVE, service.activateDevice(TelemetryDomainTestFixtures.activeSource(), device).status());
        assertEquals(TelemetryPointStatus.ACTIVE, service.activatePoint(TelemetryDomainTestFixtures.activeDevice(), point).status());
    }

    @Test
    void registrationServiceShouldRejectNumericPointWithoutUnit() {
        TelemetryRegistrationDomainService service = new TelemetryRegistrationDomainService(
                new TelemetrySourcePolicy(),
                new TelemetryPointPolicy());

        assertThrows(BusinessRuleViolationException.class, () -> service.registerPoint(
                TelemetryDomainTestFixtures.activeDevice(),
                TelemetryDomainTestFixtures.code("PT-NEW"),
                TelemetryDomainTestFixtures.localizedName(),
                TelemetryDomainTestFixtures.pointType(),
                TelemetryDomainTestFixtures.signalType("NUMERIC"),
                null,
                TelemetryDomainTestFixtures.aggregation(),
                null,
                null));
    }

    @Test
    void bindingServiceShouldCreateAndCloseBindingsButRejectDuplicates() {
        TelemetryBindingDomainService service = new TelemetryBindingDomainService(new TelemetryBindingPolicy());
        TelemetryPoint point = TelemetryDomainTestFixtures.activeNumericPoint();
        TelemetryPointBinding existing = TelemetryDomainTestFixtures.activeBinding(point.id());

        assertThrows(BusinessRuleViolationException.class, () -> service.bindPointToTopology(
                point,
                TelemetryDomainTestFixtures.topologyAssetReference(),
                TelemetryDomainTestFixtures.bindingRole(),
                List.of(existing),
                TelemetryDomainTestFixtures.CREATED_AT));

        assertEquals(false, service.closeBinding(existing, TelemetryDomainTestFixtures.UPDATED_AT).active());
    }

    @Test
    void readingServiceShouldReceiveAndAcceptReadingsForActivePoint() {
        TelemetryReadingDomainService service = new TelemetryReadingDomainService(
                new TelemetryPointPolicy(),
                new TelemetryReadingPolicy());

        TelemetryPoint point = TelemetryDomainTestFixtures.activeNumericPoint();
        TelemetryReading reading = service.receiveReading(
                point,
                TelemetryReadingValue.numeric(BigDecimal.valueOf(12.5)),
                TelemetryDomainTestFixtures.quality(),
                TelemetryTimestamp.of(TelemetryDomainTestFixtures.SOURCE_TIMESTAMP),
                null,
                TelemetryCorrelationId.of("corr-001"));

        assertEquals(TelemetryReadingState.RECEIVED, reading.state());
        assertEquals(TelemetryReadingState.ACCEPTED, service.acceptReading(point, reading).state());
    }

    @Test
    void ingestionServiceShouldStartProcessAndCompleteBatchesForActiveSource() {
        TelemetryIngestionDomainService service = new TelemetryIngestionDomainService(new TelemetryIngestionPolicy());

        TelemetryIngestionBatch started = service.startBatch(
                TelemetryDomainTestFixtures.activeSource(),
                TelemetryCorrelationId.of("corr-001"));
        TelemetryIngestionBatch processing = service.markProcessing(started);
        TelemetryIngestionBatch completed = service.completeBatch(processing, 10, 10, 0, 0, 0);

        assertEquals(TelemetryIngestionBatchStatus.RECEIVED, started.status());
        assertEquals(TelemetryIngestionBatchStatus.PROCESSING, processing.status());
        assertEquals(TelemetryIngestionBatchStatus.COMPLETED, completed.status());

        assertThrows(BusinessRuleViolationException.class, () -> service.startBatch(
                TelemetryDomainTestFixtures.source(TelemetrySourceStatus.INACTIVE),
                TelemetryCorrelationId.of("corr-002")));
    }

    @Test
    void catalogServiceShouldSelectLocalizedTranslationAndRequireUserFacingCatalog() {
        TelemetryCatalogDomainService service = new TelemetryCatalogDomainService(new TelemetryCatalogPolicy());

        TelemetryTypeTranslation english = service.selectTranslation(
                TelemetryDomainTestFixtures.catalogWithMandatoryTranslations(true),
                "en");

        TelemetryTypeTranslation fallback = service.selectTranslation(
                TelemetryDomainTestFixtures.catalogWithOnlyFrenchTranslation(true),
                "de");

        assertEquals("Pressure", english.name().value());
        assertEquals("Pression", fallback.name().value());

        assertThrows(BusinessRuleViolationException.class, () -> service.requireUserFacingCatalog(
                TelemetryDomainTestFixtures.catalogWithOnlyFrenchTranslation(true),
                "POINT_TYPE"));
    }
}
