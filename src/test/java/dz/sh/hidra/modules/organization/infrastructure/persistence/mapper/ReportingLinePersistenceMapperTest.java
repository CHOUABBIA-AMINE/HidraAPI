/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLinePersistenceMapperTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.mapper
 *
 * @Description : Verifies reporting-line catalog and typed-subject persistence round trips.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.organization.domain.model.ReportingLine;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineType;
import dz.sh.hidra.modules.organization.domain.value.ReportingSubjectReference;
import dz.sh.hidra.modules.organization.domain.value.ReportingSubjectType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.ReportingLineTypeJpaEntity;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportingLinePersistenceMapperTest {

    @Test
    void roundTripsCatalogTypeAndTypedEmployeeToPositionReportingSubjects() {
        Instant now = Instant.parse("2026-10-04T14:00:00Z");
        ReportingLine model = new ReportingLine(
                "rl-1",
                ReportingLineType.FUNCTIONAL,
                new ReportingSubjectReference(ReportingSubjectType.EMPLOYEE, "emp-1"),
                new ReportingSubjectReference(ReportingSubjectType.POSITION, "pos-9"),
                now,
                null,
                true,
                now,
                now
        );
        ReportingLineTypeJpaEntity typeEntity = new ReportingLineTypeJpaEntity(
                "FUNCTIONAL",
                "FUNCTIONAL",
                null,
                null,
                null,
                true,
                now,
                now
        );

        var entity = OrganizationPersistenceMapper.toEntity(model, typeEntity);

        assertEquals("FUNCTIONAL", entity.reportingLineType().code());
        assertEquals(ReportingSubjectType.EMPLOYEE, entity.sourceSubjectType());
        assertEquals(ReportingSubjectType.POSITION, entity.targetSubjectType());
        assertEquals("EMPLOYEE", entity.sourceType());
        assertEquals("POSITION", entity.targetType());

        ReportingLine restored = OrganizationPersistenceMapper.toDomain(entity);

        assertEquals(model.source(), restored.source());
        assertEquals(model.target(), restored.target());
        assertEquals(model.reportingLineType(), restored.reportingLineType());
    }
}
