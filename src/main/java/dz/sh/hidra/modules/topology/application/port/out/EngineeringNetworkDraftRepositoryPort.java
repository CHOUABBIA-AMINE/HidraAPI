/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Binds draft evidence to one protected physical final guard.
 *
 */
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
    default Set<String> surveyMutationSql(){throw new IllegalStateException("Survey SQL not integrated");}
    default void bindSurveyProof(Admission a,SurveyRouteProfileRepositoryPort.Snapshot snapshot){throw new IllegalStateException("Survey proof not integrated");}
    Result apply(Admission admission,Contribution contribution);
    void flush();
    boolean finalGuard(Admission admission,Contribution contribution,List<ProtectedRelationFence> fences);
}
