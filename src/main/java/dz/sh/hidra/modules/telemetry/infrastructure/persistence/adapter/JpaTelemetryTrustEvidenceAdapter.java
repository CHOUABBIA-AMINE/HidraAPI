/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetryTrustEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Loads locked quality, point and binding evidence without crossing Telemetry ownership.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryTrustEvidencePort;
import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.value.*;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryPointBindingJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryReadingJpaRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JpaTelemetryTrustEvidenceAdapter implements TelemetryTrustEvidencePort {
    private final EntityManager entities;
    private final TelemetryReadingJpaRepository readings;
    public JpaTelemetryTrustEvidenceAdapter(EntityManager entities, TelemetryReadingJpaRepository readings) {
        this.entities = Objects.requireNonNull(entities); this.readings = Objects.requireNonNull(readings);
    }
    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public Evidence load(String readingId, String assessmentId, Instant trustedAt) {
        List<Object[]> rows = entities.createNativeQuery("""
                SELECT a.id, a.reading_id, a.point_id, a.assessment_status, a.trust_level,
                       a.resolved_quality_code_id, p.status, p.unit_id,
                       (c.catalog_name = 'QUALITY_CODE' AND c.active),
                       (p.unit_id IS NULL OR EXISTS (SELECT 1 FROM hidra_telemetry_unit u WHERE u.id = p.unit_id)),
                       (r.ingestion_batch_id IS NULL OR EXISTS
                           (SELECT 1 FROM hidra_telemetry_ingestion_batch b WHERE b.id = r.ingestion_batch_id))
                FROM hidra_telemetry_reading r JOIN hidra_telemetry_point p ON p.id = r.point_id
                JOIN hidra_telemetry_quality_assessment a ON a.id = :assessment
                JOIN hidra_telemetry_type_catalog c ON c.id = a.resolved_quality_code_id
                WHERE r.id = :reading FOR SHARE OF r, p, a, c
                """).setParameter("reading", readingId).setParameter("assessment", assessmentId).getResultList();
        if (rows.size() != 1) throw new InvalidTelemetryValueException("Missing reading, point, assessment or quality code.");
        Object[] row = rows.get(0);
        var raw = TelemetryPersistenceMapper.toDomain(readings.findById(readingId).orElseThrow());
        List<TelemetryPointBindingJpaEntity> bindings = entities.createNativeQuery("""
                SELECT * FROM hidra_telemetry_point_binding
                WHERE point_id = :point AND active AND valid_from <= :at
                  AND (valid_to IS NULL OR :at < valid_to) ORDER BY id
                """, TelemetryPointBindingJpaEntity.class)
                .setParameter("point", raw.pointId()).setParameter("at", trustedAt).getResultList();
        return new Evidence(raw, (String)row[0], (String)row[1], (String)row[2],
                AssessmentStatus.valueOf((String)row[3]), TrustLevel.valueOf((String)row[4]),
                (String)row[5], TelemetryLifecycleStatus.valueOf((String)row[6]), (String)row[7],
                Boolean.TRUE.equals(row[8]), Boolean.TRUE.equals(row[9]), Boolean.TRUE.equals(row[10]),
                bindings.stream().map(x -> new Binding(x.id(), x.topologyAssetTypeCode(), x.topologyAssetId(),
                        x.topologyAssetCode(), x.topologySnapshotId())).toList());
    }
}
