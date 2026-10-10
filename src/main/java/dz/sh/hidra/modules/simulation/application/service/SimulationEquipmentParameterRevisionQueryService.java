/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentParameterRevisionQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.service
 *
 * @Description : Maintains immutable governed equipment source evidence.
 *
 */
package dz.sh.hidra.modules.simulation.application.service;

import dz.sh.hidra.modules.simulation.application.port.out.*;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationEquipmentParameterRevisionRepositoryPort.*;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationQualifiedFluidRevisionPort.SupportedUse;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimulationEquipmentParameterRevisionQueryService {
    public record ResolvedSources(SimulationQualifiedFluidRevisionPort.QualifiedRevision fluid,
            SimulationStoredPhysicalNetworkRevisionPort.Revision network) {}
    public record QualifiedRevision(StoredRevision stored,Qualification qualification,
            SimulationQualifiedFluidRevisionPort.QualifiedRevision fluid,
            SimulationStoredPhysicalNetworkRevisionPort.Revision network) {}
    private final SimulationEquipmentParameterRevisionRepositoryPort revisions;
    private final SimulationEquipmentParameterApprovalEvidencePort approvals;
    private final SimulationQualifiedFluidRevisionPort fluids;
    private final SimulationStoredPhysicalNetworkRevisionPort networks;
    public SimulationEquipmentParameterRevisionQueryService(SimulationEquipmentParameterRevisionRepositoryPort revisions,
            SimulationEquipmentParameterApprovalEvidencePort approvals,SimulationQualifiedFluidRevisionPort fluids,
            SimulationStoredPhysicalNetworkRevisionPort networks){this.revisions=revisions;this.approvals=approvals;this.fluids=fluids;this.networks=networks;}
    @Transactional(readOnly=true,isolation=Isolation.REPEATABLE_READ)
    public Optional<StoredRevision> findStored(String source,String revision){return revisions.findStored(source,revision);}
    @Transactional(readOnly=true,isolation=Isolation.REPEATABLE_READ)
    public Optional<QualifiedRevision> resolve(String source,String revision,String qualification,Instant at,SupportedUse use){
        if(at==null||use==null)return Optional.empty();
        var s=revisions.findStored(source,revision).orElse(null);var q=revisions.findQualification(qualification).orElse(null);
        if(s==null||q==null)return Optional.empty();var v=s.revision();var b=v.governanceBinding();
        if(!q.sourceId().equals(v.sourceId())||!q.revisionId().equals(v.revisionId())||!q.payloadSha256().equals(s.sha256())||q.qualifiedAt().isAfter(at)
                ||!b.definitionId().equals(q.definitionId())||b.definitionVersion()!=q.definitionVersion()||!b.targetTypeId().equals(q.targetTypeId())||!b.purposeId().equals(q.purposeId()))return Optional.empty();
        var joined=compatible(v,at,use).orElse(null);
        if(joined==null||compatible(v,q.approvedAt(),use).isEmpty()||compatible(v,q.qualifiedAt(),use).isEmpty())return Optional.empty();
        var a=approvals.resolve(s.sha256(),b,q.workflowInstanceId(),q.workflowTaskId(),q.workflowActionId(),at).orElse(null);
        if(a==null||!q.approverId().equals(a.actorId())||!q.approverDisplayName().equals(a.actorDisplayName())||!q.approvedAt().equals(a.actedAt())
                ||!q.workflowInstanceId().equals(a.instanceId())||!q.workflowTaskId().equals(a.taskId())||!q.workflowActionId().equals(a.actionId())
                ||!q.definitionId().equals(a.definitionId())||q.definitionVersion()!=a.definitionVersion()||!q.targetTypeId().equals(a.targetTypeId())||!q.purposeId().equals(a.purposeId()))return Optional.empty();
        return Optional.of(new QualifiedRevision(s,q,joined.fluid(),joined.network()));
    }
    /** Source qualification only; no engine readiness or operator safety approval. */
    @Transactional(readOnly=true,isolation=Isolation.REPEATABLE_READ)
    public Optional<ResolvedSources> compatible(SimulationEquipmentParameterRevision v,Instant at,SupportedUse use){
        if(!v.effectiveAt(at))return Optional.empty();
        if(use==null){var raw=fluids.findStored(v.fluidSourceId(),v.fluidRevisionId()).orElse(null);
            if(raw==null||raw.revision().method().supportedUses().isEmpty())return Optional.empty();use=raw.revision().method().supportedUses().getFirst();}
        var f=fluids.resolve(v.fluidSourceId(),v.fluidRevisionId(),v.fluidQualificationId(),at,use).orElse(null);
        var n=networks.find(v.networkSourceId(),v.networkRevisionId()).orElse(null);
        if(f==null||n==null||!v.networkSourceId().equals(n.sourceId())||!v.networkRevisionId().equals(n.revisionId())||!v.networkSha256().equals(n.sha256())
                ||!v.fluidSourceId().equals(f.stored().revision().sourceId())||!v.fluidRevisionId().equals(f.stored().revision().revisionId())||!v.fluidSha256().equals(f.stored().sha256())
                ||!v.fluidQualificationId().equals(f.qualification().qualificationId())||!v.fluidSourceId().equals(f.qualification().sourceId())
                ||!v.fluidRevisionId().equals(f.qualification().revisionId())||!v.fluidSha256().equals(f.qualification().payloadSha256())
                ||at.isBefore(n.recordedAt())||at.isBefore(n.effectiveFrom())||(n.effectiveUntil()!=null&&!at.isBefore(n.effectiveUntil())))return Optional.empty();
        if(("SYNTHETIC".equals(n.origin())||f.stored().revision().origin()==SimulationQualifiedFluidRevisionPort.Origin.SYNTHETIC)
                &&v.origin()!=Origin.SYNTHETIC)return Optional.empty();
        var expected=new HashMap<String,SimulationStoredPhysicalNetworkRevisionPort.EquipmentLink>();
        for(var l:n.equipmentLinks())if(expected.put(l.id(),l)!=null)return Optional.empty();
        if(expected.size()!=v.equipment().size())return Optional.empty();
        for(var e:v.equipment()){var l=expected.get(e.id());if(l==null||!e.kind().name().equals(l.kind())||!e.fromNodeId().equals(l.fromNodeId())||!e.toNodeId().equals(l.toNodeId()))return Optional.empty();}
        var method=f.stored().revision().method();
        for(var c:v.compressorCurves())if(!inside(c.referenceInletPressurePascalsAbsolute(),method.minimumPressurePascalsAbsolute(),method.maximumPressurePascalsAbsolute())
                ||!inside(c.referenceInletTemperatureKelvin(),method.minimumTemperatureKelvin(),method.maximumTemperatureKelvin()))return Optional.empty();
        for(var c:v.valveCharacteristics())if(!inside(c.referenceTemperatureKelvin(),method.minimumTemperatureKelvin(),method.maximumTemperatureKelvin()))return Optional.empty();
        return Optional.of(new ResolvedSources(f,n));
    }
    private static boolean inside(BigDecimal v,BigDecimal lo,BigDecimal hi){return v.compareTo(lo)>=0&&v.compareTo(hi)<=0;}
}
