/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SuppliedFluidPropertyRevisionRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Preserves exact authored fluid facts independently of numerical qualification.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;

import dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.*;
import dz.sh.hidra.modules.custody.application.service.ProtectedSuppliedFluidPropertyAdmissionCoordinator.Contribution;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.util.*;

public interface SuppliedFluidPropertyRevisionRepositoryPort {
    interface Admission {}
    Admission acquire(Command command);
    /** Server-only reference resolution under owner fences; no payload or count disclosure. */
    String catalogProductId(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
    void eligible(Admission admission);
    dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.Facts facts(Admission admission);
    boolean mutation(Admission admission);
    Set<String> mutationSql();
    Result apply(Admission admission,Contribution contribution);
    void flush();
    boolean finalGuard(Admission admission,Contribution contribution,List<ProtectedRelationFence> fences);
}
