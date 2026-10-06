/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowActorQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Enforces owner-validated Workflow execution and immutable evidence.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import dz.sh.hidra.modules.identity.application.port.out.UserRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import dz.sh.hidra.modules.identity.application.query.EvaluatePermissionQuery;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.domain.model.User;
import java.time.Instant;
import java.util.Optional;
import java.util.Objects;
import org.springframework.stereotype.Service;
@Service
public class WorkflowActorQueryService implements WorkflowActorContract {
    private final UserRepositoryPort users;
    private final EvaluatePermissionUseCase permissions;
    public WorkflowActorQueryService(UserRepositoryPort users, EvaluatePermissionUseCase permissions) {
        this.users=Objects.requireNonNull(users); this.permissions=Objects.requireNonNull(permissions);
    }
    public Optional<Actor> eligibleActor(String id, Instant at) {
        return id==null || id.isBlank() || at==null ? Optional.empty() : eligible(users.findById(id.trim()),at);
    }
    public Optional<Actor> eligibleReference(String reference, Instant at) {
        if(reference==null || reference.isBlank() || at==null) return Optional.empty();
        var byId=users.findById(reference.trim());
        return eligible(byId.isPresent()?byId:users.findByUsername(reference.trim()),at);
    }
    private Optional<Actor> eligible(Optional<User> user, Instant at) {
        return user.filter(u->u.active() && !u.locked(at) && u.displayName()!=null && !u.displayName().isBlank())
            .map(u->new Actor(u.id(),u.username(),u.displayName(),u.employeeReferenceId()));
    }
    public boolean permitted(String actorId, String permissionCode, String instanceId) {
        if(eligibleActor(actorId,Instant.now()).isEmpty()) return false;
        if(permissionCode==null || permissionCode.isBlank()) return true;
        return permissions.evaluate(new EvaluatePermissionQuery(actorId,permissionCode,
            "WORKFLOW_INSTANCE",instanceId,AuthorizationScope.global())).permitted();
    }
}
