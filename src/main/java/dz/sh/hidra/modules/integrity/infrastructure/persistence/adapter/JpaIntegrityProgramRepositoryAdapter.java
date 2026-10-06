/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrityProgramRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrityProgram.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.IntegrityProgramRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityProgram;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.entity.IntegrityCatalogEntryJpaEntity;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.IntegrityCatalogEntryJpaRepository;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.IntegrityProgramJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrityProgram.
 */
@Component
public class JpaIntegrityProgramRepositoryAdapter implements IntegrityProgramRepositoryPort {

    private static final String PROGRAM_TYPE_FAMILY = "INTEGRITY_PROGRAM_TYPE";

    private final IntegrityProgramJpaRepository repository;
    private final IntegrityCatalogEntryJpaRepository catalogRepository;

    public JpaIntegrityProgramRepositoryAdapter(
            IntegrityProgramJpaRepository repository,
            IntegrityCatalogEntryJpaRepository catalogRepository
    ) {
        this.repository = Objects.requireNonNull(repository, "IntegrityProgramJpaRepository must not be null.");
        this.catalogRepository = Objects.requireNonNull(
                catalogRepository,
                "IntegrityCatalogEntryJpaRepository must not be null."
        );
    }

    @Override
    public IntegrityProgram save(IntegrityProgram model) {
        Objects.requireNonNull(model, "IntegrityProgram must not be null.");
        IntegrityCatalogEntryJpaEntity programType = catalogRepository.findById(model.programTypeId())
                .orElseThrow(() -> new InvalidIntegrityValueException(
                        "IntegrityProgram programTypeId must reference an existing Integrity catalog entry."
                ));
        if (!PROGRAM_TYPE_FAMILY.equals(programType.catalogName()) || !programType.active()) {
            throw new InvalidIntegrityValueException(
                    "IntegrityProgram programTypeId must reference an active " + PROGRAM_TYPE_FAMILY + " catalog entry."
            );
        }
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrityProgram> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
