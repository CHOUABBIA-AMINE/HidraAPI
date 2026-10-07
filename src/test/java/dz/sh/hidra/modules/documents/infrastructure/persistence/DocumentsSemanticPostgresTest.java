/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsSemanticPostgresTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence
 *
 * @Description : Enforces Documents semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import dz.sh.hidra.modules.documents.application.service.DocumentContentTransferService;
import dz.sh.hidra.modules.documents.application.command.UploadDocumentBinaryVersionCommand;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionSummaryDto;
import dz.sh.hidra.modules.documents.application.port.out.*;
import dz.sh.hidra.modules.documents.domain.model.*;
import dz.sh.hidra.modules.documents.domain.value.*;
import dz.sh.hidra.modules.identity.application.contract.documents.DocumentsActorContract;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import dz.sh.hidra.platform.security.AuthenticatedPrincipal;
import dz.sh.hidra.kernel.domain.value.ActorId;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.time.Instant;
import java.util.Optional;
import java.util.HashSet;
import java.nio.file.*;
import java.sql.*;
import java.util.concurrent.*;
@Testcontainers(disabledWithoutDocker=true)
class DocumentsSemanticPostgresTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    static final Path MIGRATIONS=Path.of("src/main/resources/db/migration");
    Connection connection()throws SQLException{return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());}
    void sql(String value)throws SQLException{try(var c=connection();var s=c.createStatement()){s.execute(value);}}
    void emptySchema()throws Exception{
        sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        sql(Files.readString(MIGRATIONS.resolve("V20260611_018__create_documents_tables.sql")));
        sql("ALTER TABLE hidra_documents_document_version ADD CONSTRAINT parent_document FOREIGN KEY(document_id) REFERENCES hidra_documents_document(id)");
        sql("ALTER TABLE hidra_documents_document_version ADD CONSTRAINT parent_storage FOREIGN KEY(storage_object_id) REFERENCES hidra_documents_storage_object(id)");
        catalog("provider","DOCUMENT_STORAGE_PROVIDER");sql(storage("storage"));
    }
    @BeforeEach void setup()throws Exception{
        emptySchema();sql(Files.readString(MIGRATIONS.resolve("V20261007_003__hmr_067_documents_document.sql")));
        sql(Files.readString(MIGRATIONS.resolve("V20261007_004__hmr_068_documents_document_version.sql")));
        sql(Files.readString(MIGRATIONS.resolve("V20261007_005__hmr_084_documents_document_target_link.sql")));
        catalog("type","DOCUMENT_TYPE");catalog("classification","DOCUMENT_CLASSIFICATION");catalog("category","DOCUMENT_CATEGORY");catalog("role","DOCUMENT_LINK_ROLE");
        sql(document("doc","D"));sql(document("other","OTHER"));
    }
    void catalog(String id,String family)throws SQLException{sql("INSERT INTO hidra_documents_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES ('"+id+"','"+family+"','"+id+"',true,0,false,now(),now())");}
    String document(String id,String code){return "INSERT INTO hidra_documents_document(id,code,title_fr,document_type_id,classification_id,confidentiality_level,status,created_by_actor_id,created_by_display_name_snapshot,created_at,updated_at) VALUES ('"+id+"','"+code+"','Title','type','classification',0,'DRAFT','actor','Actor',now(),now())";}
    String version(String id,String doc,int number){return "INSERT INTO hidra_documents_document_version(id,document_id,version_number,storage_object_id,mime_type,original_filename,file_size_bytes,checksum_algorithm,checksum_value,version_status,uploaded_by_actor_id,uploaded_by_display_name_snapshot,uploaded_at) VALUES ('"+id+"','"+doc+"',"+number+",'storage','text/plain','evidence.txt',0,'SHA-256','sum','DRAFT','actor','Actor',now())";}
    @Test void currentPointerRequiresExistingVersionOfSameDocument()throws Exception{
        sql(version("rev","doc",1));sql(version("foreign","other",1));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_documents_document SET current_version_id='missing' WHERE id='doc'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_documents_document SET current_version_id='foreign' WHERE id='doc'"));
        sql("UPDATE hidra_documents_document SET current_version_id='rev' WHERE id='doc'");
        assertThrows(SQLException.class,()->sql("UPDATE hidra_documents_document_version SET document_id='other',version_number=2 WHERE id='rev'"));
        sql("UPDATE hidra_documents_document SET current_version_id=null WHERE id='doc'");
    }
    @Test void requiredDisplayTitleOwnerTupleAndCatalogFamilies()throws Exception{
        assertThrows(SQLException.class,()->sql("UPDATE hidra_documents_document SET title_fr=' ' WHERE id='doc'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_documents_document SET created_by_display_name_snapshot=' ' WHERE id='doc'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_documents_document SET owner_module='topology' WHERE id='doc'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_documents_document SET document_type_id='classification' WHERE id='doc'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_documents_document SET document_category_id='type' WHERE id='doc'"));
        sql("UPDATE hidra_documents_document SET document_category_id='category' WHERE id='doc'");
        sql("UPDATE hidra_documents_catalog_entry SET active=false WHERE id='type'");
        assertThrows(SQLException.class,()->sql(document("new","NEW")));
    }
    int insertAfter(CountDownLatch go,String query)throws Exception{go.await(10,TimeUnit.SECONDS);try{sql(query);return 1;}catch(SQLException e){assertEquals("23505",e.getSQLState());return 0;}}
    @Test void competingDocumentCodesHaveOneWinner()throws Exception{
        var pool=Executors.newFixedThreadPool(2);var go=new CountDownLatch(1);
        try{var a=pool.submit(()->insertAfter(go,document("a","SHARED")));var b=pool.submit(()->insertAfter(go,document("b","SHARED")));go.countDown();assertEquals(1,a.get(20,TimeUnit.SECONDS)+b.get(20,TimeUnit.SECONDS));}finally{pool.shutdownNow();}
    }
    @Test void legacyCrossDocumentPointerAbortsWithoutReassignment()throws Exception{
        emptySchema();catalog("type","DOCUMENT_TYPE");catalog("classification","DOCUMENT_CLASSIFICATION");
        sql(document("doc","D"));sql(document("other","OTHER"));sql(version("foreign","other",1));
        sql("UPDATE hidra_documents_document SET current_version_id='foreign' WHERE id='doc'");
        assertThrows(SQLException.class,()->sql(Files.readString(MIGRATIONS.resolve("V20261007_003__hmr_067_documents_document.sql"))));
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT current_version_id FROM hidra_documents_document WHERE id='doc'")){assertTrue(r.next());assertEquals("foreign",r.getString(1));}
    }

    @Test void requiredVersionMetadataPositiveNumberAndNullableSupersession()throws Exception{
        assertThrows(SQLException.class,()->sql(version("zero","doc",0)));
        sql(version("v1","doc",1));assertThrows(SQLException.class,()->sql(version("duplicate","doc",1)));
        for(String field:new String[]{"mime_type","original_filename","checksum_algorithm","checksum_value","uploaded_by_display_name_snapshot"})
            assertThrows(SQLException.class,()->sql("UPDATE hidra_documents_document_version SET "+field+"=' ' WHERE id='v1'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_documents_document_version SET superseded_by_version_id='missing' WHERE id='v1'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_documents_document_version SET superseded_by_version_id='v1' WHERE id='v1'"));
        sql(version("next","doc",2));sql("UPDATE hidra_documents_document_version SET superseded_by_version_id='next' WHERE id='v1'");
    }
    @Test void competingVersionNumbersHaveOneWinner()throws Exception{
        var pool=Executors.newFixedThreadPool(2);var go=new CountDownLatch(1);
        try{var a=pool.submit(()->insertAfter(go,version("a","doc",1)));var b=pool.submit(()->insertAfter(go,version("b","doc",1)));go.countDown();assertEquals(1,a.get(20,TimeUnit.SECONDS)+b.get(20,TimeUnit.SECONDS));}finally{pool.shutdownNow();}
    }
    @Test void legacyInvalidVersionAbortsWithoutRenumbering()throws Exception{
        emptySchema();catalog("type","DOCUMENT_TYPE");catalog("classification","DOCUMENT_CLASSIFICATION");sql(document("doc","D"));sql(version("zero","doc",0));
        sql(Files.readString(MIGRATIONS.resolve("V20261007_003__hmr_067_documents_document.sql")));
        assertThrows(SQLException.class,()->sql(Files.readString(MIGRATIONS.resolve("V20261007_004__hmr_068_documents_document_version.sql"))));
        try(var c=connection();var stmt=c.createStatement();var r=stmt.executeQuery("SELECT version_number FROM hidra_documents_document_version WHERE id='zero'")){assertTrue(r.next());assertEquals(0,r.getInt(1));}
    }

    String storage(String id){return "INSERT INTO hidra_documents_storage_object(id,storage_provider_id,object_key,encrypted,content_length_bytes,content_type,checksum_algorithm,checksum_value,storage_status,created_at) VALUES ('"+id+"','provider','"+id+"',false,0,'text/plain','SHA-256','sum','AVAILABLE',now())";}
    @Test void duplicateVersionRollsBackNewStorageMetadataAndCleansOnlyNewBlob()throws Exception{transactionalUpload(false);}
    @Test void confirmedCommitFailureRollsBackMetadataAndCleansNewBlob()throws Exception{transactionalUpload(true);}
    void transactionalUpload(boolean failCommit)throws Exception{
        var ds=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
        var jdbc=new JdbcTemplate(ds);
        var manager=new DataSourceTransactionManager(ds){
            @Override protected void doCommit(DefaultTransactionStatus status){
                if(failCommit)throw new IllegalStateException("Simulated confirmed commit failure");super.doCommit(status);
            }
        };
        manager.setRollbackOnCommitFailure(true);var template=new TransactionTemplate(manager);
        if(!failCommit)sql(version("existing","doc",1));
        var blobs=new HashSet<String>();blobs.add("existing-blob");
        var binary=new DocumentBinaryStoragePort(){
            public StoredBinary store(String id,InputStream in){blobs.add(id);return new StoredBinary("provider","documents",id,false,null,0,"SHA-256","sum");}
            public void delete(String id){blobs.remove(id);}public boolean available(String id){return blobs.contains(id);}
            public InputStream open(String id){return InputStream.nullInputStream();}
        };
        var objects=new DocumentStorageObjectRepositoryPort(){
            public DocumentStorageObject save(DocumentStorageObject value){jdbc.update(storage(value.id()));return value;}
            public Optional<DocumentStorageObject> findById(String id){return Optional.empty();}public boolean isActiveStorageProvider(String id){return true;}
        };
        var documents=new DocumentRepositoryPort(){
            public Document save(Document value){return value;}
            public Optional<Document> findById(String id){return Optional.of(new Document("doc","D",null,"Title",null,"type",null,"classification",0,DocumentStatus.DRAFT,null,null,null,null,null,null,"actor","Actor",Instant.now(),Instant.now(),null));}
        };
        var security=new CurrentSecurityContext(){public Optional<AuthenticatedPrincipal> currentPrincipal(){return Optional.of(new AuthenticatedPrincipal(new ActorId("actor"),"Actor",true));}public void clear(){}};
        var versions=(DocumentVersionRepositoryPort)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{DocumentVersionRepositoryPort.class},(proxy,m,a)->Optional.empty());
        var service=new DocumentContentTransferService(binary,objects,versions,c->{
            jdbc.update(version("created","doc",c.versionNumber()).replace("'storage'","'"+c.storageObjectId()+"'"));
            return new DocumentVersionSummaryDto("created","doc",c.versionNumber(),null,c.mimeType(),c.originalFilename(),0,c.checksumValue(),DocumentVersionStatus.DRAFT,Instant.now());
        },(id,at)->Optional.of(new DocumentsActorContract.Actor(id,"Actor")),security,documents);
        var command=new UploadDocumentBinaryVersionCommand("doc",1,null,null,null,null,null,null,null,null,null,"actor","Actor","file.txt","text/plain",InputStream.nullInputStream());
        assertThrows(RuntimeException.class,()->template.execute(status->service.uploadDocumentBinaryVersion(command)));
        assertEquals(java.util.Set.of("existing-blob"),blobs);
        assertEquals(1L,jdbc.queryForObject("SELECT count(*) FROM hidra_documents_storage_object",Long.class));
        assertEquals(0L,jdbc.queryForObject("SELECT count(*) FROM hidra_documents_document_version WHERE id='created'",Long.class));
    }

    String link(String id,String versionId){return "INSERT INTO hidra_documents_target_link(id,document_id,document_version_id,target_module,target_type_code,target_id,link_role_id,primary_link,linked_by_actor_id,linked_at,active) VALUES ('"+id+"','doc',"+(versionId==null?"null":"'"+versionId+"'")+",'topology','PIPELINE','pipe','role',false,'actor',now(),true)";}
    @Test void optionalLinkedVersionMustBelongToLinkedDocument()throws Exception{
        sql(version("own","doc",1));sql(version("foreign","other",1));sql(link("document-wide",null));sql(link("specific","own"));
        assertThrows(SQLException.class,()->sql(link("wrong","foreign")));assertThrows(SQLException.class,()->sql(link("missing","missing")));
    }
    @Test void targetModuleAndActiveExactLinkRoleFamily()throws Exception{
        sql(link("link",null));assertThrows(SQLException.class,()->sql("UPDATE hidra_documents_target_link SET target_module=' ' WHERE id='link'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_documents_target_link SET link_role_id='type' WHERE id='link'"));
        sql("UPDATE hidra_documents_catalog_entry SET active=false WHERE id='role'");assertThrows(SQLException.class,()->sql(link("inactive",null)));
    }
    @Test void legacyCrossDocumentLinkAbortsWithoutReassignment()throws Exception{
        emptySchema();catalog("type","DOCUMENT_TYPE");catalog("classification","DOCUMENT_CLASSIFICATION");catalog("role","DOCUMENT_LINK_ROLE");
        sql(document("doc","D"));sql(document("other","OTHER"));sql(version("foreign","other",1));sql(link("wrong","foreign"));
        sql(Files.readString(MIGRATIONS.resolve("V20261007_003__hmr_067_documents_document.sql")));
        sql(Files.readString(MIGRATIONS.resolve("V20261007_004__hmr_068_documents_document_version.sql")));
        assertThrows(SQLException.class,()->sql(Files.readString(MIGRATIONS.resolve("V20261007_005__hmr_084_documents_document_target_link.sql"))));
        try(var c=connection();var stmt=c.createStatement();var r=stmt.executeQuery("SELECT document_version_id FROM hidra_documents_target_link WHERE id='wrong'")){assertTrue(r.next());assertEquals("foreign",r.getString(1));}
    }
}
