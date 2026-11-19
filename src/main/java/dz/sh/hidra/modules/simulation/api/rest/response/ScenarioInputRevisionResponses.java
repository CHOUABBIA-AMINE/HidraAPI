/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScenarioInputRevisionResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Class
 * @Layer       : API
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.api.rest.response
 * @Description : Explicit wire projections of exact immutable scenario revision and validation evidence.
 */
package dz.sh.hidra.modules.simulation.api.rest.response;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class ScenarioInputRevisionResponses {
    private ScenarioInputRevisionResponses() {}
    public record Baseline(UUID receiptId,UUID manifestId,long epoch,String manifestDigest) {}
    public record Boundary(String slot,BigDecimal submittedValue,String submittedUnit,
                           BigDecimal siValue,String siUnit) {}
    public record Revision(UUID scenarioId,long revision,long expectedRevision,
                           String companyId,String pipelineSystemId,String name,String mode,
                           Baseline baseline,List<Boundary> boundaries,
                           Integer durationSeconds,Integer stepSeconds,
                           String requestKey,String inputDigest,String policyId,
                           String status,String numericalCapability,Instant recordedAt,boolean replay) {}
    public record Validation(UUID scenarioId,long revision,String inputDigest,String policyId,
                             String status,String numericalCapability,List<String> diagnostics) {}
}
