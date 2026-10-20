/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcSurveyRouteProfileRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Persists immutable branch surveys through the existing protected draft owner.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.SurveyRouteProfileRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Repository
public class JdbcSurveyRouteProfileRepositoryAdapter implements SurveyRouteProfileRepositoryPort {
    public static final String INSERT_SQL="INSERT INTO public.hidra_topology_engineering_survey_snapshot (draft_id,branch_id,version,graph_version,graph_sha256,payload_format,canonical_payload,sha256,transaction_id) VALUES (?,?,?,?,?,?,?,?,?)";
    private final JdbcTemplate jdbc;private final ProtectedTransactionExecutor executor;
    private final SurveyRouteProfileCodec codec=new SurveyRouteProfileCodec();
    public JdbcSurveyRouteProfileRepositoryAdapter(JdbcTemplate jdbc,ProtectedTransactionExecutor executor){this.jdbc=Objects.requireNonNull(jdbc);this.executor=Objects.requireNonNull(executor);}
    private void requireProtectedOwner(boolean requireObservedAdmission) {
        // Inward call from the one reviewed application aggregate, not an ad-hoc JDBC writer.
        if (!StackWalker.getInstance().walk(frames -> frames.anyMatch(frame -> frame.getClassName().equals(
                "dz.sh.hidra.modules.topology.application.service.EngineeringNetworkDraftApplicationService")))
            || !TransactionSynchronizationManager.isActualTransactionActive()
            || TransactionSynchronizationManager.isCurrentTransactionReadOnly())
            throw new IllegalStateException("Protected graph draft owner required");
        // A READ must observe sealed admission; an INSERT runs in the narrower
        // executor-issued provisional phase, where requireAdmission() is intentionally invalid.
        // The protected data source independently rejects SQL outside its exact allowlist.
        if (requireObservedAdmission) executor.requireAdmission();
    }


    @Override public void validateProfile(SurveyRouteProfile profile){codec.encode(profile);}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Snapshot asOf(UUID draftId,String branchId,long selectedVersion){
        requireProtectedOwner(true);
        var rows=jdbc.query("SELECT version,graph_version,graph_sha256,sha256,payload_format,canonical_payload FROM public.hidra_topology_engineering_survey_snapshot WHERE draft_id=? AND branch_id=? AND version<=? ORDER BY version DESC LIMIT 1",(r,n)->{
            byte[] bytes=r.getBytes(6);
            if(!SurveyRouteProfileCodec.FORMAT.equals(r.getString(5)) || !SurveyRouteProfileCodec.sha256(bytes).equals(r.getString(4)))throw new IllegalStateException("Invalid survey evidence");
            var profile=codec.decode(bytes);if(!profile.branchId().equals(branchId))throw new IllegalStateException("Survey branch mismatch");
            return new Snapshot(r.getLong(1),r.getLong(2),r.getString(3),r.getString(4),profile);
        },draftId,branchId,selectedVersion);
        return rows.isEmpty()?null:rows.get(0);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Snapshot append(UUID draftId,long version,long graphVersion,String graphSha,SurveyRouteProfile profile,long transactionId){
        requireProtectedOwner(false);byte[] bytes=codec.encode(profile);String sha=SurveyRouteProfileCodec.sha256(bytes);
        var result=new Snapshot(version,graphVersion,graphSha,sha,profile);
        if(jdbc.update(INSERT_SQL,draftId,profile.branchId(),version,graphVersion,graphSha,SurveyRouteProfileCodec.FORMAT,bytes,sha,transactionId)!=1)throw new IllegalStateException("Survey insertion not unique");
        return result;
    }
}
