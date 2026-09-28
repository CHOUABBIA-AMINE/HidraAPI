/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthoritativeOperationalScopeTargetResolverAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.adapter
 *
 * @Description : Verifies authoritative operational-scope owner routing and projection.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.adapter;

import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import dz.sh.hidra.modules.topology.application.contract.organization.TopologyOperationalScopeTargetContract;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthoritativeOperationalScopeTargetResolverAdapterTest {

    private static final Instant NOW = Instant.parse("2026-09-27T10:00:00Z");

    @Test
    void supportsOnlyCurrentlyGovernedEntityBackedOwnerTypes() {
        var resolver = resolver(unit("unit-1", OrganizationUnitStatus.ACTIVE), topologyPort());

        assertTrue(resolver.supports(OperationalScopeType.ORGANIZATION_UNIT));
        assertTrue(resolver.supports(OperationalScopeType.PIPELINE_SYSTEM));
        assertTrue(resolver.supports(OperationalScopeType.PIPELINE));
        assertTrue(resolver.supports(OperationalScopeType.FACILITY));
        assertTrue(resolver.supports(OperationalScopeType.EQUIPMENT));
        assertFalse(resolver.supports(OperationalScopeType.GLOBAL));
        assertFalse(resolver.supports(OperationalScopeType.CUSTOM));
    }

    @Test
    void resolvesCurrentOrganizationUnitDisplayAndLifecycle() {
        var active = resolver(unit("unit-active", OrganizationUnitStatus.ACTIVE), topologyPort())
                .resolve(OperationalScopeType.ORGANIZATION_UNIT, " unit-active ")
                .orElseThrow();

        assertEquals("OU-1", active.code());
        assertEquals("Unit EN", active.name());
        assertTrue(active.assignable());

        var inactive = resolver(unit("unit-inactive", OrganizationUnitStatus.INACTIVE), topologyPort())
                .resolve(OperationalScopeType.ORGANIZATION_UNIT, "unit-inactive")
                .orElseThrow();

        assertFalse(inactive.assignable());
    }

    @Test
    void resolvesTopologyTargetsThroughExportedContractWithoutChangingIdentity() {
        var result = resolver(unit("unit-1", OrganizationUnitStatus.ACTIVE), topologyPort())
                .resolve(OperationalScopeType.PIPELINE, " pipeline-1 ")
                .orElseThrow();

        assertEquals(OperationalScopeType.PIPELINE, result.type());
        assertEquals("pipeline-1", result.targetId());
        assertEquals("PL-1", result.code());
        assertEquals("Pipeline current", result.name());
        assertTrue(result.assignable());
    }

    @Test
    void missingAndTargetlessTypesRemainUnresolved() {
        var resolver = resolver(unit("unit-1", OrganizationUnitStatus.ACTIVE), topologyPort());

        assertTrue(resolver.resolve(OperationalScopeType.PIPELINE, "missing").isEmpty());
        assertTrue(resolver.resolve(OperationalScopeType.GLOBAL, "anything").isEmpty());
        assertTrue(resolver.resolve(OperationalScopeType.CUSTOM, "anything").isEmpty());
    }

    @Test
    void propagatesOwnerFailureInsteadOfMaskingItAsMissing() {
        TopologyOperationalScopeTargetContract unavailable = new TopologyOperationalScopeTargetContract() {
            @Override
            public Optional<TargetView> resolvePipelineSystem(String targetId) {
                throw new IllegalStateException("Topology owner unavailable");
            }

            @Override
            public Optional<TargetView> resolvePipeline(String targetId) {
                throw new IllegalStateException("Topology owner unavailable");
            }

            @Override
            public Optional<TargetView> resolveFacility(String targetId) {
                throw new IllegalStateException("Topology owner unavailable");
            }

            @Override
            public Optional<TargetView> resolveEquipment(String targetId) {
                throw new IllegalStateException("Topology owner unavailable");
            }
        };

        var resolver = resolver(unit("unit-1", OrganizationUnitStatus.ACTIVE), unavailable);

        assertThrows(
                IllegalStateException.class,
                () -> resolver.resolve(OperationalScopeType.PIPELINE, "pipeline-1")
        );
    }

    private static AuthoritativeOperationalScopeTargetResolverAdapter resolver(
            OrganizationUnit unit,
            TopologyOperationalScopeTargetContract topology
    ) {
        return new AuthoritativeOperationalScopeTargetResolverAdapter(
                organizationUnitRepository(unit),
                topology
        );
    }

    private static OrganizationUnit unit(String id, OrganizationUnitStatus status) {
        return new OrganizationUnit(
                id,
                "OU-1",
                "وحدة",
                "Unité FR",
                "Unit EN",
                "unit-type-1",
                null,
                status,
                NOW,
                null,
                NOW,
                NOW
        );
    }

    private static OrganizationUnitRepositoryPort organizationUnitRepository(OrganizationUnit model) {
        return new OrganizationUnitRepositoryPort() {
            @Override
            public OrganizationUnit save(OrganizationUnit value) {
                return value;
            }

            @Override
            public Optional<OrganizationUnit> findById(String id) {
                return model.id().equals(id) ? Optional.of(model) : Optional.empty();
            }
        };
    }

    private static TopologyOperationalScopeTargetContract topologyPort() {
        return new TopologyOperationalScopeTargetContract() {
            @Override
            public Optional<TargetView> resolvePipelineSystem(String targetId) {
                return "system-1".equals(targetId)
                        ? Optional.of(new TargetView("system-1", "SYS-1", "System current", true))
                        : Optional.empty();
            }

            @Override
            public Optional<TargetView> resolvePipeline(String targetId) {
                return "pipeline-1".equals(targetId)
                        ? Optional.of(new TargetView("pipeline-1", "PL-1", "Pipeline current", true))
                        : Optional.empty();
            }

            @Override
            public Optional<TargetView> resolveFacility(String targetId) {
                return "facility-1".equals(targetId)
                        ? Optional.of(new TargetView("facility-1", "FAC-1", "Facility current", false))
                        : Optional.empty();
            }

            @Override
            public Optional<TargetView> resolveEquipment(String targetId) {
                return "equipment-1".equals(targetId)
                        ? Optional.of(new TargetView("equipment-1", "EQ-1", "Equipment current", true))
                        : Optional.empty();
            }
        };
    }
}
