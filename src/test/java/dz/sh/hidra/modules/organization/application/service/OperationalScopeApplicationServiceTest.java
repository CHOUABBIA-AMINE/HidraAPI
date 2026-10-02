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
 * @Description : Tests canonical-reference validation before operational-scope persistence.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.command.RegisterOperationalScopeCommand;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeRegistryRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort;
import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationalScopeApplicationServiceTest {

    @Test
    void validatesOwnerReferenceThenRegistersIndependentRegistryIdentity() {
        AtomicInteger registerCalls = new AtomicInteger();
        AtomicReference<OperationalScopeReference> registeredReference =
                new AtomicReference<>();

        OperationalScopeTargetResolverPort resolver =
                new OperationalScopeTargetResolverPort() {
                    @Override
                    public boolean supports(OperationalScopeType type) {
                        return type == OperationalScopeType.PIPELINE;
                    }

                    @Override
                    public Optional<ResolvedTarget> resolve(
                            OperationalScopeType type,
                            String targetId
                    ) {
                        return Optional.of(new ResolvedTarget(
                                OperationalScopeType.PIPELINE,
                                targetId,
                                "PL-009",
                                "Pipeline 009",
                                true
                        ));
                    }
                };

        OperationalScopeRegistryRepositoryPort repository = repository(
                registerCalls,
                registeredReference,
                new OperationalScope(
                        42L,
                        OperationalScopeType.PIPELINE,
                        "pipeline-009"
                )
        );

        OperationalScopeApplicationService service =
                new OperationalScopeApplicationService(resolver, repository);

        OperationalScopeReference reference = new OperationalScopeReference(
                OperationalScopeType.PIPELINE,
                " pipeline-009 "
        );

        OperationalScope result = service.registerOperationalScope(
                new RegisterOperationalScopeCommand(reference)
        );

        assertEquals(42L, result.id());
        assertEquals("pipeline-009", result.targetId());
        assertEquals(
                new OperationalScopeReference(
                        OperationalScopeType.PIPELINE,
                        "pipeline-009"
                ),
                registeredReference.get()
        );
        assertEquals(1, registerCalls.get());
    }

    @Test
    void rejectedTargetNeverReachesPersistence() {
        AtomicInteger registerCalls = new AtomicInteger();
        AtomicReference<OperationalScopeReference> registeredReference =
                new AtomicReference<>();

        OperationalScopeTargetResolverPort resolver =
                new OperationalScopeTargetResolverPort() {
                    @Override
                    public boolean supports(OperationalScopeType type) {
                        return type == OperationalScopeType.FACILITY;
                    }

                    @Override
                    public Optional<ResolvedTarget> resolve(
                            OperationalScopeType type,
                            String targetId
                    ) {
                        return Optional.of(new ResolvedTarget(
                                type,
                                targetId,
                                "FAC-001",
                                "Retired facility",
                                false
                        ));
                    }
                };

        OperationalScopeRegistryRepositoryPort repository = repository(
                registerCalls,
                registeredReference,
                new OperationalScope(
                        10L,
                        OperationalScopeType.FACILITY,
                        "facility-001"
                )
        );

        OperationalScopeApplicationService service =
                new OperationalScopeApplicationService(resolver, repository);

        assertThrows(
                IllegalStateException.class,
                () -> service.registerOperationalScope(
                        new RegisterOperationalScopeCommand(
                                new OperationalScopeReference(
                                        OperationalScopeType.FACILITY,
                                        "facility-001"
                                )
                        )
                )
        );

        assertEquals(0, registerCalls.get());
        assertEquals(null, registeredReference.get());
    }

    @Test
    void registersGlobalReferenceWithoutCallingExternalOwnerResolver() {
        AtomicInteger resolverCalls = new AtomicInteger();
        AtomicInteger registerCalls = new AtomicInteger();
        AtomicReference<OperationalScopeReference> registeredReference =
                new AtomicReference<>();

        OperationalScopeTargetResolverPort resolver =
                new OperationalScopeTargetResolverPort() {
                    @Override
                    public boolean supports(OperationalScopeType type) {
                        resolverCalls.incrementAndGet();
                        return false;
                    }

                    @Override
                    public Optional<ResolvedTarget> resolve(
                            OperationalScopeType type,
                            String targetId
                    ) {
                        resolverCalls.incrementAndGet();
                        return Optional.empty();
                    }
                };

        OperationalScopeRegistryRepositoryPort repository = repository(
                registerCalls,
                registeredReference,
                new OperationalScope(1L, OperationalScopeType.GLOBAL, null)
        );

        OperationalScopeApplicationService service =
                new OperationalScopeApplicationService(resolver, repository);

        OperationalScopeReference global =
                new OperationalScopeReference(OperationalScopeType.GLOBAL, null);

        OperationalScope result = service.registerOperationalScope(
                new RegisterOperationalScopeCommand(global)
        );

        assertEquals(OperationalScopeType.GLOBAL, result.type());
        assertEquals(global, registeredReference.get());
        assertEquals(0, resolverCalls.get());
        assertEquals(1, registerCalls.get());
    }

    private static OperationalScopeRegistryRepositoryPort repository(
            AtomicInteger registerCalls,
            AtomicReference<OperationalScopeReference> registeredReference,
            OperationalScope registered
    ) {
        return new OperationalScopeRegistryRepositoryPort() {
            @Override
            public OperationalScope register(OperationalScopeReference reference) {
                registerCalls.incrementAndGet();
                registeredReference.set(reference);
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
