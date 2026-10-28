/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductRevisionPublicationRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Preserves governed product publication evidence under exact owner admission.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase.*;
import dz.sh.hidra.modules.custody.application.service.ProtectedProductPublicationAdmissionCoordinator.Contribution;
import dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.*;
import dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.Facts;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.util.*;
public interface ProductRevisionPublicationRepositoryPort {
    interface Admission {}
    Admission acquire(Command command);
    Scope scope(Admission admission);
    /** Native authorization must precede loading any envelope or historical result. */
    void authorized(Admission admission,String actorId);
    boolean replayed(Admission admission);
    boolean needsEligibility(Admission admission);
    Facts facts(Admission admission);
    String targetDigest(Admission admission);
    Selection selection(Admission admission);
    void eligible(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
    boolean mutation(Admission admission);
    Set<String> mutationSql();
    Result apply(Admission admission,Contribution contribution);
    void captureImages(Admission admission,List<ProtectedRelationFence> fences);
    void flush();
    boolean finalGuard(Admission admission,Contribution contribution,List<ProtectedRelationFence> fences);
}
