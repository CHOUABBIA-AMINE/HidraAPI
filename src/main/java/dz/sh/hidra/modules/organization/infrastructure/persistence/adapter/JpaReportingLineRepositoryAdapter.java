/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportingLineRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : Database-backed ReportingLine adapter with catalog and matrix-policy validation.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.organization.application.port.out.ReportingLineRepositoryPort;
import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.ReportingLine;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineType;
import dz.sh.hidra.modules.organization.domain.value.ReportingSubjectReference;
import dz.sh.hidra.modules.organization.domain.value.ReportingSubjectType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.ReportingLineTypeJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.ReportingLineJpaRepository;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.ReportingLineTypeJpaRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Persists reporting lines only after same-module subject and matrix-policy checks pass.
 */
@Component
public class JpaReportingLineRepositoryAdapter implements ReportingLineRepositoryPort {

    private final ReportingLineJpaRepository repository;
    private final ReportingLineTypeJpaRepository typeRepository;

    public JpaReportingLineRepositoryAdapter(
            ReportingLineJpaRepository repository,
            ReportingLineTypeJpaRepository typeRepository
    ) {
        this.repository = Objects.requireNonNull(
                repository,
                "ReportingLineJpaRepository must not be null."
        );
        this.typeRepository = Objects.requireNonNull(
                typeRepository,
                "ReportingLineTypeJpaRepository must not be null."
        );
    }

    @Override
    public ReportingLine save(ReportingLine model) {
        Objects.requireNonNull(model, "ReportingLine must not be null.");

        ReportingLineTypeJpaEntity typeEntity = typeRepository
                .findById(model.reportingLineType().id())
                .filter(ReportingLineTypeJpaEntity::active)
                .orElseThrow(() -> new InvalidOrganizationValueException(
                        "Reporting line type must resolve to an active catalog entry: "
                                + model.reportingLineType().id()
                ));

        if (!typeEntity.code().equals(model.reportingLineType().code())) {
            throw new InvalidOrganizationValueException(
                    "Reporting line type id/code reference is inconsistent."
            );
        }

        validateSubject(model.source(), "source");
        validateSubject(model.target(), "target");

        if (model.active() && model.lineHierarchy()) {
            if (model.source().type() == ReportingSubjectType.EMPLOYEE
                    && repository.existsOtherActiveEmployeeLine(
                            model.id(),
                            model.source().targetId()
                    )) {
                throw new InvalidOrganizationValueException(
                        "Employee may have only one active LINE reporting line."
                );
            }

            if (repository.wouldCreateLineCycle(
                    model.id(),
                    model.source().type().name(),
                    model.source().targetId(),
                    model.target().type().name(),
                    model.target().targetId()
            )) {
                throw new InvalidOrganizationValueException(
                        "Active LINE reporting relation would create a reporting cycle."
                );
            }
        }

        return OrganizationPersistenceMapper.toDomain(
                repository.save(OrganizationPersistenceMapper.toEntity(model, typeEntity))
        );
    }

    @Override
    public Optional<ReportingLine> findById(String id) {
        return repository.findById(id).map(OrganizationPersistenceMapper::toDomain);
    }

    @Override
    public Optional<ReportingLineType> findTypeByCode(String code) {
        if (code == null || code.isBlank()) {
            return Optional.empty();
        }
        return typeRepository.findByCodeAndActiveTrue(code.trim())
                .map(OrganizationPersistenceMapper::toDomain);
    }

    private void validateSubject(
            ReportingSubjectReference subject,
            String role
    ) {
        boolean valid = switch (subject.type()) {
            case EMPLOYEE -> repository.existsActiveEmployee(subject.targetId());
            case POSITION -> repository.existsPosition(subject.targetId());
            case ORGANIZATION_UNIT -> repository.existsOrganizationUnit(subject.targetId());
        };

        if (!valid) {
            String detail = subject.type() == ReportingSubjectType.EMPLOYEE
                    ? "must exist and be ACTIVE"
                    : "must exist";
            throw new InvalidOrganizationValueException(
                    "Reporting line " + role + " " + subject.type().name()
                            + " subject " + detail + ": " + subject.targetId()
            );
        }
    }
}
