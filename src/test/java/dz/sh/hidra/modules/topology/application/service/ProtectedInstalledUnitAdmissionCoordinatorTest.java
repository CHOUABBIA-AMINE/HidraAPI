/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedInstalledUnitAdmissionCoordinatorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Implements the protected installed-unit lifecycle within its owning boundary.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract;
import dz.sh.hidra.modules.party.application.contract.topology.*;
import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.*;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnit;
import dz.sh.hidra.platform.persistence.*;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.util.*;
import java.time.Instant;
class ProtectedInstalledUnitAdmissionCoordinatorTest {
    private final TopologyInstalledUnitAuthorizationContract identity=mock(TopologyInstalledUnitAuthorizationContract.class);
    private final TopologyInstalledUnitCompanyEvidenceContract company=mock(TopologyInstalledUnitCompanyEvidenceContract.class);
    private final TopologyInstalledUnitManufacturerEvidenceContract manufacturer=mock(TopologyInstalledUnitManufacturerEvidenceContract.class);
    private final InstalledUnitPositionOwnershipPort positions=mock(InstalledUnitPositionOwnershipPort.class);
    private final ProtectedInstalledUnitRepositoryPort storage=mock(ProtectedInstalledUnitRepositoryPort.class);
    private final ProtectedTransactionExecutor executor=mock(ProtectedTransactionExecutor.class);
    private final ProtectedInstalledUnitAdmissionCoordinator coordinator=new ProtectedInstalledUnitAdmissionCoordinator(identity,company,manufacturer,positions,storage,executor);
    private Command read(){return new Command(Operation.READ_UNIT,null,"company","unit",null,null,null,null,null,null,null,null,0);}
    @AfterEach void clear(){TransactionSynchronizationManager.clear();TransactionSynchronizationManager.setActualTransactionActive(false);}
    @Test void ambientTransactionCannotPrepareOrConsumeNativeEntry() {
        TransactionSynchronizationManager.setActualTransactionActive(true);assertThrows(RuntimeException.class,()->coordinator.prepare(read()));verifyNoInteractions(identity,company,manufacturer,storage);
    }
    @Test void forgedPreparationCannotReachAnyOwnerOrDml() {
        assertThrows(RuntimeException.class,()->coordinator.acquire(null,read()));verify(executor).rejectAdmission();verifyNoInteractions(identity,company,manufacturer,storage,positions);
    }
    @Test void publicTicketShapesContainNoConstructorOrAuthorityFactory() {
        for(var type:List.of(ProtectedInstalledUnitAdmissionCoordinator.Prepared.class,ProtectedInstalledUnitAdmissionCoordinator.Admission.class)) {
            assertEquals(0,type.getConstructors().length);for(var field:type.getDeclaredFields())assertTrue(java.lang.reflect.Modifier.isPrivate(field.getModifiers()));
        }
    }
    @Test void exactNativeOperationDescriptorsAreDerivedFromTypedCommand() throws Exception {
        var method=ProtectedInstalledUnitAdmissionCoordinator.class.getDeclaredMethod("request",Command.class);method.setAccessible(true);
        var c=new Command(Operation.RELOCATE,"request","company","unit","old","new",new Position("left","source"),new Position("right","destination"),null,Instant.EPOCH,Instant.EPOCH,"source",1);
        var actual=(TopologyInstalledUnitAuthorizationContract.Request)method.invoke(null,c);
        assertEquals(TopologyInstalledUnitAuthorizationContract.Operation.RELOCATE,actual.operation());assertEquals("unit",actual.unitId());assertEquals(6,actual.requirements().size());
    }
    @Test void invalidCrossOperationPayloadsCannotHideInCommandDigest() {
        assertThrows(IllegalArgumentException.class,()->new Command(Operation.READ_UNIT,"request","company","unit",null,null,null,null,null,null,null,null,0));
        assertThrows(IllegalArgumentException.class,()->new Command(Operation.RELOCATE,"request","company","unit","same","same",new Position("left","source"),new Position("right","destination"),null,Instant.EPOCH,Instant.EPOCH,"source",1));
        assertThrows(IllegalArgumentException.class,()->new Command(Operation.RECORD_INSTALLATION,"request","company","unit","installation",null,null,new Position("position","facility"),null,Instant.EPOCH.plusNanos(1),null,"source",0));
    }
    @Test void mutationSqlPinsAreExactAcrossCoordinatorAndTechnicalExecutor() throws Exception {
        var c=ProtectedInstalledUnitAdmissionCoordinator.class.getDeclaredField("DML_HASHES");c.setAccessible(true);
        var e=ProtectedTransactionExecutor.class.getDeclaredField("INSTALLED_DML_HASHES");e.setAccessible(true);
        assertEquals(5,((Set<?>)c.get(null)).size());assertEquals(c.get(null),e.get(null));
    }

    private record Setup(Command command,ProtectedInstalledUnitAdmissionCoordinator.Prepared prepared,ProtectedInstalledUnitRepositoryPort.Admission storageTicket) {}
    @SuppressWarnings("unchecked") private Setup registrationSetup(boolean permitted) throws Exception {
        var unit=new InstalledUnit("unit","company","kind",InstalledUnit.Evidence.unknown(),InstalledUnit.Evidence.unknown(),InstalledUnit.Evidence.unknown(),null);
        var c=new Command(Operation.REGISTER_UNIT,"request","company","unit",null,null,null,null,unit,null,null,null,0);
        when(identity.prepare()).thenReturn(new TopologyInstalledUnitAuthorizationContract.Prepared(){});
        var prepared=coordinator.prepare(c);
        var nativeTicket=new TopologyInstalledUnitAuthorizationContract.Admission(){};var companyTicket=new TopologyInstalledUnitCompanyEvidenceContract.Admission(){};
        var stored=new ProtectedInstalledUnitRepositoryPort.Admission(){};
        var req=new TopologyInstalledUnitAuthorizationContract.Request(TopologyInstalledUnitAuthorizationContract.Operation.REGISTER_UNIT,"company","unit",null,null,null,null);
        var decisions=req.requirements().stream().map(r->new TopologyInstalledUnitAuthorizationContract.ResourceDecision(r,permitted?TopologyInstalledUnitAuthorizationContract.Decision.PERMIT:TopologyInstalledUnitAuthorizationContract.Decision.DENY)).toList();
        when(identity.acquire(any(),eq(req))).thenReturn(nativeTicket);
        when(identity.contribute(nativeTicket)).thenReturn(new TopologyInstalledUnitAuthorizationContract.Contribution(req,decisions,"binding","session","actor",Instant.EPOCH,Instant.EPOCH.plusSeconds(30),1,1));
        when(company.acquire("company")).thenReturn(companyTicket);
        when(company.read(companyTicket)).thenReturn(new TopologyInstalledUnitCompanyEvidenceContract.Contribution("company","ACTIVE",1,1,1,"publisher"));
        when(storage.discover(c)).thenReturn(new ProtectedInstalledUnitRepositoryPort.Hint(null,null));when(storage.acquire(eq(c),any(),anyList())).thenReturn(stored);
        when(storage.observe(stored)).thenReturn(new ProtectedInstalledUnitRepositoryPort.Snapshot(null,null,null,List.of(),List.of(),null,null));
        var method=ProtectedInstalledUnitAdmissionCoordinator.class.getDeclaredMethod("expectedFences",boolean.class);method.setAccessible(true);
        var fences=(List<ProtectedRelationFence>)method.invoke(null,false);
        when(identity.finalFences(nativeTicket)).thenReturn(fences.stream().filter(f->f.relation().startsWith("public.hidra_identity_")).toList());
        when(company.finalFences(companyTicket)).thenReturn(fences.stream().filter(f->f.relation().startsWith("public.hidra_party_")).toList());
        when(storage.finalFences(stored)).thenReturn(fences.stream().filter(f->f.relation().startsWith("public.hidra_topology_")).toList());
        when(executor.sealAdmission()).thenReturn(mock(ProtectedTransactionExecutor.ProvisionalPermit.class));
        TransactionSynchronizationManager.setActualTransactionActive(true);TransactionSynchronizationManager.initSynchronization();
        return new Setup(c,prepared,stored);
    }
    @Test void ownerDenyCannotOpenProvisionalMutation() throws Exception {
        var f=registrationSetup(false);assertThrows(RuntimeException.class,()->coordinator.acquire(f.prepared(),f.command()));
        verify(executor,never()).beginProvisional(any(),any());verify(storage,never()).apply(any(),any(),any());verify(storage).reject(f.storageTicket());
    }
    @Test void arbitrarySqlCannotEnterEvenAfterPermittedNativeAdmission() throws Exception {
        var f=registrationSetup(true);var a=coordinator.acquire(f.prepared(),f.command());
        when(storage.mutationSql()).thenReturn(Set.of("DELETE FROM public.hidra_topology_installed_unit"));
        assertThrows(RuntimeException.class,()->coordinator.perform(a));verify(executor,never()).beginProvisional(any(),any());verify(storage,never()).apply(any(),any(),any());
    }
    @Test void preparedTicketCannotBeConsumedTwice() throws Exception {
        var f=registrationSetup(true);coordinator.acquire(f.prepared(),f.command());
        assertThrows(RuntimeException.class,()->coordinator.acquire(f.prepared(),f.command()));verify(identity,times(1)).contribute(any());verify(storage,never()).apply(any(),any(),any());
    }

    private Command replacement(String second,Position destination) {
        return new Command(Operation.REPLACE,"request","company","unit","old","new",new Position("position","facility"),destination,
            null,Instant.EPOCH.plusSeconds(1),Instant.EPOCH.plusSeconds(1),"replacement source",1,second);
    }
    @Test void replacementBindsDistinctUnitsAndOneExactPositionInNativeRequest() throws Exception {
        var c=replacement("second",new Position("position","facility"));assertTrue(c.mutation());
        var method=ProtectedInstalledUnitAdmissionCoordinator.class.getDeclaredMethod("request",Command.class);method.setAccessible(true);
        var r=(TopologyInstalledUnitAuthorizationContract.Request)method.invoke(null,c);
        assertEquals(TopologyInstalledUnitAuthorizationContract.Operation.REPLACE,r.operation());assertEquals("second",r.replacementUnitId());
        assertEquals(6,r.requirements().size());assertEquals("facility",r.sourceFacilityId());assertEquals(r.sourceFacilityId(),r.destinationFacilityId());
    }
    @Test void replacementRejectsMissingSameOversizedAndDifferentPositionDescriptors() {
        for(String invalid:Arrays.asList(null,"unit"," second","x".repeat(81)))
            assertThrows(IllegalArgumentException.class,()->replacement(invalid,new Position("position","facility")));
        assertThrows(IllegalArgumentException.class,()->replacement("second",new Position("other","facility")));
        assertThrows(IllegalArgumentException.class,()->replacement("second",new Position("position","other")));
        assertThrows(IllegalArgumentException.class,()->new Command(Operation.REPLACE,"request","company","unit","old","old",
            new Position("position","facility"),new Position("position","facility"),null,Instant.EPOCH,Instant.EPOCH,"evidence",1,"second"));
    }
    @Test void legacyConstructorsRetainAbsentSecondUnitAndRejectHiddenSecondUnit() {
        assertNull(read().replacementUnitId());assertEquals(7,Arrays.stream(Operation.values()).filter(x->x!=Operation.REPLACE).count());
        assertThrows(IllegalArgumentException.class,()->new Command(Operation.READ_UNIT,null,"company","unit",null,null,null,null,null,null,null,null,0,"second"));
        var unknown=InstalledUnit.Evidence.unknown();var u=new InstalledUnit("unit","company","kind",unknown,unknown,unknown,null);
        var v=new InstalledUnit("second","company","kind",unknown,unknown,unknown,null);
        assertNull(new Result(u,null,null,List.of(),false).replacementUnit());
        assertEquals(v,new Result(u,null,null,List.of(),false,v).replay().replacementUnit());
    }
    @Test void compiledSqlPinsPreserveFourLegacyShapesAndAddOnlyReplacementReceipt() throws Exception {
        var field=ProtectedInstalledUnitAdmissionCoordinator.class.getDeclaredField("DML_HASHES");field.setAccessible(true);
        var adapter=dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcProtectedInstalledUnitRepositoryAdapter.class;
        var statements=adapter.getDeclaredField("MUTATIONS");statements.setAccessible(true);
        var actual=new HashSet<String>();for(Object sql:(Set<?>)statements.get(null))actual.add(HexFormat.of().formatHex(
            java.security.MessageDigest.getInstance("SHA-256").digest(sql.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8))));
        assertEquals(field.get(null),actual);assertTrue(actual.contains("1503d363ee40899cad0081f42e84f728d974409236ebc5fe96e631e571330495"));
    }
    @Test void replacementCodecRetainsLegacyEnvelopeAndBindsBothUnitFacts() throws Exception {
        var adapter=dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcProtectedInstalledUnitRepositoryAdapter.class;
        var command=adapter.getDeclaredMethod("commandBytes",Command.class);command.setAccessible(true);
        var encode=adapter.getDeclaredMethod("encode",Result.class);encode.setAccessible(true);var decode=adapter.getDeclaredMethod("decode",byte[].class);decode.setAccessible(true);
        byte[] old=(byte[])command.invoke(null,read());assertEquals(1,java.nio.ByteBuffer.wrap(old).getInt());
        var c=replacement("second",new Position("position","facility"));byte[] changed=(byte[])command.invoke(null,c);
        assertEquals(2,java.nio.ByteBuffer.wrap(changed).getInt());assertFalse(Arrays.equals(changed,(byte[])command.invoke(null,replacement("third",new Position("position","facility")))));
        var unknown=InstalledUnit.Evidence.unknown();var u=new InstalledUnit("unit","company","kind",unknown,unknown,unknown,null);var v=new InstalledUnit("second","company","kind",unknown,unknown,unknown,null);
        var source=new dz.sh.hidra.modules.topology.domain.model.InstalledUnitInstallation("old","unit","position","company",Instant.EPOCH,Instant.EPOCH.plusSeconds(1),Instant.EPOCH,"evidence",2);
        var next=new dz.sh.hidra.modules.topology.domain.model.InstalledUnitInstallation("new","second","position","company",Instant.EPOCH.plusSeconds(1),null,Instant.EPOCH,"replacement source",1);
        var expected=new Result(u,source,next,List.of(source),false,v);byte[] payload=(byte[])encode.invoke(null,expected);
        assertEquals(2,java.nio.ByteBuffer.wrap(payload).getInt());assertEquals(expected,decode.invoke(null,(Object)payload));
        assertThrows(java.lang.reflect.InvocationTargetException.class,()->decode.invoke(null,(Object)Arrays.copyOf(payload,payload.length+1)));
        byte[] legacy=(byte[])encode.invoke(null,new Result(u,null,null,List.of(),false));assertEquals(1,java.nio.ByteBuffer.wrap(legacy).getInt());
        assertEquals(new Result(u,null,null,List.of(),false),decode.invoke(null,(Object)legacy));
    }
}
