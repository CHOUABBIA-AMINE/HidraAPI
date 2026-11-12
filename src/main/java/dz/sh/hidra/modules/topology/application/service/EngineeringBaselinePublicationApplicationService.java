/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselinePublicationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Implements the exact engineering baseline publication contract.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.model.EngineeringBaselineDraftFailure;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselinePublicationUseCase;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public final class EngineeringBaselinePublicationApplicationService implements EngineeringBaselinePublicationUseCase {
    private final ProtectedEngineeringBaselinePublicationCoordinator coordinator;
    private final ProtectedTransactionExecutor executor;

    public EngineeringBaselinePublicationApplicationService(ProtectedEngineeringBaselinePublicationCoordinator coordinator,
                                                            ProtectedTransactionExecutor executor) {
        this.coordinator = coordinator;
        this.executor = executor;
    }

    @Override
    public Result execute(Command command) {
        var prepared = coordinator.prepare();
        var admission = new AtomicReference<ProtectedEngineeringBaselinePublicationCoordinator.Admission>();
        try {
            return executor.executePublication(() -> {
                var ticket = coordinator.acquire(command, prepared);
                admission.set(ticket);
                return coordinator.perform(ticket);
            }, () -> coordinator.prepareFinal(admission.get()), () -> coordinator.guard(admission.get()));
        } catch (ProtectedTransactionExecutor.ExecutionFailure failure) {
            if (failure.outcome() == ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT) {
                if (failure.getCause() instanceof AccessDeniedException denied) throw denied;
                if (failure.getCause() instanceof EngineeringBaselineDraftFailure business) throw business;
            }
            throw failure;
        }
    }
}
