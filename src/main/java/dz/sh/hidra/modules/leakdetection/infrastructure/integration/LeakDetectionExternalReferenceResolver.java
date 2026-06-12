/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionExternalReferenceResolver
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.integration
 *
 * @Description : Resolves leak detection external references without importing external domains.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.integration;

/**
 * Resolves leak detection external references without importing external domain models.
 */
public interface LeakDetectionExternalReferenceResolver {

    boolean topologyAssetExists(String topologyAssetType, String topologyAssetId);

    boolean trustedTelemetryReadingExists(String trustedTelemetryReadingId);

    boolean monitoringEvaluationExists(String monitoringEvaluationId);

    boolean alarmExists(String alarmId);

    boolean incidentExists(String incidentId);
}
