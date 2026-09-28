/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.mapper
 *
 * @Description : Maps party domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.party.domain.model.*;
import dz.sh.hidra.modules.party.infrastructure.persistence.entity.*;

/**
 * Maps party domain models to JPA entities.
 */
public final class PartyPersistenceMapper {

    private PartyPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static PartyJpaEntity toEntity(Party model) {
            return new PartyJpaEntity(
                        model.id(),
                        model.code(),
                        model.partyTypeId(),
                        model.legalName(),
                        model.tradeName(),
                        model.shortName(),
                        model.countryCode(),
                        model.jurisdictionCode(),
                        model.status(),
                        model.primaryRoleCodeSnapshot(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static Party toDomain(PartyJpaEntity entity) {
            return new Party(
                        entity.id(),
                        entity.code(),
                        entity.partyTypeId(),
                        entity.legalName(),
                        entity.tradeName(),
                        entity.shortName(),
                        entity.countryCode(),
                        entity.jurisdictionCode(),
                        entity.status(),
                        entity.primaryRoleCodeSnapshot(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
        public static PartyRoleAssignmentJpaEntity toEntity(PartyRoleAssignment model) {
            return new PartyRoleAssignmentJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.roleId(),
                        model.validFrom(),
                        model.validTo(),
                        model.status(),
                        model.qualificationRequired(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyRoleAssignment toDomain(PartyRoleAssignmentJpaEntity entity) {
            return new PartyRoleAssignment(
                        entity.id(),
                        entity.partyId(),
                        entity.roleId(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.status(),
                        entity.qualificationRequired(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
}
