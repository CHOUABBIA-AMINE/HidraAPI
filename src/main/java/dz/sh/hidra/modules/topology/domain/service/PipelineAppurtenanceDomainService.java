/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAppurtenanceDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Domain service validating pipeline appurtenance registration.
 *
 */
package dz.sh.hidra.modules.topology.domain.service;

import java.util.Objects;

import dz.sh.hidra.modules.topology.domain.exception.TopologyValidationException;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineAppurtenance;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.policy.PipelineAppurtenancePolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyAssetStatusPolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyConnectivityPolicy;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceType;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceTypeReference;

/**
 * Validates point assets installed along pipelines.
 *
 * <p>Business role:
 * This service coordinates validation for valves, injection points, extraction points, purge points,
 * vents, drains, metering points, sampling points, scraper launchers, scraper receivers, hot taps,
 * bypass points, and other pipeline point assets.
 *
 * <p>Architecture role:
 * This service validates appurtenance catalog references by stable language-neutral codes.
 *
 * <p>Validation:
 * Appurtenances must belong to their pipeline, be represented by a compatible topology node, and
 * respect the valve/non-valve type consistency rule.
 */
public final class PipelineAppurtenanceDomainService {

    private final PipelineAppurtenancePolicy appurtenancePolicy;
    private final TopologyConnectivityPolicy connectivityPolicy;
    private final TopologyAssetStatusPolicy statusPolicy;

    public PipelineAppurtenanceDomainService() {
        this(new PipelineAppurtenancePolicy(), new TopologyConnectivityPolicy(), new TopologyAssetStatusPolicy());
    }

    public PipelineAppurtenanceDomainService(
            PipelineAppurtenancePolicy appurtenancePolicy,
            TopologyConnectivityPolicy connectivityPolicy,
            TopologyAssetStatusPolicy statusPolicy) {

        this.appurtenancePolicy = Objects.requireNonNull(appurtenancePolicy, "Pipeline appurtenance policy must not be null.");
        this.connectivityPolicy = Objects.requireNonNull(connectivityPolicy, "Topology connectivity policy must not be null.");
        this.statusPolicy = Objects.requireNonNull(statusPolicy, "Topology status policy must not be null.");
    }

    public void validateRegistration(
            Pipeline pipeline,
            PipelineAppurtenance appurtenance,
            TopologyNode node) {

        Objects.requireNonNull(pipeline, "Pipeline must not be null.");
        Objects.requireNonNull(appurtenance, "Pipeline appurtenance must not be null.");
        Objects.requireNonNull(node, "Topology node must not be null.");

        statusPolicy.requireCanAttachTopologyChild(pipeline.status());
        appurtenancePolicy.validate(appurtenance);
        appurtenancePolicy.validateBelongsToPipeline(appurtenance, pipeline);
        appurtenancePolicy.validateNodeCompatibility(appurtenance, node);
        connectivityPolicy.validateAppurtenanceNode(appurtenance, node);
    }

    public void requireValveAppurtenance(PipelineAppurtenance appurtenance) {
        requireAppurtenanceType(appurtenance, PipelineAppurtenanceTypeReference.of("VALVE", "VALVE"), "Pipeline appurtenance must be of type VALVE.");
    }

    public void requireInjectionPoint(PipelineAppurtenance appurtenance) {
        requireAppurtenanceType(appurtenance, PipelineAppurtenanceTypeReference.of("INJECTION_POINT", "INJECTION_POINT"), "Pipeline appurtenance must be an injection point.");
    }

    public void requireExtractionPoint(PipelineAppurtenance appurtenance) {
        requireAppurtenanceType(appurtenance, PipelineAppurtenanceTypeReference.of("EXTRACTION_POINT", "EXTRACTION_POINT"), "Pipeline appurtenance must be an extraction point.");
    }

    public void requirePurgePoint(PipelineAppurtenance appurtenance) {
        requireAppurtenanceType(appurtenance, PipelineAppurtenanceTypeReference.of("PURGE_POINT", "PURGE_POINT"), "Pipeline appurtenance must be a purge point.");
    }

    @Deprecated(forRemoval = true)
    private void requireAppurtenanceType(
            PipelineAppurtenance appurtenance,
            PipelineAppurtenanceType expectedType,
            String message) {

        requireAppurtenanceType(appurtenance, PipelineAppurtenanceTypeReference.from(expectedType), message);
    }

    private void requireAppurtenanceType(
            PipelineAppurtenance appurtenance,
            PipelineAppurtenanceTypeReference expectedType,
            String message) {

        Objects.requireNonNull(appurtenance, "Pipeline appurtenance must not be null.");
        Objects.requireNonNull(expectedType, "Expected appurtenance type reference must not be null.");

        appurtenancePolicy.validateValveTypeConsistency(appurtenance.appurtenanceType(), appurtenance.valveType());

        if (!appurtenance.appurtenanceType().is(expectedType.name())) {
            throw new TopologyValidationException(message);
        }
    }
}
