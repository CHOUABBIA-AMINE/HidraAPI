/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmShelvingJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.repository
 *
 * @Description : Enforces Alarm-owned transactional lifecycle evidence and integrity.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.repository;

import dz.sh.hidra.modules.alarm.infrastructure.persistence.entity.AlarmShelvingJpaEntity;
import dz.sh.hidra.modules.alarm.domain.value.AlarmShelvingStatus;
import jakarta.persistence.LockModeType;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmShelvingJpaRepository extends JpaRepository<AlarmShelvingJpaEntity,String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select shelving from AlarmShelvingJpaEntity shelving where shelving.id = :id")
    Optional<AlarmShelvingJpaEntity> findByIdForUpdate(@Param("id") String id);
    boolean existsByAlarmIdAndStatus(String alarmId,AlarmShelvingStatus status);
    @Query(value="select alarm_id from hidra_alarm_shelving where id = :id",nativeQuery=true)
    Optional<String> findAlarmId(@Param("id") String id);
    @Query(value="select id from hidra_alarm_shelving where status='ACTIVE' and shelved_until <= :asOf order by shelved_until,id",nativeQuery=true)
    List<String> findDueIds(@Param("asOf") Instant asOf);
}
