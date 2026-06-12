/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskWorkflowPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.out
 *
 * @Description : Outbound port RiskWorkflowPort.
 *
 */
package dz.sh.hidra.modules.risk.application.port.out;

/**
 * Outbound port for external evidence or action integration.
 */
public interface RiskWorkflowPort {

    boolean available(String referenceId);
}
