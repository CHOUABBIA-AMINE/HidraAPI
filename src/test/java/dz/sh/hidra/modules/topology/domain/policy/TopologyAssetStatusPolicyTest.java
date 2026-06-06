/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyAssetStatusPolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.policy
 *
 * @Description : Unit tests for topology asset lifecycle status policy.
 *
 */
package dz.sh.hidra.modules.topology.domain.policy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.topology.domain.exception.TopologyValidationException;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Unit tests for topology lifecycle status policy.
 */
class TopologyAssetStatusPolicyTest {

    private final TopologyAssetStatusPolicy policy = new TopologyAssetStatusPolicy();

    @Test
    void shouldAllowExpectedLifecycleTransitions() {
        assertDoesNotThrow(() -> policy.validateTransition(TopologyStatus.PLANNED, TopologyStatus.ACTIVE));
        assertDoesNotThrow(() -> policy.validateTransition(TopologyStatus.ACTIVE, TopologyStatus.UNDER_MAINTENANCE));
        assertDoesNotThrow(() -> policy.validateTransition(TopologyStatus.RETIRED, TopologyStatus.DECOMMISSIONED));
    }

    @Test
    void shouldRejectTransitionFromDecommissionedToActive() {
        assertThrows(TopologyValidationException.class, () -> policy.validateTransition(
                TopologyStatus.DECOMMISSIONED,
                TopologyStatus.ACTIVE));
    }

    @Test
    void shouldRequireActiveStatusForOperationalUse() {
        assertDoesNotThrow(() -> policy.requireOperationallyUsable(TopologyStatus.ACTIVE));

        assertThrows(TopologyValidationException.class, () -> policy.requireOperationallyUsable(TopologyStatus.PLANNED));
        assertThrows(TopologyValidationException.class, () -> policy.requireOperationallyUsable(TopologyStatus.UNDER_MAINTENANCE));
    }

    @Test
    void shouldRejectChildAttachmentToTerminalAssets() {
        assertDoesNotThrow(() -> policy.requireCanAttachTopologyChild(TopologyStatus.PLANNED));
        assertDoesNotThrow(() -> policy.requireCanAttachTopologyChild(TopologyStatus.ACTIVE));

        assertThrows(TopologyValidationException.class, () -> policy.requireCanAttachTopologyChild(TopologyStatus.RETIRED));
        assertThrows(TopologyValidationException.class, () -> policy.requireCanAttachTopologyChild(TopologyStatus.DECOMMISSIONED));
    }
}
