/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcCustodyIntervalEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 * @Description : Validates trusted source value and exact immutable Telemetry interval evidence.
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyIntervalEvidenceContract;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Repository
public class JdbcCustodyIntervalEvidenceAdapter implements CustodyIntervalEvidenceContract {
    private final JdbcTemplate jdbc;
    public JdbcCustodyIntervalEvidenceAdapter(JdbcTemplate jdbc){this.jdbc=jdbc;}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Evidence> qualified(String id,String run,String meter,String type,String asset){
        if(id==null || run==null || meter==null || !"FACILITY".equals(type) || asset==null)return Optional.empty();
        List<Evidence> found=jdbc.query("""
            SELECT e.id,e.trusted_reading_id,t.reading_id,t.quality_assessment_id,t.point_id,t.binding_id,
                   e.meter_run_snapshot_id,e.metering_system_id,e.asset_type,e.asset_id,
                   e.observed_quantity,e.unit_id,e.interval_start,e.interval_end
              FROM public.hidra_telemetry_custody_interval_evidence e
              JOIN public.hidra_telemetry_trusted_reading t ON t.id=e.trusted_reading_id
              JOIN public.hidra_telemetry_reading raw ON raw.id=t.reading_id
              JOIN public.hidra_telemetry_quality_assessment qa ON qa.id=t.quality_assessment_id
              JOIN public.hidra_telemetry_point p ON p.id=t.point_id
              JOIN public.hidra_telemetry_point_binding b ON b.id=t.binding_id
              JOIN public.hidra_telemetry_unit u ON u.id=t.unit_id
             WHERE e.id=? AND e.meter_run_snapshot_id=? AND e.metering_system_id=?
               AND e.asset_type=? AND e.asset_id=? AND e.measurement_kind='INTERVAL_VOLUME_TOTAL'
               AND e.observed_quantity=t.numeric_value AND e.unit_id=t.unit_id
               AND t.numeric_value IS NOT NULL AND t.numeric_value>=0
               AND t.reading_id=raw.id AND raw.point_id=t.point_id
               AND raw.numeric_value=t.numeric_value AND raw.quality_code_id=t.quality_code_id
               AND raw.state NOT IN ('REJECTED','QUARANTINED')
               AND qa.reading_id=raw.id AND qa.point_id=t.point_id
               AND qa.assessment_status='PASSED' AND qa.trust_level=t.trust_level
               AND qa.resolved_quality_code_id=t.quality_code_id
               AND t.trust_level IN ('MEDIUM','HIGH','CERTIFIED')
               AND p.status='ACTIVE' AND p.id=b.point_id AND p.unit_id=t.unit_id
               AND b.topology_asset_type_code=e.asset_type AND b.topology_asset_id=e.asset_id
               AND b.active AND b.valid_from<=e.interval_start
               AND (b.valid_to IS NULL OR b.valid_to>=e.interval_end)
               AND t.topology_asset_type_code=e.asset_type AND t.topology_asset_id=e.asset_id
               AND u.active AND u.dimension='VOLUME' AND e.interval_start<e.interval_end
            """,(row,i)->map(row),id,run,meter,type,asset);
        return found.size()==1?Optional.of(found.getFirst()):Optional.empty();
    }
    private static Evidence map(ResultSet row)throws SQLException{
        return new Evidence(row.getString(1),row.getString(2),row.getString(3),row.getString(4),
            row.getString(5),row.getString(6),row.getString(7),row.getString(8),
            row.getString(9),row.getString(10),row.getBigDecimal(11),row.getString(12),
            row.getObject(13,OffsetDateTime.class).toInstant(),
            row.getObject(14,OffsetDateTime.class).toInstant());
    }
}
