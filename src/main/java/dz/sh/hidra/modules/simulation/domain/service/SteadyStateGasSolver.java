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
 * @Description : Solves bounded synthetic isothermal ideal-gas pipe networks.
 *
 */
package dz.sh.hidra.modules.simulation.domain.service;

import dz.sh.hidra.modules.simulation.domain.model.SimulationPhysicalNetworkInput;
import dz.sh.hidra.modules.simulation.domain.model.SimulationPipeSegmentInput;
import dz.sh.hidra.modules.simulation.domain.model.SteadyStateGasSolution;
import dz.sh.hidra.modules.simulation.domain.model.SimulationSyntheticEquipmentNetworkInput;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentGasSolution;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision.*;
import java.util.Set;
import java.util.EnumMap;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Educational/synthetic isothermal ideal-gas tree-network reference.
 * No SCADA inputs, equipment, transient behavior, field qualification, or production EOS.
 * The separate network entry point handles mixed boundaries, loops and parallel pipes.
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
        double loss = friction * length * orientedMass * Math.abs(orientedMass) / (coefficient * diameter * area * area);
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

    /** Exactly one pressure or injection boundary per node; pressure exchange is an output. */
    public record Boundary(Double pressurePascalsAbsolute, Double injectionKilogramsPerSecond) {
        public Boundary {
            if ((pressurePascalsAbsolute == null) == (injectionKilogramsPerSecond == null)) {
                throw new IllegalArgumentException("Exactly one boundary type is required.");
            }
            if (pressurePascalsAbsolute != null) positive(pressurePascalsAbsolute, "boundary pressure");
            else finite(injectionKilogramsPerSecond, "boundary injection");
        }
    }

    public record SyntheticGasProperties(double temperatureKelvin, double molarMassKilogramsPerMole,
            double viscosityPascalSeconds) {
        public SyntheticGasProperties {
            positive(temperatureKelvin, "temperature");
            positive(molarMassKilogramsPerMole, "molar mass");
            positive(viscosityPascalSeconds, "viscosity");
            positive(molarMassKilogramsPerMole / (GAS_CONSTANT * temperatureKelvin), "density coefficient");
        }
    }

    /** Numerical development controls, never an operational eligibility policy. */
    public record NumericalControls(double pressureScalePascals, double flowScaleKilogramsPerSecond,
            double massToleranceKilogramsPerSecond, double momentumRelativeTolerance,
            double relativePivotTolerance, int maximumIterations, int maximumLineSearchSteps) {
        public NumericalControls {
            positive(pressureScalePascals, "pressure scale");
            positive(pressureScalePascals * pressureScalePascals, "squared pressure scale");
            positive(flowScaleKilogramsPerSecond, "flow scale");
            positive(massToleranceKilogramsPerSecond, "mass tolerance");
            positive(momentumRelativeTolerance, "momentum tolerance");
            positive(relativePivotTolerance, "pivot tolerance");
            if (relativePivotTolerance >= 1 || maximumIterations < 1 || maximumLineSearchSteps < 1) {
                throw new IllegalArgumentException("Invalid iteration or pivot controls.");
            }
        }
    }

    private record Edge(String id, int from, int to, double diameter, double roughness,
            double exponential, double lossFactor, double viscosity) { }
    private record Evaluation(double[] residual, double[][] jacobian, double merit, String failure) { }

    /** Dense deterministic damped Newton reference for the explicitly synthetic ideal-gas model. */
    public SteadyStateGasSolution solveSyntheticIdealGasNetwork(SimulationPhysicalNetworkInput network,
            Map<String, Boundary> suppliedBoundaries, SyntheticGasProperties gas, NumericalControls controls,
            Map<String, Double> initialUnknownPressurePascals, Map<String, Double> initialPipeFlows) {
        if (network == null || suppliedBoundaries == null || gas == null || controls == null
                || initialUnknownPressurePascals == null || initialPipeFlows == null) {
            throw new IllegalArgumentException("Network, boundaries, properties, controls and guesses required.");
        }
        var boundaries = Map.copyOf(suppliedBoundaries);
        var pressureGuesses = Map.copyOf(initialUnknownPressurePascals);
        var flowGuesses = Map.copyOf(initialPipeFlows);
        var nodes = network.nodes().stream().sorted(Comparator.comparing(n -> n.id())).toList();
        var pipes = network.pipeSegments().stream().sorted(Comparator.comparing(p -> p.id())).toList();
        var index = new HashMap<String, Integer>();
        for (int i = 0; i < nodes.size(); i++) index.put(nodes.get(i).id(), i);
        if (!boundaries.keySet().equals(index.keySet())) throw new IllegalArgumentException("Exact node boundaries required.");
        var unknownIds = new HashSet<String>();
        var pipeIds = new HashSet<String>();
        int[] columns = new int[nodes.size()];
        Arrays.fill(columns, -1);
        double p2Scale = controls.pressureScalePascals() * controls.pressureScalePascals();
        double[] fixedSquared = new double[nodes.size()];
        double[] injections = new double[nodes.size()];
        int unknownCount = 0;
        for (int i = 0; i < nodes.size(); i++) {
            var b = boundaries.get(nodes.get(i).id());
            if (b.pressurePascalsAbsolute() == null) {
                columns[i] = unknownCount++; unknownIds.add(nodes.get(i).id());
                injections[i] = b.injectionKilogramsPerSecond();
            } else fixedSquared[i] = positive(b.pressurePascalsAbsolute() * b.pressurePascalsAbsolute() / p2Scale, "fixed squared pressure");
        }
        if (unknownCount == nodes.size()) throw new IllegalArgumentException("At least one pressure anchor required.");
        pipes.forEach(p -> pipeIds.add(p.id()));
        if (!pressureGuesses.keySet().equals(unknownIds) || !flowGuesses.keySet().equals(pipeIds)) {
            throw new IllegalArgumentException("Exact unknown pressure and pipe flow guesses required.");
        }
        double[] state = new double[unknownCount + pipes.size()];
        for (int i = 0; i < nodes.size(); i++) if (columns[i] >= 0) {
            double p = positive(pressureGuesses.get(nodes.get(i).id()), "initial pressure");
            state[columns[i]] = positive(p * p / p2Scale, "initial squared pressure");
        }
        double c = gas.molarMassKilogramsPerMole() / (GAS_CONSTANT * gas.temperatureKelvin());
        var edges = new ArrayList<Edge>();
        for (int j = 0; j < pipes.size(); j++) {
            var p = pipes.get(j);
            double d = positive(p.internalDiameterMeters().doubleValue(), "diameter");
            double length = positive(p.lengthMeters().doubleValue(), "length");
            double roughness = finite(p.absoluteRoughnessMeters().doubleValue(), "roughness");
            if (roughness < 0) throw new IllegalArgumentException("Negative roughness.");
            int from = index.get(p.fromNodeId()), to = index.get(p.toNodeId());
            double dz = finite(nodes.get(to).elevationMeters().doubleValue(), "elevation")
                    - finite(nodes.get(from).elevationMeters().doubleValue(), "elevation");
            double h = finite(-2 * c * GRAVITY * dz, "elevation exponent");
            double phi = Math.abs(h) < 1e-7 ? 1 + h / 2 + h * h / 6 : Math.expm1(h) / h;
            double area = PI * d * d / 4;
            edges.add(new Edge(p.id(), from, to, d, roughness,
                    positive(Math.exp(h), "hydrostatic factor"),
                    positive(length * phi / (c * d * area * area), "friction loss factor"), gas.viscosityPascalSeconds()));
            state[unknownCount + j] = finite(flowGuesses.get(p.id()), "initial flow") / controls.flowScaleKilogramsPerSecond();
            finite(state[unknownCount + j], "scaled initial flow");
        }
        Evaluation evaluation = evaluate(state, columns, fixedSquared, injections, edges, unknownCount, controls);
        if (evaluation.failure() != null) return solution(false, evaluation.failure(), 0, state,
                columns, fixedSquared, injections, edges, nodes.stream().map(n -> n.id()).toList(), controls);
        int iterations = 0;
        while (evaluation.merit() > 1 && iterations < controls.maximumIterations()) {
            double[] step = linearStep(evaluation.jacobian(), evaluation.residual(), controls.relativePivotTolerance());
            if (step == null) return solution(false, "SINGULAR_OR_ILL_CONDITIONED", iterations, state,
                    columns, fixedSquared, injections, edges, nodes.stream().map(n -> n.id()).toList(), controls);
            boolean accepted = false; String trialFailure = null;
            double alpha = 1;
            for (int attempt = 0; attempt < controls.maximumLineSearchSteps(); attempt++, alpha *= 0.5) {
                var candidate = state.clone();
                for (int j = 0; j < state.length; j++) candidate[j] += alpha * step[j];
                var trial = evaluate(candidate, columns, fixedSquared, injections, edges, unknownCount, controls);
                if (trial.failure() == null && (trial.merit() <= 1 || trial.merit() < evaluation.merit() * (1 - 1e-4 * alpha))) {
                    state = candidate; evaluation = trial; accepted = true; break;
                }
                if (trial.failure() != null) trialFailure = trial.failure();
            }
            iterations++;
            if (!accepted) return solution(false, trialFailure == null ? "LINE_SEARCH_FAILED" : trialFailure,
                    iterations, state, columns, fixedSquared, injections, edges, nodes.stream().map(n -> n.id()).toList(), controls);
        }
        boolean converged = evaluation.merit() <= 1;
        return solution(converged, converged ? "CONVERGED_SYNTHETIC_IDEAL_GAS_NETWORK" : "ITERATION_LIMIT",
                iterations, state, columns, fixedSquared, injections, edges, nodes.stream().map(n -> n.id()).toList(), controls);
    }

    private static Evaluation evaluate(double[] state, int[] columns, double[] fixed,
            double[] injections, List<Edge> edges, int unknownCount, NumericalControls controls) {
        int size = state.length;
        var residual = new double[size]; var jacobian = new double[size][size];
        double p2Scale = controls.pressureScalePascals() * controls.pressureScalePascals();
        double flowScale = controls.flowScaleKilogramsPerSecond();
        for (double v : state) if (!Double.isFinite(v)) return new Evaluation(residual, jacobian, Double.POSITIVE_INFINITY, "NONPHYSICAL_PRESSURE");
        for (int i = 0; i < unknownCount; i++) if (state[i] <= 0) return new Evaluation(residual, jacobian, Double.POSITIVE_INFINITY, "NONPHYSICAL_PRESSURE");
        for (int i = 0; i < columns.length; i++) if (columns[i] >= 0) residual[columns[i]] = -injections[i] / flowScale;
        for (int j = 0; j < edges.size(); j++) {
            var e = edges.get(j); int flowColumn = unknownCount + j;
            double q = state[flowColumn] * flowScale;
            double[] friction = frictionProductAndDerivative(q, e);
            if (friction == null) return new Evaluation(residual, jacobian, Double.POSITIVE_INFINITY, "UNSUPPORTED_PROPERTY_REGIME");
            int row = unknownCount + j;
            double from = columns[e.from()] < 0 ? fixed[e.from()] : state[columns[e.from()]];
            double to = columns[e.to()] < 0 ? fixed[e.to()] : state[columns[e.to()]];
            residual[row] = to - e.exponential() * from + e.lossFactor() * friction[0] / p2Scale;
            if (columns[e.from()] >= 0) {
                residual[columns[e.from()]] += state[flowColumn];
                jacobian[columns[e.from()]][flowColumn] += 1;
                jacobian[row][columns[e.from()]] -= e.exponential();
            }
            if (columns[e.to()] >= 0) {
                residual[columns[e.to()]] -= state[flowColumn];
                jacobian[columns[e.to()]][flowColumn] -= 1;
                jacobian[row][columns[e.to()]] += 1;
            }
            jacobian[row][flowColumn] = e.lossFactor() * friction[1] * flowScale / p2Scale;
        }
        double merit = 0;
        for (int i = 0; i < size; i++) {
            if (!Double.isFinite(residual[i])) return new Evaluation(residual, jacobian, Double.POSITIVE_INFINITY, "NONPHYSICAL_PRESSURE");
            double tolerance = i < unknownCount ? controls.massToleranceKilogramsPerSecond() / flowScale : controls.momentumRelativeTolerance();
            merit = Math.max(merit, Math.abs(residual[i]) / tolerance);
            for (double entry : jacobian[i]) if (!Double.isFinite(entry)) return new Evaluation(residual, jacobian, Double.POSITIVE_INFINITY, "SINGULAR_OR_ILL_CONDITIONED");
        }
        return new Evaluation(residual, jacobian, merit, null);
    }

    /** G(q)=f_D(q)*q*abs(q), with exact continuous laminar extension at zero. */
    private static double[] frictionProductAndDerivative(double q, Edge e) {
        double magnitude = Math.abs(q), rePerMass = 4 / (PI * e.diameter() * e.viscosity());
        double re = magnitude * rePerMass;
        if (re < 2300) {
            double slope = 16 * PI * e.diameter() * e.viscosity();
            return new double[]{slope * q, slope};
        }
        if (re <= 4000 || !Double.isFinite(re)) return null;
        double term = e.roughness() / (3.7 * e.diameter()) + 5.74 / Math.pow(re, 0.9);
        double logarithm = Math.log10(term);
        if (!(term > 0) || !Double.isFinite(term) || logarithm == 0) return null;
        double f = 0.25 / (logarithm * logarithm);
        double dTerm = -0.9 * 5.74 * Math.pow(re, -1.9) * rePerMass;
        double df = -0.5 * dTerm / (Math.log(10) * term * logarithm * logarithm * logarithm);
        return new double[]{f * q * magnitude, 2 * magnitude * f + magnitude * magnitude * df};
    }

    /** Partial pivoting on the explicitly scaled Jacobian, with caller-selected refusal limit. */
    private static double[] linearStep(double[][] source, double[] residual, double pivotTolerance) {
        int n = residual.length; var matrix = new double[n][n]; var rhs = new double[n];
        double maximum = 0;
        for (int i = 0; i < n; i++) {
            matrix[i] = source[i].clone(); rhs[i] = -residual[i];
            for (double v : matrix[i]) maximum = Math.max(maximum, Math.abs(v));
        }
        if (!(maximum > 0)) return null;
        for (int k = 0; k < n; k++) {
            int pivot = k;
            for (int i = k + 1; i < n; i++) if (Math.abs(matrix[i][k]) > Math.abs(matrix[pivot][k])) pivot = i;
            if (Math.abs(matrix[pivot][k]) <= pivotTolerance * maximum) return null;
            var row = matrix[k]; matrix[k] = matrix[pivot]; matrix[pivot] = row;
            double b = rhs[k]; rhs[k] = rhs[pivot]; rhs[pivot] = b;
            for (int i = k + 1; i < n; i++) {
                double factor = matrix[i][k] / matrix[k][k]; matrix[i][k] = 0;
                for (int j = k + 1; j < n; j++) matrix[i][j] -= factor * matrix[k][j];
                rhs[i] -= factor * rhs[k];
            }
        }
        var step = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double value = rhs[i];
            for (int j = i + 1; j < n; j++) value -= matrix[i][j] * step[j];
            step[i] = value / matrix[i][i]; if (!Double.isFinite(step[i])) return null;
        }
        return step;
    }

    private static SteadyStateGasSolution solution(boolean converged, String status, int iterations,
            double[] state, int[] columns, double[] fixed, double[] injections, List<Edge> edges,
            List<String> ids, NumericalControls controls) {
        int unknownCount = state.length - edges.size();
        var pressures = new HashMap<String, Double>(); var flows = new HashMap<String, Double>();
        var mass = new HashMap<String, Double>(); var momentum = new HashMap<String, Double>();
        var exchanges = new HashMap<String, Double>(); var net = new double[ids.size()];
        double scale = controls.pressureScalePascals() * controls.pressureScalePascals();
        for (int i = 0; i < ids.size(); i++) pressures.put(ids.get(i), Math.sqrt(columns[i] < 0 ? fixed[i] : state[columns[i]]) * controls.pressureScalePascals());
        for (int j = 0; j < edges.size(); j++) {
            var e = edges.get(j); double q = state[unknownCount + j] * controls.flowScaleKilogramsPerSecond();
            flows.put(e.id(), q); net[e.from()] += q; net[e.to()] -= q;
            var product = frictionProductAndDerivative(q, e);
            if (product != null) {
                double from = columns[e.from()] < 0 ? fixed[e.from()] : state[columns[e.from()]];
                double to = columns[e.to()] < 0 ? fixed[e.to()] : state[columns[e.to()]];
                double value = (to - e.exponential() * from) * scale + e.lossFactor() * product[0];
                if (Double.isFinite(value)) momentum.put(e.id(), value);
            }
        }
        for (int i = 0; i < ids.size(); i++) {
            if (columns[i] < 0) exchanges.put(ids.get(i), net[i]);
            else mass.put(ids.get(i), net[i] - injections[i]);
        }
        // Pressure boundaries have computed exchanges, not independent zero-injection constraints.
        return new SteadyStateGasSolution(converged, status, pressures, flows, mass, momentum, iterations, exchanges);
    }

    private record SyntheticEquipmentEdge(String id, int from, int to, Equipment equipment,
            CompressorCurve compressor, ValveCharacteristic valve, String method, boolean closed,
            Map<Quantity,GovernedLimit> limits) { }
    private record SyntheticEquipmentContext(List<String> ids, List<Edge> pipes, List<SyntheticEquipmentEdge> equipment,
            int[] columns, double[] fixed, double[] injections, int unknownCount,
            SyntheticGasProperties gas, NumericalControls controls) { }

    /** Independent union mode: synthetic map-only equipment, no operational source promotion. */
    public SimulationEquipmentGasSolution solveSyntheticIdealGasEquipmentNetwork(
            SimulationSyntheticEquipmentNetworkInput input, Map<String, Boundary> nodeBoundaries,
            SyntheticGasProperties gas, NumericalControls controls,
            Map<String, Double> unknownPressureGuesses,
            Map<String, Double> realPipeFlowGuesses, Map<String, Double> equipmentFlowGuesses) {
        if (input == null || nodeBoundaries == null || gas == null || controls == null
                || unknownPressureGuesses == null || realPipeFlowGuesses == null || equipmentFlowGuesses == null)
            throw new IllegalArgumentException("All synthetic union inputs, controls and guesses required.");
        var nodes=input.nodes().stream().sorted(Comparator.comparing(n->n.id())).toList();
        var pipes=input.pipes().stream().sorted(Comparator.comparing(p->p.id())).toList();
        var devices=input.equipmentRevision().equipment().stream().sorted(Comparator.comparing(e->e.id())).toList();
        var index=new HashMap<String,Integer>();
        for(int i=0;i<nodes.size();i++)index.put(nodes.get(i).id(),i);
        if(!nodeBoundaries.keySet().equals(index.keySet()))throw new IllegalArgumentException("Exact union node boundaries required.");
        double scale=controls.pressureScalePascals(), qscale=controls.flowScaleKilogramsPerSecond();
        double s2=scale*scale;
        int[] columns=new int[nodes.size()];
        Arrays.fill(columns,-1);
        var fixed=new double[nodes.size()];
        var injections=new double[nodes.size()];
        var unknowns=new HashSet<String>();
        int nunknown=0;
        for(int i=0;i<nodes.size();i++){
            var id=nodes.get(i).id();var b=nodeBoundaries.get(id);
            if(b==null)throw new IllegalArgumentException("Null boundary.");
            if(b.pressurePascalsAbsolute()!=null){
                fixed[i]=positive(b.pressurePascalsAbsolute()*b.pressurePascalsAbsolute()/s2,"pressure boundary");
            }else{
                columns[i]=nunknown++;unknowns.add(id);injections[i]=b.injectionKilogramsPerSecond();
            }
        }
        if(nunknown==nodes.size()||!unknownPressureGuesses.keySet().equals(unknowns))
            throw new IllegalArgumentException("Exact unknown pressures and at least one datum required.");
        var pid=new HashSet<String>();pipes.forEach(p->pid.add(p.id()));
        var eid=new HashSet<String>();devices.forEach(e->eid.add(e.id()));
        if(!realPipeFlowGuesses.keySet().equals(pid)||!equipmentFlowGuesses.keySet().equals(eid))
            throw new IllegalArgumentException("Exact real pipe/equipment flow guesses required.");
        double c=gas.molarMassKilogramsPerMole()/(GAS_CONSTANT*gas.temperatureKelvin());
        var pipeEdges=new ArrayList<Edge>();
        for(var pipe:pipes){
            double d=positive(pipe.internalDiameterMeters().doubleValue(),"diameter");
            double length=positive(pipe.lengthMeters().doubleValue(),"length");
            double roughness=finite(pipe.absoluteRoughnessMeters().doubleValue(),"roughness");
            if(roughness<0)throw new IllegalArgumentException("Negative roughness.");
            int f=index.get(pipe.fromNodeId()),t=index.get(pipe.toNodeId());
            double dz=nodes.get(t).elevationMeters().doubleValue()-nodes.get(f).elevationMeters().doubleValue();
            double h=finite(-2*c*GRAVITY*dz,"elevation exponent");
            double phi=Math.abs(h)<1e-7?1+h/2+h*h/6:Math.expm1(h)/h;
            double area=PI*d*d/4;
            pipeEdges.add(new Edge(pipe.id(),f,t,d,roughness,positive(Math.exp(h),"hydrostatic factor"),
                    positive(length*phi/(c*d*area*area),"friction factor"),gas.viscosityPascalSeconds()));
        }
        var curves=new HashMap<String,CompressorCurve>();
        for(var e:input.equipmentRevision().compressorCurves())curves.put(e.id()+"@"+e.revisionId(),e);
        var valves=new HashMap<String,ValveCharacteristic>();
        for(var e:input.equipmentRevision().valveCharacteristics())valves.put(e.id()+"@"+e.revisionId(),e);
        var limitsByEquipment=new HashMap<String,EnumMap<Quantity,GovernedLimit>>();
        for(var limit:input.equipmentRevision().governedLimits())
            limitsByEquipment.computeIfAbsent(limit.equipmentId(),k->new EnumMap<>(Quantity.class))
                    .put(limit.quantity(),limit);
        var evaluator=new SimulationEquipmentBehaviorEvaluator();
        var equipmentEdges=new ArrayList<SyntheticEquipmentEdge>();
        var adjacent=new HashMap<Integer,Set<Integer>>();
        for(int i=0;i<nodes.size();i++)adjacent.put(i,new HashSet<>());
        for(var edge:pipeEdges){adjacent.get(edge.from()).add(edge.to());adjacent.get(edge.to()).add(edge.from());}
        for(var e:devices){
            int f=index.get(e.fromNodeId()),t=index.get(e.toNodeId());
            if(nodes.get(f).elevationMeters().compareTo(nodes.get(t).elevationMeters())!=0)
                throw new IllegalArgumentException("Synthetic equipment requires equal endpoint elevation.");
            CompressorCurve curve=null;ValveCharacteristic valve=null;boolean closed=false;
            if(e.kind()==Kind.COMPRESSOR){
                curve=curves.get(e.curveId()+"@"+e.curveRevisionId());
                if(curve==null||columns[f]>=0||nodeBoundaries.get(e.fromNodeId()).pressurePascalsAbsolute()
                        !=curve.referenceInletPressurePascalsAbsolute().doubleValue()
                        ||gas.temperatureKelvin()!=curve.referenceInletTemperatureKelvin().doubleValue())
                    throw new IllegalArgumentException("Compressor requires exact anchored reference inlet P/T.");
                evaluator.compressor(curve,e.configuredSpeedRevolutionsPerMinute().doubleValue()==0?0:
                        curve.speedLines().getFirst().points().getFirst().massFlowKilogramsPerSecond().doubleValue(),
                        e.configuredSpeedRevolutionsPerMinute().doubleValue());
            } else {
                valve=valves.get(e.characteristicId()+"@"+e.characteristicRevisionId());
                if(valve==null||gas.temperatureKelvin()!=valve.referenceTemperatureKelvin().doubleValue())
                    throw new IllegalArgumentException("Valve requires exact reference T.");
                closed=e.configuredOpeningFraction().doubleValue()==0;
                if(closed){
                    var zero=evaluator.valve(valve,input.valveMethods().get(e.id()),0,0);
                    if(!zero.closed())throw new IllegalArgumentException("Closed valve map must have zero flow.");
                    for(var point:valve.openingLines().getFirst().points())
                        if(point.massFlowKilogramsPerSecond().signum()!=0)
                            throw new IllegalArgumentException("Closed valve line must be entirely zero.");
                }else evaluator.valve(valve,input.valveMethods().get(e.id()),0,e.configuredOpeningFraction().doubleValue());
            }
            equipmentEdges.add(new SyntheticEquipmentEdge(e.id(),f,t,e,curve,valve,input.valveMethods().get(e.id()),closed,Map.copyOf(limitsByEquipment.get(e.id()))));
            if(!closed){adjacent.get(f).add(t);adjacent.get(t).add(f);}
        }
        var seen=new HashSet<Integer>();
        for(int root=0;root<nodes.size();root++)if(seen.add(root)){
            var queue=new ArrayDeque<Integer>();queue.add(root);boolean anchored=false;
            while(!queue.isEmpty()){
                int v=queue.removeFirst();
                if(columns[v]<0)anchored=true;
                for(int next:adjacent.get(v))if(seen.add(next))queue.add(next);
            }
            if(!anchored)throw new IllegalArgumentException("Unanchored active component after isolation.");
        }
        var ctx=new SyntheticEquipmentContext(nodes.stream().map(n->n.id()).toList(),pipeEdges,
                equipmentEdges,columns,fixed,injections,nunknown,gas,controls);
        int n=nunknown+pipeEdges.size()+equipmentEdges.size();
        var state=new double[n];
        for(int i=0;i<nodes.size();i++)if(columns[i]>=0){
            double p=positive(unknownPressureGuesses.get(nodes.get(i).id()),"initial pressure");
            state[columns[i]]=positive(p*p/s2,"scaled initial pressure");
        }
        for(int j=0;j<pipeEdges.size();j++)
            state[nunknown+j]=finite(realPipeFlowGuesses.get(pipeEdges.get(j).id()),"initial pipe flow")/qscale;
        for(int j=0;j<equipmentEdges.size();j++)
            state[nunknown+pipeEdges.size()+j]=finite(equipmentFlowGuesses.get(equipmentEdges.get(j).id()),"initial equipment flow")/qscale;
        var evaluation=evaluateEquipment(state,ctx,evaluator);
        if(evaluation.failure()!=null)
            throw new IllegalArgumentException("Invalid initial synthetic hydraulic guess: "+evaluation.failure());
        int iterations=0;
        while(evaluation.merit()>1&&iterations<controls.maximumIterations()){
            var step=linearStep(evaluation.jacobian(),evaluation.residual(),controls.relativePivotTolerance());
            if(step==null)return equipmentSolution(false,"SINGULAR_OR_ILL_CONDITIONED",iterations,state,ctx,evaluator);
            boolean accepted=false;String failure=null;
            double alpha=1;
            for(int attempt=0;attempt<controls.maximumLineSearchSteps();attempt++,alpha*=0.5){
                var trialState=state.clone();
                for(int i=0;i<n;i++)trialState[i]+=alpha*step[i];
                var trial=evaluateEquipment(trialState,ctx,evaluator);
                if(trial.failure()==null&&(trial.merit()<=1||trial.merit()<evaluation.merit()*(1-1e-4*alpha))){
                    state=trialState;evaluation=trial;accepted=true;break;
                }
                failure=trial.failure();
            }
            iterations++;
            if(!accepted)return equipmentSolution(false,failure==null?"LINE_SEARCH_FAILED":failure,iterations,state,ctx,evaluator);
        }
        boolean converged=evaluation.merit()<=1;
        return equipmentSolution(converged,converged?"CONVERGED_SYNTHETIC_GAS_EQUIPMENT_V1":"ITERATION_LIMIT",
                iterations,state,ctx,evaluator);
    }

    private static Evaluation evaluateEquipment(double[] state,SyntheticEquipmentContext ctx,
            SimulationEquipmentBehaviorEvaluator evaluator){
        int size=state.length,unknown=ctx.unknownCount();
        var residual=new double[size];var jacobian=new double[size][size];
        double S=ctx.controls().pressureScalePascals(),Q=ctx.controls().flowScaleKilogramsPerSecond(),S2=S*S;
        for(double v:state)if(!Double.isFinite(v))
            return new Evaluation(residual,jacobian,Double.POSITIVE_INFINITY,"NONFINITE_STATE");
        for(int i=0;i<unknown;i++)if(!(state[i]>0))
            return new Evaluation(residual,jacobian,Double.POSITIVE_INFINITY,"NONPHYSICAL_PRESSURE");
        var u=new double[ctx.ids().size()];
        var pressure=new double[u.length];
        for(int i=0;i<u.length;i++){
            u[i]=ctx.columns()[i]<0?ctx.fixed()[i]:state[ctx.columns()[i]];
            if(!(u[i]>0))return new Evaluation(residual,jacobian,Double.POSITIVE_INFINITY,"NONPHYSICAL_PRESSURE");
            pressure[i]=S*Math.sqrt(u[i]);
        }
        for(int i=0;i<u.length;i++)if(ctx.columns()[i]>=0)
            residual[ctx.columns()[i]]=-ctx.injections()[i]/Q;
        for(int j=0;j<ctx.pipes().size();j++){
            var e=ctx.pipes().get(j);int row=unknown+j,flowColumn=row;
            double q=state[flowColumn]*Q;
            var loss=frictionProductAndDerivative(q,e);
            if(loss==null)return new Evaluation(residual,jacobian,Double.POSITIVE_INFINITY,"UNSUPPORTED_PIPE_REYNOLDS");
            residual[row]=u[e.to()]-e.exponential()*u[e.from()]+e.lossFactor()*loss[0]/S2;
            addFlow(residual,jacobian,ctx.columns(),e.from(),e.to(),flowColumn,state[flowColumn]);
            if(ctx.columns()[e.from()]>=0)jacobian[row][ctx.columns()[e.from()]]-=e.exponential();
            if(ctx.columns()[e.to()]>=0)jacobian[row][ctx.columns()[e.to()]]+=1;
            jacobian[row][flowColumn]=e.lossFactor()*loss[1]*Q/S2;
        }
        for(int j=0;j<ctx.equipment().size();j++){
            var e=ctx.equipment().get(j);
            int row=unknown+ctx.pipes().size()+j,flowColumn=row;
            double q=state[flowColumn]*Q;
            double pFrom=pressure[e.from()],pTo=pressure[e.to()];
            addFlow(residual,jacobian,ctx.columns(),e.from(),e.to(),flowColumn,state[flowColumn]);
            try{
                verifyLimits(e,ctx.gas().temperatureKelvin(),pFrom,q);
                if(e.compressor()!=null){
                    var v=evaluator.compressor(e.compressor(),q,e.equipment().configuredSpeedRevolutionsPerMinute().doubleValue());
                    double rT=GAS_CONSTANT*ctx.gas().temperatureKelvin()/ctx.gas().molarMassKilogramsPerMole();
                    if(q<=0||pTo<pFrom)
                        throw new IllegalArgumentException("Compressor reverse or noncompressing regime.");
                    residual[row]=Math.log(pTo/pFrom)-v.headJoulesPerKilogram()/rT;
                    if(ctx.columns()[e.from()]>=0)jacobian[row][ctx.columns()[e.from()]]=-0.5/u[e.from()];
                    if(ctx.columns()[e.to()]>=0)jacobian[row][ctx.columns()[e.to()]]=0.5/u[e.to()];
                    jacobian[row][flowColumn]=-v.headFlowDerivative()*Q/rT;
                } else if(e.closed()){
                    double dp=Math.abs(pFrom-pTo);
                    var max=e.valve().openingLines().getFirst().points().getLast().differentialPressurePascals().doubleValue();
                    if(dp>max)throw new IllegalArgumentException("Closed valve pressure exceeds synthetic map.");
                    residual[row]=state[flowColumn];
                    jacobian[row][flowColumn]=1;
                }else{
                    double dp=pFrom-pTo;
                    if(dp<0||q<0)throw new IllegalArgumentException("Active valve reverse differential/flow.");
                    var value=evaluator.valve(e.valve(),e.method(),dp,e.equipment().configuredOpeningFraction().doubleValue());
                    if(value.closed())throw new IllegalArgumentException("Zero flow at nonzero valve opening.");
                    residual[row]=(q-value.massFlowKilogramsPerSecond())/Q;
                    if(ctx.columns()[e.from()]>=0)jacobian[row][ctx.columns()[e.from()]]
                        =-value.flowDifferentialPressureDerivative()*S2/(2*pFrom*Q);
                    if(ctx.columns()[e.to()]>=0)jacobian[row][ctx.columns()[e.to()]]
                        =value.flowDifferentialPressureDerivative()*S2/(2*pTo*Q);
                    jacobian[row][flowColumn]=1;
                }
            }catch(IllegalArgumentException ex){
                return new Evaluation(residual,jacobian,Double.POSITIVE_INFINITY,"UNSUPPORTED_EQUIPMENT_STATE");
            }
        }
        double merit=0;
        for(int i=0;i<size;i++){
            if(!Double.isFinite(residual[i]))return new Evaluation(residual,jacobian,Double.POSITIVE_INFINITY,"NONFINITE_RESIDUAL");
            double tol=i<unknown||i>=unknown+ctx.pipes().size()&&ctx.equipment().get(i-unknown-ctx.pipes().size()).compressor()==null
                    ?ctx.controls().massToleranceKilogramsPerSecond()/Q
                    :ctx.controls().momentumRelativeTolerance();
            merit=Math.max(merit,Math.abs(residual[i])/tol);
            for(double v:jacobian[i])if(!Double.isFinite(v))
                return new Evaluation(residual,jacobian,Double.POSITIVE_INFINITY,"NONFINITE_JACOBIAN");
        }
        return new Evaluation(residual,jacobian,merit,null);
    }

    private static void addFlow(double[] r,double[][] jac,int[] cols,int from,int to,int qColumn,double qScaled){
        if(cols[from]>=0){r[cols[from]]+=qScaled;jac[cols[from]][qColumn]+=1;}
        if(cols[to]>=0){r[cols[to]]-=qScaled;jac[cols[to]][qColumn]-=1;}
    }
    private static void verifyLimits(SyntheticEquipmentEdge e,double T,double inletPressure,double flow){
        for(var entry:e.limits().entrySet()){
            double v=switch(entry.getKey()){
                case INLET_PRESSURE_PASCALS_ABSOLUTE -> inletPressure;
                case INLET_TEMPERATURE_KELVIN -> T;
                case MASS_FLOW_KILOGRAMS_PER_SECOND -> flow;
                case ROTATIONAL_SPEED_REVOLUTIONS_PER_MINUTE -> e.equipment().configuredSpeedRevolutionsPerMinute().doubleValue();
                case OPENING_FRACTION -> e.equipment().configuredOpeningFraction().doubleValue();
            };
            if(!Double.isFinite(v)||v<entry.getValue().minimumInclusive().doubleValue()
                    ||v>entry.getValue().maximumInclusive().doubleValue())
                throw new IllegalArgumentException("Outside explicit synthetic equipment limits.");
        }
    }

    private static SimulationEquipmentGasSolution equipmentSolution(boolean converged,String status,int iterations,
            double[] state,SyntheticEquipmentContext ctx,SimulationEquipmentBehaviorEvaluator evaluator){
        double S=ctx.controls().pressureScalePascals(),Q=ctx.controls().flowScaleKilogramsPerSecond();
        var pressures=new HashMap<String,Double>();
        for(int i=0;i<ctx.ids().size();i++){
            double u=ctx.columns()[i]<0?ctx.fixed()[i]:state[ctx.columns()[i]];
            if(!(u>0)||!Double.isFinite(u)){
                var failed=new SteadyStateGasSolution(false,status,Map.of(),Map.of(),Map.of(),Map.of(),iterations,Map.of());
                return new SimulationEquipmentGasSolution(failed,Map.of(),Map.of(),Map.of(),Map.of());
            }
            pressures.put(ctx.ids().get(i),S*Math.sqrt(u));
        }
        var flows=new HashMap<String,Double>();var equipmentFlows=new HashMap<String,Double>();
        var mass=new HashMap<String,Double>();var pipeResiduals=new HashMap<String,Double>();
        var compResiduals=new HashMap<String,Double>();var valveResiduals=new HashMap<String,Double>();
        var power=new HashMap<String,Double>();
        double[] net=new double[ctx.ids().size()];
        for(int j=0;j<ctx.pipes().size();j++){
            var e=ctx.pipes().get(j);double q=state[ctx.unknownCount()+j]*Q;
            flows.put(e.id(),q);net[e.from()]+=q;net[e.to()]-=q;
            var g=frictionProductAndDerivative(q,e);
            if(g!=null){
                double from=pressures.get(ctx.ids().get(e.from())),to=pressures.get(ctx.ids().get(e.to()));
                pipeResiduals.put(e.id(),to*to-e.exponential()*from*from+e.lossFactor()*g[0]);
            }
        }
        for(int j=0;j<ctx.equipment().size();j++){
            var e=ctx.equipment().get(j);
            double q=state[ctx.unknownCount()+ctx.pipes().size()+j]*Q;
            equipmentFlows.put(e.id(),q);net[e.from()]+=q;net[e.to()]-=q;
            double pFrom=pressures.get(ctx.ids().get(e.from())),pTo=pressures.get(ctx.ids().get(e.to()));
            try{
                if(e.compressor()!=null){
                    var v=evaluator.compressor(e.compressor(),q,e.equipment().configuredSpeedRevolutionsPerMinute().doubleValue());
                    double rT=GAS_CONSTANT*ctx.gas().temperatureKelvin()/ctx.gas().molarMassKilogramsPerMole();
                    compResiduals.put(e.id(),Math.log(pTo/pFrom)-v.headJoulesPerKilogram()/rT);
                    power.put(e.id(),q*v.headJoulesPerKilogram()/v.efficiency());
                } else if(e.closed())valveResiduals.put(e.id(),q);
                else{
                    var v=evaluator.valve(e.valve(),e.method(),pFrom-pTo,e.equipment().configuredOpeningFraction().doubleValue());
                    valveResiduals.put(e.id(),q-v.massFlowKilogramsPerSecond());
                }
            }catch(IllegalArgumentException ex){/* Failure diagnostic stays nonconverged; no fabricated map power. */}
        }
        var exchange=new HashMap<String,Double>();
        for(int i=0;i<net.length;i++)if(ctx.columns()[i]<0)exchange.put(ctx.ids().get(i),net[i]);
        else mass.put(ctx.ids().get(i),net[i]-ctx.injections()[i]);
        var pipe=new SteadyStateGasSolution(converged,status,pressures,flows,mass,pipeResiduals,iterations,exchange);
        return new SimulationEquipmentGasSolution(pipe,equipmentFlows,compResiduals,valveResiduals,power);
    }

}
