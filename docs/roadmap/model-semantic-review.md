# HidraAPI Target Model Semantic Review Roadmap

**Status:** Active — HMS-001 completed; dependency analysis not yet started.

**Repository:** `CHOUABBIA-AMINE/HidraAPI`  
**Roadmap:** `docs/roadmap/model-semantic-review.md`  
**Roadmap code:** `HMS`  
**Created:** 2026-10-02  
**Live baseline at creation:** `5e289aa31a5d622e50176d69fd858c3ea0b4aa34`  
**Catalogue source:** `docs/roadmap/model-field-catalogue.md`  
**Pinned catalogue model source:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Catalogue scope:** 24 modules, 123 direct domain models, 1,867 declared fields/components  
**Execution mode:** exactly one roadmap task/commit at a time; never continue automatically.

## 1. Purpose

Establish an authoritative semantic baseline for the 123 catalogued HidraAPI domain models before legacy-source classification, source-to-target mapping, or provisioning decisions continue.

The review sequence is dependency-driven:

```text
models with no confirmed model dependencies
    ↓
models whose confirmed dependencies have already been reviewed
    ↓
progressively deeper dependent models
    ↓
terminal/consumer models that are not prerequisites of other models
```

Within the same dependency level, models with more confirmed downstream dependents are reviewed first. Cyclic dependencies are never forced into an artificial linear order; strongly connected components are explicitly grouped and resolved.

## 2. Why this roadmap exists

HMC-001 through HMC-028 produced and validated a model/field/type catalogue, but that catalogue intentionally records Java declarations only. It does not establish:

- semantic meaning of `...Id` fields;
- persistence foreign keys;
- cross-module ownership;
- mandatory/optional relationship meaning;
- snapshot/reference-only semantics;
- lifecycle rules;
- multilingual modeling correctness;
- whether a model should be retained, revised, deferred, or removed.

HDP-004 and HDP-005 therefore remain paused until this target-model semantic review produces an approved baseline.

## 3. Authoritative evidence

Dependency and semantic decisions must use current/live repository evidence where available:

1. verified HMC catalogue and its pinned model source;
2. current domain model source;
3. JPA mappings/entities;
4. current Flyway migrations and foreign keys;
5. application ports/contracts;
6. enums/value types;
7. module roadmaps and architectural boundaries.

A Java field type alone is insufficient. For example, a `String organizationUnitId` may represent a real dependency even though the declared Java type is only `String`.

Do not infer a dependency merely from similar names. Every edge must have evidence and a classification.

## 4. Dependency edge classifications

Every confirmed model-to-model edge must be classified as one of:

| Classification | Meaning |
|---|---|
| Persistence dependency | Backed by target persistence FK/constraint or equivalent owned persistence relation. |
| Domain reference | Explicit domain relationship not necessarily represented by a database FK. |
| Cross-module reference | Stable reference into another bounded context. |
| Optional reference | Semantically optional dependency. |
| Snapshot/reference-only | Snapshot/code/name field that must not be treated as an owning dependency. |
| Value/catalog dependency | Reference to controlled value/catalog semantics rather than aggregate ownership. |
| Unresolved | Evidence is insufficient or contradictory; interactive decision required. |

Snapshot fields such as `...NameSnapshot` or `...CodeSnapshot` are not dependencies unless repository evidence establishes otherwise.

## 5. Graph and ordering rules

For each of the 123 models record:

- module;
- model;
- confirmed upstream dependencies;
- dependency evidence;
- dependency classification;
- confirmed downstream dependents;
- inbound dependent count;
- outbound dependency count;
- dependency depth;
- strongly connected component, if any;
- review level;
- review readiness;
- unresolved edges.

Ordering algorithm:

1. validate all dependency edges first;
2. collapse strongly connected components for ordering;
3. topologically order the resulting graph;
4. Level 0 contains models with no confirmed upstream model dependency;
5. Level N contains models whose confirmed upstream dependencies are in prior levels;
6. within a level, sort by downstream dependent count descending;
7. use module then model name only as deterministic tie-breakers;
8. terminal/consumer models naturally appear toward the end because they have few or no downstream dependents.

## 6. Interactive model-review protocol

HMS-005 will generate the authoritative review register and assign one task code per model:

```text
HMSR-001
HMSR-002
...
HMSR-123
```

The codes and exact commit messages are generated only after dependency ordering is validated. Do not preassign model codes before HMS-005.

Each interactive model session must present:

- model identity and bounded context;
- dependency level and why it appears there;
- confirmed upstream dependencies;
- confirmed downstream dependents;
- every declared field and type;
- intended business meaning of each field;
- `...Id`, code, snapshot and enum/reference semantics;
- mandatory vs optional semantics;
- lifecycle/status semantics;
- multilingual handling;
- bounded-context ownership;
- persistence/FK consistency;
- relevant pipeline/SONATRACH operational interpretation;
- unresolved questions requiring user decision.

Allowed model decisions:

```text
APPROVED
REVISE
DEFER
REMOVE
```

A review task records the decision and evidence. It must not silently change production code. Any approved production-model correction requires its own appropriate module roadmap/task before the semantic baseline can treat that correction as implemented.

## 7. Task roadmap

| Code | Exact commit message | Deliverable / acceptance | Status |
|---|---|---|---|
| HMS-001 | `docs(model-review): establish target model semantic review roadmap` | Create this roadmap and amend HDP so HDP-004 is explicitly paused behind the semantic-review prerequisite. No dependency analysis or production code changes. | **Completed** |
| HMS-002 | `docs(model-review): inventory target model dependency evidence` | Inventory candidate relationship evidence for all 123 models from domain source, JPA, Flyway, contracts and enums. Every candidate edge retains evidence/provenance; no review order yet. | Planned |
| HMS-003 | `docs(model-review): classify target model dependencies` | Resolve candidate edges into the dependency classifications in section 4; separate true dependencies from snapshots/codes and record unresolved edges. | Planned |
| HMS-004 | `docs(model-review): validate dependency graph and cycles` | Reconcile graph against persistence/contracts, identify strongly connected components, missing targets, contradictory edges and cross-module boundary concerns. | Planned |
| HMS-005 | `docs(model-review): generate dependency ordered model review register` | Compute deterministic review levels/order and generate the 123-model review register with `HMSR-001…HMSR-123` codes and exact per-model commit messages. | Planned |
| HMS-006 | `docs(model-review): reconcile interactive model decisions` | After all HMSR tasks are resolved, reconcile APPROVED/REVISE/DEFER/REMOVE decisions, outstanding corrections, unresolved cycles and dependency impacts. | Planned |
| HMS-007 | `docs(model-review): finalize approved target model semantic baseline` | Publish the final target-model semantic baseline only when every model has a resolved disposition and any required model corrections are implemented or explicitly deferred. | Planned |
| HMS-008 | `docs(data-provisioning): align provisioning roadmap to semantic model baseline` | Amend HDP target assumptions, record the approved HMS baseline, and determine whether HDP-004 may resume. Does not itself classify source data or start HDP-005. | Planned |

## 8. HMSR review tasks

The model-specific section is intentionally empty until HMS-005.

HMS-005 must populate a deterministic register containing, at minimum:

| Review Code | Level | Module | Model | Upstream dependencies | Downstream dependents | Decision | Status |
|---|---:|---|---|---|---:|---|---|

Each HMSR code is an independent interactive review commit. Execute only the model explicitly authorized by the user.

## 9. Completion gate

The semantic baseline is complete only when:

- all 123 catalogued models appear exactly once in the review register;
- every dependency edge has evidence/classification or an explicit unresolved decision;
- graph cycles are identified and resolved/grouped;
- every model has an interactive decision;
- model corrections required by REVISE/REMOVE decisions are implemented through their owning module roadmaps or explicitly deferred;
- dependency impacts are recalculated after corrections;
- HMS-007 is complete.

Only HMS-008 may then decide whether HDP-004 can resume.

## 10. Current next task

```text
HMS-002 — docs(model-review): inventory target model dependency evidence
```

Do not start HMS-003 or any interactive HMSR review until HMS-002 is completed and reported.
