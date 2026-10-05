/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkbenchResourceDefinitionTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Verifies explicit resource registration rejects sensitive and invalid contracts.
 *
 */
package dz.sh.hidra.platform.workbench;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class WorkbenchResourceDefinitionTest {
    private static WorkbenchResourceDefinition definition(String resource, List<String> output, List<String> query) {
        return new WorkbenchResourceDefinition("identity", resource, "id", output, query, List.of(), List.of(),
                "Test purpose", "Test owner", "TEST-REVIEW");
    }

    @Test
    void refusesCredentialResourcesAndSensitiveFieldsAtRegistration() {
        for (String resource : List.of("local-credentials", "access-tokens", "private-keys", "api-keys")) {
            assertThrows(IllegalArgumentException.class, () -> definition(resource, List.of("id"), List.of()));
        }
        for (String field : List.of("passwordHash", "apiKey", "accessToken", "privateKey", "connectionSettings", "secret", "privateCertificate", "optionsJson", "optionsJSON")) {
            assertThrows(IllegalArgumentException.class, () -> definition("safe-users", List.of("id", field), List.of()));
        }
    }

    @Test
    void requiresApprovedIdentifierAndQueryFieldsAndReviewEvidence() {
        assertThrows(IllegalArgumentException.class, () -> definition("Safe Users", List.of("id"), List.of()));
        assertThrows(IllegalArgumentException.class, () -> definition("safe-users", List.of("label"), List.of()));
        assertThrows(IllegalArgumentException.class, () -> definition("safe-users", List.of("id"), List.of("label")));
        assertThrows(IllegalArgumentException.class, () -> definition("safe-users", List.of("id", "owner.name"), List.of()));
        assertThrows(IllegalArgumentException.class, () -> new WorkbenchResourceDefinition("identity", "safe-users", "id",
                List.of("id"), List.of(), List.of(), List.of(), "purpose", "owner", ""));
    }

    @Test
    void snapshotsFieldSetsAndRejectsDuplicateRegistration() {
        var fields = new ArrayList<>(List.of("id"));
        var definition = definition("safe-users", fields, List.of());
        fields.add("passwordHash");
        assertEquals(List.of("id"), definition.outputFields());
        var adapter = mock(WorkbenchResource.class);
        when(adapter.definition()).thenReturn(definition);
        assertThrows(IllegalArgumentException.class, () -> new WorkbenchResourceRegistry(List.of(adapter, adapter)));
    }
}
