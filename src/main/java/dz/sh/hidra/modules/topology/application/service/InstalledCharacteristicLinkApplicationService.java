/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Coordinates protected draft-link operations without exposing an unverified runtime writer.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase;
import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase.Command;
import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase.Result;
import dz.sh.hidra.modules.topology.application.port.out.InstalledCharacteristicLinkRepositoryPort;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Deliberately not a Spring bean until native cross-owner authorization, final
 * fences and trusted SQL are independently implemented and verified. A caller
 * cannot obtain a result until the executor reports committed publication.
 * Never retry a potentially indeterminate physical commit automatically.
 */
public final class InstalledCharacteristicLinkApplicationService
        implements InstalledCharacteristicLinkUseCase {
    private final InstalledCharacteristicLinkRepositoryPort repository;
    private final ProtectedTransactionExecutor executor;

    public InstalledCharacteristicLinkApplicationService(
            InstalledCharacteristicLinkRepositoryPort repository,
            ProtectedTransactionExecutor executor) {
        this.repository = Objects.requireNonNull(repository);
        this.executor = Objects.requireNonNull(executor);
    }

    @Override public Result execute(Command command) {
        Objects.requireNonNull(command);
        executor.requireNoProtectedContext();
        var prepared = repository.prepare(command);
        var admitted = new AtomicReference<InstalledCharacteristicLinkRepositoryPort.Admission>();
        return executor.executePublication(() -> {
            var ticket = Objects.requireNonNull(repository.acquire(prepared, command));
            admitted.set(ticket);
            return repository.perform(ticket, command);
        }, () -> {
            var ticket = Objects.requireNonNull(admitted.get());
            repository.flush();
            repository.prepareFinal(ticket);
        }, () -> {
            var ticket = Objects.requireNonNull(admitted.get());
            if (!repository.finalGuard(ticket))
                throw new IllegalStateException("Protected characteristic link final guard unavailable");
        });
    }
}
