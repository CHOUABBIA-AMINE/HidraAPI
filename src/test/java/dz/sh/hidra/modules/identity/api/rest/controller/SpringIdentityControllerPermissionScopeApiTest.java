/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringIdentityControllerPermissionScopeApiTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Verifies permission evaluation keeps the REST scope boundary separate from the domain.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.identity.api.rest.request.AuthorizationScopeRequest;
import dz.sh.hidra.modules.identity.api.rest.request.EvaluatePermissionRequest;
import dz.sh.hidra.modules.identity.api.rest.response.PermissionDecisionResponse;
import dz.sh.hidra.modules.identity.application.dto.PermissionDecisionDto;
import dz.sh.hidra.modules.identity.application.port.in.CreateUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import dz.sh.hidra.modules.identity.application.query.EvaluatePermissionQuery;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class SpringIdentityControllerPermissionScopeApiTest {

    @Test
    void permissionRequestMapsApiOwnedScopeBeforeInvokingApplicationUseCase() {
        CreateUserUseCase createUserUseCase = mock(CreateUserUseCase.class);
        EvaluatePermissionUseCase evaluatePermissionUseCase = mock(EvaluatePermissionUseCase.class);
        SpringIdentityController controller =
                new SpringIdentityController(createUserUseCase, evaluatePermissionUseCase);

        Instant evaluatedAt = Instant.parse("2026-09-29T08:50:00Z");
        when(evaluatePermissionUseCase.evaluate(any(EvaluatePermissionQuery.class)))
                .thenReturn(new PermissionDecisionDto(
                        true,
                        AuthorizationDecisionValue.PERMIT,
                        "POLICY_PERMIT",
                        "Permission granted.",
                        evaluatedAt
                ));

        PermissionDecisionResponse response = controller.evaluate(new EvaluatePermissionRequest(
                "user-1",
                "pipeline:read",
                "PIPELINE",
                "pipeline-1",
                new AuthorizationScopeRequest(
                        ScopeType.PIPELINE,
                        " pipeline-1 ",
                        " PIPE-001 "
                )
        ));

        ArgumentCaptor<EvaluatePermissionQuery> queryCaptor =
                ArgumentCaptor.forClass(EvaluatePermissionQuery.class);
        verify(evaluatePermissionUseCase).evaluate(queryCaptor.capture());

        EvaluatePermissionQuery query = queryCaptor.getValue();
        assertThat(query.scope().scopeType()).isEqualTo(ScopeType.PIPELINE);
        assertThat(query.scope().scopeReferenceId()).isEqualTo("pipeline-1");
        assertThat(query.scope().scopeCodeSnapshot()).isEqualTo("PIPE-001");

        assertThat(response.permitted()).isTrue();
        assertThat(response.decision()).isEqualTo(AuthorizationDecisionValue.PERMIT);
        assertThat(response.evaluatedAt()).isEqualTo(evaluatedAt);
    }
}
