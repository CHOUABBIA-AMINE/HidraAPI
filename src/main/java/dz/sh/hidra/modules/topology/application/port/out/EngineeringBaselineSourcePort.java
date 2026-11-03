/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineSourcePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Obtains protected exact owner facts within the original baseline transaction.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;


import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest;
import dz.sh.hidra.modules.topology.domain.service.EngineeringBaselineCompletenessPolicy.SourceFacts;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;

public interface EngineeringBaselineSourcePort {
    enum Kind { NETWORK_PUBLICATION, STATION_CONFIGURATION, INSTALLED_UNIT,
        CHARACTERISTIC_LINK, MANUFACTURER_CHARACTERISTIC, PRODUCT_PUBLICATION }
    record Read(Kind kind,String referenceId,List<String> units) {
        public Read {units=List.copyOf(units);}
        public Read(Kind kind,String referenceId){this(kind,referenceId,List.of());}
    }
    record Original(String bindingId,String sessionId,String userId,int backendPid,long transactionId,
                    String databaseRole,Instant sampledAt,Instant validUntil) {}
    interface Admission {}
    Admission capture(EngineeringBaselineManifest manifest,Original original,Consumer<Read> authorizer);
    List<SourceFacts> facts(Admission admission);
    void requireCurrent(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
}
