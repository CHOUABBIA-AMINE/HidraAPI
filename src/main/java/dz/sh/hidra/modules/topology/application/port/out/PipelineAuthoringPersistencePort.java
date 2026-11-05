/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAuthoringPersistencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Owns the bounded canonical Pipeline and persisted graph association contract.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.application.port.in.*;
import dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator.Contribution;
import java.util.*;
public interface PipelineAuthoringPersistencePort {
    interface Admission {}
    record FinalCheck(String sql,List<Object> arguments) {public FinalCheck {Objects.requireNonNull(sql);arguments=List.copyOf(arguments);}}
    /** Acquire source/association fences while native admission is still ADMITTING. */
    Admission acquire(PipelineAuthoringUseCase.Scope scope);
    /** Discovery source locks must be taken before native admission is sealed. */
    Admission acquire(PipelineAuthoringUseCase.Scope scope,boolean discovery);
    void prepare(Admission admission,PipelineAuthoringUseCase.Write command,Contribution actor);
    Set<String> mutationSql();
    void persist(Admission admission);
    AuthoredTopologyViewUseCase.View materialize(Admission admission,EngineeringGraphDraftUseCase.Result graph);
    AuthoredTopologyViewUseCase.Discovery discover(Admission admission,int offset,int limit);
    /** Freeze SELECT-only row images before the single native GUARD statement. */
    void freeze(Admission admission);
    /** Issuer-authenticated, memory-only final predicate; never another SQL statement. */
    FinalCheck finalCheck(Admission admission);
}
