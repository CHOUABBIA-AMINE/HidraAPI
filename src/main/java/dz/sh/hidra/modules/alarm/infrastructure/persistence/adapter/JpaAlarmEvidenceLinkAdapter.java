/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmEvidenceLinkAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter
 *
 * @Description : Stores durable typed Monitoring evaluation evidence in Alarm ownership.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmEvidenceLinkPort;
import dz.sh.hidra.modules.alarm.domain.value.AlarmEvidenceType;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.entity.AlarmEvidenceLinkJpaEntity;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmEvidenceLinkJpaRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class JpaAlarmEvidenceLinkAdapter implements AlarmEvidenceLinkPort {
    private final AlarmEvidenceLinkJpaRepository links;
    private final EntityManager entityManager;

    public JpaAlarmEvidenceLinkAdapter(AlarmEvidenceLinkJpaRepository links, EntityManager entityManager) {
        this.links = links;
        this.entityManager = entityManager;
    }

    @Override
    public void linkEvaluation(String alarmId, String evaluationId) {
        links.saveAndFlush(new AlarmEvidenceLinkJpaEntity(UUID.randomUUID().toString(), alarmId,
                AlarmEvidenceType.MONITORING_EVALUATION, evaluationId, null, null,
                "Operator-initiated evidence-linked evaluation", Instant.now()));
    }

    @Override
    public boolean hasEvaluation(String alarmId, String evaluationId) {
        return !entityManager.createQuery("""
                select e.id from AlarmEvidenceLinkJpaEntity e
                where e.alarmId = :alarm and e.evidenceType = :type and e.evidenceReferenceId = :evaluation
                """, String.class)
                .setParameter("alarm", alarmId)
                .setParameter("type", AlarmEvidenceType.MONITORING_EVALUATION)
                .setParameter("evaluation", evaluationId)
                .setMaxResults(1).getResultList().isEmpty();
    }
}
