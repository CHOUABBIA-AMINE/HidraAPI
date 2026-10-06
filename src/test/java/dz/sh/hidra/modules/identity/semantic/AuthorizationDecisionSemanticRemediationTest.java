/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationDecisionSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.semantic
 *
 * @Description : Resolves bounded and explainable Identity authorization evidence.
 *
 */
package dz.sh.hidra.modules.identity.semantic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import dz.sh.hidra.modules.identity.domain.policy.*;
import dz.sh.hidra.modules.identity.domain.service.*;
import dz.sh.hidra.modules.identity.domain.model.AuthorizationDecision;
import dz.sh.hidra.modules.identity.domain.value.*;
import dz.sh.hidra.modules.identity.application.service.IdentityAuthorizationApplicationService;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationDecisionRepositoryPort;
import dz.sh.hidra.modules.identity.application.query.EvaluatePermissionQuery;
import java.time.Instant;
import java.util.*;

class AuthorizationDecisionSemanticRemediationTest {
    private final Instant at=Instant.parse("2026-10-06T12:00:00Z");
    private final AuthorizationEvaluationRequest request=new AuthorizationEvaluationRequest("u","pipeline:read","PIPELINE","p",AuthorizationScope.global());
    private final AuthorizationPolicyEvaluator evaluator=new AuthorizationPolicyEvaluator();
    private AuthorizationEvidence.Grant grant(GrantEffect effect) {
        return new AuthorizationEvidence.Grant(1,effect,List.of("user-role","role-permission"),null,List.of());
    }
    private AuthorizationEvidence.Rule rule(String id,PolicyRuleEffect effect,String context,String obligation) {
        return new AuthorizationEvidence.Rule(id,1,effect,Arrays.asList(null,null,null,context),obligation);
    }
    private AuthorizationDecision evaluate(List<AuthorizationEvidence.Grant> grants,List<AuthorizationEvidence.Rule> rules,Map<String,Object> facts) {
        return evaluator.evaluate(request,new AuthorizationEvidence(null,grants,rules,facts),at);
    }
    @Test void permitsAndRecordsCompleteParticipatingChainsAtOneInstant() {
        var decision=evaluate(List.of(grant(GrantEffect.GRANT)),List.of(),Map.of());
        assertEquals(AuthorizationDecisionValue.PERMIT,decision.decision());
        assertEquals("[\"role-permission\",\"user-role\"]",decision.matchedGrantIds());
        assertEquals(at,decision.evaluatedAt());
    }
    @Test void explicitDenyDominatesPermitsAndUnresolvedPolicies() {
        var decision=evaluate(List.of(grant(GrantEffect.GRANT),grant(GrantEffect.DENY)),
                List.of(rule("unknown",PolicyRuleEffect.PERMIT,"{}",null)),Map.of());
        assertEquals(AuthorizationDecisionValue.DENY,decision.decision());
        assertEquals("EXPLICIT_DENY",decision.reasonCode());
    }
    @Test void activePolicyDenialDominatesDirectAuthority() {
        var decision=evaluate(List.of(grant(GrantEffect.GRANT)),List.of(rule("denial",PolicyRuleEffect.DENY,null,null)),Map.of());
        assertEquals(AuthorizationDecisionValue.DENY,decision.decision());
        assertEquals("[\"denial\"]",decision.matchedPolicyRuleIds());
    }
    @Test void policyPermitCanAuthorizeButConstraintCannotCreateAuthority() {
        assertEquals(AuthorizationDecisionValue.PERMIT,evaluate(List.of(),List.of(rule("permit",PolicyRuleEffect.PERMIT,null,null)),Map.of()).decision());
        assertEquals(AuthorizationDecisionValue.DENY,evaluate(List.of(),List.of(rule("constraint",PolicyRuleEffect.CONSTRAIN,null,null)),Map.of()).decision());
    }
    @Test void applicableConstraintRestrictsExistingAuthority() {
        String context="{\"op\":\"eq\",\"attribute\":\"subject.status\",\"type\":\"STRING\",\"value\":\"ACTIVE\"}";
        var facts=Map.<String,Object>of("subject.status","DISABLED");
        assertEquals(AuthorizationDecisionValue.DENY,evaluate(List.of(grant(GrantEffect.GRANT)),List.of(rule("constraint",PolicyRuleEffect.CONSTRAIN,context,null)),facts).decision());
    }
    @Test void obligationsAndMissingEvidenceCannotBeBypassed() {
        assertEquals(AuthorizationDecisionValue.INDETERMINATE,evaluate(List.of(grant(GrantEffect.GRANT)),List.of(rule("obligation",PolicyRuleEffect.PERMIT,null,"{}")),Map.of()).decision());
        String condition="{\"op\":\"eq\",\"attribute\":\"resource.owner\",\"type\":\"STRING\",\"value\":\"u\"}";
        assertEquals(AuthorizationDecisionValue.INDETERMINATE,evaluate(List.of(grant(GrantEffect.GRANT)),List.of(rule("missing",PolicyRuleEffect.DENY,condition,null)),Map.of()).decision());
    }
    @Test void expressionGrammarIsBoundedTypedAndRejectsHiddenErrors() {
        var x=new AuthorizationExpressionEvaluator();
        var facts=Map.<String,Object>of("subject.id","u","subject.level",new java.math.BigDecimal("2.0"),"subject.groups",List.of("a","b"));
        assertEquals(AuthorizationExpressionEvaluator.Result.MATCH,x.evaluate("{\"op\":\"eq\",\"attribute\":\"subject.level\",\"type\":\"NUMBER\",\"value\":2}",facts));
        assertEquals(AuthorizationExpressionEvaluator.Result.MATCH,x.evaluate("{\"op\":\"in\",\"attribute\":\"subject.groups\",\"type\":\"STRING\",\"value\":[\"b\"]}",facts));
        for(String invalid:List.of("{}","{\"op\":\"script\"}","{\"op\":\"exists\",\"op\":\"exists\",\"attribute\":\"subject.id\"}",
                "{\"op\":\"eq\",\"attribute\":\"subject.id\",\"type\":\"NUMBER\",\"value\":2}",
                "{\"op\":\"any\",\"args\":[{\"op\":\"exists\",\"attribute\":\"subject.id\"},{}]}"," ".repeat(8193)))
            assertEquals(AuthorizationExpressionEvaluator.Result.INDETERMINATE,x.evaluate(invalid,facts),invalid);
        String deep="{\"op\":\"exists\",\"attribute\":\"subject.id\"}";
        for(int i=0;i<20;i++) deep="{\"op\":\"not\",\"arg\":"+deep+"}";
        assertEquals(AuthorizationExpressionEvaluator.Result.INDETERMINATE,x.evaluate(deep,facts));
    }
    @Test void unresolvedGraphPreservesRealExplicitDenyEvidence() {
        var decision=evaluator.evaluate(request,new AuthorizationEvidence("EVIDENCE_INDETERMINATE",List.of(grant(GrantEffect.DENY)),List.of(),Map.of()),at);
        assertEquals(AuthorizationDecisionValue.DENY,decision.decision());
        assertEquals("[]",decision.matchedPolicyRuleIds());
    }
    @Test void optionalPersistenceChangesOnlySaving() {
        int[] saves={0};
        AuthorizationDecisionRepositoryPort repository=new AuthorizationDecisionRepositoryPort() {
            public AuthorizationDecision save(AuthorizationDecision d) { saves[0]++; return d; }
            public Optional<AuthorizationDecision> findById(String id) { return Optional.empty(); }
        };
        var evidence=new AuthorizationEvidence(null,List.of(grant(GrantEffect.GRANT)),List.of(),Map.of());
        var query=new EvaluatePermissionQuery("u","pipeline:read","PIPELINE","p",AuthorizationScope.global());
        for(boolean persist:List.of(false,true)) {
            var service=new IdentityAuthorizationApplicationService(repository,evaluator,(r,t,a)->evidence,u->null,()->persist);
            assertTrue(service.evaluate(query).permitted());
            assertEquals(persist?1:0,saves[0]);
        }
    }
    @Test void failureReasonsDoNotPretendThatGrantsWereEvaluated() {
        var decision=evaluator.evaluate(request,new AuthorizationEvidence("USER_INELIGIBLE",List.of(),List.of(),Map.of()),at);
        assertEquals("USER_INELIGIBLE",decision.reasonCode());
        assertEquals(AuthorizationDecisionValue.DENY,decision.decision());
    }
}
