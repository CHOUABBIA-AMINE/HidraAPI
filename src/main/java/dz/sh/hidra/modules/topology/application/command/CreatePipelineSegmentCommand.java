/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePipelineSegmentCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.command
 *
 * @Description : Application command for creating a pipeline segment.
 *
 */
package dz.sh.hidra.modules.topology.application.command;

import java.util.Objects;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.topology.domain.value.DiameterInInches;
import dz.sh.hidra.modules.topology.domain.value.LengthInKilometers;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;

/**
 * Carries input required to create a pipeline segment.
 *
 * <p>Business role:
 * This command creates a physical pipe segment between two topology nodes.
 *
 * <p>Architecture role:
 * This is an application command. It does not access repositories, persistence entities, or API DTOs.
 *
 * <p>Validation:
 * Pipeline id, code, name, from node, to node, length, and diameter are mandatory. From-node and
 * to-node must be different.
 *
 * @param pipelineId parent pipeline identifier
 * @param code business code
 * @param name display name
 * @param fromNodeId from-node identifier
 * @param toNodeId to-node identifier
 * @param length segment length
 * @param diameter segment diameter
 */
public record CreatePipelineSegmentCommand(
        PipelineId pipelineId,
        TopologyCode code,
        TopologyName name,
        TopologyNodeId fromNodeId,
        TopologyNodeId toNodeId,
        LengthInKilometers length,
        DiameterInInches diameter) implements Command {

    public CreatePipelineSegmentCommand {
        Objects.requireNonNull(pipelineId, "Pipeline id must not be null.");
        Objects.requireNonNull(code, "Pipeline segment code must not be null.");
        Objects.requireNonNull(name, "Pipeline segment name must not be null.");
        Objects.requireNonNull(fromNodeId, "Pipeline segment from node id must not be null.");
        Objects.requireNonNull(toNodeId, "Pipeline segment to node id must not be null.");
        Objects.requireNonNull(length, "Pipeline segment length must not be null.");
        Objects.requireNonNull(diameter, "Pipeline segment diameter must not be null.");

        if (fromNodeId.equals(toNodeId)) {
            throw new IllegalArgumentException("Pipeline segment from node and to node must be different.");
        }
    }
}
