/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskTelemetryEvidenceLookupPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.out
 *
 * @Description : Outbound port RiskTelemetryEvidenceLookupPort.
 *
 */
package dz.sh.hidra.modules.risk.application.port.out;

/**
 * Outbound port for external evidence or action integration.
 */
public interface RiskTelemetryEvidenceLookupPort {

    boolean available(String referenceId);
}
