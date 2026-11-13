/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMonitoringRulePointEligibilityAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Locks active Telemetry source, point, effective binding and compatible unit.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.contract.monitoring.MonitoringRulePointEligibilityContract;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryLifecycleStatus;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.time.Instant;
import java.util.Objects;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public final class JpaMonitoringRulePointEligibilityAdapter implements MonitoringRulePointEligibilityContract {
    private final EntityManager em;
    public JpaMonitoringRulePointEligibilityAdapter(EntityManager em){this.em=Objects.requireNonNull(em);}
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void requireEligible(String pointId,String assetType,String assetId,String unitId,Instant at) {
        if(!valid(pointId)||!valid(assetType)||!valid(assetId)||!valid(unitId)||at==null)throw unavailable();
        var point=em.find(TelemetryPointJpaEntity.class,pointId,LockModeType.PESSIMISTIC_READ);
        if(point==null||point.status()!=TelemetryLifecycleStatus.ACTIVE||!unitId.equals(point.unitId()))
            throw unavailable();
        var device=em.find(TelemetryDeviceJpaEntity.class,point.deviceId(),LockModeType.PESSIMISTIC_READ);
        if(device==null||device.status()!=TelemetryLifecycleStatus.ACTIVE)throw unavailable();
        var source=em.find(TelemetrySourceJpaEntity.class,device.sourceId(),LockModeType.PESSIMISTIC_READ);
        if(source==null||source.status()!=TelemetryLifecycleStatus.ACTIVE)throw unavailable();
        var unit=em.find(TelemetryUnitJpaEntity.class,unitId,LockModeType.PESSIMISTIC_READ);
        if(unit==null||!unit.active())throw unavailable();
        var bindings=em.createQuery("""
            select b from TelemetryPointBindingJpaEntity b where b.pointId=:point
            and b.active=true and b.validFrom <= :at and (b.validTo is null or b.validTo > :at)
            order by b.id
            """,TelemetryPointBindingJpaEntity.class).setParameter("point",pointId)
            .setParameter("at",at).setLockMode(LockModeType.PESSIMISTIC_READ)
            .setMaxResults(2).getResultList();
        if(bindings.size()!=1)throw unavailable();
        var b=bindings.getFirst();
        if(!assetType.equals(b.topologyAssetTypeCode())||!assetId.equals(b.topologyAssetId()))
            throw unavailable();
    }
    private static boolean valid(String s){return s!=null&&!s.isBlank()&&s.equals(s.strip());}
    private static AccessDeniedException unavailable(){
        return new AccessDeniedException("Protected Monitoring point evidence unavailable.");
    }
}
