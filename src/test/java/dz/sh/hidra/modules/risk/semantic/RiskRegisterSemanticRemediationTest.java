/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskRegisterSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Risk Test
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.semantic
 *
 * @Description : Verifies HMR-049 RiskRegister catalog, typed scope and audit semantics.
 *
 */
package dz.sh.hidra.modules.risk.semantic;

import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
import dz.sh.hidra.modules.risk.domain.model.RiskRegister;
import dz.sh.hidra.modules.risk.domain.value.RiskRegisterStatus;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RiskRegisterSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-05T00:00:00Z");

    @Test
    void domainRequiresCompleteTypedScopePair() {
        assertThatThrownBy(() -> register(" ", "scope-1"))
                .isInstanceOf(InvalidRiskValueException.class)
                .hasMessageContaining("scope type");
    }

    @Test
    void reviewFrequencyRemainsOpaqueAndIsNotMappedToRiskReviewType() throws Exception {
        RiskRegister register = register("PIPELINE", "pipeline-1");
        assertThat(register.reviewFrequencyId()).isEqualTo("frequency-opaque");

        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_049__hmr_049_risk_risk_register.sql"
        ));
        assertThat(sql)
                .doesNotContain("RISK_REVIEW_TYPE")
                .doesNotContain("owner_organization_unit_id)")
                .doesNotContain("FOREIGN KEY");
    }

    @Test
    void auditTaxonomyProvisioningIsRiskSpecific() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261005_001__provision_risk_register_created_audit_taxonomy.sql"
        ));
        assertThat(sql)
                .contains("'EVENT_TYPE'")
                .contains("'RISK_REGISTER_CREATED'")
                .doesNotContain("'EVENT_CATEGORY'");
    }

    private static RiskRegister register(String scopeType, String scopeId) {
        return new RiskRegister(
                "register-1",
                "REG-1",
                null,
                "Registre",
                null,
                null,
                "register-type-1",
                null,
                null,
                scopeType,
                scopeId,
                null,
                null,
                RiskRegisterStatus.DRAFT,
                "frequency-opaque",
                NOW,
                NOW.plusSeconds(60),
                "actor-1",
                "Actor One",
                NOW,
                NOW
        );
    }
}
