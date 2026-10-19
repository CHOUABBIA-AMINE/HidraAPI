/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringGraphDraft
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Defines bounded, immutable, author-entered station and branch incidence without physical qualification.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;


import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * An incomplete graph sketch, never a published or hydraulically qualified physical revision.
 * Identity namespaces are per element kind; parallel branches deliberately remain distinct.
 */
public record EngineeringGraphDraft(List<Node> nodes, List<Port> ports, List<Branch> branches) {
    public static final int MAX_NODES = 2048;
    public static final int MAX_PORTS = 8192;
    public static final int MAX_BRANCHES = 8192;

    public enum NodeKind { JUNCTION, STATION }
    public enum PortDirection { INLET, OUTLET, BIDIRECTIONAL }
    public enum BranchKind { PIPE, CONNECTOR }

    public record Node(String id, NodeKind kind, String facilityId) {
        public Node {
            id = identity(id);
            Objects.requireNonNull(kind, "Node kind required");
            if (kind == NodeKind.STATION) facilityId = identity(facilityId);
            else if (facilityId != null) throw new IllegalArgumentException("Junction must not claim facility");
        }
    }

    public record Port(String id, String nodeId, PortDirection direction) {
        public Port {
            id = identity(id);
            nodeId = identity(nodeId);
            Objects.requireNonNull(direction, "Port direction required");
        }
    }

    /** PIPE means a user-authored placeholder, not a dimensioned or calibrated pipe. */
    public record Branch(String id, BranchKind kind, String fromPortId, String toPortId) {
        public Branch {
            id = identity(id);
            Objects.requireNonNull(kind, "Branch kind required");
            fromPortId = identity(fromPortId);
            toPortId = identity(toPortId);
            if (fromPortId.equals(toPortId)) throw new IllegalArgumentException("Self-linked port");
        }
    }

    public EngineeringGraphDraft {
        nodes = ordered(nodes, MAX_NODES, Comparator.comparing(Node::id));
        ports = ordered(ports, MAX_PORTS, Comparator.comparing(Port::id));
        branches = ordered(branches, MAX_BRANCHES, Comparator.comparing(Branch::id));
        Map<String, Node> nodeById = new HashMap<>();
        for (Node n : nodes) if (nodeById.putIfAbsent(n.id(), n) != null)
            throw new IllegalArgumentException("Duplicate node identity");
        Map<String, Port> portById = new HashMap<>();
        for (Port p : ports) {
            if (portById.putIfAbsent(p.id(), p) != null)
                throw new IllegalArgumentException("Duplicate port identity");
            if (!nodeById.containsKey(p.nodeId())) throw new IllegalArgumentException("Dangling node reference");
        }
        Set<String> branchIds = new HashSet<>();
        for (Branch b : branches) {
            if (!branchIds.add(b.id())) throw new IllegalArgumentException("Duplicate branch identity");
            Port from = portById.get(b.fromPortId()), to = portById.get(b.toPortId());
            if (from == null || to == null) throw new IllegalArgumentException("Dangling port reference");
            if (from.nodeId().equals(to.nodeId())) throw new IllegalArgumentException("Intra-node branch");
            if (from.direction() == PortDirection.INLET || to.direction() == PortDirection.OUTLET)
                throw new IllegalArgumentException("Incompatible port direction");
        }
    }

    private static <T> List<T> ordered(List<T> source, int max, Comparator<T> order) {
        if (source == null || source.size() > max) throw new IllegalArgumentException("Graph size limit");
        ArrayList<T> copy = new ArrayList<>(source);
        if (copy.stream().anyMatch(Objects::isNull)) throw new IllegalArgumentException("Null graph element");
        copy.sort(order);
        return List.copyOf(copy);
    }

    /** Exact NFC identifiers: no trimming, replacement, control chars or ambiguous UTF-8. */
    public static String identity(String value) {
        if (value == null || value.isBlank() || !value.equals(value.strip())
                || !Normalizer.isNormalized(value, Normalizer.Form.NFC))
            throw new IllegalArgumentException("Exact NFC identity required");
        for (int i = 0; i < value.length();) {
            int cp = value.codePointAt(i);
            if (Character.isISOControl(cp) || Character.isSurrogate(value.charAt(i)) &&
                    (cp <= 0xffff || !Character.isHighSurrogate(value.charAt(i))))
                throw new IllegalArgumentException("Invalid identity code point");
            i += Character.charCount(cp);
        }
        if (value.getBytes(StandardCharsets.UTF_8).length > 80)
            throw new IllegalArgumentException("Identity byte limit");
        return value;
    }

    /**
     * Stable draft diagnostics. Even a connected graph has no physical/industrial qualification.
     * Station owner/kind/status are checked later in the authorized Topology adapter, not here.
     */
    public List<String> diagnostics() {
        if (nodes.isEmpty() && ports.isEmpty() && branches.isEmpty())
            return List.of("GRAPH_NOT_AUTHORED");
        ArrayList<String> findings = new ArrayList<>();
        if (nodes.size() < 2 || !connected()) findings.add("GRAPH_DISCONNECTED");
        if (branches.stream().noneMatch(b -> b.kind() == BranchKind.PIPE))
            findings.add("PIPE_NOT_AUTHORED");
        findings.add("PHYSICAL_VALUES_NOT_SUPPLIED");
        return List.copyOf(findings);
    }

    private boolean connected() {
        if (nodes.isEmpty()) return false;
        Map<String, String> portNodes = new HashMap<>();
        for (Port p : ports) portNodes.put(p.id(), p.nodeId());
        Map<String, Set<String>> neighbors = new HashMap<>();
        for (Node n : nodes) neighbors.put(n.id(), new HashSet<>());
        for (Branch b : branches) {
            String a = portNodes.get(b.fromPortId()), z = portNodes.get(b.toPortId());
            neighbors.get(a).add(z);
            neighbors.get(z).add(a);
        }
        var visited = new HashSet<String>();
        var pending = new ArrayDeque<String>();
        pending.add(nodes.get(0).id());
        visited.add(nodes.get(0).id());
        while (!pending.isEmpty()) {
            for (String other : neighbors.get(pending.removeFirst()))
                if (visited.add(other)) pending.addLast(other);
        }
        return visited.size() == nodes.size();
    }
}
