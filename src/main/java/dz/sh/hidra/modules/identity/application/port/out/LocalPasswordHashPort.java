/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalPasswordHashPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Defines one-way password hashing required by persistent LOCAL credential provisioning.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

/**
 * Technical hashing boundary used by Identity application services.
 */
public interface LocalPasswordHashPort {

    String hash(String rawPassword);
}
