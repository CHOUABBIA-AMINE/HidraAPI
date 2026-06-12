/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopAlarmExternalReferenceResolver
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.integration
 *
 * @Description : No-op alarm external reference resolver.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.integration;

/**
 * No-op alarm external reference resolver.
 */
public class NoopAlarmExternalReferenceResolver implements AlarmExternalReferenceResolver {

    @Override
    public boolean monitoringAlertCandidateExists(String candidateId) {
        return true;
    }

    @Override
    public boolean telemetryReadingExists(String telemetryReadingId) {
        return true;
    }

    @Override
    public boolean topologyAssetExists(String topologyAssetTypeCode, String topologyAssetId) {
        return true;
    }

    @Override
    public boolean incidentExists(String incidentId) {
        return true;
    }
}
