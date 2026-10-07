/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentContentTransferServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.service
 *
 * @Description : Enforces Documents semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.documents.application.service;
import dz.sh.hidra.modules.documents.application.command.UploadDocumentBinaryVersionCommand;
import dz.sh.hidra.modules.documents.application.port.out.*;
import dz.sh.hidra.modules.documents.application.port.in.UploadDocumentVersionUseCase;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionSummaryDto;
import dz.sh.hidra.modules.documents.domain.model.*;
import dz.sh.hidra.modules.documents.domain.value.*;
import dz.sh.hidra.modules.identity.application.contract.documents.DocumentsActorContract;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import dz.sh.hidra.platform.security.AuthenticatedPrincipal;
import dz.sh.hidra.kernel.domain.value.ActorId;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.time.Instant;
import java.lang.reflect.Proxy;
import java.io.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DocumentContentTransferServiceTest {
    static final Instant NOW=Instant.parse("2026-10-07T00:00:00Z");
    static class Binary implements DocumentBinaryStoragePort {
        int stores,deletes;boolean failStore,failDelete;String storedId,deletedId;
        public StoredBinary store(String id,InputStream content){stores++;storedId=id;if(failStore)throw new IllegalArgumentException("partial store");return new StoredBinary("provider","documents",id,false,null,0,"SHA-256","sum");}
        public InputStream open(String id){return InputStream.nullInputStream();}public boolean available(String id){return true;}
        public void delete(String id){deletes++;deletedId=id;if(failDelete)throw new IllegalStateException("cleanup failed");}
    }
    static class Storage implements DocumentStorageObjectRepositoryPort {
        boolean active=true;int saves;public DocumentStorageObject save(DocumentStorageObject value){saves++;return value;}
        public Optional<DocumentStorageObject> findById(String id){return Optional.empty();}public boolean isActiveStorageProvider(String id){return active;}
    }
    static class Documents implements DocumentRepositoryPort {
        boolean exists=true;public Document save(Document value){return value;}
        public Optional<Document> findById(String id){return exists?Optional.of(new Document("doc","D",null,"Title",null,"type",null,"classification",0,DocumentStatus.DRAFT,null,null,null,null,null,null,"actor","Actor",NOW,NOW,null)):Optional.empty();}
    }
    static class Security implements CurrentSecurityContext {
        public Optional<AuthenticatedPrincipal> currentPrincipal(){return Optional.of(new AuthenticatedPrincipal(new ActorId("actor"),"Actor",true));}public void clear(){}
    }
    static UploadDocumentBinaryVersionCommand command(String actor,int number){return new UploadDocumentBinaryVersionCommand("doc",number,null,null,null,null,null,null,null,null,null,actor,"caller","file.txt","text/plain",InputStream.nullInputStream());}
    static final DocumentsActorContract ACTORS=(id,at)->Optional.of(new DocumentsActorContract.Actor(id,"Canonical actor"));
    static DocumentVersionSummaryDto summary(){return new DocumentVersionSummaryDto("version","doc",1,null,"text/plain","file.txt",0,"sum",DocumentVersionStatus.DRAFT,NOW);}
    DocumentContentTransferService service(Binary binary,Storage storage,Documents documents,UploadDocumentVersionUseCase upload){
        var versions=(DocumentVersionRepositoryPort)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{DocumentVersionRepositoryPort.class},(p,m,a)->Optional.empty());
        return new DocumentContentTransferService(binary,storage,versions,upload,ACTORS,new Security(),documents);
    }
    void begin(){TransactionSynchronizationManager.initSynchronization();TransactionSynchronizationManager.setActualTransactionActive(true);}
    void complete(int status){for(var sync:TransactionSynchronizationManager.getSynchronizations())sync.afterCompletion(status);}
    @Test void rejectsInactiveOrWrongFamilyStorageProviderAndDeletesStoredBinary(){
        begin();try{var b=new Binary();var s=new Storage();s.active=false;
            assertThrows(RuntimeException.class,()->service(b,s,new Documents(),c->summary()).uploadDocumentBinaryVersion(command("actor",1)));
            complete(TransactionSynchronization.STATUS_ROLLED_BACK);assertEquals(1,b.deletes);assertEquals(b.storedId,b.deletedId);assertEquals(0,s.saves);
        }finally{TransactionSynchronizationManager.clear();}
    }
    @Test void authParentAndVersionMetadataFailBeforeStorage(){
        begin();try{var b=new Binary();var s=new Storage();var docs=new Documents();var service=service(b,s,docs,c->summary());
            assertThrows(SecurityException.class,()->service.uploadDocumentBinaryVersion(command("other",1)));
            assertThrows(RuntimeException.class,()->service.uploadDocumentBinaryVersion(command("actor",0)));
            docs.exists=false;assertThrows(RuntimeException.class,()->service.uploadDocumentBinaryVersion(command("actor",1)));assertEquals(0,b.stores);
        }finally{TransactionSynchronizationManager.clear();}
    }
    @Test void failedVersionAndPartialStoreDeleteOnlyNewBlobAndKeepOriginalFailure(){
        begin();try{var b=new Binary();var s=new Storage();b.failDelete=true;var primary=new IllegalArgumentException("duplicate version");
            var error=assertThrows(IllegalArgumentException.class,()->service(b,s,new Documents(),c->{throw primary;}).uploadDocumentBinaryVersion(command("actor",1)));
            assertEquals(primary,error);assertEquals(1,error.getSuppressed().length);assertEquals(b.storedId,b.deletedId);
            complete(TransactionSynchronization.STATUS_ROLLED_BACK);assertEquals(1,b.deletes);
        }finally{TransactionSynchronizationManager.clear();}
        begin();try{var b=new Binary();b.failStore=true;
            assertThrows(IllegalArgumentException.class,()->service(b,new Storage(),new Documents(),c->summary()).uploadDocumentBinaryVersion(command("actor",1)));assertEquals(1,b.deletes);
        }finally{TransactionSynchronizationManager.clear();}
    }
    @Test void confirmedRollbackAfterSuccessfulMethodCleansBlob(){
        begin();try{var b=new Binary();service(b,new Storage(),new Documents(),c->{assertEquals("Canonical actor",c.uploadedByDisplayNameSnapshot());return summary();}).uploadDocumentBinaryVersion(command("actor",1));
            assertEquals(0,b.deletes);complete(TransactionSynchronization.STATUS_ROLLED_BACK);assertEquals(1,b.deletes);
        }finally{TransactionSynchronizationManager.clear();}
    }
    @Test void committedAndUnknownOutcomesPreservePossiblyReferencedContent(){
        for(int status:new int[]{TransactionSynchronization.STATUS_COMMITTED,TransactionSynchronization.STATUS_UNKNOWN}){
            begin();try{var b=new Binary();service(b,new Storage(),new Documents(),c->summary()).uploadDocumentBinaryVersion(command("actor",1));complete(status);assertEquals(0,b.deletes);}
            finally{TransactionSynchronizationManager.clear();}
        }
    }
    @Test void unproxiedNontransactionalUploadFailsBeforeStorage(){
        TransactionSynchronizationManager.clear();var b=new Binary();
        assertThrows(IllegalStateException.class,()->service(b,new Storage(),new Documents(),c->summary()).uploadDocumentBinaryVersion(command("actor",1)));assertEquals(0,b.stores);
    }
}
