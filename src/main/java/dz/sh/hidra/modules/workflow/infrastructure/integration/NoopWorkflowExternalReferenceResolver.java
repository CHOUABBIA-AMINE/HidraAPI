/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopWorkflowExternalReferenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.integration
 *
 * @Description : No-op workflow external reference resolver.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.integration;

/**
 * No-op workflow external reference resolver.
 */
public class NoopWorkflowExternalReferenceResolver implements WorkflowExternalReferenceResolver {

    @Override
    public boolean targetExists(String targetModule, String targetTypeId, String targetId) {
        return true;
    }

    @Override
    public boolean actorEligible(String actorId, String roleCodeSnapshot) {
        return true;
    }

    @Override
    public boolean organizationUnitAvailable(String organizationUnitId) {
        return true;
    }

    @Override
    public boolean auditOutboxAvailable(String outboxEventId) {
        return true;
    }
}
