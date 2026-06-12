/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopMonitoringExternalReferenceResolver
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.integration
 *
 * @Description : No-op monitoring external reference resolver.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.integration;

/**
 * No-op monitoring external reference resolver.
 */
public class NoopMonitoringExternalReferenceResolver implements MonitoringExternalReferenceResolver {

    @Override
    public boolean topologyAssetExists(String topologyAssetType, String topologyAssetId) {
        return true;
    }

    @Override
    public boolean planTargetExists(String planTargetId) {
        return true;
    }

    @Override
    public boolean trustedTelemetryReadingExists(String trustedTelemetryReadingId) {
        return true;
    }
}
