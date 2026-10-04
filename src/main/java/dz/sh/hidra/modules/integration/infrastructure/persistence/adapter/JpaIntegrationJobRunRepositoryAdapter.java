/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationJobRunRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter enforcing IntegrationJobRun execution governance.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationJobRunRepositoryPort;
import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import dz.sh.hidra.modules.integration.domain.model.IntegrationJobRun;
import dz.sh.hidra.modules.integration.domain.value.JobTriggerType;
import dz.sh.hidra.modules.integration.domain.value.MappingProfileStatus;
import dz.sh.hidra.modules.integration.infrastructure.persistence.entity.IntegrationJobDefinitionJpaEntity;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.ConnectorInstanceJpaRepository;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationCatalogEntryJpaRepository;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationJobDefinitionJpaRepository;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationJobRunJpaRepository;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationMappingProfileJpaRepository;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Database-backed repository adapter for IntegrationJobRun.
 */
@Component
public class JpaIntegrationJobRunRepositoryAdapter implements IntegrationJobRunRepositoryPort {

    private static final String JOB_TYPE = "JOB_TYPE";

    private final IntegrationJobRunJpaRepository repository;
    private final IntegrationJobDefinitionJpaRepository jobDefinitionRepository;
    private final ConnectorInstanceJpaRepository connectorRepository;
    private final IntegrationMappingProfileJpaRepository mappingProfileRepository;
    private final IntegrationCatalogEntryJpaRepository catalogRepository;

    public JpaIntegrationJobRunRepositoryAdapter(
            IntegrationJobRunJpaRepository repository,
            IntegrationJobDefinitionJpaRepository jobDefinitionRepository,
            ConnectorInstanceJpaRepository connectorRepository,
            IntegrationMappingProfileJpaRepository mappingProfileRepository,
            IntegrationCatalogEntryJpaRepository catalogRepository
    ) {
        this.repository = Objects.requireNonNull(repository, "IntegrationJobRunJpaRepository must not be null.");
        this.jobDefinitionRepository = Objects.requireNonNull(
                jobDefinitionRepository,
                "IntegrationJobDefinitionJpaRepository must not be null."
        );
        this.connectorRepository = Objects.requireNonNull(
                connectorRepository,
                "ConnectorInstanceJpaRepository must not be null."
        );
        this.mappingProfileRepository = Objects.requireNonNull(
                mappingProfileRepository,
                "IntegrationMappingProfileJpaRepository must not be null."
        );
        this.catalogRepository = Objects.requireNonNull(
                catalogRepository,
                "IntegrationCatalogEntryJpaRepository must not be null."
        );
    }

    @Override
    @Transactional
    public IntegrationJobRun save(IntegrationJobRun model) {
        Objects.requireNonNull(model, "IntegrationJobRun must not be null.");
        requireAuditTimestamps(model);

        var existing = repository.findById(model.id());
        if (existing.isPresent()) {
            IntegrationJobRun previous = IntegrationPersistenceMapper.toDomain(existing.orElseThrow());
            if (!previous.jobDefinitionId().equals(model.jobDefinitionId())) {
                throw new InvalidIntegrationValueException(
                        "IntegrationJobRun job definition cannot change after creation."
                );
            }
            if (previous.runNumber() != model.runNumber()) {
                throw new InvalidIntegrationValueException(
                        "IntegrationJobRun run number cannot change after allocation."
                );
            }
            model.validateTransitionFrom(previous.status());
            return IntegrationPersistenceMapper.toDomain(
                    repository.save(IntegrationPersistenceMapper.toEntity(model))
            );
        }

        validateStartEligibility(model);
        IntegrationJobRun unallocated = model.withRunNumber(0L);
        repository.saveAndFlush(IntegrationPersistenceMapper.toEntity(unallocated));
        long allocatedRunNumber = repository.findAllocatedRunNumberById(model.id())
                .orElseThrow(() -> new InvalidIntegrationValueException(
                        "IntegrationJobRun database run-number allocation did not produce a value."
                ));
        return unallocated.withRunNumber(allocatedRunNumber);
    }

    @Override
    public Optional<IntegrationJobRun> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }

    private void validateStartEligibility(IntegrationJobRun model) {
        IntegrationJobDefinitionJpaEntity job = jobDefinitionRepository.findById(model.jobDefinitionId())
                .orElseThrow(() -> new InvalidIntegrationValueException(
                        "IntegrationJobRun must reference an existing IntegrationJobDefinition."
                ));
        if (!job.active()) {
            throw new InvalidIntegrationValueException(
                    "IntegrationJobRun cannot start from an inactive IntegrationJobDefinition."
            );
        }

        var jobType = catalogRepository.findById(job.jobTypeId())
                .orElseThrow(() -> new InvalidIntegrationValueException(
                        "IntegrationJobDefinition job type must reference an existing Integration catalog entry."
                ));
        if (!JOB_TYPE.equals(jobType.catalogName()) || !jobType.active()) {
            throw new InvalidIntegrationValueException(
                    "IntegrationJobDefinition job type must reference an ACTIVE JOB_TYPE entry."
            );
        }

        if (model.triggerType() == JobTriggerType.MANUAL) {
            if (!job.manualRunAllowed()) {
                throw new InvalidIntegrationValueException(
                        "IntegrationJobDefinition does not allow MANUAL execution."
                );
            }
            if (model.triggeredByActorId() == null) {
                throw new InvalidIntegrationValueException(
                        "MANUAL IntegrationJobRun requires actor provenance."
                );
            }
        } else {
            var connector = connectorRepository.findById(job.connectorInstanceId())
                    .orElseThrow(() -> new InvalidIntegrationValueException(
                            "Automated IntegrationJobRun requires an existing ConnectorInstance."
                    ));
            if (!connector.active()) {
                throw new InvalidIntegrationValueException(
                        "Automated IntegrationJobRun requires an active ConnectorInstance."
                );
            }
        }

        String jobTypeCode = jobType.code().trim().toUpperCase(Locale.ROOT);
        if ("IMPORT".equals(jobTypeCode) || "SYNC".equals(jobTypeCode)) {
            if (job.mappingProfileId() == null || job.mappingProfileId().isBlank()) {
                throw new InvalidIntegrationValueException(
                        "IMPORT/SYNC IntegrationJobDefinition requires a mapping profile in the current repository baseline."
                );
            }
            var mapping = mappingProfileRepository.findById(job.mappingProfileId())
                    .orElseThrow(() -> new InvalidIntegrationValueException(
                            "IMPORT/SYNC IntegrationJobDefinition mapping profile must exist."
                    ));
            if (model.triggerType() != JobTriggerType.MANUAL
                    && mapping.status() != MappingProfileStatus.ACTIVE) {
                throw new InvalidIntegrationValueException(
                        "Automated IMPORT/SYNC IntegrationJobRun requires an ACTIVE mapping profile."
                );
            }
            if (job.targetModule() != null
                    && !job.targetModule().isBlank()
                    && !job.targetModule().trim().equals(mapping.targetModule())) {
                throw new InvalidIntegrationValueException(
                        "IntegrationJobDefinition target module must match its mapping profile target module."
                );
            }
        }
    }

    private static void requireAuditTimestamps(IntegrationJobRun model) {
        if (model.createdAt() == null || model.updatedAt() == null) {
            throw new InvalidIntegrationValueException(
                    "IntegrationJobRun createdAt and updatedAt are required at persistence."
            );
        }
    }
}
