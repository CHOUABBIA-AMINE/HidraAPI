/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitLifecycleApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Implements the protected installed-unit lifecycle within its owning boundary.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase;
import dz.sh.hidra.modules.topology.application.port.out.ProtectedInstalledUnitRepositoryPort;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.Objects;
import dz.sh.hidra.modules.topology.application.model.InstalledUnitLifecycleFailure;
import dz.sh.hidra.modules.topology.application.model.InstalledUnitLifecycleFailure.Kind;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Service;

/** No user callbacks or ambient transaction; results leave only after observed physical commit. */
@Service
public class InstalledUnitLifecycleApplicationService implements InstalledUnitLifecycleUseCase {
    private final ProtectedInstalledUnitAdmissionCoordinator coordinator;
    private final ProtectedInstalledUnitRepositoryPort storage;
    private final ProtectedTransactionExecutor executor;
    public InstalledUnitLifecycleApplicationService(ProtectedInstalledUnitAdmissionCoordinator coordinator,
            ProtectedInstalledUnitRepositoryPort storage,ProtectedTransactionExecutor executor) {
        this.coordinator=Objects.requireNonNull(coordinator);this.storage=Objects.requireNonNull(storage);this.executor=Objects.requireNonNull(executor);
    }
    @Override public Result execute(Command command) {
        try {
        Objects.requireNonNull(command);executor.requireNoProtectedContext();var prepared=coordinator.prepare(command);
        var admission=new AtomicReference<ProtectedInstalledUnitAdmissionCoordinator.Admission>();
        return executor.executePublication(()->{
            var a=coordinator.acquire(prepared,command);admission.set(a);return coordinator.perform(a);
        },()->{storage.flush();coordinator.prepareFinal(admission.get());},()->{
            if(!coordinator.finalGuard(admission.get()))throw new IllegalStateException("Installed-unit operation unavailable");
        });
        }catch(RuntimeException failure) {
            if(failure instanceof ProtectedTransactionExecutor.ExecutionFailure execution
                && execution.outcome()==ProtectedTransactionExecutor.Outcome.INDETERMINATE)
                throw new InstalledUnitLifecycleFailure(Kind.INDETERMINATE,failure);
            for(Throwable cause=failure;cause!=null;cause=cause.getCause()) {
                if(cause instanceof InstalledUnitLifecycleFailure typed)throw new InstalledUnitLifecycleFailure(typed.kind(),failure);
                if(cause instanceof org.springframework.security.core.AuthenticationException)
                    throw new InstalledUnitLifecycleFailure(Kind.UNAUTHENTICATED,failure);
                if(cause instanceof org.springframework.security.access.AccessDeniedException) {
                    var actual=org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
                    throw new InstalledUnitLifecycleFailure(actual==null || !actual.isAuthenticated()
                        || actual instanceof org.springframework.security.authentication.AnonymousAuthenticationToken
                        ? Kind.UNAUTHENTICATED:Kind.DENIED,failure);
                }
            }
            throw new InstalledUnitLifecycleFailure(failure instanceof IllegalArgumentException || failure instanceof NullPointerException
                ? Kind.INVALID:Kind.UNAVAILABLE,failure);
        }
    }
}
