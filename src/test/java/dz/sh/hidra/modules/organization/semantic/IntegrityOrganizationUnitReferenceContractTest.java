/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityOrganizationUnitReferenceContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.semantic
 *
 * @Description : Validates IntegrityCase provenance through explicit owner-controlled references.
 *
 */
package dz.sh.hidra.modules.organization.semantic;

import dz.sh.hidra.modules.organization.application.service.IntegrityOrganizationUnitReferenceQueryService;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class IntegrityOrganizationUnitReferenceContractTest {
    @Test void blankAndMissingUnitAreRejected() {
        var repository=mock(OrganizationUnitRepositoryPort.class);var owner=new IntegrityOrganizationUnitReferenceQueryService(repository);
        assertFalse(owner.exists(null));assertFalse(owner.exists(" "));when(repository.findById("missing")).thenReturn(Optional.empty());assertFalse(owner.exists("missing"));
    }
    @Test void actualOwnerLookupProvesExistenceWithoutInventedStateRule() {
        var repository=mock(OrganizationUnitRepositoryPort.class);when(repository.findById("unit")).thenReturn(Optional.of(mock(OrganizationUnit.class)));
        assertTrue(new IntegrityOrganizationUnitReferenceQueryService(repository).exists(" unit "));
    }
}
