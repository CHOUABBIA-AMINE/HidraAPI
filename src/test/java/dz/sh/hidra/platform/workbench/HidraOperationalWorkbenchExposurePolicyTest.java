/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraOperationalWorkbenchExposurePolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-26
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Verifies fail-closed Workbench resource and field exposure configuration.
 *
 */
package dz.sh.hidra.platform.workbench;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class HidraOperationalWorkbenchExposurePolicyTest {

    @Test
    void blankConfigurationExposesNoResources() {
        HidraOperationalWorkbenchExposurePolicy policy = new HidraOperationalWorkbenchExposurePolicy("   ");

        assertThat(policy.hasNoApprovedResources()).isTrue();
        assertThat(policy.approvedFields("identity", "users")).isEmpty();
    }

    @Test
    void approvedResourceExposesOnlyExplicitFields() {
        HidraOperationalWorkbenchExposurePolicy policy =
                new HidraOperationalWorkbenchExposurePolicy(" Identity/Users = id, username ");

        assertThat(policy.hasNoApprovedResources()).isFalse();
        assertThat(policy.approvedFields("identity", "users"))
                .hasValueSatisfying(fields -> assertThat(fields).containsExactlyInAnyOrder("id", "username"));
        assertThat(policy.approvedFields("identity", "roles")).isEmpty();
    }

    @Test
    void localCredentialResourceCannotBeConfigured() {
        assertThatThrownBy(() -> new HidraOperationalWorkbenchExposurePolicy(
                "identity/local-credentials=id,userId"
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("credential/secret resources cannot be exposed");
    }

    @Test
    void credentialOrSecretResourceNamesCannotBeConfigured() {
        assertThatThrownBy(() -> new HidraOperationalWorkbenchExposurePolicy(
                "identity/api-credentials=id,name"
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("credential/secret resources cannot be exposed");

        assertThatThrownBy(() -> new HidraOperationalWorkbenchExposurePolicy(
                "identity/client-secrets=id,name"
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("credential/secret resources cannot be exposed");
    }

    @Test
    void controlledDocumentsMayNeverBeExposedThroughGenericWorkbench() {
        var policy=new HidraOperationalWorkbenchExposurePolicy("identity/users=id,username");
        assertThat(policy.approvedFields("documents","document")).isEmpty();
        assertThat(policy.approvedFields("documents","document-versions")).isEmpty();
        assertThat(policy.approvedFields("identity","users")).isPresent();
        assertThatThrownBy(()->new HidraOperationalWorkbenchExposurePolicy(
            "documents/documents=id,code;identity/users=id"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void enrolledHseRowsAndDependentEvidenceCannotBeConfiguredOrQueried() {
        var policy = new HidraOperationalWorkbenchExposurePolicy("identity/users=id,username");
        for (String resource : java.util.List.of("hse-cases", "hse-corrective-preventive-actions",
                "hse-case-status-histories", "hse-case-evidence-links",
                "hse-impact-assessments", "hse-closures")) {
            assertThat(policy.approvedFields("HSE", resource)).isEmpty();
            assertThatThrownBy(() -> new HidraOperationalWorkbenchExposurePolicy(
                    "hse/" + resource + "=id;identity/users=id")).isInstanceOf(IllegalArgumentException.class);
        }
        assertThat(policy.approvedFields("identity", "users")).isPresent();
        assertThat(new HidraOperationalWorkbenchExposurePolicy("hse/safety-observations=id")
                .approvedFields("hse", "safety-observations")).isPresent();
        for(String resource:java.util.List.of(
                "custody-agreement-parties",
                "custody-agreements",
                "custody-approval-references",
                "custody-batchs",
                "custody-correction-factors",
                "custody-discrepancies",
                "custody-document-references",
                "custody-measurement-periods",
                "custody-measurement-snapshots",
                "custody-meter-run-snapshots",
                "custody-metering-systems",
                "custody-quality-certificates",
                "custody-quality-samples",
                "custody-quantity-calculations",
                "custody-reconciliations",
                "custody-ticket-lines",
                "custody-transfer-points",
                "custody-transfer-tickets")){
            assertThat(policy.approvedFields("CUSTODY",resource)).isEmpty();
            assertThatThrownBy(()->new HidraOperationalWorkbenchExposurePolicy("custody/"+resource+"=id"))
                .isInstanceOf(IllegalArgumentException.class);
        }
        var catalogs=new HidraOperationalWorkbenchExposurePolicy("custody/custody-catalog-entries=id;custody/custody-catalog-translations=id");
        assertThat(catalogs.approvedFields("custody","custody-catalog-entries")).isPresent();
        assertThat(catalogs.approvedFields("custody","custody-catalog-translations")).isPresent();
    }

    @Test
    void passwordHashAndSecretFieldsCannotBeConfigured() {
        assertThatThrownBy(() -> new HidraOperationalWorkbenchExposurePolicy(
                "identity/users=id,passwordHash"
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("password/secret fields cannot be exposed");

        assertThatThrownBy(() -> new HidraOperationalWorkbenchExposurePolicy(
                "identity/users=id,secretReference"
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("password/secret fields cannot be exposed");
    }
}
