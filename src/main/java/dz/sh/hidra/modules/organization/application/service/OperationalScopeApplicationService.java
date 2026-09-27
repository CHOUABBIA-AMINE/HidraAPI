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
 * @Description : Application service for owner-validated operational-scope registration.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.command.RegisterOperationalScopeCommand;
import dz.sh.hidra.modules.organization.application.port.in.RegisterOperationalScopeUseCase;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeRegistryRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort;
import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeReference;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Validates an owner-native scope reference and registers its independent registry identity.
 *
 * <p>Business role: turns a governed owner reference into the canonical
 * {@link OperationalScope} identity used by responsibility assignments.</p>
 *
 * <p>Architecture role: orchestrates owner validation and persistence while carrying
 * {@link OperationalScopeReference} intact across the registration boundary.</p>
 *
 * <p>Validation: owner existence/assignability is delegated to
 * {@link OperationalScopeRegistrationValidator}; registry uniqueness remains a
 * persistence concern.</p>
 *
 * <p>Usage: callers provide only the owner reference. The returned positive
 * {@code OperationalScope.id} is the identity responsibilities persist; the
 * owner-native target ID is not a substitute for that registry ID.</p>
 */
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

        OperationalScopeReference validated =
                registrationValidator.validate(command.reference());

        return registryRepository.register(validated);
    }
}
