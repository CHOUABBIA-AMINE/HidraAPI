/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditEventSemanticRemediationTest
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
import dz.sh.hidra.modules.audit.application.port.out.*;
import dz.sh.hidra.modules.audit.domain.model.*;
import dz.sh.hidra.modules.audit.domain.value.*;
import dz.sh.hidra.modules.audit.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.*;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AuditEventSemanticRemediationTest {
    static AuditEvent event(String id,String source,String target,String targetType,String severity,String reason,String reasonText,String payload) {
        return new AuditEvent(id,
                "type",
                "category",
                severity,
                source,
                null,
                null,
                "OBSERVED",
                null,
                AuditEventStatus.RECORDED,
                null,
                AuditActorType.SYSTEM,
                null,
                null,
                null,
                null,
                null,
                null,
                target,
                targetType,
                "asset",
                null,
                null,
                AuditOperation.READ,
                null,
                reason,
                reasonText,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                Instant.now(),
                Instant.now(),
                null,
                null,
                null,
                payload);
    }

    @Test void requiredModuleAndTypeEvidenceFailsFast(){
        assertThrows(RuntimeException.class,()->event("id"," ","audit","ASSET",null,null,null,null));
        assertThrows(RuntimeException.class,()->event("id","audit",null,"ASSET",null,null,null,null));
        assertThrows(RuntimeException.class,()->event("id","audit","audit"," ",null,null,null,null));
        assertNull(event("id","audit","audit","ASSET",null,null,null,null).actorId());
    }
    @Test void catalogFamiliesAreValidatedIndependentlyBeforePersist(){
        var catalogs=mock(AuditCatalogEligibilityPort.class);var em=mock(EntityManager.class);
        var adapter=new JpaAuditEventRepositoryAdapter(mock(AuditEventJpaRepository.class),em,catalogs,new AuditInputPolicy());
        adapter.save(event("id","audit","audit","ASSET","severity","reason",null,null));
        verify(catalogs).requireActive("type","EVENT_TYPE");verify(catalogs).requireActive("category","EVENT_CATEGORY");
        verify(catalogs).requireActive("severity","SEVERITY");verify(catalogs).requireActive("reason","DECISION_REASON");
        verify(em).persist(any());verify(em).flush();
    }
    @Test void invalidCatalogStopsBeforeInsert(){
        var catalogs=mock(AuditCatalogEligibilityPort.class);var em=mock(EntityManager.class);
        doThrow(new IllegalArgumentException("wrong family")).when(catalogs).requireActive("category","EVENT_CATEGORY");
        var adapter=new JpaAuditEventRepositoryAdapter(mock(AuditEventJpaRepository.class),em,catalogs,new AuditInputPolicy());
        assertThrows(RuntimeException.class,()->adapter.save(event("id","audit","audit","ASSET",null,null,null,null)));
        verify(em,never()).persist(any());
    }
    @Test void genericRepositoryCannotBypassPayloadOrFreeTextSanitation(){
        var adapter=new JpaAuditEventRepositoryAdapter(mock(AuditEventJpaRepository.class),mock(EntityManager.class),mock(AuditCatalogEligibilityPort.class),new AuditInputPolicy());
        var saved=adapter.save(event("id","audit","audit","ASSET",null,null,"Routine", "{\"credential\":\"raw\"}"));
        assertFalse(saved.payloadJson().contains("raw"));assertEquals("Routine",saved.reasonText());
        assertThrows(RuntimeException.class,()->adapter.save(event("id","audit","audit","ASSET",null,null,"password=abc",null)));
        assertThrows(RuntimeException.class,()->adapter.save(event("id","audit","audit","ASSET",null,null,null,"{} {}")));
    }
    @Test void insertFailuresPropagateWithoutMergeFallback(){
        var em=mock(EntityManager.class);var repo=mock(AuditEventJpaRepository.class);
        doThrow(new IllegalStateException("duplicate")).when(em).flush();
        var adapter=new JpaAuditEventRepositoryAdapter(repo,em,mock(AuditCatalogEligibilityPort.class),new AuditInputPolicy());
        assertThrows(IllegalStateException.class,()->adapter.save(event("id","audit","audit","ASSET",null,null,null,null)));
        verify(repo,never()).save(any());
    }
}
