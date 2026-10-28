/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitLifecycleApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
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
        Objects.requireNonNull(command);executor.requireNoProtectedContext();var prepared=coordinator.prepare(command);
        var admission=new AtomicReference<ProtectedInstalledUnitAdmissionCoordinator.Admission>();
        return executor.executePublication(()->{
            var a=coordinator.acquire(prepared,command);admission.set(a);return coordinator.perform(a);
        },()->{storage.flush();coordinator.prepareFinal(admission.get());},()->{
            if(!coordinator.finalGuard(admission.get()))throw new IllegalStateException("Installed-unit operation unavailable");
        });
    }
}
