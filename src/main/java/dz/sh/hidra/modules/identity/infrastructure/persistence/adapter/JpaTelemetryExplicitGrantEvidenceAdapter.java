/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetryExplicitGrantEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Row-locks current explicit Identity grants used by Telemetry in the original transaction.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;


import dz.sh.hidra.modules.identity.application.port.out.TelemetryExplicitGrantEvidencePort;
import dz.sh.hidra.modules.identity.domain.value.GrantEffect;
import dz.sh.hidra.modules.identity.domain.value.GrantStatus;
import dz.sh.hidra.modules.identity.domain.value.PermissionStatus;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserPermissionGrantJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JpaTelemetryExplicitGrantEvidenceAdapter implements TelemetryExplicitGrantEvidencePort {
    private final EntityManager entityManager;
    public JpaTelemetryExplicitGrantEvidenceAdapter(EntityManager entityManager) {
        this.entityManager=Objects.requireNonNull(entityManager);
    }
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public boolean currentDirectPermit(String actor,String permission,ScopeType type,String reference,Instant at) {
        if(actor==null||permission==null||type!=ScopeType.CUSTOM||reference==null
                ||reference.isBlank()||at==null)return false;
        return !entityManager.createQuery("""
            select g from UserPermissionGrantJpaEntity g, PermissionJpaEntity p
            where g.permissionId = p.id and p.code = :permission and p.status = :permissionStatus
            and g.userId = :actor and g.effect = :effect and g.status = :status
            and g.scopeType = :scopeType and g.scopeReferenceId = :reference
            and g.revokedAt is null and g.validFrom <= :at and g.validTo > :at
            """,UserPermissionGrantJpaEntity.class)
            .setParameter("actor",actor).setParameter("permission",permission)
            .setParameter("permissionStatus",PermissionStatus.ACTIVE)
            .setParameter("effect",GrantEffect.GRANT).setParameter("status",GrantStatus.ACTIVE)
            .setParameter("scopeType",type).setParameter("reference",reference).setParameter("at",at)
            .setLockMode(LockModeType.PESSIMISTIC_READ).setMaxResults(1).getResultList().isEmpty();
    }
}
