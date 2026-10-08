/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopIncidentExternalReferenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.integration
 *
 * @Description : No-op incident external reference resolver.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.integration;

/**
 * No-op incident external reference resolver.
 */
public class NoopIncidentExternalReferenceResolver implements IncidentExternalReferenceResolver {

    @Override
    public boolean monitoringAlertCandidateExists(String referenceId) {
        return false;
    }

    @Override
    public boolean alarmExists(String alarmId) {
        return false;
    }

    @Override
    public boolean leakDetectionCaseExists(String leakCaseId) {
        return false;
    }

    @Override
    public boolean topologyAssetExists(String topologyAssetTypeCode, String topologyAssetId) {
        return false;
    }

    @Override
    public boolean workflowInstanceExists(String workflowInstanceId) {
        return false;
    }

    @Override
    public boolean documentExists(String documentReferenceId) {
        return false;
    }
}
