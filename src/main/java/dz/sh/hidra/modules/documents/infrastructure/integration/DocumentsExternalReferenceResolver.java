/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsExternalReferenceResolver
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.integration
 *
 * @Description : Resolves document external references without importing target domains.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.integration;

/**
 * Resolves document external references without importing target domain models.
 */
public interface DocumentsExternalReferenceResolver {

    boolean targetExists(String targetModule, String targetTypeCode, String targetId);

    boolean workflowInstanceExists(String workflowInstanceId);

    boolean actorExists(String actorId);

    boolean externalSystemExists(String externalSystemId);
}
