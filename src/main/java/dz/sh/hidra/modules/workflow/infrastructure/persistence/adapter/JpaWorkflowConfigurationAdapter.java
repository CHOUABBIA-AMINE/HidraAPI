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
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowDefinitionJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowDefinitionTargetBindingJpaEntity;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import jakarta.persistence.LockModeType;
import java.util.Comparator;
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
    @Override
    public boolean currentInstanceConfiguration(String definitionId, int version,
            String module, String type, String purpose) {
        return eligible(definitionId, version, module, type, purpose, false);
    }

    @Override
    public boolean lockCurrentInstanceConfiguration(String definitionId, int version,
            String module, String type, String purpose) {
        return eligible(definitionId, version, module, type, purpose, true);
    }

    private boolean eligible(String definitionId, int version, String module,
            String type, String purpose, boolean locked) {
        if (definitionId == null || definitionId.isBlank() || version < 1
                || module == null || module.isBlank() || type == null || purpose == null) {
            return false;
        }
        var mode = locked ? LockModeType.PESSIMISTIC_READ : LockModeType.NONE;
        // Commands hold the definition, catalog and binding locks until transaction commit.
        var definition = locked
                ? em.find(WorkflowDefinitionJpaEntity.class, definitionId, mode)
                : em.find(WorkflowDefinitionJpaEntity.class, definitionId);
        if (definition == null || definition.status() != WorkflowDefinitionStatus.ACTIVE
                || definition.version() != version) return false;
        // A fixed lock order prevents the type/purpose catalog lookup from reversing on races.
        var ids = java.util.stream.Stream.of(type, purpose).distinct()
                .sorted(Comparator.naturalOrder()).toList();
        java.util.Map<String, WorkflowCatalogEntryJpaEntity> entries = new java.util.HashMap<>();
        for (String id : ids) {
            var entry = locked ? em.find(WorkflowCatalogEntryJpaEntity.class, id, mode)
                    : em.find(WorkflowCatalogEntryJpaEntity.class, id);
            if (entry == null || !entry.active()) return false;
            entries.put(id, entry);
        }
        if (!"WORKFLOW_TARGET_TYPE".equals(entries.get(type).catalogName())
                || !"WORKFLOW_PURPOSE".equals(entries.get(purpose).catalogName())) return false;
        var matches = em.createQuery(
                "select e from WorkflowDefinitionTargetBindingJpaEntity e "
                + "where e.definitionId=:definition and e.targetModule=:module "
                + "and e.targetTypeId=:type and e.workflowPurposeId=:purpose and e.active=true",
                WorkflowDefinitionTargetBindingJpaEntity.class)
                .setParameter("definition", definitionId).setParameter("module", module)
                .setParameter("type", type).setParameter("purpose", purpose)
                .setMaxResults(2);
        if (locked) matches.setLockMode(mode);
        var rows = matches.getResultList();
        return rows.size() == 1 && rows.getFirst().active();
    }
}
