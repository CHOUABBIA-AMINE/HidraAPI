/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditOwnerReferenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.integration
 *
 * @Description : Enforces Audit evidence integrity and explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.integration;
import dz.sh.hidra.modules.audit.application.port.out.AuditDocumentReferencePort;
import dz.sh.hidra.modules.documents.application.contract.audit.AuditDocumentReferenceContract;
import org.springframework.stereotype.Component;
/** Adapts Documents ownership; Workflow is a separate bean to avoid ambiguous boolean signatures. */
@Component
public final class AuditOwnerReferenceAdapter implements AuditDocumentReferencePort {
    private final AuditDocumentReferenceContract documents;
    public AuditOwnerReferenceAdapter(AuditDocumentReferenceContract documents) {this.documents=java.util.Objects.requireNonNull(documents);}
    public boolean available(String id) {return documents.exists(id);}
    @Component
    public static final class Workflow implements dz.sh.hidra.modules.audit.application.port.out.AuditWorkflowReferencePort {
        private final dz.sh.hidra.modules.workflow.application.contract.audit.AuditWorkflowReferenceContract workflows;
        public Workflow(dz.sh.hidra.modules.workflow.application.contract.audit.AuditWorkflowReferenceContract workflows) {this.workflows=java.util.Objects.requireNonNull(workflows);}
        public boolean available(String id) {return workflows.exists(id);}
    }
}
