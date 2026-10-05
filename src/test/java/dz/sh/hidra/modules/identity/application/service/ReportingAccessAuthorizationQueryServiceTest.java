/**
 * @Project : HidraAPI
 * @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author : Abir MEDJERAB
 * @Owner : Sonatrach / TRC : Digitalization Initiative
 * @Name : ReportingAccessAuthorizationQueryServiceTest
 * @CreatedOn : 2025-06-26
 * @UpdatedOn : 2026-10-05
 * @Type : Class
 * @Layer : Identity Test
 * @Module : identity
 * @Package : dz.sh.hidra.modules.identity.application.service
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.reporting.ReportingAccessAuthorizationContract;
import dz.sh.hidra.modules.identity.application.dto.PermissionDecisionDto;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReportingAccessAuthorizationQueryServiceTest {

    @Test
    void delegatesDecisionToIdentityPermissionEvaluation() {
        EvaluatePermissionUseCase evaluator = mock(EvaluatePermissionUseCase.class);
        when(evaluator.evaluate(any())).thenReturn(new PermissionDecisionDto(
                true,
                AuthorizationDecisionValue.PERMIT,
                "PERMIT",
                "permitted",
                Instant.parse("2026-10-05T00:00:00Z")
        ));
        ReportingAccessAuthorizationQueryService service =
                new ReportingAccessAuthorizationQueryService(evaluator);

        assertThat(service.permitted(new ReportingAccessAuthorizationContract.AccessRequest(
                "actor-1",
                "REPORT_VIEW",
                "definition-1",
                "GLOBAL",
                null
        ))).isTrue();
    }
}
