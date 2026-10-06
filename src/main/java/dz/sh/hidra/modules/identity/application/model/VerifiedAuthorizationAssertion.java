/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : VerifiedAuthorizationAssertion
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.model
 *
 * @Description : Resolves bounded and explainable Identity authorization evidence.
 *
 */
package dz.sh.hidra.modules.identity.application.model;

import java.time.Instant;
import java.util.*;

/** Populated only from authenticated infrastructure; never bound from an authorization request. */
public record VerifiedAuthorizationAssertion(String userId, String providerId, String externalIdentityId,
        String subject, Instant expiresAt, Map<String,Set<String>> claims) {
    public VerifiedAuthorizationAssertion {
        Objects.requireNonNull(userId); Objects.requireNonNull(providerId);
        Objects.requireNonNull(externalIdentityId); Objects.requireNonNull(subject);
        Map<String,Set<String>> copy=new TreeMap<>();
        if(claims.size()>64) throw new IllegalArgumentException("Too many authorization claims");
        claims.forEach((k,v)-> {
            if(k.length()>255 || v.size()>256 || v.stream().anyMatch(s->s.length()>1024)) throw new IllegalArgumentException("Authorization claim size");
            copy.put(k,Set.copyOf(v));
        });
        claims=Map.copyOf(copy);
    }
}
