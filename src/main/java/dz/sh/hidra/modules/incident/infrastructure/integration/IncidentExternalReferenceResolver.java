/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentExternalReferenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.integration
 *
 * @Description : Resolves incident external references without importing external domains.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.integration;

/**
 * Resolves incident external references without importing external domain models.
 */
public interface IncidentExternalReferenceResolver {

    boolean monitoringAlertCandidateExists(String referenceId);

    boolean alarmExists(String alarmId);

    boolean leakDetectionCaseExists(String leakCaseId);

    boolean topologyAssetExists(String topologyAssetTypeCode, String topologyAssetId);

    boolean workflowInstanceExists(String workflowInstanceId);

    boolean documentExists(String documentReferenceId);
}
