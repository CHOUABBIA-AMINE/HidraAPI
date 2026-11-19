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


import dz.sh.hidra.modules.simulation.application.port.in.ScenarioInputRevisionUseCase;
import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision;
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
    public static Revision revision(ScenarioInputRevisionUseCase.Result result) {
        ScenarioInputRevision v=result.revision();
        var b=v.baseline();
        var boundaries=v.inputs().boundaries().stream().map(x->new Boundary(x.slot().name(),
                x.value(),x.unit().name(),x.si(),x.slot()==ScenarioInputRevision.Slot.SUPPLY_PRESSURE
                        ?"PA":"KG_PER_S")).toList();
        return new Revision(v.scenarioId(),v.revision(),v.expectedRevision(),
                v.scope().companyId(),v.scope().pipelineSystemId(),v.inputs().name(),
                v.inputs().mode().name(),new Baseline(b.receiptId(),b.manifestId(),b.epoch(),b.digest()),
                boundaries,v.inputs().durationSeconds(),v.inputs().stepSeconds(),
                v.inputs().requestKey(),v.inputDigest(),v.policyId(),
                v.validationStatus(),v.numericalCapability(),v.recordedAt(),result.replay());
    }
    public static Validation validation(ScenarioInputRevisionUseCase.Validation v) {
        return new Validation(v.scenarioId(),v.revision(),v.inputDigest(),v.policyId(),
                v.status(),v.numericalCapability(),List.copyOf(v.diagnostics()));
    }
}
