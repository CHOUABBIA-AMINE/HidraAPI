/**
 * @Project : HidraAPI
 * @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author : Abir MEDJERAB
 * @Owner : Sonatrach / TRC : Digitalization Initiative
 * @Name : ReportingOrganizationUnitReferenceQueryServiceTest
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

class ReportingOrganizationUnitReferenceQueryServiceTest {

    @Test
    void resolvesExistenceThroughOrganizationOwnerRepository() {
        OrganizationUnitRepositoryPort repository = mock(OrganizationUnitRepositoryPort.class);
        when(repository.findById("unit-1")).thenReturn(Optional.of(mock(OrganizationUnit.class)));

        ReportingOrganizationUnitReferenceQueryService service =
                new ReportingOrganizationUnitReferenceQueryService(repository);

        assertThat(service.exists(" unit-1 ")).isTrue();
        assertThat(service.exists(null)).isFalse();
    }
}
