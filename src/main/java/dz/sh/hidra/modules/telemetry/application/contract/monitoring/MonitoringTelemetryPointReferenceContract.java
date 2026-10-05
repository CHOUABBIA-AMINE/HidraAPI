/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringTelemetryPointReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.contract.monitoring
 *
 * @Description : Telemetry-owned public point-existence contract exported to Monitoring.
 *
 */
package dz.sh.hidra.modules.telemetry.application.contract.monitoring;

public interface MonitoringTelemetryPointReferenceContract {

    boolean exists(String telemetryPointId);
}
