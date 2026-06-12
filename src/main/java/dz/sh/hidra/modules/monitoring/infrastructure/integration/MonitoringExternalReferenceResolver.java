/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringExternalReferenceResolver
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.integration
 *
 * @Description : Resolves external monitoring references without importing external domains.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.integration;

/**
 * Resolves external monitoring references without importing external domain models.
 */
public interface MonitoringExternalReferenceResolver {

    boolean topologyAssetExists(String topologyAssetType, String topologyAssetId);

    boolean planTargetExists(String planTargetId);

    boolean trustedTelemetryReadingExists(String trustedTelemetryReadingId);
}
