/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportDefinitionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ReportDefinition semantic integrity.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.reporting.application.port.out.ReportDefinitionRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
import dz.sh.hidra.modules.reporting.domain.model.ReportDefinition;
import dz.sh.hidra.modules.reporting.domain.value.ReportDefinitionStatus;
import dz.sh.hidra.modules.reporting.domain.value.ReportTemplateVersionStatus;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper.ReportingPersistenceMapper;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportAccessPolicyJpaRepository;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportCatalogEntryJpaRepository;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportDefinitionJpaRepository;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportTemplateJpaRepository;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportTemplateVersionJpaRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Database-backed adapter for ReportDefinition.
 */
@Component
public class JpaReportDefinitionRepositoryAdapter implements ReportDefinitionRepositoryPort {

    private static final String REPORT_CATEGORY = "REPORT_CATEGORY";

    private final ReportDefinitionJpaRepository repository;
    private final ReportCatalogEntryJpaRepository catalogRepository;
    private final ReportTemplateJpaRepository templateRepository;
    private final ReportTemplateVersionJpaRepository templateVersionRepository;
    private final ReportAccessPolicyJpaRepository accessPolicyRepository;

    public JpaReportDefinitionRepositoryAdapter(
            ReportDefinitionJpaRepository repository,
            ReportCatalogEntryJpaRepository catalogRepository,
            ReportTemplateJpaRepository templateRepository,
            ReportTemplateVersionJpaRepository templateVersionRepository,
            ReportAccessPolicyJpaRepository accessPolicyRepository
    ) {
        this.repository = Objects.requireNonNull(repository, "ReportDefinitionJpaRepository must not be null.");
        this.catalogRepository = Objects.requireNonNull(catalogRepository, "ReportCatalogEntryJpaRepository must not be null.");
        this.templateRepository = Objects.requireNonNull(templateRepository, "ReportTemplateJpaRepository must not be null.");
        this.templateVersionRepository = Objects.requireNonNull(
                templateVersionRepository,
                "ReportTemplateVersionJpaRepository must not be null."
        );
        this.accessPolicyRepository = Objects.requireNonNull(
                accessPolicyRepository,
                "ReportAccessPolicyJpaRepository must not be null."
        );
    }

    @Override
    public ReportDefinition save(ReportDefinition model) {
        Objects.requireNonNull(model, "ReportDefinition must not be null.");
        if (repository.existsByCodeAndIdNot(model.code(), model.id())) {
            throw new InvalidReportingValueException("ReportDefinition code must be unique.");
        }
        requireReportCategory(model.reportCategoryId());
        validateCurrentTemplateVersion(model);
        if (model.status() == ReportDefinitionStatus.ACTIVE && model.restricted()) {
            boolean hasPolicy = accessPolicyRepository.findAll().stream()
                    .anyMatch(policy -> model.id().equals(policy.reportDefinitionId()) && policy.restricted());
            if (!hasPolicy) {
                throw new InvalidReportingValueException(
                        "ACTIVE restricted ReportDefinition requires an explicit Reporting access policy."
                );
            }
        }
        return ReportingPersistenceMapper.toDomain(
                repository.save(ReportingPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<ReportDefinition> findById(String id) {
        return repository.findById(id).map(ReportingPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByCode(String code) {
        return code != null && !code.isBlank() && repository.existsByCode(code.trim());
    }

    private void requireReportCategory(String categoryId) {
        var category = catalogRepository.findById(categoryId)
                .orElseThrow(() -> new InvalidReportingValueException(
                        "ReportDefinition report category must reference an existing Reporting catalog entry."
                ));
        if (!REPORT_CATEGORY.equals(category.catalogName()) || !category.active()) {
            throw new InvalidReportingValueException(
                    "ReportDefinition report category must reference an ACTIVE REPORT_CATEGORY entry."
            );
        }
    }

    private void validateCurrentTemplateVersion(ReportDefinition model) {
        if (model.currentTemplateVersionId() == null) {
            return;
        }
        var version = templateVersionRepository.findById(model.currentTemplateVersionId())
                .orElseThrow(() -> new InvalidReportingValueException(
                        "ReportDefinition current template version must exist."
                ));
        var template = templateRepository.findById(version.reportTemplateId())
                .orElseThrow(() -> new InvalidReportingValueException(
                        "ReportDefinition current template version must reference an existing ReportTemplate."
                ));
        if (!model.id().equals(template.reportDefinitionId())) {
            throw new InvalidReportingValueException(
                    "ReportDefinition current template version must belong to the same report definition."
            );
        }
        if (model.status() == ReportDefinitionStatus.ACTIVE
                && (!template.active() || version.status() != ReportTemplateVersionStatus.ACTIVE)) {
            throw new InvalidReportingValueException(
                    "ACTIVE ReportDefinition current template version must be ACTIVE on an active template."
            );
        }
    }
}
