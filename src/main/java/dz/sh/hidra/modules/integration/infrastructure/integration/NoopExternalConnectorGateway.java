/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopExternalConnectorGateway
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.integration
 *
 * @Description : No-op external connector gateway.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.integration;

/**
 * No-op external connector gateway.
 */
public class NoopExternalConnectorGateway implements ExternalConnectorGateway {

    @Override
    public boolean canConnect(String connectorInstanceId) {
        return true;
    }

    @Override
    public boolean canExchange(String connectorInstanceId, String direction) {
        return true;
    }
}
