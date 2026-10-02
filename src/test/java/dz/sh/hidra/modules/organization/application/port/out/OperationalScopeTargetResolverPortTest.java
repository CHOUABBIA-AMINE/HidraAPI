/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeTargetResolverPortTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-24
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Tests the read-only owner-resolution boundary value contract.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OperationalScopeTargetResolverPortTest {

    @Test
    void preservesOwnerIdAndCurrentDisplayWithoutEquatingItWithRegistryId() {
        OperationalScopeTargetResolverPort.ResolvedTarget result =
                new OperationalScopeTargetResolverPort.ResolvedTarget(
                        OperationalScopeType.PIPELINE, " pipeline-009 ", "PL-009", "Pipeline 009", true);
        assertEquals("pipeline-009", result.targetId());
        assertEquals("PL-009", result.code());
        assertEquals(OperationalScopeType.PIPELINE, result.type());
        assertTrue(result.assignable());
    }

    @Test
    void rejectsTargetlessAndUnregisteredTypesAndMissingOwnerId() {
        assertThrows(IllegalArgumentException.class, () -> new OperationalScopeTargetResolverPort.ResolvedTarget(
                OperationalScopeType.GLOBAL, "fake-global", null, null, true));
        assertThrows(IllegalArgumentException.class, () -> new OperationalScopeTargetResolverPort.ResolvedTarget(
                OperationalScopeType.CUSTOM, "unregistered", null, null, true));
        assertThrows(IllegalArgumentException.class, () -> new OperationalScopeTargetResolverPort.ResolvedTarget(
                OperationalScopeType.FACILITY, "  ", null, null, true));
        assertThrows(NullPointerException.class, () -> new OperationalScopeTargetResolverPort.ResolvedTarget(
                null, "facility-001", null, null, true));
    }

    @Test
    void missingTargetAndRetiredTargetDoNotBecomeAssignable() {
        OperationalScopeTargetResolverPort resolver = new OperationalScopeTargetResolverPort() {
            @Override
            public boolean supports(OperationalScopeType type) {
                return type == OperationalScopeType.PIPELINE;
            }

            @Override
            public Optional<ResolvedTarget> resolve(OperationalScopeType type, String targetId) {
                if (!supports(type) || !"pipeline-009".equals(targetId)) {
                    return Optional.empty();
                }
                return Optional.of(new ResolvedTarget(type, targetId, "PL-009", "Retired pipeline", false));
            }
        };
        assertTrue(resolver.supports(OperationalScopeType.PIPELINE));
        assertFalse(resolver.supports(OperationalScopeType.EQUIPMENT));
        assertTrue(resolver.resolve(OperationalScopeType.PIPELINE, "missing").isEmpty());
        assertFalse(resolver.resolve(OperationalScopeType.PIPELINE, "pipeline-009").orElseThrow().assignable());
    }
}
