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
}
