/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmExternalReferenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.integration
 *
 * @Description : Resolves external alarm references without importing external domains.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.integration;

/**
 * Resolves external alarm references without importing external domain models.
 */
public interface AlarmExternalReferenceResolver {

    boolean monitoringAlertCandidateExists(String candidateId);

    boolean telemetryReadingExists(String telemetryReadingId);

    boolean topologyAssetExists(String topologyAssetTypeCode, String topologyAssetId);

    boolean incidentExists(String incidentId);
}
