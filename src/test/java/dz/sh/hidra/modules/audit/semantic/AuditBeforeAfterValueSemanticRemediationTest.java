/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditBeforeAfterValueSemanticRemediationTest
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
import dz.sh.hidra.modules.audit.domain.model.AuditBeforeAfterValue;
import dz.sh.hidra.modules.audit.domain.value.AuditValueType;
import dz.sh.hidra.modules.audit.application.service.AuditInputPolicy;
import dz.sh.hidra.modules.audit.application.port.out.AuditCatalogEligibilityPort;
import dz.sh.hidra.modules.audit.infrastructure.persistence.adapter.JpaAuditBeforeAfterValueRepositoryAdapter;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.*;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AuditBeforeAfterValueSemanticRemediationTest {
    AuditBeforeAfterValue value(String field,boolean masked,AuditValueType type,String text,String reason){
        return new AuditBeforeAfterValue("id","event",field,null,type,text,null,"hash",null,masked,reason,false,Instant.now());
    }
    @Test void fieldPathIsRequired(){assertThrows(RuntimeException.class,()->value(" ",false,AuditValueType.STRING,null,null));}
    @Test void maskedAndSensitiveRawTextAreRejected(){
        assertThrows(RuntimeException.class,()->value("pressure",true,AuditValueType.STRING,"raw",null));
        assertThrows(RuntimeException.class,()->value("pressure",false,AuditValueType.MASKED,"raw",null));
        assertThrows(RuntimeException.class,()->value("actor.api-key",false,AuditValueType.STRING,"raw",null));
        assertThrows(RuntimeException.class,()->value("actor.private_key",false,AuditValueType.STRING,"raw",null));
    }
    @Test void hashOnlyMaskedEvidenceAndUnchangedRowsRemainLegal(){
        var row=value("actor.token",true,AuditValueType.MASKED,null,null);assertEquals("hash",row.beforeValueHash());assertFalse(row.changed());
        assertEquals("raw pressure",value("pressure",false,AuditValueType.STRING,"raw pressure",null).beforeValueText());
    }
    @Test void parentAndOptionalMaskReasonAreValidatedBeforeInsert(){
        var events=mock(AuditEventJpaRepository.class);var catalogs=mock(AuditCatalogEligibilityPort.class);var em=mock(EntityManager.class);
        var adapter=new JpaAuditBeforeAfterValueRepositoryAdapter(mock(AuditBeforeAfterValueJpaRepository.class),em,events,catalogs,new AuditInputPolicy());
        assertThrows(RuntimeException.class,()->adapter.save(value("pressure",false,AuditValueType.STRING,null,null)));
        when(events.existsById("event")).thenReturn(true);doThrow(new IllegalArgumentException("wrong family")).when(catalogs).requireActive("reason","MASK_REASON");
        assertThrows(RuntimeException.class,()->adapter.save(value("pressure",true,AuditValueType.MASKED,null,"reason")));verify(em,never()).persist(any());
    }
    @Test void optionalReasonRemainsAbsentAndRepositoryCannotMerge(){
        var events=mock(AuditEventJpaRepository.class);var catalogs=mock(AuditCatalogEligibilityPort.class);var em=mock(EntityManager.class);var repo=mock(AuditBeforeAfterValueJpaRepository.class);
        when(events.existsById("event")).thenReturn(true);
        var adapter=new JpaAuditBeforeAfterValueRepositoryAdapter(repo,em,events,catalogs,new AuditInputPolicy());
        adapter.save(value("pressure",false,AuditValueType.STRING,"12",null));verify(catalogs,never()).requireActive(anyString(),anyString());
        doThrow(new IllegalStateException("duplicate")).when(em).flush();assertThrows(IllegalStateException.class,()->adapter.save(value("pressure",false,AuditValueType.STRING,"13",null)));verify(repo,never()).save(any());
    }
    @Test void unmaskedTextStillRejectsRecognizableCredentials(){
        var events=mock(AuditEventJpaRepository.class);when(events.existsById("event")).thenReturn(true);
        var adapter=new JpaAuditBeforeAfterValueRepositoryAdapter(mock(AuditBeforeAfterValueJpaRepository.class),mock(EntityManager.class),events,mock(AuditCatalogEligibilityPort.class),new AuditInputPolicy());
        assertThrows(RuntimeException.class,()->adapter.save(value("comment",false,AuditValueType.STRING,"password=abc",null)));
    }
}
