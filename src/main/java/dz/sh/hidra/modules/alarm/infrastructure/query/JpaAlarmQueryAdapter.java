/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.query
 *
 * @Description : JPA-backed active/history/detail alarm and shelving query adapter.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.query;

import dz.sh.hidra.modules.alarm.application.port.in.AlarmQueryUseCase;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmResourceAuthorizationPort;
import dz.sh.hidra.modules.alarm.domain.exception.AlarmResourceNotFoundException;
import dz.sh.hidra.modules.alarm.domain.value.AlarmState;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.entity.AlarmJpaEntity;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.entity.AlarmShelvingJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public final class JpaAlarmQueryAdapter implements AlarmQueryUseCase {
    private final EntityManager em;
    private final AlarmResourceAuthorizationPort scopes;
    public JpaAlarmQueryAdapter(EntityManager em,AlarmResourceAuthorizationPort scopes) {
        this.em=Objects.requireNonNull(em);this.scopes=Objects.requireNonNull(scopes);
    }
    @Override
    public Page<AlarmView> alarms(String view,String state,String severityId,String topologyAssetId,
            Instant from,Instant to,int requestedPage,int requestedSize) {
        String mode=view==null?"active":view.trim().toLowerCase(Locale.ROOT);
        if(!List.of("active","history","all").contains(mode))
            throw new IllegalArgumentException("Unsupported alarm view.");
        int page=Math.max(0,requestedPage),size=Math.min(200,Math.max(1,requestedSize));
        var permitted=scopes.permittedAssets("alarm:alarms:read");
        if(permitted.isEmpty())return new Page<>(List.of(),page,size,0,0,false);
        var cb=em.getCriteriaBuilder();
        var select=cb.createQuery(AlarmJpaEntity.class);
        var root=select.from(AlarmJpaEntity.class);
        select.select(root).where(visible(cb,root,permitted,mode,state,severityId,topologyAssetId,from,to))
                .orderBy(cb.desc(root.get("raisedAt")),cb.asc(root.get("id")));
        var count=cb.createQuery(Long.class);
        var countRoot=count.from(AlarmJpaEntity.class);
        count.select(cb.count(countRoot)).where(visible(cb,countRoot,permitted,mode,state,severityId,topologyAssetId,from,to));
        long total=em.createQuery(count).getSingleResult();
        long start=(long)page*size;
        List<AlarmView> content=start>=total?List.of():
                em.createQuery(select).setFirstResult((int)Math.min(start,Integer.MAX_VALUE))
                        .setMaxResults(size).getResultList().stream().map(this::alarmView).toList();
        int totalPages=(int)Math.min(Integer.MAX_VALUE,(total+size-1)/size);
        return new Page<>(content,page,size,total,totalPages,start+content.size()<total);
    }
    private static Predicate visible(CriteriaBuilder cb,Root<AlarmJpaEntity> root,
            List<AlarmResourceAuthorizationPort.AssetRef> permitted,String mode,String state,String severity,
            String asset,Instant from,Instant to){
        Map<String,List<String>> byType=permitted.stream().collect(Collectors.groupingBy(
                AlarmResourceAuthorizationPort.AssetRef::typeCode,TreeMap::new,
                Collectors.mapping(AlarmResourceAuthorizationPort.AssetRef::assetId,Collectors.toList())));
        var allowed=new ArrayList<Predicate>();
        for(var entry:byType.entrySet())
            allowed.add(cb.and(cb.equal(root.get("topologyAssetTypeCode"),entry.getKey()),
                    root.get("topologyAssetId").in(entry.getValue())));
        var conditions=new ArrayList<Predicate>();
        conditions.add(cb.or(allowed.toArray(Predicate[]::new)));
        if("active".equals(mode))conditions.add(cb.not(root.get("currentState").in(
                AlarmState.CLEARED,AlarmState.CLOSED,AlarmState.CANCELLED)));
        if("history".equals(mode))conditions.add(root.get("currentState").in(
                AlarmState.CLEARED,AlarmState.CLOSED,AlarmState.CANCELLED));
        if(state!=null&&!state.isBlank())
            conditions.add(cb.equal(root.get("currentState"),AlarmState.valueOf(state.trim().toUpperCase(Locale.ROOT))));
        if(severity!=null&&!severity.isBlank())conditions.add(cb.equal(root.get("severityId"),severity));
        if(asset!=null&&!asset.isBlank())conditions.add(cb.equal(root.get("topologyAssetId"),asset));
        if(from!=null)conditions.add(cb.greaterThanOrEqualTo(root.<Instant>get("raisedAt"),from));
        if(to!=null)conditions.add(cb.lessThanOrEqualTo(root.<Instant>get("raisedAt"),to));
        return cb.and(conditions.toArray(Predicate[]::new));
    }
    @Override public AlarmView alarm(String id){
        if(id==null||id.isBlank())throw new AlarmResourceNotFoundException();
        var entity=em.find(AlarmJpaEntity.class,id);
        if(entity==null)throw new AlarmResourceNotFoundException();
        scopes.requireAsset("alarm:alarms:read",entity.topologyAssetTypeCode(),entity.topologyAssetId());
        return alarmView(entity);
    }
    @Override public List<ShelvingView> shelvings(String alarmId){
        scopes.requireAlarm("alarm:alarms:read",alarmId);
        return em.createQuery("select e from AlarmShelvingJpaEntity e where e.alarmId = :alarm order by e.shelvedAt desc",
                AlarmShelvingJpaEntity.class).setParameter("alarm",alarmId).getResultList().stream()
                .map(this::shelvingView).toList();
    }
    private AlarmView alarmView(AlarmJpaEntity entity){
        return new AlarmView(
                entity.id(),entity.alarmNumber(),entity.alarmTypeId(),entity.severityId(),entity.priorityId(),
                entity.titleAr(),entity.titleFr(),entity.titleEn(),String.valueOf(entity.sourceType()),
                entity.sourceReferenceId(),entity.topologyAssetTypeCode(),entity.topologyAssetId(),
                entity.topologyAssetCode(),entity.topologyAssetNameSnapshot(),String.valueOf(entity.currentState()),
                entity.raisedAt(),entity.firstDetectedAt(),entity.lastUpdatedAt(),entity.clearedAt(),
                entity.closedAt(),entity.acknowledgedAt(),entity.acknowledgedByActorId(),
                entity.owningOrganizationUnitId(),entity.workflowInstanceId(),entity.incidentId(),entity.correlationId()
        );
    }
    private ShelvingView shelvingView(AlarmShelvingJpaEntity entity){
        return new ShelvingView(entity.id(),entity.alarmId(),entity.shelvingReasonId(),entity.reasonText(),
                entity.shelvedByActorId(),entity.shelvedAt(),entity.shelvedUntil(),entity.unshelvedAt(),
                entity.unshelvedByActorId(),String.valueOf(entity.status()),entity.correlationId());
    }
}
