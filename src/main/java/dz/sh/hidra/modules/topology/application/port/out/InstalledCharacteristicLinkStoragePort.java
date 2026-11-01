/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 *
 * @Name        : InstalledCharacteristicLinkStoragePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Outbound contract for original-transaction immutable draft-link persistence.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase.*;
import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLink;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledCharacteristicLinkAuthorizationContract.Origin;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Neither the snapshot nor a storage ticket carries permission; the coordinator must separately authorize. */
public interface InstalledCharacteristicLinkStoragePort {
    interface Admission {}
    record Receipt(String commandSha256,long linkRevision) {
        public Receipt {
            if(commandSha256==null||!commandSha256.matches("[0-9a-f]{64}")||linkRevision<1)
                throw new IllegalArgumentException("Invalid request receipt");
        }
    }
    record Snapshot(InstalledCharacteristicLink current,InstalledCharacteristicLink revision,
                    InstalledCharacteristicLink replayRevision,List<InstalledCharacteristicLink> history,
                    Receipt receipt,Long headEpoch,Long nextBeforeRevision) {
        public Snapshot {
            history=List.copyOf(Objects.requireNonNull(history));
            if(headEpoch!=null&&headEpoch<1||nextBeforeRevision!=null&&nextBeforeRevision<1)
                throw new IllegalArgumentException("Invalid immutable source snapshot");
        }
        public List<InstalledCharacteristicLink> sourceRows() {
            var all=new java.util.LinkedHashMap<String,InstalledCharacteristicLink>();
            for(var x:history)add(all,x);
            add(all,current);add(all,revision);add(all,replayRevision);
            return List.copyOf(all.values());
        }
        private static void add(java.util.Map<String,InstalledCharacteristicLink> rows,InstalledCharacteristicLink link) {
            if(link!=null)rows.putIfAbsent(link.linkId()+"|"+link.linkRevision(),link);
        }
    }
    Admission acquire(Command command);
    Snapshot observe(Admission admission,Command command);
    Set<String> mutationSql();
    String commandDigest(Command command);
    Result perform(Admission admission,Command command,Origin original,Snapshot snapshot);
    void prepareFinal(Admission admission,Origin original,List<ProtectedRelationFence> fences);
    boolean finalGuard(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
    void flush();
    void reject(Admission admission);
}
