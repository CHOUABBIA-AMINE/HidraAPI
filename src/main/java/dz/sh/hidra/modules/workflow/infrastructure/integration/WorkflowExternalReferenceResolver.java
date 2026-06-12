/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowExternalReferenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.integration
 *
 * @Description : Resolves workflow external references without importing target domains.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.integration;

/**
 * Resolves workflow external references without importing target domains.
 */
public interface WorkflowExternalReferenceResolver {

    boolean targetExists(String targetModule, String targetTypeId, String targetId);

    boolean actorEligible(String actorId, String roleCodeSnapshot);

    boolean organizationUnitAvailable(String organizationUnitId);

    boolean auditOutboxAvailable(String outboxEventId);
}
