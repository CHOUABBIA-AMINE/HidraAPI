/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationPolicyRule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Defines one ABAC rule inside a policy version.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Defines one ABAC rule inside a policy version.
 *
     * @param id id
 * @param policyVersionId policyVersionId
 * @param ruleCode ruleCode
 * @param effect effect
 * @param priority priority
 * @param subjectExpression subjectExpression
 * @param resourceExpression resourceExpression
 * @param actionExpression actionExpression
 * @param contextExpression contextExpression
 * @param obligationExpression obligationExpression
 * @param status status
 * @param createdAt createdAt
 */
public record AuthorizationPolicyRule(
        String id,
    String policyVersionId,
    String ruleCode,
    PolicyRuleEffect effect,
    int priority,
    String subjectExpression,
    String resourceExpression,
    String actionExpression,
    String contextExpression,
    String obligationExpression,
    PolicyRuleStatus status,
    Instant createdAt
) {

    public AuthorizationPolicyRule {
    id = normalize(id);
    policyVersionId = normalize(policyVersionId);
    ruleCode = normalize(ruleCode);
    subjectExpression = normalize(subjectExpression);
    resourceExpression = normalize(resourceExpression);
    actionExpression = normalize(actionExpression);
    contextExpression = normalize(contextExpression);
    obligationExpression = normalize(obligationExpression);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
