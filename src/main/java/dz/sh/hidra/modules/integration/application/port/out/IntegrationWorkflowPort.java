/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationWorkflowPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.out
 *
 * @Description : Outbound integration port IntegrationWorkflowPort.
 *
 */
package dz.sh.hidra.modules.integration.application.port.out;

/**
 * Outbound integration port.
 */
public interface IntegrationWorkflowPort {

    boolean available(String referenceId);
}
