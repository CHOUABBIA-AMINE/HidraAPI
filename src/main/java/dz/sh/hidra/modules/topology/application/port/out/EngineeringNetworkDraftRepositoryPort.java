/** HAPI-588B work-in-progress; graph proof must be consumed by one physical final SQL guard. */
package dz.sh.hidra.modules.topology.application.port.out;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.*;
import dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator.Contribution;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.util.*;
public interface EngineeringNetworkDraftRepositoryPort {
    interface Admission {}
    Admission acquire(Command command);
    List<ProtectedRelationFence> finalFences(Admission admission);
    void eligible(Admission admission);
    boolean replay(Admission admission);
    Set<String> mutationSql();
    /** Reviewed metadata-version INSERT plus exact graph-snapshot INSERT, with hashes pinned separately. */
    default Set<String> graphMutationSql(){throw new IllegalStateException("Graph mutation not integrated");}
    /** Confirms a requested historical metadata-version is within this exact authorized draft. */
    default long currentGraphDraft(Admission admission, Long requestedVersion){
        throw new IllegalStateException("Graph version membership not integrated");
    }
    /** Supplies verified provenance to the single final SQL statement, never authority itself. */
    default void bindGraphProof(Admission admission, long selected,
            EngineeringGraphDraftRepositoryPort.Snapshot snapshot,
            List<EngineeringGraphStationWitnessPort.Witness> stations,long observedVersion){
        throw new IllegalStateException("Graph final guard not integrated");
    }
    Result apply(Admission admission,Contribution contribution);
    void flush();
    boolean finalGuard(Admission admission,Contribution contribution,List<ProtectedRelationFence> fences);
}
