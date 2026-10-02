/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityAssignmentPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.mapper
 *
 * @Description : Canonical mapper for responsibility assignments using registry scope IDs.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.ResponsibilityAssignmentJpaEntity;

/**
 * Maps ResponsibilityAssignment without reintroducing legacy type/id/code/name scope tuples.
 */
public final class ResponsibilityAssignmentPersistenceMapper {

    private ResponsibilityAssignmentPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static ResponsibilityAssignmentJpaEntity toEntity(ResponsibilityAssignment model) {
        return new ResponsibilityAssignmentJpaEntity(
                model.id(),
                model.responsibilityType(),
                model.assigneeType(),
                model.assigneeId(),
                model.scopeId(),
                model.description(),
                model.validFrom(),
                model.validTo(),
                model.status(),
                model.createdAt(),
                model.updatedAt()
        );
    }

    public static ResponsibilityAssignment toDomain(ResponsibilityAssignmentJpaEntity entity) {
        return new ResponsibilityAssignment(
                entity.id(),
                entity.responsibilityType(),
                entity.assigneeType(),
                entity.assigneeId(),
                entity.scopeId(),
                entity.description(),
                entity.validFrom(),
                entity.validTo(),
                entity.status(),
                entity.createdAt(),
                entity.updatedAt()
        );
    }
}
