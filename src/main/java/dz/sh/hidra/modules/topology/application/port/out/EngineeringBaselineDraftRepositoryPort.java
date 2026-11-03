/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineDraftRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Owns opaque baseline storage admissions and exact final transaction evidence.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;


import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineDraftUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringBaselineSourcePort.Original;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest.Completeness;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.util.*;

public interface EngineeringBaselineDraftRepositoryPort {
    interface Admission {}
    void lockSourcesAndStorage();
    Admission select(Command command,Original origin);
    dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest manifest(Admission admission);
    Set<String> mutationSql();
    Result apply(Admission admission,Completeness current);
    void flush();
    void prepareFinal(Admission admission,List<ProtectedRelationFence> fences);
    boolean finalGuard(Admission admission);
    List<ProtectedRelationFence> finalFences();
}
