/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringAuthorizationContextAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Resolves bounded and explainable Identity authorization evidence.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationAssertionPort;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationDecisionSettingsPort;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.ExternalIdentityJpaEntity;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.*;

@Component
public class SpringAuthorizationContextAdapter implements AuthorizationAssertionPort,AuthorizationDecisionSettingsPort {
    private final EntityManager em;
    private final boolean persist;
    public SpringAuthorizationContextAdapter(EntityManager em,
            @Value("${hidra.identity.authorization-decision-persistence-enabled:true}") boolean persist) { this.em=em; this.persist=persist; }
    @Override public boolean persistenceEnabled() { return persist; }
    @Override public VerifiedAuthorizationAssertion currentFor(String userId) {
        var auth=SecurityContextHolder.getContext().getAuthentication();
        if(auth==null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof HidraPrincipal p) || !userId.equals(p.userId())) return null;
        if(auth instanceof HidraOidcAuthenticationToken oidc && oidc.authorizationAssertion()!=null) return oidc.authorizationAssertion();
        // Verified local/LDAP authentication transports no live claims. Stored external snapshots are never read.
        if(p.externalIdentityId()==null || p.identityProviderId()==null) return null;
        ExternalIdentityJpaEntity e=em.find(ExternalIdentityJpaEntity.class,p.externalIdentityId());
        if(e==null) return null;
        return new VerifiedAuthorizationAssertion(p.userId(),p.identityProviderId(),e.id(),e.externalSubject(),null,Map.of());
    }
    public VerifiedAuthorizationAssertion capture(HidraPrincipal p,Jwt jwt) {
        Set<String> names=new TreeSet<>();
        for(String entity:List.of("ExternalGroupMappingJpaEntity","ExternalRoleMappingJpaEntity","ExternalPermissionMappingJpaEntity")) {
            names.addAll(em.createQuery("select distinct m.claimName from "+entity+" m where m.identityProviderId = :provider and m.claimName is not null",String.class)
                    .setParameter("provider",p.identityProviderId()).getResultList());
        }
        Map<String,Set<String>> claims=new TreeMap<>();
        for(String name:names) {
            Object value=jwt.getClaims();
            for(String part:name.split("\\.")) { value=value instanceof Map<?,?> m?m.get(part):null; }
            Set<String> values=new TreeSet<>();
            if(value instanceof String s) values.add(s);
            else if(value instanceof Collection<?> list) for(Object v:list) {
                if(!(v instanceof String s)) { values.clear(); break; } values.add(s);
            }
            if(!values.isEmpty()) claims.put(name,values);
        }
        return new VerifiedAuthorizationAssertion(p.userId(),p.identityProviderId(),p.externalIdentityId(),jwt.getSubject(),jwt.getExpiresAt(),claims);
    }
}
