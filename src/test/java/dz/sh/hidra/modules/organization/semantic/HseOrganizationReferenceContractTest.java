/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseOrganizationReferenceContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.semantic
 *
 * @Description : Enforces HSE-owned lifecycle integrity through owner-controlled references.
 *
 */
package dz.sh.hidra.modules.organization.semantic;

import dz.sh.hidra.modules.organization.application.service.HseOrganizationReferenceQueryService;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class HseOrganizationReferenceContractTest {
    final OrganizationUnitRepositoryPort repository=mock(OrganizationUnitRepositoryPort.class);
    final HseOrganizationReferenceQueryService service=new HseOrganizationReferenceQueryService(repository);
    @Test void missingOptionalReferenceHasNoInventedOwner() {assertTrue(service.resolve(null).isEmpty());when(repository.findById("missing")).thenReturn(Optional.empty());assertTrue(service.resolve("missing").isEmpty());}
    @Test void ownerReturnsItsCanonicalScalarSnapshot() {
        var unit=mock(OrganizationUnit.class);when(unit.id()).thenReturn("unit");when(unit.code()).thenReturn("UNIT");when(unit.nameFr()).thenReturn("Canonical Unit");
        when(repository.findById("unit")).thenReturn(Optional.of(unit));assertEquals("Canonical Unit",service.resolve("unit").orElseThrow().label());
    }
}
