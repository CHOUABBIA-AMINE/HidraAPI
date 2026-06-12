/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAuditEventPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.out
 *
 * @Description : Outbound port RiskAuditEventPort.
 *
 */
package dz.sh.hidra.modules.risk.application.port.out;

/**
 * Outbound port for external evidence or action integration.
 */
public interface RiskAuditEventPort {

    boolean available(String referenceId);
}
