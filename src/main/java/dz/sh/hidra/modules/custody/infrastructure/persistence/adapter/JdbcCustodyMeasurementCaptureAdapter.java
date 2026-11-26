/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcCustodyMeasurementCaptureAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-26
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 * @Description : Locks Custody owner state and atomically persists immutable evidence receipts.
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.in.CustodyMeasurementCaptureUseCase.Receipt;
import dz.sh.hidra.modules.custody.application.port.out.CustodyMeasurementCapturePort;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class JdbcCustodyMeasurementCaptureAdapter implements CustodyMeasurementCapturePort {
    private final JdbcTemplate jdbc;
    private final NamedParameterJdbcTemplate named;
    public JdbcCustodyMeasurementCaptureAdapter(JdbcTemplate jdbc) {
        this.jdbc=jdbc;this.named=new NamedParameterJdbcTemplate(jdbc);
    }
    @Override public Optional<Scope> scope(String id) {
        var results=jdbc.query("""
            SELECT tp.id,tp.topology_asset_type_code,tp.topology_asset_id
              FROM public.hidra_custody_measurement_period p
              JOIN public.hidra_custody_transfer_point tp ON tp.id=p.transfer_point_id
             WHERE p.id=? FOR UPDATE OF p
            """,(row,n)->new Scope(row.getString(1),row.getString(2),row.getString(3)),id);
        return results.size()==1?Optional.of(results.getFirst()):Optional.empty();
    }
    @Override public Optional<Context> lockContext(String period,String run,String agreementParty) {
        var rows=jdbc.query("""
          SELECT p.id,a.id,tp.id,tp.topology_asset_type_code,tp.topology_asset_id,
                 m.id,mr.id,ap.id,ap.party_id,ap.party_role_id,tp.product_type_id,
                 p.period_start,p.period_end,a.valid_from,a.valid_to,tp.effective_from,tp.effective_to,
                 m.effective_from,m.effective_to,ap.effective_from,ap.effective_to
            FROM public.hidra_custody_measurement_period p
            JOIN public.hidra_custody_agreement a ON a.id=p.agreement_id
                     AND a.transfer_point_id=p.transfer_point_id
            JOIN public.hidra_custody_transfer_point tp ON tp.id=p.transfer_point_id
            JOIN public.hidra_custody_meter_run_snapshot mr ON mr.measurement_period_id=p.id
            JOIN public.hidra_custody_metering_system m ON m.id=mr.metering_system_id
                     AND m.transfer_point_id=tp.id AND m.topology_asset_type_code=tp.topology_asset_type_code
                     AND m.topology_asset_id=tp.topology_asset_id
            JOIN public.hidra_custody_agreement_party ap ON ap.agreement_id=a.id
           WHERE p.id=? AND mr.id=? AND ap.id=?
             AND p.status='OPEN' AND a.status='ACTIVE' AND tp.status='ACTIVE' AND m.active
             AND tp.topology_asset_type_code='FACILITY'
             AND tp.product_type_id IS NOT NULL
          """,(row,n)->new Context(row.getString(1),row.getString(2),row.getString(3),
             row.getString(4),row.getString(5),row.getString(6),row.getString(7),
             row.getString(8),row.getString(9),row.getString(10),row.getString(11),
             time(row,12),time(row,13),time(row,14),time(row,15),time(row,16),
             time(row,17),time(row,18),time(row,19),time(row,20),time(row,21)),
             period,run,agreementParty);
        return rows.size()==1?Optional.of(rows.getFirst()):Optional.empty();
    }
    @Override public boolean currentProduct(Context context,String company,UUID publication) {
        return Boolean.TRUE.equals(jdbc.queryForObject("""
          SELECT EXISTS(
            SELECT 1 FROM public.hidra_custody_product_publication_head h
            JOIN public.hidra_custody_product_engineering_draft d ON d.id=h.product_draft_id
            JOIN public.hidra_custody_product_publication pub
                 ON pub.publication_id=h.publication_id
                AND pub.product_draft_id=h.product_draft_id
                AND pub.company_party_id=h.company_party_id
                AND pub.catalog_product_id=h.catalog_product_id
            JOIN public.hidra_custody_catalog_entry catalog ON catalog.id=h.catalog_product_id
            WHERE h.publication_id=? AND h.state='ACTIVE'
              AND h.company_party_id=? AND d.company_party_id=h.company_party_id
              AND d.catalog_product_id=h.catalog_product_id
              AND h.catalog_product_id=? AND catalog.active AND catalog.catalog_name='PRODUCT'
          )
          """,Boolean.class,publication,
          company,context.productTypeId()));
    }
    @Override public boolean measurementTypeActive(String id){
        return Boolean.TRUE.equals(jdbc.queryForObject("""
            SELECT EXISTS(SELECT 1 FROM public.hidra_custody_catalog_entry
            WHERE id=? AND active AND catalog_name='MEASUREMENT_TYPE')
            """,Boolean.class,id));
    }
    @Override public Optional<Stored> byCommand(String period,UUID command){
        var rows=jdbc.query("""
            SELECT r.*, r.payload_fingerprint FROM public.hidra_custody_measurement_receipt r
             WHERE r.period_id=? AND r.command_id=?
            """,(row,n)->new Stored(receipt(row,false),row.getString("payload_fingerprint")),period,command);
        return rows.size()==1?Optional.of(rows.getFirst()):Optional.empty();
    }
    @Override public Optional<Receipt> read(String period,String measurement) {
        var rows=jdbc.query("""
            SELECT * FROM public.hidra_custody_measurement_receipt
             WHERE period_id=? AND measurement_id=?
            """,(row,n)->receipt(row,false),period,measurement);
        return rows.size()==1?Optional.of(rows.getFirst()):Optional.empty();
    }
    @Override public boolean sourceExists(String period,String run,String type,String evidence) {
        return Boolean.TRUE.equals(jdbc.queryForObject("""
            SELECT EXISTS(SELECT 1 FROM public.hidra_custody_measurement_receipt
                WHERE period_id=? AND meter_run_snapshot_id=? AND measurement_type_id=?
                  AND interval_evidence_id=?)
            """,Boolean.class,period,run,type,evidence));
    }
    @Override public void append(Receipt r,String digest){
        // Both inserts share the application transaction and an immutable, FK-backed receipt.
        jdbc.update("""
            INSERT INTO public.hidra_custody_measurement_snapshot
              (id,measurement_period_id,meter_run_snapshot_id,telemetry_reading_reference_id,
               telemetry_point_reference_id,measurement_type_id,observed_value,observed_unit_id,
               measured_at,accepted_for_custody,quality_flag_snapshot,created_at)
            VALUES (?,?,?,?,?,?,?,?,?,false,'QUALIFIED',?)
            """,r.measurementId(),r.periodId(),r.meterRunSnapshotId(),r.rawReadingId(),
             r.pointId(),r.measurementTypeId(),r.observedValue(),r.unitId(),
             Timestamp.from(r.intervalEnd()),Timestamp.from(r.capturedAt()));
        var x=new MapSqlParameterSource();
        x.addValue("measurementId",r.measurementId()).addValue("periodId",r.periodId())
         .addValue("commandId",r.commandId()).addValue("digest",digest)
         .addValue("meterRun",r.meterRunSnapshotId()).addValue("meter",r.meteringSystemId())
         .addValue("measurementType",r.measurementTypeId())
         .addValue("publication",r.productPublicationId())
         .addValue("agreementParty",r.agreementPartyId())
         .addValue("company",r.companyId()).addValue("transfer",r.transferPointId())
         .addValue("assetType",r.assetType()).addValue("assetId",r.assetId())
         .addValue("trusted",r.trustedReadingId()).addValue("raw",r.rawReadingId())
         .addValue("assessment",r.assessmentId()).addValue("point",r.pointId())
         .addValue("binding",r.bindingId()).addValue("evidence",r.intervalEvidenceId())
         .addValue("quantity",r.observedValue()).addValue("unit",r.unitId())
         .addValue("basis",r.quantityBasis())
         .addValue("start",Timestamp.from(r.intervalStart()))
         .addValue("end",Timestamp.from(r.intervalEnd()))
         .addValue("actor",r.actorId()).addValue("at",Timestamp.from(r.capturedAt()));
        named.update("""
           INSERT INTO public.hidra_custody_measurement_receipt(
             measurement_id,period_id,command_id,payload_fingerprint,
             meter_run_snapshot_id,metering_system_id,measurement_type_id,
             product_publication_id,agreement_party_id,company_id,transfer_point_id,
             asset_type,asset_id,trusted_reading_id,raw_reading_id,assessment_id,
             point_id,binding_id,interval_evidence_id,observed_quantity,unit_id,
             quantity_basis,interval_start,interval_end,actor_id,captured_at)
           VALUES (:measurementId,:periodId,:commandId,:digest,
             :meterRun,:meter,:measurementType,:publication,:agreementParty,:company,:transfer,
             :assetType,:assetId,:trusted,:raw,:assessment,:point,:binding,:evidence,
             :quantity,:unit,:basis,:start,:end,:actor,:at)
           """,x);
    }
    private static Instant time(ResultSet row,int column)throws SQLException{
        OffsetDateTime t=row.getObject(column,OffsetDateTime.class);return t==null?null:t.toInstant();
    }
    private static Receipt receipt(ResultSet r,boolean replay)throws SQLException{
        return new Receipt(r.getString("measurement_id"),r.getString("period_id"),
            r.getObject("command_id",UUID.class),r.getString("meter_run_snapshot_id"),
            r.getString("metering_system_id"),r.getString("measurement_type_id"),
            r.getObject("product_publication_id",UUID.class),r.getString("agreement_party_id"),
            r.getString("company_id"),r.getString("transfer_point_id"),
            r.getString("asset_type"),r.getString("asset_id"),
            r.getString("trusted_reading_id"),r.getString("raw_reading_id"),
            r.getString("assessment_id"),r.getString("point_id"),r.getString("binding_id"),
            r.getString("interval_evidence_id"),r.getBigDecimal("observed_quantity"),
            r.getString("unit_id"),r.getString("quantity_basis"),
            r.getObject("interval_start",OffsetDateTime.class).toInstant(),
            r.getObject("interval_end",OffsetDateTime.class).toInstant(),
            r.getString("actor_id"),r.getObject("captured_at",OffsetDateTime.class).toInstant(),
            false,replay);
    }

    @Override public Optional<Scope> lockReadScope(String period){
        var rows=jdbc.query("""
            SELECT tp.id,tp.topology_asset_type_code,tp.topology_asset_id
              FROM public.hidra_custody_measurement_period p
              JOIN public.hidra_custody_transfer_point tp ON tp.id=p.transfer_point_id
             WHERE p.id=? FOR SHARE OF p,tp
            """,(row,n)->new Scope(row.getString(1),row.getString(2),row.getString(3)),period);
        return rows.size()==1?Optional.of(rows.getFirst()):Optional.empty();
    }
    @Override public dz.sh.hidra.modules.custody.application.port.in.CustodyTransferLifecycleUseCase.DiscoveryPage<Receipt>
            list(String period,Scope scope,String company,int page,int size,long offset){
        return jdbc.query("""
            WITH eligible AS MATERIALIZED (
                SELECT r.* FROM public.hidra_custody_measurement_receipt r
                WHERE r.period_id=? AND r.company_id=? AND r.transfer_point_id=?
                  AND r.asset_type=? AND r.asset_id=?
            ), slice AS (
                SELECT * FROM eligible ORDER BY interval_start,interval_end,measurement_id LIMIT ? OFFSET ?
            ), tally AS (SELECT count(*) AS total FROM eligible)
            SELECT slice.*,tally.total FROM tally LEFT JOIN slice ON true
            ORDER BY slice.interval_start,slice.interval_end,slice.measurement_id
            """,(org.springframework.jdbc.core.ResultSetExtractor<dz.sh.hidra.modules.custody.application.port.in.CustodyTransferLifecycleUseCase.DiscoveryPage<Receipt>>) row->{
                var items=new java.util.ArrayList<Receipt>();long total=0;
                while(row.next()){
                    total=row.getLong("total");
                    if(row.getString("measurement_id")!=null)items.add(receipt(row,false));
                }
                return new dz.sh.hidra.modules.custody.application.port.in.CustodyTransferLifecycleUseCase.DiscoveryPage<>(items,page,size,total);
            },period,company,scope.transferPointId(),scope.assetType(),scope.assetId(),size,offset);
    }
}
