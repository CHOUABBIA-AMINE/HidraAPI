/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowConfigurationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Enforces owner-validated Workflow execution and immutable evidence.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowConfigurationPort;
import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowCatalogEntryJpaEntity;
import jakarta.persistence.EntityManager;
import java.util.Objects;
import org.springframework.stereotype.Component;
@Component
public class JpaWorkflowConfigurationAdapter implements WorkflowConfigurationPort {
    private final EntityManager em;
    public JpaWorkflowConfigurationAdapter(EntityManager em){this.em=Objects.requireNonNull(em);}
    public Catalog requireActiveCatalog(String id,String family){
        var entry=id==null?null:em.find(WorkflowCatalogEntryJpaEntity.class,id);
        if(entry==null || !entry.active() || !family.equals(entry.catalogName()))
            throw new InvalidWorkflowValueException("Active catalog entry required in "+family+": "+id);
        return new Catalog(entry.id(),entry.catalogName(),entry.code(),entry.active());
    }
    public boolean activeBinding(String definitionId,String module,String type,String purpose){
        return em.createQuery("select count(e) from WorkflowDefinitionTargetBindingJpaEntity e where e.definitionId=:definition and e.targetModule=:module and e.targetTypeId=:type and e.workflowPurposeId=:purpose and e.active=true",Long.class)
            .setParameter("definition",definitionId).setParameter("module",module).setParameter("type",type)
            .setParameter("purpose",purpose).getSingleResult()==1L;
    }
}
