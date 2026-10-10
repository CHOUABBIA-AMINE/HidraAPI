/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationGasFluidRevisionContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.contract.simulation
 *
 * @Description : Exports complete immutable stored and explicitly qualified gas revision facts.
 *
 */
package dz.sh.hidra.modules.custody.application.contract.simulation;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SimulationGasFluidRevisionContract {
    enum Origin { DECLARED_PARAMETER, SYNTHETIC }
    enum ProductKind { GAS }
    enum InputRepresentation { GAS_MOLE_FRACTION }
    enum SupportedUse { STEADY_STATE, TRANSIENT }
    Optional<StoredRevision> findStored(String sourceId, String revisionId);
    Optional<QualifiedRevision> resolve(String sourceId, String revisionId, String qualificationId,
            Instant evaluationAt, SupportedUse supportedUse);
    record StoredRevision(Revision revision, String payloadFormat, String sha256) {}
    record QualifiedRevision(StoredRevision stored, Qualification qualification) {}
    record ProductSnapshot(String id,String catalogName,String code,boolean active,Instant createdAt,Instant updatedAt) {
    }
    record GovernanceBinding(String definitionId,int definitionVersion,String targetTypeId,String purposeId) {
    }
    record Component(String componentReference,BigDecimal moleFraction) {
    }
    record Method(String reference,String revisionId,Instant recordedAt,Instant effectiveFrom,Instant effectiveUntil,Origin origin,String evidenceReference,InputRepresentation inputRepresentation,List<String> allowedComponentReferences,BigDecimal minimumPressurePascalsAbsolute,BigDecimal maximumPressurePascalsAbsolute,BigDecimal minimumTemperatureKelvin,BigDecimal maximumTemperatureKelvin,List<SupportedUse> supportedUses) {
        public Method { allowedComponentReferences = List.copyOf(allowedComponentReferences); supportedUses = List.copyOf(supportedUses); }
    }
    record Revision(String sourceId,String revisionId,Instant recordedAt,Instant effectiveFrom,Instant effectiveUntil,Origin origin,String evidenceReference,ProductSnapshot productSnapshot,ProductKind productKind,Method method,List<Component> components,GovernanceBinding governanceBinding) {
        public Revision { components = List.copyOf(components); }
    }
    record Qualification(String qualificationId,String sourceId,String revisionId,String payloadSha256,Instant qualifiedAt,String workflowInstanceId,String workflowTaskId,String workflowActionId,String approverId,String approverDisplayName,Instant approvedAt,String definitionId,int definitionVersion,String targetTypeId,String purposeId) {
    }
}
