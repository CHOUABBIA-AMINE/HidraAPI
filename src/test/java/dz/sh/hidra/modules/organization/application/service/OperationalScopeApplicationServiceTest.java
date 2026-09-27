/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Tests validation-before-persistence for operational-scope registration.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.command.RegisterOperationalScopeCommand;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeRegistryRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort;
import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationalScopeApplicationServiceTest {

    @Test
    void validatesOwnerThenRegistersCanonicalScope() {
        AtomicInteger registerCalls = new AtomicInteger();

        OperationalScopeTargetResolverPort resolver = new OperationalScopeTargetResolverPort() {
            @Override
            public boolean supports(OperationalScopeType type) {
                return type == OperationalScopeType.PIPELINE;
            }

            @Override
            public Optional<ResolvedTarget> resolve(OperationalScopeType type, String targetId) {
                return Optional.of(new ResolvedTarget(
                        OperationalScopeType.PIPELINE,
                        targetId,
                        "PL-009",
                        "Pipeline 009",
                        true
                ));
            }
        };

        OperationalScopeRegistryRepositoryPort repository =
                repository(registerCalls, new OperationalScope(42L, OperationalScopeType.PIPELINE, "pipeline-009"));

        OperationalScopeApplicationService service =
                new OperationalScopeApplicationService(resolver, repository);

        OperationalScope result = service.registerOperationalScope(
                new RegisterOperationalScopeCommand(OperationalScopeType.PIPELINE, " pipeline-009 ")
        );

        assertEquals(42L, result.id());
        assertEquals("pipeline-009", result.targetId());
        assertEquals(1, registerCalls.get());
    }

    @Test
    void rejectedTargetNeverReachesPersistence() {
        AtomicInteger registerCalls = new AtomicInteger();

        OperationalScopeTargetResolverPort resolver = new OperationalScopeTargetResolverPort() {
            @Override
            public boolean supports(OperationalScopeType type) {
                return type == OperationalScopeType.FACILITY;
            }

            @Override
            public Optional<ResolvedTarget> resolve(OperationalScopeType type, String targetId) {
                return Optional.of(new ResolvedTarget(
                        type,
                        targetId,
                        "FAC-001",
                        "Retired facility",
                        false
                ));
            }
        };

        OperationalScopeRegistryRepositoryPort repository =
                repository(registerCalls, new OperationalScope(10L, OperationalScopeType.FACILITY, "facility-001"));

        OperationalScopeApplicationService service =
                new OperationalScopeApplicationService(resolver, repository);

        assertThrows(
                IllegalStateException.class,
                () -> service.registerOperationalScope(
                        new RegisterOperationalScopeCommand(OperationalScopeType.FACILITY, "facility-001")
                )
        );

        assertEquals(0, registerCalls.get());
    }

    @Test
    void registersGlobalWithoutCallingExternalOwnerResolver() {
        AtomicInteger resolverCalls = new AtomicInteger();
        AtomicInteger registerCalls = new AtomicInteger();

        OperationalScopeTargetResolverPort resolver = new OperationalScopeTargetResolverPort() {
            @Override
            public boolean supports(OperationalScopeType type) {
                resolverCalls.incrementAndGet();
                return false;
            }

            @Override
            public Optional<ResolvedTarget> resolve(OperationalScopeType type, String targetId) {
                resolverCalls.incrementAndGet();
                return Optional.empty();
            }
        };

        OperationalScopeRegistryRepositoryPort repository =
                repository(registerCalls, new OperationalScope(1L, OperationalScopeType.GLOBAL, null));

        OperationalScopeApplicationService service =
                new OperationalScopeApplicationService(resolver, repository);

        OperationalScope result = service.registerOperationalScope(
                new RegisterOperationalScopeCommand(OperationalScopeType.GLOBAL, null)
        );

        assertEquals(OperationalScopeType.GLOBAL, result.type());
        assertEquals(0, resolverCalls.get());
        assertEquals(1, registerCalls.get());
    }

    private static OperationalScopeRegistryRepositoryPort repository(
            AtomicInteger registerCalls,
            OperationalScope registered
    ) {
        return new OperationalScopeRegistryRepositoryPort() {
            @Override
            public OperationalScope register(OperationalScopeType type, String targetId) {
                registerCalls.incrementAndGet();
                return registered;
            }

            @Override
            public Optional<OperationalScope> findById(Long scopeId) {
                return Optional.empty();
            }

            @Override
            public Optional<OperationalScope> findByTypeAndTargetId(
                    OperationalScopeType type,
                    String targetId
            ) {
                return Optional.empty();
            }

            @Override
            public Optional<OperationalScope> findGlobal() {
                return Optional.empty();
            }
        };
    }
}
