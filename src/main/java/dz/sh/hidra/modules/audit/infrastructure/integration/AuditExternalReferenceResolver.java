/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditExternalReferenceResolver
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.integration
 *
 * @Description : Resolves audit external references without importing foreign domains.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.integration;

/**
 * Resolves audit external references without importing foreign domain models.
 */
public interface AuditExternalReferenceResolver {

    boolean sourceEventAvailable(String sourceModule, String sourceEventId);

    boolean targetAvailable(String targetModule, String targetType, String targetId);

    boolean workflowReferenceAvailable(String workflowInstanceId);

    boolean documentReferenceAvailable(String documentReferenceId);
}
