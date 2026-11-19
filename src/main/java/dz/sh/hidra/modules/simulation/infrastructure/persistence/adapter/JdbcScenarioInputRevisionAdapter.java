/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcScenarioInputRevisionAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 * @Description : Appends one immutable Simulation-owned revision using parameterized JDBC and verifies stored canonical values.
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;


import dz.sh.hidra.modules.simulation.application.port.out.ScenarioInputRevisionRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision;
import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** No update, delete, or cross-owner table access is available through this adapter. */
@Repository
public class JdbcScenarioInputRevisionAdapter implements ScenarioInputRevisionRepositoryPort {
    private static final String TABLE="public.hidra_simulation_t31_scenario_input_revision";
    private static final String SCOPE="company_id=? AND pipeline_system_id=?";
    private final JdbcTemplate jdbc;
    public JdbcScenarioInputRevisionAdapter(JdbcTemplate jdbc){this.jdbc=Objects.requireNonNull(jdbc);}
    private static void active() {
        if(!TransactionSynchronizationManager.isActualTransactionActive())
            throw new IllegalStateException("Scenario history requires a current transaction");
    }
    @Override public Optional<Locator> locate(Scope s,UUID id,long revision) {
        active();
        return jdbc.query("SELECT scenario_id,revision,baseline_receipt_id,baseline_digest FROM "+TABLE+
                " WHERE "+SCOPE+" AND scenario_id=? AND revision=?",
                (rs,n)->new Locator((UUID)rs.getObject("scenario_id"),rs.getLong("revision"),
                        (UUID)rs.getObject("baseline_receipt_id"),rs.getString("baseline_digest")),
                s.companyId(),s.pipelineSystemId(),id,revision).stream().findFirst();
    }
    @Override public Optional<ScenarioInputRevision> find(Scope s,UUID id,long revision) {
        active();
        return jdbc.query("SELECT * FROM "+TABLE+" WHERE "+SCOPE+" AND scenario_id=? AND revision=?",
                (rs,n)->row(rs),s.companyId(),s.pipelineSystemId(),id,revision).stream().findFirst();
    }
    // A revision is immutable: do not lock its predecessor FOR UPDATE.
    // The (scenario_id, revision) primary key and INSERT ON CONFLICT arbitrate concurrent appends.
    @Override public Optional<ScenarioInputRevision> head(Scope s,UUID id) {
        active();
        return jdbc.query("SELECT * FROM "+TABLE+" WHERE "+SCOPE+
                " AND scenario_id=? ORDER BY revision DESC LIMIT 1",
                (rs,n)->row(rs),s.companyId(),s.pipelineSystemId(),id).stream().findFirst();
    }
    @Override public Optional<ScenarioInputRevision> byRequest(Scope s,String actor,String key) {
        active();
        return jdbc.query("SELECT * FROM "+TABLE+" WHERE "+SCOPE+" AND actor_id=? AND request_key=?",
                (rs,n)->row(rs),s.companyId(),s.pipelineSystemId(),actor,key).stream().findFirst();
    }
    @Override public boolean append(ScenarioInputRevision v) {
        active();
        var p=v.inputs().pressure(); var f=v.inputs().delivery();
        int rows=jdbc.update("""
                INSERT INTO public.hidra_simulation_t31_scenario_input_revision
                (scenario_id,revision,predecessor_revision,company_id,pipeline_system_id,
                 baseline_receipt_id,manifest_id,baseline_epoch,baseline_digest,
                 scenario_name,mode,pressure_value,pressure_unit,pressure_si_pa,
                 flow_value,flow_unit,flow_si_kg_per_s,duration_seconds,step_seconds,
                 actor_id,recorded_at,policy_id,input_digest,request_key,request_fingerprint)
                VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                ON CONFLICT DO NOTHING
                """,
                v.scenarioId(),v.revision(),v.revision()==1?null:v.revision()-1,
                v.scope().companyId(),v.scope().pipelineSystemId(),v.baseline().receiptId(),
                v.baseline().manifestId(),v.baseline().epoch(),v.baseline().digest(),
                v.inputs().name(),v.inputs().mode().name(),p.value(),p.unit().name(),p.si(),
                f.value(),f.unit().name(),f.si(),v.inputs().durationSeconds(),v.inputs().stepSeconds(),
                v.actorId(),Timestamp.from(v.recordedAt()),v.policyId(),
                v.inputDigest(),v.inputs().requestKey(),v.fingerprint());
        return rows==1;
    }
    private static ScenarioInputRevision row(ResultSet rs) throws SQLException {
        UUID id=(UUID)rs.getObject("scenario_id");
        long revision=rs.getLong("revision");
        Scope s=new Scope(rs.getString("company_id"),rs.getString("pipeline_system_id"));
        Baseline b=new Baseline((UUID)rs.getObject("baseline_receipt_id"),
                (UUID)rs.getObject("manifest_id"),rs.getLong("baseline_epoch"),rs.getString("baseline_digest"));
        Boundary pressure=new Boundary(Slot.SUPPLY_PRESSURE,rs.getBigDecimal("pressure_value"),
                Unit.valueOf(rs.getString("pressure_unit")));
        Boundary flow=new Boundary(Slot.DELIVERY_MASS_FLOW,rs.getBigDecimal("flow_value"),
                Unit.valueOf(rs.getString("flow_unit")));
        if(pressure.si().compareTo(rs.getBigDecimal("pressure_si_pa"))!=0
                ||flow.si().compareTo(rs.getBigDecimal("flow_si_kg_per_s"))!=0)
            throw new IllegalStateException("Scenario stored SI integrity unavailable");
        var duration=rs.getObject("duration_seconds",Integer.class);
        var step=rs.getObject("step_seconds",Integer.class);
        Inputs inputs=new Inputs(rs.getString("scenario_name"),Mode.valueOf(rs.getString("mode")),
                b.receiptId(),b.digest(),List.of(pressure,flow),duration,step,rs.getString("request_key"));
        if(!ScenarioInputRevision.POLICY.equals(rs.getString("policy_id"))
                ||rs.getLong("predecessor_revision")!=(revision==1?0:revision-1))
            throw new IllegalStateException("Scenario stored policy or predecessor unavailable");
        return new ScenarioInputRevision(id,revision,revision-1,s,b,inputs,
                rs.getString("actor_id"),rs.getTimestamp("recorded_at").toInstant(),
                rs.getString("input_digest"),rs.getString("request_fingerprint"));
    }
}
