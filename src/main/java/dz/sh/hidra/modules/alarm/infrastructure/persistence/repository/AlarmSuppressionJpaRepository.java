/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for AlarmSuppression.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.repository;

import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionScopeType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionStatus;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.entity.AlarmSuppressionJpaEntity;

import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for AlarmSuppression.
 */
@Repository
public interface AlarmSuppressionJpaRepository
        extends JpaRepository<AlarmSuppressionJpaEntity, String>,
        JpaSpecificationExecutor<AlarmSuppressionJpaEntity> {

    boolean existsByScopeTypeAndScopeReferenceIdAndStatus(
            AlarmSuppressionScopeType scopeType,
            String scopeReferenceId,
            AlarmSuppressionStatus status
    );

    List<AlarmSuppressionJpaEntity> findByStatusAndSuppressedUntilLessThanEqualOrderBySuppressedUntilAsc(
            AlarmSuppressionStatus status,
            Instant asOf
    );
}

