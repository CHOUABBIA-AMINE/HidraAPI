/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Application service for validated operational-scope registration.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.command.RegisterOperationalScopeCommand;
import dz.sh.hidra.modules.organization.application.port.in.RegisterOperationalScopeUseCase;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeRegistryRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort;
import dz.sh.hidra.modules.organization.application.service.OperationalScopeRegistrationValidator.ValidatedTarget;
import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public final class OperationalScopeApplicationService implements RegisterOperationalScopeUseCase {

    private final OperationalScopeRegistrationValidator registrationValidator;
    private final OperationalScopeRegistryRepositoryPort registryRepository;

    public OperationalScopeApplicationService(
            OperationalScopeTargetResolverPort targetResolver,
            OperationalScopeRegistryRepositoryPort registryRepository
    ) {
        this.registrationValidator = new OperationalScopeRegistrationValidator(
                Objects.requireNonNull(targetResolver, "Target resolver must not be null.")
        );
        this.registryRepository = Objects.requireNonNull(
                registryRepository,
                "Operational scope registry repository must not be null."
        );
    }

    @Override
    public OperationalScope registerOperationalScope(RegisterOperationalScopeCommand command) {
        Objects.requireNonNull(command, "Register operational scope command must not be null.");

        ValidatedTarget validated = registrationValidator.validate(
                command.type(),
                command.targetId()
        );

        return registryRepository.register(
                validated.type(),
                validated.targetId()
        );
    }
}
