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
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;

public final class EngineeringGraphDraftResponses {
    private EngineeringGraphDraftResponses() { }
    /** Transport-owned DTOs: domain records are never exposed as REST body types. */
    @Schema(name="EngineeringGraphDraftNodeResponse")
    public record Node(String id, String kind, String facilityId) {
        static Node from(EngineeringGraphDraft.Node node) {
            return new Node(node.id(), node.kind().name(), node.facilityId());
        }
    }
    @Schema(name="EngineeringGraphDraftPortResponse")
    public record Port(String id, String nodeId, String direction) {
        static Port from(EngineeringGraphDraft.Port port) {
            return new Port(port.id(), port.nodeId(), port.direction().name());
        }
    }
    @Schema(name="EngineeringGraphDraftBranchResponse")
    public record Branch(String id, String kind, String fromPortId, String toPortId) {
        static Branch from(EngineeringGraphDraft.Branch branch) {
            return new Branch(branch.id(), branch.kind().name(), branch.fromPortId(), branch.toPortId());
        }
    }
    @Schema(name="EngineeringGraphDraftResponse")
    public record Graph(UUID draftId, long version, Long graphVersion, String graphSha256,
                        List<Node> nodes, List<Port> ports, List<Branch> branches,
                        String state, boolean engineeringComplete, List<String> diagnostics) {
        public static Graph from(EngineeringGraphDraftUseCase.Result result) {
            var graph = result.graph();
            return new Graph(result.draftId(), result.version(), result.graphVersion(), result.graphSha256(),
                    graph == null ? List.of() : graph.nodes().stream().map(Node::from).toList(),
                    graph == null ? List.of() : graph.ports().stream().map(Port::from).toList(),
                    graph == null ? List.of() : graph.branches().stream().map(Branch::from).toList(),
                    "DRAFT", false, result.diagnostics());
        }
    }
}
