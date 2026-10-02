/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterOperationalScopeUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.in
 *
 * @Description : Use case for validating and registering canonical operational scopes.
 *
 */
package dz.sh.hidra.modules.organization.application.port.in;

import dz.sh.hidra.modules.organization.application.command.RegisterOperationalScopeCommand;
import dz.sh.hidra.modules.organization.domain.model.OperationalScope;

public interface RegisterOperationalScopeUseCase {

    OperationalScope registerOperationalScope(RegisterOperationalScopeCommand command);
}
