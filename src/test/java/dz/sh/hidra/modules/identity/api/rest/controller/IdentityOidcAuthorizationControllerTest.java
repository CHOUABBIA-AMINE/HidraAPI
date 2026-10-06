/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityOidcAuthorizationControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Resolves bounded and explainable Identity authorization evidence.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.value.*;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import dz.sh.hidra.modules.identity.application.query.EvaluatePermissionQuery;
import dz.sh.hidra.modules.identity.application.dto.PermissionDecisionDto;
import dz.sh.hidra.modules.identity.api.rest.request.EvaluatePermissionRequest;
import org.springframework.security.access.AccessDeniedException;
import java.util.Set;
import java.time.Instant;

class IdentityOidcAuthorizationControllerTest {
    private final HidraPrincipal principal=new HidraPrincipal("u","user","User",ProviderType.OIDC,"provider",Set.of(),Set.of(),"external");
    @Test void evaluatesOnlyTheLinkedAuthenticatedOidcUser() {
        var useCase=mock(EvaluatePermissionUseCase.class);
        var query=new EvaluatePermissionQuery("u","pipeline:pipeline:read","PIPELINE","p",null);
        when(useCase.evaluate(query)).thenReturn(new PermissionDecisionDto(true,AuthorizationDecisionValue.PERMIT,"AUTHORITY_MATCHED","Matched",Instant.now()));
        var response=new IdentityOidcAuthorizationController(useCase).evaluate(principal,new EvaluatePermissionRequest("u","pipeline:pipeline:read","PIPELINE","p",null));
        assertTrue(response.permitted()); verify(useCase).evaluate(query);
    }
    @Test void rejectsOtherUsersAndUnlinkedOrLocalPrincipals() {
        var useCase=mock(EvaluatePermissionUseCase.class); var controller=new IdentityOidcAuthorizationController(useCase);
        var other=new EvaluatePermissionRequest("other","pipeline:pipeline:read",null,null,null);
        assertThrows(AccessDeniedException.class,()->controller.evaluate(principal,other));
        assertThrows(AccessDeniedException.class,()->controller.evaluate("untrusted",other));
        assertThrows(AccessDeniedException.class,()->controller.evaluate(new HidraPrincipal("other","user",null,ProviderType.LOCAL,null,Set.of(),Set.of()),other));
        verifyNoInteractions(useCase);
    }
}
