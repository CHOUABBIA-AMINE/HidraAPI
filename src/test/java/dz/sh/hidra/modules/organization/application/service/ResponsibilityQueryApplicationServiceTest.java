/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityQueryApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Focused tests for responsibility assignment queries.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.port.out.ResponsibilityAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResponsibilityQueryApplicationServiceTest {

    @Test
    void delegatesCanonicalScopeAndNormalizedAssigneeQueries() {
        var repository = org.mockito.Mockito.mock(ResponsibilityAssignmentRepositoryPort.class);
        var service = new ResponsibilityQueryApplicationService(repository);
        service.listByScopeId(42L);
        service.listByAssignee(" EMPLOYEE ", " emp-1 ");
        org.mockito.Mockito.verify(repository).findByScopeId(42L);
        org.mockito.Mockito.verify(repository).findByAssignee("EMPLOYEE", "emp-1");
    }

    @Test
    void rejectsInvalidQueryIdentityBeforePersistence() {
        var repository = org.mockito.Mockito.mock(ResponsibilityAssignmentRepositoryPort.class);
        var service = new ResponsibilityQueryApplicationService(repository);
        assertThrows(IllegalArgumentException.class, () -> service.listByScopeId(0L));
        assertThrows(IllegalArgumentException.class, () -> service.listByAssignee(" ", "emp-1"));
        assertEquals(0, org.mockito.Mockito.mockingDetails(repository).getInvocations().size());
    }
}
