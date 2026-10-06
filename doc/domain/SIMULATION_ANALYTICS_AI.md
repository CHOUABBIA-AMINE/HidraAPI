# Simulation, Analytics and AI Semantics

## Status

CURRENT simulation/analytics semantics with AI explicitly NOT ESTABLISHED as an autonomous runtime capability.

## Simulation

Simulation owns `SimulationModel`, `SimulationScenario`, `SimulationRun`, optimization/candidate/change records and `SimulationRecommendation`.

A simulation recommendation is explicitly human-facing and is derived from a run or candidate. The simulation domain safety boundary is strict:

- operations containing SCADA/PLC/RTU/SIS/ESD or direct valve/pump/compressor start/stop actuation patterns are forbidden;
- simulation may not write directly to foreign operational module tables;
- `SimulationSafetyGuard` enforces these restrictions.

The application exposes a `SimulationSolverPort` whose current contract only reports solver availability by reference. Repository search at the HPR-P2-003 baseline found no implementation of that port. Therefore an executing external solver integration is **NOT ESTABLISHED** by current source.

## Analytics

Analytics owns curated datasets/versions, metric/projection executions, metric values, analytical insights and digital-twin-readiness assessments.

Current boundary semantics are explicit:

- analytics must not mutate operational source-of-truth state;
- forbidden mutations include closing incidents, acknowledging alarms, validating telemetry, changing topology, creating custody tickets, executing work orders, approving business decisions or field-system actuation;
- analytics outbound boundaries must use read models/snapshots rather than foreign aggregates or JPA entities;
- `AnalyticsInsight.advisoryOnly()` returns true;
- successful `AnalyticsProjectionRun` records require a source watermark;
- failed projection/evaluation runs retain diagnostic/correlation evidence according to their current model rules.

## Analytics Model Metadata

The current repository contains persistence structures for:

- analytics model metadata;
- model versions;
- model runs;
- input/output dataset-version references;
- run type/status, scope, period, timestamps and diagnostics.

This proves model catalog/run metadata exists. It does **not** by itself prove machine-learning inference, autonomous decision execution or an AI-agent runtime.

## Digital Twin

`DigitalTwinReadinessAssessment` evaluates readiness factors including telemetry completeness/quality, topology completeness, model availability and lineage completeness.

Its current domain method `runtimeDigitalTwin()` returns false. Therefore the canonical term is **digital-twin readiness assessment**, not an implemented runtime digital twin.

## AI / Autonomous Intelligence

**NOT ESTABLISHED / DEFERRED**

At the HPR-P2-003 baseline:

- there is no current `agents` source module root;
- repository search did not establish a model-inference execution component;
- analytics insights are advisory;
- simulation forbids field actuation;
- digital-twin readiness explicitly is not runtime digital twin behavior.

Accordingly, documentation must not claim autonomous AI, agentic control, predictive actuation or closed-loop field control. Future AI capabilities require explicit approved requirements, architecture, implementation, tests, evidence and safety/governance controls before becoming CURRENT.
