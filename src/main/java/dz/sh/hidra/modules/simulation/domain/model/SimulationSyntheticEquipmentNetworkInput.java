/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationSyntheticEquipmentNetworkInput
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Captures connected synthetic equipment and pipe union with source revision coherence.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import java.time.Instant;
import java.util.*;

/** A separate synthetic union; does not weaken existing pipe-only source contracts. */
public record SimulationSyntheticEquipmentNetworkInput(
        String id, String networkSourceId, String networkRevisionId, String networkSha256,
        String fluidSourceId, String fluidRevisionId, String fluidSha256, String fluidQualificationId,
        Instant at, List<SimulationNetworkNodeInput> nodes, List<SimulationPipeSegmentInput> pipes,
        SimulationEquipmentParameterRevision equipmentRevision, Map<String,String> valveMethods) {
    public SimulationSyntheticEquipmentNetworkInput {
        if (id==null||id.isBlank()||at==null||equipmentRevision==null||nodes==null||pipes==null||valveMethods==null)
            throw new IllegalArgumentException("Explicit immutable synthetic union inputs required.");
        if(!id.equals(id.trim())||nodes.size()<2)throw new IllegalArgumentException("Invalid network identity/count.");
        nodes=List.copyOf(nodes);pipes=List.copyOf(pipes);valveMethods=Map.copyOf(valveMethods);
        if (equipmentRevision.origin()!=SimulationEquipmentParameterRevision.Origin.SYNTHETIC
            ||!equipmentRevision.effectiveAt(at)
            ||!Objects.equals(equipmentRevision.networkSourceId(),networkSourceId)
            ||!Objects.equals(equipmentRevision.networkRevisionId(),networkRevisionId)
            ||!Objects.equals(equipmentRevision.networkSha256(),networkSha256)
            ||!Objects.equals(equipmentRevision.fluidSourceId(),fluidSourceId)
            ||!Objects.equals(equipmentRevision.fluidRevisionId(),fluidRevisionId)
            ||!Objects.equals(equipmentRevision.fluidSha256(),fluidSha256)
            ||!Objects.equals(equipmentRevision.fluidQualificationId(),fluidQualificationId))
            throw new IllegalArgumentException("Synthetic source revision bindings or effective time mismatch.");
        var adj=new HashMap<String,Set<String>>();var links=new HashSet<String>();
        for(var node:nodes)if(adj.putIfAbsent(node.id(),new HashSet<>())!=null)
            throw new IllegalArgumentException("Duplicate node.");
        for(var edge:pipes) {
            if(!links.add(edge.id()))throw new IllegalArgumentException("Duplicate pipe.");
            connect(adj,edge.fromNodeId(),edge.toNodeId());
        }
        var valveIds=new HashSet<String>();
        for(var e:equipmentRevision.equipment()) {
            if(!links.add(e.id()))throw new IllegalArgumentException("Duplicate equipment/pipe ID.");
            connect(adj,e.fromNodeId(),e.toNodeId());
            if(e.kind()==SimulationEquipmentParameterRevision.Kind.VALVE) {
                valveIds.add(e.id());
                if(!"SYNTHETIC_FORWARD_DP_TABLE_V1".equals(valveMethods.get(e.id())))
                    throw new IllegalArgumentException("Unapproved valve method.");
            }
        }
        if(!valveMethods.keySet().equals(valveIds)||links.isEmpty())
            throw new IllegalArgumentException("Exact valve selections and at least one real link required.");
        for(var c:equipmentRevision.compressorCurves())
            if(c.origin()!=SimulationEquipmentParameterRevision.Origin.SYNTHETIC
                    ||!"SYNTHETIC_ISOTHERMAL_HEAD_V1".equals(c.headDefinitionReference())
                    ||!"SYNTHETIC_ISOTHERMAL_EFFICIENCY_V1".equals(c.efficiencyDefinitionReference())
                    ||!"SYNTHETIC_RECTANGULAR_LINEAR_V1".equals(c.interpolationMethodReference()))
                throw new IllegalArgumentException("Unapproved compressor method.");
        for(var v:equipmentRevision.valveCharacteristics())
            if(v.origin()!=SimulationEquipmentParameterRevision.Origin.SYNTHETIC)
                throw new IllegalArgumentException("Real-source valve characteristic forbidden.");
        var visited=new HashSet<String>();var pending=new ArrayDeque<String>();
        pending.add(nodes.get(0).id());visited.add(nodes.get(0).id());
        while(!pending.isEmpty())for(var peer:adj.get(pending.removeFirst()))
            if(visited.add(peer))pending.addLast(peer);
        if(visited.size()!=nodes.size())throw new IllegalArgumentException("Disconnected structural union.");
    }
    private static void connect(Map<String,Set<String>> adj,String from,String to){
        if(!adj.containsKey(from)||!adj.containsKey(to)||from.equals(to))
            throw new IllegalArgumentException("Invalid synthetic union endpoints.");
        adj.get(from).add(to);adj.get(to).add(from);
    }
}
