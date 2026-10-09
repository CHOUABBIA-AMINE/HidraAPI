# HidraAPI Version History

**Current release/development state — 2026-10-09:** `v0.6.0` is the published
latest formal release at `09bf1cbf82c20f19c50ebb1ff9e27047c7f34856`, verified by
Documentation #138 and full HidraAPI CI #610. Main is on `0.7.0-SNAPSHOT` at
`ce8867c252fb537d9cca652f9b397a6d35bbc96a`, verified by Documentation #140 and
full HidraAPI CI #611; HPR-REL-002 is COMPLETED.

## 1. Purpose

This document reconstructs the logical HidraAPI release history from the live `main` branch,
repository roadmaps, milestone closure documents, and real Git commit history.

It intentionally does **not** create a release version for every commit. HidraAPI uses roadmap-scale
development milestones as release boundaries and follows Semantic Versioning 2.0.0 conventions
for its pre-1.0 development line.

## 2. Evidence baseline

Forensic reconstruction was performed against the live repository state ending at:

```text
4cadc132ce4cbb9d2aa075eaaf4f3dfeb69cf2dc
docs(organization): record ORG-033 closure
2026-09-29
```

The inspected `main` history contains **855 commits**, beginning with:

```text
0797f94a81d9bb1dca881b0dbd939e8b472f73c3
Initial commit
2026-05-30
```

and ending at the closure commit above.

The source tree contains 24 bounded business modules:

```text
alarm
analytics
assets
audit
configuration
custody
documents
hse
identity
incident
integration
integrity
leakdetection
monitoring
notification
organization
party
planning
reporting
risk
simulation
telemetry
topology
workflow
```

Release boundaries were selected from explicit roadmap/checklist completion commits and major
architecture/capability closures rather than raw commit count.

## 3. Version-to-commit traceability

| SemVer Version | Corresponding Git Commit Hash | Release Date / Milestone Context | Core Summary of Changes |
|---|---|---|---|
| `0.1.0-alpha.1` | `999fb7568a7cde6c8bab7df80e641d21d7879017` | 2026-05-30 — Kernel checklist finalized | First coherent framework-neutral Kernel baseline: API/error primitives, application contracts, pagination/results, domain primitives, validation structure, and dependency rules. |
| `0.1.0-alpha.2` | `69dadf9d627683b8259c1957b8340ade50c03402` | 2026-05-31 — Platform checklist finalized | Technical Platform baseline layered over Kernel: configuration, persistence/security plumbing, and the platform infrastructure defined at that milestone. |
| `0.1.0` | `cd7fa45f00f97b2fc1b7fa4b41da76b5e3a3f065` | 2026-06-05 — STB-011 foundation validation | Foundation release boundary: Kernel/Platform plus early Identity/Organization implementation, Flyway foundation, development security, OpenAPI completion, and application boot validation before Topology expansion. |
| `0.2.0-beta.1` | `a2e132338b252b9ec7e993c0b030052c85a0afbe` | 2026-06-06 — Correction 02 finalized | Architecture/API correction milestone: catalog and controlled-vocabulary policy, multilingual contracts, OpenAPI boundary policy, schema documentation, repository guidance, and architecture guardrails. |
| `0.2.0` | `c8947feab934fe95c7eaff3c863afaaba73ddb8e` | 2026-06-13 — explicit HidraAPI v0.2.0 milestone | Full modular-monolith baseline across 24 business modules with domain/application/API/infrastructure sources, consolidated architecture/data definitions, configuration/security wiring, and initial cross-module integration. This commit also established the historical POM version `0.2.0`. |
| `0.3.0` | `7b24dc122e52dd0c5471307e3611f2a3d999ae7d` | 2026-09-15 — AUTH-030 authentication gap closure | Dynamic LOCAL + LDAP/AD + OIDC routing; persistent LOCAL credentials; unified Hidra principal, session and JWT contracts; safe administrator bootstrap; authorization ownership; secret/architecture guardrails; deployment runbook and verified authentication closure. |
| `0.4.0` | `be3fefdd86094f2f2645a737c1d9375e7daff7b0` | 2026-09-29 — forensic remediation closure | Repository-wide architectural hardening: dead event/orphan removal, domain invariant enforcement, deliberate domain/JPA simplification, generated boundary mapping, cross-module contract repairs, REST/domain leakage remediation, and same-module referential integrity enforcement. |
| **`0.5.0`** | **`4cadc132ce4cbb9d2aa075eaaf4f3dfeb69cf2dc`** | **2026-09-29 — ORG-033 operational-scope correction closure** | Canonical operational-scope registry; multi-scope responsibilities; authoritative owner resolution; Identity authorization + Workflow + Audit governance; concurrency protection; canonical REST/OpenAPI; legacy scope-schema retirement; and issue #130 end-to-end acceptance closure. |
| **`0.6.0`** | **`09bf1cbf82c20f19c50ebb1ff9e27047c7f34856`** | **2026-10-09 — formal v0.6.0 release; semantic milestone anchor a8905e32289a583f47b831e0381783e556ae0c8d** | P0 security/audit closure, P1 production-readiness/survivability controls within retained evidence scope, P2 canonical governance/API/database documentation and all 57 reconciled HMR implementations; permanent 123-subject catalogue; exact release CI #610 passed. |

## 4. Interpretation of historical version labels

Commit `beffa6e3d41d7a76c37e01d7d2b3e7d5c7d44d37` is titled `HidraAPI v0.0.2`,
but its project POM still reports `0.0.1`. It is therefore retained as historical evidence but is
not used as a formal reconstructed release boundary.

Commit `c8947feab934fe95c7eaff3c863afaaba73ddb8e` is internally consistent: its commit
subject is `HidraAPI v0.2.0` and the POM at that milestone reports `0.2.0`.

Versions `0.3.0`, `0.4.0`, and `0.5.0` are retrospective semantic release identifiers derived
from later roadmap-scale completion milestones. They must not be represented as historical Git tags
unless such tags actually exist.

## 5. Current baseline

The last formally released repository baseline is:

```text
0.6.0
```

The logical reconstructed progression is:

```text
0.1.0-alpha.1
    ↓
0.1.0-alpha.2
    ↓
0.1.0
    ↓
0.2.0-beta.1
    ↓
0.2.0
    ↓
0.3.0
    ↓
0.4.0
    ↓
0.5.0
    ↓
0.6.0
```

The project remains pre-`1.0.0`: controlled data provisioning and other future product work remain
outside the completed release milestones documented here.

## 6. Release tagging rule

The historical rows through 0.5.0 contain **milestone traceability anchors**.
The 0.6.0 row records its actual formal release-alignment commit and identifies
the semantic milestone separately. A Git release tag must point to a commit
whose POM contains the matching release version.

Therefore `v0.5.0` must **not** be retroactively placed on
`4cadc132ce4cbb9d2aa075eaaf4f3dfeb69cf2dc`, because that historical milestone commit still
contains POM version `0.2.0`.

The appropriate `v0.5.0` tag target is the release-alignment commit that introduces this
`VERSIONS.md` file together with `pom.xml` version `0.5.0`, after that commit passes the
repository verification pipeline.

No tag or GitHub Release is created merely by this document.

## 7. Maven release policy

The repository configures Apache Maven Release Plugin `3.3.1` with:

- tag format `v@{project.version}`;
- built-in `SemVerMinorDevelopment` policy for the next development line;
- `clean verify` as the release preparation verification gate;
- SCM push enabled for an explicitly executed release operation.

The release plugin is tooling only; adding it does not itself create tags or releases.

Signed Git tags are recommended for formal releases, but signing is intentionally not forced in the
POM until the release environment has a verified signing identity and key configuration.

## 8. Historical 0.5.0 development-line transition

After a formal `v0.5.0` release is created from the verified release-alignment commit, normal
development should advance to:

```xml
<version>0.6.0-SNAPSHOT</version>
```

That development-version transition should be performed as part of the formal release procedure,
not pre-emptively in this baseline-alignment change.

## 9. Published 0.6.0 platform release — 2026-10-09

| Field | Published release state |
|---|---|
| Formal release | `0.6.0` — pre-1.0 minor architecture and stabilization milestone |
| Task / exact commit message | HPR-REL-001 / `chore(release): prepare 0.6.0 platform milestone` |
| Semantic milestone anchor | `a8905e32289a583f47b831e0381783e556ae0c8d` — renewed P2 canonical-governance closure |
| Verified milestone gates | Documentation #136 / run 37932486353 and full HidraAPI CI #609 / run 37933120029, both successful on the milestone anchor |
| Preparation source parent | `ac6cbb4b1cd584b0c780dd208543f41ebe326e06` — verified P2 closure evidence record; Documentation #137 / run 37934631329 successful |
| Release-alignment commit | `09bf1cbf82c20f19c50ebb1ff9e27047c7f34856`, tree `d46f669bda1bf74e2fe35ede3d57aa554d0c1919`; POM 0.6.0 |
| Release verification | PASSED — Documentation #138 / run 37936829457 and full HidraAPI CI #610 / run 37936829442, both successful on the exact alignment SHA |
| Tag / GitHub Release | `v0.6.0` / HidraAPI v0.6.0, ID 407972853; published by owner 2026-10-09T14:02:18Z, latest, non-draft, non-prerelease |
| Tag form | Lightweight/unsigned ref, directly targeting the release-alignment commit; no signed annotated tag is claimed |
| Last formal release | `v0.6.0`, release commit `09bf1cbf82c20f19c50ebb1ff9e27047c7f34856`; previous formal release v0.5.0 remains historical |
| Current development line | `0.7.0-SNAPSHOT` at ce8867c252fb537d9cca652f9b397a6d35bbc96a; HPR-REL-002 COMPLETED; Documentation #140 and full HidraAPI CI #611 PASSED |

The coherent milestone comprises the completed P0 security/audit closure, P1
production-readiness and survivability controls with their retained evidence scope,
P2 canonical governance/API/database documentation, and all 57 reconciled HMR
implementations. The permanent semantic catalogue retains 123 subjects across
24 implemented modules. These are architecture/capability and stabilization
changes since 0.5.0, justifying a minor milestone rather than a patch release.

Source-of-truth completion evidence remains in the
[Ultimate Roadmap](doc/roadmap/ULTIMATE_ROADMAP.md) and
[reconciliation](doc/model-remediation/RECONCILIATION.md). P3, TimescaleDB,
PostGIS and future industrial extensions remain DEFERRED/TARGET. This candidate
does not claim unrestricted endpoint/performance/OT coverage, new physical
survivability exercises, production-data/import acceptance, unknown business
policy approval or deployed autonomous AI/actuation.

API metadata remains `v1`; the Maven release version is a separate version line.
No API snapshot, schema, migration, dependency or feature change is included.
The milestone anchor contains POM 0.6.0-SNAPSHOT and is not the tag target.
The actual v0.6.0 tag resolves to the successfully verified alignment commit
containing POM 0.6.0. Preserve this published tag; never move it to the development
transition or supporting evidence-record commit.

HPR-REL-001 preparation is COMPLETED; the formal release is published.
CI #610's Java 21 production job passed clean verify, generated OpenAPI identity
and canonical equality, backward compatibility, current migrated dictionary
comparison and retained P1 evidence/operations gates. Its dictionary summary
reports 139 migrations, 470 JPA mappings, 482 relations, reviewed dictionary
checked true and zero unresolved owners. The exact-SHA OpenAPI and schema artifacts
were retained. Artifact metadata and CI logs were inspected; artifact archives
and per-class execution/skip reports were not independently re-inspected here.
The release/development record does not replace the verified release-alignment SHA
as the tag target or infer production deployment from publication.

## 10. Post-release development transition — 2026-10-09

The owner selected HPR-REL-002 after the real tag and GitHub Release were verified:
`chore(release): start 0.7.0 development`. Only the POM project version changes
from 0.6.0 to 0.7.0-SNAPSHOT; no dependencies/features/schema/API change is included.
The transition commit is `ce8867c252fb537d9cca652f9b397a6d35bbc96a`, tree
`a5a5dd39214d7f5cfdcee96af878c437103211a5`, with POM 0.7.0-SNAPSHOT.
Documentation #140 / run 37941995197 and full HidraAPI CI #611 / run 37941995284
both PASSED on that SHA. HPR-REL-002 is COMPLETED. Release #610 remains the exact
formal-release evidence; #611 separately verifies the development transition.
The release-management cycle is complete under AGENTS.md §19, without asserting
production deployment. This supporting documentation record is not a replacement
release tag target or executable transition baseline.

[Published v0.6.0 release](https://github.com/CHOUABBIA-AMINE/HidraAPI/releases/tag/v0.6.0).
P3 remains DEFERRED. Production promotion, private-data imports, owner-policy
approvals and new physical campaigns are separate actions.
