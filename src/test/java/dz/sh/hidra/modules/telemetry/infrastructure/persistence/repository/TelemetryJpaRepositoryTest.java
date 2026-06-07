/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryJpaRepositoryTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository
 *
 * @Description : JPA repository tests for telemetry persistence entities and derived queries.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import dz.sh.hidra.modules.telemetry.infrastructure.persistence.support.TelemetryPersistenceTestFixtures;

/**
 * JPA repository tests for telemetry persistence entities and derived queries.
 */
class TelemetryJpaRepositoryTest {

    @Autowired
    private TelemetryTypeCatalogJpaRepository catalogRepository;

    @Autowired
    private TelemetryTypeTranslationJpaRepository translationRepository;

    @Autowired
    private TelemetrySourceJpaRepository sourceRepository;

    @Autowired
    private TelemetryDeviceJpaRepository deviceRepository;

    @Autowired
    private TelemetryPointJpaRepository pointRepository;

    @Autowired
    private TelemetryPointBindingJpaRepository bindingRepository;

    @Autowired
    private TelemetryIngestionBatchJpaRepository batchRepository;

    @Autowired
    private TelemetryReadingJpaRepository readingRepository;

    @Test
    void shouldPersistCatalogAndTranslationsAndResolveDerivedQueries() {
        catalogRepository.save(TelemetryPersistenceTestFixtures.catalogEntity());
        translationRepository.save(TelemetryPersistenceTestFixtures.frenchTranslationEntity());
        translationRepository.save(TelemetryPersistenceTestFixtures.arabicTranslationEntity());

        assertTrue(catalogRepository.existsByCatalogNameAndCode("POINT_TYPE", "PRESSURE"));
        assertEquals("PRESSURE", catalogRepository.findByCatalogNameAndCode("POINT_TYPE", "PRESSURE").orElseThrow().getCode());
        assertEquals(2, translationRepository.findByTypeId("catalog-point-type-pressure").size());
        assertEquals("Pression", translationRepository.findByTypeIdAndLocale("catalog-point-type-pressure", "fr").orElseThrow().getName());
    }

    @Test
    void shouldPersistCoreTelemetryEntitiesAndResolveRepositoryQueries() {
        sourceRepository.save(TelemetryPersistenceTestFixtures.sourceEntity());
        deviceRepository.save(TelemetryPersistenceTestFixtures.deviceEntity());
        pointRepository.save(TelemetryPersistenceTestFixtures.pointEntity());
        bindingRepository.save(TelemetryPersistenceTestFixtures.bindingEntity());
        batchRepository.save(TelemetryPersistenceTestFixtures.batchEntity());
        readingRepository.save(TelemetryPersistenceTestFixtures.readingEntity());
        readingRepository.save(TelemetryPersistenceTestFixtures.laterReadingEntity());

        assertEquals("SCADA-TRC-01", sourceRepository.findByCode("SCADA-TRC-01").orElseThrow().getCode());
        assertEquals(1, deviceRepository.findBySourceId("source-001", org.springframework.data.domain.PageRequest.of(0, 10)).getContent().size());
        assertEquals(1, pointRepository.findByDeviceId("device-001", org.springframework.data.domain.PageRequest.of(0, 10)).getContent().size());
        assertEquals(1, bindingRepository.findByPointIdAndActiveTrue("point-001").size());
        assertEquals(1, batchRepository.findBySourceIdAndStatus("source-001", "COMPLETED", org.springframework.data.domain.PageRequest.of(0, 10)).getContent().size());
        assertEquals("reading-002", readingRepository.findFirstByPointIdOrderBySourceTimestampDesc("point-001").orElseThrow().getId());
        assertTrue(readingRepository.existsByPointIdAndSourceTimestamp("point-001", TelemetryPersistenceTestFixtures.SOURCE_TIMESTAMP));
    }
}
