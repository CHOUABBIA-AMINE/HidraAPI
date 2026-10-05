/**
 * @Project : HidraAPI
 * @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author : Abir MEDJERAB
 * @Owner : Sonatrach / TRC : Digitalization Initiative
 * @Name : RiskOrganizationReferenceQueryServiceTest
 * @CreatedOn : 2025-06-26
 * @UpdatedOn : 2026-10-05
 * @Type : Class
 * @Layer : Organization Test
 * @Module : organization
 * @Package : dz.sh.hidra.modules.organization.application.service
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RiskOrganizationReferenceQueryServiceTest {

    @Test
    void resolvesOrganizationUnitThroughOwnerRepository() {
        OrganizationUnitRepositoryPort repository = mock(OrganizationUnitRepositoryPort.class);
        OrganizationUnit unit = mock(OrganizationUnit.class);
        when(unit.id()).thenReturn("unit-1");
        when(unit.code()).thenReturn("UNIT-1");
        when(unit.nameFr()).thenReturn("Unité");
        when(repository.findById("unit-1")).thenReturn(Optional.of(unit));

        RiskOrganizationReferenceQueryService service =
                new RiskOrganizationReferenceQueryService(repository);

        assertThat(service.resolveOrganizationUnit(" unit-1 ")).isPresent();
        assertThat(service.resolveOrganizationUnit(" ")).isEmpty();
    }
}
