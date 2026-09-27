/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationContactPointPersistenceMapperTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.mapper
 *
 * @Description : Verifies typed contact-point target persistence round trips.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.organization.domain.model.OrganizationContactPoint;
import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetReference;
import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetType;
import dz.sh.hidra.modules.organization.domain.value.ContactPointType;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrganizationContactPointPersistenceMapperTest {

    @Test
    void roundTripsTypedOrganizationUnitTarget() {
        Instant now = Instant.parse("2026-09-27T15:00:00Z");
        OrganizationContactPoint model = new OrganizationContactPoint(
                "cp-1",
                ContactPointType.PHONE,
                new ContactPointTargetReference(
                        ContactPointTargetType.ORGANIZATION_UNIT,
                        "unit-1"
                ),
                "Control room",
                "+213-21-000000",
                true,
                true,
                true,
                now,
                now
        );

        var entity = OrganizationPersistenceMapper.toEntity(model);

        assertEquals(ContactPointTargetType.ORGANIZATION_UNIT, entity.contactTargetType());
        assertEquals("ORGANIZATION_UNIT", entity.targetType());
        assertEquals("unit-1", entity.targetId());

        OrganizationContactPoint restored = OrganizationPersistenceMapper.toDomain(entity);

        assertEquals(model.target(), restored.target());
        assertEquals(model.contactPointType(), restored.contactPointType());
        assertEquals(model.value(), restored.value());
    }
}
