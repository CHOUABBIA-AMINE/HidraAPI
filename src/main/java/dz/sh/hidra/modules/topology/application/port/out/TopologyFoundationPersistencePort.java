package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.application.port.in.TopologyFoundationUseCase.*;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.time.Instant;
import java.util.*;

public interface TopologyFoundationPersistencePort {
    interface Admission {}
    record Origin(String actorId,String bindingId,String sessionId,Instant sampledAt,Instant validUntil,int backendPid,long transactionId,long partyGeneration) {}
    Admission acquire(Command command);
    Result observe(Admission admission);
    Set<String> mutationSql();
    Result persist(Admission admission,Origin origin);
    void freeze(Admission admission);
    boolean finalGuard(Admission admission,Origin origin,List<ProtectedRelationFence> ownerFences);
}
