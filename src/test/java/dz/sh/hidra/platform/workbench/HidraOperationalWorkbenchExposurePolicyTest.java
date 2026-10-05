/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraOperationalWorkbenchExposurePolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
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
