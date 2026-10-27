/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductEngineeringDraftRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Provides company-owned catalog-linked product metadata drafts.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;

import dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.*;
import dz.sh.hidra.modules.custody.application.service.ProtectedProductDraftAdmissionCoordinator.Contribution;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.util.*;

public interface ProductEngineeringDraftRepositoryPort {
    interface Admission {}
    Admission acquire(Command command);
    /** Server-only reference resolution under owner fences; no payload or count disclosure. */
    String catalogProductId(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
    void eligible(Admission admission);
    boolean mutation(Admission admission);
    Set<String> mutationSql();
    Result apply(Admission admission,Contribution contribution);
    void flush();
    boolean finalGuard(Admission admission,Contribution contribution,List<ProtectedRelationFence> fences);
}
