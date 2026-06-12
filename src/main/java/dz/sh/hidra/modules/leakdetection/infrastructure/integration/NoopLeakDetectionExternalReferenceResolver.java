/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopLeakDetectionExternalReferenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.integration
 *
 * @Description : No-op leak detection external reference resolver.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.integration;

/**
 * No-op leak detection external reference resolver.
 */
public class NoopLeakDetectionExternalReferenceResolver implements LeakDetectionExternalReferenceResolver {

    @Override
    public boolean topologyAssetExists(String topologyAssetType, String topologyAssetId) {
        return true;
    }

    @Override
    public boolean trustedTelemetryReadingExists(String trustedTelemetryReadingId) {
        return true;
    }

    @Override
    public boolean monitoringEvaluationExists(String monitoringEvaluationId) {
        return true;
    }

    @Override
    public boolean alarmExists(String alarmId) {
        return true;
    }

    @Override
    public boolean incidentExists(String incidentId) {
        return true;
    }
}
