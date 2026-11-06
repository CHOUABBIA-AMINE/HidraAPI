/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAuthoringApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Owns the bounded canonical Pipeline and persisted graph association contract.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.*;
import dz.sh.hidra.modules.topology.application.port.out.AuthoredTopologyAuthorizationPort;
import org.springframework.stereotype.Service;
@Service
public final class PipelineAuthoringApplicationService implements PipelineAuthoringUseCase,AuthoredTopologyViewUseCase {
    private final EngineeringGraphDraftUseCase graphOwner;
    private final InstalledUnitLifecycleUseCase units;
    private final AuthoredTopologyAuthorizationPort authentication;
    public PipelineAuthoringApplicationService(EngineeringGraphDraftUseCase graphOwner,AuthoredTopologyAuthorizationPort authentication,InstalledUnitLifecycleUseCase units){this.units=java.util.Objects.requireNonNull(units);this.graphOwner=java.util.Objects.requireNonNull(graphOwner);this.authentication=java.util.Objects.requireNonNull(authentication);}
    @Override public void requireGlobalMapAccess(){authentication.requireLegacyMapAdministrator();}
    @Override public View write(Write command){authentication.requireNativeAuthentication();return graphOwner.executePipeline(command);}
    @Override public View view(Scope scope,Long version){authentication.requireNativeAuthentication();return graphOwner.executeAuthoredView(scope,version);}
    @Override public Discovery discover(Scope scope,int offset,int limit){authentication.requireNativeAuthentication();if(offset<0||offset>100000||limit<1||limit>200)throw new IllegalArgumentException("Bounded discovery required");var found=graphOwner.executeAuthoredDiscovery(scope,offset,limit);
        var visible=new java.util.ArrayList<Choice>();
        // Each candidate requires its own existing protected READ_UNIT transaction.
        // Graph authority never substitutes for installed-unit read authority.
        for(String id:found.candidateInstalledUnitIds()) {
            try {
                var result=units.execute(new InstalledUnitLifecycleUseCase.Command(InstalledUnitLifecycleUseCase.Operation.READ_UNIT,
                    null,scope.companyPartyId(),id,null,null,null,null,null,null,null,null,0));
                var unit=result.unit();
                if(!id.equals(unit.id())||!scope.companyPartyId().equals(unit.companyPartyId()))throw new IllegalStateException("Installed-unit scope mismatch");
                visible.add(new Choice(id,id,id,unit.equipmentKindId()));
            } catch(dz.sh.hidra.modules.topology.application.model.InstalledUnitLifecycleFailure failure) {
                switch(failure.kind()) {
                    case DENIED,NOT_FOUND -> { /* Neither identity nor denied counts are disclosed. */ }
                    case UNAUTHENTICATED -> throw new dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.DENIED);
                    default -> {
                        var translated=new dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.UNAVAILABLE);
                        translated.initCause(failure);throw translated;
                    }
                }
            }
        }
        return new Discovery(found.companyPartyId(),found.pipelineSystemId(),found.pipelines(),found.totalPipelines(),found.offset(),found.limit(),found.facilities(),found.pipelineTypes(),found.segments(),found.equipmentKinds(),visible,java.util.List.of(),found.nextOffset());}
}
