/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetryReadingIngestionReferenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Locks authoritative current source/device/point and validates signal, mapping and units.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;


import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingIngestionReferencePort;
import dz.sh.hidra.modules.telemetry.domain.value.ExternalTagMappingMode;
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
public class JpaTelemetryReadingIngestionReferenceAdapter implements TelemetryReadingIngestionReferencePort {
    private final EntityManager em;
    public JpaTelemetryReadingIngestionReferenceAdapter(EntityManager em) {
        this.em=Objects.requireNonNull(em);
    }
    private static AccessDeniedException unavailable() {
        return new AccessDeniedException("Protected telemetry resource unavailable.");
    }
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public Scope current(String pointId,String claimedSourceId,Instant at) {
        if(pointId==null||pointId.isBlank()||at==null)throw unavailable();
        var p=em.find(TelemetryPointJpaEntity.class,pointId,LockModeType.PESSIMISTIC_READ);
        if(p==null||p.status()!=TelemetryLifecycleStatus.ACTIVE)throw unavailable();
        var device=em.find(TelemetryDeviceJpaEntity.class,p.deviceId(),LockModeType.PESSIMISTIC_READ);
        if(device==null||device.status()!=TelemetryLifecycleStatus.ACTIVE)throw unavailable();
        var source=em.find(TelemetrySourceJpaEntity.class,device.sourceId(),LockModeType.PESSIMISTIC_READ);
        if(source==null||source.status()!=TelemetryLifecycleStatus.ACTIVE
                ||(claimedSourceId!=null&&!claimedSourceId.equals(source.id())))throw unavailable();
        var bindings=em.createQuery("""
                select b from TelemetryPointBindingJpaEntity b where b.pointId=:point
                and b.active = true and b.validFrom <= :at and (b.validTo is null or b.validTo > :at)
                order by b.id
                """,TelemetryPointBindingJpaEntity.class).setParameter("point",pointId)
                .setParameter("at",at).setLockMode(LockModeType.PESSIMISTIC_READ)
                .setMaxResults(2).getResultList();
        if(bindings.size()!=1)throw unavailable();
        var binding=bindings.getFirst();
        var mapping=em.createQuery("""
                select m from TelemetryExternalTagMappingJpaEntity m where m.pointId=:point
                and m.active = true and m.validFrom <= :at and (m.validTo is null or m.validTo > :at)
                order by m.id
                """,TelemetryExternalTagMappingJpaEntity.class).setParameter("point",pointId)
                .setParameter("at",at).setLockMode(LockModeType.PESSIMISTIC_READ)
                .setMaxResults(2).getResultList();
        if(mapping.size()>1)throw unavailable();
        String mapId=null;
        if(!mapping.isEmpty()) {
            var m=mapping.getFirst();
            if(!source.id().equals(m.sourceId()) ||
                    (m.deviceId()!=null && !device.id().equals(m.deviceId()))
                    ||m.mappingMode()!=ExternalTagMappingMode.DIRECT)throw unavailable();
            mapId=m.id();
        }
        var pointType=em.find(TelemetryCatalogEntryJpaEntity.class,p.pointTypeId(),
                LockModeType.PESSIMISTIC_READ);
        if(pointType==null||!pointType.active()||!"POINT_TYPE".equals(pointType.catalogName()))
            throw unavailable();
        var signal=em.find(TelemetryCatalogEntryJpaEntity.class,p.signalTypeId(),
                LockModeType.PESSIMISTIC_READ);
        if(signal==null||!signal.active()||!"SIGNAL_TYPE".equals(signal.catalogName())
                ||signal.valueShape()==null)throw unavailable();
        if(p.unitId()!=null){
            var unit=em.find(TelemetryUnitJpaEntity.class,p.unitId(),LockModeType.PESSIMISTIC_READ);
            if(unit==null||!unit.active())throw unavailable();
        }
        return new Scope(source.id(),device.id(),p.id(),p.unitId(),binding.topologyAssetTypeCode(),
                binding.topologyAssetId(),mapId,signal.valueShape(),pointType.numericUnitExempt());
    }
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void validateValue(Scope scope,String qualityId,String unitId,String valueShape) {
        if(scope==null || qualityId==null || qualityId.isBlank()
                ||valueShape==null || !valueShape.equals(scope.signalShape())
                || !Objects.equals(scope.unitId(),unitId)
                || ("NUMERIC".equals(valueShape)&&unitId==null&&!scope.numericUnitExempt()))
            throw unavailable();
        var quality=em.find(TelemetryCatalogEntryJpaEntity.class,qualityId,LockModeType.PESSIMISTIC_READ);
        if(quality==null||!quality.active()||!"QUALITY_CODE".equals(quality.catalogName()))
            throw unavailable();
    }
}
