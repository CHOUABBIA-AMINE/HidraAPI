# Simulation Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.simulation`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns simulation models/scenarios/runs, optimization candidates/changes and human-facing simulation recommendations.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [SimulationCandidateChange](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java)
- [SimulationModel](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java)
- [SimulationOptimizationCandidate](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java)
- [SimulationRecommendation](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java)
- [SimulationRun](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java)
- [SimulationScenario](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java)

Domain policies:

- [SimulationSafetyPolicy](../../src/main/java/dz/sh/hidra/modules/simulation/domain/policy/SimulationSafetyPolicy.java)

Domain services:

- [SimulationSafetyGuard](../../src/main/java/dz/sh/hidra/modules/simulation/domain/service/SimulationSafetyGuard.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [CreateSimulationModelUseCase](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java)
- [CreateSimulationScenarioUseCase](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationScenarioUseCase.java)
- [PublishSimulationRecommendationUseCase](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java)
- [QueueSimulationRunUseCase](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java)

Current application services:

- [SimulationApplicationService](../../src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java)

Current API/controller classes:

- [SimulationController](../../src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SimulationController.java)
- [SpringSimulationController](../../src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **25** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [SimulationCandidateChangeJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateChangeJpaEntity.java)
- [SimulationCandidateOperatingConditionJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateOperatingConditionJpaEntity.java)
- [SimulationCandidateScoreJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateScoreJpaEntity.java)
- [SimulationCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCatalogEntryJpaEntity.java)
- [SimulationCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCatalogTranslationJpaEntity.java)
- [SimulationConstraintEvaluationJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationConstraintEvaluationJpaEntity.java)
- [SimulationConstraintJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationConstraintJpaEntity.java)
- [SimulationEvidenceLinkJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationEvidenceLinkJpaEntity.java)
- [SimulationInputDatasetJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationInputDatasetJpaEntity.java)
- [SimulationInputSnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationInputSnapshotJpaEntity.java)
- [SimulationModelJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelJpaEntity.java)
- [SimulationModelVersionJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelVersionJpaEntity.java)
- [SimulationObjectiveJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationObjectiveJpaEntity.java)
- [SimulationOptimizationCandidateJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationOptimizationCandidateJpaEntity.java)
- [SimulationRecommendationJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRecommendationJpaEntity.java)
- [SimulationResultSeriesReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationResultSeriesReferenceJpaEntity.java)
- [SimulationResultSummaryJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationResultSummaryJpaEntity.java)
- [SimulationResultValueJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationResultValueJpaEntity.java)
- [SimulationRunJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunJpaEntity.java)
- [SimulationRunStepJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunStepJpaEntity.java)
- [SimulationScenarioAssumptionJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioAssumptionJpaEntity.java)
- [SimulationScenarioJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioJpaEntity.java)
- [SimulationSensitivityAnalysisJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationSensitivityAnalysisJpaEntity.java)
- [SimulationSolverTraceJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationSolverTraceJpaEntity.java)
- [SimulationValidationFindingJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationValidationFindingJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaSimulationCandidateChangeRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationCandidateChangeRepositoryAdapter.java)
- [JpaSimulationModelRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationModelRepositoryAdapter.java)
- [JpaSimulationOptimizationCandidateRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationOptimizationCandidateRepositoryAdapter.java)
- [JpaSimulationRecommendationRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRecommendationRepositoryAdapter.java)
- [JpaSimulationRunRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRunRepositoryAdapter.java)
- [JpaSimulationScenarioRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationScenarioRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

No source class is present in this category at the verified parent.

Imported scalar contracts supplied by collaborating owners:

- [SimulationRecommendationAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/simulation/SimulationRecommendationAuditContract.java)
- [RiskOwnedEvidenceLookup](../../src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java)
- [SimulationTopologyScopeContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java)
- [SimulationTopologyTargetContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyTargetContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [AssetAvailabilityLookupPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/AssetAvailabilityLookupPort.java)
- [AuditEventPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/AuditEventPort.java)
- [DocumentReferencePort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/DocumentReferencePort.java)
- [IntegrityConstraintLookupPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/IntegrityConstraintLookupPort.java)
- [MonitoringContextLookupPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/MonitoringContextLookupPort.java)
- [NotificationRequestPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/NotificationRequestPort.java)
- [PlanningSnapshotLookupPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/PlanningSnapshotLookupPort.java)
- [SimulationCandidateChangeRepositoryPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationCandidateChangeRepositoryPort.java)
- [SimulationModelRepositoryPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationModelRepositoryPort.java)
- [SimulationOptimizationCandidateRepositoryPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationOptimizationCandidateRepositoryPort.java)
- [SimulationRecommendationRepositoryPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRecommendationRepositoryPort.java)
- [SimulationResultStoragePort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationResultStoragePort.java)
- [SimulationRunRepositoryPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRunRepositoryPort.java)
- [SimulationScenarioRepositoryPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationScenarioRepositoryPort.java)
- [SimulationSolverPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationSolverPort.java)
- [TelemetryTrustedReadingSnapshotPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TelemetryTrustedReadingSnapshotPort.java)
- [TopologyChangeProposalPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java)
- [TopologySnapshotLookupPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologySnapshotLookupPort.java)
- [WorkflowStartPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/WorkflowStartPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Simulation owns model/scenario/run and advisory candidate/recommendation evidence. Completed runs cannot be rewritten; recommendation publication is audited. Source-visible solver availability metadata does not prove solver execution or field control.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| SimulationModel | [HMSR-009 reconciled rule](../domain/SEMANTIC_DECISIONS.md#simulation-simulationmodel) |
| SimulationScenario | [HMSR-038 reconciled rule](../domain/SEMANTIC_DECISIONS.md#simulation-simulationscenario) |
| SimulationRun | [HMSR-055 reconciled rule](../domain/SEMANTIC_DECISIONS.md#simulation-simulationrun) |
| SimulationOptimizationCandidate | [HMSR-066 reconciled rule](../domain/SEMANTIC_DECISIONS.md#simulation-simulationoptimizationcandidate) |
| SimulationCandidateChange | [HMSR-092 reconciled rule](../domain/SEMANTIC_DECISIONS.md#simulation-simulationcandidatechange) |
| SimulationRecommendation | [HMSR-093 reconciled rule](../domain/SEMANTIC_DECISIONS.md#simulation-simulationrecommendation) |


## Phase 2.5 governed equipment source revisions

Immutable equipment parameter source revisions retain exact stored network and qualified gas-fluid identities, full supplied compressor/valve maps, explicit limits and independent characteristic origin/time. Exact qualified reads reattest current owner and Workflow evidence through local ports. Internal append-only JDBC operations and canonical SHA-256 integrity add no public write endpoint, executing property algorithm, hydraulic solver, engine-ready claim, operational approval or OT action. C3D candidate acceptance and actual PostgreSQL/dictionary evidence remain pending.


## HPR-P25-008A — Internal Gas Engine Numerical Design (2026-10-10)

**Status:** Design specification submitted for exact-head documentation verification; no executing solver, gas-property implementation, numerical benchmark run or GZ4 field qualification is asserted. **Owner:** Simulation module. **Implementation sequence:** 008B steady pipes/network, 008C equipment, 008D transients, 008E independent verification, 008F execution integration; all remain separate pending gates. This is a *proposed mathematical design*, not a calibrated industrial model.

### 1. Scope, state and assumptions

Initial supported physics target is **single-phase, compressible, subsonic natural gas** in a one-dimensional, connected network. Design-first baseline: quasi-steady/isothermal pipes with a uniform specified or solved effective gas temperature on each pipe (008B); transient **isothermal** mass–momentum equations with a prescribed thermal field (008D). Treat heat-transfer, thermally coupled non-isothermal transients, liquids, H2/blends, multiphase flow, hydrates, shock discontinuities, rapid-actuation safety assessment and compressor station performance without qualified curves as **unsupported**, not approximated silently. An isothermal approximation must expose a validity-range assessment; thermal accuracy is not established by input availability. All property queries must carry explicit gas composition, EOS/method revision and a valid temperature/pressure range. No gas composition, reference temperature, base pressure or GZ4 pressure/flow profile is inferred from 2024 geometry or unqualified 2026 aggregates.

The solver uses SI quantities: absolute pressure p [Pa], temperature T [K], oriented mass flow m_dot [kg/s], axial length x [m], time t [s], elevation z [m], cross-section A [m²], hydraulic diameter D [m], absolute roughness epsilon [m], density rho [kg/m³], gravitational acceleration g [m/s²] and dynamic viscosity mu [Pa s]. Positive pipe flow follows the exact fromNodeId -> toNodeId orientation. Positive node mass injection adds mass. **Do not equate nominal diameter with internal hydraulic diameter:** physical input requires a qualified internal diameter or a clearly labeled synthetic diameter. The source's 48-inch nominal and 11.91–22.22 mm wall thickness range do not determine per-segment internal diameters; source friction/geometry acceptance remains separate.

### 2. Governing equations

**Steady pipes:** for the positive geometric x-direction, model continuity d(m_dot)/dx = 0 (unless explicitly modeled withdrawals) and momentum by the integrated or spatially discretized form

    dp/dx = -(f_D/(2 D)) rho u |u| - rho g dz/dx

where u = m_dot/(rho A). This is a **low-Mach frictional quasi-steady approximation**; acceleration/compressibility terms neglected here require declared validity. Use EOS closure rho = rho(p,T,composition,method). For a simple ideal-gas manufactured reference, rho = p M/(R_u T), where M is molar mass and R_u universal gas constant; ideal gas is *not* selected as an industrial GZ4 EOS. Darcy friction f_D is explicitly distinguished from Fanning friction, obtained from laminar 64/Re when Re < 2300; for Re > 4000 select a friction correlation (e.g. Colebrook-White via convergent subsolve or a verified explicit approximation) against Re = rho |u| D/mu and relative roughness epsilon/D. Treat transitional 2300–4000 as model-uncertain/rejected unless separately verified; do not insert an unapproved hard step. A zero-flow pipe must have no spurious friction pressure drop while elevation hydrostatics still apply. The sign of friction opposes m_dot even when it reverses.

At each internal node impose **sum of signed outgoing pipe mass rates = node injection**; check boundary-node sign and solve only unknown nodal pressures/pipe rates compatible with prescribed quantities. The residual includes EOS/friction/elevation consistency and node conservation; do not force every synthetic pressure into a Dirichlet boundary. If the network has no absolute-pressure datum, inconsistent multiple boundary constraints, missing branch connectivity, unsupported loop status or an unqualified GZ3 boundary, fail with an explanatory diagnostic. No fictional GZ3 compressor head is inferred from GZ4's statement of zero *own* compressor stations.

**Transient pipes (later 008D):** define the conservative isothermal 1D equations, with cross section A constant on each segment:

    d(rho A)/dt + d(rho u A)/dx = 0
    d(rho u A)/dt + d[(rho u^2 + p) A]/dx =
        - A (f_D rho u |u|/(2D) + rho g dz/dx)

Close with the same declared rho(p,T,composition,EOS) and sound speed a² = (dp/d rho)_T for a thermodynamically consistent isothermal closure; do not substitute adiabatic acoustic speed without matching assumptions. These equations omit pipe-wall compliance, energy balance and phase change. Junction conditions enforce common junction pressure (within declared junction-loss law) and net mass conservation; any detailed junction/equipment thermodynamics is a later independently verified model.

### 3. Source-contract binding and architecture

Use the existing immutable contracts without asserting that they execute mathematics: SimulationPhysicalNetworkInput(nodes, pipeSegments) gives oriented graph plus positive length, internal diameter and absolute roughness; SimulationNetworkNodeInput exposes elevationMeters. SimulationConnectedNetworkInput and SimulationEquipmentModelInput give scope, topology/equipment identities and version binding. SimulationGasFluidInput provides a complete mole-fraction composition declaration plus propertyMethodReference, method revision and evidence; declarations are not an executable EOS. SimulationInitialStateInput / SimulationStateQuantityInput provide absolute pressure, temperature, pipe mass flow, node injection, RPM and valve fraction with explicit MEASURED / ESTIMATED / SYNTHETIC / UNKNOWN knowledge and evidence times. SimulationBoundaryScheduleInput/SimulationBoundarySeriesInput define STEP_PREVIOUS and LINEAR **declarations** with strictly ordered knots and exact horizon anchors; they do not evaluate interpolation. SimulationInputManifest and SimulationPhysicalInputPayload enforce some identity/scope/schema-one coherence, but not complete digest authenticity, hydraulic well-posedness, EOS availability or operational fitness.

For 008B, map each pipe to end nodes, elevation difference, D, epsilon, thermodynamic property reference and oriented mass flux; map each node to unknown p or approved pressure/mass-injection boundary and optional temperature. Boundary choice must be determined by a solver-side well-posedness check, not automatically by the number of reported state values. For 008D, map initial distributed cell rho and mass-flux to a consistent PDE state; a list of independent node pressures cannot by itself define all cell states. Boundaries and events are advisory simulation inputs only; never emit SCADA/PLC/RTU/SIS/ESD or direct OT commands.

Exact source identities and effective times must be checked, including network/topology, fluid, equipment, OPERATING_STATE and BOUNDARY_SCHEDULE revisions, and origin classifications; no cross-module persistence or controller dependency is introduced into the numerical package. Simulation is the business owner. C3E unqualified external telemetry must not be silently promoted; C3F/002D canonical capture and verified reproducibility remain pending.

### 4. Numerical methods and failure semantics

**008B candidate:** represent connected pipes by nonlinear pressure–mass-flow residual functions and enforce node balance with an oriented incidence matrix. Evaluate each pipe's pressure-drop law via bounded quadrature/discretization or a verified closed form only within its assumptions. Use damped Newton / trust-region or another convergence-controlled nonlinear method with sparse linear algebra; declare deterministic pivot ordering, variable scaling, iteration limits, positive-pressure admissibility and explicit stopping reasons. Mixed boundary types must be checked for uniqueness and consistency before solution. Report mass-balance residual at every node, pipe momentum residual, norm/scaling, iteration trace, EOS validity, pressure positivity, flow reversals, and solver-specific error categories. A plausible output without convergence and conservation evidence is a failure.

**008D candidate:** choose a conservative finite-volume formulation with fluxes consistent with the isothermal EOS; resolve junction coupling and friction/elevation source terms to a well-balanced target verified on hydrostatic resting cases. Use an appropriate monotone Riemann/numerical flux and time integration with reproducible CFL checks using |u|+a and local cell lengths. Control stiff friction, event discontinuities and boundary interpolation explicitly (step events at exact knots); reject insufficient resolution or invalid state rather than produce silent extrapolation. Track network-integrated mass at every step, boundary flux, source contributions, minimum p/rho and normalized conservation drift. A method name, nominal CFL number or order of accuracy is not an acceptance result; derive thresholds from method stability analysis and independently run refinement tests.

**Design acceptance metrics (not approved field tolerances):** absolute and scaled node mass residuals, maximum normalized pipe momentum residual, conservation closure integral, nonlinear iteration convergence rate/failure code, spatial/time self-convergence, manufactured-solution error, EOS/reference discrepancy and deterministic replay equivalence. Numerical tolerances must be parameterized and traceable to benchmark conditioning and machine precision; **no universal fixed tolerances, fixed operating limits, or real-GZ4 validation thresholds are claimed**. Refuse NaN/infinite values, negative p/T/rho, missing property coefficients, invalid composition reference, unsupported Re regime, unconstrained pressure datum, inconsistent boundaries, invalid event timeline and loss of solver precision. A failure result retains diagnostics and must not masquerade as a successful physical recommendation.

### 5. Independent reference and reproducible synthetic matrix

Before 008B implementation, establish a **new conservation-consistent numerical oracle** independent of the existing GZ4 960 synthetic state rows. Required baseline comparisons: single straight horizontal ideal-gas pipe with analytically integrated simplified friction; stationary hydrostatic inclined pipe; zero-flow equal-elevation pipe; reversed-flow pipe; simple two-branch tree and loop junction mass conservation; distinct prescribed-pressure/mass-injection boundary choices; and no-reference/overconstraint/negative-pressure/unsupported-fluid fail cases. Independently calculate expected mass balances and pressure trends using clearly specified reference parameter revisions. For 008D add manufactured isothermal transport waves, steady-state-as-transient limit, linepack mass balance, step-boundary event, hydrostatic equilibrium, and at least three successive spatial/time refinements with observed error behavior. Report reference method, method independence, hardware/precision, error measures and fixture revision for every case.

**External evaluation, not adopted dependencies:** (a) NeqSim as Java-compatible candidate thermodynamic/property reference, (b) pandapipes as optional independent steady network comparison, and (c) DWSIM as supplementary thermodynamic/process benchmark. Confirm exact version, license, EOS/properties, supported models, input matching and environment before running comparisons; mismatched assumptions cannot prove solver accuracy. None is currently integrated or run; external tools do not replace Hidra's own engine.

**GZ4 evidence split:** 2024 technical sheet offers mainline 513.172 km, branch 120.37 km, 48-inch nominal diameter and stated roughness 0.015 mm as *unqualified/static reference*; GZ3 interconnection boundaries and internal hydraulic diameter remain unresolved. Six daily operation aggregates have uncertain reported volumetric units and are not per-pipe mass-flow boundary truth. The 9-node / 8-segment / 960-state GZ4-inspired package is **DEVELOPMENT_TEST_ONLY**, not hydraulically mass balanced and not accepted as a solver oracle. A separate small ideal-gas fixture with synthetic fully specified EOS/reference conditions may be used to exercise mathematics while retaining SYNTHETIC provenance; those conditions are **test definitions**, not approvals for GZ4 field conversions.

### 6. Rejected shortcuts, acceptance and deferred decisions

Do not use: constant unversioned compressibility or assumed standard cubic meter conversion as industrial truth; node-by-node hardcoded pressure interpolation as a solver; irreversible dependence of core numerical code on external Python/process engines; a steady solution advertised as dynamic transients; synthetic historical sums as real SCADA traces; coupling to automatic OT actuation; or transfer of a gas-only validity claim to oil/H2/blends. The above steady/transient formulations are **design selections requiring numerical verification**, not measured performance assertions. The choice of actual sparse library, friction-transition treatment, EOS implementation and transient flux remains an 008B/008D preflight and verification decision, not a hidden implementation authorization here.

**008A design acceptance:** this specification is the sole 008A numerical design deliverable; review mathematical consistency, source-contract binding, source provenance, negative cases, and later independent benchmark obligations; apply exact-head documentation CI. Implementation 008B is a separately owner-selected next task and requires its own preflight. No numerical source or tests were created for 008A.


## HPR-P25-008B — Synthetic Connected-Network Acceptance Remediation (2026-10-10)

The internal `SteadyStateGasSolver.solveSyntheticIdealGasNetwork` adds a bounded
synthetic ideal-gas damped Newton reference for connected pipes, including meshes
and parallel links. Each node supplies exactly one pressure or injection boundary;
pressure-boundary mass exchanges are computed outputs. Temperature, molar mass,
viscosity, initial unknown pressures, initial pipe flows, numerical scales/tolerances
and iteration/line-search limits are supplied explicitly through nested immutable
records. It calculates no field property method and consumes no telemetry or GZ4
fixture import. The existing tree method and seven-argument solution constructor
remain source-compatible.

Unknowns are oriented pipe flows and pressure-squared values at injection nodes.
Node conservation and integrated isothermal Darcy/elevation relations form a square
system. The implementation uses an analytical flow-dependent friction Jacobian, the
continuous zero-flow laminar derivative, scaled partial-pivot linear solves and
positivity-preserving residual-reducing line search. Unsupported transitional Reynolds,
nonphysical pressure, singular/ill-conditioned Jacobian, failed line search and iteration
limit do not report convergence. Results contain actual iteration count, immutable
finite pressure/flow maps, injection-node mass residuals, pipe momentum residuals and
computed pressure-boundary exchanges. Pressure boundaries are not independently
constrained to zero injection. No iteration-history trace or sparse large-network
performance validation is claimed.

Independent tests use the separately derived compressible Poiseuille relation
`p_i^2-p_j^2=256*mu*L*q/(pi*c*D^4)`, high-precision reference constants and the
zero-flow hydrostatic exponential. They verify forward/reverse pressure and flow,
flowing elevation, an actual branch, triangle loop, parallel orientation, multiple
pressure boundaries with an interior injection, global mass balance, distinct initial
guesses/tighter tolerances, deterministic graph replay, independently bracketed
turbulent flow and central-perturbation derivative agreement. Controlled low-budget,
conditioning, unsupported and nonphysical cases test failure reporting. Numerical
values and tolerances are development references, not GZ4 operating limits.

The model remains the declared uniform-temperature ideal-gas, low-inertia synthetic
reference with laminar/Swamee-Jain Darcy friction. General EOS/property qualification,
equipment laws, transients, field calibration, production state qualification and run
integration are separate gates. C3E remains blocked. 008B numerical acceptance remains
pending exact-head full Java CI; no operational or calibrated twin claim follows from
the local benchmark pass.


## HPR-P25-008C — Synthetic Equipment Hydraulic Candidate (2026-10-10)

**Status:** bounded compressor/valve source candidate under independently authorized HPR-P25-008C; production Java 21 CI and numerical acceptance pending. NOT a completed equipment task, not a field-qualified simulator.

A Simulation-owned pure Java candidate provides explicit method-tagged synthetic interpolation of immutable compressor speed maps and valve opening/differential-pressure tables, including analytical partial derivatives and closed valve zero flow. Supported compressor head is reversible **isothermal specific work**, so `log(p_out/p_in)=H/(R*T/M)` and the separate synthetic shaft power is `q*H/eta`. This is not adiabatic/polytropic behavior. Valve V1 is a supplied synthetic table `q=F(dp,opening)`, not Cv/Kv/IEC valve sizing or choking. No method is selected by arbitrary text.

`SimulationSyntheticEquipmentNetworkInput` keeps one explicitly revision-bound, structurally connected union of real pipe and equipment links without creating fake pipe IDs or weakening existing physical-input validation. A new separate `solveSyntheticIdealGasEquipmentNetwork` method on `SteadyStateGasSolver` accepts explicit synthetic gas properties, per-node pressure or injection boundaries, numerical controls, actual pipe/equipment guesses, configured compressor speed and valve opening. It uses one deterministic incidence balance per unknown-injection node and pressure-squared unknowns; normal pipes retain the accepted friction/elevation mathematics. Closed links are excluded from active hydraulic anchoring while remaining zero-flow identities in the result; unanchored islands reject. `SimulationEquipmentGasSolution` retains actual pipe-only maps plus distinct equipment flows, compressor log pressure residuals and shaft power, and valve mass-flow residuals.

**Declared refusal:** unknown or non-SYNTHETIC reference methods, missing or mismatched source bindings/times, invalid/extra device IDs, extrapolated or nonrectangular maps, mismatched T or compressor inlet reference pressure, unsupported zero/reverse compressor/active valve operation, out-of-map flow/differential pressure, unspecified synthetic equipment limits, invalid trial pressure, unanchored closed components and solver nonconvergence. Map interpretation, synthetic physical values, all numerical tolerances, pressure/flow domains and stability remain development-only. Real GZ4 operational source qualification, actual equipment curves and field calibration are NOT established.

**Tests added:** independent bilinear interpolation and derivatives, named-method rejection, synthetic union/binding validation, compressor/valve bridge pressure and power, equipment-only union, real pipe plus device flow separation, closed-isolation anchoring, and invalid-state rejection. Numerical acceptance must still verify full source-derived ten-file scope and reference matrix through actual Java 21 CI; no test pass is claimed before observation. Regulator behavior cannot be silently aliased to valve and remains a separate unresolved topology/source-schema obligation; 008C therefore remains PENDING even if this bounded candidate's CI passes.
