/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringGraphDraftResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : Describes versioned draft-only graph output without publication or physics claims.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;

public final class EngineeringGraphDraftResponses {
    private EngineeringGraphDraftResponses() { }
    /** Transport-owned DTOs: domain records are never exposed as REST body types. */
    @Schema(name="EngineeringGraphDraftNodeResponse")
    public record Node(String id, String kind, String facilityId) {
    }
    @Schema(name="EngineeringGraphDraftPortResponse")
    public record Port(String id, String nodeId, String direction) {
    }
    @Schema(name="EngineeringGraphDraftBranchResponse")
    public record Branch(String id, String kind, String fromPortId, String toPortId) {
    }
    @Schema(name="EngineeringGraphDraftResponse")
    public record Graph(UUID draftId, long version, Long graphVersion, String graphSha256,
                        List<Node> nodes, List<Port> ports, List<Branch> branches,
                        String state, boolean engineeringComplete, List<String> diagnostics) {
        public static Graph from(EngineeringGraphDraftUseCase.Result result) {
            var graph = result.graph();
            return new Graph(result.draftId(), result.version(), result.graphVersion(), result.graphSha256(),
                    graph == null ? List.of() : graph.nodes().stream().map(n -> new Node(n.id(), n.kind().name(), n.facilityId())).toList(),
                    graph == null ? List.of() : graph.ports().stream().map(port -> new Port(port.id(), port.nodeId(), port.direction().name())).toList(),
                    graph == null ? List.of() : graph.branches().stream().map(branch -> new Branch(branch.id(), branch.kind().name(), branch.fromPortId(), branch.toPortId())).toList(),
                    "DRAFT", false, result.diagnostics());
        }
    }
}
