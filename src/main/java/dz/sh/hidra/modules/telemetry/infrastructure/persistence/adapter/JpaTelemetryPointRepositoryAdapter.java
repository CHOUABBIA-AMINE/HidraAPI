/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetryPointRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for TelemetryPoint.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryCatalogEntryJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryCatalogEntryJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryPointJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryUnitJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for TelemetryPoint.
 */
@Component
public class JpaTelemetryPointRepositoryAdapter implements TelemetryPointRepositoryPort {

    private static final String POINT_TYPE = "POINT_TYPE";
    private static final String SIGNAL_TYPE = "SIGNAL_TYPE";
    private static final String AGGREGATION_METHOD = "AGGREGATION_METHOD";

    private final TelemetryPointJpaRepository repository;
    private final TelemetryCatalogEntryJpaRepository catalogRepository;
    private final TelemetryUnitJpaRepository unitRepository;

    public JpaTelemetryPointRepositoryAdapter(
            TelemetryPointJpaRepository repository,
            TelemetryCatalogEntryJpaRepository catalogRepository,
            TelemetryUnitJpaRepository unitRepository
    ) {
        this.repository = Objects.requireNonNull(repository, "TelemetryPointJpaRepository must not be null.");
        this.catalogRepository = Objects.requireNonNull(catalogRepository, "TelemetryCatalogEntryJpaRepository must not be null.");
        this.unitRepository = Objects.requireNonNull(unitRepository, "TelemetryUnitJpaRepository must not be null.");
    }

    @Override
    public TelemetryPoint save(TelemetryPoint model) {
        Objects.requireNonNull(model, "TelemetryPoint must not be null.");

        TelemetryCatalogEntryJpaEntity pointType = requireActiveCatalog(model.pointTypeId(), POINT_TYPE);
        TelemetryCatalogEntryJpaEntity signalType = requireActiveCatalog(model.signalTypeId(), SIGNAL_TYPE);
        if (model.defaultAggregationMethodId() != null) {
            requireActiveCatalog(model.defaultAggregationMethodId(), AGGREGATION_METHOD);
        }

        if (model.unitId() != null) {
            var unit = unitRepository.findById(model.unitId())
                    .orElseThrow(() -> new InvalidTelemetryValueException("TelemetryPoint unit must reference an existing TelemetryUnit."));
            if (!unit.active()) {
                throw new InvalidTelemetryValueException("TelemetryPoint unit must reference an active TelemetryUnit.");
            }
        }

        String valueShape = signalType.valueShape();
        if (valueShape == null || valueShape.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryPoint SIGNAL_TYPE must define valueShape.");
        }
        switch (valueShape) {
            case "NUMERIC" -> {
                if (model.unitId() == null && !pointType.numericUnitExempt()) {
                    throw new InvalidTelemetryValueException("Numeric TelemetryPoint requires an active unit unless POINT_TYPE explicitly exempts it.");
                }
            }
            case "TEXT", "BOOLEAN" -> {
                // No unit is required; if supplied, active-unit validation above still applies.
            }
            default -> throw new InvalidTelemetryValueException("TelemetryPoint SIGNAL_TYPE valueShape is unsupported: " + valueShape);
        }

        return TelemetryPersistenceMapper.toDomain(
                repository.save(TelemetryPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<TelemetryPoint> findById(String id) {
        return repository.findById(id).map(TelemetryPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByDeviceIdAndCode(String deviceId, String code) {
        return repository.existsByDeviceIdAndCode(deviceId, code);
    }

    private TelemetryCatalogEntryJpaEntity requireActiveCatalog(String id, String expectedFamily) {
        TelemetryCatalogEntryJpaEntity entry = catalogRepository.findById(id)
                .orElseThrow(() -> new InvalidTelemetryValueException(
                        "TelemetryPoint reference must resolve to an existing " + expectedFamily + " catalog entry."
                ));
        if (!expectedFamily.equals(entry.catalogName()) || !entry.active()) {
            throw new InvalidTelemetryValueException(
                    "TelemetryPoint reference must resolve to an active " + expectedFamily + " catalog entry."
            );
        }
        return entry;
    }
}
