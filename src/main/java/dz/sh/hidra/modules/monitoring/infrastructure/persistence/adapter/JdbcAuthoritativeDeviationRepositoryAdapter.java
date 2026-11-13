/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcAuthoritativeDeviationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter
 *
 * @Description : Stores immutable authoritative plan deviation receipts.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.monitoring.application.port.out.AuthoritativeDeviationRepositoryPort;
import dz.sh.hidra.modules.monitoring.domain.model.AuthoritativeDeviationEvaluation;
import dz.sh.hidra.modules.monitoring.domain.model.AuthoritativeDeviationEvaluation.Observation;
import dz.sh.hidra.modules.monitoring.domain.model.AuthoritativeDeviationEvaluation.Outcome;
import dz.sh.hidra.modules.monitoring.domain.model.AuthoritativeDeviationEvaluation.Target;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAuthoritativeDeviationRepositoryAdapter implements AuthoritativeDeviationRepositoryPort {
    private static final String TABLE = "public.hidra_monitoring_authoritative_evaluation";
    private final JdbcTemplate jdbc;

    public JdbcAuthoritativeDeviationRepositoryAdapter(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public OptionalLong maximumAgeSeconds(String ruleId, long revision) {
        return jdbc.query("""
                select maximum_age_seconds from public.hidra_monitoring_evaluation_freshness_policy
                where rule_id=? and rule_revision=?
                """, (row, number) -> row.getLong(1), ruleId, revision).stream()
                .mapToLong(Long::longValue).findFirst();
    }

    @Override
    public Optional<AuthoritativeDeviationEvaluation> byRequest(Key key) {
        return jdbc.query("select * from " + TABLE + """
                 where rule_id=? and requested_target_id=? and requested_reading_id=? and evaluated_at=?
                """, JdbcAuthoritativeDeviationRepositoryAdapter::read, key.ruleId(), key.targetId(),
                key.trustedReadingId(), Timestamp.from(key.evaluatedAt())).stream().findFirst();
    }

    @Override
    public AuthoritativeDeviationEvaluation save(Key key, AuthoritativeDeviationEvaluation evaluation) {
        var target = evaluation.target();
        var reading = evaluation.observation();
        jdbc.update("""
                insert into public.hidra_monitoring_authoritative_evaluation
                    (id,rule_id,rule_revision,requested_target_id,requested_reading_id,evaluated_at,recorded_at,
                     actor_id,unit_id,outcome,target_id,target_revision_id,approval_id,approved_at,expected_value,
                     trusted_reading_id,raw_reading_id,assessment_id,binding_id,quality_code_id,trust_level,
                     observed_at,trusted_at,actual_value,policy_revision)
                values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                on conflict (rule_id,requested_target_id,requested_reading_id,evaluated_at) do nothing
                """, evaluation.id(), evaluation.ruleId(), evaluation.ruleRevision(), key.targetId(),
                key.trustedReadingId(), timestamp(evaluation.evaluatedAt()), timestamp(evaluation.recordedAt()),
                evaluation.actorId(), evaluation.unitId(), evaluation.outcome().name(),
                target == null ? null : target.id(), target == null ? null : target.revisionId(),
                target == null ? null : target.approvalId(), target == null ? null : timestamp(target.approvedAt()),
                evaluation.expected(), reading == null ? null : reading.id(), reading == null ? null : reading.rawId(),
                reading == null ? null : reading.assessmentId(), reading == null ? null : reading.bindingId(),
                reading == null ? null : reading.qualityCodeId(), reading == null ? null : reading.trustLevel(),
                reading == null ? null : timestamp(reading.observedAt()),
                reading == null ? null : timestamp(reading.trustedAt()), evaluation.actual(),
                evaluation.outcome() == Outcome.POLICY_UNAVAILABLE ? null : evaluation.ruleRevision());
        return byRequest(key).orElseThrow(() -> new IllegalStateException("Evaluation receipt was not persisted."));
    }

    @Override
    public Optional<AuthoritativeDeviationEvaluation> find(String ruleId, String id) {
        return jdbc.query("select * from " + TABLE + " where rule_id=? and id=?",
                JdbcAuthoritativeDeviationRepositoryAdapter::read, ruleId, id).stream().findFirst();
    }

    @Override
    public List<AuthoritativeDeviationEvaluation> history(String ruleId, int limit) {
        return jdbc.query("select * from " + TABLE + " where rule_id=? order by evaluated_at desc,id desc limit ?",
                JdbcAuthoritativeDeviationRepositoryAdapter::read, ruleId, limit);
    }

    private static AuthoritativeDeviationEvaluation read(ResultSet row, int number) throws SQLException {
        String targetId = row.getString("target_id");
        String readingId = row.getString("trusted_reading_id");
        Target target = targetId == null ? null : new Target(targetId, row.getString("target_revision_id"),
                row.getString("approval_id"), instant(row, "approved_at"), row.getBigDecimal("expected_value"));
        Observation reading = readingId == null ? null : new Observation(readingId, row.getString("raw_reading_id"),
                row.getString("assessment_id"), row.getString("binding_id"), row.getString("quality_code_id"),
                row.getString("trust_level"), instant(row, "observed_at"), instant(row, "trusted_at"),
                row.getBigDecimal("actual_value"));
        return new AuthoritativeDeviationEvaluation(row.getString("id"), row.getString("rule_id"),
                row.getLong("rule_revision"), instant(row, "evaluated_at"), instant(row, "recorded_at"),
                row.getString("actor_id"), row.getString("unit_id"), Outcome.valueOf(row.getString("outcome")),
                target, reading);
    }

    private static Timestamp timestamp(Instant instant) {
        return instant == null ? null : Timestamp.from(instant);
    }

    private static Instant instant(ResultSet row, String column) throws SQLException {
        Timestamp timestamp = row.getTimestamp(column);
        return timestamp == null ? null : timestamp.toInstant();
    }
}
