/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedStationInstalledConfigurationPostgresIntegrationTest
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
package dz.sh.hidra.modules.topology.infrastructure.persistence;

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
class ProtectedStationInstalledConfigurationPostgresIntegrationTest {
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
    dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcStationInstalledConfigurationRepositoryAdapter nativeStoreSpy;

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

    @Test void sourceReaderRejectsTransactionlessCall(){
        JdbcTemplate db=jdbc();
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        Fixture f=fixture(db,true);
        assertThrows(RuntimeException.class,()->adapter.acquire(f.declaration()));
    }

    @Test void absentSourceLocksRejectBeforeAnyProtectedEvidence(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            assertThrows(RuntimeException.class,()->adapter.acquire(f.declaration()));
            s.setRollbackOnly();
        });
    }

    @Test void selectedPublishedGraphAndInstallationEventsReloadUnderActualLocks(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            var admission=adapter.acquire(f.declaration());
            var first=adapter.read(admission);
            assertEquals(3,first.positionCount());
            assertEquals(f.declaration().companyPartyId(),first.companyPartyId());
            assertEquals(f.declaration().graphSha256().length(),first.sourceSha256().length());
            assertEquals(8,adapter.finalFences(admission).size());
            adapter.requireCurrent(admission);
        });
    }

    @Test void graphWithoutActivePublicationFailsClosedEvenWithLocks(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,false);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            var ticket=adapter.acquire(f.declaration());
            assertThrows(RuntimeException.class,()->adapter.read(ticket));
            s.setRollbackOnly();
        });
    }

    @Test void sourceChangesAfterObservationPoisonOriginalTransaction(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            var ticket=adapter.acquire(f.declaration());
            adapter.read(ticket);
            db.update("UPDATE public.hidra_topology_equipment SET status='ACTIVE' WHERE id=?",f.firstPosition());
            assertThrows(RuntimeException.class,()->adapter.requireCurrent(ticket));
            s.setRollbackOnly();
        });
        assertEquals("PLANNED",db.queryForObject(
            "SELECT status FROM public.hidra_topology_equipment WHERE id=?",String.class,f.firstPosition()));
    }

    private static StationInstalledConfiguration cloneScope(StationInstalledConfiguration c,
            String company,long graphEpoch) {
        return new StationInstalledConfiguration(company,c.facilityId(),c.revisionId(),c.requestId(),
            c.expectedEpoch(),c.selectedEpoch(),graphEpoch,c.pinnedGraph(),
            c.graphPayloadFormat(),c.graphSha256(),c.effectiveFrom(),c.effectiveUntil(),
            c.recordedAt(),c.sourceReference(),c.actorBindingId(),c.actorSessionId(),
            c.actorUserId(),c.positions(),c.activeEdgeIds());
    }

    @Test void wrongCompanyCannotReadStationSourceEvenWithLocks(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        var wrong=cloneScope(f.declaration(),id(),1);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            var ticket=adapter.acquire(wrong);
            assertThrows(RuntimeException.class,()->adapter.read(ticket));
            s.setRollbackOnly();
        });
    }

    @Test void staleGraphHeadEpochCannotBeTreatedAsCurrent(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        var stale=cloneScope(f.declaration(),f.declaration().companyPartyId(),2);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            var ticket=adapter.acquire(stale);
            assertThrows(RuntimeException.class,()->adapter.read(ticket));
            s.setRollbackOnly();
        });
    }

    @Test void positionMovedToAnotherStationCannotBeRetainedAsSource(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);Fixture other=fixture(db,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            db.update("UPDATE public.hidra_topology_equipment SET facility_id=? WHERE id=?",
                other.declaration().facilityId(),f.firstPosition());
            var ticket=adapter.acquire(f.declaration());
            assertThrows(RuntimeException.class,()->adapter.read(ticket));
            s.setRollbackOnly();
        });
        assertEquals(f.declaration().facilityId(),db.queryForObject(
            "SELECT facility_id FROM public.hidra_topology_equipment WHERE id=?",
            String.class,f.firstPosition()));
    }

    @Test void closedAndRevisedInstallationCannotMatchStaleWitness(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);
        String installation=f.declaration().positions().stream()
            .filter(p->p.positionId().equals(f.firstPosition()))
            .findFirst().orElseThrow().installationId();
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            db.update("UPDATE public.hidra_topology_installed_unit_installation "
                +"SET effective_until=?,revision=revision+1 WHERE installation_id=?",
                Timestamp.from(FROM.plusSeconds(3600)),installation);
            var ticket=adapter.acquire(f.declaration());
            assertThrows(RuntimeException.class,()->adapter.read(ticket));
            s.setRollbackOnly();
        });
        assertEquals(1L,db.queryForObject(
            "SELECT revision FROM public.hidra_topology_installed_unit_installation "
                +"WHERE installation_id=?",Long.class,installation));
    }

    @Test void newlyOccupiedVacantPositionInvalidatesOriginalSourceTicket(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            var ticket=adapter.acquire(f.declaration());
            var original=adapter.read(ticket);
            assertEquals(3,original.positionCount());
            String unit=id();
            String kind=db.queryForObject("SELECT equipment_type_id FROM public.hidra_topology_equipment "
                +"WHERE id=?",String.class,f.firstPosition());
            db.update("INSERT INTO public.hidra_topology_installed_unit(id,company_party_id,equipment_kind_id,"
                +"manufacturer_party_verification,manufacturer_model_verification,serial_verification)"
                +" VALUES(?,?,?,'UNKNOWN','UNKNOWN','UNKNOWN')",
                unit,f.declaration().companyPartyId(),kind);
            db.update("INSERT INTO public.hidra_topology_installed_unit_installation(installation_id,"
                +"unit_id,position_id,company_party_id,effective_from,recorded_at,evidence_reference,revision)"
                +" VALUES(?,?,?,?,?,?,'synthetic:new-occupancy',1)",
                id(),unit,f.firstPosition(),f.declaration().companyPartyId(),
                Timestamp.from(FROM),Timestamp.from(FROM));
            assertThrows(RuntimeException.class,()->adapter.requireCurrent(ticket));
            s.setRollbackOnly();
        });
        assertEquals(0,db.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_installed_unit_installation "
                +"WHERE position_id=?",Integer.class,f.firstPosition()));
    }

    @Test void catalogColumnMutationFailsClosedAndRestoresOnRollback(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            var ticket=adapter.acquire(f.declaration());
            db.execute("ALTER TABLE public.hidra_topology_station_graph_revision "
                +"RENAME COLUMN sha256 TO synthetic_missing_digest");
            assertThrows(RuntimeException.class,()->adapter.read(ticket));
            s.setRollbackOnly();
        });
        assertEquals("sha256",db.queryForObject("""
            SELECT attname FROM pg_catalog.pg_attribute
            WHERE attrelid='public.hidra_topology_station_graph_revision'::regclass
            AND attname='sha256' AND attnum>0 AND NOT attisdropped
            """,String.class));
    }

    @Test void roleChangeAfterSourceReadInvalidatesIssuerCoordinates(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            var ticket=adapter.acquire(f.declaration());
            adapter.read(ticket);
            db.execute("SET LOCAL ROLE pg_monitor");
            assertThrows(RuntimeException.class,()->adapter.requireCurrent(ticket));
            s.setRollbackOnly();
        });
    }


    @Test void protectedSourceLockExcludesConcurrentVacancyPhantomUntilRelease(){
        JdbcTemplate db=jdbc();
        Fixture f=fixture(db,true,true);
        String kind=db.queryForObject("SELECT equipment_type_id FROM public.hidra_topology_equipment "
            +"WHERE id=?",String.class,f.firstPosition());
        String unit=id(),installation=id();
        db.update("INSERT INTO public.hidra_topology_installed_unit(id,company_party_id,equipment_kind_id,"
                +"manufacturer_party_verification,manufacturer_model_verification,serial_verification)"
                +" VALUES(?,?,?,'UNKNOWN','UNKNOWN','UNKNOWN')",
            unit,f.declaration().companyPartyId(),kind);
        String insert="INSERT INTO public.hidra_topology_installed_unit_installation("
            +"installation_id,unit_id,position_id,company_party_id,effective_from,recorded_at,"
            +"evidence_reference,revision) VALUES(?,?,?,?,?,?,'synthetic:concurrent-occupancy',1)";
        Object[] values={installation,unit,f.firstPosition(),f.declaration().companyPartyId(),
            Timestamp.from(FROM),Timestamp.from(FROM)};
        JdbcTemplate outside=jdbc();
        var concurrent=java.util.concurrent.Executors.newSingleThreadExecutor();
        try {
            tx(db).executeWithoutResult(status->{
                db.execute("SELECT public.hidra_station_configuration_lock_sources()");
                var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
                var ticket=adapter.acquire(f.declaration());
                assertEquals(3,adapter.read(ticket).positionCount());
                var attempted=concurrent.submit(()->tx(outside).executeWithoutResult(inner->{
                    outside.execute("SET LOCAL lock_timeout='750ms'");
                    var denied=assertThrows(org.springframework.dao.DataAccessException.class,
                        ()->outside.update(insert,values));
                    assertInstanceOf(java.sql.SQLException.class,denied.getMostSpecificCause());
                    assertEquals("55P03",
                        ((java.sql.SQLException)denied.getMostSpecificCause()).getSQLState());
                    inner.setRollbackOnly();
                }));
                assertDoesNotThrow(()->attempted.get(10,java.util.concurrent.TimeUnit.SECONDS));
                adapter.requireCurrent(ticket);
            });
        }finally{
            concurrent.shutdownNow();
        }
        assertEquals(0,db.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_installed_unit_installation "
            +"WHERE installation_id=?",Integer.class,installation));
        assertEquals(1,db.update(insert,values));
        assertEquals(1,db.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_installed_unit_installation "
            +"WHERE installation_id=?",Integer.class,installation));
    }

    @Test void protectedSourceLockFunctionKeepsAllTwelveRelationsOnOriginalTransaction(){
        JdbcTemplate db=jdbc();
        tx(db).executeWithoutResult(t->{
            db.execute("SELECT public.hidra_station_configuration_lock_sources()");
            Integer count=db.queryForObject("""
                SELECT count(*) FROM pg_catalog.pg_locks
                WHERE pid=pg_catalog.pg_backend_pid() AND granted AND locktype='relation'
                  AND mode='ShareRowExclusiveLock'
                  AND relation IN (
                    'public.hidra_topology_equipment'::regclass,
                    'public.hidra_topology_facility'::regclass,
                    'public.hidra_topology_facility_owner_generation'::regclass,
                    'public.hidra_topology_installed_unit'::regclass,
                    'public.hidra_topology_installed_unit_installation'::regclass,
                    'public.hidra_topology_installed_unit_installation_event'::regclass,
                    'public.hidra_topology_station_graph_head'::regclass,
                    'public.hidra_topology_station_graph_revision'::regclass,
                    'public.hidra_topology_station_configuration_revision'::regclass,
                    'public.hidra_topology_station_configuration_member'::regclass,
                    'public.hidra_topology_station_configuration_head'::regclass,
                    'public.hidra_topology_station_configuration_request'::regclass)
                """,Integer.class);
            assertEquals(12,count);
        });
    }

    @Test void ordinarySyntheticRoleCannotExecuteUnprovisionedSourceLock(){
        JdbcTemplate db=jdbc();
        String role="synthetic_station_denied_"+UUID.randomUUID().toString().replace("-","");
        db.execute("CREATE ROLE "+role+" NOLOGIN");
        try{
            tx(db).executeWithoutResult(t->{
                db.execute("SET LOCAL ROLE "+role);
                assertThrows(org.springframework.dao.DataAccessException.class,
                    ()->db.execute("SELECT public.hidra_station_configuration_lock_sources()"));
                t.setRollbackOnly();
            });
        }finally{db.execute("DROP ROLE "+role);}
    }

    @Test void explicitSyntheticPublisherGrantDoesNotGrantSourceDml(){
        JdbcTemplate db=jdbc();
        String role="synthetic_station_publisher_"+UUID.randomUUID().toString().replace("-","");
        db.execute("CREATE ROLE "+role+" NOLOGIN");
        try{
            db.execute("GRANT EXECUTE ON FUNCTION public.hidra_station_configuration_lock_sources() TO "+role);
            tx(db).executeWithoutResult(t->{
                db.execute("SET LOCAL ROLE "+role);
                Boolean denied=db.queryForObject("""
                    SELECT NOT pg_catalog.has_table_privilege(current_user,
                        'public.hidra_topology_installed_unit_installation','UPDATE')
                      AND NOT pg_catalog.has_table_privilege(current_user,
                        'public.hidra_topology_equipment','INSERT')
                      AND pg_catalog.has_function_privilege(current_user,
                        'public.hidra_station_configuration_lock_sources()','EXECUTE')
                    """,Boolean.class);
                assertEquals(Boolean.TRUE,denied);
                db.execute("SELECT public.hidra_station_configuration_lock_sources()");
                Integer locks=db.queryForObject("""
                    SELECT count(*) FROM pg_catalog.pg_locks
                    WHERE pid=pg_catalog.pg_backend_pid() AND granted
                      AND locktype='relation' AND mode='ShareRowExclusiveLock'
                    """,Integer.class);
                assertTrue(locks>=12);
                t.setRollbackOnly();
            });
        }finally{
            db.execute("REVOKE EXECUTE ON FUNCTION public.hidra_station_configuration_lock_sources() FROM "+role);
            db.execute("DROP ROLE "+role);
        }
    }

    @Test void protectedRequestReceiptNeedsNativeTransactionOriginColumns(){
        JdbcTemplate db=jdbc();migrate();
        Set<String> columns=new HashSet<>(db.queryForList("""
            SELECT column_name FROM information_schema.columns
            WHERE table_schema='public' AND table_name='hidra_topology_station_configuration_request'
            """,String.class));
        assertTrue(columns.containsAll(Set.of("actor_session_id","actor_user_id","sampled_at",
            "valid_until","backend_pid","transaction_id","database_role")));
        assertEquals(1,db.queryForObject("""
            SELECT count(*) FROM pg_catalog.pg_trigger t
            WHERE t.tgrelid='public.hidra_topology_station_configuration_request'::regclass
              AND t.tgname='trg_station_configuration_request_origin' AND t.tgenabled='O'
            """,Integer.class));
    }

    private static StationInstalledConfiguration successor(StationInstalledConfiguration old,
            long epoch,String revision,String request) {
        return new StationInstalledConfiguration(old.companyPartyId(),old.facilityId(),
            revision,request,epoch-1,epoch,old.graphHeadEpoch(),
            old.pinnedGraph(),old.graphPayloadFormat(),old.graphSha256(),
            old.effectiveFrom(),old.effectiveUntil(),old.recordedAt(),
            old.sourceReference(),old.actorBindingId(),old.actorSessionId(),
            old.actorUserId(),old.positions(),old.activeEdgeIds());
    }

    @Test void nativeFourProducersSaveReplayReadAndHistoryAcrossThreeStationsNineUnits() {
        JdbcTemplate owner=jdbc();
        for(String provider:List.of("LOCAL","LDAP","ACTIVE_DIRECTORY","OIDC")) {
            NativeActor actor=nativeActor(provider);
            for(int station=0;station<3;station++) {
                var stationFixture=fixture(owner,true);
                var c=stationFixture.declaration();
                var refs=unitRefs(c);
                assertEquals(3,refs.size());
                // The input order is intentionally noncanonical: the stored graph sorts by ID.
                assertEquals(List.of("suction","discharge"),
                    c.pinnedGraph().graph().headers().stream()
                        .map(StationInternalGraph.Header::id).toList());
                var saved=nativeExecute(actor,command(
                    dz.sh.hidra.modules.topology.application.port.in
                        .StationInstalledConfigurationUseCase.Operation.SAVE,c),refs);
                assertNotNull(saved.declaration());
                assertFalse(saved.replayed());
                assertEquals(1,saved.declaration().selectedEpoch());
                assertEquals(actor.user(),saved.declaration().actorUserId());
                assertNotEquals(c.actorBindingId(),saved.declaration().actorBindingId());
                assertEquals(List.of("discharge","suction"),
                    saved.declaration().pinnedGraph().graph().headers().stream()
                        .map(StationInternalGraph.Header::id).toList());
                assertArrayEquals(new StationInternalGraphRevisionCodec().encode(c.pinnedGraph()),
                    new StationInternalGraphRevisionCodec().encode(saved.declaration().pinnedGraph()));
                var replay=nativeExecute(actor,command(
                    dz.sh.hidra.modules.topology.application.port.in
                        .StationInstalledConfigurationUseCase.Operation.SAVE,c),refs);
                assertTrue(replay.replayed());
                assertEquals(saved.declaration(),replay.declaration());
                for(var op:List.of(
                    dz.sh.hidra.modules.topology.application.port.in
                        .StationInstalledConfigurationUseCase.Operation.READ_CURRENT,
                    dz.sh.hidra.modules.topology.application.port.in
                        .StationInstalledConfigurationUseCase.Operation.READ_REVISION)) {
                    var result=nativeExecute(actor,command(op,c),refs);
                    assertEquals(saved.declaration(),result.declaration());
                }
                var history=nativeExecute(actor,command(
                    dz.sh.hidra.modules.topology.application.port.in
                        .StationInstalledConfigurationUseCase.Operation.LIST_HISTORY,c),refs);
                assertEquals(List.of(saved.declaration()),history.history());
                assertEquals(3,owner.queryForObject(
                    "SELECT count(*) FROM public.hidra_topology_station_configuration_member "
                    +"WHERE company_party_id=? AND facility_id=? AND revision_id=?",
                    Integer.class,c.companyPartyId(),c.facilityId(),c.revisionId()));
                assertEquals(1,owner.queryForObject(
                    "SELECT count(*) FROM public.hidra_topology_station_configuration_request "
                    +"WHERE company_party_id=? AND facility_id=? AND request_id=?",
                    Integer.class,c.companyPartyId(),c.facilityId(),c.requestId()));
            }
        }
    }

    @Test void nativeAuthorizedSecondEpochAndHistoryCannotBeChangedByStaleCas() {
        JdbcTemplate owner=jdbc();
        NativeActor actor=nativeActor("LOCAL");
        StationInstalledConfiguration first=fixture(owner,true).declaration();
        var refs=unitRefs(first);
        var saved=nativeExecute(actor,command(
            dz.sh.hidra.modules.topology.application.port.in
                .StationInstalledConfigurationUseCase.Operation.SAVE,first),refs).declaration();
        StationInstalledConfiguration second=successor(first,2,syntheticId(),syntheticId());
        var newer=nativeExecute(actor,command(
            dz.sh.hidra.modules.topology.application.port.in
                .StationInstalledConfigurationUseCase.Operation.SAVE,second),refs).declaration();
        assertEquals(2,newer.selectedEpoch());
        assertEquals(saved.facilityId(),newer.facilityId());
        var current=nativeExecute(actor,command(
            dz.sh.hidra.modules.topology.application.port.in
                .StationInstalledConfigurationUseCase.Operation.READ_CURRENT,second),refs);
        assertEquals(newer,current.declaration());
        var history=nativeExecute(actor,command(
            dz.sh.hidra.modules.topology.application.port.in
                .StationInstalledConfigurationUseCase.Operation.LIST_HISTORY,second),refs);
        assertEquals(List.of(newer,saved),history.history());
        var oldRevision=nativeExecute(actor,command(
            dz.sh.hidra.modules.topology.application.port.in
                .StationInstalledConfigurationUseCase.Operation.READ_REVISION,first),refs);
        assertEquals(saved,oldRevision.declaration());
        int before=owner.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_station_configuration_revision "
            +"WHERE facility_id=?",Integer.class,first.facilityId());
        StationInstalledConfiguration stale=successor(first,2,syntheticId(),syntheticId());
        var staleCommand=command(dz.sh.hidra.modules.topology.application.port.in
            .StationInstalledConfigurationUseCase.Operation.SAVE,stale);
        authorize(actor,staleCommand,refs);login(actor);
        assertThrows(RuntimeException.class,()->protectedService.execute(staleCommand));
        assertEquals(before,owner.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_station_configuration_revision "
            +"WHERE facility_id=?",Integer.class,first.facilityId()));
    }

    @Test void nativeUnitDenialAndRevocationHideCurrentAndRejectReplayWithoutMutation() {
        JdbcTemplate owner=jdbc();
        NativeActor actor=nativeActor("LOCAL");
        StationInstalledConfiguration c=fixture(owner,true).declaration();
        var save=command(dz.sh.hidra.modules.topology.application.port.in
            .StationInstalledConfigurationUseCase.Operation.SAVE,c);
        var refs=unitRefs(c);
        var saved=nativeExecute(actor,save,refs);
        var read=command(dz.sh.hidra.modules.topology.application.port.in
            .StationInstalledConfigurationUseCase.Operation.READ_CURRENT,c);
        authorize(actor,read,refs);
        nativeJdbc.update("INSERT INTO "+T+"user_permission_grant("
            +"id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,"
            +"emergency_access,valid_from,valid_to,status,created_at) "
            +"SELECT ?,?,p.id,'DENY','CUSTOM',?,'synthetic explicit physical unit denial',"
            +"false,now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now() "
            +"FROM "+T+"permission p WHERE p.code='topology:installed-unit:read'",
            syntheticId(),actor.user(),"topology-company:"+c.companyPartyId());
        login(actor);
        assertThrows(RuntimeException.class,()->protectedService.execute(read));
        int receipts=owner.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_station_configuration_request "
            +"WHERE facility_id=?",Integer.class,c.facilityId());
        assertEquals(1,receipts);
        nativeJdbc.update("UPDATE "+T+"user_permission_grant SET status='REVOKED' "
            +"WHERE user_id=?",actor.user());
        login(actor);
        assertThrows(RuntimeException.class,()->protectedService.execute(save));
        assertEquals(receipts,owner.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_station_configuration_request "
            +"WHERE facility_id=?",Integer.class,c.facilityId()));
        assertNotNull(saved.declaration());
    }

    @Test void nativeSourceAndRoleEnforcementRejectMissingInstallationAndOrdinaryDml() {
        JdbcTemplate owner=jdbc();
        NativeActor actor=nativeActor("LOCAL");
        Fixture f=fixture(owner,true);
        var c=f.declaration();
        var command=command(dz.sh.hidra.modules.topology.application.port.in
            .StationInstalledConfigurationUseCase.Operation.SAVE,c);
        var refs=unitRefs(c);
        assertEquals(Boolean.TRUE,nativeJdbc.queryForObject(
            "SELECT NOT has_table_privilege(current_user,"
            +"'public.hidra_topology_station_configuration_revision','SELECT')",
            Boolean.class));
        assertEquals(Boolean.TRUE,owner.queryForObject(
            "SELECT NOT has_table_privilege(?::name,"
            +"'public.hidra_topology_installed_unit_installation','UPDATE') "
            +"AND has_function_privilege(?::name,"
            +"'public.hidra_station_configuration_lock_sources()','EXECUTE')",
            Boolean.class,PUBLISHER,PUBLISHER));
        owner.update("UPDATE public.hidra_topology_installed_unit_installation "
            +"SET effective_until=?,revision=revision+1 WHERE installation_id=?",
            Timestamp.from(c.effectiveFrom().plusSeconds(500)),
            c.positions().getFirst().installationId());
        authorize(actor,command,refs);login(actor);
        assertThrows(RuntimeException.class,()->protectedService.execute(command));
        assertEquals(0,owner.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_station_configuration_request "
            +"WHERE facility_id=?",Integer.class,c.facilityId()));
    }


    @Test void nativeCompetingFirstEpochWritersCommitExactlyOneHeadAndImmutableReceipt() throws Exception {
        JdbcTemplate owner=jdbc();
        NativeActor actor=nativeActor("LOCAL");
        StationInstalledConfiguration first=fixture(owner,true).declaration();
        StationInstalledConfiguration rival=successor(first,1,syntheticId(),syntheticId());
        var writeA=command(dz.sh.hidra.modules.topology.application.port.in
            .StationInstalledConfigurationUseCase.Operation.SAVE,first);
        var writeB=command(dz.sh.hidra.modules.topology.application.port.in
            .StationInstalledConfigurationUseCase.Operation.SAVE,rival);
        var refs=unitRefs(first);
        authorize(actor,writeA,refs);
        authorize(actor,writeB,refs);
        login(actor);
        var bearer=org.springframework.security.core.context.SecurityContextHolder
            .getContext().getAuthentication();
        var start=new java.util.concurrent.CountDownLatch(1);
        var workers=java.util.concurrent.Executors.newFixedThreadPool(2);
        try {
            java.util.concurrent.Callable<Boolean> left=()->{
                org.springframework.security.core.context.SecurityContextHolder.getContext()
                    .setAuthentication(bearer);
                try {
                    if(!start.await(5,java.util.concurrent.TimeUnit.SECONDS))
                        throw new AssertionError("Concurrent first writer did not start");
                    try{protectedService.execute(writeA);return true;}
                    catch(dz.sh.hidra.modules.topology.application.model
                        .StationInstalledConfigurationFailure conflict){return false;}
                }finally{org.springframework.security.core.context.SecurityContextHolder.clearContext();}
            };
            java.util.concurrent.Callable<Boolean> right=()->{
                org.springframework.security.core.context.SecurityContextHolder.getContext()
                    .setAuthentication(bearer);
                try {
                    if(!start.await(5,java.util.concurrent.TimeUnit.SECONDS))
                        throw new AssertionError("Concurrent rival writer did not start");
                    try{protectedService.execute(writeB);return true;}
                    catch(dz.sh.hidra.modules.topology.application.model
                        .StationInstalledConfigurationFailure conflict){return false;}
                }finally{org.springframework.security.core.context.SecurityContextHolder.clearContext();}
            };
            var a=workers.submit(left);var b=workers.submit(right);start.countDown();
            assertEquals(1,(a.get(20,java.util.concurrent.TimeUnit.SECONDS)?1:0)
                +(b.get(20,java.util.concurrent.TimeUnit.SECONDS)?1:0));
        }finally{workers.shutdownNow();}
        assertEquals(1,owner.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_station_configuration_head "
            +"WHERE facility_id=? AND head_epoch=1",Integer.class,first.facilityId()));
        assertEquals(1,owner.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_station_configuration_revision "
            +"WHERE facility_id=?",Integer.class,first.facilityId()));
        assertEquals(1,owner.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_station_configuration_request "
            +"WHERE facility_id=?",Integer.class,first.facilityId()));
        assertEquals(3,owner.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_station_configuration_member "
            +"WHERE facility_id=?",Integer.class,first.facilityId()));
        var read=command(dz.sh.hidra.modules.topology.application.port.in
            .StationInstalledConfigurationUseCase.Operation.READ_CURRENT,first);
        var saved=nativeExecute(actor,read,refs).declaration();
        assertTrue(saved.revisionId().equals(first.revisionId())
            || saved.revisionId().equals(rival.revisionId()));
        assertEquals(1,saved.selectedEpoch());
    }

    @Test void nativeSqlAfterActualFinalGuardCannotCommitProvisionalConfiguration() {
        JdbcTemplate owner=jdbc();
        NativeActor actor=nativeActor("LOCAL");
        StationInstalledConfiguration c=fixture(owner,true).declaration();
        var request=command(dz.sh.hidra.modules.topology.application.port.in
            .StationInstalledConfigurationUseCase.Operation.SAVE,c);
        var refs=unitRefs(c);
        authorize(actor,request,refs);login(actor);
        org.mockito.Mockito.doAnswer(invocation->{
            Object result=invocation.callRealMethod();
            assertEquals(Boolean.TRUE,result);
            // Test-only injected statement after the genuine scalar final SQL guard:
            // neither a swallowed failure nor a post-guard statement may commit.
            nativeJdbc.queryForObject("SELECT 1",Integer.class);
            return result;
        }).when(nativeStoreSpy).finalGuard(org.mockito.ArgumentMatchers.any());
        try {
            assertThrows(dz.sh.hidra.modules.topology.application.model
                .StationInstalledConfigurationFailure.class,
                ()->protectedService.execute(request));
            org.mockito.Mockito.verify(nativeStoreSpy).finalGuard(
                org.mockito.ArgumentMatchers.any());
        }finally{org.mockito.Mockito.reset(nativeStoreSpy);}
        for(String suffix:List.of("revision","member","head","request"))
            assertEquals(0,owner.queryForObject(
                "SELECT count(*) FROM public.hidra_topology_station_configuration_"+suffix
                +" WHERE facility_id=?",Integer.class,c.facilityId()),suffix);
    }
}
