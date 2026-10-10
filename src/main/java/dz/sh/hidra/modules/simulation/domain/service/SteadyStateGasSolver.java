/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SteadyStateGasSolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.service
 *
 * @Description : Solves bounded synthetic isothermal ideal-gas tree networks.
 *
 */
package dz.sh.hidra.modules.simulation.domain.service;

import dz.sh.hidra.modules.simulation.domain.model.SimulationPhysicalNetworkInput;
import dz.sh.hidra.modules.simulation.domain.model.SimulationPipeSegmentInput;
import dz.sh.hidra.modules.simulation.domain.model.SteadyStateGasSolution;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Educational/synthetic isothermal ideal-gas tree-network reference.
 * No SCADA inputs, equipment, loops, transient behavior, field qualification, or production EOS.
 * Every node specifies signed injection (positive means supply); one node supplies absolute pressure.
 * A connected tree has unique edge flows from node conservation and pressures from one datum.
 */
public final class SteadyStateGasSolver {
    private static final double GAS_CONSTANT = 8.31446261815324;
    private static final double GRAVITY = 9.80665;
    private static final double PI = Math.PI;

    public SteadyStateGasSolution solveSyntheticIdealGasTree(
            SimulationPhysicalNetworkInput network,
            Map<String, Double> nodeInjectionsKilogramsPerSecond,
            String pressureDatumNodeId,
            double datumPressurePascalsAbsolute,
            double temperatureKelvin,
            double molarMassKilogramsPerMole,
            double viscosityPascalSeconds) {
        if (network == null || nodeInjectionsKilogramsPerSecond == null || pressureDatumNodeId == null) {
            throw new IllegalArgumentException("Network, injections and datum must be explicit.");
        }
        positive(datumPressurePascalsAbsolute, "datum absolute pressure");
        positive(temperatureKelvin, "temperature");
        positive(molarMassKilogramsPerMole, "molar mass");
        positive(viscosityPascalSeconds, "viscosity");
        var nodes = network.nodes();
        var pipes = network.pipeSegments();
        if (pipes.size() != nodes.size() - 1) {
            throw new IllegalArgumentException("Only connected, acyclic synthetic trees supported.");
        }
        var elevations = new HashMap<String, Double>();
        for (var node : nodes) {
            elevations.put(node.id(), finite(node.elevationMeters().doubleValue(), "elevation"));
        }
        if (!elevations.containsKey(pressureDatumNodeId)
                || !nodeInjectionsKilogramsPerSecond.keySet().equals(elevations.keySet())) {
            throw new IllegalArgumentException("Exactly one injection per node and an existing pressure datum required.");
        }
        double total = 0, scale = 0;
        for (double injection : nodeInjectionsKilogramsPerSecond.values()) {
            finite(injection, "injection");
            total += injection;
            scale += Math.abs(injection);
        }
        if (Math.abs(total) > 1e-10 * Math.max(1, scale)) {
            throw new IllegalArgumentException("Closed tree must have balanced explicit injections.");
        }
        var adjacent = new HashMap<String, List<SimulationPipeSegmentInput>>();
        elevations.keySet().forEach(id -> adjacent.put(id, new ArrayList<>()));
        for (var pipe : pipes) {
            adjacent.get(pipe.fromNodeId()).add(pipe);
            adjacent.get(pipe.toNodeId()).add(pipe);
        }
        var parent = new HashMap<String, String>();
        var parentEdge = new HashMap<String, SimulationPipeSegmentInput>();
        var order = new ArrayList<String>();
        var queue = new ArrayDeque<String>();
        queue.add(pressureDatumNodeId);
        parent.put(pressureDatumNodeId, pressureDatumNodeId);
        while (!queue.isEmpty()) {
            var id = queue.removeFirst();
            order.add(id);
            for (var edge : adjacent.get(id)) {
                var other = edge.fromNodeId().equals(id) ? edge.toNodeId() : edge.fromNodeId();
                if (!parent.containsKey(other)) {
                    parent.put(other, id);
                    parentEdge.put(other, edge);
                    queue.addLast(other);
                }
            }
        }
        if (order.size() != elevations.size()) {
            throw new IllegalArgumentException("Disconnected network is unsupported.");
        }
        var subtree = new HashMap<>(nodeInjectionsKilogramsPerSecond);
        var flows = new HashMap<String, Double>();
        for (int i = order.size() - 1; i > 0; i--) {
            String child = order.get(i), par = parent.get(child);
            var edge = parentEdge.get(child);
            double childSupply = subtree.get(child);
            double oriented = edge.fromNodeId().equals(child) ? childSupply : -childSupply;
            flows.put(edge.id(), oriented);
            subtree.put(par, subtree.get(par) + childSupply);
        }

        double coefficient = molarMassKilogramsPerMole / (GAS_CONSTANT * temperatureKelvin);
        var pressures = new HashMap<String, Double>();
        pressures.put(pressureDatumNodeId, datumPressurePascalsAbsolute);
        for (int i = 1; i < order.size(); i++) {
            String child = order.get(i), par = parent.get(child);
            var edge = parentEdge.get(child);
            double parentToChildFlow = edge.fromNodeId().equals(par) ? flows.get(edge.id()) : -flows.get(edge.id());
            double signedDeltaZ = elevations.get(child) - elevations.get(par);
            double squared = downstreamSquared(pressures.get(par), parentToChildFlow,
                    edge.lengthMeters().doubleValue(), edge.internalDiameterMeters().doubleValue(),
                    edge.absoluteRoughnessMeters().doubleValue(), signedDeltaZ, coefficient,
                    viscosityPascalSeconds);
            if (!(squared > 0) || !Double.isFinite(squared)) {
                return new SteadyStateGasSolution(false, "NONPHYSICAL_PRESSURE", Map.of(), flows,
                        Map.of(), Map.of(), 1);
            }
            pressures.put(child, Math.sqrt(squared));
        }
        var balance = new HashMap<String, Double>();
        for (var id : elevations.keySet()) {
            balance.put(id, -nodeInjectionsKilogramsPerSecond.get(id));
        }
        for (var edge : pipes) {
            double mass = flows.get(edge.id());
            balance.put(edge.fromNodeId(), balance.get(edge.fromNodeId()) + mass);
            balance.put(edge.toNodeId(), balance.get(edge.toNodeId()) - mass);
        }
        var momentum = new HashMap<String, Double>();
        for (var edge : pipes) {
            double predictedSquared = downstreamSquared(pressures.get(edge.fromNodeId()), flows.get(edge.id()),
                    edge.lengthMeters().doubleValue(), edge.internalDiameterMeters().doubleValue(),
                    edge.absoluteRoughnessMeters().doubleValue(),
                    elevations.get(edge.toNodeId()) - elevations.get(edge.fromNodeId()),
                    coefficient, viscosityPascalSeconds);
            momentum.put(edge.id(), pressures.get(edge.toNodeId()) * pressures.get(edge.toNodeId()) - predictedSquared);
        }
        return new SteadyStateGasSolution(true, "CONVERGED_SYNTHETIC_IDEAL_GAS_TREE", pressures,
                flows, balance, momentum, 1);
    }

    private static double downstreamSquared(double inletPressure, double orientedMass, double length,
            double diameter, double roughness, double deltaElevation, double coefficient, double viscosity) {
        positive(inletPressure, "pressure");
        positive(length, "length");
        positive(diameter, "diameter");
        finite(roughness, "roughness");
        if (roughness < 0) throw new IllegalArgumentException("Negative roughness.");
        double area = PI * diameter * diameter / 4;
        double reynolds = 4 * Math.abs(orientedMass) / (PI * diameter * viscosity);
        double friction = 0;
        if (reynolds > 0 && reynolds < 2300) friction = 64 / reynolds;
        else if (reynolds >= 2300 && reynolds <= 4000)
            throw new IllegalArgumentException("Unsupported transitional Reynolds regime.");
        else if (reynolds > 4000) {
            // Swamee-Jain explicit Darcy approximation, synthetic numerical prototype.
            double term = roughness / (3.7 * diameter) + 5.74 / Math.pow(reynolds, 0.9);
            friction = 0.25 / Math.pow(Math.log10(term), 2);
        }
        double h = -2 * coefficient * GRAVITY * deltaElevation;
        double factor = Math.exp(h);
        double phi = Math.abs(h) < 1e-7 ? 1 + h / 2 + h * h / 6 : Math.expm1(h) / h;
        double loss = friction * coefficient * length * orientedMass * Math.abs(orientedMass) / (diameter * area * area);
        return inletPressure * inletPressure * factor - loss * phi;
    }

    private static double positive(double value, String label) {
        if (!Double.isFinite(value) || value <= 0) throw new IllegalArgumentException(label + " must be positive finite.");
        return value;
    }

    private static double finite(double value, String label) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException(label + " must be finite.");
        return value;
    }
}
