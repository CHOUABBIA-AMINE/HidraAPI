/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopDocumentsExternalReferenceResolver
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.integration
 *
 * @Description : No-op documents external reference resolver.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.integration;

/**
 * No-op documents external reference resolver.
 */
public class NoopDocumentsExternalReferenceResolver implements DocumentsExternalReferenceResolver {

    @Override
    public boolean targetExists(String targetModule, String targetTypeCode, String targetId) {
        return true;
    }

    @Override
    public boolean workflowInstanceExists(String workflowInstanceId) {
        return true;
    }

    @Override
    public boolean actorExists(String actorId) {
        return true;
    }

    @Override
    public boolean externalSystemExists(String externalSystemId) {
        return true;
    }
}
