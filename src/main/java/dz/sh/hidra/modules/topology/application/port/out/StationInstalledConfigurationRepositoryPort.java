/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInstalledConfigurationRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Protected installed-station configuration ownership boundary.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.*;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration;
import java.time.Instant;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.util.List;
import java.util.Set;

/** Opaque original-transaction ticket. Implementations reject non-owner callers and missing roles/locks. */
public interface StationInstalledConfigurationRepositoryPort {
    interface Admission {}
    /** Traceable scalars after original native admission; never accepted as permission alone. */
    record Origin(String bindingId,String sessionId,String userId,Instant sampledAt,Instant validUntil,
                  int backendPid,long transactionId){
        public Origin{
            if(bindingId==null || sessionId==null || userId==null || sampledAt==null
              || validUntil==null || backendPid<1 || transactionId<1 || !sampledAt.isBefore(validUntil))
                throw new IllegalArgumentException("Invalid admitted origin");
        }
        @Override public String toString(){return "Origin[private verified scalars]";}
    }
    record Snapshot(StationInstalledConfiguration current,StationInstalledConfiguration revision,
                    List<StationInstalledConfiguration> history,String requestDigest,
                    String requestRevision) {
        public Snapshot {history=List.copyOf(history);}
    }
    Admission acquire(Command command);
    Snapshot observe(Admission admission,Command command);
    Set<String> mutationSql();
    Result execute(Admission admission,Command command,Origin origin,Snapshot snapshot);
    void prepareFinal(Admission admission,Origin origin,List<ProtectedRelationFence> fences);
    boolean finalGuard(Admission admission);
    void flush();
    void reject(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
}
