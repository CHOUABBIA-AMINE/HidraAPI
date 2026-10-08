/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentOrganizationContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.semantic
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.organization.semantic;

import dz.sh.hidra.modules.organization.application.service.IncidentOrganizationQueryService;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class IncidentOrganizationContractTest {
    @Test void missingAndBlankUnitsDoNotResolve() {
        var repository=mock(OrganizationUnitRepositoryPort.class);var query=new IncidentOrganizationQueryService(repository);
        assertTrue(query.resolve(" ").isEmpty());verifyNoInteractions(repository);
        when(repository.findById("missing")).thenReturn(Optional.empty());assertTrue(query.resolve("missing").isEmpty());
    }
}
