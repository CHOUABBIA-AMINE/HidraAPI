/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePipelineCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.command
 *
 * @Description : Application command for creating a pipeline with trilingual labels.
 *
 */
package dz.sh.hidra.modules.topology.application.command;

import java.util.Objects;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.topology.domain.value.DiameterInInches;
import dz.sh.hidra.modules.topology.domain.value.LengthInKilometers;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemId;
import dz.sh.hidra.modules.topology.domain.value.ProductTypeReference;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyMultilingualDescription;
import dz.sh.hidra.modules.topology.domain.value.TopologyMultilingualName;

/**
 * Carries input required to create a physical pipeline.
 */
public record CreatePipelineCommand(
        PipelineSystemId pipelineSystemId,
        TopologyCode code,
        TopologyMultilingualName name,
        TopologyMultilingualDescription description,
        ProductTypeReference productType,
        DiameterInInches nominalDiameter,
        LengthInKilometers designLength) implements Command {

    public CreatePipelineCommand {
        Objects.requireNonNull(pipelineSystemId, "Pipeline system id must not be null.");
        Objects.requireNonNull(code, "Pipeline code must not be null.");
        Objects.requireNonNull(name, "Pipeline trilingual name must not be null.");
        Objects.requireNonNull(productType, "Pipeline product type reference must not be null.");
        Objects.requireNonNull(nominalDiameter, "Pipeline nominal diameter must not be null.");
        Objects.requireNonNull(designLength, "Pipeline design length must not be null.");
    }
}
