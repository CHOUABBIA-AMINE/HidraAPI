/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityUserApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Application service for identity user creation.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import org.springframework.stereotype.Service;

import dz.sh.hidra.modules.identity.application.command.CreateUserCommand;
import dz.sh.hidra.modules.identity.application.dto.UserSummaryDto;
import dz.sh.hidra.modules.identity.application.mapper.IdentityApplicationMapper;
import dz.sh.hidra.modules.identity.application.port.in.CreateUserUseCase;
import dz.sh.hidra.modules.identity.application.port.out.UserRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.value.IdentityId;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.UserType;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for identity user creation.
 */
@Service
public final class IdentityUserApplicationService implements CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public IdentityUserApplicationService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = Objects.requireNonNull(userRepositoryPort, "User repository port must not be null.");
    }

    @Override
    public UserSummaryDto createUser(CreateUserCommand command) {
        Objects.requireNonNull(command, "Create user command must not be null.");
        Instant now = Instant.now();
        User user = new User(
                IdentityId.newId().value(),
                command.username(),
                command.emailAddress(),
                command.displayName(),
                command.userType() == null ? UserType.HUMAN : command.userType(),
                UserStatus.REGISTERED,
                command.employeeReferenceId(),
                null,
                0,
                null,
                now,
                null,
                null,
                null,
                now
        );
        return IdentityApplicationMapper.toSummary(userRepositoryPort.save(user));
    }
}
