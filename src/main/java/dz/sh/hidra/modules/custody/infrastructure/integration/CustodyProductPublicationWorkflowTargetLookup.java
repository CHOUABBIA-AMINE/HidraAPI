/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyProductPublicationWorkflowTargetLookup
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.integration
 *
 * @Description : Preserves governed product publication evidence under exact owner admission.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.integration;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import java.util.*;
import org.springframework.stereotype.Component;
@Component("custodyProductPublicationWorkflowTargetLookup")
public class CustodyProductPublicationWorkflowTargetLookup implements WorkflowOwnedTargetLookup {
    private final ProductRevisionPublicationUseCase publications;
    public CustodyProductPublicationWorkflowTargetLookup(ProductRevisionPublicationUseCase publications){this.publications=Objects.requireNonNull(publications);}
    @Override public String module(){return "custody";}
    @Override public Set<String> targetTypeCodes(){return Set.of("PRODUCT_SOURCE_PUBLICATION");}
    @Override public Optional<Target> eligibleTarget(String typeCode,String targetId){
        if(!targetTypeCodes().contains(typeCode) || targetId==null || !targetId.matches("[0-9a-f]{64}"))return Optional.empty();
        return publications.candidateForWorkflow(targetId).map(actual->{if(!actual.equals(targetId))throw new IllegalStateException("Publication target binding mismatch");return new Target(actual,actual,"Product source publication");});
    }
}
