/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityRestMapperPermissionScopeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.mapper
 *
 * @Description : Verifies API-owned permission scope requests map to domain authorization scopes.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.identity.api.rest.request.AuthorizationScopeRequest;
import dz.sh.hidra.modules.identity.api.rest.request.EvaluatePermissionRequest;
import dz.sh.hidra.modules.identity.application.query.EvaluatePermissionQuery;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class IdentityRestMapperPermissionScopeTest {

    @Test
    void apiScopeRecordPreservesTheEstablishedWireComponentNames() {
        assertThat(Arrays.stream(AuthorizationScopeRequest.class.getRecordComponents())
                .map(component -> component.getName())
                .toList())
                .containsExactly("scopeType", "scopeReferenceId", "scopeCodeSnapshot");
    }

    @Test
    void globalScopeNormalizationRemainsOwnedByTheDomainBoundary() {
        EvaluatePermissionQuery query = IdentityRestMapper.toQuery(new EvaluatePermissionRequest(
                "user-1",
                "pipeline:read",
                "PIPELINE",
                "pipeline-1",
                new AuthorizationScopeRequest(null, " ignored-reference ", " ignored-code ")
        ));

        assertThat(query.scope()).isNotNull();
        assertThat(query.scope().scopeType()).isEqualTo(ScopeType.GLOBAL);
        assertThat(query.scope().scopeReferenceId()).isNull();
        assertThat(query.scope().scopeCodeSnapshot()).isNull();
    }

    @Test
    void nonGlobalScopeMapsAndNormalizesWithoutChangingApplicationSemantics() {
        EvaluatePermissionQuery query = IdentityRestMapper.toQuery(new EvaluatePermissionRequest(
                "user-1",
                "pipeline:read",
                "PIPELINE",
                "pipeline-1",
                new AuthorizationScopeRequest(
                        ScopeType.ORGANIZATION_UNIT,
                        " org-unit-1 ",
                        " TRC-DP "
                )
        ));

        assertThat(query.scope()).isNotNull();
        assertThat(query.scope().scopeType()).isEqualTo(ScopeType.ORGANIZATION_UNIT);
        assertThat(query.scope().scopeReferenceId()).isEqualTo("org-unit-1");
        assertThat(query.scope().scopeCodeSnapshot()).isEqualTo("TRC-DP");
    }

    @Test
    void optionalNullScopeRemainsNull() {
        EvaluatePermissionQuery query = IdentityRestMapper.toQuery(new EvaluatePermissionRequest(
                "user-1",
                "pipeline:read",
                null,
                null,
                null
        ));

        assertThat(query.scope()).isNull();
    }
}
