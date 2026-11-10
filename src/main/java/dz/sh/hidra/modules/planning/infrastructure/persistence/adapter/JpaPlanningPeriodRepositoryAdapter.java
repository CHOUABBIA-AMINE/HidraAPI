/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPlanningPeriodRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PlanningPeriod.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.application.port.out.PlanningPeriodRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.PlanningPeriod;
import dz.sh.hidra.modules.planning.domain.exception.PlanningPeriodCodeConflictException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.PlanningPeriodJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PlanningPeriod.
 */
@Component
public class JpaPlanningPeriodRepositoryAdapter implements PlanningPeriodRepositoryPort {

    private final PlanningPeriodJpaRepository repository;

    public JpaPlanningPeriodRepositoryAdapter(PlanningPeriodJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PlanningPeriodJpaRepository must not be null.");
    }

    @Override
    public PlanningPeriod save(PlanningPeriod model) {
        try {
            return PlanningPersistenceMapper.toDomain(repository.saveAndFlush(PlanningPersistenceMapper.toEntity(model)));
        } catch (DataIntegrityViolationException failure) {
            for (Throwable cause = failure; cause != null; cause = cause.getCause()) {
                if (cause instanceof ConstraintViolationException constraint
                        && "uk_t01_planning_period_company_code".equals(constraint.getConstraintName())) {
                    throw new PlanningPeriodCodeConflictException();
                }
            }
            throw failure;
        }
    }

    @Override
    public Optional<PlanningPeriod> findById(String id) {
        return repository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByCompanyPartyIdAndCode(String companyPartyId, String code) {
        return repository.existsByCompanyPartyIdAndCode(companyPartyId, code);
    }

    @Override
    public boolean isActivePeriodType(String periodTypeId) {
        return periodTypeId != null
                && !periodTypeId.isBlank()
                && repository.existsActivePeriodTypeById(periodTypeId);
    }
}
