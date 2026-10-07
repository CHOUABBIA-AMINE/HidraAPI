/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditAccessRecordSemanticRemediationTest
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
import dz.sh.hidra.modules.audit.domain.model.AuditAccessRecord;
import dz.sh.hidra.modules.audit.domain.value.AuditAccessType;
import dz.sh.hidra.modules.audit.infrastructure.persistence.adapter.JpaAuditAccessRecordRepositoryAdapter;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.*;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AuditAccessRecordSemanticRemediationTest {
    AuditAccessRecord record(String event,String export){return new AuditAccessRecord("id","actor",null,AuditAccessType.VIEW,event,null,export,null,null,Instant.now(),null);}
    @Test void absentOptionalReferencesRemainLegal(){
        var events=mock(AuditEventJpaRepository.class);var exports=mock(AuditExportRequestJpaRepository.class);var em=mock(EntityManager.class);
        var adapter=new JpaAuditAccessRecordRepositoryAdapter(mock(AuditAccessRecordJpaRepository.class),em,events,exports);
        assertNull(adapter.save(record(null,null)).auditEventId());verify(events,never()).existsById(anyString());verify(exports,never()).existsById(anyString());verify(em).persist(any());
    }
    @Test void unknownEventAndExportFailBeforePersist(){
        var events=mock(AuditEventJpaRepository.class);var exports=mock(AuditExportRequestJpaRepository.class);var em=mock(EntityManager.class);
        var adapter=new JpaAuditAccessRecordRepositoryAdapter(mock(AuditAccessRecordJpaRepository.class),em,events,exports);
        assertThrows(RuntimeException.class,()->adapter.save(record("missing",null)));
        assertThrows(RuntimeException.class,()->adapter.save(record(null,"missing")));verify(em,never()).persist(any());
    }
    @Test void populatedReferencesResolveAndInsertFailuresCannotMerge(){
        var events=mock(AuditEventJpaRepository.class);var exports=mock(AuditExportRequestJpaRepository.class);var em=mock(EntityManager.class);var repo=mock(AuditAccessRecordJpaRepository.class);
        when(events.existsById("event")).thenReturn(true);when(exports.existsById("export")).thenReturn(true);
        var adapter=new JpaAuditAccessRecordRepositoryAdapter(repo,em,events,exports);adapter.save(record("event","export"));
        verify(events).existsById("event");verify(exports).existsById("export");verify(em).flush();
        doThrow(new IllegalStateException("duplicate")).when(em).persist(any());
        assertThrows(IllegalStateException.class,()->adapter.save(record("event","export")));verify(repo,never()).save(any());
    }
}
