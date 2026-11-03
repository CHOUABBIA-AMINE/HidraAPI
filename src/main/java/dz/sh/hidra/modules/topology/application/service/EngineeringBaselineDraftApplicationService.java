/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineDraftApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Returns baseline draft results only after the physical protected commit.
 *
 */
package dz.sh.hidra.modules.topology.application.service;


import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineDraftUseCase;
import dz.sh.hidra.modules.topology.application.model.EngineeringBaselineDraftFailure;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

@Service
public final class EngineeringBaselineDraftApplicationService implements EngineeringBaselineDraftUseCase {
    private final ProtectedEngineeringBaselineAdmissionCoordinator coordinator;
    private final ProtectedTransactionExecutor executor;
    public EngineeringBaselineDraftApplicationService(ProtectedEngineeringBaselineAdmissionCoordinator coordinator,ProtectedTransactionExecutor executor){
        this.coordinator=Objects.requireNonNull(coordinator);this.executor=Objects.requireNonNull(executor);
    }
    @Override public Result execute(Command command){
        var prepared=coordinator.prepare(command);
        var current=new AtomicReference<ProtectedEngineeringBaselineAdmissionCoordinator.Admission>();
        try{
            return executor.executePublication(()->{
                var a=coordinator.acquire(prepared,command);current.set(a);return coordinator.perform(a);
            },()->coordinator.flushAndPrepare(current.get()),()->coordinator.finalGuard(current.get()));
        }catch(ProtectedTransactionExecutor.ExecutionFailure failure){
            if(failure.outcome()==ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT){
                if(failure.getCause() instanceof AccessDeniedException)throw new AccessDeniedException("Baseline draft denied");
                if(failure.getCause() instanceof EngineeringBaselineDraftFailure expected)throw expected;
                if(failure.getCause() instanceof IllegalArgumentException invalid)throw invalid;
            }
            // An indeterminate commit acknowledgement must never be retried automatically.
            throw failure;
        }
    }
}
