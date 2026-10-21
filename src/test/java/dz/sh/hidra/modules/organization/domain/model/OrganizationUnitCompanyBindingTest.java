/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitCompanyBindingTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Verifies explicit immutable binding identities and required revision facts.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrganizationUnitCompanyBindingTest {
    private static final Instant RECORDED = Instant.parse("2026-10-01T00:00:00Z");

    @Test void normalizesExplicitIdentifiersWithoutInferringOwnership() {
        var binding = new OrganizationUnitCompanyBinding(" binding ", 3, " unit ", " party ", RECORDED);
        assertEquals("binding", binding.id());
        assertEquals("unit", binding.organizationUnitId());
        assertEquals("party", binding.companyPartyId());
        assertEquals(3, binding.revision());
        assertEquals(RECORDED, binding.recordedAt());
    }

    @Test void rejectsMissingIdentifiersIndependently() {
        for (String invalid : new String[] { null, "", " \t\n " }) {
            assertThrows(InvalidOrganizationValueException.class,
                    () -> new OrganizationUnitCompanyBinding(invalid, 1, "unit", "party", RECORDED));
            assertThrows(InvalidOrganizationValueException.class,
                    () -> new OrganizationUnitCompanyBinding("binding", 1, invalid, "party", RECORDED));
            assertThrows(InvalidOrganizationValueException.class,
                    () -> new OrganizationUnitCompanyBinding("binding", 1, "unit", invalid, RECORDED));
        }
    }

    @Test void requiresPositiveRevisionAndExplicitRecordedTime() {
        for (long invalid : new long[] { Long.MIN_VALUE, -1, 0 }) {
            assertThrows(InvalidOrganizationValueException.class,
                    () -> new OrganizationUnitCompanyBinding("binding", invalid, "unit", "party", RECORDED));
        }
        assertThrows(InvalidOrganizationValueException.class,
                () -> new OrganizationUnitCompanyBinding("binding", 1, "unit", "party", null));
        assertEquals(Long.MAX_VALUE,
                new OrganizationUnitCompanyBinding("binding", Long.MAX_VALUE, "unit", "party", RECORDED).revision());
    }

    @Test void equalityIncludesEveryImmutableBindingFact() {
        var original = new OrganizationUnitCompanyBinding("binding", 7, "unit", "party", RECORDED);
        var equal = new OrganizationUnitCompanyBinding(" binding ", 7, "unit", "party", RECORDED);
        assertEquals(original, equal);
        assertEquals(original.hashCode(), equal.hashCode());
        for (var different : new OrganizationUnitCompanyBinding[] {
                new OrganizationUnitCompanyBinding("other", 7, "unit", "party", RECORDED),
                new OrganizationUnitCompanyBinding("binding", 8, "unit", "party", RECORDED),
                new OrganizationUnitCompanyBinding("binding", 7, "other", "party", RECORDED),
                new OrganizationUnitCompanyBinding("binding", 7, "unit", "other", RECORDED),
                new OrganizationUnitCompanyBinding("binding", 7, "unit", "party", RECORDED.plusSeconds(1)) }) {
            assertNotEquals(original, different);
        }
        assertEquals(7, original.revision());
        assertEquals("party", original.companyPartyId());
    }
}
