/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationEvidence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.policy
 *
 * @Description : Resolves bounded and explainable Identity authorization evidence.
 *
 */
package dz.sh.hidra.modules.identity.domain.policy;

import java.util.List;
import java.util.Map;
import dz.sh.hidra.modules.identity.domain.value.GrantEffect;
import dz.sh.hidra.modules.identity.domain.value.PolicyRuleEffect;

public record AuthorizationEvidence(String failure, List<Grant> grants, List<Rule> rules,
                                    Map<String, Object> attributes) {
    public AuthorizationEvidence {
        grants = List.copyOf(grants);
        rules = List.copyOf(rules);
        attributes = Map.copyOf(attributes);
    }
    public record Grant(int order, GrantEffect effect, List<String> ids, String condition,
                        List<Map<String, String>> externalEvidence) {
        public Grant { ids = List.copyOf(ids); externalEvidence = List.copyOf(externalEvidence); }
    }
    public record Rule(String id, int priority, PolicyRuleEffect effect, List<String> expressions,
                       String obligation) {
        public Rule { expressions = java.util.Collections.unmodifiableList(new java.util.ArrayList<>(expressions)); }
    }
}
