/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseIncidentSourceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.service
 * @Description : Exact T16 reviewed Incident and T14 FACILITY correlation evidence for HSE.
 */
package dz.sh.hidra.modules.incident.application.service;

import dz.sh.hidra.modules.incident.application.contract.hse.HseIncidentSourceContract;
import dz.sh.hidra.modules.incident.application.port.out.*;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class HseIncidentSourceQueryService implements HseIncidentSourceContract {
    private final IncidentEscalationStorePort correlations;
    private final IncidentInvestigationStorePort investigation;
    private final IncidentRepositoryPort incidents;
    private final IncidentEscalationAuthorizationPort authorization;
    private final IncidentEscalationSourcePort sources;
    public HseIncidentSourceQueryService(IncidentEscalationStorePort correlations,
            IncidentInvestigationStorePort investigation,IncidentRepositoryPort incidents,
            IncidentEscalationAuthorizationPort authorization,IncidentEscalationSourcePort sources){
        this.correlations=correlations;this.investigation=investigation;this.incidents=incidents;
        this.authorization=authorization;this.sources=sources;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Source> reviewed(String id,String company,String facility){
        if(id==null||company==null||facility==null)return Optional.empty();
        // Incident owner must authorize exact live protected source before any read.
        authorization.requireIncident("incident:incidents:read",id);
        var correlation=correlations.byIncident(id).orElse(null);
        if(correlation==null||!"FACILITY".equals(correlation.assetType())
                ||!company.equals(correlation.companyId())||!facility.equals(correlation.assetId()))
            return Optional.empty();
        if(!sources.readableCorrelation(correlation))return Optional.empty();
        var evidence=investigation.view(id);
        if(!java.util.Set.of("APPROVED","CLOSED").contains(evidence.state())
                ||evidence.reviewedById()==null||evidence.reviewedById().isBlank()
                ||!evidence.hasEvidence()||!evidence.hasAction()
                ||evidence.resolutionSummary()==null||evidence.resolutionSummary().isBlank())
            return Optional.empty();
        return incidents.findById(id).filter(parent->!"CANCELLED".equals(parent.status().name()))
                .map(parent->new Source(id,parent.incidentNumber(),parent.title(),company,facility,
                        evidence.reviewedById(),Long.toString(evidence.version())));
    }
}
