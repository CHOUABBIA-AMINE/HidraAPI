/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcAssetScopedTrustedObservationReadAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Reads bounded asset membership and immutable trusted evidence from Telemetry.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.Asset;
import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.Binding;
import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.HistoryCursor;
import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.Observation;
import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.Point;
import dz.sh.hidra.modules.telemetry.application.port.out.AssetScopedTrustedObservationReadPort;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class JdbcAssetScopedTrustedObservationReadAdapter implements AssetScopedTrustedObservationReadPort {
    private static final String EXACT_ASSET = """
            b.topology_asset_type_code = :type AND b.topology_asset_id = :asset AND b.active
            """;
    private static final String UNIQUE_BINDING_AT = """
            AND NOT EXISTS (
                SELECT 1 FROM public.hidra_telemetry_point_binding other
                WHERE other.point_id = b.point_id AND other.id <> b.id AND other.active
                  AND other.valid_from <= %1$s AND (other.valid_to IS NULL OR %1$s < other.valid_to)
            )
            """;
    private static final String UNIQUE_MAPPING_AT = """
            AND NOT EXISTS (
                SELECT 1 FROM public.hidra_telemetry_external_tag_mapping other
                WHERE other.point_id = m.point_id AND other.id <> m.id AND other.active
                  AND other.valid_from <= %1$s AND (other.valid_to IS NULL OR %1$s < other.valid_to)
            )
            """;
    private static final String OBSERVATIONS = """
            SELECT t.id AS trusted_id, t.reading_id, i.source_id, t.unit_id,
                   t.numeric_value, t.text_value, t.boolean_value, t.source_timestamp,
                   r.received_at, q.assessed_at, t.trusted_at, q.id AS assessment_id,
                   q.assessment_status, t.quality_code_id, t.trust_level,
                   b.id AS binding_id, b.valid_from, b.valid_to
            FROM public.hidra_telemetry_trusted_reading t
            JOIN public.hidra_telemetry_point_binding b ON b.id = t.binding_id AND b.point_id = t.point_id
            JOIN public.hidra_telemetry_reading r ON r.id = t.reading_id AND r.point_id = t.point_id
            JOIN public.hidra_telemetry_quality_assessment q
              ON q.id = t.quality_assessment_id AND q.reading_id = r.id AND q.point_id = t.point_id
            JOIN public.hidra_telemetry_reading_ingest_receipt i
              ON i.reading_id = r.id AND i.point_id = t.point_id
            JOIN public.hidra_telemetry_external_tag_mapping m
              ON m.id = i.mapping_id AND m.point_id = t.point_id AND m.source_id = i.source_id
            LEFT JOIN public.hidra_telemetry_unit u ON u.id = i.unit_id
            WHERE t.point_id = :point AND i.source_id IN (:sources)
              AND b.topology_asset_type_code = t.topology_asset_type_code
              AND b.topology_asset_id = t.topology_asset_id
              AND b.topology_asset_code = t.topology_asset_code
              AND b.topology_snapshot_id IS NOT DISTINCT FROM t.topology_snapshot_id
              AND b.valid_from <= t.source_timestamp AND (b.valid_to IS NULL OR t.source_timestamp < b.valid_to)
              AND m.active AND m.mapping_mode = 'DIRECT'
              AND m.valid_from = b.valid_from AND m.valid_to IS NOT DISTINCT FROM b.valid_to
              AND m.valid_from <= t.source_timestamp
              AND (m.valid_to IS NULL OR t.source_timestamp < m.valid_to)
              AND i.mapping_id = r.external_tag_mapping_id AND i.payload_digest = r.raw_payload_hash
              AND i.source_sequence_number = r.source_sequence_number AND i.received_at = r.received_at
              AND i.unit_id IS NOT DISTINCT FROM t.unit_id
              AND (i.unit_id IS NULL OR u.id IS NOT NULL)
              AND (r.source_timestamp, r.numeric_value, r.text_value, r.boolean_value)
                  IS NOT DISTINCT FROM (t.source_timestamp, t.numeric_value, t.text_value, t.boolean_value)
              AND r.state IN ('RECEIVED', 'ACCEPTED')
              AND q.assessment_status = 'PASSED' AND q.input_quality_code_id = r.quality_code_id
              AND q.resolved_quality_code_id = t.quality_code_id AND q.trust_level = t.trust_level
              AND t.trust_level IN ('MEDIUM', 'HIGH', 'CERTIFIED')
              AND
            """ + EXACT_ASSET + UNIQUE_BINDING_AT.formatted("t.source_timestamp")
            + UNIQUE_MAPPING_AT.formatted("t.source_timestamp");

    private static final RowMapper<Observation> OBSERVATION = (row, index) -> new Observation(
            row.getString("trusted_id"), row.getString("reading_id"), row.getString("source_id"),
            row.getString("unit_id"), row.getBigDecimal("numeric_value"), row.getString("text_value"),
            row.getObject("boolean_value", Boolean.class), instant(row, "source_timestamp"),
            instant(row, "received_at"), instant(row, "assessed_at"), instant(row, "trusted_at"),
            row.getString("assessment_id"), row.getString("assessment_status"), row.getString("quality_code_id"),
            row.getString("trust_level"), binding(row));

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcAssetScopedTrustedObservationReadAdapter(JdbcTemplate jdbc) {
        this.jdbc = new NamedParameterJdbcTemplate(jdbc);
    }

    @Override
    public List<String> pointIds(Asset asset, Instant at, String after, int batchSize) {
        var params = scope(asset).addValue("at", timestamp(at)).addValue("limit", batchSize);
        String sql = "SELECT DISTINCT b.point_id FROM public.hidra_telemetry_point_binding b WHERE " + EXACT_ASSET
                + " AND b.valid_from <= :at AND (b.valid_to IS NULL OR :at < b.valid_to)";
        if (after != null) {
            sql += " AND b.point_id > :after";
            params.addValue("after", after);
        }
        return jdbc.queryForList(sql + " ORDER BY b.point_id LIMIT :limit", params, String.class);
    }

    @Override
    public Optional<Point> membership(Asset asset, String pointId, Instant at) {
        String sql = """
                SELECT b.point_id, m.source_id, p.unit_id, b.id AS binding_id, b.valid_from, b.valid_to
                FROM public.hidra_telemetry_point_binding b
                JOIN public.hidra_telemetry_point p ON p.id = b.point_id
                JOIN public.hidra_telemetry_device d ON d.id = p.device_id
                JOIN public.hidra_telemetry_external_tag_mapping m ON m.point_id = b.point_id
                JOIN public.hidra_telemetry_source s ON s.id = m.source_id
                LEFT JOIN public.hidra_telemetry_unit u ON u.id = p.unit_id
                JOIN public.hidra_telemetry_type_catalog pt ON pt.id = p.point_type_id
                JOIN public.hidra_telemetry_type_catalog st ON st.id = p.signal_type_id
                WHERE b.point_id = :point AND b.valid_from <= :at AND (b.valid_to IS NULL OR :at < b.valid_to)
                  AND m.active AND m.mapping_mode = 'DIRECT'
                  AND m.valid_from = b.valid_from AND m.valid_to IS NOT DISTINCT FROM b.valid_to
                  AND m.source_id = d.source_id AND (m.device_id IS NULL OR m.device_id = d.id)
                  AND p.status = 'ACTIVE' AND d.status = 'ACTIVE' AND s.status = 'ACTIVE'
                  AND pt.catalog_name = 'POINT_TYPE' AND pt.active
                  AND st.catalog_name = 'SIGNAL_TYPE' AND st.active
                  AND (p.unit_id IS NULL OR u.active)
                  AND (p.unit_id IS NOT NULL OR st.value_shape <> 'NUMERIC' OR pt.numeric_unit_exempt)
                  AND
                """ + EXACT_ASSET + UNIQUE_BINDING_AT.formatted(":at") + UNIQUE_MAPPING_AT.formatted(":at");
        var rows = jdbc.query(sql + " LIMIT 2",
                scope(asset).addValue("point", pointId).addValue("at", timestamp(at)),
                (row, index) -> new Point(row.getString("point_id"), row.getString("source_id"),
                        row.getString("unit_id"), binding(row)));
        return rows.size() == 1 ? Optional.of(rows.getFirst()) : Optional.empty();
    }

    @Override
    public List<String> historicalSources(Asset asset, String pointId, Instant from, Instant until) {
        // Bindings and direct mappings are paired by T08 on the same point and effective start.
        String sql = """
                SELECT DISTINCT m.source_id
                FROM public.hidra_telemetry_point_binding b
                JOIN public.hidra_telemetry_external_tag_mapping m
                  ON m.point_id = b.point_id AND m.valid_from = b.valid_from
                 AND m.valid_to IS NOT DISTINCT FROM b.valid_to
                WHERE b.point_id = :point AND b.valid_from < :until
                  AND (b.valid_to IS NULL OR :from < b.valid_to)
                  AND m.active AND m.mapping_mode = 'DIRECT' AND
                """ + EXACT_ASSET + " ORDER BY m.source_id";
        return jdbc.queryForList(sql, window(scope(asset).addValue("point", pointId), from, until), String.class);
    }

    @Override
    public Optional<String> latestCandidate(Asset asset, Point point) {
        // Choose the newest publication before validating it so bad lineage cannot hide behind an older result.
        String sql = """
                SELECT t.id FROM public.hidra_telemetry_trusted_reading t
                JOIN public.hidra_telemetry_point_binding b ON b.id = t.binding_id AND b.point_id = t.point_id
                WHERE t.point_id = :point AND b.id = :binding AND
                """ + EXACT_ASSET + " ORDER BY t.source_timestamp DESC, t.id DESC LIMIT 1";
        return jdbc.queryForList(sql, scope(asset).addValue("point", point.id())
                .addValue("binding", point.binding().id()), String.class).stream().findFirst();
    }

    @Override
    public Optional<Observation> evidence(Asset asset, String pointId, List<String> sources, String trustedId) {
        var params = observationScope(asset, pointId, sources).addValue("trusted", trustedId);
        return jdbc.query(OBSERVATIONS + " AND t.id = :trusted", params, OBSERVATION).stream().findFirst();
    }

    @Override
    public List<Observation> history(Asset asset, String pointId, List<String> sources,
            Instant from, Instant until, HistoryCursor after, int limit) {
        var params = window(observationScope(asset, pointId, sources), from, until).addValue("limit", limit);
        String sql = OBSERVATIONS + " AND t.source_timestamp >= :from AND t.source_timestamp < :until";
        if (after != null) {
            sql += " AND (t.source_timestamp, t.id) < (:afterTime, :afterId)";
            params.addValue("afterTime", timestamp(after.observedAt())).addValue("afterId", after.trustedId());
        }
        return jdbc.query(sql + " ORDER BY t.source_timestamp DESC, t.id DESC LIMIT :limit", params, OBSERVATION);
    }

    private static MapSqlParameterSource scope(Asset asset) {
        return new MapSqlParameterSource().addValue("type", asset.type().name()).addValue("asset", asset.id());
    }

    private static MapSqlParameterSource observationScope(Asset asset, String point, List<String> sources) {
        return scope(asset).addValue("point", point).addValue("sources", sources);
    }

    private static MapSqlParameterSource window(MapSqlParameterSource params, Instant from, Instant until) {
        return params.addValue("from", timestamp(from)).addValue("until", timestamp(until));
    }

    private static Timestamp timestamp(Instant instant) {
        return Timestamp.from(instant);
    }

    private static Instant instant(ResultSet row, String name) throws SQLException {
        Timestamp value = row.getTimestamp(name);
        return value == null ? null : value.toInstant();
    }

    private static Binding binding(ResultSet row) throws SQLException {
        return new Binding(row.getString("binding_id"), instant(row, "valid_from"), instant(row, "valid_to"));
    }
}
