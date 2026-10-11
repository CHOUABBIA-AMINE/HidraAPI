/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentParameterRevisionQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.service
 *
 * @Description : Maintains immutable governed equipment source evidence.
 *
 */
package dz.sh.hidra.modules.simulation.application.service;

import dz.sh.hidra.modules.simulation.application.port.out.*;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationEquipmentParameterRevisionRepositoryPort.*;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevisionTest;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevisionTest.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class SimulationEquipmentParameterRevisionQueryServiceTest {
    public static SimulationStoredPhysicalNetworkRevisionPort.Revision network(boolean empty){return new SimulationStoredPhysicalNetworkRevisionPort.Revision("network","n1","PIPELINE_SYSTEM","synthetic",AT,AT,null,"SYNTHETIC","synthetic test",
        List.of(new SimulationStoredPhysicalNetworkRevisionPort.Node("a",d("0")),new SimulationStoredPhysicalNetworkRevisionPort.Node("b",d("0"))),
        List.of(new SimulationStoredPhysicalNetworkRevisionPort.PipeSegment("pipe","a","b",d("100"),d("0.5"),d("0.001"))),
        empty?List.of():List.of(new SimulationStoredPhysicalNetworkRevisionPort.EquipmentLink("compressor","a","b","COMPRESSOR"),new SimulationStoredPhysicalNetworkRevisionPort.EquipmentLink("valve","b","a","VALVE")),"HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V1","a".repeat(64));}
    public static SimulationQualifiedFluidRevisionPort.QualifiedRevision fluid(){var v=new SimulationQualifiedFluidRevisionPort.Revision("fluid","f1",AT,AT,null,SimulationQualifiedFluidRevisionPort.Origin.SYNTHETIC,"synthetic",
        new SimulationQualifiedFluidRevisionPort.ProductSnapshot("p","PRODUCT","synthetic",true,AT,AT),SimulationQualifiedFluidRevisionPort.ProductKind.GAS,
        new SimulationQualifiedFluidRevisionPort.Method("method","m1",AT,AT,null,SimulationQualifiedFluidRevisionPort.Origin.SYNTHETIC,"synthetic",SimulationQualifiedFluidRevisionPort.InputRepresentation.GAS_MOLE_FRACTION,List.of("gas"),d("100000"),d("900000"),d("250"),d("350"),List.of(SimulationQualifiedFluidRevisionPort.SupportedUse.STEADY_STATE,SimulationQualifiedFluidRevisionPort.SupportedUse.TRANSIENT)),
        List.of(new SimulationQualifiedFluidRevisionPort.Component("gas",d("1"))),new SimulationQualifiedFluidRevisionPort.GovernanceBinding("fdef",1,"ftype","fpurpose"));
        return new SimulationQualifiedFluidRevisionPort.QualifiedRevision(new SimulationQualifiedFluidRevisionPort.StoredRevision(v,"HIDRA_CUSTODY_GAS_FLUID_V1","b".repeat(64)),
        new SimulationQualifiedFluidRevisionPort.Qualification("fluid-qualified","fluid","f1","b".repeat(64),AT,"fi","ft","fa","actor","Synthetic",AT,"fdef",1,"ftype","fpurpose"));}
    public static Qualification qualification(String digest){return new Qualification("q","equipment","r1",digest,AT,"i","t","a","actor","Synthetic",AT,"definition",1,"type","purpose");}
    final SimulationEquipmentParameterRevisionRepositoryPort revisions=mock(SimulationEquipmentParameterRevisionRepositoryPort.class);
    final SimulationEquipmentParameterApprovalEvidencePort approvals=mock(SimulationEquipmentParameterApprovalEvidencePort.class);
    final SimulationQualifiedFluidRevisionPort fluids=mock(SimulationQualifiedFluidRevisionPort.class);
    final SimulationStoredPhysicalNetworkRevisionPort networks=mock(SimulationStoredPhysicalNetworkRevisionPort.class);
    final SimulationEquipmentParameterRevisionQueryService query=new SimulationEquipmentParameterRevisionQueryService(revisions,approvals,fluids,networks);
    public void setup(){var v=fixture("r1");when(revisions.findStored("equipment","r1")).thenReturn(Optional.of(new StoredRevision(v,"HIDRA_SIMULATION_EQUIPMENT_PARAMETERS_V1","c".repeat(64))));when(revisions.findQualification("q")).thenReturn(Optional.of(qualification("c".repeat(64))));
        when(fluids.resolve(eq("fluid"),eq("f1"),eq("fluid-qualified"),any(),any())).thenReturn(Optional.of(fluid()));when(fluids.findStored("fluid","f1")).thenReturn(Optional.of(fluid().stored()));
        when(networks.find("network","n1")).thenReturn(Optional.of(network(false)));
        when(approvals.resolve(anyString(),any(),anyString(),anyString(),anyString(),any())).thenReturn(Optional.of(new SimulationEquipmentParameterApprovalEvidencePort.Evidence("i","t","a","definition",1,"type","purpose","actor","Synthetic",AT)));}
    @Test void completeBundleKeepsValveMapsLimitsAndSyntheticOrigin(){setup();var v=query.resolve("equipment","r1","q",AT,SimulationQualifiedFluidRevisionPort.SupportedUse.STEADY_STATE).orElseThrow();assertEquals(8,v.stored().revision().governedLimits().size());assertEquals(2,v.stored().revision().valveCharacteristics().getFirst().openingLines().size());}
    @Test void changedMissingWithdrawnOwnerEvidenceIsUnavailable(){setup();when(networks.find("network","n1")).thenReturn(Optional.of(network(true)));assertTrue(query.resolve("equipment","r1","q",AT,SimulationQualifiedFluidRevisionPort.SupportedUse.STEADY_STATE).isEmpty());
        when(networks.find("network","n1")).thenReturn(Optional.of(network(false)));when(approvals.resolve(anyString(),any(),anyString(),anyString(),anyString(),any())).thenReturn(Optional.empty());assertTrue(query.resolve("equipment","r1","q",AT,SimulationQualifiedFluidRevisionPort.SupportedUse.STEADY_STATE).isEmpty());}
    @Test void characteristicExpiryAtAnySelectedTimeMakesItUnavailable(){setup();var v=build("r1",SimulationEquipmentParameterRevision.Origin.SYNTHETIC,curve(SimulationEquipmentParameterRevision.Origin.SYNTHETIC,AT,AT,AT.plusSeconds(1)),valve(SimulationEquipmentParameterRevision.Origin.SYNTHETIC,AT,AT,null));
        when(revisions.findStored("equipment","r1")).thenReturn(Optional.of(new StoredRevision(v,"HIDRA_SIMULATION_EQUIPMENT_PARAMETERS_V1","c".repeat(64))));assertTrue(query.resolve("equipment","r1","q",AT.plusSeconds(1),SimulationQualifiedFluidRevisionPort.SupportedUse.STEADY_STATE).isEmpty());}

    public static SimulationStoredPhysicalNetworkRevisionPort.Revision regulatorNetwork(){var n=network(false);return new SimulationStoredPhysicalNetworkRevisionPort.Revision(n.sourceId(),n.revisionId(),n.scopeType(),n.scopeId(),n.recordedAt(),n.effectiveFrom(),n.effectiveUntil(),n.origin(),n.evidenceReference(),n.nodes(),n.pipeSegments(),
        List.of(new SimulationStoredPhysicalNetworkRevisionPort.EquipmentLink("regulator","a","b","REGULATOR")),"HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V2",n.sha256());}
    @Test void exactQualifiedRegulatorBundlePreservesBothOwnerAuthorities(){setup();var v=dz.sh.hidra.modules.simulation.domain.model.SimulationRegulatorCharacteristicRevisionTest.regulatorFixture();
        when(revisions.findStored("equipment","r1")).thenReturn(Optional.of(new StoredRevision(v,"HIDRA_SIMULATION_EQUIPMENT_PARAMETERS_V2","c".repeat(64))));
        when(networks.find("network","n1")).thenReturn(Optional.of(regulatorNetwork()));
        var result=query.resolve("equipment","r1","q",AT,SimulationQualifiedFluidRevisionPort.SupportedUse.STEADY_STATE).orElseThrow();assertEquals(v.regulatorCharacteristics(),result.stored().revision().regulatorCharacteristics());
        when(networks.find("network","n1")).thenReturn(Optional.of(network(false)));assertTrue(query.resolve("equipment","r1","q",AT,SimulationQualifiedFluidRevisionPort.SupportedUse.STEADY_STATE).isEmpty());
        when(networks.find("network","n1")).thenReturn(Optional.of(regulatorNetwork()));when(approvals.resolve(anyString(),any(),anyString(),anyString(),anyString(),any())).thenReturn(Optional.empty());
        assertTrue(query.resolve("equipment","r1","q",AT,SimulationQualifiedFluidRevisionPort.SupportedUse.STEADY_STATE).isEmpty());}
    @Test void regulatorFluidDomainMismatchAndExactTopologyFormatAreRefused(){setup();var v=dz.sh.hidra.modules.simulation.domain.model.SimulationRegulatorCharacteristicRevisionTest.regulatorFixture();
        when(revisions.findStored("equipment","r1")).thenReturn(Optional.of(new StoredRevision(v,"HIDRA_SIMULATION_EQUIPMENT_PARAMETERS_V2","c".repeat(64))));when(networks.find("network","n1")).thenReturn(Optional.of(regulatorNetwork()));
        var f=fluid();var x=f.stored().revision();var m=x.method();
        var narrow=new SimulationQualifiedFluidRevisionPort.Method(m.reference(),m.revisionId(),m.recordedAt(),m.effectiveFrom(),m.effectiveUntil(),m.origin(),m.evidenceReference(),m.inputRepresentation(),m.allowedComponentReferences(),d("150000"),m.maximumPressurePascalsAbsolute(),m.minimumTemperatureKelvin(),m.maximumTemperatureKelvin(),m.supportedUses());
        var changed=new SimulationQualifiedFluidRevisionPort.Revision(x.sourceId(),x.revisionId(),x.recordedAt(),x.effectiveFrom(),x.effectiveUntil(),x.origin(),x.evidenceReference(),x.productSnapshot(),x.productKind(),narrow,x.components(),x.governanceBinding());
        when(fluids.resolve(eq("fluid"),eq("f1"),eq("fluid-qualified"),any(),any())).thenReturn(Optional.of(new SimulationQualifiedFluidRevisionPort.QualifiedRevision(new SimulationQualifiedFluidRevisionPort.StoredRevision(changed,f.stored().payloadFormat(),f.stored().sha256()),f.qualification())));
        assertTrue(query.resolve("equipment","r1","q",AT,SimulationQualifiedFluidRevisionPort.SupportedUse.STEADY_STATE).isEmpty());
        var n=regulatorNetwork();assertThrows(IllegalArgumentException.class,()->new SimulationStoredPhysicalNetworkRevisionPort.Revision(n.sourceId(),n.revisionId(),n.scopeType(),n.scopeId(),n.recordedAt(),n.effectiveFrom(),n.effectiveUntil(),n.origin(),n.evidenceReference(),n.nodes(),n.pipeSegments(),n.equipmentLinks(),"HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V1",n.sha256()));
        var old=network(false);assertThrows(IllegalArgumentException.class,()->new SimulationStoredPhysicalNetworkRevisionPort.Revision(old.sourceId(),old.revisionId(),old.scopeType(),old.scopeId(),old.recordedAt(),old.effectiveFrom(),old.effectiveUntil(),old.origin(),old.evidenceReference(),old.nodes(),old.pipeSegments(),old.equipmentLinks(),"HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V2",old.sha256()));
        assertThrows(IllegalArgumentException.class,()->new SimulationStoredPhysicalNetworkRevisionPort.Revision(n.sourceId(),n.revisionId(),n.scopeType(),n.scopeId(),n.recordedAt(),n.effectiveFrom(),n.effectiveUntil(),n.origin(),n.evidenceReference(),n.nodes(),n.pipeSegments(),n.equipmentLinks(),n.payloadFormat(),"bad"));}
}
