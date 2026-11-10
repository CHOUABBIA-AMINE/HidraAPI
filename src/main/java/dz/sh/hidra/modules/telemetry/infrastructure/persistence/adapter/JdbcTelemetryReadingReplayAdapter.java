/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcTelemetryReadingReplayAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Persists one canonical raw reading via PostgreSQL unique receipt and deferred FK.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;


import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingReplayPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.exception.TelemetryReadingReplayConflictException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import jakarta.persistence.EntityManager;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JdbcTelemetryReadingReplayAdapter implements TelemetryReadingReplayPort {
    private final EntityManager em;
    private final TelemetryReadingRepositoryPort readings;
    public JdbcTelemetryReadingReplayAdapter(EntityManager em,TelemetryReadingRepositoryPort readings) {
        this.em=Objects.requireNonNull(em);this.readings=Objects.requireNonNull(readings);
    }
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public Result appendOrReplay(String sourceId,String unitId,TelemetryReading reading) {
        if(reading==null||sourceId==null||sourceId.isBlank()
                ||reading.sourceSequenceNumber()==null||reading.rawPayloadHash()==null)
            throw new IllegalArgumentException("Canonical telemetry receipt required.");
        var inserted=em.createNativeQuery("""
            INSERT INTO public.hidra_telemetry_reading_ingest_receipt
            (source_id,point_id,source_sequence_number,reading_id,payload_digest,unit_id,mapping_id,received_at)
            VALUES (:source,:point,:sequence,:reading,:digest,:unit,:mapping,:received)
            ON CONFLICT (source_id,point_id,source_sequence_number) DO NOTHING
            RETURNING reading_id
            """,String.class).setParameter("source",sourceId)
                .setParameter("point",reading.pointId())
                .setParameter("sequence",reading.sourceSequenceNumber())
                .setParameter("reading",reading.id())
                .setParameter("digest",reading.rawPayloadHash())
                .setParameter("unit",unitId)
                .setParameter("mapping",reading.externalTagMappingId())
                .setParameter("received",reading.receivedAt()).getResultList();
        if(!inserted.isEmpty()){
            return new Result(readings.save(reading),unitId,false);
        }
        var existing=em.createNativeQuery("""
            SELECT reading_id,payload_digest,unit_id FROM public.hidra_telemetry_reading_ingest_receipt
            WHERE source_id=:source AND point_id=:point AND source_sequence_number=:sequence
            FOR SHARE
            """).setParameter("source",sourceId).setParameter("point",reading.pointId())
                .setParameter("sequence",reading.sourceSequenceNumber()).getResultList();
        if(existing.size()!=1)throw new IllegalStateException("Canonical ingestion receipt unavailable.");
        Object[] tuple=(Object[])existing.getFirst();
        if(!reading.rawPayloadHash().equals(tuple[1])||!Objects.equals(unitId,tuple[2]))
            throw new TelemetryReadingReplayConflictException();
        var persisted=readings.findById((String)tuple[0])
                .orElseThrow(()->new IllegalStateException("Canonical reading missing."));
        if(!reading.rawPayloadHash().equals(persisted.rawPayloadHash()))
            throw new IllegalStateException("Canonical reading digest mismatch.");
        return new Result(persisted,(String)tuple[2],true);
    }
}
