/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportTelemetryProjectionPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.out
 *
 * @Description : Outbound reporting port ReportTelemetryProjectionPort.
 *
 */
package dz.sh.hidra.modules.reporting.application.port.out;

/**
 * Outbound reporting port returning read-only projections, references, or artifact handles.
 */
public interface ReportTelemetryProjectionPort {

    boolean available(String referenceId);
}
