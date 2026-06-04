/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Unit tests for OrganizationUnit aggregate lifecycle, station type, scope, and hierarchy rules.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitName;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitType;

/**
 * Tests the organization unit aggregate.
 *
 * <p>Business role:
 * Verifies organization units, station-as-organization-unit support, operational scope references,
 * and hierarchy invariants.
 *
 * <p>Architecture role:
 * This is a domain aggregate test with no infrastructure dependencies.
 */
class OrganizationUnitTest {

    @Test
    void shouldCreateActiveOrganizationUnit() {
        OrganizationUnit organizationUnit = OrganizationUnit.create(
                OrganizationUnitCode.of("REGION_EAST"),
                OrganizationUnitName.of("Operational East Region"),
                OrganizationUnitType.REGION,
                null,
                null);

        assertEquals(OrganizationUnitStatus.ACTIVE, organizationUnit.status());
        assertEquals(OrganizationUnitType.REGION, organizationUnit.type());
        assertTrue(organizationUnit.parentId().isEmpty());
    }

    @Test
    void shouldCreateStationOrganizationUnitWithStationScope() {
        OperationalScopeReference scopeReference = new OperationalScopeReference(
                OperationalScopeType.TOPOLOGY_COMPRESSION_STATION,
                "station-001",
                "CS-EAST-01",
                "Compression Station East 01");

        OrganizationUnit stationUnit = OrganizationUnit.createStation(
                OrganizationUnitCode.of("CS_EAST_01"),
                OrganizationUnitName.of("Compression Station East 01"),
                null,
                scopeReference);

        assertTrue(stationUnit.isStationOrganizationUnit());
        assertTrue(stationUnit.operationalScopeReference().isPresent());
        assertTrue(stationUnit.operationalScopeReference().orElseThrow().isStationScope());
    }

    @Test
    void shouldRejectStationOrganizationUnitWithNonStationScope() {
        OperationalScopeReference pipelineScope = new OperationalScopeReference(
                OperationalScopeType.TOPOLOGY_PIPELINE,
                "pipeline-001",
                "PIPE-01",
                "Pipeline 01");

        assertThrows(
                BusinessRuleViolationException.class,
                () -> OrganizationUnit.createStation(
                        OrganizationUnitCode.of("CS_EAST_02"),
                        OrganizationUnitName.of("Compression Station East 02"),
                        null,
                        pipelineScope));
    }

    @Test
    void shouldRejectSelfParentingWhenRestored() {
        OrganizationUnitId id = OrganizationUnitId.newId();
        Instant now = Instant.now();

        assertThrows(
                BusinessRuleViolationException.class,
                () -> OrganizationUnit.restore(
                        id,
                        OrganizationUnitCode.of("TEAM_01"),
                        OrganizationUnitName.of("Team 01"),
                        OrganizationUnitStatus.ACTIVE,
                        OrganizationUnitType.TEAM,
                        id,
                        null,
                        now,
                        now));
    }

    @Test
    void shouldRejectAssignmentWhenOrganizationUnitIsDisabled() {
        OrganizationUnit disabledUnit = OrganizationUnit.create(
                        OrganizationUnitCode.of("DEPT_OPS"),
                        OrganizationUnitName.of("Operations Department"),
                        OrganizationUnitType.DEPARTMENT,
                        null,
                        null)
                .disable();

        assertThrows(BusinessRuleViolationException.class, disabledUnit::ensureCanReceiveEmployeeAssignment);
    }
}
