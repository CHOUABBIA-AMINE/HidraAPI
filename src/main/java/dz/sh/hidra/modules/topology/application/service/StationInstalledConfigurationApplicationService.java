/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInstalledConfigurationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Coordinates one protected station configuration transaction with physical commit proof.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase;
import dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.*;
import dz.sh.hidra.modules.topology.application.model.StationInstalledConfigurationFailure;
import dz.sh.hidra.modules.topology.application.model.StationInstalledConfigurationFailure.Kind;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Service;

/** Internal use case, no user callbacks, no HTTP exposure, no retry of indeterminate commits. */
@Service
public class StationInstalledConfigurationApplicationService implements StationInstalledConfigurationUseCase {
    private final ProtectedStationConfigurationAdmissionCoordinator coordinator;
    private final ProtectedTransactionExecutor executor;
    public StationInstalledConfigurationApplicationService(ProtectedStationConfigurationAdmissionCoordinator coordinator,
            ProtectedTransactionExecutor executor) {
        this.coordinator=Objects.requireNonNull(coordinator);this.executor=Objects.requireNonNull(executor);
    }
    @Override public Result execute(Command command){
        try{
            Objects.requireNonNull(command);
            executor.requireNoProtectedContext();
            var prepared=coordinator.prepare(command);
            var ticket=new AtomicReference<ProtectedStationConfigurationAdmissionCoordinator.Admission>();
            return executor.executePublication(()->{
                var a=coordinator.acquire(prepared,command);
                ticket.set(a);
                return coordinator.perform(a);
            },()->{
                coordinator.flush();
                coordinator.prepareFinal(ticket.get());
            },()->{
                if(!coordinator.finalGuard(ticket.get()))
                    throw new IllegalStateException("Protected station configuration guard unavailable");
            });
        }catch(RuntimeException failure){
            if(failure instanceof StationInstalledConfigurationFailure typed)
                throw new StationInstalledConfigurationFailure(typed.kind(),failure);
            if(failure instanceof ProtectedTransactionExecutor.ExecutionFailure exec
               && exec.outcome()==ProtectedTransactionExecutor.Outcome.INDETERMINATE)
                throw new StationInstalledConfigurationFailure(Kind.INDETERMINATE,failure);
            for(Throwable c=failure;c!=null;c=c.getCause()){
                if(c instanceof StationInstalledConfigurationFailure typed)
                    throw new StationInstalledConfigurationFailure(typed.kind(),failure);
                if(c instanceof org.springframework.dao.DuplicateKeyException
                   || c instanceof java.sql.SQLException sql && "23505".equals(sql.getSQLState()))
                    throw new StationInstalledConfigurationFailure(Kind.CONFLICT,failure);
                if(c instanceof org.springframework.security.core.AuthenticationException)
                    throw new StationInstalledConfigurationFailure(Kind.UNAUTHENTICATED,failure);
                if(c instanceof org.springframework.security.access.AccessDeniedException){
                    var auth=org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
                    boolean anonymous=auth==null || !auth.isAuthenticated()
                        || auth instanceof org.springframework.security.authentication.AnonymousAuthenticationToken;
                    throw new StationInstalledConfigurationFailure(anonymous?Kind.UNAUTHENTICATED:Kind.DENIED,failure);
                }
            }
            throw new StationInstalledConfigurationFailure(
                failure instanceof IllegalArgumentException || failure instanceof NullPointerException
                    ? Kind.INVALID:Kind.UNAVAILABLE,failure);
        }
    }
}
