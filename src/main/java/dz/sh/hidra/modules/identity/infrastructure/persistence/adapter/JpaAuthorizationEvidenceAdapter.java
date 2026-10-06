/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuthorizationEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Resolves bounded and explainable Identity authorization evidence.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.AuthorizationEvidencePort;
import dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion;
import dz.sh.hidra.modules.identity.domain.policy.*;
import dz.sh.hidra.modules.identity.domain.value.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.*;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.math.BigDecimal;
import java.util.*;

/** Reads Identity-owned evidence at one instant; no snapshot or cross-module object establishes authority. */
@Repository
public class JpaAuthorizationEvidenceAdapter implements AuthorizationEvidencePort {
    private final EntityManager em;
    public JpaAuthorizationEvidenceAdapter(EntityManager em) { this.em=Objects.requireNonNull(em); }

    @Override
    @Transactional(readOnly=true)
    public AuthorizationEvidence load(AuthorizationEvaluationRequest r,Instant at,VerifiedAuthorizationAssertion assertion) {
        UserJpaEntity user=em.find(UserJpaEntity.class,r.userId());
        if(user==null || user.status()!=UserStatus.ACTIVE || (user.lockedUntil()!=null && at.isBefore(user.lockedUntil()))) return failure("USER_INELIGIBLE");
        List<PermissionJpaEntity> permissions=rows(PermissionJpaEntity.class,"code",r.permissionCode());
        if(permissions.size()!=1 || permissions.get(0).status()!=PermissionStatus.ACTIVE) return failure("PERMISSION_INELIGIBLE");
        PermissionJpaEntity p=permissions.get(0);
        if(r.resourceType()!=null && !Objects.equals(p.resourceType(),r.resourceType())) return failure("PERMISSION_INELIGIBLE");
        if(r.scope()!=null && r.scope().scopeType()!=ScopeType.GLOBAL && r.scope().scopeReferenceId()==null) return failure("EVIDENCE_INDETERMINATE");
        if(assertion!=null && !eligible(assertion,r.userId(),at)) return failure("EXTERNAL_IDENTITY_INELIGIBLE");
        List<AuthorizationEvidence.Grant> grants=new ArrayList<>();
        List<AuthorizationEvidence.Rule> rules=new ArrayList<>();
        Map<String,Object> facts=new HashMap<>();
        facts.put("subject.id",user.id()); facts.put("subject.type",user.userType().name()); facts.put("subject.status",user.status().name());
        facts.put("action.permissionCode",p.code()); facts.put("action.name",p.action()); if(p.resourceType()!=null) facts.put("resource.type",p.resourceType());
        if(r.resourceReferenceId()!=null) facts.put("resource.referenceId",r.resourceReferenceId());
        facts.put("context.evaluatedAt",at.toString());
        facts.put("context.scopeType",r.scope()==null?"GLOBAL":r.scope().scopeType().name());
        if(r.scope()!=null && r.scope().scopeReferenceId()!=null) facts.put("context.scopeReferenceId",r.scope().scopeReferenceId());
        for(var g:rows(UserPermissionGrantJpaEntity.class,"userId",user.id())) {
            if(Objects.equals(g.permissionId(),p.id()) && g.status()==GrantStatus.ACTIVE && window(g.validFrom(),g.validTo(),at)
                    && covers(g.scopeType(),g.scopeReferenceId(),r))
                grants.add(new AuthorizationEvidence.Grant(0,g.effect(),List.of(g.id()),null,List.of()));
        }
        for(var g:rows(UserRoleGrantJpaEntity.class,"userId",user.id())) {
            if(g.status()==GrantStatus.ACTIVE && window(g.validFrom(),g.validTo(),at) && covers(g.scopeType(),g.scopeReferenceId(),r))
                role(grants,g.roleId(),p.id(),at,1,List.of(g.id()),List.of());
        }
        Set<String> groups=new TreeSet<>();
        for(var m:rows(UserGroupMembershipJpaEntity.class,"userId",user.id())) {
            if(m.status()!=MembershipStatus.ACTIVE || !window(m.validFrom(),m.validTo(),at) || !membershipSourceEligible(m)) continue;
            List<String> chain=m.sourceMappingId()==null?List.of(m.id()):List.of(m.id(),m.sourceMappingId());
            List<Map<String,String>> provenance=m.sourceMappingId()==null?List.of():List.of(Map.of("providerId",m.sourceProviderId(),"mappingId",m.sourceMappingId(),"source","SYNC_MEMBERSHIP"));
            if(group(grants,m.groupId(),p.id(),r,at,2,chain,provenance)) groups.add(m.groupId());
        }
        if(assertion!=null) {
            for(var m:rows(ExternalGroupMappingJpaEntity.class,"identityProviderId",assertion.providerId())) {
                if(m.status()!=ExternalMappingStatus.ACTIVE || m.mappingMode()!=ExternalMappingMode.ASSERTION_ONLY) continue;
                Set<String> values=assertion.claims().getOrDefault(Objects.toString(m.claimName(),""),Set.of());
                String matched=firstMatch(values,m.externalGroupId(),m.externalGroupName(),m.externalGroupDn());
                if(matched!=null && group(grants,m.groupId(),p.id(),r,at,3,List.of(m.id()),proof(assertion,m.id(),m.claimName(),matched))) groups.add(m.groupId());
            }
            for(var m:rows(ExternalRoleMappingJpaEntity.class,"identityProviderId",assertion.providerId())) {
                if(m.status()==ExternalMappingStatus.ACTIVE && m.mappingMode()==ExternalMappingMode.DIRECT_GRANT
                        && contains(assertion,m.claimName(),m.externalRoleCode()) && covers(m.scopeType(),m.scopeReferenceId(),r))
                    role(grants,m.roleId(),p.id(),at,4,List.of(m.id()),proof(assertion,m.id(),m.claimName(),m.externalRoleCode()));
            }
            for(var m:rows(ExternalPermissionMappingJpaEntity.class,"identityProviderId",assertion.providerId())) {
                if(m.status()==ExternalMappingStatus.ACTIVE && m.mappingMode()==ExternalMappingMode.DIRECT_GRANT
                        && Objects.equals(m.permissionId(),p.id()) && contains(assertion,m.claimName(),m.externalPermissionCode()))
                    grants.add(new AuthorizationEvidence.Grant(5,m.effect(),List.of(m.id()),null,proof(assertion,m.id(),m.claimName(),m.externalPermissionCode())));
            }
        }
        facts.put("subject.groupIds",List.copyOf(groups));
        boolean unresolved=false;
        try {
            attributes(facts,SubjectAttributeOwnerType.USER,user.id(),at);
            for(String id:groups) attributes(facts,SubjectAttributeOwnerType.GROUP,id,at);
            if(assertion!=null) attributes(facts,SubjectAttributeOwnerType.EXTERNAL_IDENTITY,assertion.externalIdentityId(),at);
        } catch(IllegalArgumentException ex) { unresolved=true; }
        for(var policy:rows(AuthorizationPolicyJpaEntity.class,"policyDomain",p.permissionDomain())) {
            if(policy.status()!=PolicyStatus.ACTIVE) continue;
            List<AuthorizationPolicyVersionJpaEntity> versions=rows(AuthorizationPolicyVersionJpaEntity.class,"policyId",policy.id()).stream()
                    .filter(v->v.status()==PolicyVersionStatus.ACTIVE && window(v.effectiveFrom(),v.effectiveTo(),at)).toList();
            if(versions.size()!=1) { unresolved=true; continue; }
            for(var rule:rows(AuthorizationPolicyRuleJpaEntity.class,"policyVersionId",versions.get(0).id())) {
                if(rule.status()==PolicyRuleStatus.ACTIVE) rules.add(new AuthorizationEvidence.Rule(rule.id(),rule.priority(),rule.effect(),
                        Arrays.asList(rule.subjectExpression(),rule.resourceExpression(),rule.actionExpression(),rule.contextExpression()),rule.obligationExpression()));
            }
        }
        // Preserve explicit denials while marking unresolved graph/attribute evidence.
        return new AuthorizationEvidence(unresolved?"EVIDENCE_INDETERMINATE":null,grants,rules,facts);
    }
    private boolean eligible(VerifiedAuthorizationAssertion a,String user,Instant at) {
        if((!a.claims().isEmpty() && a.expiresAt()==null) || !a.userId().equals(user) || (a.expiresAt()!=null && !at.isBefore(a.expiresAt()))) return false;
        ExternalIdentityJpaEntity e=em.find(ExternalIdentityJpaEntity.class,a.externalIdentityId());
        IdentityProviderJpaEntity provider=em.find(IdentityProviderJpaEntity.class,a.providerId());
        return e!=null && provider!=null && provider.status()==IdentityProviderStatus.ACTIVE && e.status()==ExternalIdentityStatus.LINKED
                && Objects.equals(e.userId(),user) && Objects.equals(e.identityProviderId(),a.providerId()) && Objects.equals(e.externalSubject(),a.subject());
    }
    private boolean membershipSourceEligible(UserGroupMembershipJpaEntity m) {
        if(m.membershipType()==MembershipType.EXTERNAL_ASSERTION) return false;
        if(m.sourceProviderId()==null) return m.sourceMappingId()==null && m.membershipType()!=MembershipType.EXTERNAL_SYNC;
        IdentityProviderJpaEntity provider=em.find(IdentityProviderJpaEntity.class,m.sourceProviderId());
        ExternalGroupMappingJpaEntity mapping=m.sourceMappingId()==null?null:em.find(ExternalGroupMappingJpaEntity.class,m.sourceMappingId());
        return provider!=null && provider.status()==IdentityProviderStatus.ACTIVE && mapping!=null
                && mapping.status()==ExternalMappingStatus.ACTIVE && mapping.mappingMode()==ExternalMappingMode.SYNC_MEMBERSHIP
                && Objects.equals(mapping.identityProviderId(),provider.id()) && Objects.equals(mapping.groupId(),m.groupId())
                && rows(ExternalIdentityJpaEntity.class,"userId",m.userId()).stream().anyMatch(e ->
                    e.status()==ExternalIdentityStatus.LINKED && Objects.equals(e.identityProviderId(),provider.id()));
    }
    private boolean group(List<AuthorizationEvidence.Grant> target,String id,String permission,AuthorizationEvaluationRequest r,
                          Instant at,int order,List<String> chain,List<Map<String,String>> proof) {
        GroupJpaEntity group=em.find(GroupJpaEntity.class,id);
        if(group==null || group.status()!=GroupStatus.ACTIVE) return false;
        for(var g:rows(GroupRoleGrantJpaEntity.class,"groupId",id)) {
            if(g.status()==GrantStatus.ACTIVE && window(g.validFrom(),g.validTo(),at) && covers(g.scopeType(),g.scopeReferenceId(),r))
                role(target,g.roleId(),permission,at,order,append(chain,g.id()),proof);
        }
        return true;
    }
    private void role(List<AuthorizationEvidence.Grant> target,String id,String permission,Instant at,int order,
                      List<String> chain,List<Map<String,String>> proof) {
        RoleJpaEntity role=em.find(RoleJpaEntity.class,id);
        if(role==null || role.status()!=RoleStatus.ACTIVE) return;
        for(var g:rows(RolePermissionGrantJpaEntity.class,"roleId",id)) {
            if(Objects.equals(g.permissionId(),permission) && g.status()==GrantStatus.ACTIVE && window(g.validFrom(),g.validTo(),at))
                target.add(new AuthorizationEvidence.Grant(order,g.effect(),append(chain,g.id()),g.conditionExpression(),proof));
        }
    }
    private void attributes(Map<String,Object> facts,SubjectAttributeOwnerType type,String id,Instant at) {
        for(var a:rows(SubjectSecurityAttributeJpaEntity.class,"subjectId",id)) {
            if(a.subjectType()!=type || a.status()!=PermissionStatus.ACTIVE || (a.validFrom()!=null && at.isBefore(a.validFrom())) || (a.validTo()!=null && !at.isBefore(a.validTo()))) continue;
            AttributeDefinitionJpaEntity d=em.find(AttributeDefinitionJpaEntity.class,a.attributeDefinitionId());
            if(d==null || d.status()!=PermissionStatus.ACTIVE || !d.attributeTarget().name().equals(type.name())) continue;
            if(a.sourceProviderId()!=null) {
                IdentityProviderJpaEntity source=em.find(IdentityProviderJpaEntity.class,a.sourceProviderId());
                if(source==null || source.status()!=IdentityProviderStatus.ACTIVE) throw new IllegalArgumentException("Attribute source");
            }
            Object value=typed(d,a.attributeValue());
            if(value==null) throw new IllegalArgumentException("Null subject attribute");
            String key="subject.attributes."+d.code();
            Object previous=facts.putIfAbsent(key,value);
            if(previous!=null && !previous.equals(value)) throw new IllegalArgumentException("Conflicting subject attributes");
        }
    }
    private Object typed(AttributeDefinitionJpaEntity d,String value) {
        if(value==null) throw new IllegalArgumentException("Absent attribute value");
        if(d.multiValued() || d.dataType()==AttributeDataType.LIST) {
            Object parsed=AuthorizationJson.parse(value);
            if(!(parsed instanceof List<?> list) || list.stream().anyMatch(v-> !(v instanceof String || v instanceof Boolean || v instanceof BigDecimal)))
                throw new IllegalArgumentException("Attribute list");
            return List.copyOf(list);
        }
        return switch(d.dataType()) {
            case STRING -> value;
            case NUMBER -> new BigDecimal(value).stripTrailingZeros();
            case BOOLEAN -> { if(!value.equals("true") && !value.equals("false")) throw new IllegalArgumentException("Attribute boolean"); yield Boolean.valueOf(value); }
            case DATE -> date(value);
            case DATETIME -> dateTime(value);
            case JSON -> AuthorizationJson.parse(value);
            case LIST -> throw new IllegalArgumentException("Attribute list");
        };
    }
    private String date(String value) { try { return java.time.LocalDate.parse(value).toString(); }
        catch(java.time.DateTimeException ex) { throw new IllegalArgumentException("Invalid date attribute",ex); } }
    private String dateTime(String value) { try { return Instant.parse(value).toString(); }
        catch(java.time.DateTimeException ex) { throw new IllegalArgumentException("Invalid datetime attribute",ex); } }
    private <T> List<T> rows(Class<T> type,String field,String value) {
        return em.createQuery("select e from "+type.getSimpleName()+" e where e."+field+" = :value order by e.id",type)
                .setParameter("value",value).getResultList();
    }
    private AuthorizationEvidence failure(String code) { return new AuthorizationEvidence(code,List.of(),List.of(),Map.of()); }
    private boolean window(Instant from,Instant to,Instant at) { return from!=null && !at.isBefore(from) && (to==null || at.isBefore(to)); }
    private boolean covers(ScopeType type,String ref,AuthorizationEvaluationRequest r) {
        if(type==null || type==ScopeType.GLOBAL) return ref==null;
        return ref!=null && r.scope()!=null && r.scope().scopeType()==type && ref.equals(r.scope().scopeReferenceId());
    }
    private List<String> append(List<String> values,String id) { List<String> result=new ArrayList<>(values); result.add(id); return result; }
    private boolean contains(VerifiedAuthorizationAssertion a,String claim,String value) { return claim!=null && value!=null && a.claims().getOrDefault(claim,Set.of()).contains(value); }
    private String firstMatch(Set<String> values,String... keys) { for(String key:keys) if(key!=null && values.contains(key)) return key; return null; }
    private List<Map<String,String>> proof(VerifiedAuthorizationAssertion a,String mapping,String claim,String value) {
        try {
            String hash=HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(value.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
            return List.of(Map.of("providerId",a.providerId(),"externalIdentityId",a.externalIdentityId(),"mappingId",mapping,"claimName",claim,"valueHash",hash));
        } catch(java.security.NoSuchAlgorithmException ex) { throw new IllegalStateException(ex); }
    }
}
