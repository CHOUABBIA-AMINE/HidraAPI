/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryLifecycleDomainTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Domain tests for telemetry source, device, and point lifecycle models.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.domain.support.TelemetryDomainTestFixtures;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceStatus;

/**
 * Domain tests for telemetry source, device, and point lifecycle models.
 */
class TelemetryLifecycleDomainTest {

    @Test
    void shouldMoveTelemetrySourceThroughLifecycle() {
        TelemetrySource planned = TelemetryDomainTestFixtures.source(TelemetrySourceStatus.PLANNED);

        TelemetrySource active = planned.activate();
        TelemetrySource retired = active.retire();

        assertEquals(TelemetrySourceStatus.ACTIVE, active.status());
        assertTrue(active.active());
        assertEquals(TelemetrySourceStatus.RETIRED, retired.status());
        assertThrows(BusinessRuleViolationException.class, retired::activate);
    }

    @Test
    void shouldMoveTelemetryDeviceThroughLifecycle() {
        TelemetryDevice planned = TelemetryDomainTestFixtures.device(
                TelemetryDomainTestFixtures.activeSource().id(),
                TelemetryDeviceStatus.PLANNED);

        TelemetryDevice active = planned.activate();
        TelemetryDevice retired = active.retire();

        assertEquals(TelemetryDeviceStatus.ACTIVE, active.status());
        assertTrue(active.active());
        assertEquals(TelemetryDeviceStatus.RETIRED, retired.status());
        assertThrows(BusinessRuleViolationException.class, retired::deactivate);
    }

    @Test
    void shouldMoveTelemetryPointThroughLifecycle() {
        TelemetryPoint planned = TelemetryDomainTestFixtures.point(
                TelemetryDomainTestFixtures.activeDevice().id(),
                TelemetryPointStatus.PLANNED,
                "NUMERIC",
                TelemetryDomainTestFixtures.unit());

        TelemetryPoint active = planned.activate();
        TelemetryPoint suspended = active.suspend();
        TelemetryPoint retired = suspended.retire();

        assertEquals(TelemetryPointStatus.ACTIVE, active.status());
        assertTrue(active.active());
        assertEquals(TelemetryPointStatus.SUSPENDED, suspended.status());
        assertEquals(TelemetryPointStatus.RETIRED, retired.status());
        assertThrows(BusinessRuleViolationException.class, retired::activate);
    }
}
