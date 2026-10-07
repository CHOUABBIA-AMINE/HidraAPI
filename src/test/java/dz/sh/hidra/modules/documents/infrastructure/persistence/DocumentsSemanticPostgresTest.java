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
    }
    @BeforeEach void setup()throws Exception{
        emptySchema();sql(Files.readString(MIGRATIONS.resolve("V20261007_003__hmr_067_documents_document.sql")));
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
}
