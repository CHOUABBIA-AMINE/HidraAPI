/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcAlarmEvaluationTriggerAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter
 *
 * @Description : Serializes and persists exact Alarm-owned evaluation trigger identity.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmEvaluationTriggerPort;
import dz.sh.hidra.modules.alarm.domain.model.AlarmEvaluationTrigger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAlarmEvaluationTriggerAdapter implements AlarmEvaluationTriggerPort {
    private static final String TABLE = "public.hidra_alarm_evaluation_trigger";
    private final JdbcTemplate jdbc;

    public JdbcAlarmEvaluationTriggerAdapter(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void serialize(String evaluationId) {
        // Transaction-scoped lock is released automatically at commit or rollback.
        // Hash collisions only over-serialize distinct evaluations.
        jdbc.queryForObject("select pg_advisory_xact_lock(hashtextextended(?, 0))",
                Object.class, "hidra:t12:" + evaluationId);
    }

    @Override
    public Optional<AlarmEvaluationTrigger> byEvaluation(String evaluationId) {
        return jdbc.query("select * from " + TABLE + " where evaluation_id=?",
                JdbcAlarmEvaluationTriggerAdapter::map, evaluationId).stream().findFirst();
    }

    @Override
    public Optional<AlarmEvaluationTrigger> byAlarm(String alarmId) {
        return jdbc.query("select * from " + TABLE + " where alarm_id=?",
                JdbcAlarmEvaluationTriggerAdapter::map, alarmId).stream().findFirst();
    }

    @Override
    public void save(AlarmEvaluationTrigger v) {
        jdbc.update("""
                insert into public.hidra_alarm_evaluation_trigger
                    (evaluation_id,alarm_id,rule_id,rule_revision,company_party_id,asset_type,asset_id,unit_id,
                     target_id,approval_id,trusted_reading_id,evaluated_at,alarm_type_id,severity_id,title_fr,
                     actor_id,created_at)
                values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                """, v.evaluationId(), v.alarmId(), v.ruleId(), v.ruleRevision(),
                v.companyPartyId(), v.assetType(), v.assetId(), v.unitId(), v.targetId(),
                v.approvalId(), v.trustedReadingId(), Timestamp.from(v.evaluatedAt()),
                v.alarmTypeId(), v.severityId(), v.titleFr(), v.actorId(), Timestamp.from(v.createdAt()));
    }

    private static AlarmEvaluationTrigger map(ResultSet r, int ignored) throws SQLException {
        return new AlarmEvaluationTrigger(r.getString("evaluation_id"), r.getString("alarm_id"),
                r.getString("rule_id"), r.getLong("rule_revision"),
                r.getString("company_party_id"), r.getString("asset_type"), r.getString("asset_id"),
                r.getString("unit_id"), r.getString("target_id"), r.getString("approval_id"),
                r.getString("trusted_reading_id"), r.getTimestamp("evaluated_at").toInstant(),
                r.getString("alarm_type_id"), r.getString("severity_id"), r.getString("title_fr"),
                r.getString("actor_id"), r.getTimestamp("created_at").toInstant());
    }
}
