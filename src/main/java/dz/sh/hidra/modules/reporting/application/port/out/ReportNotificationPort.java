/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportNotificationPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.out
 *
 * @Description : Outbound reporting port ReportNotificationPort.
 *
 */
package dz.sh.hidra.modules.reporting.application.port.out;

/**
 * Outbound reporting port returning read-only projections, references, or artifact handles.
 */
public interface ReportNotificationPort {

    boolean available(String referenceId);
}
