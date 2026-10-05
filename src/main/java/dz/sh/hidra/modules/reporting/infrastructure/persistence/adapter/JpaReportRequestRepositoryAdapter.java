/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportRequestRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ReportRequest.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.reporting.application.port.out.ReportRequestRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ReportRequest;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper.ReportingPersistenceMapper;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportAccessPolicyJpaRepository;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportRequestJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ReportRequest.
 */
@Component
public class JpaReportRequestRepositoryAdapter implements ReportRequestRepositoryPort {

    private final ReportRequestJpaRepository repository;
    private final ReportAccessPolicyJpaRepository accessPolicyRepository;

    public JpaReportRequestRepositoryAdapter(
            ReportRequestJpaRepository repository,
            ReportAccessPolicyJpaRepository accessPolicyRepository
    ) {
        this.repository = Objects.requireNonNull(repository, "ReportRequestJpaRepository must not be null.");
        this.accessPolicyRepository = Objects.requireNonNull(
                accessPolicyRepository,
                "ReportAccessPolicyJpaRepository must not be null."
        );
    }

    @Override
    public ReportRequest save(ReportRequest model) {
        return ReportingPersistenceMapper.toDomain(repository.save(ReportingPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ReportRequest> findById(String id) {
        return repository.findById(id).map(ReportingPersistenceMapper::toDomain);
    }

    @Override
    public List<AccessPolicyView> accessPoliciesForDefinition(String reportDefinitionId) {
        if (reportDefinitionId == null || reportDefinitionId.isBlank()) {
            return List.of();
        }
        String normalized = reportDefinitionId.trim();
        return accessPolicyRepository.findAll().stream()
                .filter(policy -> normalized.equals(policy.reportDefinitionId()) && policy.restricted())
                .map(policy -> new AccessPolicyView(
                        policy.scopeType().name(),
                        policy.scopeReferenceId(),
                        policy.permissionCode()
                ))
                .toList();
    }
}
