/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInstalledConfigurationOperatorQualificationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Proves locked source qualification and fail-closed source drift on fresh synthetic PostgreSQL.
 *
 */
package dz.sh.hidra.modules.topology.qualification;

import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration.*;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.*;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.*;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker=true)
@org.springframework.boot.test.context.SpringBootTest(
    classes=dz.sh.hidra.HidraApplication.class,
    webEnvironment=org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK,
    properties={
        "hidra.platform.persistence.execution.installed-unit-work-budget-ms=10000",
        "hidra.platform.security.ldap.enabled=true",
        "hidra.platform.security.ldap.url=ldap://synthetic.invalid:389",
        "hidra.platform.security.ldap.base-dn=dc=synthetic,dc=invalid",
        "hidra.platform.security.ldap.user-search-base=ou=people",
        "hidra.platform.security.ldap.user-search-filter=(uid={0})",
        "hidra.platform.security.jwt.hmac-secret=synthetic-completion-test-key-32-bytes-only",
        "hidra.platform.security.jwt.token-issuer=hidra-api",
        "hidra.platform.security.jwt.audience=hidra-api"
    })
@org.springframework.test.context.ActiveProfiles("test")
@org.springframework.test.annotation.DirtiesContext(
    classMode=org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS)
class StationInstalledConfigurationOperatorQualificationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES =
        new PostgreSQLContainer<>("postgres:16").withCommand("postgres",
            "-c","max_locks_per_transaction=256","-c","track_commit_timestamp=on");
    private static volatile boolean migrated;

    private static final String APP="config_native_app",PUBLISHER="config_native_publisher";
    private static final String PASSWORD="synthetic-configuration-role-only";
    private static final String T="public.hidra_identity_";
    private static final String KEY="synthetic-completion-test-key-32-bytes-only";
    private static final String OIDC_SOURCE="https://oidc.synthetic.invalid";

    @org.springframework.test.context.DynamicPropertySource
    static void nativeDatabase(org.springframework.test.context.DynamicPropertyRegistry properties) {
        if(!POSTGRES.isRunning()) POSTGRES.start();
        Flyway.configure().dataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),
            POSTGRES.getPassword()).load().migrate();
        var owner=new JdbcTemplate(new DriverManagerDataSource(POSTGRES.getJdbcUrl(),
            POSTGRES.getUsername(),POSTGRES.getPassword()));
        owner.execute("REVOKE CREATE ON SCHEMA public FROM PUBLIC");
        for(String role:List.of(APP,PUBLISHER)) {
            owner.execute("CREATE ROLE "+role+" LOGIN PASSWORD '"+PASSWORD+"'");
            owner.execute("GRANT USAGE ON SCHEMA public TO "+role);
            owner.execute("GRANT SELECT ON ALL TABLES IN SCHEMA public TO "+role);
        }
        owner.execute("GRANT CREATE ON DATABASE "+POSTGRES.getDatabaseName()+" TO "+APP);
        for(String table:owner.queryForList(
                "SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname='public' "
                +"AND tablename LIKE 'hidra_identity_%'",String.class)) {
            if(!table.matches("hidra_identity_[a-z0-9_]+"))throw new IllegalStateException();
            owner.execute("GRANT INSERT,UPDATE,DELETE ON public."+table+" TO "+APP);
        }
        owner.execute("REVOKE INSERT,UPDATE,DELETE ON "
            +"public.hidra_identity_external_mapping_generation FROM "+APP);
        owner.execute("REVOKE SELECT ON public.hidra_topology_station_configuration_revision FROM "+APP);
        for(var relation:dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
                .JdbcIdentityAuthorityFence.requiredRelationFences()) {
            if(relation.mode()!=dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.ACCESS_SHARE)
                owner.execute("GRANT UPDATE ON "+relation.relation()+" TO "+PUBLISHER);
        }
        for(String relation:List.of("revision","member","request"))
            owner.execute("GRANT INSERT ON public.hidra_topology_station_configuration_"
                +relation+" TO "+PUBLISHER);
        owner.execute("GRANT INSERT,UPDATE ON "
            +"public.hidra_topology_station_configuration_head TO "+PUBLISHER);
        owner.execute("GRANT EXECUTE ON FUNCTION "
            +"public.hidra_station_configuration_lock_sources() TO "+PUBLISHER);
        properties.add("spring.datasource.url",POSTGRES::getJdbcUrl);
        properties.add("spring.datasource.username",()->APP);
        properties.add("spring.datasource.password",()->PASSWORD);
        properties.add("spring.flyway.url",POSTGRES::getJdbcUrl);
        properties.add("spring.flyway.user",POSTGRES::getUsername);
        properties.add("spring.flyway.password",POSTGRES::getPassword);
        properties.add("spring.flyway.enabled",()->true);
        properties.add("spring.jpa.hibernate.ddl-auto",()->"validate");
        properties.add("hidra.platform.persistence.publication.enabled",()->true);
        properties.add("hidra.platform.persistence.publication.username",()->PUBLISHER);
        properties.add("hidra.platform.persistence.publication.password",()->PASSWORD);
    }

    @org.springframework.beans.factory.annotation.Autowired
    dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase protectedService;
    @org.springframework.beans.factory.annotation.Autowired
    dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase direct;
    @org.springframework.beans.factory.annotation.Autowired
    dz.sh.hidra.modules.identity.application.port.in.CompleteAuthenticatedPrincipalUseCase completion;
    @org.springframework.beans.factory.annotation.Autowired
    org.springframework.security.crypto.password.PasswordEncoder passwords;
    @org.springframework.beans.factory.annotation.Autowired
    dz.sh.hidra.modules.identity.infrastructure.security.IdentityOidcJwtAuthenticationConverter oidc;
    @org.springframework.beans.factory.annotation.Autowired
    dz.sh.hidra.modules.identity.infrastructure.security.LdapAuthenticationProvider ldap;
    @org.springframework.beans.factory.annotation.Autowired
    dz.sh.hidra.modules.identity.infrastructure.security.TrustedAuthenticationVerificationRegistry verificationRegistry;
    @org.springframework.test.context.bean.override.mockito.MockitoBean
    dz.sh.hidra.modules.identity.application.port.out.LdapCredentialVerificationPort directory;
    @org.springframework.test.context.bean.override.mockito.MockitoBean(name="externalOidcJwtDecoder")
    org.springframework.security.oauth2.jwt.JwtDecoder externalDecoder;
    @org.springframework.beans.factory.annotation.Autowired
    JdbcTemplate nativeJdbc;
    @org.springframework.test.context.bean.override.mockito.MockitoSpyBean
    dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcStationInstalledConfigurationRepositoryAdapter nativeRepositorySpy;


    @org.springframework.beans.factory.annotation.Autowired
    org.springframework.web.context.WebApplicationContext securedWebContext;
    @org.springframework.test.context.bean.override.mockito.MockitoSpyBean
    org.springframework.transaction.PlatformTransactionManager guardedTransactionManager;
    private org.springframework.test.web.servlet.MockMvc securedMvc(){
        return org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup(securedWebContext)
            .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
            .build();
    }
    private static String issuedBearer(){
        return ((org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken)
            org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication()).getToken().getTokenValue();
    }

    private final org.springframework.security.oauth2.jwt.JwtEncoder sourceEncoder=
        org.springframework.security.oauth2.jwt.NimbusJwtEncoder.withSecretKey(
            new javax.crypto.spec.SecretKeySpec(KEY.getBytes(java.nio.charset.StandardCharsets.UTF_8),
                "HmacSHA256")).algorithm(org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256).build();
    private final org.springframework.security.oauth2.jwt.JwtDecoder verifiedExternal=
        org.springframework.security.oauth2.jwt.NimbusJwtDecoder.withSecretKey(
            new javax.crypto.spec.SecretKeySpec(KEY.getBytes(java.nio.charset.StandardCharsets.UTF_8),
                "HmacSHA256")).macAlgorithm(org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256).build();

    private record NativeActor(String user,String provider,String subject,String type) {}
    private static String syntheticId(){return UUID.randomUUID().toString();}
    private static org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
            ambient(String raw) {
        var wrapper=org.springframework.security.oauth2.jwt.Jwt.withTokenValue(raw)
            .header("alg","HS256").issuer("synthetic-wrapper").subject("synthetic-wrapper")
            .issuedAt(Instant.now().minusSeconds(1))
            .expiresAt(Instant.now().plusSeconds(3600)).build();
        var auth=new org.springframework.security.oauth2.server.resource.authentication
            .JwtAuthenticationToken(wrapper,List.of());
        auth.setAuthenticated(true);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }
    @org.junit.jupiter.api.BeforeEach
    void initializeNativeAuthentication() {
        org.springframework.security.core.context.SecurityContextHolder.clearContext();
        org.mockito.Mockito.when(externalDecoder.decode(org.mockito.ArgumentMatchers.anyString()))
            .thenAnswer(invocation->verifiedExternal.decode(invocation.getArgument(0)));
        org.springframework.test.util.ReflectionTestUtils.setField(oidc,
            "verifiedClaimReceiptEnabled",true);
    }
    @org.junit.jupiter.api.AfterEach
    void clearNativeAuthentication() {
        org.springframework.security.core.context.SecurityContextHolder.clearContext();
        org.springframework.test.util.ReflectionTestUtils.setField(oidc,
            "verifiedClaimReceiptEnabled",false);
        org.springframework.test.util.ReflectionTestUtils.setField(ldap,
            "verifiedClaimReceiptEnabled",false);
    }
    private NativeActor nativeActor(String type) {
        nativeJdbc.update("UPDATE "+T+"provider SET status='INACTIVE' WHERE status='ACTIVE'");
        String user=syntheticId(),provider=syntheticId(),link=syntheticId();
        String subject="  exact-synthetic-é-"+link+"  ";
        Timestamp now=Timestamp.from(Instant.now());
        nativeJdbc.update("INSERT INTO "+T+"user"
            +"(id,username,user_type,status,failed_login_count,created_at,updated_at)"
            +" VALUES (?,?,'HUMAN','ACTIVE',0,?,?)",user,user,now,now);
        nativeJdbc.update("INSERT INTO "+T+"provider"
            +"(id,code,name,provider_type,issuer_uri,directory_base_dn,user_search_base,status,"
            +"sync_enabled,just_in_time_provisioning_enabled,created_at,updated_at)"
            +" VALUES (?,?,'Synthetic configuration',?,?,?,?,'ACTIVE',false,false,?,?)",
            provider,provider,type,"OIDC".equals(type)?OIDC_SOURCE:null,
            "LDAP".equals(type)||"ACTIVE_DIRECTORY".equals(type)?"dc=synthetic,dc=invalid":null,
            "LDAP".equals(type)||"ACTIVE_DIRECTORY".equals(type)?"ou=people":null,now,now);
        if("LOCAL".equals(type)) {
            nativeJdbc.update("INSERT INTO "+T+"local_credential"
                +"(id,user_id,password_hash,credential_status,created_at,updated_at)"
                +" VALUES (?, ?,?,'ACTIVE',?,?)",syntheticId(),user,
                passwords.encode("synthetic-password"),now,now);
        }else{
            nativeJdbc.update("INSERT INTO "+T+"external_identity"
                +"(id,user_id,identity_provider_id,external_subject,status,created_at,updated_at)"
                +" VALUES (?,?,?,?,'LINKED',?,?)",link,user,provider,subject,now,now);
        }
        return new NativeActor(user,provider,subject,type);
    }
    private void directoryCredential(NativeActor actor) {
        var settings=new dz.sh.hidra.platform.configuration.HidraLdapSecurityProperties(
            true,"ldap://synthetic.invalid:389","dc=synthetic,dc=invalid","ou=people",
            "(uid={0})",null,null,null,null);
        var adapter=new dz.sh.hidra.modules.identity.infrastructure.security.LdapCredentialVerificationAdapter(
            org.mockito.Mockito.mock(org.springframework.ldap.core.support.LdapContextSource.class),
            settings,verificationRegistry);
        var ldapTemplate=org.mockito.Mockito.mock(org.springframework.ldap.core.LdapTemplate.class);
        org.springframework.test.util.ReflectionTestUtils.setField(adapter,"ldapTemplate",ldapTemplate);
        var context=org.mockito.Mockito.mock(javax.naming.directory.DirContext.class);
        var attrs=new javax.naming.directory.BasicAttributes(true);
        attrs.put("entryUUID",actor.subject());attrs.put("uid",actor.user());
        attrs.put("memberOf"," private-original-directory-group ");
        try{
            org.mockito.Mockito.when(context.getAttributes(
                org.mockito.ArgumentMatchers.any(javax.naming.Name.class),
                org.mockito.ArgumentMatchers.any(String[].class))).thenReturn(attrs);
            org.mockito.Mockito.when(ldapTemplate.authenticate(
                org.mockito.ArgumentMatchers.anyString(),
                org.mockito.ArgumentMatchers.anyString(),
                org.mockito.ArgumentMatchers.anyString(),
                org.mockito.ArgumentMatchers.any(org.springframework.ldap.core
                    .AuthenticatedLdapEntryContextCallback.class))).thenAnswer(invocation->{
                ((org.springframework.ldap.core.AuthenticatedLdapEntryContextCallback)
                    invocation.getArgument(3)).executeWithContext(context,
                    new org.springframework.ldap.core.LdapEntryIdentification(
                        new javax.naming.ldap.LdapName(
                            "uid=synthetic,ou=people,dc=synthetic,dc=invalid"),
                        new javax.naming.ldap.LdapName("uid=synthetic,ou=people")));
                return true;
            });
        }catch(javax.naming.NamingException failure){throw new IllegalStateException(failure);}
        org.mockito.Mockito.doAnswer(i->adapter.verify(i.getArgument(0),i.getArgument(1)))
            .when(directory).verify(actor.user(),"synthetic-password");
        org.springframework.test.util.ReflectionTestUtils.setField(ldap,
            "verifiedClaimReceiptEnabled",true);
    }
    private void login(NativeActor actor) {
        if("LDAP".equals(actor.type()) || "ACTIVE_DIRECTORY".equals(actor.type()))
            directoryCredential(actor);
        if("OIDC".equals(actor.type())) {
            var token=sourceEncoder.encode(org.springframework.security.oauth2.jwt
                .JwtEncoderParameters.from(org.springframework.security.oauth2.jwt.JwtClaimsSet.builder()
                    .issuer(OIDC_SOURCE).subject(actor.subject()).audience(List.of("hidra-api"))
                    .issuedAt(Instant.now().minusSeconds(1))
                    .expiresAt(Instant.now().plusSeconds(300))
                    .claim("groups",List.of("private-original-group")).build()));
            var principal=oidc.convert(verifiedExternal.decode(token.getTokenValue()));
            org.springframework.security.core.context.SecurityContextHolder.getContext()
                .setAuthentication(principal);
            var actual=(dz.sh.hidra.modules.identity.domain.model.HidraPrincipal)
                principal.getPrincipal();
            var input=new dz.sh.hidra.modules.identity.application.model.AuthenticatedPrincipalInput(
                actual.userId(),actual.username(),actual.displayName(),
                actual.authenticationType(),actual.identityProviderId(),actual.roles(),
                actual.permissions(),actual.externalIdentityId(),null);
            var result=completion.complete(input,"synthetic-ip","synthetic-agent",
                "synthetic-correlation");
            ambient(result.accessToken());
        } else {
            var result=direct.authenticate(new dz.sh.hidra.modules.identity.application.model
                .DirectAuthenticationCommand(
                    dz.sh.hidra.modules.identity.domain.value.ProviderType.valueOf(actor.type()),
                    actor.user(),"synthetic-password","synthetic-ip","synthetic-agent",
                    "synthetic-correlation"));
            ambient(result.accessToken());
        }
    }
    private void authorize(NativeActor actor,
          dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.Command c,
          Collection<String> units) {
        var req=new dz.sh.hidra.modules.identity.application.contract.topology
            .TopologyStationConfigurationAuthorizationContract.Request(
                dz.sh.hidra.modules.identity.application.contract.topology
                    .TopologyStationConfigurationAuthorizationContract.Operation.valueOf(c.operation().name()),
                c.companyPartyId(),c.facilityId(),c.revisionId(),List.copyOf(new TreeSet<>(units)));
        for(var q:req.requirements()) {
            String code=dz.sh.hidra.modules.identity.application.contract.topology
                .TopologyStationConfigurationAuthorizationContract.permissionCode(
                    req.operation(),q.resourceType());
            var ids=nativeJdbc.queryForList("SELECT id FROM "+T+"permission WHERE code=?",
                String.class,code);
            String permission=ids.isEmpty()?syntheticId():ids.getFirst();
            if(ids.isEmpty())nativeJdbc.update("INSERT INTO "+T+"permission"
                +"(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)"
                +" VALUES (?,?,'topology',?,?,false,'ACTIVE',now(),now())",
                permission,code,q.resourceType(),code.substring(code.lastIndexOf(':')+1));
            String scope=q.scope().name().equals("COMPANY")?"CUSTOM":"FACILITY";
            String value=scope.equals("CUSTOM")?"topology-company:"+q.scopeReference():q.scopeReference();
            Integer count=nativeJdbc.queryForObject(
                "SELECT count(*) FROM "+T+"user_permission_grant "
                +"WHERE user_id=? AND permission_id=? AND effect='GRANT' AND status='ACTIVE' "
                +"AND scope_type=? AND scope_reference_id=? AND valid_to>clock_timestamp()",
                Integer.class,actor.user(),permission,scope,value);
            if(count!=null && count>0)continue;
            nativeJdbc.update("INSERT INTO "+T+"user_permission_grant"
                +"(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,"
                +"emergency_access,valid_from,valid_to,status,created_at)"
                +" VALUES (?,?,?,'GRANT',?,?,'synthetic native configuration',false,"
                +"now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())",
                syntheticId(),actor.user(),permission,scope,value);
        }
    }
    private static dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.Command
            command(dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.Operation kind,
            StationInstalledConfiguration c) {
        return new dz.sh.hidra.modules.topology.application.port.in
            .StationInstalledConfigurationUseCase.Command(kind,c.companyPartyId(),
                c.facilityId(),kind==dz.sh.hidra.modules.topology.application.port.in
                    .StationInstalledConfigurationUseCase.Operation.SAVE
                    || kind==dz.sh.hidra.modules.topology.application.port.in
                    .StationInstalledConfigurationUseCase.Operation.READ_REVISION
                    ? c.revisionId():null,
                kind==dz.sh.hidra.modules.topology.application.port.in
                    .StationInstalledConfigurationUseCase.Operation.SAVE?c:null,
                kind==dz.sh.hidra.modules.topology.application.port.in
                    .StationInstalledConfigurationUseCase.Operation.LIST_HISTORY?10:0);
    }
    private static List<String> unitRefs(StationInstalledConfiguration c) {
        return c.positions().stream().map(StationInstalledConfiguration.Position::installedUnitId)
            .filter(Objects::nonNull).sorted().toList();
    }
    private dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.Result
            nativeExecute(NativeActor actor,
              dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.Command c,
              Collection<String> refs) {
        authorize(actor,c,refs);login(actor);return protectedService.execute(c);
    }

    private static final Instant FROM=Instant.parse("2026-02-01T00:00:00Z");
    private static final String LOCK_ALL="""
        LOCK TABLE public.hidra_topology_equipment,
          public.hidra_topology_facility,
          public.hidra_topology_facility_owner_generation,
          public.hidra_topology_installed_unit,
          public.hidra_topology_installed_unit_installation,
          public.hidra_topology_installed_unit_installation_event,
          public.hidra_topology_station_graph_head,
          public.hidra_topology_station_graph_revision
        IN SHARE ROW EXCLUSIVE MODE NOWAIT
        """;
    private static synchronized void migrate() {
        if(!migrated){
            Flyway.configure().dataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),
                POSTGRES.getPassword()).load().migrate();
            migrated=true;
        }
    }
    private JdbcTemplate jdbc() {
        migrate();
        var ds=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),
            POSTGRES.getPassword());
        ds.setDriverClassName("org.postgresql.Driver");
        return new JdbcTemplate(ds);
    }
    private static String id(){return UUID.randomUUID().toString();}
    private static TransactionTemplate tx(JdbcTemplate db){
        return new TransactionTemplate(new DataSourceTransactionManager(
            Objects.requireNonNull(db.getDataSource())));
    }
    private record Fixture(StationInstalledConfiguration declaration,String firstPosition){}
    private Fixture fixture(JdbcTemplate db,boolean published) { return fixture(db,published,false); }
    private Fixture fixture(JdbcTemplate db,boolean published,boolean vacantFirst) {
        String company=id(),facility=id(),facilityType=id(),kind=id();
        db.update("INSERT INTO public.hidra_topology_facility_type(id,code,name,facility_kind,status,created_at,updated_at)"
                +" VALUES(?,?,'Synthetic station','STATION','ACTIVE',now(),now())",
            facilityType,facilityType);
        db.update("INSERT INTO public.hidra_topology_equipment_type(id,code,name,status,created_at,updated_at)"
                +" VALUES(?,?,'Synthetic unit','ACTIVE',now(),now())",kind,kind);
        db.update("INSERT INTO public.hidra_topology_facility(id,code,facility_type_id,facility_kind,"
                +"owner_party_id,status,created_at,updated_at) VALUES(?,?,?,'STATION',?,'ACTIVE',now(),now())",
            facility,facility,facilityType,company);
        List<Unit> graphUnits=new ArrayList<>();
        List<Edge> graphEdges=new ArrayList<>();
        List<Position> positions=new ArrayList<>();
        String firstPosition=null;
        for(int i=0;i<3;i++){
            String position=id(),unit=id(),installation=id();
            if(i==0)firstPosition=position;
            db.update("INSERT INTO public.hidra_topology_equipment(id,code,facility_id,equipment_type_id,"
                    +"status,created_at,updated_at) VALUES(?,?,?,?,'PLANNED',now(),now())",
                position,position,facility,kind);
            if(!(vacantFirst && i==0)) db.update("INSERT INTO public.hidra_topology_installed_unit(id,company_party_id,equipment_kind_id,"
                    +"manufacturer_party_verification,manufacturer_model_verification,serial_verification)"
                    +" VALUES(?,?,?,'UNKNOWN','UNKNOWN','UNKNOWN')",unit,company,kind);
            if(!(vacantFirst && i==0)) db.update("INSERT INTO public.hidra_topology_installed_unit_installation(installation_id,"
                    +"unit_id,position_id,company_party_id,effective_from,recorded_at,evidence_reference,revision)"
                    +" VALUES(?,?,?,?,?,?,'synthetic:source',1)",
                installation,unit,position,company,Timestamp.from(FROM),Timestamp.from(FROM));
            graphUnits.add(new Unit(position,facility,"i"+i,"o"+i));
            graphEdges.add(new Edge("in"+i,"s","i"+i));
            graphEdges.add(new Edge("out"+i,"o"+i,"d"));
            positions.add(vacantFirst && i==0
                ? new Position(position,Disposition.UNOCCUPIED,null,null,0,null,null,null)
                : new Position(position,Disposition.CONNECTED,unit,installation,1,
                    FROM,null,"synthetic:source"));
        }
        var graph=new StationInternalGraph(facility,"graph-r1",Status.COMPLETE,
            List.of(new Header("suction",HeaderKind.SUCTION,"s"),
                    new Header("discharge",HeaderKind.DISCHARGE,"d")),graphUnits,graphEdges);
        var revision=new StationInternalGraphRevision(facility,"graph-r1",graph,
            SourceClassification.SYNTHETIC,FROM,FROM,null,"synthetic:graph",null,
            Qualification.STRUCTURALLY_QUALIFIED);
        var codec=new StationInternalGraphRevisionCodec();
        byte[] bytes=codec.encode(revision);
        db.update("INSERT INTO public.hidra_topology_station_graph_revision"
                +"(facility_id,revision_id,payload_format,canonical_payload,sha256) VALUES(?,?,?,?,?)",
            facility,"graph-r1",StationInternalGraphRevisionCodec.FORMAT,bytes,codec.sha256(bytes));
        if(published){
            UUID event=UUID.randomUUID();
            db.update("INSERT INTO public.hidra_topology_station_graph_operation_event("
                    +"event_id,facility_id,event_kind,resulting_epoch,resulting_revision_id,"
                    +"actor_binding_id,actor_session_id,actor_user_id,company_party_id,party_generation,"
                    +"facility_generation,sampled_at,valid_until,backend_pid,transaction_id)"
                    +" VALUES(?,?,'PUBLISH',1,?,?,?,?,?,?,?,?,?,?,?)",
                event,facility,"graph-r1","synthetic-binding","synthetic-session","synthetic-user",
                company,1L,1L,Timestamp.from(FROM),Timestamp.from(FROM.plusSeconds(3600)),
                1,1L);
            db.update("INSERT INTO public.hidra_topology_station_graph_head("
                    +"facility_id,revision_id,head_epoch,state,last_event_id)"
                    +" VALUES(?, ?,1,'ACTIVE',?)",facility,"graph-r1",event);
        }
        var configured=new StationInstalledConfiguration(company,facility,"config-r1",id(),0,1,1,
            revision,StationInternalGraphRevisionCodec.FORMAT,codec.sha256(bytes),
            FROM,null,FROM,"synthetic:configuration","synthetic-binding","synthetic-session",
            "synthetic-user",positions,graphEdges.stream().map(Edge::id)
                .filter(edge -> !vacantFirst || !edge.equals("in0") && !edge.equals("out0")).toList());
        return new Fixture(configured,firstPosition);
    }


    @org.springframework.beans.factory.annotation.Autowired
    dz.sh.hidra.modules.topology.api.rest.controller.StationInstalledConfigurationController configuredHttp;
    @org.springframework.beans.factory.annotation.Autowired
    org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping configuredMappings;
    private org.springframework.test.web.servlet.MockMvc mvc(){
        // Real Spring-discovered controller and real native service/DB; no mocked permission issuer.
        return org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(configuredHttp)
            .setControllerAdvice(new dz.sh.hidra.modules.topology.api.rest
                .StationInstalledConfigurationApiExceptionHandler()).build();
    }
    private static String path(StationInstalledConfiguration c) {
        return "/api/v1/topology/facilities/"+c.facilityId()+"/installed-configuration";
    }
    private static String instantText(java.time.Instant value){
        return value==null?null:value.toString();
    }
    private static String wire(StationInstalledConfiguration c) {
        var revision=c.pinnedGraph();var graph=revision.graph();
        var g=new dz.sh.hidra.modules.topology.api.rest.request.StationInstalledConfigurationRequests.GraphRevision(
            revision.facilityId(),revision.revisionId(),
            new dz.sh.hidra.modules.topology.api.rest.request.StationInstalledConfigurationRequests.Graph(
                graph.facilityId(),graph.revisionId(),graph.status().name(),
                graph.headers().stream().map(h->new dz.sh.hidra.modules.topology.api.rest.request
                    .StationInstalledConfigurationRequests.Header(h.id(),h.kind().name(),h.portId())).toList(),
                graph.units().stream().map(u->new dz.sh.hidra.modules.topology.api.rest.request
                    .StationInstalledConfigurationRequests.Unit(
                        u.equipmentId(),u.facilityId(),u.inletPortId(),u.outletPortId())).toList(),
                graph.edges().stream().map(e->new dz.sh.hidra.modules.topology.api.rest.request
                    .StationInstalledConfigurationRequests.Edge(e.id(),e.fromPortId(),e.toPortId())).toList()),
            revision.sourceClassification().name(),instantText(revision.recordedAt()),
            instantText(revision.effectiveFrom()),instantText(revision.effectiveUntil()),
            revision.evidenceReference(),revision.expectedPredecessorRevisionId(),
            revision.qualification().name());
        var positions=c.positions().stream().map(p->
            new dz.sh.hidra.modules.topology.api.rest.request.StationInstalledConfigurationRequests.Position(
                p.positionId(),p.disposition().name(),p.installedUnitId(),p.installationId(),
                p.installationRevision(),instantText(p.installationFrom()),
                instantText(p.installationUntil()),p.installationEvidenceReference())).toList();
        var save=new dz.sh.hidra.modules.topology.api.rest.request.StationInstalledConfigurationRequests.Save(
            c.requestId(),c.companyPartyId(),c.revisionId(),c.expectedEpoch(),
            c.graphHeadEpoch(),g,c.graphPayloadFormat(),c.graphSha256(),
            instantText(c.effectiveFrom()),instantText(c.effectiveUntil()),
            c.sourceReference(),positions,c.activeEdgeIds());
        return tools.jackson.databind.json.JsonMapper.builder().build().writeValueAsString(save);
    }

    @Test void springRegistersAllFourProtectedConfigurationHttpRoutes() {
        var handlers=configuredMappings.getHandlerMethods().values().stream()
            .filter(h->h.getBeanType().equals(
                dz.sh.hidra.modules.topology.api.rest.controller.StationInstalledConfigurationController.class))
            .toList();
        assertEquals(4,handlers.size());
        assertNotNull(configuredHttp);
        assertNotNull(protectedService);
    }

    @Test void actualHttpFourProducersThreeStationsNineUnitsSaveReplayCurrentRevisionHistory() throws Exception {
        var http=mvc();
        JdbcTemplate owner=jdbc();
        for(String producer:List.of("LOCAL","LDAP","ACTIVE_DIRECTORY","OIDC")){
            var actor=nativeActor(producer);
            for(int station=0;station<3;station++){
                var c=fixture(owner,true).declaration();
                var refs=unitRefs(c);
                assertEquals(3,refs.size());
                var save=command(dz.sh.hidra.modules.topology.application.port.in
                    .StationInstalledConfigurationUseCase.Operation.SAVE,c);
                authorize(actor,save,refs);login(actor);
                http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(path(c))
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(wire(c)))
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                        "$.snapshot.selectedEpoch").value(1))
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                        "$.replayed").value(false))
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                        "$.engineeringQualified").value(false));
                authorize(actor,save,refs);login(actor);
                http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(path(c))
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(wire(c)))
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                        "$.replayed").value(true));
                for(var op:List.of(
                    dz.sh.hidra.modules.topology.application.port.in
                        .StationInstalledConfigurationUseCase.Operation.READ_CURRENT,
                    dz.sh.hidra.modules.topology.application.port.in
                        .StationInstalledConfigurationUseCase.Operation.READ_REVISION)){
                    authorize(actor,command(op,c),refs);login(actor);
                    String url=op.name().equals("READ_REVISION")
                        ?path(c)+"/revisions/"+c.revisionId():path(c);
                    http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(url)
                        .param("companyPartyId",c.companyPartyId()))
                        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                            "$.snapshot.revisionId").value(c.revisionId()));
                }
                authorize(actor,command(dz.sh.hidra.modules.topology.application.port.in
                    .StationInstalledConfigurationUseCase.Operation.LIST_HISTORY,c),refs);login(actor);
                http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(
                    path(c)+"/history").param("companyPartyId",c.companyPartyId()).param("limit","1"))
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                        "$.history[0].revisionId").value(c.revisionId()));
                assertEquals(3,owner.queryForObject(
                    "SELECT count(*) FROM public.hidra_topology_station_configuration_member WHERE facility_id=?",
                    Integer.class,c.facilityId()));
                assertEquals(1,owner.queryForObject(
                    "SELECT count(*) FROM public.hidra_topology_station_configuration_request WHERE facility_id=?",
                    Integer.class,c.facilityId()));
            }
        }
    }

    @Test void nativeHttpVacantPositionAndForeignCompanyCannotBecomeOperatingOrDisclosureRights() throws Exception {
        var http=mvc();var owner=jdbc();var actor=nativeActor("LOCAL");
        var c=fixture(owner,true,true).declaration();var refs=unitRefs(c);
        assertEquals(2,refs.size());
        var save=command(dz.sh.hidra.modules.topology.application.port.in
            .StationInstalledConfigurationUseCase.Operation.SAVE,c);
        authorize(actor,save,refs);login(actor);
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(path(c))
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(wire(c)))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                "$.snapshot.positions.length()").value(3))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                "$.operatingStateValidated").value(false));
        login(actor);
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(path(c))
            .param("companyPartyId",syntheticId()))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isForbidden());
    }

    @Test void nativeHttpChangedReplayBodyMaps409AndPreservesCommittedSource() throws Exception {
        var http=mvc();var owner=jdbc();var actor=nativeActor("LOCAL");
        var c=fixture(owner,true).declaration();var refs=unitRefs(c);
        var save=command(dz.sh.hidra.modules.topology.application.port.in
            .StationInstalledConfigurationUseCase.Operation.SAVE,c);
        authorize(actor,save,refs);login(actor);
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(path(c))
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(wire(c)))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
        authorize(actor,save,refs);login(actor);
        String tampered=wire(c).replace("synthetic:configuration","synthetic:modified-command");
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(path(c))
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(tampered))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isConflict());
        assertEquals(1,owner.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_station_configuration_revision WHERE facility_id=?",
            Integer.class,c.facilityId()));
    }

    @Test void nativeHttpSequentialEpochHistoryCursorIsExclusiveAndAuthorized() throws Exception {
        var http=mvc();var owner=jdbc();var actor=nativeActor("LOCAL");
        var first=fixture(owner,true).declaration();var refs=unitRefs(first);
        var save=command(dz.sh.hidra.modules.topology.application.port.in
            .StationInstalledConfigurationUseCase.Operation.SAVE,first);
        authorize(actor,save,refs);login(actor);
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(path(first))
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(wire(first)))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
        var second=new StationInstalledConfiguration(first.companyPartyId(),first.facilityId(),
            syntheticId(),syntheticId(),1,2,first.graphHeadEpoch(),first.pinnedGraph(),
            first.graphPayloadFormat(),first.graphSha256(),first.effectiveFrom(),
            first.effectiveUntil(),first.recordedAt(),first.sourceReference(),
            first.actorBindingId(),first.actorSessionId(),first.actorUserId(),
            first.positions(),first.activeEdgeIds());
        var secondSave=command(dz.sh.hidra.modules.topology.application.port.in
            .StationInstalledConfigurationUseCase.Operation.SAVE,second);
        authorize(actor,secondSave,refs);login(actor);
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(path(second))
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(wire(second)))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
        var history=command(dz.sh.hidra.modules.topology.application.port.in
            .StationInstalledConfigurationUseCase.Operation.LIST_HISTORY,second);
        authorize(actor,history,refs);login(actor);
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(path(first)+"/history")
            .param("companyPartyId",first.companyPartyId()).param("limit","1"))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                "$.history[0].selectedEpoch").value(2))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                "$.nextCursor").value("2"));
        authorize(actor,history,refs);login(actor);
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(path(first)+"/history")
            .param("companyPartyId",first.companyPartyId()).param("limit","1").param("cursor","2"))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                "$.history[0].selectedEpoch").value(1))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                "$.nextCursor").doesNotExist());
    }

    @Test void configuredSecurityChainVerifiesRealBearerAndCurrentConfigurationGrants() throws Exception {
        var owner=jdbc();var actor=nativeActor("LOCAL");var c=fixture(owner,true).declaration();
        var refs=unitRefs(c);
        var save=command(dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.Operation.SAVE,c);
        var read=command(dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.Operation.READ_CURRENT,c);
        authorize(actor,save,refs);login(actor);String bearer=issuedBearer();
        var http=securedMvc();
        org.springframework.security.core.context.SecurityContextHolder.clearContext();
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(path(c))
            .param("companyPartyId",c.companyPartyId()))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isUnauthorized());
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(path(c))
            .param("companyPartyId",c.companyPartyId()).header("Authorization","Bearer not.a.valid.jwt"))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isUnauthorized());
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(path(c))
            .header("Authorization","Bearer "+bearer)
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(wire(c)))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                "$.snapshot.revisionId").value(c.revisionId()));
        authorize(actor,read,refs);
        org.springframework.security.core.context.SecurityContextHolder.clearContext();
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(path(c))
            .param("companyPartyId",c.companyPartyId()).header("Authorization","Bearer "+bearer))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                "$.snapshot.revisionId").value(c.revisionId()));
        assertEquals(1,owner.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_station_configuration_request WHERE facility_id=?",
            Integer.class,c.facilityId()));
        owner.update("UPDATE public.hidra_identity_user_permission_grant SET status='REVOKED' "
            +"WHERE user_id=? AND status='ACTIVE'",actor.user());
        org.springframework.security.core.context.SecurityContextHolder.clearContext();
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(path(c))
            .param("companyPartyId",c.companyPartyId()).header("Authorization","Bearer "+bearer))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isForbidden())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                "$.code").value("DENIED"))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString(c.revisionId()))));
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(path(c))
            .header("Authorization","Bearer "+bearer)
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(wire(c)))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isForbidden());
        assertEquals(1,owner.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_station_configuration_request WHERE facility_id=?",
            Integer.class,c.facilityId()));
    }
    @Test void configuredHttpLostCommitAcknowledgementReturnsIndeterminateThenExactReplay() throws Exception {
        var owner=jdbc();var actor=nativeActor("LOCAL");var c=fixture(owner,true).declaration();
        var save=command(dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.Operation.SAVE,c);
        authorize(actor,save,unitRefs(c));login(actor);String bearer=issuedBearer();
        var http=securedMvc();
        org.springframework.security.core.context.SecurityContextHolder.clearContext();
        var armed=new java.util.concurrent.atomic.AtomicReference<Thread>();
        var physicallyCommitted=new java.util.concurrent.atomic.AtomicBoolean();
        org.mockito.Mockito.clearInvocations(nativeRepositorySpy);
        org.mockito.Mockito.doAnswer(inv->{inv.callRealMethod();armed.set(Thread.currentThread());return null;})
            .when(nativeRepositorySpy).prepareFinal(org.mockito.ArgumentMatchers.any(),
                org.mockito.ArgumentMatchers.any(),org.mockito.ArgumentMatchers.anyList());
        org.mockito.Mockito.doAnswer(inv->{
            if(armed.compareAndSet(Thread.currentThread(),null)){
                inv.callRealMethod();
                physicallyCommitted.set(true);
                throw new org.springframework.transaction.TransactionSystemException(
                    "synthetic station configuration commit acknowledgement loss");
            }
            return inv.callRealMethod();
        }).when(guardedTransactionManager).commit(
            org.mockito.ArgumentMatchers.any(org.springframework.transaction.TransactionStatus.class));
        try{
            http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(path(c))
                .header("Authorization","Bearer "+bearer)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(wire(c)))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isServiceUnavailable())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                    "$.code").value("INDETERMINATE"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content()
                    .string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString(c.revisionId()))));
            assertTrue(physicallyCommitted.get(),"The selected physical commit must succeed before acknowledgement loss");
            org.mockito.Mockito.verify(nativeRepositorySpy).finalGuard(org.mockito.ArgumentMatchers.any());
        }finally{org.mockito.Mockito.reset(guardedTransactionManager,nativeRepositorySpy);}
        for(String suffix:List.of("revision","request"))
            assertEquals(1,owner.queryForObject(
                "SELECT count(*) FROM public.hidra_topology_station_configuration_"+suffix+" WHERE facility_id=?",
                Integer.class,c.facilityId()));
        assertEquals(c.positions().size(),owner.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_station_configuration_member WHERE facility_id=?",
            Integer.class,c.facilityId()));
        org.springframework.security.core.context.SecurityContextHolder.clearContext();
        http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(path(c))
            .header("Authorization","Bearer "+bearer)
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(wire(c)))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                "$.replayed").value(true))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                "$.snapshot.revisionId").value(c.revisionId()));
        assertEquals(1,owner.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_station_configuration_request WHERE facility_id=?",
            Integer.class,c.facilityId()));
    }
}
