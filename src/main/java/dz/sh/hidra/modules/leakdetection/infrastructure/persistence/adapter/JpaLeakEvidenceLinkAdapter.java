/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JpaLeakEvidenceLinkAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 * @Description : T13 atomic suspected-leak evidence and ownership handling.
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakEvidenceLinkPort;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakEvidenceDirection;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakEvidenceType;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity.LeakEvidenceLinkJpaEntity;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class JpaLeakEvidenceLinkAdapter implements LeakEvidenceLinkPort {
    private final EntityManager em;
    public JpaLeakEvidenceLinkAdapter(EntityManager em) { this.em=Objects.requireNonNull(em); }
    @Override public void linkEvaluation(String candidate,String evaluation) {
        em.persist(new LeakEvidenceLinkJpaEntity(UUID.randomUUID().toString(),candidate,
            LeakEvidenceType.MONITORING_DEVIATION,evaluation,null,null,
            LeakEvidenceDirection.SUPPORTING,null,
            "T13 operator-declared suspicion; not a verified physical leak.",Instant.now()));
        em.flush();
    }
    @Override public boolean hasEvaluation(String candidate,String evaluation) {
        return !em.createQuery("""
            select l.id from LeakEvidenceLinkJpaEntity l
            where l.candidateId = :candidate and l.evidenceType = :type
              and l.evidenceReferenceId = :evaluation
            """,String.class)
            .setParameter("candidate",candidate)
            .setParameter("type",LeakEvidenceType.MONITORING_DEVIATION)
            .setParameter("evaluation",evaluation).setMaxResults(1).getResultList().isEmpty();
    }
}
