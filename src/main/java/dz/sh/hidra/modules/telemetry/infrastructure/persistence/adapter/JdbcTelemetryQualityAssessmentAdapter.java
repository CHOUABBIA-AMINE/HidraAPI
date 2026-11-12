/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcTelemetryQualityAssessmentAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Resolves commissioned scope and stores immutable assessed and trusted lineage.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryQualityAssessmentPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingIngestionReferencePort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingIngestionReferencePort.Scope;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryTrustedReadPort;
import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryQualityAssessmentPolicy;
import dz.sh.hidra.modules.telemetry.domain.value.TrustLevel;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.*;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JdbcTelemetryQualityAssessmentAdapter
        implements TelemetryQualityAssessmentPort, TelemetryTrustedReadPort {
    private final EntityManager entities;
    private final TelemetryReadingIngestionReferencePort references;
    private final ObjectMapper json;

    public JdbcTelemetryQualityAssessmentAdapter(EntityManager entities,
            TelemetryReadingIngestionReferencePort references) {
        this.entities = Objects.requireNonNull(entities);
        this.references = Objects.requireNonNull(references);
        // The narrow, immutable policy JSON parser does not depend on an optional
        // Boot-managed Jackson 2 ObjectMapper bean during non-database API startup.
        this.json = new ObjectMapper();
    }

    private static AccessDeniedException unavailable() {
        return new AccessDeniedException("Protected telemetry resource unavailable.");
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Scope current(String pointId, Instant at) {
        if (pointId == null || pointId.isBlank() || at == null) throw unavailable();
        // The existing T08 reference adapter locks the point, current binding, source, device,
        // DIRECT mapping and unit in the current transaction.
        Scope scope = references.current(pointId, null, at);
        if (scope.mappingId() == null) throw unavailable();
        return scope;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Assessment assess(Scope scope, String readingId, String actorId, Instant at) {
        if (scope == null || readingId == null || readingId.isBlank()
                || actorId == null || actorId.isBlank()) throw unavailable();
        // The row lock serializes repeat assessment with publication for this raw observation.
        TelemetryReading raw = lockedRaw(scope.pointId(), readingId);
        requireRawReceipt(scope, raw);
        var previous = findAssessment(raw.id());
        if (previous != null) return assessment(previous, true);
        var active = activeRule(scope.pointId(), at);
        TelemetryQualityAssessmentPolicy.Rule configured = active.policy();
        if (!raw.qualityCodeId().equals(configured.acceptedQualityCodeId())) {
            // A valid but unacceptable producer quality code yields a truthful FAILED assessment.
            // Its quality code must still resolve to a current active QUALITY_CODE.
            requireQuality(raw.qualityCodeId());
        } else {
            requireQuality(configured.acceptedQualityCodeId());
        }
        var decision = TelemetryQualityAssessmentPolicy.decide(raw,configured,at);
        String id = UUID.nameUUIDFromBytes(("t09-quality:"+raw.id())
                .getBytes(StandardCharsets.UTF_8)).toString();
        var stored = new TelemetryQualityAssessmentJpaEntity(id,raw.id(),raw.pointId(),
                decision.status(),raw.qualityCodeId(),raw.qualityCodeId(),decision.trustLevel(),
                active.id(),decision.reasonCode(),null,at,actorId,null);
        entities.persist(stored);
        entities.flush();
        return assessment(stored,false);
    }

    private record ActiveRule(String id, TelemetryQualityAssessmentPolicy.Rule policy) { }

    private ActiveRule activeRule(String pointId, Instant at) {
        var rules = entities.createNativeQuery("""
                SELECT r.id,CAST(r.configuration_json AS text) FROM hidra_telemetry_validation_rule r
                JOIN hidra_telemetry_type_catalog k ON k.id=r.rule_type_id
                WHERE r.code='T09_RAW_QUALITY' AND r.scope_type='POINT'
                  AND r.scope_reference_id=:point AND r.active=true
                  AND r.action_on_failure='REJECT' AND k.active=true
                  AND k.catalog_name='VALIDATION_RULE_TYPE'
                  AND r.valid_from <= :at AND (r.valid_to IS NULL OR :at < r.valid_to)
                ORDER BY r.id FOR SHARE OF r,k
                """).setParameter("point",pointId).setParameter("at",at)
                .getResultList();
        if (rules.size()!=1) throw new IllegalStateException("Exactly one approved quality rule required.");
        Object[] rule=(Object[])rules.getFirst();
        return new ActiveRule((String)rule[0],policy((String)rule[1]));
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void requireCurrentEligibility(Scope scope, Assessment decision, Instant at) {
        if(scope==null||decision==null||at==null || !scope.pointId().equals(decision.pointId()))
            throw unavailable();
        var raw=lockedRaw(scope.pointId(),decision.readingId());
        requireRawReceipt(scope,raw);
        var current=activeRule(scope.pointId(),at);
        requireQuality(raw.qualityCodeId());
        var outcome=TelemetryQualityAssessmentPolicy.decide(raw,current.policy(),at);
        if(!current.id().equals(decision.ruleId()) || !raw.qualityCodeId().equals(decision.inputQualityCodeId())
                || !raw.qualityCodeId().equals(decision.resolvedQualityCodeId())
                || outcome.status()!=dz.sh.hidra.modules.telemetry.domain.value.AssessmentStatus.PASSED
                || outcome.trustLevel()!=decision.trustLevel()
                || decision.status()!=dz.sh.hidra.modules.telemetry.domain.value.AssessmentStatus.PASSED) {
            throw new InvalidTelemetryValueException("Current quality evidence is ineligible.");
        }
    }

    private void requireQuality(String qualityId) {
        var rows=entities.createNativeQuery("""
            SELECT id FROM hidra_telemetry_type_catalog
            WHERE id=:id AND catalog_name='QUALITY_CODE' AND active=true FOR SHARE
            """).setParameter("id",qualityId).getResultList();
        if (rows.size()!=1) throw new IllegalStateException("Quality reference is no longer eligible.");
    }

    private TelemetryQualityAssessmentPolicy.Rule policy(String input) {
        try {
            if (input == null) throw new IllegalArgumentException("Missing policy");
            JsonNode cfg=json.readTree(input);
            if (cfg == null || !cfg.isObject()
                    || !cfg.path("qualityCodeId").isTextual()
                    || !cfg.path("trustLevel").isTextual()
                    || !cfg.path("maxPastAgeSeconds").isIntegralNumber()
                    || !cfg.path("maxFutureSkewSeconds").isIntegralNumber())
                throw new IllegalArgumentException("Unsupported policy structure");
            return new TelemetryQualityAssessmentPolicy.Rule(
                    cfg.path("qualityCodeId").asText(),
                    TrustLevel.valueOf(cfg.path("trustLevel").asText()),
                    cfg.path("maxPastAgeSeconds").longValue(),
                    cfg.path("maxFutureSkewSeconds").longValue());
        } catch (Exception invalid) {
            throw new IllegalStateException("Configured quality rule is not supported.");
        }
    }

    private TelemetryReading lockedRaw(String pointId, String readingId) {
        if(readingId == null || readingId.isBlank()) throw unavailable();
        TelemetryReadingJpaEntity entity=entities.find(TelemetryReadingJpaEntity.class,readingId,
                LockModeType.PESSIMISTIC_WRITE);
        if(entity==null || !entity.pointId().equals(pointId)) throw unavailable();
        return TelemetryPersistenceMapper.toDomain(entity);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void lock(String pointId, String readingId) {
        lockedRaw(pointId,readingId);
    }

    private void requireRawReceipt(Scope scope,TelemetryReading raw) {
        var rows=entities.createNativeQuery("""
            SELECT source_id,unit_id,mapping_id,payload_digest
            FROM hidra_telemetry_reading_ingest_receipt
            WHERE reading_id=:reading AND point_id=:point FOR SHARE
            """).setParameter("reading",raw.id()).setParameter("point",scope.pointId()).getResultList();
        if(rows.size()!=1) throw unavailable();
        Object[] row=(Object[])rows.getFirst();
        if(!Objects.equals(row[0],scope.sourceId()) || !Objects.equals(row[1],scope.unitId())
                || !Objects.equals(row[2],scope.mappingId())
                || !Objects.equals(row[3],raw.rawPayloadHash())) throw unavailable();
    }

    private TelemetryQualityAssessmentJpaEntity findAssessment(String readingId) {
        var rows=entities.createQuery("""
            select a from TelemetryQualityAssessmentJpaEntity a where a.readingId=:reading
            """,TelemetryQualityAssessmentJpaEntity.class)
                .setParameter("reading",readingId).setMaxResults(2).getResultList();
        if(rows.size()>1) throw new IllegalStateException("Ambiguous immutable assessment.");
        return rows.isEmpty()?null:rows.getFirst();
    }
    private static Assessment assessment(TelemetryQualityAssessmentJpaEntity a,boolean replayed) {
        return new Assessment(a.id(),a.readingId(),a.pointId(),a.assessmentStatus(),
                a.inputQualityCodeId(),a.resolvedQualityCodeId(),a.trustLevel(),
                a.validationRuleId(),a.reasonCode(),a.assessedAt(),a.assessedByActorId(),replayed);
    }
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Assessment find(String pointId,String readingId) {
        if(readingId==null || readingId.isBlank())throw unavailable();
        var a=findAssessment(readingId);
        if(a==null || !a.pointId().equals(pointId)) throw new java.util.NoSuchElementException("Protected telemetry evidence unavailable.");
        return assessment(a,false);
    }

    private Published published(TrustedTelemetryReadingJpaEntity entity,boolean replayed) {
        var raw=entities.find(TelemetryReadingJpaEntity.class,entity.readingId());
        var quality=entities.find(TelemetryQualityAssessmentJpaEntity.class,entity.qualityAssessmentId());
        if(raw==null||quality==null||!quality.readingId().equals(raw.id()))
            throw new IllegalStateException("Missing immutable publication provenance.");
        return new Published(TelemetryPersistenceMapper.toDomain(entity),
                quality.reasonCode(),raw.receivedAt(),quality.assessedAt(),replayed);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public java.util.Optional<Published> byRaw(String readingId) {
        var rows=entities.createQuery("""
            select t from TrustedTelemetryReadingJpaEntity t where t.readingId=:reading
            """,TrustedTelemetryReadingJpaEntity.class).setParameter("reading",readingId)
                .setMaxResults(2).getResultList();
        if(rows.size()>1)throw new IllegalStateException("Duplicate trusted evidence.");
        return rows.isEmpty()?java.util.Optional.empty():java.util.Optional.of(published(rows.getFirst(),false));
    }
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public java.util.Optional<Published> byId(String pointId,String trustedId) {
        if(trustedId==null||trustedId.isBlank())return java.util.Optional.empty();
        var entity=entities.find(TrustedTelemetryReadingJpaEntity.class,trustedId);
        if(entity==null||!entity.pointId().equals(pointId))return java.util.Optional.empty();
        return java.util.Optional.of(published(entity,false));
    }
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Published> history(String pointId,int limit) {
        if(limit<1||limit>50)throw new InvalidTelemetryValueException("Invalid history limit.");
        return entities.createQuery("""
                select t from TrustedTelemetryReadingJpaEntity t
                where t.pointId=:point order by t.trustedAt desc,t.id desc
                """,TrustedTelemetryReadingJpaEntity.class).setParameter("point",pointId)
                .setMaxResults(limit).getResultList().stream().map(t->published(t,false)).toList();
    }
}
