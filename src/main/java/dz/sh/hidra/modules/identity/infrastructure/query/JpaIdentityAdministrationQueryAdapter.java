package dz.sh.hidra.modules.identity.infrastructure.query;

import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.domain.value.GrantEffect;
import dz.sh.hidra.modules.identity.domain.value.GrantStatus;
import dz.sh.hidra.modules.identity.domain.value.PermissionStatus;
import dz.sh.hidra.modules.identity.domain.value.RoleStatus;
import dz.sh.hidra.modules.identity.domain.value.RoleType;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.PermissionJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.RoleJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.RolePermissionGrantJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserPermissionGrantJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserRoleGrantJpaEntity;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class JpaIdentityAdministrationQueryAdapter implements IdentityAdministrationQueryUseCase {
    private final EntityManager entityManager;
    public JpaIdentityAdministrationQueryAdapter(EntityManager entityManager) { this.entityManager = Objects.requireNonNull(entityManager); }

    @Override public Page<UserView> users(String q, int page, int size) { return page(entityManager.createQuery("select e from UserJpaEntity e order by e.username", UserJpaEntity.class).getResultList().stream().filter(e -> matches(q,e.username(),e.emailAddress(),e.displayName())).map(this::userView).toList(),page,size); }
    @Override public UserView user(String id) { UserJpaEntity e=entityManager.find(UserJpaEntity.class,id); if(e==null) throw new IllegalArgumentException("Unknown identity user: "+id); return userView(e); }
    @Override public Page<RoleView> roles(String q,int page,int size) { return page(entityManager.createQuery("select e from RoleJpaEntity e order by e.code",RoleJpaEntity.class).getResultList().stream().filter(e->matches(q,e.code(),e.nameFr(),e.nameEn(),e.nameAr())).map(this::roleView).toList(),page,size); }
    @Override public Page<PermissionView> permissions(String q,int page,int size) { return page(entityManager.createQuery("select e from PermissionJpaEntity e order by e.code",PermissionJpaEntity.class).getResultList().stream().filter(e->matches(q,e.code(),e.permissionDomain(),e.resourceType(),e.action())).map(this::permissionView).toList(),page,size); }

    @Override public PrincipalView principal(String subject,List<String> authorities) {
        UserJpaEntity user=entityManager.createQuery("select e from UserJpaEntity e where e.username=:s or e.id=:s",UserJpaEntity.class).setParameter("s",subject).getResultStream().findFirst().orElse(null);
        if(user==null) return new PrincipalView(subject,"AUTHENTICATION",null,null,null,null,List.copyOf(authorities),authorities.stream().filter(a->a.startsWith("HIDRA_")).sorted().toList());
        Map<String,String> permissionCodes=new LinkedHashMap<>(); entityManager.createQuery("select e from PermissionJpaEntity e",PermissionJpaEntity.class).getResultList().forEach(p->permissionCodes.put(p.id(),p.code()));
        Instant now=Instant.now(); Set<String> allow=new LinkedHashSet<>(); Set<String> deny=new LinkedHashSet<>();
        entityManager.createQuery("select e from UserPermissionGrantJpaEntity e where e.userId=:u",UserPermissionGrantJpaEntity.class).setParameter("u",user.id()).getResultList().stream().filter(g->active(g.status(),g.validFrom(),g.validTo(),now)).forEach(g->apply(permissionCodes.get(g.permissionId()),g.effect(),allow,deny));
        List<String> roleIds=entityManager.createQuery("select e from UserRoleGrantJpaEntity e where e.userId=:u",UserRoleGrantJpaEntity.class).setParameter("u",user.id()).getResultList().stream().filter(g->active(g.status(),g.validFrom(),g.validTo(),now)).map(UserRoleGrantJpaEntity::roleId).toList();
        if(!roleIds.isEmpty()) entityManager.createQuery("select e from RolePermissionGrantJpaEntity e where e.roleId in :r",RolePermissionGrantJpaEntity.class).setParameter("r",roleIds).getResultList().stream().filter(g->active(g.status(),g.validFrom(),g.validTo(),now)).forEach(g->apply(permissionCodes.get(g.permissionId()),g.effect(),allow,deny));
        allow.removeAll(deny); authorities.stream().filter(a->a.startsWith("HIDRA_")).forEach(allow::add);
        return new PrincipalView(subject,"IDENTITY_USER",user.id(),user.username(),user.displayName(),user.employeeReferenceId(),List.copyOf(authorities),allow.stream().sorted().toList());
    }

    @Override @Transactional public RoleView createRole(CreateRoleCommand c) { Instant now=Instant.now(); RoleJpaEntity e=new RoleJpaEntity(UUID.randomUUID().toString(),c.code(),c.nameAr(),c.nameFr(),c.nameEn(),c.description(),RoleType.valueOf(c.roleType()),RoleStatus.valueOf(c.status()),now,now); entityManager.persist(e); return roleView(e); }
    @Override @Transactional public PermissionView createPermission(CreatePermissionCommand c) { Instant now=Instant.now(); PermissionJpaEntity e=new PermissionJpaEntity(UUID.randomUUID().toString(),c.code(),c.nameAr(),c.nameFr(),c.nameEn(),c.description(),c.permissionDomain(),c.resourceType(),c.action(),c.sensitive(),PermissionStatus.valueOf(c.status()),now,now); entityManager.persist(e); return permissionView(e); }
    @Override @Transactional public String grantRoleToUser(UserRoleGrantCommand c) { String id=UUID.randomUUID().toString(); Instant now=Instant.now(); entityManager.persist(new UserRoleGrantJpaEntity(id,c.userId(),c.roleId(),scope(c.scopeType()),c.scopeReferenceId(),c.scopeCodeSnapshot(),c.reason(),c.approvedByWorkflowId(),orNow(c.validFrom(),now),c.validTo(),GrantStatus.ACTIVE,now,null,null)); return id; }
    @Override @Transactional public String grantPermissionToRole(RolePermissionGrantCommand c) { String id=UUID.randomUUID().toString(); Instant now=Instant.now(); entityManager.persist(new RolePermissionGrantJpaEntity(id,c.roleId(),c.permissionId(),GrantEffect.valueOf(c.effect()),c.conditionExpression(),orNow(c.validFrom(),now),c.validTo(),GrantStatus.ACTIVE,now)); return id; }
    @Override @Transactional public String grantPermissionToUser(UserPermissionGrantCommand c) { String id=UUID.randomUUID().toString(); Instant now=Instant.now(); entityManager.persist(new UserPermissionGrantJpaEntity(id,c.userId(),c.permissionId(),GrantEffect.valueOf(c.effect()),scope(c.scopeType()),c.scopeReferenceId(),c.scopeCodeSnapshot(),c.reason(),c.approvedByWorkflowId(),c.emergencyAccess(),orNow(c.validFrom(),now),c.validTo(),GrantStatus.ACTIVE,now,null)); return id; }

    private UserView userView(UserJpaEntity e){return new UserView(e.id(),e.username(),e.emailAddress(),e.displayName(),name(e.userType()),name(e.status()),e.employeeReferenceId());}
    private RoleView roleView(RoleJpaEntity e){return new RoleView(e.id(),e.code(),e.nameAr(),e.nameFr(),e.nameEn(),e.description(),name(e.roleType()),name(e.status()));}
    private PermissionView permissionView(PermissionJpaEntity e){return new PermissionView(e.id(),e.code(),e.nameAr(),e.nameFr(),e.nameEn(),e.description(),e.permissionDomain(),e.resourceType(),e.action(),e.sensitive(),name(e.status()));}
    private static void apply(String code,GrantEffect effect,Set<String> allow,Set<String> deny){if(code==null)return;if("DENY".equals(name(effect)))deny.add(code);else allow.add(code);}
    private static boolean active(Object status,Instant from,Instant to,Instant now){return "ACTIVE".equals(name(status))&&!from.isAfter(now)&&(to==null||to.isAfter(now));}
    private static ScopeType scope(String value){return value==null||value.isBlank()?null:ScopeType.valueOf(value);}
    private static Instant orNow(Instant value,Instant now){return value==null?now:value;}
    private static String name(Object v){return v==null?null:v.toString();}
    private static boolean matches(String q,Object...v){if(q==null||q.isBlank())return true;String n=q.toLowerCase(Locale.ROOT);for(Object x:v)if(x!=null&&x.toString().toLowerCase(Locale.ROOT).contains(n))return true;return false;}
    private static <T> Page<T> page(List<T> all,int requestedPage,int requestedSize){int p=Math.max(0,requestedPage);int s=Math.min(200,Math.max(1,requestedSize));int from=Math.min(all.size(),p*s);int to=Math.min(all.size(),from+s);return new Page<>(List.copyOf(all.subList(from,to)),p,s,all.size(),all.isEmpty()?0:(all.size()+s-1)/s,to<all.size());}
}
