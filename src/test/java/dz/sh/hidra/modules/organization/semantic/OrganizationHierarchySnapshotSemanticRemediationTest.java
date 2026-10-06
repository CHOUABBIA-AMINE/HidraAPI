/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationHierarchySnapshotSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.semantic
 *
 * @Description : Verifies HMR-075 mapping and PostgreSQL mandatory-reference enforcement.
 *
 */
package dz.sh.hidra.modules.organization.semantic;

import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OrganizationHierarchySnapshotJpaEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers(disabledWithoutDocker = true)
class OrganizationHierarchySnapshotSemanticRemediationTest extends OrganizationMandatoryReferenceMigrationSupport {
    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");

    @Override protected PostgreSQLContainer<?> postgres() { return POSTGRES; }
    @Override protected String table() { return "hidra_org_hierarchy_snapshot"; }
    @Override protected String column() { return "captured_by_employee_id"; }
    @Override protected String migration() { return "V20261006_003__hmr_075_captured_by_employee_id_required.sql"; }
    @Override protected Class<?> entity() { return OrganizationHierarchySnapshotJpaEntity.class; }
    @Override protected String field() { return "capturedByEmployeeId"; }
    @Override protected String task() { return "HMR-075"; }
}
