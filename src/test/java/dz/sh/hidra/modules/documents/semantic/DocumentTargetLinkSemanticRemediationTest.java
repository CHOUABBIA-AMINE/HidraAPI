/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentTargetLinkSemanticRemediationTest
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
import dz.sh.hidra.modules.documents.domain.model.DocumentTargetLink;
import dz.sh.hidra.modules.documents.application.command.LinkDocumentToTargetCommand;
import dz.sh.hidra.modules.documents.application.port.out.*;
import dz.sh.hidra.modules.documents.application.service.DocumentsApplicationService;
import dz.sh.hidra.modules.documents.application.contract.target.DocumentsOwnedTargetLookup.Target;
import java.lang.reflect.Proxy;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DocumentTargetLinkSemanticRemediationTest {
    DocumentTargetLink link(String module){return new DocumentTargetLink("link","doc",null,module,"PIPELINE","pipe",null,null,"role",false,"actor",DocumentSemanticRemediationTest.NOW,null,true);}
    LinkDocumentToTargetCommand command(String version,String actor){return new LinkDocumentToTargetCommand("doc",version,"topology","PIPELINE","pipe","caller","Caller","role",false,actor);}
    @Test void targetModuleRequired(){assertThrows(RuntimeException.class,()->link(" "));assertEquals("topology",link("topology").targetModule());}
    @Test void neutralOwnerSnapshotsAndAuthenticatedActorUsed(){
        var docs=new DocumentSemanticRemediationTest.Documents();docs.saved=DocumentSemanticRemediationTest.document("Title","Actor",null);DocumentTargetLink[] saved={null};
        var links=(DocumentTargetLinkRepositoryPort)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{DocumentTargetLinkRepositoryPort.class},(p,m,a)->{saved[0]=(DocumentTargetLink)a[0];return a[0];});
        var service=new DocumentsApplicationService(docs,DocumentSemanticRemediationTest.empty(DocumentVersionRepositoryPort.class),links,DocumentSemanticRemediationTest.ACTORS,(id,family)->{assertEquals("DOCUMENT_LINK_ROLE",family);},(m,t,id)->new Target(id,"Owner code","Owner label"),new DocumentSemanticRemediationTest.Security());
        service.linkDocumentToTarget(command(null,"actor"));assertEquals("Owner label",saved[0].targetLabelSnapshot());assertNull(saved[0].documentVersionId());
        assertThrows(SecurityException.class,()->service.linkDocumentToTarget(command(null,"other")));
    }
    @Test void versionOfOtherDocumentRejectedBeforeLinkSave(){
        var docs=new DocumentSemanticRemediationTest.Documents();docs.saved=DocumentSemanticRemediationTest.document("Title","Actor",null);
        var versions=(DocumentVersionRepositoryPort)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{DocumentVersionRepositoryPort.class},(p,m,a)->Optional.of(DocumentSemanticRemediationTest.version("other")));
        var service=new DocumentsApplicationService(docs,versions,DocumentSemanticRemediationTest.empty(DocumentTargetLinkRepositoryPort.class),DocumentSemanticRemediationTest.ACTORS,(id,family)->{},(m,t,id)->new Target(id,null,null),new DocumentSemanticRemediationTest.Security());
        assertThrows(IllegalArgumentException.class,()->service.linkDocumentToTarget(command("version","actor")));
    }
    @Test void missingTargetOrIneligibleRoleDeniedBeforeLinkSave(){
        var docs=new DocumentSemanticRemediationTest.Documents();docs.saved=DocumentSemanticRemediationTest.document("Title","Actor",null);
        var service=new DocumentsApplicationService(docs,DocumentSemanticRemediationTest.empty(DocumentVersionRepositoryPort.class),DocumentSemanticRemediationTest.empty(DocumentTargetLinkRepositoryPort.class),DocumentSemanticRemediationTest.ACTORS,(id,family)->{throw new IllegalArgumentException("role");},(m,t,id)->new Target(id,null,null),new DocumentSemanticRemediationTest.Security());
        assertThrows(IllegalArgumentException.class,()->service.linkDocumentToTarget(command(null,"actor")));
        var missing=new DocumentsApplicationService(docs,DocumentSemanticRemediationTest.empty(DocumentVersionRepositoryPort.class),DocumentSemanticRemediationTest.empty(DocumentTargetLinkRepositoryPort.class),DocumentSemanticRemediationTest.ACTORS,(id,family)->{},(m,t,id)->{throw new IllegalArgumentException("missing");},new DocumentSemanticRemediationTest.Security());
        assertThrows(IllegalArgumentException.class,()->missing.linkDocumentToTarget(command(null,"actor")));
    }
}
