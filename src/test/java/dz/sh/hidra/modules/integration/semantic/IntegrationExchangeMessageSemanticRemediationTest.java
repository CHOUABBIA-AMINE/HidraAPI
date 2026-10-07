/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationExchangeMessageSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.semantic
 *
 * @Description : Enforces Integration evidence integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.integration.semantic;
import dz.sh.hidra.modules.integration.domain.model.IntegrationExchangeMessage;
import dz.sh.hidra.modules.integration.domain.value.*;
import dz.sh.hidra.modules.integration.infrastructure.persistence.adapter.JpaIntegrationExchangeMessageRepositoryAdapter;
import dz.sh.hidra.modules.integration.infrastructure.persistence.entity.*;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.*;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class IntegrationExchangeMessageSemanticRemediationTest {
    IntegrationExchangeMessage message(String run,String endpoint){return new IntegrationExchangeMessage("id",run,"system",endpoint,IntegrationDirection.INBOUND,"type",null,"format",PayloadStorageMode.HASH_ONLY,null,null,"hash",0L,Instant.EPOCH,null,ExchangeMessageStatus.RECEIVED,Instant.EPOCH);}
    final IntegrationExchangeMessageJpaRepository messages=mock(IntegrationExchangeMessageJpaRepository.class);
    final IntegrationJobRunJpaRepository runs=mock(IntegrationJobRunJpaRepository.class);
    final ExternalEndpointJpaRepository endpoints=mock(ExternalEndpointJpaRepository.class);
    final IntegrationCatalogEntryJpaRepository catalogs=mock(IntegrationCatalogEntryJpaRepository.class);
    JpaIntegrationExchangeMessageRepositoryAdapter adapter(){
        catalog("type","MESSAGE_TYPE",true);catalog("format","PAYLOAD_FORMAT",true);
        when(messages.saveAndFlush(any())).thenAnswer(i->i.getArgument(0));
        return new JpaIntegrationExchangeMessageRepositoryAdapter(messages,runs,endpoints,catalogs);
    }
    void catalog(String id,String family,boolean active){var row=mock(IntegrationCatalogEntryJpaEntity.class);when(row.catalogName()).thenReturn(family);when(row.active()).thenReturn(active);when(catalogs.findById(id)).thenReturn(Optional.of(row));}
    @Test void absentOptionalReferencesRemainLegal(){
        var saved=adapter().save(message(null,null));assertNull(saved.jobRunId());assertNull(saved.endpointId());verify(runs,never()).existsById(anyString());verify(endpoints,never()).findById(anyString());
    }
    @Test void unknownOptionalRunFailsBeforeSave(){
        var adapter=adapter();assertThrows(RuntimeException.class,()->adapter.save(message("missing",null)));verify(messages,never()).saveAndFlush(any());
    }
    @Test void unknownOrForeignEndpointFailsBeforeSave(){
        var adapter=adapter();assertThrows(RuntimeException.class,()->adapter.save(message(null,"missing")));
        var endpoint=mock(ExternalEndpointJpaEntity.class);when(endpoint.externalSystemId()).thenReturn("other");when(endpoints.findById("foreign")).thenReturn(Optional.of(endpoint));
        assertThrows(RuntimeException.class,()->adapter.save(message(null,"foreign")));verify(messages,never()).saveAndFlush(any());
    }
    @Test void populatedRunAndCorrelatedEndpointAreAccepted(){
        var adapter=adapter();when(runs.existsById("run")).thenReturn(true);var endpoint=mock(ExternalEndpointJpaEntity.class);when(endpoint.externalSystemId()).thenReturn("system");when(endpoints.findById("endpoint")).thenReturn(Optional.of(endpoint));
        assertEquals("endpoint",adapter.save(message("run","endpoint")).endpointId());verify(messages).saveAndFlush(any());
    }
    @Test void wrongInactiveAndUnknownCatalogFamiliesFailClosed(){
        var adapter=adapter();catalog("type","JOB_TYPE",true);assertThrows(RuntimeException.class,()->adapter.save(message(null,null)));
        catalog("type","MESSAGE_TYPE",false);assertThrows(RuntimeException.class,()->adapter.save(message(null,null)));
        catalog("type","MESSAGE_TYPE",true);catalog("format","MESSAGE_TYPE",true);assertThrows(RuntimeException.class,()->adapter.save(message(null,null)));
        when(catalogs.findById("format")).thenReturn(Optional.empty());assertThrows(RuntimeException.class,()->adapter.save(message(null,null)));verify(messages,never()).saveAndFlush(any());
    }
}
