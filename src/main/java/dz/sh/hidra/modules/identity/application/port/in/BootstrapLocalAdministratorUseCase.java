/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : BootstrapLocalAdministratorUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.in
 *
 * @Description : Defines safe one-time provisioning of a persistent LOCAL Hidra administrator.
 *
 */
package dz.sh.hidra.modules.identity.application.port.in;

/**
 * Application boundary for controlled LOCAL administrator bootstrap.
 */
public interface BootstrapLocalAdministratorUseCase {

    BootstrapResult bootstrap(BootstrapCommand command);

    record BootstrapCommand(
            String username,
            String password,
            String emailAddress,
            String displayName
    ) {
    }

    record BootstrapResult(
            String userId,
            String username,
            String roleCode,
            boolean created
    ) {
    }
}
