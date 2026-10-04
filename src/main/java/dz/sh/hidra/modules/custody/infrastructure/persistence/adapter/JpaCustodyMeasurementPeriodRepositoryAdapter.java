/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyMeasurementPeriodRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyMeasurementPeriod.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyMeasurementPeriodRepositoryPort;
import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import dz.sh.hidra.modules.custody.domain.model.CustodyMeasurementPeriod;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyAgreementJpaRepository;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyMeasurementPeriodJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyMeasurementPeriod.
 */
@Component
public class JpaCustodyMeasurementPeriodRepositoryAdapter implements CustodyMeasurementPeriodRepositoryPort {

    private final CustodyMeasurementPeriodJpaRepository repository;
    private final CustodyAgreementJpaRepository agreementRepository;

    public JpaCustodyMeasurementPeriodRepositoryAdapter(
            CustodyMeasurementPeriodJpaRepository repository,
            CustodyAgreementJpaRepository agreementRepository
    ) {
        this.repository = Objects.requireNonNull(
                repository,
                "CustodyMeasurementPeriodJpaRepository must not be null."
        );
        this.agreementRepository = Objects.requireNonNull(
                agreementRepository,
                "CustodyAgreementJpaRepository must not be null."
        );
    }

    @Override
    public CustodyMeasurementPeriod save(CustodyMeasurementPeriod model) {
        Objects.requireNonNull(model, "CustodyMeasurementPeriod must not be null.");

        var agreement = agreementRepository.findById(model.agreementId())
                .orElseThrow(() -> new InvalidCustodyValueException(
                        "CustodyMeasurementPeriod agreement must reference an existing CustodyAgreement."
                ));
        if (!model.transferPointId().equals(agreement.transferPointId())) {
            throw new InvalidCustodyValueException(
                    "CustodyMeasurementPeriod transfer point must match the governing CustodyAgreement transfer point."
            );
        }

        return CustodyPersistenceMapper.toDomain(
                repository.save(CustodyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<CustodyMeasurementPeriod> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
