/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationPolicyEvaluator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.service
 *
 * @Description : Evaluates normalized authorization graphs with deny precedence and bounded policies.
 *
 */
package dz.sh.hidra.modules.identity.domain.service;

import dz.sh.hidra.modules.identity.domain.model.AuthorizationDecision;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import dz.sh.hidra.modules.identity.domain.value.IdentityId;

import java.time.Instant;
import java.util.*;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvidence;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationJson;
import dz.sh.hidra.modules.identity.domain.value.GrantEffect;
import dz.sh.hidra.modules.identity.domain.value.PolicyRuleEffect;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationExpressionEvaluator.Result;

/**
 * Domain service for explainable authorization decisions.
 *
 * <p>Infrastructure resolves the graph; this service evaluates its conditions and policies
 * without framework dependencies.</p>
 */
public class AuthorizationPolicyEvaluator {


    public AuthorizationDecision evaluate(AuthorizationEvaluationRequest request, AuthorizationEvidence evidence, Instant at) {
        AuthorizationExpressionEvaluator expressions=new AuthorizationExpressionEvaluator();
        Set<String> grants=new TreeSet<>(), rules=new TreeSet<>();
        Map<String,Map<String,String>> external=new TreeMap<>();
        boolean permit=false, deny=false, unknown="EVIDENCE_INDETERMINATE".equals(evidence.failure()), constrained=false;
        if(evidence.failure()!=null && !unknown) return explained(request,at,
                AuthorizationDecisionValue.DENY,
                evidence.failure(),grants,rules,external.values());
        List<AuthorizationEvidence.Grant> ordered=new ArrayList<>(evidence.grants());
        ordered.sort(Comparator.comparingInt(AuthorizationEvidence.Grant::order).thenComparing(g->g.ids().get(0)));
        for(var grant:ordered) {
            Result result=expressions.evaluate(grant.condition(),evidence.attributes());
            if(result==Result.NO_MATCH) continue;
            grants.addAll(grant.ids());
            grant.externalEvidence().forEach(e->external.put(AuthorizationJson.write(e),e));
            if(result==Result.INDETERMINATE || grant.effect()==null) unknown=true;
            else if(grant.effect()==GrantEffect.DENY) deny=true;
            else permit=true;
        }
        List<AuthorizationEvidence.Rule> orderedRules=new ArrayList<>(evidence.rules());
        orderedRules.sort(Comparator.comparingInt(AuthorizationEvidence.Rule::priority).thenComparing(AuthorizationEvidence.Rule::id));
        for(var rule:orderedRules) {
            boolean noMatch=false, unresolved=false, constraintFailed=false;
            for(int i=0;i<rule.expressions().size();i++) {
                String expression=rule.expressions().get(i);
                Result result=expressions.evaluate(expression,evidence.attributes());
                if(rule.effect()==PolicyRuleEffect.CONSTRAIN && i==3) constraintFailed=result==Result.NO_MATCH;
                else noMatch |= result==Result.NO_MATCH;
                unresolved |= result==Result.INDETERMINATE;
            }
            if(noMatch && !unresolved) continue;
            rules.add(rule.id());
            if(unresolved || rule.effect()==null) { unknown=true; continue; }
            if(rule.effect()==PolicyRuleEffect.DENY || constraintFailed) { deny=true; continue; }
            if(rule.obligation()!=null) { unknown=true; continue; }
            if(rule.effect()==PolicyRuleEffect.PERMIT) permit=true;
            if(rule.effect()==PolicyRuleEffect.CONSTRAIN) constrained=true;
        }
        // DENY wins even when another path is unresolved; unresolved permit/constraint evidence never grants authority.
        AuthorizationDecisionValue value=deny?AuthorizationDecisionValue.DENY:unknown?AuthorizationDecisionValue.INDETERMINATE:
                permit?AuthorizationDecisionValue.PERMIT:AuthorizationDecisionValue.DENY;
        String reason=deny?"EXPLICIT_DENY":unknown?"EVIDENCE_INDETERMINATE":permit?
                (constrained?"PERMIT_WITH_CONSTRAINTS":"AUTHORITY_MATCHED"):"NO_GRANT_MATCHED";
        return explained(request,at,value,reason,grants,rules,external.values());
    }
    private AuthorizationDecision explained(AuthorizationEvaluationRequest r,Instant at,AuthorizationDecisionValue value,
            String reason,Set<String> grants,Set<String> rules,Collection<Map<String,String>> external) {
        String message=switch(reason) {
            case "EXPLICIT_DENY" -> "An active grant or policy explicitly denied the operation.";
            case "EVIDENCE_INDETERMINATE" -> "Required policy or authorization evidence could not be resolved.";
            case "AUTHORITY_MATCHED", "PERMIT_WITH_CONSTRAINTS" -> "Active authorization evidence permits the requested operation.";
            case "NO_GRANT_MATCHED" -> "No eligible grant or policy permitted the requested operation.";
            case "USER_INELIGIBLE" -> "The user is absent, inactive or locked.";
            case "PERMISSION_INELIGIBLE" -> "The permission is absent, inactive or does not cover the resource type.";
            case "EXTERNAL_IDENTITY_INELIGIBLE" -> "The verified external identity or provider is no longer eligible.";
            default -> "Authorization evidence did not permit the requested operation.";
        };
        return new AuthorizationDecision(IdentityId.newId().value(),r.userId(),r.permissionCode(),r.resourceType(),
                r.resourceReferenceId(),r.scope(),value,reason,message,AuthorizationJson.write(grants),AuthorizationJson.write(rules),
                AuthorizationJson.write(external),at,null,null);
    }

    public AuthorizationDecision permit(AuthorizationEvaluationRequest request, String reasonCode, String reasonMessage) {
        return decision(request, AuthorizationDecisionValue.PERMIT, reasonCode, reasonMessage);
    }

    public AuthorizationDecision deny(AuthorizationEvaluationRequest request, String reasonCode, String reasonMessage) {
        return decision(request, AuthorizationDecisionValue.DENY, reasonCode, reasonMessage);
    }

    private AuthorizationDecision decision(
            AuthorizationEvaluationRequest request,
            AuthorizationDecisionValue value,
            String reasonCode,
            String reasonMessage
    ) {
        return new AuthorizationDecision(
                IdentityId.newId().value(),
                request.userId(),
                request.permissionCode(),
                request.resourceType(),
                request.resourceReferenceId(),
                request.scope(),
                value,
                reasonCode,
                reasonMessage,
                null,
                null,
                null,
                Instant.now(),
                null,
                null
        );
    }
}
