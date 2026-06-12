/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationSecretReferencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.port.out
 *
 * @Description : Outbound configuration port ConfigurationSecretReferencePort.
 *
 */
package dz.sh.hidra.modules.configuration.application.port.out;

/**
 * Outbound configuration port.
 */
public interface ConfigurationSecretReferencePort {

    boolean available(String referenceId);
}
