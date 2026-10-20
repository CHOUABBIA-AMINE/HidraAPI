/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcEngineeringGraphStationWitnessAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Verifies current Topology station owner/kind/status and owner-generation witnesses.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.EngineeringGraphStationWitnessPort;
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** A scalar current-source observation; never independently confers identity authority. */
@Repository
public final class JdbcEngineeringGraphStationWitnessAdapter implements EngineeringGraphStationWitnessPort {
    public static final String CURRENT_SQL = "SELECT g.generation FROM public.hidra_topology_facility f "
        + "JOIN public.hidra_topology_facility_owner_generation g ON g.facility_id=f.id "
        + "WHERE f.id=? AND f.owner_party_id=? AND f.status='ACTIVE' "
        + "AND f.facility_kind IN ('STATION','COMPRESSOR_STATION','PUMP_STATION','METERING_STATION') "
        + "AND g.present AND g.generation>0";
    private final JdbcTemplate jdbc;
    private final ProtectedTransactionExecutor executor;

    public JdbcEngineeringGraphStationWitnessAdapter(JdbcTemplate jdbc, ProtectedTransactionExecutor executor) {
        this.jdbc = Objects.requireNonNull(jdbc);
        this.executor = Objects.requireNonNull(executor);
    }

    private void requireProtectedOwner() {
        if (!StackWalker.getInstance().walk(frames -> frames.anyMatch(frame -> frame.getClassName().equals(
                "dz.sh.hidra.modules.topology.application.service.EngineeringNetworkDraftApplicationService")))
            || !TransactionSynchronizationManager.isActualTransactionActive()
            || TransactionSynchronizationManager.isCurrentTransactionReadOnly())
            throw new IllegalStateException("Protected station witness owner required");
        executor.requireAdmission();
    }

    @Override
    public List<Witness> requireCurrent(String companyPartyId, EngineeringGraphDraft graph) {
        requireProtectedOwner();
        Objects.requireNonNull(companyPartyId);
        Objects.requireNonNull(graph);
        // One bounded protected SQL read for each distinct current Facility row.
        // Two independent authored nodes may refer to the same Facility; the registered
        // graph contract places uniqueness on NODE identity, not on Facility references.
        List<String> stationIds=graph.nodes().stream()
            .filter(n -> n.kind()==EngineeringGraphDraft.NodeKind.STATION)
            .map(EngineeringGraphDraft.Node::facilityId).sorted().toList();
        if(stationIds.isEmpty())return List.of();
        List<String> uniqueIds=stationIds.stream().distinct().toList();
        String placeholders=String.join(",",java.util.Collections.nCopies(uniqueIds.size(),"?"));
        String sql="SELECT f.id,g.generation FROM public.hidra_topology_facility f "
            +"JOIN public.hidra_topology_facility_owner_generation g ON g.facility_id=f.id "
            +"WHERE f.owner_party_id=? AND f.status='ACTIVE' "
            +"AND f.facility_kind IN ('STATION','COMPRESSOR_STATION','PUMP_STATION','METERING_STATION') "
            +"AND g.present AND g.generation>0 AND f.id IN ("+placeholders+")";
        Object[] args=new Object[uniqueIds.size()+1];args[0]=companyPartyId;
        for(int i=0;i<uniqueIds.size();i++)args[i+1]=uniqueIds.get(i);
        var found=jdbc.query(sql,(rs,n)->new Witness(rs.getString(1),rs.getLong(2)),args);
        if(found.size()!=uniqueIds.size())
            throw new IllegalStateException("Station evidence unavailable");
        var uniqueWitnesses=new ArrayList<>(found);
        uniqueWitnesses.sort(Comparator.comparing(Witness::facilityId));
        for(int i=0;i<uniqueIds.size();i++)
            if(!uniqueIds.get(i).equals(uniqueWitnesses.get(i).facilityId()))
                throw new IllegalStateException("Station witness mismatch");
        var witnesses=new ArrayList<Witness>(stationIds.size());
        for(int i=0;i<stationIds.size();i++){
            int index=java.util.Collections.binarySearch(uniqueIds,stationIds.get(i));
            if(index<0)throw new IllegalStateException("Missing author station identity");
            witnesses.add(uniqueWitnesses.get(index));
        }
        return List.copyOf(witnesses);
    }

    @Override
    public boolean verifyCurrent(String companyPartyId, List<Witness> witnesses) {
        requireProtectedOwner();
        Objects.requireNonNull(companyPartyId);
        Objects.requireNonNull(witnesses);
        // Supporting check only; the owning JDBC draft adapter must still embed the entire
        // station+generation predicate in its ONE final materialized physical SQL guard.
        for (var witness : witnesses) {
            var values = jdbc.query(CURRENT_SQL, (rs, n) -> rs.getLong(1), witness.facilityId(), companyPartyId);
            if (values.size() != 1 || values.get(0) != witness.ownerGeneration()) return false;
        }
        return true;
    }
}
