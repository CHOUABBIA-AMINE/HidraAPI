/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcDeviationMetricReceiptAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter
 *
 * @Description : Persist immutable metric lineage and serialize exact scoped replay.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.analytics.application.port.in.DeviationMetricRunUseCase.Result;
import dz.sh.hidra.modules.analytics.application.port.in.DeviationMetricRunUseCase.Source;
import dz.sh.hidra.modules.analytics.application.port.out.DeviationMetricReceiptPort;
import dz.sh.hidra.modules.analytics.domain.service.AbsoluteDeviationMetricV1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcDeviationMetricReceiptAdapter implements DeviationMetricReceiptPort {
    private static final String TABLE="public.hidra_analytics_deviation_metric_receipt";
    private static final String SELECT="""
            select r.*,m.run_status,m.error_code,m.started_at,m.completed_at,
                   v.value_numeric
            from public.hidra_analytics_deviation_metric_receipt r
            join public.hidra_analytics_metric_evaluation_run m on m.id=r.run_id
            left join public.hidra_analytics_metric_value v on v.id=r.value_id
            """;
    private final JdbcTemplate sql;
    @PersistenceContext private EntityManager entityManager;
    public JdbcDeviationMetricReceiptAdapter(JdbcTemplate sql) { this.sql=sql; }

    @Override
    public void lock(String company,String facility,String version,String evaluation) {
        // Transaction-level lock; the unique constraint independently enforces uniqueness.
        String key=company.length()+":"+company+"|"+facility.length()+":"+facility
                +"|"+version.length()+":"+version+"|"+evaluation;
        sql.queryForList("select pg_advisory_xact_lock(hashtextextended(?,0))",key);
    }

    @Override
    public Optional<Result> byKey(String company,String facility,String version,String evaluation) {
        return sql.query(SELECT+"""
                where r.company_party_id=? and r.facility_id=?
                  and r.definition_version_id=? and r.evaluation_id=?
                """,JdbcDeviationMetricReceiptAdapter::read,
                company,facility,version,evaluation).stream().findFirst();
    }

    @Override
    public Optional<Result> byRun(String company,String facility,String runId) {
        return sql.query(SELECT+"""
                where r.company_party_id=? and r.facility_id=? and r.run_id=?
                """,JdbcDeviationMetricReceiptAdapter::read,
                company,facility,runId).stream().findFirst();
    }

    @Override
    public Configuration eligible(String versionId,String unitId,Instant evaluatedAt) {
        if(unitId==null||unitId.isBlank()||evaluatedAt==null)
            throw new UnsupportedOperationException("Metric unit or source time unavailable.");
        // The explicit formula is a version marker, not an executable expression.
        List<Configuration> rows=sql.query("""
                select d.id,d.unit_id
                from public.hidra_analytics_metric_definition_version v
                join public.hidra_analytics_metric_definition d on d.id=v.metric_definition_id
                where v.id=? and v.version_number=1
                  and v.formula_expression='ABSOLUTE_DEVIATION_V1'
                  and d.code='ABSOLUTE_DEVIATION' and d.active=true
                  and d.unit_id=? and (v.valid_from is null or v.valid_from<=?)
                  and (v.valid_to is null or v.valid_to>=?)
                for share of v,d
                """,(row,index)->new Configuration(row.getString(1),row.getString(2)),
                versionId,unitId,Timestamp.from(evaluatedAt),Timestamp.from(evaluatedAt));
        if(rows.size()!=1) throw new UnsupportedOperationException("Unsupported metric definition or version.");
        return rows.getFirst();
    }

    @Override
    public void seal(String runId,String valueId,String company,String facility,
            String version,String evaluation,String actor,Source source) {
        // Spring Data repositories may delay inserts. Flush before the JDBC referential receipt.
        entityManager.flush();
        int written=sql.update("""
                insert into public.hidra_analytics_deviation_metric_receipt (
                    run_id,value_id,company_party_id,facility_id,definition_id,definition_version_id,
                    evaluation_id,rule_id,rule_revision,telemetry_point_id,source_outcome,unit_id,
                    actual_value,expected_value,target_id,target_revision_id,approval_id,
                    trusted_reading_id,raw_reading_id,assessment_id,binding_id,evaluated_at,
                    source_recorded_at,method_revision,actor_id,created_at)
                select ?,?,?,?,?,v.id,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,
                       '1',?,clock_timestamp()
                from public.hidra_analytics_metric_definition_version v
                where v.id=?
                """,runId,valueId,company,facility,
                eligible(version,source.unitId(),source.evaluatedAt()).definitionId(),
                source.evaluationId(),source.ruleId(),source.ruleRevision(),
                source.telemetryPointId(),source.outcome(),source.unitId(),
                source.actual(),source.expected(),source.targetId(),source.targetRevisionId(),
                source.approvalId(),source.trustedReadingId(),source.rawReadingId(),
                source.assessmentId(),source.bindingId(),Timestamp.from(source.evaluatedAt()),
                Timestamp.from(source.recordedAt()),actor,version);
        if(written!=1) throw new IllegalStateException("Could not seal metric result.");
    }

    private static Result read(ResultSet row,int n) throws SQLException {
        Source evidence=new Source(row.getString("evaluation_id"),row.getString("rule_id"),
                row.getLong("rule_revision"),row.getString("company_party_id"),row.getString("facility_id"),
                row.getString("telemetry_point_id"),row.getString("unit_id"),
                row.getString("source_outcome"),row.getBigDecimal("actual_value"),
                row.getBigDecimal("expected_value"),row.getString("target_id"),
                row.getString("target_revision_id"),row.getString("approval_id"),
                row.getString("trusted_reading_id"),row.getString("raw_reading_id"),
                row.getString("assessment_id"),row.getString("binding_id"),
                time(row,"evaluated_at"),time(row,"source_recorded_at"));
        return new Result(row.getString("run_id"),row.getString("run_status"),
                row.getString("error_code"),row.getString("company_party_id"),
                row.getString("facility_id"),AbsoluteDeviationMetricV1.CODE,
                row.getString("definition_version_id"),
                AbsoluteDeviationMetricV1.REVISION,row.getString("unit_id"),
                row.getBigDecimal("value_numeric"),time(row,"started_at"),
                time(row,"completed_at"),evidence,false);
    }
    private static Instant time(ResultSet r,String name) throws SQLException {
        Timestamp value=r.getTimestamp(name);
        return value==null?null:value.toInstant();
    }
}
