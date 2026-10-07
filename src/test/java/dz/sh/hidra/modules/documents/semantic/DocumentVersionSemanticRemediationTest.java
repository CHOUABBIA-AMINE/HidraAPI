/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentVersionSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.semantic
 *
 * @Description : Enforces Documents semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.documents.semantic;
import dz.sh.hidra.modules.documents.domain.model.DocumentVersion;
import dz.sh.hidra.modules.documents.domain.value.DocumentVersionStatus;
import dz.sh.hidra.modules.documents.application.command.UploadDocumentVersionCommand;
import dz.sh.hidra.modules.documents.application.port.out.*;
import dz.sh.hidra.modules.documents.application.service.DocumentsApplicationService;
import dz.sh.hidra.modules.documents.infrastructure.persistence.adapter.JpaDocumentVersionRepositoryAdapter;
import dz.sh.hidra.modules.documents.infrastructure.persistence.repository.DocumentVersionJpaRepository;
import java.lang.reflect.Proxy;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DocumentVersionSemanticRemediationTest {
    DocumentVersion version(int number,String mime,String display,String workflow,String superseding){return new DocumentVersion("version","doc",number,null,null,null,null,null,"storage",mime,"file.txt",null,0,"SHA-256","sum",null,null,null,null,DocumentVersionStatus.DRAFT,"actor",display,DocumentSemanticRemediationTest.NOW,workflow,null,superseding);}
    @Test void requiredMetadataPositiveNumberAndNormalizedSelfReference(){
        assertThrows(RuntimeException.class,()->version(0,"text/plain","Actor",null,null));
        assertThrows(RuntimeException.class,()->version(1," ","Actor",null,null));
        assertThrows(RuntimeException.class,()->version(1,"text/plain",null,null,null));
        assertThrows(RuntimeException.class,()->version(1,"text/plain","Actor",null," version "));
        assertEquals(0L,version(1,"text/plain","Actor",null,null).fileSizeBytes());
    }
    @Test void optionalWorkflowAndPopulatedSupersessionFailClosedBeforePersistence(){
        var repo=(DocumentVersionJpaRepository)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{DocumentVersionJpaRepository.class},(p,m,a)->{
            if(m.getName().equals("existsById"))return false;throw new AssertionError("Unexpected save");});
        var adapter=new JpaDocumentVersionRepositoryAdapter(repo,DocumentSemanticRemediationTest.ACTORS,id->false);
        assertThrows(IllegalArgumentException.class,()->adapter.save(version(1,"text/plain","Historical display","missing",null)));
        assertThrows(IllegalArgumentException.class,()->adapter.save(version(1,"text/plain","Historical display",null,"missing")));
    }
    @Test void genericUploadDerivesUploaderDisplayAndValidatesParent(){
        var documents=new DocumentSemanticRemediationTest.Documents();documents.saved=DocumentSemanticRemediationTest.document("Title","Actor",null);
        DocumentVersion[] captured={null};
        var versions=(DocumentVersionRepositoryPort)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{DocumentVersionRepositoryPort.class},(p,m,a)->{captured[0]=(DocumentVersion)a[0];return a[0];});
        var service=new DocumentsApplicationService(documents,versions,DocumentSemanticRemediationTest.empty(DocumentTargetLinkRepositoryPort.class),DocumentSemanticRemediationTest.ACTORS,(id,family)->{},(m,t,id)->null,new DocumentSemanticRemediationTest.Security());
        var command=new UploadDocumentVersionCommand("doc",1,null,null,null,null,null,"storage","text/plain","file.txt",null,0,"SHA-256","sum",null,null,null,null,"actor","Forged display");
        service.uploadDocumentVersion(command);assertEquals("Owner actor",captured[0].uploadedByDisplayNameSnapshot());
        documents.saved=null;assertThrows(IllegalArgumentException.class,()->service.uploadDocumentVersion(command));
    }
}
