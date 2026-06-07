/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.configuration
 *
 * @Description : Spring configuration wiring identity module beans.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.configuration;

import dz.sh.hidra.modules.identity.application.port.in.ActivateUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.AssignRoleToUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.CreateRoleUseCase;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import dz.sh.hidra.modules.identity.application.port.in.GetUserPermissionsUseCase;
import dz.sh.hidra.modules.identity.application.port.in.GrantPermissionToRoleUseCase;
import dz.sh.hidra.modules.identity.application.port.in.RegisterUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.RevokeRoleFromUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.SuspendUserUseCase;
import dz.sh.hidra.modules.identity.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.identity.application.port.out.PasswordEncoderPort;
import dz.sh.hidra.modules.identity.application.port.out.PermissionRepository;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepository;
import dz.sh.hidra.modules.identity.application.port.out.UserRepository;
import dz.sh.hidra.modules.identity.application.service.ActivateUserService;
import dz.sh.hidra.modules.identity.application.service.AssignRoleToUserService;
import dz.sh.hidra.modules.identity.application.service.CreateRoleService;
import dz.sh.hidra.modules.identity.application.service.EvaluatePermissionService;
import dz.sh.hidra.modules.identity.application.service.GetUserPermissionsService;
import dz.sh.hidra.modules.identity.application.service.GrantPermissionToRoleService;
import dz.sh.hidra.modules.identity.application.service.RegisterUserService;
import dz.sh.hidra.modules.identity.application.service.RevokeRoleFromUserService;
import dz.sh.hidra.modules.identity.application.service.SuspendUserService;
import dz.sh.hidra.modules.identity.domain.policy.PermissionEvaluationPolicy;
import dz.sh.hidra.modules.identity.domain.policy.RoleAssignmentPolicy;
import dz.sh.hidra.modules.identity.domain.policy.SegregationOfDutiesPolicy;
import dz.sh.hidra.modules.identity.domain.service.PermissionEvaluationDomainService;
import dz.sh.hidra.modules.identity.domain.service.RoleAssignmentDomainService;
import dz.sh.hidra.modules.identity.infrastructure.adapter.NoOpDomainEventPublisherAdapter;
import dz.sh.hidra.modules.identity.infrastructure.adapter.SpringPasswordEncoderAdapter;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;

/**
 * Spring configuration wiring identity module beans.
 *
 * <p>Business role: assembles identity application services, domain services, mappers,
 * and infrastructure fallback adapters for users, roles, permissions, and access policy
 * use cases.</p>
 *
 * <p>Architecture role: identity infrastructure configuration. It wires identity module
 * beans but does not create a Spring Security filter chain, authentication entry point,
 * access-denied handler, current-principal extractor, or platform security plumbing.</p>
 *
 * <p>Validation responsibility: Spring validates dependency availability at startup. The
 * configuration only creates password encoding integration when a platform-provided
 * {@link PasswordEncoder} bean already exists.</p>
 *
 * <p>Usage: discovered by Spring component scanning when the identity module is enabled.</p>
 */
@Configuration(proxyBeanMethods = false)
public class IdentityConfiguration {

    @Bean(name = "identityClock")
    @ConditionalOnMissingBean(name = "identityClock")
    Clock identityClock() {
        return Clock.systemUTC();
    }

    @Bean
    @ConditionalOnMissingBean
    IdentityPersistenceMapper identityPersistenceMapper() {
        return new IdentityPersistenceMapper();
    }

    @Bean
    @ConditionalOnMissingBean
    RoleAssignmentPolicy roleAssignmentPolicy() {
        return new RoleAssignmentPolicy();
    }

    @Bean
    @ConditionalOnMissingBean
    SegregationOfDutiesPolicy segregationOfDutiesPolicy() {
        return new SegregationOfDutiesPolicy();
    }

    @Bean
    @ConditionalOnMissingBean
    PermissionEvaluationPolicy permissionEvaluationPolicy() {
        return new PermissionEvaluationPolicy();
    }

    @Bean
    @ConditionalOnMissingBean
    RoleAssignmentDomainService roleAssignmentDomainService(
            RoleAssignmentPolicy roleAssignmentPolicy,
            SegregationOfDutiesPolicy segregationOfDutiesPolicy
    ) {
        return new RoleAssignmentDomainService(roleAssignmentPolicy, segregationOfDutiesPolicy);
    }

    @Bean
    @ConditionalOnMissingBean
    PermissionEvaluationDomainService permissionEvaluationDomainService(
            PermissionEvaluationPolicy permissionEvaluationPolicy
    ) {
        return new PermissionEvaluationDomainService(permissionEvaluationPolicy);
    }

    @Bean
    @ConditionalOnMissingBean(DomainEventPublisherPort.class)
    DomainEventPublisherPort identityDomainEventPublisherPort() {
        return new NoOpDomainEventPublisherAdapter();
    }

    @Bean
    @ConditionalOnBean(PasswordEncoder.class)
    @ConditionalOnMissingBean(PasswordEncoderPort.class)
    PasswordEncoderPort identityPasswordEncoderPort(PasswordEncoder passwordEncoder) {
        return new SpringPasswordEncoderAdapter(passwordEncoder);
    }

    @Bean
    @ConditionalOnMissingBean(RegisterUserUseCase.class)
    RegisterUserUseCase registerUserUseCase(
            UserRepository userRepository,
            DomainEventPublisherPort domainEventPublisherPort,
            @Qualifier("identityClock") Clock clock
    ) {
        return new RegisterUserService(userRepository, domainEventPublisherPort, clock);
    }

    @Bean
    @ConditionalOnMissingBean(ActivateUserUseCase.class)
    ActivateUserUseCase activateUserUseCase(
            UserRepository userRepository,
            RoleRepository roleRepository,
            DomainEventPublisherPort domainEventPublisherPort,
            @Qualifier("identityClock") Clock clock
    ) {
        return new ActivateUserService(userRepository, roleRepository, domainEventPublisherPort, clock);
    }

    @Bean
    @ConditionalOnMissingBean(SuspendUserUseCase.class)
    SuspendUserUseCase suspendUserUseCase(
            UserRepository userRepository,
            RoleRepository roleRepository,
            DomainEventPublisherPort domainEventPublisherPort,
            @Qualifier("identityClock") Clock clock
    ) {
        return new SuspendUserService(userRepository, roleRepository, domainEventPublisherPort, clock);
    }

    @Bean
    @ConditionalOnMissingBean(CreateRoleUseCase.class)
    CreateRoleUseCase createRoleUseCase(
            RoleRepository roleRepository,
            DomainEventPublisherPort domainEventPublisherPort,
            @Qualifier("identityClock") Clock clock
    ) {
        return new CreateRoleService(roleRepository, domainEventPublisherPort, clock);
    }

    @Bean
    @ConditionalOnMissingBean(AssignRoleToUserUseCase.class)
    AssignRoleToUserUseCase assignRoleToUserUseCase(
            UserRepository userRepository,
            RoleRepository roleRepository,
            DomainEventPublisherPort domainEventPublisherPort,
            RoleAssignmentDomainService roleAssignmentDomainService,
            @Qualifier("identityClock") Clock clock
    ) {
        return new AssignRoleToUserService(
                userRepository,
                roleRepository,
                domainEventPublisherPort,
                roleAssignmentDomainService,
                clock
        );
    }

    @Bean
    @ConditionalOnMissingBean(RevokeRoleFromUserUseCase.class)
    RevokeRoleFromUserUseCase revokeRoleFromUserUseCase(
            UserRepository userRepository,
            RoleRepository roleRepository
    ) {
        return new RevokeRoleFromUserService(userRepository, roleRepository);
    }

    @Bean
    @ConditionalOnMissingBean(GrantPermissionToRoleUseCase.class)
    GrantPermissionToRoleUseCase grantPermissionToRoleUseCase(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            DomainEventPublisherPort domainEventPublisherPort,
            @Qualifier("identityClock") Clock clock
    ) {
        return new GrantPermissionToRoleService(
                roleRepository,
                permissionRepository,
                domainEventPublisherPort,
                clock
        );
    }

    @Bean
    @ConditionalOnMissingBean(EvaluatePermissionUseCase.class)
    EvaluatePermissionUseCase evaluatePermissionUseCase(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PermissionEvaluationDomainService permissionEvaluationDomainService
    ) {
        return new EvaluatePermissionService(userRepository, roleRepository, permissionEvaluationDomainService);
    }

    @Bean
    @ConditionalOnMissingBean(GetUserPermissionsUseCase.class)
    GetUserPermissionsUseCase getUserPermissionsUseCase(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PermissionRepository permissionRepository
    ) {
        return new GetUserPermissionsService(userRepository, roleRepository, permissionRepository);
    }
}
