/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetrySourceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Database-backed TelemetrySource adapter with code and catalog-family validation.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetrySourceRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetrySourceJpaRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Persists only telemetry sources whose code and catalog semantics are valid.
 */
@Component
public class JpaTelemetrySourceRepositoryAdapter
        implements TelemetrySourceRepositoryPort {

    private static final String SOURCE_TYPE_CATALOG = "SOURCE_TYPE";
    private static final String PROTOCOL_CATALOG = "PROTOCOL";

    private final TelemetrySourceJpaRepository repository;

    public JpaTelemetrySourceRepositoryAdapter(TelemetrySourceJpaRepository repository) {
        this.repository = Objects.requireNonNull(
                repository,
                "TelemetrySourceJpaRepository must not be null."
        );
    }

    @Override
    public TelemetrySource save(TelemetrySource model) {
        Objects.requireNonNull(model, "TelemetrySource must not be null.");

        if (repository.existsByCodeAndIdNot(model.code(), model.id())) {
            throw new InvalidTelemetryValueException(
                    "TelemetrySource code must be unique: " + model.code()
            );
        }
        if (!repository.existsActiveCatalogEntry(
                model.sourceTypeId(),
                SOURCE_TYPE_CATALOG
        )) {
            throw new InvalidTelemetryValueException(
                    "TelemetrySource source type must resolve to an active SOURCE_TYPE entry: "
                            + model.sourceTypeId()
            );
        }
        if (!repository.existsActiveCatalogEntry(
                model.protocolId(),
                PROTOCOL_CATALOG
        )) {
            throw new InvalidTelemetryValueException(
                    "TelemetrySource protocol must resolve to an active PROTOCOL entry: "
                            + model.protocolId()
            );
        }

        return TelemetryPersistenceMapper.toDomain(
                repository.save(TelemetryPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<TelemetrySource> findById(String id) {
        return repository.findById(id).map(TelemetryPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByCode(String code) {
        return code != null && !code.isBlank() && repository.existsByCode(code.trim());
    }

    @Override
    public boolean activeCatalogEntryExists(String id, String catalogName) {
        return id != null
                && !id.isBlank()
                && catalogName != null
                && !catalogName.isBlank()
                && repository.existsActiveCatalogEntry(id.trim(), catalogName.trim());
    }
}
