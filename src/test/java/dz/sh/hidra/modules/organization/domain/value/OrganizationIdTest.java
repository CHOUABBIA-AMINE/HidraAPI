/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationIdTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Verifies Organization String/UUID identifier policy semantics.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OrganizationIdTest {

    @Test
    void normalizesExistingStringIdentifier() {
        assertThat(OrganizationId.of("  employee-1  ").value()).isEqualTo("employee-1");
    }

    @Test
    void rejectsNullOrBlankIdentifier() {
        assertThatThrownBy(() -> OrganizationId.of(null))
                .isInstanceOf(InvalidOrganizationValueException.class);
        assertThatThrownBy(() -> OrganizationId.of("   "))
                .isInstanceOf(InvalidOrganizationValueException.class);
    }

    @Test
    void generatesUuidText() {
        OrganizationId id = OrganizationId.newId();

        assertThat(UUID.fromString(id.value()).toString()).isEqualTo(id.value());
    }

    @Test
    void operationalScopeRegistryIdentityRemainsLongAndSeparate() {
        Class<?> idType = Arrays.stream(OperationalScope.class.getRecordComponents())
                .filter(component -> component.getName().equals("id"))
                .findFirst()
                .map(RecordComponent::getType)
                .orElseThrow();

        assertThat(idType).isEqualTo(Long.class);
    }
}
