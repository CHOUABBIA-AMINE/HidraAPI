/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyRegistrationDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Domain service coordinating topology asset registration validation.
 *
 */
package dz.sh.hidra.modules.topology.domain.service;

import java.util.Objects;

import dz.sh.hidra.modules.topology.domain.exception.TopologyValidationException;
import dz.sh.hidra.modules.topology.domain.model.Equipment;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineAppurtenance;
import dz.sh.hidra.modules.topology.domain.model.PipelineSegment;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.model.TopologyConnection;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.policy.FacilityTopologyPolicy;
import dz.sh.hidra.modules.topology.domain.policy.PipelineAppurtenancePolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyAssetStatusPolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyConnectivityPolicy;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;

/**
 * Coordinates domain validation when registering topology assets.
 *
 * <p>Business role:
 * This service validates multi-object registration scenarios such as adding a pipeline to a
 * pipeline system, attaching nodes to facilities, creating segments between nodes, registering
 * pipeline appurtenances, and attaching equipment references to topology assets.
 *
 * <p>Architecture role:
 * This is a pure topology domain service. It has no Spring annotation, no repository access, no
 * persistence dependency, no REST DTO dependency, and no identity or organization implementation
 * dependency.
 *
 * <p>Validation:
 * The service delegates focused rules to domain policies and adds cross-aggregate consistency checks
 * that require more than one topology object.
 *
 * <p>Usage:
 * Application services should call this domain service before saving newly created topology assets.
 */
public final class TopologyRegistrationDomainService {

    private final TopologyAssetStatusPolicy statusPolicy;
    private final TopologyConnectivityPolicy connectivityPolicy;
    private final FacilityTopologyPolicy facilityPolicy;
    private final PipelineAppurtenancePolicy appurtenancePolicy;

    /**
     * Creates a domain service with default pure-domain policies.
     */
    public TopologyRegistrationDomainService() {
        this(
                new TopologyAssetStatusPolicy(),
                new TopologyConnectivityPolicy(),
                new FacilityTopologyPolicy(),
                new PipelineAppurtenancePolicy());
    }

    /**
     * Creates a domain service with explicit policies.
     *
     * @param statusPolicy status policy
     * @param connectivityPolicy connectivity policy
     * @param facilityPolicy facility policy
     * @param appurtenancePolicy appurtenance policy
     */
    public TopologyRegistrationDomainService(
            TopologyAssetStatusPolicy statusPolicy,
            TopologyConnectivityPolicy connectivityPolicy,
            FacilityTopologyPolicy facilityPolicy,
            PipelineAppurtenancePolicy appurtenancePolicy) {

        this.statusPolicy = Objects.requireNonNull(statusPolicy, "Topology status policy must not be null.");
        this.connectivityPolicy = Objects.requireNonNull(connectivityPolicy, "Topology connectivity policy must not be null.");
        this.facilityPolicy = Objects.requireNonNull(facilityPolicy, "Facility topology policy must not be null.");
        this.appurtenancePolicy = Objects.requireNonNull(appurtenancePolicy, "Pipeline appurtenance policy must not be null.");
    }

    /**
     * Validates pipeline registration inside a pipeline system.
     *
     * @param pipelineSystem parent pipeline system
     * @param pipeline pipeline to register
     */
    public void validatePipelineRegistration(PipelineSystem pipelineSystem, Pipeline pipeline) {
        Objects.requireNonNull(pipelineSystem, "Pipeline system must not be null.");
        Objects.requireNonNull(pipeline, "Pipeline must not be null.");

        statusPolicy.requireCanAttachTopologyChild(pipelineSystem.status());

        if (!pipeline.pipelineSystemId().equals(pipelineSystem.id())) {
            throw new TopologyValidationException("Pipeline must belong to the provided pipeline system.");
        }
    }

    /**
     * Validates facility registration.
     *
     * @param facility facility to register
     */
    public void validateFacilityRegistration(Facility facility) {
        Objects.requireNonNull(facility, "Facility must not be null.");

        facilityPolicy.validateFacility(facility);
        facilityPolicy.ensurePhysicalFacilityOnly(facility);
    }

    /**
     * Validates a topology node attached to a physical facility.
     *
     * @param facility facility owning the node
     * @param node node to validate
     */
    public void validateFacilityNodeRegistration(Facility facility, TopologyNode node) {
        Objects.requireNonNull(facility, "Facility must not be null.");
        Objects.requireNonNull(node, "Topology node must not be null.");

        statusPolicy.requireCanAttachTopologyChild(facility.status());
        facilityPolicy.validateFacilityNode(facility, node);
    }

    /**
     * Validates a pipeline segment registration.
     *
     * @param pipeline parent pipeline
     * @param segment segment to register
     * @param fromNode segment from-node
     * @param toNode segment to-node
     */
    public void validatePipelineSegmentRegistration(
            Pipeline pipeline,
            PipelineSegment segment,
            TopologyNode fromNode,
            TopologyNode toNode) {

        Objects.requireNonNull(pipeline, "Pipeline must not be null.");
        Objects.requireNonNull(segment, "Pipeline segment must not be null.");
        Objects.requireNonNull(fromNode, "Pipeline segment from-node must not be null.");
        Objects.requireNonNull(toNode, "Pipeline segment to-node must not be null.");

        statusPolicy.requireCanAttachTopologyChild(pipeline.status());
        connectivityPolicy.validateSegmentBelongsToPipeline(segment, pipeline);
        connectivityPolicy.validateSegment(segment);

        if (!segment.fromNodeId().equals(fromNode.id())) {
            throw new TopologyValidationException("Pipeline segment from-node id must match the provided from-node.");
        }

        if (!segment.toNodeId().equals(toNode.id())) {
            throw new TopologyValidationException("Pipeline segment to-node id must match the provided to-node.");
        }
    }

    /**
     * Validates a pipeline appurtenance registration.
     *
     * @param pipeline parent pipeline
     * @param appurtenance appurtenance to register
     * @param node topology node representing the appurtenance
     */
    public void validatePipelineAppurtenanceRegistration(
            Pipeline pipeline,
            PipelineAppurtenance appurtenance,
            TopologyNode node) {

        Objects.requireNonNull(pipeline, "Pipeline must not be null.");
        Objects.requireNonNull(appurtenance, "Pipeline appurtenance must not be null.");
        Objects.requireNonNull(node, "Pipeline appurtenance node must not be null.");

        statusPolicy.requireCanAttachTopologyChild(pipeline.status());
        appurtenancePolicy.validate(appurtenance);
        appurtenancePolicy.validateBelongsToPipeline(appurtenance, pipeline);
        appurtenancePolicy.validateNodeCompatibility(appurtenance, node);
        connectivityPolicy.validateAppurtenanceNode(appurtenance, node);
    }

    /**
     * Validates an explicit topology connection registration.
     *
     * @param connection connection to register
     * @param fromNode connection from-node
     * @param toNode connection to-node
     */
    public void validateTopologyConnectionRegistration(
            TopologyConnection connection,
            TopologyNode fromNode,
            TopologyNode toNode) {

        Objects.requireNonNull(connection, "Topology connection must not be null.");
        Objects.requireNonNull(fromNode, "Topology connection from-node must not be null.");
        Objects.requireNonNull(toNode, "Topology connection to-node must not be null.");

        connectivityPolicy.validateConnection(connection);

        if (!connection.fromNodeId().equals(fromNode.id())) {
            throw new TopologyValidationException("Topology connection from-node id must match the provided from-node.");
        }

        if (!connection.toNodeId().equals(toNode.id())) {
            throw new TopologyValidationException("Topology connection to-node id must match the provided to-node.");
        }
    }

    /**
     * Validates an equipment reference registration.
     *
     * @param equipment equipment to register
     */
    public void validateEquipmentRegistration(Equipment equipment) {
        Objects.requireNonNull(equipment, "Equipment must not be null.");

        if (equipment.parentAssetType() == TopologyAssetType.EQUIPMENT) {
            throw new TopologyValidationException("Topology equipment must not use another equipment reference as its parent asset.");
        }

        statusPolicy.requireCanAttachTopologyChild(equipment.status());
    }
}
