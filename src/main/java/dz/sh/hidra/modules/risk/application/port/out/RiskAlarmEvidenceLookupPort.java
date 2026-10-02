/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAlarmEvidenceLookupPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.out
 *
 * @Description : Outbound port RiskAlarmEvidenceLookupPort.
 *
 */
package dz.sh.hidra.modules.risk.application.port.out;

/**
 * Outbound port for external evidence or action integration.
 */
public interface RiskAlarmEvidenceLookupPort {

    boolean available(String referenceId);
}
