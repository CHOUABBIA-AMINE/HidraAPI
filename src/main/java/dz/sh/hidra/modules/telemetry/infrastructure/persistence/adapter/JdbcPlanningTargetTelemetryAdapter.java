/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcPlanningTargetTelemetryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Locks current Telemetry unit, signal and binding evidence for draft authoring.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.contract.planning.PlanningTargetTelemetryContract;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JdbcPlanningTargetTelemetryAdapter implements PlanningTargetTelemetryContract {

    private final JdbcTemplate jdbc;

    public JdbcPlanningTargetTelemetryAdapter(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void requireCompatible(String pointId, String assetType, String assetId,
            String unitId, boolean numeric, Instant from, Instant to) {
        if (numeric && (unitId == null || jdbc.queryForList("""
                SELECT id FROM public.hidra_telemetry_unit WHERE id = ? AND active FOR SHARE
                """, String.class, unitId).size() != 1)) {
            throw incompatible();
        }
        if (pointId == null) {
            return;
        }
        var rows = jdbc.queryForList("""
                SELECT p.unit_id FROM public.hidra_telemetry_point p
                JOIN public.hidra_telemetry_device d ON d.id = p.device_id AND d.status = 'ACTIVE'
                JOIN public.hidra_telemetry_source s ON s.id = d.source_id AND s.status = 'ACTIVE'
                JOIN public.hidra_telemetry_type_catalog k ON k.id = p.signal_type_id
                    AND k.catalog_name = 'SIGNAL_TYPE' AND k.active
                JOIN public.hidra_telemetry_point_binding b ON b.point_id = p.id AND b.active
                JOIN public.hidra_telemetry_type_catalog r ON r.id = b.binding_role_id
                    AND r.catalog_name = 'BINDING_ROLE' AND r.active
                WHERE p.id = ? AND p.status = 'ACTIVE' AND k.value_shape = ?
                    AND b.topology_asset_type_code = ? AND b.topology_asset_id = ?
                    AND b.valid_from <= ? AND (b.valid_to IS NULL OR b.valid_to >= ?)
                FOR SHARE OF p, d, s, k, b, r
                """, pointId, numeric ? "NUMERIC" : "TEXT", assetType, assetId,
                OffsetDateTime.ofInstant(from, ZoneOffset.UTC), OffsetDateTime.ofInstant(to, ZoneOffset.UTC));
        if (rows.size() != 1 || (numeric && !unitId.equals(rows.getFirst().get("unit_id")))) {
            throw incompatible();
        }
    }

    private static IllegalArgumentException incompatible() {
        return new IllegalArgumentException("Target unit, point or asset binding is incompatible.");
    }
}
