/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningTargetValuePolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.configuration
 *
 * @Description : Reads owner-approved target representations without inventing catalog mappings.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.configuration;

import jakarta.persistence.EntityManager;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class PlanningTargetValuePolicy {
    private final EntityManager entityManager;

    public PlanningTargetValuePolicy(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager);
    }

    public enum Representation { NUMERIC, TEXT }
    public record Policy(String targetTypeId, Representation representation, boolean active) { }

    public Optional<Policy> findByIdForShare(String id) {
        var rows = entityManager.createNativeQuery(
                "select target_type_id, representation_kind, active from hidra_planning_target_value_policy where target_type_id = :id for share")
                .setParameter("id", id).getResultList();
        if (rows.isEmpty()) return Optional.empty();
        Object[] row = (Object[]) rows.getFirst();
        return Optional.of(new Policy((String) row[0], Representation.valueOf((String) row[1]), (Boolean) row[2]));
    }
}
