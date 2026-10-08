/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityAssessmentWorkflowReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Validates owner-controlled IntegrityAssessment references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.workflow.application.contract.integrity.IntegrityAssessmentWorkflowReferenceContract;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowConfigurationPort;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class IntegrityAssessmentWorkflowReferenceQueryService implements IntegrityAssessmentWorkflowReferenceContract {
    private final WorkflowInstanceRepositoryPort instances;
    private final WorkflowConfigurationPort configuration;
    public IntegrityAssessmentWorkflowReferenceQueryService(WorkflowInstanceRepositoryPort instances,WorkflowConfigurationPort configuration) {
        this.instances=Objects.requireNonNull(instances); this.configuration=Objects.requireNonNull(configuration);
    }
    @Override @Transactional(readOnly=true)
    public boolean matches(String id,String targetId) {
        if(id==null || id.isBlank() || targetId==null || targetId.isBlank()) return false;
        var instance=instances.findById(id.trim()).orElse(null);
        if(instance==null || !id.trim().equals(instance.id()) || !"integrity".equals(instance.targetModule())
                || !targetId.trim().equals(instance.targetId()) || instance.workflowPurposeId()==null) return false;
        var type=configuration.requireActiveCatalog(instance.targetTypeId(),"WORKFLOW_TARGET_TYPE");
        var purpose=configuration.requireActiveCatalog(instance.workflowPurposeId(),"WORKFLOW_PURPOSE");
        return type!=null && type.active() && instance.targetTypeId().equals(type.id())
                && "WORKFLOW_TARGET_TYPE".equals(type.family()) && "INTEGRITY_ASSESSMENT".equals(type.code())
                && purpose!=null && purpose.active() && instance.workflowPurposeId().equals(purpose.id())
                && "WORKFLOW_PURPOSE".equals(purpose.family())
                && configuration.activeBinding(instance.definitionId(),"integrity",type.id(),purpose.id());
    }
}
