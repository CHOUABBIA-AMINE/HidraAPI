/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyQualifiedFluidRevisionQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.integration
 *
 * @Description : Maintains immutable governed equipment source evidence.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.integration;

import dz.sh.hidra.modules.custody.application.contract.simulation.SimulationGasFluidRevisionContract;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationQualifiedFluidRevisionPort;
import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Component;
@Component
public class CustodyQualifiedFluidRevisionQueryAdapter implements SimulationQualifiedFluidRevisionPort {
    private final SimulationGasFluidRevisionContract owner;
    public CustodyQualifiedFluidRevisionQueryAdapter(SimulationGasFluidRevisionContract owner){this.owner=owner;}
    @Override public Optional<StoredRevision> findStored(String source,String revision){return owner.findStored(source,revision).map(this::map);}
    @Override public Optional<QualifiedRevision> resolve(String source,String revision,String qualification,Instant at,SupportedUse use){
        return owner.resolve(source,revision,qualification,at,SimulationGasFluidRevisionContract.SupportedUse.valueOf(use.name())).map(this::map);}
    private SimulationQualifiedFluidRevisionPort.StoredRevision map(SimulationGasFluidRevisionContract.StoredRevision v){return new SimulationQualifiedFluidRevisionPort.StoredRevision(map(v.revision()),v.payloadFormat(),v.sha256());}
    private SimulationQualifiedFluidRevisionPort.QualifiedRevision map(SimulationGasFluidRevisionContract.QualifiedRevision v){return new SimulationQualifiedFluidRevisionPort.QualifiedRevision(map(v.stored()),map(v.qualification()));}
    private SimulationQualifiedFluidRevisionPort.ProductSnapshot map(SimulationGasFluidRevisionContract.ProductSnapshot v){return new SimulationQualifiedFluidRevisionPort.ProductSnapshot(v.id(),v.catalogName(),v.code(),v.active(),v.createdAt(),v.updatedAt());}
    private SimulationQualifiedFluidRevisionPort.GovernanceBinding map(SimulationGasFluidRevisionContract.GovernanceBinding v){return new SimulationQualifiedFluidRevisionPort.GovernanceBinding(v.definitionId(),v.definitionVersion(),v.targetTypeId(),v.purposeId());}
    private SimulationQualifiedFluidRevisionPort.Component map(SimulationGasFluidRevisionContract.Component v){return new SimulationQualifiedFluidRevisionPort.Component(v.componentReference(),v.moleFraction());}
    private SimulationQualifiedFluidRevisionPort.Method map(SimulationGasFluidRevisionContract.Method v){return new SimulationQualifiedFluidRevisionPort.Method(v.reference(),v.revisionId(),v.recordedAt(),v.effectiveFrom(),v.effectiveUntil(),SimulationQualifiedFluidRevisionPort.Origin.valueOf(v.origin().name()),v.evidenceReference(),SimulationQualifiedFluidRevisionPort.InputRepresentation.valueOf(v.inputRepresentation().name()),v.allowedComponentReferences(),v.minimumPressurePascalsAbsolute(),v.maximumPressurePascalsAbsolute(),v.minimumTemperatureKelvin(),v.maximumTemperatureKelvin(),v.supportedUses().stream().map(x->SimulationQualifiedFluidRevisionPort.SupportedUse.valueOf(x.name())).toList());}
    private SimulationQualifiedFluidRevisionPort.Revision map(SimulationGasFluidRevisionContract.Revision v){return new SimulationQualifiedFluidRevisionPort.Revision(v.sourceId(),v.revisionId(),v.recordedAt(),v.effectiveFrom(),v.effectiveUntil(),SimulationQualifiedFluidRevisionPort.Origin.valueOf(v.origin().name()),v.evidenceReference(),map(v.productSnapshot()),SimulationQualifiedFluidRevisionPort.ProductKind.valueOf(v.productKind().name()),map(v.method()),v.components() .stream().map(this::map).toList(),map(v.governanceBinding()));}
    private SimulationQualifiedFluidRevisionPort.Qualification map(SimulationGasFluidRevisionContract.Qualification v){return new SimulationQualifiedFluidRevisionPort.Qualification(v.qualificationId(),v.sourceId(),v.revisionId(),v.payloadSha256(),v.qualifiedAt(),v.workflowInstanceId(),v.workflowTaskId(),v.workflowActionId(),v.approverId(),v.approverDisplayName(),v.approvedAt(),v.definitionId(),v.definitionVersion(),v.targetTypeId(),v.purposeId());}
}
