/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.semantic
 *
 * @Description : Verifies HMR-007 Role semantic remediation.
 *
 */
package dz.sh.hidra.modules.identity.semantic;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoleSemanticRemediationTest {

    @Test
    void migrationEnforcesUniqueRoleCode() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_007__hmr_007_identity_role.sql"
        ));

        assertThat(sql).contains("CREATE UNIQUE INDEX uk_hmr007_identity_role_code");
        assertThat(sql).contains("ON hidra_identity_role (code)");
    }
}
