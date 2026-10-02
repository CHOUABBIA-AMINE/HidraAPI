# HidraAPI Version History

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

The verified repository baseline is:

```text
0.5.0
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
```

The project remains pre-`1.0.0`: controlled data provisioning and other future product work remain
outside the completed release milestones documented here.

## 6. Release tagging rule

The hashes in the table are **milestone traceability anchors**. A Git release tag should point to a
commit whose POM contains the matching release version.

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

## 8. Next development line

After a formal `v0.5.0` release is created from the verified release-alignment commit, normal
development should advance to:

```xml
<version>0.6.0-SNAPSHOT</version>
```

That development-version transition should be performed as part of the formal release procedure,
not pre-emptively in this baseline-alignment change.
