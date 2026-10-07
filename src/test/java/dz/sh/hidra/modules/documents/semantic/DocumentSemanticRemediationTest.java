/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentSemanticRemediationTest
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
import dz.sh.hidra.modules.documents.domain.model.*;
import dz.sh.hidra.modules.documents.domain.value.*;
import dz.sh.hidra.modules.documents.application.command.RegisterDocumentCommand;
import dz.sh.hidra.modules.documents.application.port.out.*;
import dz.sh.hidra.modules.documents.application.service.DocumentsApplicationService;
import dz.sh.hidra.modules.documents.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.documents.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.documents.infrastructure.persistence.entity.DocumentCatalogEntryJpaEntity;
import dz.sh.hidra.modules.documents.application.contract.target.DocumentsOwnedTargetLookup.Target;
import dz.sh.hidra.modules.identity.application.contract.documents.DocumentsActorContract;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import dz.sh.hidra.platform.security.AuthenticatedPrincipal;
import dz.sh.hidra.kernel.domain.value.ActorId;
import java.time.Instant;
import java.lang.reflect.Proxy;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DocumentSemanticRemediationTest {
    static final Instant NOW=Instant.parse("2026-10-07T00:00:00Z");
    static Document document(String title,String display,String current){return new Document("doc","D",null,title,null,"type",null,"classification",0,DocumentStatus.DRAFT,current,null,null,null,null,null,"actor",display,NOW,NOW,null);}
    static DocumentVersion version(String doc){return new DocumentVersion("version",doc,1,null,null,null,null,null,"storage","text/plain","evidence.txt",null,0,"SHA-256","sum",null,null,null,null,DocumentVersionStatus.DRAFT,"actor","Owner actor",NOW,null,null,null);}
    static final DocumentsActorContract ACTORS=(id,at)->"actor".equals(id)?Optional.of(new DocumentsActorContract.Actor("actor","Owner actor")):Optional.empty();
    static class Security implements CurrentSecurityContext {
        public Optional<AuthenticatedPrincipal> currentPrincipal(){return Optional.of(new AuthenticatedPrincipal(new ActorId("actor"),"User",true));}public void clear(){}
    }
    static class Documents implements DocumentRepositoryPort {
        Document saved;int saves;public Document save(Document d){saves++;return saved=d;}public Optional<Document> findById(String id){return Optional.ofNullable(saved);}
    }
    @SuppressWarnings("unchecked") static <T>T empty(Class<T> t){return (T)Proxy.newProxyInstance(t.getClassLoader(),new Class<?>[]{t},(p,m,a)->Optional.empty());}
    static RegisterDocumentCommand command(String actor,String module,String type,String id){return new RegisterDocumentCommand("D",null,"Titre",null,"type",null,"classification",0,module,type,id,"caller code","caller label",actor,"caller display");}
    @Test void requiredDisplayAndTitleFailBeforePersistence(){
        assertThrows(RuntimeException.class,()->document(" ","Actor",null));assertThrows(RuntimeException.class,()->document("Title",null,null));
    }
    @Test void registrationUsesAuthenticatedOwnerSnapshotsAndRejectsImpersonation(){
        var repository=new Documents();
        var service=new DocumentsApplicationService(repository,empty(DocumentVersionRepositoryPort.class),empty(DocumentTargetLinkRepositoryPort.class),ACTORS,(id,family)->{},(m,t,id)->new Target(id,"Owner code","Owner label"),new Security());
        service.registerDocument(command("actor","topology","PIPELINE","pipe"));
        assertEquals("Owner actor",repository.saved.createdByDisplayNameSnapshot());assertEquals("Owner code",repository.saved.ownerTargetCodeSnapshot());
        assertThrows(SecurityException.class,()->service.registerDocument(command("other",null,null,null)));
    }
    @Test void partialOwnerTupleDoesNotBecomeAnUnownedDocument(){
        var service=new DocumentsApplicationService(new Documents(),empty(DocumentVersionRepositoryPort.class),empty(DocumentTargetLinkRepositoryPort.class),ACTORS,(id,family)->{},(m,t,id)->{throw new IllegalArgumentException("partial");},new Security());
        assertThrows(IllegalArgumentException.class,()->service.registerDocument(command("actor","topology",null,null)));
        service.registerDocument(command("actor",null,null,null));
    }
    @Test void persistenceRejectsCrossDocumentCurrentVersion(){
        var repository=(DocumentJpaRepository)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{DocumentJpaRepository.class},(p,m,a)->{throw new AssertionError("Unexpected persistence");});
        var versions=(DocumentVersionRepositoryPort)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{DocumentVersionRepositoryPort.class},(p,m,a)->Optional.of(version("other")));
        var adapter=new JpaDocumentRepositoryAdapter(repository,(id,family)->{},(m,t,id)->new Target(id,null,null),ACTORS,versions);
        assertThrows(IllegalArgumentException.class,()->adapter.save(document("Title","Historical snapshot","version")));
    }
    @Test void actualCatalogAdapterRequiresActiveExactFamily(){
        DocumentCatalogEntryJpaEntity[] entry={new DocumentCatalogEntryJpaEntity("type","DOCUMENT_TYPE","TYPE",true,0,false,NOW,NOW)};
        var repo=(DocumentCatalogEntryJpaRepository)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{DocumentCatalogEntryJpaRepository.class},(p,m,a)->Optional.ofNullable(entry[0]));
        var adapter=new JpaDocumentsCatalogEligibilityAdapter(repo);adapter.requireActive("type","DOCUMENT_TYPE");
        assertThrows(IllegalArgumentException.class,()->adapter.requireActive("type","DOCUMENT_CLASSIFICATION"));
        entry[0]=new DocumentCatalogEntryJpaEntity("type","DOCUMENT_TYPE","TYPE",false,0,false,NOW,NOW);
        assertThrows(IllegalArgumentException.class,()->adapter.requireActive("type","DOCUMENT_TYPE"));
    }
}
