/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditExportRequestSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.semantic
 *
 * @Description : Enforces Audit evidence integrity and explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.audit.semantic;
import dz.sh.hidra.modules.audit.application.service.*;
import dz.sh.hidra.modules.audit.application.command.*;
import dz.sh.hidra.modules.audit.application.port.out.*;
import dz.sh.hidra.modules.audit.domain.model.*;
import dz.sh.hidra.modules.audit.domain.value.*;
import dz.sh.hidra.modules.audit.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.*;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AuditExportRequestSemanticRemediationTest {
    final AuditInputPolicy policy=new AuditInputPolicy();
    AuditExportRequest request(String filter,String format,String workflow,String doc) {
        return new AuditExportRequest("export","actor","Actor","purpose",filter,format,AuditExportStatus.REQUESTED,
                workflow,doc,null,null,Instant.now(),null,null);
    }
    @Test void requiredMetadataIsRejectedBeforePersistence(){
        assertThrows(RuntimeException.class,()->request(" ","JSON",null,null));
        assertThrows(RuntimeException.class,()->request("{}",null,null,null));
    }
    @Test void nestedSensitiveKeysAreRedactedWithoutLosingBenignCriteria(){
        String clean=policy.json("{\"nested\":[{\"API-Key\":\"raw-secret\",\"pipeline\":\"GK3\"}],\"session_cookie\":\"cookie-value\"}",true);
        assertTrue(clean.contains("GK3"));assertTrue(clean.contains("[REDACTED]"));
        assertFalse(clean.contains("raw-secret"));assertFalse(clean.contains("cookie-value"));
        assertEquals(clean,policy.json(clean,true));
    }
    @Test void malformedDuplicateTrailingScalarAndTooDeepJsonAreRejected(){
        for(String value:new String[]{"{","{\"a\":1,\"a\":2}","{} {}","{\"password=raw\":0}","null","5","[".repeat(33)+"0"+"]".repeat(33)})
            assertThrows(RuntimeException.class,()->policy.json(value,true));
        assertNotNull(policy.json("[".repeat(32)+"0"+"]".repeat(32),true));
    }
    @Test void utf8BudgetAndCredentialPatternsAreEnforced(){
        assertNotNull(policy.json("[\""+"é".repeat(32766)+"\"]",true));
        assertThrows(RuntimeException.class,()->policy.json("[\""+"é".repeat(32767)+"\"]",true));
        for(String value:new String[]{"password=abc","Bearer abc.def","Basic dXNlcjpwYXNz","-----BEGIN RSA PRIVATE KEY-----","api-key: abc"})
            assertThrows(RuntimeException.class,()->policy.text(value,1000));
        assertEquals("Routine observation",policy.text(" Routine observation ",1000));
        assertEquals("Basic station inspection",policy.text("Basic station inspection",1000));
        assertThrows(RuntimeException.class,()->policy.text("x".repeat(1001),1000));
    }
    @Test void ownerReferencesAndPurposeFailBeforePersist(){
        var repo=mock(AuditExportRequestJpaRepository.class);var em=mock(EntityManager.class);
        AuditCatalogEligibilityPort catalogs=mock(AuditCatalogEligibilityPort.class);
        AuditDocumentReferencePort documents=id->false;AuditWorkflowReferencePort workflows=id->false;
        var adapter=new JpaAuditExportRequestRepositoryAdapter(repo,em,catalogs,documents,workflows,policy);
        assertThrows(RuntimeException.class,()->adapter.save(request("{}","JSON","unknown",null)));
        assertThrows(RuntimeException.class,()->adapter.save(request("{}","JSON",null,"unknown")));
        doThrow(new IllegalArgumentException("inactive")).when(catalogs).requireActive("purpose","EXPORT_PURPOSE");
        assertThrows(RuntimeException.class,()->adapter.save(request("{}","JSON",null,null)));
        verify(em,never()).persist(any());
    }
    @Test void optionalOwnersRemainOptionalAndGenericWritesSanitize(){
        var em=mock(EntityManager.class);var catalogs=mock(AuditCatalogEligibilityPort.class);
        var adapter=new JpaAuditExportRequestRepositoryAdapter(mock(AuditExportRequestJpaRepository.class),em,catalogs,id->false,id->false,policy);
        var saved=adapter.save(request("{\"token\":\"raw\"}","JSON",null,null));
        assertFalse(saved.filterJson().contains("raw"));verify(catalogs).requireActive("purpose","EXPORT_PURPOSE");verify(em).persist(any());verify(em).flush();
    }
    @Test void lifecycleIsNotApprovedByExistence(){
        var adapter=new JpaAuditExportRequestRepositoryAdapter(mock(AuditExportRequestJpaRepository.class),mock(EntityManager.class),
                mock(AuditCatalogEligibilityPort.class),id->true,id->true,policy);
        var r=request("{}","JSON","known",null);
        var approved=new AuditExportRequest(r.id(),r.requestedByActorId(),null,r.purposeId(),r.filterJson(),r.format(),
                AuditExportStatus.APPROVED,r.workflowInstanceId(),null,null,null,r.requestedAt(),null,null);
        assertThrows(RuntimeException.class,()->adapter.save(approved));
    }
    @Test void requestCreatesExactlyOneAccessEvidenceAndPropagatesFailure(){
        var requests=mock(AuditExportRequestRepositoryPort.class);var access=mock(AuditAccessRecordRepositoryPort.class);
        when(requests.save(any())).thenAnswer(i->i.getArgument(0));
        var service=new AuditApplicationService(mock(AuditEventRepositoryPort.class),requests,access,policy);
        var result=service.requestAuditExport(new RequestAuditExportCommand("actor","Actor","purpose","{\"token\":\"raw\"}","JSON",null));
        var captured=org.mockito.ArgumentCaptor.forClass(AuditAccessRecord.class);verify(access).save(captured.capture());
        var evidence=captured.getValue();assertEquals(result.id(),evidence.exportRequestId());assertEquals(AuditAccessType.EXPORT,evidence.accessType());
        assertNull(evidence.resultCount());assertEquals(policy.hash(policy.json("{\"token\":\"raw\"}",true)),evidence.searchFilterHash());
        doThrow(new IllegalStateException("evidence unavailable")).when(access).save(any());
        assertThrows(IllegalStateException.class,()->service.requestAuditExport(new RequestAuditExportCommand("actor",null,"purpose","{}","JSON",null)));
    }
}
