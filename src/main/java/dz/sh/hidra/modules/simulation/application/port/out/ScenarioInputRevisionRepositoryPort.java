/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScenarioInputRevisionRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 * @Description : Read and append a Simulation-owned immutable history under its exact scope.
 */
package dz.sh.hidra.modules.simulation.application.port.out;


import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision;
import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision.Scope;
import java.util.Optional;
import java.util.UUID;
public interface ScenarioInputRevisionRepositoryPort {
    record Locator(UUID scenarioId,long revision,UUID baselineReceiptId,String baselineDigest) {}
    Optional<Locator> locate(Scope scope,UUID scenarioId,long revision);
    Optional<ScenarioInputRevision> find(Scope scope,UUID scenarioId,long revision);
    Optional<ScenarioInputRevision> head(Scope scope,UUID scenarioId);
    Optional<ScenarioInputRevision> byRequest(Scope scope,String actorId,String requestKey);
    /** Only parameterized INSERT ... ON CONFLICT DO NOTHING; no edit or delete surface. */
    boolean append(ScenarioInputRevision revision);
}
