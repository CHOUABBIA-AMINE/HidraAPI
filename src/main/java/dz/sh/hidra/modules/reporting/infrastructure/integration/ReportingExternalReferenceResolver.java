/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingExternalReferenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.integration
 *
 * @Description : Resolves reporting external references without importing foreign internals.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.integration;

/**
 * Resolves reporting external references without importing foreign domain models.
 */
public interface ReportingExternalReferenceResolver {

    boolean sourceProjectionAvailable(String sourceModule, String sourceName);

    boolean documentReferenceAvailable(String documentReferenceId);

    boolean notificationRequestAvailable(String notificationRequestId);

    boolean workflowReferenceAvailable(String workflowReferenceId);

    boolean storageObjectAvailable(String storageObjectReferenceId);
}
