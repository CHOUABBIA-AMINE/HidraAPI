/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopReportingExternalReferenceResolver
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.integration
 *
 * @Description : No-op reporting external reference resolver.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.integration;

/**
 * No-op reporting external reference resolver.
 */
public class NoopReportingExternalReferenceResolver implements ReportingExternalReferenceResolver {

    @Override
    public boolean sourceProjectionAvailable(String sourceModule, String sourceName) {
        return true;
    }

    @Override
    public boolean documentReferenceAvailable(String documentReferenceId) {
        return true;
    }

    @Override
    public boolean notificationRequestAvailable(String notificationRequestId) {
        return true;
    }

    @Override
    public boolean workflowReferenceAvailable(String workflowReferenceId) {
        return true;
    }

    @Override
    public boolean storageObjectAvailable(String storageObjectReferenceId) {
        return true;
    }
}
