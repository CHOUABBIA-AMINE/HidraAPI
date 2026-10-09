# HidraAPI Canonical Documentation Validation

## Status and applicability

CURRENT — HPR-P2-012 bounded CI validation controls, verified against source parent
`508337351eef03b82e2c6078a7c8523013efd063`, on 2026-10-09. Repository governance controls
manifest/index/status changes; module owners retain implementation authority. No new
named owner, per-class runtime result, deployment acceptance or physical evidence is
established. [Standard](DOCUMENTATION_STANDARD.md), [status model](DOCUMENT_STATUS_MODEL.md)
and [lifecycle](DOCUMENT_LIFECYCLE.md) govern metadata and preserved evidence.

## Implemented controls and boundaries

| Control | Source / bounded responsibility |
|---|---|
| Canonical estate | [validate_docs.py](../../.github/scripts/validate_docs.py): exact reviewed inventory, UTF-8/nonempty/conflicts, local inline/reference links and anchors |
| Metadata/status | Leading declared statuses; explicit containing-index/register metadata signals; exact historical P1 exceptions and roadmap ACTIVE control |
| Navigation/module/register | Declared index targets, actual module roots/documents, duplicate module links and CURRENT registry consistency |
| Execution control | Primary 13-row P2 registry shape/status/codes, selected next task and prerequisite-safe later closure |
| Snapshot integrity | [validate_openapi_snapshot.py](../../.github/scripts/validate_openapi_snapshot.py): strict JSON, sorted compact bytes, digest/counts/source/provenance, local pointers and representative security |
| Runtime drift | Production CI compares every generated value against canonical content except x-hidra-ci-source-sha; expected source identities validated separately |
| Compatibility | Existing generated-base resolver/tests and supported backward-compatibility gate remain separate and required |

[Reviewed manifest](../../.github/documentation-validation.json) declares inventory,
metadata inheritance, index/register obligations, module roots, historical-status
exceptions and artifact-derived snapshot identities. Signals and structural consistency
do not prove narrative truth, comprehensive business metadata or owner approval. Only
the leading status/control block is checked; appended historical task paragraphs are
not reclassified as present authority. Eleven exact P1 operating/status phrases retain
path-specific rationale, while bold HISTORICAL/CURRENT formatting uses normal statuses.
No historical P1 document is rewritten to satisfy the checker.

Local paths/fragments are checked without network calls; external/mailto availability
is not checked. Fenced/inline examples are ignored. Heading anchors handle formatting,
duplicates, Unicode and explicit IDs. Case-mismatched/missing targets and repository
escapes fail. The parser implements the estate's Markdown conventions, not every
CommonMark extension or arbitrary HTML resource reference.

## Commands

Run from repository root using standard-library Python 3 (CI Ubuntu provides it):

```bash
python3 .github/scripts/test_validate_docs.py
python3 .github/scripts/test_validate_openapi_snapshot.py
python3 .github/scripts/validate_docs.py
python3 .github/scripts/validate_openapi_snapshot.py
python3 .github/scripts/validate_openapi_snapshot.py --generated target/openapi/hidra-api.json --generated-source-sha "$GITHUB_SHA"
```

The last command requires actual generated output and its expected build SHA; it is
not part of the offline documentation job. Both scripts accept --root and --manifest
for isolated fixtures. Tests create temporary estates/contracts and assert actual
negative failures; they make no network/runtime/production-data changes.

## CI wiring

[Documentation workflow](../../.github/workflows/docs.yml) runs both suites and both
offline validators on doc/Markdown, exact validator/test/manifest and workflow changes.
[Production workflow](../../.github/workflows/ci.yml) also runs these checks, preserves
all prior P1/Java 21/PostgreSQL/clean-verify/OpenAPI gates and adds exact runtime equality
after fresh generation. Existing read-only permissions/concurrency remain intact.
A source-only production change still exercises documentation consistency and snapshot
equality through production CI. This implementation starts both exact-head workflows;
startup is not completion. Failures must be diagnosed before later task execution.

## Maintenance and generated-only refresh

Adding/removing a canonical document, module, entry point or status exception requires
deliberate manifest and index/register review. New unknown statuses do not become
accepted just by editing prose. Preserved historical phrases are exact/path-specific;
never add a wildcard exemption. Metadata inheritance must name existing canonical
sources and contain the declared owner/applicability/evidence/target/unknown/verification
signals. This is bounded validation, not automated business attestation.

When runtime contract content changes, obtain a successful exact-source generated
artifact; verify run/source/archive/member/digests; serialize its object with sorted
compact JSON and newline; update source/counts/file digest and generation provenance
in manifest/API documents. Preserve prior generation evidence. Never hand-edit the
contract, claim the consumer commit generated it, remove other fields during equality,
or treat supported compatibility as equality. The current snapshot is exactly the
verified CI #604 object at `617c2eec812e3a5734957ee9fa0360f6f5613032`: 244 paths,
263 operations, 231 schemas. Source/archive/file identities are in
[OpenAPI governance](../api/OPENAPI_GOVERNANCE.md).

## Actual validation and unresolved evidence

Python test/validator results and current Maven attempt are recorded in canonical
[execution memory](../roadmap/ULTIMATE_ROADMAP.md). The successful CI #604 generator
remains prior evidence; local Java 17 cannot establish a Java 21 build. The current
Bash wrapper clean-verify attempt failed before compilation resolving Boot parent
4.1.1 because Maven Central DNS was unavailable. No runtime/JPA/test pass or physical
campaign is claimed. New exact-head production/documentation CI follows publication.

## TARGET and NOT ESTABLISHED

Complete narrative truth, full Markdown/OpenAPI certification, external-link uptime,
per-class execution/no skips, deployed-data/import approval and fresh physical
survivability are NOT ESTABLISHED by these controls. HPR-P2-013 closure remains pending,
P3 deferred and P2 open. Future rule expansions require their own reviewed scope.

## HPR-P2-013 current canonical review — 2026-10-09

Verified source parent: `00c4fda266b2dfd175cca37ad789dc9462a5af0b`. Revised preflight Documentation Validation
#126 (37913628486) PASSED before this implementation. HPR-P2-012 documentation #124
(37909982710) and production #605 (37909982823) PASSED at
`e4dba168c9e612a5fd49d50b155fa3b2d8d64e40`.

All 24 module documents and canonical indexes remain registered; maintained metadata,
local links and source module roots are checked by canonical validation. Permanent
semantics retain 123 subjects and reconciliation retains 57 completed HMR identities.
The executable source/test/resources/POM and production operations are unchanged from
the semantic-transfer and CI #604 baselines. Legacy docs/** remains subordinate
history; no review verdict is used as current execution authority.

Current database inventory is refreshed to 139 unique versioned migrations and 470
module entities. Current architecture exports are 70 Java files in 63 packages,
including neutral extension roles. Original architecture/database/P1 provenance
retains its own source/deployed applicability. The current API object is unchanged:
CI #604 generated the snapshot; #605 freshly verified equality except source-SHA
provenance and independently passed supported compatibility. No endpoint/schema or
security value is manually edited.

HPR-P2-013 documentation implementation is complete, but P2 final VERIFIED/CLOSED
disposition is PENDING both successful CI workflows on the resulting implementation
SHA. Current implementation publication is not CI success. No P3 task is selected.
Prior dated pending/publication statements above retain historical applicability and
are superseded by this current review where they describe the earlier execution state.

Unknown business retention/policy approvals, complete endpoint/performance/OT coverage,
per-class no-skips evidence, deployed-data/import acceptance, runtime inference/actuation
and fresh physical survivability are not established by this documentation closure.
P0/P1 disposition and original physical evidence remain unchanged absent regression.
TimescaleDB, PostGIS and unimplemented industrial/AI extensions remain DEFERRED/TARGET.

## Renewed HPR-P2-013 audit and closure gate — 2026-10-09

The read-only P2 audit at `7be1c9cb47ed9b6f73a7692d0a328ad870e2b4d4`
returned PASS: all twelve checks VERIFIED. Preflight Documentation #135 passed at
that SHA. HPR-P2-005 correction is COMPLETED after #131; HPR-P2-006 is COMPLETED
at `1bc3c1bba2d08a0b493e5ece43e834e8d56d04f0` after Documentation #134 and
Production #608 passed, including fresh physical dictionary comparison with zero
unresolved owners. These later facts supersede earlier publication-pending summaries;
dated historical records and their original applicability remain preserved.

This closure metadata implementation records the audit and verified prerequisites.
HPR-P2-013 is IN PROGRESS; P2 remains OPEN pending both documentation and full
production CI on the resulting closure commit. The exact current disposition and
retained twelve-check evidence follow [the roadmap](../roadmap/ULTIMATE_ROADMAP.md).
Docs-only push does not start full production CI: the existing HidraAPI CI manual
workflow must run on the exact closure head. No preceding CI result substitutes for
that gate. Stop after startup observation; no P3 task is selected.

All 24 module slices, 123 semantic subjects and 57 completed HMR identities remain.
Source-derived schema facts retain disposable PostgreSQL-16 capture applicability;
P1 deployed/recovery evidence retains its original scope. No production-data/import
approval, business retention/policy values, hydraulic/ML runtime execution or field
actuation is established. Version remains 0.6.0-SNAPSHOT. No executable, migration,
contract snapshot, dictionary, ownership metadata or operating artifact is changed.
