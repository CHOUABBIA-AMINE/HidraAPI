/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedInstalledUnitRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Implements the protected installed-unit lifecycle within its owning boundary.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase.*;
import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.time.Instant;
import java.util.*;

/** Internal storage authority stays with its opaque issuer, not the constructible result records. */
public interface ProtectedInstalledUnitRepositoryPort {
    interface Admission {}
    record Hint(InstalledUnit unit,InstalledUnitInstallation source) {}
    record Snapshot(InstalledUnit unit,InstalledUnitInstallation source,InstalledUnitInstallation replacement,
            List<InstalledUnitInstallation> history,List<InstalledUnitInstallation> replacementHistory,
            String receiptDigest,Result receiptResult) {
        public Snapshot {history=List.copyOf(history);replacementHistory=List.copyOf(replacementHistory);}
    }
    record Origin(String actorId,String bindingId,String sessionId,Instant sampledAt,Instant validUntil,int backendPid,long transactionId) {}
    Hint discover(Command command);
    Admission acquire(Command command,Hint hint,List<ProtectedRelationFence> expectedFences);
    Snapshot observe(Admission ticket);
    Set<String> mutationSql();
    List<ProtectedRelationFence> finalFences(Admission ticket);
    String commandDigest(Command command);
    void apply(Admission ticket,Result result,Origin origin);
    void verifyResult(Admission ticket,Result result,boolean replay);
    void freeze(Admission ticket,Origin origin,List<ProtectedRelationFence> fences);
    boolean finalGuard(Admission ticket);
    void reject(Admission ticket);
    void flush();
}
