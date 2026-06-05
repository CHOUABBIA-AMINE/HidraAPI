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

/**
 * Validates point assets installed along pipelines.
 *
 * <p>Business role:
 * This service coordinates validation for valves, injection points, extraction points, purge points,
 * vents, drains, metering points, sampling points, scraper launchers, scraper receivers, hot taps,
 * bypass points, and other pipeline point assets.
 *
 * <p>Architecture role:
 * This is a pure topology domain service. It has no Spring annotation, no repository access, no
 * persistence dependency, no REST DTO dependency, and no identity, organization, measurement,
 * operations, flow, risk, workflow, maintenance, or infrastructure dependency.
 *
 * <p>Validation:
 * Appurtenances must belong to their pipeline, be represented by a compatible topology node, and
 * respect the valve/non-valve type consistency rule.
 *
 * <p>Usage:
 * Application services should call this service before saving pipeline appurtenances.
 */
public final class PipelineAppurtenanceDomainService {

    private final PipelineAppurtenancePolicy appurtenancePolicy;
    private final TopologyConnectivityPolicy connectivityPolicy;
    private final TopologyAssetStatusPolicy statusPolicy;

    /**
     * Creates a service with default pure-domain policies.
     */
    public PipelineAppurtenanceDomainService() {
        this(
                new PipelineAppurtenancePolicy(),
                new TopologyConnectivityPolicy(),
                new TopologyAssetStatusPolicy());
    }

    /**
     * Creates a service with explicit policies.
     *
     * @param appurtenancePolicy appurtenance policy
     * @param connectivityPolicy connectivity policy
     * @param statusPolicy status policy
     */
    public PipelineAppurtenanceDomainService(
            PipelineAppurtenancePolicy appurtenancePolicy,
            TopologyConnectivityPolicy connectivityPolicy,
            TopologyAssetStatusPolicy statusPolicy) {

        this.appurtenancePolicy = Objects.requireNonNull(appurtenancePolicy, "Pipeline appurtenance policy must not be null.");
        this.connectivityPolicy = Objects.requireNonNull(connectivityPolicy, "Topology connectivity policy must not be null.");
        this.statusPolicy = Objects.requireNonNull(statusPolicy, "Topology status policy must not be null.");
    }

    /**
     * Validates a pipeline appurtenance before registration.
     *
     * @param pipeline parent pipeline
     * @param appurtenance appurtenance to validate
     * @param node topology node representing the appurtenance
     */
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

    /**
     * Validates that an appurtenance is a valve point.
     *
     * @param appurtenance appurtenance to validate
     */
    public void requireValveAppurtenance(PipelineAppurtenance appurtenance) {
        Objects.requireNonNull(appurtenance, "Pipeline appurtenance must not be null.");

        appurtenancePolicy.validateValveTypeConsistency(appurtenance.appurtenanceType(), appurtenance.valveType());

        if (appurtenance.appurtenanceType() != PipelineAppurtenanceType.VALVE) {
            throw new TopologyValidationException("Pipeline appurtenance must be of type VALVE.");
        }
    }

    /**
     * Validates that an appurtenance is an injection point.
     *
     * @param appurtenance appurtenance to validate
     */
    public void requireInjectionPoint(PipelineAppurtenance appurtenance) {
        requireAppurtenanceType(appurtenance, PipelineAppurtenanceType.INJECTION_POINT, "Pipeline appurtenance must be an injection point.");
    }

    /**
     * Validates that an appurtenance is an extraction point.
     *
     * @param appurtenance appurtenance to validate
     */
    public void requireExtractionPoint(PipelineAppurtenance appurtenance) {
        requireAppurtenanceType(appurtenance, PipelineAppurtenanceType.EXTRACTION_POINT, "Pipeline appurtenance must be an extraction point.");
    }

    /**
     * Validates that an appurtenance is a purge point.
     *
     * @param appurtenance appurtenance to validate
     */
    public void requirePurgePoint(PipelineAppurtenance appurtenance) {
        requireAppurtenanceType(appurtenance, PipelineAppurtenanceType.PURGE_POINT, "Pipeline appurtenance must be a purge point.");
    }

    private void requireAppurtenanceType(
            PipelineAppurtenance appurtenance,
            PipelineAppurtenanceType expectedType,
            String message) {

        Objects.requireNonNull(appurtenance, "Pipeline appurtenance must not be null.");
        Objects.requireNonNull(expectedType, "Expected appurtenance type must not be null.");

        appurtenancePolicy.validateValveTypeConsistency(appurtenance.appurtenanceType(), appurtenance.valveType());

        if (appurtenance.appurtenanceType() != expectedType) {
            throw new TopologyValidationException(message);
        }
    }
}
