# Topology and Telemetry Semantics

## Status

CURRENT focused semantic baseline.

## Topology Ownership

Topology owns graph/transport identity represented by `PipelineSystem`, `Pipeline`, `Facility`, `Equipment` and `TopologyConnection`.

Current invariants include:

- pipeline-system id/code/type/status are required;
- a pipeline requires its pipeline-system reference, code, type and status;
- topology connections require distinct from/to node identifiers, connection type, flow direction and status;
- self-loop topology connections are rejected;
- topology's boundary policy treats telemetry, assets, integrity, HSE and custody persistence prefixes as foreign rather than topology-owned state.

Topology references exported to other modules are references/contracts, not transferred aggregate ownership.

## Telemetry Acquisition and Point Semantics

`TelemetrySource` represents acquisition-source metadata. The source type/protocol are references rather than a hard-coded protocol implementation. Current source documentation names possible source categories such as SCADA, historian, OPC server, API feed, manual import source or edge gateway, but canonicalization does not claim that any particular industrial transport protocol is implemented.

Only an ACTIVE telemetry source is `ingestionEligible()`. Source endpoint/external-reference strings are rejected when they embed recognized secret material.

`TelemetryPoint` is the canonical Hidra point/tag. It requires device identity, code, French name, point type, signal type and lifecycle status; unit, aggregation, sampling and operating-range metadata are carried where available.

## Raw Reading Semantics

`TelemetryReading` is the raw received reading. Current value-shape policy is explicit:

- at most one of numeric, text or boolean value may be present;
- normal/non-rejected readings require exactly one value;
- REJECTED or QUARANTINED readings may retain zero typed values as evidence;
- point id, quality code, source timestamp, received timestamp and reading state are required.

A raw reading must not be described as trusted merely because it was stored.

## Trusted Reading Semantics

`TrustedTelemetryReading` is the downstream trusted-reading contract. It carries:

- the original reading/point identifiers;
- typed reading value;
- unit and quality code;
- `TrustLevel`;
- source timestamp and `trustedAt`;
- required quality-assessment identity;
- optional topology asset/snapshot and ingestion-batch provenance.

This model establishes a semantic distinction between raw acquisition evidence and downstream trusted data.

## Cross-Domain Boundary

Current architecture exports Telemetry point/trusted-reading evidence to Monitoring and Planning, plus Topology typed-reference capability to its documented consumers. Consumers must use those deliberate contracts/references and must not import telemetry/topology private aggregates.

## Not Established

HPR-P2-003 does not establish:

- an OT network topology;
- a specific SCADA/PLC/RTU ingestion protocol;
- TimescaleDB;
- PostGIS;
- trust promotion beyond the explicit evidence gate described below; assessment thresholds or cleansing algorithms are not invented.

## Catalog identity and owned references

System, pipeline, connection and equipment types are extensible catalog identities,
not closed enum taxonomies. Same-module parent/attachment integrity is owned by
Topology; optional Party manufacturer/owner references use the Party contracts.
Fresh references and unchanged historical snapshots have their specific rules in
[Topology decisions](SEMANTIC_DECISIONS.md#topology-decisions) and
[the Topology module](../modules/topology.md#cross-module-boundary).

## The implemented trust gate

[TrustedTelemetryReadingApplicationService](../../src/main/java/dz/sh/hidra/modules/telemetry/application/service/TrustedTelemetryReadingApplicationService.java)
requires the raw reading, point and assessment identities to agree. Assessment must
be PASSED and [TelemetryTrustPolicy](../../src/main/java/dz/sh/hidra/modules/telemetry/domain/policy/TelemetryTrustPolicy.java)
accepts only MEDIUM, HIGH or CERTIFIED; the service never upgrades assessment trust.
The point is ACTIVE, quality family is eligible, and populated optional unit/batch
provenance exists. Values/unit/quality/time come from evidence rather than caller input.
An unbound point remains possible; a single applicable binding may be selected,
while multiple bindings require explicit selection. Historical trusted snapshots
remain evidence after point retirement/rebinding rather than being refreshed.
See [Telemetry decisions](SEMANTIC_DECISIONS.md#telemetry-decisions).

## Expected versus observed evidence

Planning owns [PlanTarget](SEMANTIC_DECISIONS.md#planning-plantarget), not Telemetry.
Its exact TARGET_TYPE and empty-until-approved NUMERIC/TEXT policy determine target
shape without guessing quantity meaning or exclusivity. Monitoring resolves its
mandatory Planning target through the scalar export. Optional point, trusted reading
and evaluation contexts must be coherent where populated; reference checks do not
turn optional fields into mandatory links. Unchanged historical evidence/snapshots
remain preserved. See [PlanActualDeviation](SEMANTIC_DECISIONS.md#monitoring-planactualdeviation)
and [Planning](../modules/planning.md#cross-module-boundary).
