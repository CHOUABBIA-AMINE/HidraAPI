/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationDeadLetterRecordSemanticRemediationTest
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
import dz.sh.hidra.modules.integration.domain.model.IntegrationDeadLetterRecord;
import dz.sh.hidra.modules.integration.domain.value.DeadLetterStatus;
import dz.sh.hidra.modules.integration.infrastructure.persistence.adapter.JpaIntegrationDeadLetterRecordRepositoryAdapter;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.identity.application.contract.integration.IntegrationResolverContract;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import dz.sh.hidra.platform.security.AuthenticatedPrincipal;
import dz.sh.hidra.kernel.domain.value.ActorId;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class IntegrationDeadLetterRecordSemanticRemediationTest {
    IntegrationDeadLetterRecord record(String stage,String reason,String actor,Instant at,String comment,DeadLetterStatus status){
        return new IntegrationDeadLetterRecord("id","system",null,null,null,null,null,stage,"FAILED",reason,null,null,status,actor,at,comment,Instant.EPOCH,Instant.EPOCH);
    }
    IntegrationDeadLetterRecord optional(String run,String message,String in,String out){
        return new IntegrationDeadLetterRecord("id","system",run,message,in,out,null,"PARSE","FAILED","Failure",null,null,DeadLetterStatus.OPEN,null,null,null,Instant.EPOCH,Instant.EPOCH);
    }
    final IntegrationDeadLetterRecordJpaRepository records=mock(IntegrationDeadLetterRecordJpaRepository.class);
    final IntegrationJobRunJpaRepository runs=mock(IntegrationJobRunJpaRepository.class);
    final IntegrationExchangeMessageJpaRepository messages=mock(IntegrationExchangeMessageJpaRepository.class);
    final IntegrationInboundRecordJpaRepository inbound=mock(IntegrationInboundRecordJpaRepository.class);
    final IntegrationOutboundRecordJpaRepository outbound=mock(IntegrationOutboundRecordJpaRepository.class);
    final IntegrationResolverContract resolvers=mock(IntegrationResolverContract.class);
    final CurrentSecurityContext security=mock(CurrentSecurityContext.class);
    JpaIntegrationDeadLetterRecordRepositoryAdapter adapter(){
        when(records.saveAndFlush(any())).thenAnswer(i->i.getArgument(0));
        return new JpaIntegrationDeadLetterRecordRepositoryAdapter(records,runs,messages,inbound,outbound,resolvers,security);
    }
    void authenticated(String actor){when(security.currentPrincipal()).thenReturn(Optional.of(new AuthenticatedPrincipal(ActorId.of(actor),actor,true)));}
    @Test void requiredFailureEvidenceFailsFast(){
        assertThrows(RuntimeException.class,()->record(" ","Failure",null,null,null,DeadLetterStatus.OPEN));
        assertThrows(RuntimeException.class,()->record("PARSE",null,null,null,null,DeadLetterStatus.OPEN));
    }
    @Test void allSixPartialManualCombinationsFailAfterNormalization(){
        for(int bits=1;bits<7;bits++){
            String actor=(bits&1)!=0?"actor":null;Instant at=(bits&2)!=0?Instant.EPOCH:null;String comment=(bits&4)!=0?"Resolved":null;
            assertThrows(RuntimeException.class,()->record("PARSE","Failure",actor,at,comment,DeadLetterStatus.RESOLVED));
        }
        assertThrows(RuntimeException.class,()->record("PARSE","Failure","actor",Instant.EPOCH," ",DeadLetterStatus.RESOLVED));
    }
    @Test void absentAndCompleteTrioRetainIndependentStatusSemantics(){
        for(var status:DeadLetterStatus.values())assertFalse(record("PARSE","Failure",null,null,null,status).hasManualResolution());
        assertTrue(record("PARSE","Failure","actor",Instant.EPOCH,"Resolved",DeadLetterStatus.RESOLVED).hasManualResolution());
        assertTrue(record("PARSE","Failure",null,null,null,DeadLetterStatus.OPEN).replayable());
        assertFalse(record("PARSE","Failure",null,null,null,DeadLetterStatus.REPLAYED).replayable());
    }
    @Test void everyUnknownOptionalEvidenceIdFailsBeforeSave(){
        var adapter=adapter();
        for(var row:new IntegrationDeadLetterRecord[]{optional("missing",null,null,null),optional(null,"missing",null,null),optional(null,null,"missing",null),optional(null,null,null,"missing")})
            assertThrows(RuntimeException.class,()->adapter.save(row));
        verify(records,never()).saveAndFlush(any());
    }
    @Test void absentAndExistingReferencesAreAccepted(){
        var adapter=adapter();assertNull(adapter.save(optional(null,null,null,null)).jobRunId());
        when(runs.existsById("run")).thenReturn(true);when(messages.existsById("message")).thenReturn(true);when(inbound.existsById("in")).thenReturn(true);when(outbound.existsById("out")).thenReturn(true);
        assertEquals("run",adapter.save(optional("run","message","in","out")).jobRunId());
    }
    @Test void newManualEvidenceRequiresAuthenticationMatchingAndCurrentEligibility(){
        var adapter=adapter();var row=record("PARSE","Failure","actor",Instant.EPOCH,"Resolved",DeadLetterStatus.RESOLVED);
        assertThrows(RuntimeException.class,()->adapter.save(row));authenticated("other");assertThrows(RuntimeException.class,()->adapter.save(row));
        authenticated("actor");assertThrows(RuntimeException.class,()->adapter.save(row));
        when(resolvers.eligibleResolver(eq("actor"),any())).thenAnswer(i->{Instant time=i.getArgument(1);assertTrue(time.isAfter(Instant.EPOCH));return true;});
        assertTrue(adapter.save(row).hasManualResolution());
    }
    @Test void unauthenticatedPrincipalCannotRecordManualEvidence(){
        var adapter=adapter();when(security.currentPrincipal()).thenReturn(Optional.of(AuthenticatedPrincipal.anonymous(ActorId.of("actor"))));
        assertThrows(RuntimeException.class,()->adapter.save(record("PARSE","Failure","actor",Instant.EPOCH,"Resolved",DeadLetterStatus.RESOLVED)));
    }
    @Test void recordedProvenanceSurvivesActorLifecycleAndCannotBeChangedOrRemoved(){
        var adapter=adapter();var old=record("PARSE","Failure","actor",Instant.EPOCH,"Resolved",DeadLetterStatus.RESOLVED);
        when(records.findById("id")).thenReturn(Optional.of(IntegrationPersistenceMapper.toEntity(old)));
        assertTrue(adapter.save(record("PARSE","Failure","actor",Instant.EPOCH,"Resolved",DeadLetterStatus.IGNORED)).hasManualResolution());
        verify(security,never()).currentPrincipal();verify(resolvers,never()).eligibleResolver(anyString(),any());
        assertThrows(RuntimeException.class,()->adapter.save(record("PARSE","Failure","actor",Instant.EPOCH,"Changed",DeadLetterStatus.RESOLVED)));
        assertThrows(RuntimeException.class,()->adapter.save(record("PARSE","Failure","actor",Instant.EPOCH.plusSeconds(1),"Resolved",DeadLetterStatus.RESOLVED)));
        assertThrows(RuntimeException.class,()->adapter.save(record("PARSE","Failure","other",Instant.EPOCH,"Resolved",DeadLetterStatus.RESOLVED)));
        assertThrows(RuntimeException.class,()->adapter.save(record("PARSE","Failure",null,null,null,DeadLetterStatus.OPEN)));
    }
}
