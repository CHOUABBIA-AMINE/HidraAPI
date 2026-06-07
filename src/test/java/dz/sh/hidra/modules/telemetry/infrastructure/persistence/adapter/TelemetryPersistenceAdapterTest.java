/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPersistenceAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter tests for telemetry repository port implementations.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryTypeCatalog;
import dz.sh.hidra.modules.telemetry.domain.support.TelemetryDomainTestFixtures;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingState;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTimestamp;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryDeviceJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryIngestionBatchJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryPointBindingJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryPointJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryReadingJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetrySourceJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryTypeCatalogJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryTypeTranslationJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.support.TelemetryPersistenceTestFixtures;

/**
 * Persistence adapter tests for telemetry repository port implementations.
 */
class TelemetryPersistenceAdapterTest {

    private final TelemetryPersistenceMapper mapper = new TelemetryPersistenceMapper();

    @Test
    void shouldPersistAndQueryCatalogThroughAdapter() {
        TelemetryTypeCatalogJpaRepository catalogRepository = TelemetryPersistenceTestFixtures.catalogRepository();
        TelemetryTypeTranslationJpaRepository translationRepository = TelemetryPersistenceTestFixtures.translationRepository();
        TelemetryCatalogRepositoryAdapter adapter = new TelemetryCatalogRepositoryAdapter(
                catalogRepository,
                translationRepository,
                mapper);

        TelemetryTypeCatalog catalog = TelemetryDomainTestFixtures.catalogWithMandatoryTranslations(true);
        TelemetryTypeCatalog saved = adapter.save(catalog);

        assertEquals(catalog.id(), saved.id());
        assertTrue(adapter.existsByCatalogNameAndCode("POINT_TYPE", TelemetryCode.of("PRESSURE")));
        assertEquals(catalog.id(), adapter.findById(catalog.id()).orElseThrow().id());
        assertEquals(1, adapter.findAll("POINT_TYPE", true, PageRequest.of(0, 20)).items().size());
    }

    @Test
    void shouldPersistAndQuerySourcesDevicesPointsBindingsAndBatchesThroughAdapters() {
        TelemetrySourceJpaRepository sourceRepository = TelemetryPersistenceTestFixtures.sourceRepository();
        TelemetryDeviceJpaRepository deviceRepository = TelemetryPersistenceTestFixtures.deviceRepository();
        TelemetryPointJpaRepository pointRepository = TelemetryPersistenceTestFixtures.pointRepository();
        TelemetryPointBindingJpaRepository bindingRepository = TelemetryPersistenceTestFixtures.bindingRepository();
        TelemetryIngestionBatchJpaRepository batchRepository = TelemetryPersistenceTestFixtures.batchRepository();

        TelemetrySourceRepositoryAdapter sourceAdapter = new TelemetrySourceRepositoryAdapter(sourceRepository, mapper);
        TelemetryDeviceRepositoryAdapter deviceAdapter = new TelemetryDeviceRepositoryAdapter(deviceRepository, mapper);
        TelemetryPointRepositoryAdapter pointAdapter = new TelemetryPointRepositoryAdapter(pointRepository, mapper);
        TelemetryPointBindingRepositoryAdapter bindingAdapter = new TelemetryPointBindingRepositoryAdapter(bindingRepository, mapper);
        TelemetryIngestionBatchRepositoryAdapter batchAdapter = new TelemetryIngestionBatchRepositoryAdapter(batchRepository, mapper);

        sourceAdapter.save(TelemetryDomainTestFixtures.activeSource());
        deviceAdapter.save(TelemetryDomainTestFixtures.activeDevice());
        pointAdapter.save(TelemetryDomainTestFixtures.activeNumericPoint());
        bindingAdapter.save(TelemetryDomainTestFixtures.activeBinding(TelemetryDomainTestFixtures.activeNumericPoint().id()));
        batchAdapter.save(TelemetryDomainTestFixtures.batch(TelemetryIngestionBatchStatus.COMPLETED));

        assertEquals(1, sourceAdapter.findAll(null, null, null, null, PageRequest.of(0, 20)).items().size());
        assertEquals(1, deviceAdapter.findAll(null, TelemetryDomainTestFixtures.activeSource().id(), null, null, PageRequest.of(0, 20)).items().size());
        assertEquals(1, pointAdapter.findAll(null, TelemetryDomainTestFixtures.activeDevice().id(), null, null, null, PageRequest.of(0, 20)).items().size());
        assertEquals(1, bindingAdapter.findActiveByPointId(TelemetryDomainTestFixtures.activeNumericPoint().id()).size());
        assertEquals(1, batchAdapter.findAll(TelemetryDomainTestFixtures.activeSource().id(), null, null, null, PageRequest.of(0, 20)).items().size());
    }

    @Test
    void shouldPersistAndQueryReadingsThroughAdapter() {
        TelemetryReadingJpaRepository readingRepository = TelemetryPersistenceTestFixtures.readingRepository();
        TelemetryReadingRepositoryAdapter adapter = new TelemetryReadingRepositoryAdapter(readingRepository, mapper);

        adapter.save(TelemetryDomainTestFixtures.reading(
                TelemetryDomainTestFixtures.activeNumericPoint().id(),
                TelemetryReadingState.RECEIVED));

        assertTrue(adapter.existsByPointIdAndSourceTimestamp(
                TelemetryDomainTestFixtures.activeNumericPoint().id(),
                TelemetryTimestamp.of(TelemetryDomainTestFixtures.SOURCE_TIMESTAMP)));
        assertEquals("reading-001", adapter.findLatestByPointId(
                TelemetryDomainTestFixtures.activeNumericPoint().id()).orElseThrow().id().value());
        assertEquals(1, adapter.findAll(
                TelemetryDomainTestFixtures.activeNumericPoint().id(),
                null,
                TelemetryReadingState.RECEIVED,
                null,
                null,
                null,
                PageRequest.of(0, 20)).items().size());
    }
}
