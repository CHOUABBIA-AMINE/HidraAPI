/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTelemetryTargetLookupAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.telemetry
 *
 * @Description : Workflow target lookup adapter for telemetry readings.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.telemetry;

import java.util.Objects;

import org.springframework.stereotype.Component;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryReadingDto;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryReadingByIdQuery;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingId;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTargetLookupPort;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;

/**
 * Workflow target lookup adapter for telemetry readings.
 *
 * <p>Business role:
 * Verifies that a workflow target reference points to an existing telemetry reading and enriches the
 * workflow target snapshot from the telemetry application contract.
 *
 * <p>Architecture role:
 * This adapter is the only WF-016 bridge from workflow infrastructure to telemetry. It depends on
 * telemetry application contracts, not telemetry REST, persistence, database repositories, entities,
 * mappers, controllers, or infrastructure implementation classes.
 *
 * <p>V1 scope:
 * The first workflow target is <code>TELEMETRY_READING</code> only. Workflow owns the decision
 * process. Telemetry remains the owner of reading state and value.
 */
@Component
public class WorkflowTelemetryTargetLookupAdapter implements WorkflowTargetLookupPort {

    private final GetTelemetryReadingUseCase getTelemetryReadingUseCase;

    public WorkflowTelemetryTargetLookupAdapter(GetTelemetryReadingUseCase getTelemetryReadingUseCase) {
        this.getTelemetryReadingUseCase = Objects.requireNonNull(
                getTelemetryReadingUseCase,
                "GetTelemetryReadingUseCase must not be null.");
    }

    @Override
    public boolean exists(WorkflowTargetReference target) {
        if (!isSupportedTelemetryReadingTarget(target)) {
            return false;
        }

        try {
            readTelemetryReading(target);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean canStartWorkflow(WorkflowTargetReference target) {
        if (!isSupportedTelemetryReadingTarget(target)) {
            return false;
        }

        TelemetryReadingDto reading = readTelemetryReading(target);
        return reading.id() != null && !reading.id().isBlank();
    }

    @Override
    public WorkflowTargetReference resolve(WorkflowTargetReference target) {
        if (!isSupportedTelemetryReadingTarget(target)) {
            throw new BusinessRuleViolationException("Workflow v1 supports TELEMETRY_READING targets only.");
        }

        TelemetryReadingDto reading = readTelemetryReading(target);

        return WorkflowTargetReference.of(
                target.targetModule(),
                target.targetType(),
                reading.id(),
                reading.id(),
                labelFor(reading));
    }

    private boolean isSupportedTelemetryReadingTarget(WorkflowTargetReference target) {
        return target != null && target.isTelemetryReading();
    }

    private TelemetryReadingDto readTelemetryReading(WorkflowTargetReference target) {
        return getTelemetryReadingUseCase.getTelemetryReading(
                new GetTelemetryReadingByIdQuery(TelemetryReadingId.of(target.targetId())));
    }

    private String labelFor(TelemetryReadingDto reading) {
        String pointId = reading.pointId() == null ? "unknown-point" : reading.pointId();
        String state = reading.state() == null ? "unknown-state" : reading.state();

        return "Telemetry reading " + reading.id() + " / point " + pointId + " / state " + state;
    }
}
