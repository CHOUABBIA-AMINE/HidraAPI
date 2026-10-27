/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowHumanActorQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Provides bounded product source publication governance.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowHumanActorContract;
import dz.sh.hidra.modules.identity.application.port.out.UserRepositoryPort;
import dz.sh.hidra.modules.identity.domain.value.UserType;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
@Service
public class WorkflowHumanActorQueryService implements WorkflowHumanActorContract {
    private final UserRepositoryPort users;
    public WorkflowHumanActorQueryService(UserRepositoryPort users){this.users=Objects.requireNonNull(users);}
    public Optional<Human> eligibleHuman(String actorId,Instant at) {
        if(actorId==null || actorId.isBlank() || !actorId.equals(actorId.strip()) || actorId.codePointCount(0,actorId.length())>80 || at==null)return Optional.empty();
        return users.findById(actorId).filter(u->actorId.equals(u.id()) && u.userType()==UserType.HUMAN && u.active() && !u.locked(at)
            && u.displayName()!=null && !u.displayName().isBlank()).map(u->new Human(u.id(),u.displayName()));
    }
}
