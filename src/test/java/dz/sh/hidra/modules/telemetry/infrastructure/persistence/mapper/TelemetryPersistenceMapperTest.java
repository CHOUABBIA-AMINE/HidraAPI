/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPersistenceMapperTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper
 *
 * @Description : Persistence mapper tests for telemetry domain and JPA entity conversion.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.telemetry.domain.model.TelemetryDevice;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryIngestionBatch;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPointBinding;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryTypeCatalog;
import dz.sh.hidra.modules.telemetry.domain.support.TelemetryDomainTestFixtures;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingState;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryDeviceJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryIngestionBatchJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryPointBindingJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryPointJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryReadingJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetrySourceJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryTypeCatalogJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.support.TelemetryPersistenceTestFixtures;

/**
 * Persistence mapper tests for telemetry domain and JPA entity conversion.
 */
class TelemetryPersistenceMapperTest {

    private final TelemetryPersistenceMapper mapper = new TelemetryPersistenceMapper();

    @Test
    void shouldMapCatalogEntityWithTranslationsToDomainAndBack() {
        TelemetryTypeCatalog catalog = mapper.toDomain(
                TelemetryPersistenceTestFixtures.catalogEntity(),
                List.of(
                        TelemetryPersistenceTestFixtures.frenchTranslationEntity(),
                        TelemetryPersistenceTestFixtures.arabicTranslationEntity(),
                        TelemetryPersistenceTestFixtures.englishTranslationEntity()));

        TelemetryTypeCatalogJpaEntity entity = mapper.toEntity(catalog);

        assertEquals("catalog-point-type-pressure", catalog.id().value());
        assertEquals("POINT_TYPE", catalog.catalogName());
        assertEquals("PRESSURE", catalog.code().value());
        assertEquals(3, catalog.translations().size());
        assertEquals("catalog-point-type-pressure", entity.getId());
        assertEquals("POINT_TYPE", entity.getCatalogName());
    }

    @Test
    void shouldMapSourceDevicePointAndBindingInBothDirections() {
        TelemetrySource source = TelemetryDomainTestFixtures.activeSource();
        TelemetryDevice device = TelemetryDomainTestFixtures.activeDevice();
        TelemetryPoint point = TelemetryDomainTestFixtures.activeNumericPoint();
        TelemetryPointBinding binding = TelemetryDomainTestFixtures.activeBinding(point.id());

        TelemetrySourceJpaEntity sourceEntity = mapper.toEntity(source);
        TelemetryDeviceJpaEntity deviceEntity = mapper.toEntity(device);
        TelemetryPointJpaEntity pointEntity = mapper.toEntity(point);
        TelemetryPointBindingJpaEntity bindingEntity = mapper.toEntity(binding);

        assertEquals(source.id(), mapper.toDomain(sourceEntity).id());
        assertEquals(device.id(), mapper.toDomain(deviceEntity).id());
        assertEquals(point.id(), mapper.toDomain(pointEntity).id());
        assertEquals(binding.id(), mapper.toDomain(bindingEntity).id());
        assertEquals("source-type-scada", sourceEntity.getSourceTypeId());
        assertEquals("device-type-rtu", deviceEntity.getDeviceTypeId());
        assertEquals("point-type-pressure", pointEntity.getPointTypeId());
        assertEquals("PIPELINE", bindingEntity.getTopologyAssetTypeCode());
    }

    @Test
    void shouldMapReadingsAndBatchesInBothDirections() {
        TelemetryReading reading = TelemetryDomainTestFixtures.reading(
                TelemetryDomainTestFixtures.activeNumericPoint().id(),
                TelemetryReadingState.RECEIVED);
        TelemetryIngestionBatch batch = TelemetryDomainTestFixtures.batch(TelemetryIngestionBatchStatus.COMPLETED);

        TelemetryReadingJpaEntity readingEntity = mapper.toEntity(reading);
        TelemetryIngestionBatchJpaEntity batchEntity = mapper.toEntity(batch);

        assertEquals(reading.id(), mapper.toDomain(readingEntity).id());
        assertEquals(batch.id(), mapper.toDomain(batchEntity).id());
        assertEquals(BigDecimal.valueOf(42.25), readingEntity.getNumericValue());
        assertNull(readingEntity.getTextValue());
        assertEquals("COMPLETED", batchEntity.getStatus());
    }

    @Test
    void shouldMapJpaEntitiesFromPersistenceFixturesToDomain() {
        TelemetrySource source = mapper.toDomain(TelemetryPersistenceTestFixtures.sourceEntity());
        TelemetryPoint point = mapper.toDomain(TelemetryPersistenceTestFixtures.pointEntity());
        TelemetryReading reading = mapper.toDomain(TelemetryPersistenceTestFixtures.readingEntity());

        assertEquals("source-001", source.id().value());
        assertEquals("PT-001", point.code().value());
        assertEquals(BigDecimal.valueOf(42.25), reading.value().numericValue());
    }
}
