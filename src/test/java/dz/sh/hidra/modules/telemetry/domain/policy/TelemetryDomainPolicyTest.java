/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryDomainPolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.policy
 *
 * @Description : Domain tests for telemetry policies.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.policy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryIngestionBatch;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPointBinding;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.support.TelemetryDomainTestFixtures;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingState;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceStatus;

/**
 * Domain tests for telemetry policies.
 */
class TelemetryDomainPolicyTest {

    @Test
    void sourcePolicyShouldRequireActiveSourceForDeviceActivation() {
        TelemetrySourcePolicy policy = new TelemetrySourcePolicy();

        assertDoesNotThrow(() -> policy.requireDeviceCanBeActivated(
                TelemetryDomainTestFixtures.activeSource(),
                TelemetryDomainTestFixtures.device(TelemetryDomainTestFixtures.activeSource().id(), TelemetryDeviceStatus.PLANNED)));

        assertThrows(BusinessRuleViolationException.class, () -> policy.requireDeviceCanBeActivated(
                TelemetryDomainTestFixtures.source(TelemetrySourceStatus.INACTIVE),
                TelemetryDomainTestFixtures.device(TelemetryDomainTestFixtures.activeSource().id(), TelemetryDeviceStatus.PLANNED)));
    }

    @Test
    void pointPolicyShouldRequireUnitForNumericPointAndActivePointForReadings() {
        TelemetryPointPolicy policy = new TelemetryPointPolicy();

        assertDoesNotThrow(() -> policy.requireUnitForNumericPoint(TelemetryDomainTestFixtures.activeNumericPoint()));
        assertThrows(BusinessRuleViolationException.class, () -> policy.requireUnitForNumericPoint(
                TelemetryDomainTestFixtures.activeNumericPointWithoutUnit()));

        assertDoesNotThrow(() -> policy.requirePointCanAcceptReadings(TelemetryDomainTestFixtures.activeNumericPoint()));
        assertThrows(BusinessRuleViolationException.class, () -> policy.requirePointCanAcceptReadings(
                TelemetryDomainTestFixtures.point(
                        TelemetryDomainTestFixtures.activeDevice().id(),
                        TelemetryPointStatus.SUSPENDED,
                        "NUMERIC",
                        TelemetryDomainTestFixtures.unit())));
    }

    @Test
    void bindingPolicyShouldPreventDuplicateActiveBindings() {
        TelemetryBindingPolicy policy = new TelemetryBindingPolicy();
        TelemetryPoint point = TelemetryDomainTestFixtures.activeNumericPoint();
        TelemetryPointBinding existing = TelemetryDomainTestFixtures.activeBinding(point.id());
        TelemetryPointBinding candidate = TelemetryPointBinding.create(
                point.id(),
                TelemetryDomainTestFixtures.topologyAssetReference(),
                TelemetryDomainTestFixtures.bindingRole(),
                TelemetryDomainTestFixtures.CREATED_AT);

        assertThrows(BusinessRuleViolationException.class, () -> policy.requireNoDuplicateActiveBinding(
                candidate,
                List.of(existing)));

        assertDoesNotThrow(() -> policy.requireBindingCanBeClosed(existing));
        assertThrows(BusinessRuleViolationException.class, () -> policy.requireBindingCanBeClosed(
                existing.close(TelemetryDomainTestFixtures.UPDATED_AT)));
    }

    @Test
    void readingPolicyShouldGuardReadingStateTransitions() {
        TelemetryReadingPolicy policy = new TelemetryReadingPolicy();
        TelemetryReading received = TelemetryDomainTestFixtures.receivedReading(TelemetryDomainTestFixtures.activeNumericPoint().id());

        assertDoesNotThrow(() -> policy.requireReadingCanBeAccepted(received));
        assertDoesNotThrow(() -> policy.requireReadingCanBeQuarantined(received, "suspicious value"));
        assertThrows(BusinessRuleViolationException.class, () -> policy.requireReadingCanBeRejected(received, " "));
        assertThrows(BusinessRuleViolationException.class, () -> policy.requireReadingCanBeMarkedDuplicate(
                TelemetryDomainTestFixtures.reading(received.pointId(), TelemetryReadingState.ACCEPTED)));
    }

    @Test
    void ingestionPolicyShouldGuardSourceAndBatchLifecycle() {
        TelemetryIngestionPolicy policy = new TelemetryIngestionPolicy();
        TelemetryIngestionBatch processing = TelemetryDomainTestFixtures.batch(TelemetryIngestionBatchStatus.PROCESSING);

        assertDoesNotThrow(() -> policy.requireSourceCanIngest(TelemetryDomainTestFixtures.activeSource()));
        assertThrows(BusinessRuleViolationException.class, () -> policy.requireSourceCanIngest(
                TelemetryDomainTestFixtures.source(TelemetrySourceStatus.PLANNED)));

        assertDoesNotThrow(() -> policy.requireBatchCanComplete(processing, 10, 8, 1, 1, 0));
        assertThrows(BusinessRuleViolationException.class, () -> policy.requireBatchCanComplete(processing, 10, 8, 2, 1, 1));
    }

    @Test
    void catalogPolicyShouldRequireExpectedActiveTranslatedCatalog() {
        TelemetryCatalogPolicy policy = new TelemetryCatalogPolicy();

        assertDoesNotThrow(() -> policy.requireMandatoryTranslations(
                TelemetryDomainTestFixtures.catalogWithMandatoryTranslations(true)));

        assertThrows(BusinessRuleViolationException.class, () -> policy.requireMandatoryTranslations(
                TelemetryDomainTestFixtures.catalogWithOnlyFrenchTranslation(true)));

        assertThrows(BusinessRuleViolationException.class, () -> policy.requireUsableCatalog(
                TelemetryDomainTestFixtures.catalogWithMandatoryTranslations(false),
                "POINT_TYPE"));
    }
}
