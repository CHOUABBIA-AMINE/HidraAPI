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
        return eligible(definitionId, version, module, type, purpose);
    }

    @Override
    public boolean lockCurrentInstanceConfiguration(String definitionId, int version,
            String module, String type, String purpose) {
        if (!wellFormed(definitionId, version, module, type, purpose)) return false;
        // This native query runs through the current JPA EntityManager connection and
        // original Workflow write transaction. The definer acquires the four shared
        // row locks without granting the app database role UPDATE on configuration.
        return Boolean.TRUE.equals(em.createNativeQuery(
                "select public.hidra_workflow_t30_lock_current_configuration("
                + "cast(:definition as text), :revision, cast(:module as text), "
                + "cast(:type as text), cast(:purpose as text))")
                .setParameter("definition", definitionId)
                .setParameter("revision", version)
                .setParameter("module", module)
                .setParameter("type", type)
                .setParameter("purpose", purpose)
                .getSingleResult());
    }

    private static boolean wellFormed(String definitionId, int version, String module,
            String type, String purpose) {
        return definitionId != null && !definitionId.isBlank() && version >= 1
                && module != null && !module.isBlank()
                && type != null && !type.isBlank() && purpose != null && !purpose.isBlank();
    }

    private boolean eligible(String definitionId, int version, String module,
            String type, String purpose) {
        if (!wellFormed(definitionId, version, module, type, purpose)) return false;
        var definition = em.find(WorkflowDefinitionJpaEntity.class, definitionId);
        if (definition == null || definition.status() != WorkflowDefinitionStatus.ACTIVE
                || definition.version() != version) return false;
        // This is advisory only. The command uses the locked database function.
        var ids = java.util.stream.Stream.of(type, purpose).distinct().sorted().toList();
        java.util.Map<String, WorkflowCatalogEntryJpaEntity> entries = new java.util.HashMap<>();
        for (String id : ids) {
            var entry = em.find(WorkflowCatalogEntryJpaEntity.class, id);
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
        var rows = matches.getResultList();
        return rows.size() == 1 && rows.getFirst().active();
    }
}
