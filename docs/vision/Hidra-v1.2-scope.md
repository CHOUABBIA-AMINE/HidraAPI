# Hidra v1.2 Scope – Internal Network Simulator

## 1. Document Status

| Field | Value |
|---|---|
| Product | Hidra |
| Document type | Scope definition |
| Version | 1.2 Draft |
| Status | For discussion |
| Target repository | HidraAPI |

---

## 2. Purpose of v1.2

Hidra v1.2 extends the platform from operational intelligence and integrity monitoring to **native network simulation–driven hydrocarbon optimization**.

v1.2 introduces explicit support for:

- designing and implementing an **internal hydraulic / network simulation engine** dedicated to Sonatrach pipeline operations (Hidra Simulation Core),
- running steady-state (first) and, later, dynamic simulations for Sonatrach pipeline networks,
- embedding simulation results into planning, monitoring, integrity, and risk decisions,
- enabling "what-if" and look-ahead scenarios directly from Hidra,
- benchmarking Hidra Simulation Core against leading commercial tools to validate physics, performance, and usability.[web:219][web:220][web:221][web:222][web:223][web:224][web:225][web:226][web:227][web:228][web:231]

Hidra remains the **system-of-intelligence** and **system-of-coordination**.  
With v1.2, it also becomes the **system-of-calculation** for Sonatrach’s pipeline network, using external simulators only for **validation, cross-checking, and special cases**, not as primary engines.

---

## 3. Design Principles for Hidra Simulation Core

1. **Hidra-Owned Physics and Algorithms**  
   - Implement Sonatrach-focused hydraulic and thermodynamic models inside Hidra Simulation Core.  
   - Start with steady-state single/multiphase hydraulics for pipeline networks, extend to dynamic effects when justified.[web:219][web:220][web:221][web:224][web:225][web:226][web:227][web:230]

2. **Topology-Driven Simulation**  
   - Use Hidra topology models as the canonical network description (pipelines, segments, stations, equipment, elevation).  
   - Avoid duplicating topology in separate “simulator schematics”; derive simulation graphs from topology.

3. **Scenario-First**  
   - Model simulations as **Scenarios** with clear business objectives (capacity, debottlenecking, safety margin, integrity check), inputs and constraints.  
   - Ensure every scenario is auditable and reproducible, independent of UI and storage details.

4. **Explainable and Audited Simulations**  
   - Every simulation run must record: who ran it, when, for which network, with which fluid model and boundary conditions, and which version of the solver.  
   - For each key result (pressure limit violation, capacity shortfall, surge risk), provide traceable reasoning and links back to inputs.

5. **Safe and Bounded Complexity**  
   - Focus the first iterations on **operationally relevant** models, not on exhaustively reproducing every feature of commercial tools.  
   - Use commercial tools as reference cases and acceptance tests, not as runtime dependencies.[web:219][web:220][web:221][web:222][web:223][web:224][web:225][web:226][web:227][web:228][web:231]

6. **Non-Blocking, Asynchronous Runs**  
   - Treat simulations as asynchronous jobs with status, progress, cancellation, retry, and robust error reporting.

7. **Separation of Concerns**  
   - Simulation Core does physics and network solving.  
   - Existing contexts (planning, monitoring, integrity, incidents, analytics) consume results, apply business rules, and drive workflows.

8. **Validation Against Leader Tools**  
   - Define standard validation suites (benchmark networks, fluids, scenarios) and compare Hidra results with leader tools to ensure acceptable accuracy and robustness.[web:219][web:220][web:221][web:222][web:223][web:224][web:225][web:226][web:227][web:228][web:231]

---

## 4. External Tools – Benchmarks, Not Engines

Commercial tools remain relevant as **benchmarks and validation references**, not as runtime dependencies for v1.2:

- Aspen HYSYS / Aspen Hydraulics (pipeline hydraulics, process coupling).[web:219][web:225][web:231]  
- Schlumberger PIPESIM (steady-state multiphase networks).[web:220][web:226]  
- OLGA (dynamic multiphase transients).[web:221][web:227][web:226]  
- Honeywell UniSim Design (process and some pipeline dynamics).[web:222][web:228]  
- AVEVA Process Simulation / PIPEPHASE heritage.[web:223]  
- DNV Synergi Gas / Pipeline Simulator (steady-state + transient networks, time-varying).[web:224][web:230]

For v1.2:

- Hidra MAY import or export data in formats that simplify cross-checking with these tools.  
- Hidra does NOT depend on them operationally; all production-grade calculations are done by Hidra Simulation Core.

---

## 5. Hidra v1.2 Scope – Internal Simulation and Co-Simulation

Hidra v1.2 extends the original vision and macro/micro architecture by adding a **Simulation & Optimization** dimension tightly integrated with existing contexts (topology, telemetry, planning, monitoring, integrity, incidents, analytics, integration).

### 5.1 New Simulation-Oriented Goals

1. Allow engineers and planners to run **“what-if” scenarios** on Sonatrach networks directly from Hidra:  
   - maximum capacity,  
   - feasible pressure envelopes,  
   - effect of equipment outages,  
   - new connection impacts.

2. Use Hidra Simulation Core for **design and capacity analysis** and for **look-ahead operations** (short-term hydraulic forecasts).

3. Enable **integrity and safety studies**:  
   - compute operating envelopes,  
   - identify segments at risk of overpressure, slack flow, or unstable regimes,  
   - support leak scenario analysis when combined with integrity signals.

4. Integrate simulation results into **planning, monitoring, integrity, risk, and incident workflows**.

5. Provide a governance and audit layer over simulation-driven decisions.

### 5.2 Conceptual Simulation Bounded Context

v1.2 introduces a conceptual **Simulation** bounded context (implemented across a dedicated simulation module plus analytics + integration):

- **Purpose**:  
  - own scenario and run lifecycle,  
  - own solvers and physics models,  
  - map results to topology and time,  
  - expose clean APIs to other contexts.

Key domain concepts:

- `SimulationScenario` – purpose, scope, time horizon, selected models, inputs, constraints.  
- `ScenarioInput` – topology subset, boundary conditions, fluid description, equipment states, operating rules.  
- `ScenarioRun` – single run instance (solver version, status, durations, logs).  
- `ScenarioResult` – normalized outputs (pressures, flows, temperatures, mass balances, constraint checks) linked back to topology elements.  
- `SimulationModelVersion` – identifies solver code and physics model variants used.  
- `Recommendation` – human-readable proposal derived from results (capacity upgrades, new operating envelopes, risk mitigations).

Interactions:

- **Topology** – provides canonical network graph, equipment characteristics, elevation profiles.  
- **Telemetry** – provides current operating point snapshots for initialization and calibration.  
- **Planning** – consumes results to check feasibility of future plans and to size projects.  
- **Monitoring** – uses look-ahead simulation to anticipate where alarms and risks may appear.  
- **Integrity** – uses simulation to understand consequences of defects, failures, or leak scenarios.  
- **Incidents** – logs simulations run during incident analysis and post-mortem.  
- **Analytics** – aggregates scenarios and runs for trend analysis, solver validation, and optimization.

---

## 6. How v1.2 Aligns with Existing Hidra Vision & Architecture

### 6.1 Vision Alignment

Hidra already aims at:

- trusted topology and telemetry,  
- planning and monitoring,  
- risk, integrity, and incidents,  
- audit and analytics.

v1.2 evolves the "Intelligence" horizon into:

- **simulation-driven intelligence**: design, operations, and integrity insights generated by Hidra’s own solvers,  
- **benchmark-driven confidence**: systematically validated against market leaders but independent of them.

### 6.2 Macro Architecture Alignment

The modular monolith and bounded contexts remain valid.

Changes:

- A **Simulation** capability appears alongside Analytics and Integration:  
  - Simulation Core code sits in its own module,  
  - Analytics stores scenarios and results,  
  - Integration optionally handles import/export for validation with external tools.  
- Dependency direction:  
  - Simulation depends on topology and telemetry (read side) and on platform for persistence,  
  - Planning/Monitoring/Integrity depend on Simulation’s APIs (not on solver internals).

### 6.3 Micro Architecture Alignment

In `dz.sonatrach.hidra.modules`:

```text
modules
├── simulation
│   ├── api
│   ├── application
│   ├── domain
│   └── infrastructure
├── analytics
└── integration
```

- `simulation.domain` contains:  
  - solver-independent domain model (Scenario, Run, Result, ModelVersion),  
  - physics model interfaces and implementations (network solvers, fluids, correlations).  
- `simulation.application` contains:  
  - `ScenarioPlannerService`, `SimulationOrchestratorService`, `ResultMappingService`.  
- `simulation.infrastructure` contains numerical and algorithmic details (matrix solvers, time-stepping, correlations).

Integration module optionally adds **adapters for validation purposes only** (e.g., export a scenario to commercial tools for comparison) but does not own solver logic.

---

## 7. Concrete v1.2 Scope Items (Internal Simulator)

### 7.1 Minimal Viable Simulation Core

1. **Network Model & Solver (Steady-State)**  
   - represent pipelines, segments, nodes, equipment, elevation, and boundary conditions,  
   - solve for pressure and flow in gas or liquid networks for realistic Sonatrach cases.

2. **Scenario Management**  
   - CRUD for `SimulationScenario` tied to topology subsets and objectives,  
   - configuration of boundary conditions and key assumptions.

3. **Scenario Execution**  
   - asynchronous runs with status tracking, logs, and error handling,  
   - persistence of `ScenarioRun` and `ScenarioResult`.

4. **Result Visualization & Comparison**  
   - overlay simulation pressures/flows on topology,  
   - compare simulated vs actual telemetry for calibration.

5. **Audit & Governance**  
   - link runs to users, decisions, and workflows,  
   - enforce that simulation-based recommendations follow approval workflows.

### 7.2 Extended Features (Post v1.2)

1. **Multiphase and Thermal Effects**  
   - multiphase flow models where needed,  
   - temperature and heat transfer if justified.

2. **Time-Varying / Quasi-Dynamic Simulation**  
   - sequences of steady states to approximate time-varying behavior.

3. **Transient Solver (Later)**  
   - truly dynamic hydraulic solver for critical segments (where ROI is clear).

4. **Advanced Optimization**  
   - search for optimal operating envelopes, bottleneck identification, and debottlenecking proposals.

---

## 8. Out of Scope for v1.2

To keep v1.2 focused, the following remain out of scope:

- building a full-featured process simulator (entire processing plants) – focus on pipelines and connected equipment first,  
- replicating every feature of commercial tools (GUI, training simulators, all fluid packages),  
- taking direct automated control actions on the real pipeline solely based on simulation output,  
- replacing all engineering workflows and tools in one step.

---

## 9. Open Questions for Sonatrach Stakeholders

1. What are the priority network types and operating regimes for the first Hidra Simulation Core (high-pressure gas, crude oil, multiproduct, multiphase segments)?  
2. What accuracy and performance requirements must the internal solver meet to be accepted for operations and integrity studies (tolerance, runtime, case sizes)?  
3. Which commercial tools and scenarios should be used as reference benchmarks for calibrating and validating Hidra Simulation Core?  
4. Where should we limit scope in v1.2: only steady-state hydraulics, or also simple time-varying sequences?  
5. How will responsibilities be split between operations, flow assurance, process engineering, and integrity teams for owning scenarios and validating models?
