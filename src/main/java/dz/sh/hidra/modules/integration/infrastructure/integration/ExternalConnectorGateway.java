/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalConnectorGateway
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.integration
 *
 * @Description : Gateway contract for external connector execution.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.integration;

/**
 * Gateway contract for external connector execution.
 */
public interface ExternalConnectorGateway {

    boolean canConnect(String connectorInstanceId);

    boolean canExchange(String connectorInstanceId, String direction);
}
